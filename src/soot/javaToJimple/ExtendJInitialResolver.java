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

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

import soot.*;
import soot.javaToJimple.extendj.ast.*;
import soot.javaToJimple.extendj.ast.ClassSource;
import soot.options.Options;
import soot.toolkits.scalar.Pair;

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

    private Map<String, WeakReference<CompilationUnit>> classNameToCU     = new TreeMap<>();
    private Map<String, ClassSource                   > className2source  = new HashMap<>();
    private WeakReference<Program>                      program           = null;

    public ExtendJInitialResolver(soot.Singletons.Global g) { }

    // SS SPECIFIC: Grafiketisto requires access to this lookup info.
    public Map<String, ClassSource> classNameToSource() { return className2source; }

    @Override
    public Dependencies resolveFromJavaFile(List<String> locations, String fullPath, String className, SootClass sc) {
        assert sc.getName().equals(className);

        final Option<Pair<CompilationUnit, Dependencies>> ud = loadAST(className);
        if (!ud.hasValue())
            throw new RuntimeException("Error: couldn't find class: " + className + " are the packages set properly?");

        return ud.get().getO2();
    }

    protected Option<Pair<CompilationUnit, Dependencies>> loadAST(String className) {
        Option<CompilationUnit> optCU = parseAST(className);
        if (!optCU.hasValue() ) return Option.none();
        // if resolved -> all the dependencies for this CU have already been added to the worklist
        CompilationUnit u = optCU.get();
        if (u.isResolved      ) return Option.some(new Pair<>(u, new Dependencies()));
        u.isResolved = true;

        u.jimplify1();

        HashSet<TypeDecl> types = new HashSet<>();
        for (TypeDecl   t : u.getTypeDecls())
            collectTypeDecl(t, types);

        for (TypeDecl t : types) {
            Map<SootMethod, BodyDecl> method2body = new HashMap<>();
            for (BodyDecl d : t.methodsAndConstructors()) {
                if (d instanceof      MethodDecl) method2body.put(((     MethodDecl)d).sootMethod, d);
                if (d instanceof ConstructorDecl) method2body.put(((ConstructorDecl)d).sootMethod, d);
            }

            // HACK: FIXME: Extendj isn't yet thread-safe. Serialise at program granularity.
            Object        lock  = u.program();
            // FIXME: Do we just need to honour the signature? If so then we need to generalise
            //        body-production to be reentrant/pure and change the lookup mapping key.
            MethodSource  ms    = (m, phaseName) -> { synchronized(lock) {
                if (m.getDeclaringClass() != t.sootClass())
                    throw new RuntimeException("Attempted to load a method from some other class: " +
                            m.getDeclaringClass().getName());

                if (t.clinit == m) {
                    m.setActiveBody(t.jimplify2clinit(m));
                    return m.getActiveBody();
                }

                // TODO: Convert the jimplify2 routines to something that's reentrant instead of implicitly mugging
                //       the existing body. (If any.)
                if (!method2body.containsKey(m))
                    throw new RuntimeException(
                            "Could not find body for " + m.getSignature() +
                                    " in " + m.getDeclaringClass().getName() + " during " + phaseName);

                method2body.get(m).jimplify2();
                return m.getActiveBody();
            } };

            for (SootMethod m : t.sootClass().getMethods())
                m.setSource(ms);
        }

        Dependencies deps = new Dependencies();
        deps.typesToHierarchy = u.hierarchyDependencies();
        deps.typesToSignature = u.signatureDependencies();
        return Option.some(new Pair<>(u, deps));
    }

    // parses the file(s) for the given class-name. result is cached.
    protected Option<CompilationUnit> parseAST(String className) {
        // extendj is finicky about how it loads its sources, so we need to work around some ugly behaviour:
        //  If we're attempting to load a (possibly) class, try to load the (possible) outer-class(es) first.
        //  If that succeeds, then we check to see if the class was loaded into our known-classes map.
        //  If it was, we're golden; if not, we need to try loading the file literally.
        //  We don't want to try loading the file literally first, because that seems to upset extendj.
        { Option<CompilationUnit> optCU = cuForClass(className);
          if (optCU.hasValue()) return optCU; }

        int outerClassSepIdx = className.lastIndexOf("$");
        if (outerClassSepIdx != -1) {
            // attempt to load the class as an inner class of some compilation unit
            // Local var to keep it alive until we finish checking below if it is the right one
            Option<CompilationUnit> liveHolder = parseAST(className.substring(0, outerClassSepIdx));

            // note that the 'outer' file might not contain the class (e.g. auto-gen code from jastadd)
            { Option<CompilationUnit> optCU = cuForClass(className);
              if (optCU.hasValue()) return optCU; }
        }

        CompilationUnit u = getProgram().getLibCompilationUnit(className);
        if (u == null) return Option.none();

        Collection<Problem> errors = u.errors();
        if (!errors.isEmpty()) {
            for (Problem p : errors)
                G.v().out.println(p);

            throw new CompilationDeathException(CompilationDeathException.COMPILATION_ABORTED,
                    "there were errors during parsing and/or type checking (extendj frontend)");
        }

        HashSet<TypeDecl> types = new HashSet<>();
        for (TypeDecl typeDecl : u.getTypeDecls())
            collectTypeDecl(typeDecl, types);

        WeakReference<CompilationUnit> ru = new WeakReference<>(u);
        for (TypeDecl t : types) {
            assert !classNameToCU.containsKey(t.jvmName());
            classNameToCU   .put(t.jvmName(), ru);
            className2source.put(t.jvmName(), u.getClassSource());
        }

        // Odd. We expect that the file would at least declare this className (if given).
        assert !types.isEmpty();
//        if (types.isEmpty()) {
//            assert !classNameToCU.containsKey(className);
//            classNameToCU.put(className, ru);
//        }

        assert classNameToCU.containsKey(className);
        assert classNameToCU.get(className).get() == u;
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

    protected Program getProgram() {
        Program p = (program == null) ? null : program.get();
        if (p == null) {
            p = mkProgram();
            program = new WeakReference<>(p);
        }

        return p;
    }

    protected Option<CompilationUnit> cuForClass(String className) {
        if (!classNameToCU.containsKey(className)) return Option.none();

        return Option.maybe(classNameToCU.get(className).get());
    }

    private void collectTypeDecl(TypeDecl typeDecl, HashSet<TypeDecl> types) {
        types.add(typeDecl);
        for (TypeDecl nestedType : typeDecl.nestedTypes())
            collectTypeDecl(nestedType, types);
    }
}
