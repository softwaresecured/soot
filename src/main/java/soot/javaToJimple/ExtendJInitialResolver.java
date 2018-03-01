/* Soot - a J*va Optimization Framework
 * Copyright (C) 2008 Eric Bodden
 * Copyright (C) 2008 Torbjorn Ekman
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */
package soot.javaToJimple;

import java.io.File;
import java.util.*;
import java.util.List;

import soot.*;
import soot.javaToJimple.extendj.ast.*;
import soot.options.Options;


/**
 * An {@link IInitialResolver} for the ExtendJ frontend.
 * NOTE: An instance of this class cannot be used concurrently.
 *
 * @author Torbjorn Ekman
 * @author Eric Bodden
 * @author Olivier Hamel
 */
public class ExtendJInitialResolver implements IInitialResolver {
    public static ExtendJInitialResolver v() { return soot.G.v().soot_javaToJimple_ExtendJInitialResolver(); }

    // TODO: Make these weak references so we can reclaim memory once the bodies are loaded?
    private Program program = null;

    public ExtendJInitialResolver(soot.Singletons.Global g) { }

    @Override
    public Dependencies resolveFromJavaFile(List<String> locations, File src, String className, SootClass sc) {
        if (!loadAST(className).hasValue())
            throw new RuntimeException("Error: couldn't find class: " + className + " are the packages set properly?");

        return resolveFromCache(className, sc);
    }

    public Dependencies resolveFromCache(String className, SootClass sc) {
        assert (sc == null) || sc.getName().equals(className);

        final Option<CompilationUnit> u = cuForClass(className);
        if (!u.hasValue()) {
            // HACK: Utter BS, but we need to humour anyone who happens to use annotations.
            if (className.endsWith(".package-info")) return dependencies(getProgram().getCompilationUnit(className));

            throw new RuntimeException("Error: couldn't find class: " + className + " are the packages set properly?");
        }

        return dependencies(u.get());
    }

    protected Dependencies dependencies(CompilationUnit u) {
        Dependencies deps = new Dependencies();
        deps.typesToHierarchy = u.hierarchyDependencies();
        deps.typesToSignature = u.signatureDependencies();

        for (Type t : deps.typesToHierarchy) assert !t.toString().contains("<");
        for (Type t : deps.typesToSignature) assert !t.toString().contains("<");
        return deps;
    }

    protected Option<CompilationUnit> loadAST(String className) {
        { Option<CompilationUnit> optCU = cuForClass(className);
          if (optCU.hasValue()) return optCU; }

        // extendj is finicky about how it loads its sources, so we need to work around some ugly behaviour:
        //  If we're attempting to load a (possibly) class, try to load the (possible) outer-class(es) first.
        //  If that succeeds, then we check to see if the class was loaded into our known-classes map.
        //  If it was, we're golden; if not, we need to try loading the file literally.
        //  We don't want to try loading the file literally first, because that seems to upset extendj.
        int outerClassSepIdx = className.lastIndexOf("$");
        if (outerClassSepIdx != -1) {
            // attempt to load the class as an inner class of some compilation unit
            new JavaClassProvider().find(className.substring(0, outerClassSepIdx)).resolve(null);

            // note that the 'outer' file might not contain the class (e.g. auto-gen code from jastadd)
            { Option<CompilationUnit> optCU = cuForClass(className);
              if (optCU.hasValue()) return optCU; }
        }

        CompilationUnit     u       = getProgram().getCompilationUnit(className);
        Collection<Problem> errors  = u.errors();
        if (!errors.isEmpty()) {
            for (Problem p : errors)
                G.v().out.println(p);

            throw new CompilationDeathException(CompilationDeathException.COMPILATION_ABORTED,
                    "there were errors during parsing and/or type checking (extendj frontend)");
        }

        HashSet<TypeDecl> types = new HashSet<>();
        for (TypeDecl typeDecl : u.getTypeDecls())
            collectTypeDecl(typeDecl, types);

        // Odd. We expect that the file would at least declare this className (if given).
        assert !types.isEmpty();
//        if (types.isEmpty()) {
//            assert !classNameToCU.containsKey(className);
//            classNameToCU.put(className, ru);
//        }

        return Option.some(u);
    }

    // FIXME: Replace `Integer` w/ `Maybe Integer` when/if soot ever modernises.
    protected static Integer sourcePrecedence() {
        final int src_prec = Options.v().src_prec();
        switch (src_prec) {
        case Options.src_prec_apk_c_j   : return null;
        case Options.src_prec_java      : return ClassPath.SRC_PREC_JAVA;
        case Options.src_prec_class     : return ClassPath.SRC_PREC_CLASS;
        case Options.src_prec_only_class: return ClassPath.SRC_PREC_ONLY_CLASS;
        default                         : throw new RuntimeException("invalid/unknown src-prec option: " + src_prec);
        }
    }

    protected static Program mkProgram() {
        // TODO: Why? This was the old behaviour but this seems... blunt. See `src_prec_apk_c_j`.
        final Integer src_prec = sourcePrecedence();
        if (src_prec == null) return null;

        final Program program = new Program();
        program.state().reset();

        program.initBytecodeReader(Program.defaultBytecodeReader());
        program.initJavaParser(Program.defaultJavaParser());
        program.setSrcPrec(src_prec);

        program.options().initOptions();
        program.options().addKeyValueOption("-classpath");
        program.options().setValueForOption(Scene.v().getSootClassPath(), "-classpath");
        if (src_prec != ClassPath.SRC_PREC_ONLY_CLASS) {
            program.options().addKeyValueOption("-sourcepath");
            program.options().setValueForOption(Scene.v().getSootClassPath(), "-sourcepath");
        }
        return program;
    }

    // SS SPECIFIC: Grafiketisto requires access to this for lookup info.
    public Program getProgram() {
        if (program == null)
            program = mkProgram();

        return program;
    }

    public Option<CompilationUnit> cuForClass(String jvmName) {
        TypeDecl typ = getProgram().lookupTypeByJvmName(jvmName);
        if ( typ.isUnknown()) return Option.none();

        CompilationUnit u = typ.compilationUnit();

        // Don't provide CUs for things which were parsed from byte-code.
        // If we try to handle it via extendj, we might get a byte-code class with constructs
        // that our current version of extendj cannot handle.
        if (!u.fromSource() ) return Option.none();

        // if resolved -> the types have been fully declared
        if ( u.isResolved   ) return Option.some(u);
        u.isResolved = true;

        u.jimpleDeclare();

        HashSet<TypeDecl> types = new HashSet<>();
        for (TypeDecl   t : u.getTypeDecls())
            collectTypeDecl(t, types);

        for (final TypeDecl t : types) {
            final Map<SootMethod, MethodLikeDecl<?>> method2body = new HashMap<>();
            for (BodyDecl d : t.methodsAndConstructors()) {
                MethodLikeDecl<?> m = null;
                if (d instanceof      MethodDecl) m = (MethodDecl     )d;
                if (d instanceof ConstructorDecl) m = (ConstructorDecl)d;

                if (m != null) method2body.put(m.sootMethod(), m);
            }
            if (t.needsClinit()) {
                MethodLikeDecl<?> body = t.clinitHelper();
                method2body.put(body.sootMethod(), body);
            }

            // HACK: FIXME: Extendj isn't yet thread-safe. Serialise at program granularity.
            final Object  lock  = u.program();
            // FIXME: Do we just need to honour the signature? If so then we need to generalise
            //        body-production to be reentrant/pure and change the lookup key.
            MethodSource  ms    = new MethodSource() {
                @Override
                public Body getBody(SootMethod m, String phaseName) { synchronized(lock) {
                    if (m.getDeclaringClass() != t.sootClass())
                        throw new RuntimeException("Attempted to load a method from some other class: " +
                                m.getDeclaringClass().getName());

                    if (!method2body.containsKey(m))
                        throw new RuntimeException("Could not find body for " + m.getSignature() + " in " +
                                m.getDeclaringClass().getName() + " during " + phaseName);

                    final Body b = method2body.get(m).jimpleBody();
                    // must set the method before, otherwise `setActiveBody` tries to access the previous method owning the
                    // body & panics when there isn't one
                    b.setMethod(m);
                    m.setActiveBody(b);
                    return m.getActiveBody();
                } } };

            for (SootMethod m : t.sootClass().getMethods())
                m.setSource(ms);
        }


        return Option.some(u);
    }

    private void collectTypeDecl(TypeDecl typeDecl, HashSet<TypeDecl> types) {
        types.add(typeDecl);
        for (TypeDecl nestedType : typeDecl.nestedTypes())
            collectTypeDecl(nestedType, types);
    }
}
