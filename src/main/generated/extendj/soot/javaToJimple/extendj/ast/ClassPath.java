package soot.javaToJimple.extendj.ast;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.*;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jastadd.util.*;
import java.util.Collections;
import java.util.zip.*;
import java.io.*;
import java.util.Collection;
import org.jastadd.util.PrettyPrintable;
import org.jastadd.util.PrettyPrinter;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import soot.*;
import soot.util.*;
import soot.jimple.*;
import soot.coffi.ClassFile;
import soot.coffi.method_info;
import soot.coffi.CONSTANT_Utf8_info;
import soot.tagkit.SourceFileTag;
import soot.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import beaver.*;
import soot.coffi.CoffiMethodSource;
/**
 * @ast class
 * @aspect ClassPath
 * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:236
 */
public class ClassPath extends java.lang.Object {
  
    // SOOT EXTENSION: enables tweaking the load order
    public static final int SRC_PREC_JAVA       = 1;

  
    public static final int SRC_PREC_CLASS      = 2;

  
    public static final int SRC_PREC_ONLY_CLASS = 3;

  

    /**
     * Tracks all currently available packages in the program classpath.
     */
    private Set<String> packages = new HashSet<String>();

  

    private boolean pathsInitialized = false;

  

    private ArrayList<PathPart> classPath = new ArrayList<PathPart>();

  

    private ArrayList<PathPart> sourcePath = new ArrayList<PathPart>();

  

    private final Program program;

  

    public ClassPath(Program program) {
      this.program = program;
    }

  

    /**
     * Used to make the classpath empty, in case you want more control
     * over the classpath initialization. Usually you would use
     * addClassPath to manually setup the classpath after this.
     */
    public synchronized void initEmptyPaths() {
      pathsInitialized = true;
    }

  

    /**
     * Set up the classpaths (standard + boot classpath).
     */
    private synchronized void initPaths() {
      if (pathsInitialized) {
        return;
      }
      pathsInitialized = true;

      ArrayList<String> classPaths = new ArrayList<String>();
      ArrayList<String> sourcePaths = new ArrayList<String>();

      String[] bootclasspaths;
      if (program.options().hasValueForOption("-bootclasspath")) {
        bootclasspaths = program.options().getValueForOption("-bootclasspath")
            .split(File.pathSeparator);
      } else {
        bootclasspaths = System.getProperty("sun.boot.class.path")
            .split(File.pathSeparator);
      }
      for (String path : bootclasspaths) {
        classPaths.add(path);
      }

      String[] extdirs;
      if (program.options().hasValueForOption("-extdirs")) {
        extdirs = program.options().getValueForOption("-extdirs").split(File.pathSeparator);
      } else {
        extdirs = System.getProperty("java.ext.dirs").split(File.pathSeparator);
      }
      for (String path : extdirs) {
        classPaths.add(path);
      }

      String[] userClasses = null;
      if (program.options().hasValueForOption("-classpath")) {
        userClasses = program.options().getValueForOption("-classpath").split(File.pathSeparator);
      } else if (program.options().hasValueForOption("-cp")) {
        userClasses = program.options().getValueForOption("-cp").split(File.pathSeparator);
      } else {
        userClasses = new String[] { "." };
      }
      if (!program.options().hasValueForOption("-sourcepath")) {
        for (String path : userClasses) {
          classPaths.add(path);
          sourcePaths.add(path);
        }
      } else {
        for (String path : userClasses) {
          classPaths.add(path);
        }
        userClasses = program.options().getValueForOption("-sourcepath").split(File.pathSeparator);
        for (String path : userClasses) {
          sourcePaths.add(path);
        }
      }

      for (String path : classPaths) {
        PathPart part = PathPart.createClassPath(path);
        if (part != null) {
          addClassPath(part);
        } else if (program.options().verbose()) {
          System.out.println("Warning: Could not use " + path + " as class path");
        }
      }
      for (String path : sourcePaths) {
        // PathPart part = null;
        // try {
        //     part = new File(path).isFile()  ? new JarSrcFilePath(path)
        //                                     : PathPart.createSourcePath(path);
        // } catch (IOException e) {}
        PathPart part = PathPart.createSourcePath(path);
        if (part != null) {
          addSourcePath(part);
        } else if(program.options().verbose()) {
          System.out.println("Warning: Could not use " + path + " as source path");
        }
      }
    }

  

    /**
     * Get the input stream for a compilation unit specified using a canonical
     * name. This is used by the bytecode reader to load nested types.
     * @param name The canonical name of the compilation unit.
     */
    public synchronized InputStream getInputStream(String name) {
      try {
        for (PathPart part : classPath) {
          ClassSource source = part.findSource(name);
          if (source != ClassSource.NONE) {
            return source.openInputStream();
          }
        }
      } catch(IOException e) {
      }
      throw new Error("Could not find nested type " + name);
    }

  

    public ClassSource classSource(int srcPrec, String typeName) {
      initPaths();

      ClassSource sourcePart  = ClassSource.NONE;
      ClassSource classPart   = ClassSource.NONE;

      for (PathPart part : sourcePath) {
        sourcePart = part.findSource(typeName);
        if (sourcePart != ClassSource.NONE) break;
      }

      for (PathPart part : classPath) {
        classPart = part.findSource(typeName);
        if (classPart != ClassSource.NONE) break;
      }

      boolean javaMoreRecent  =  (   sourcePart               != ClassSource.NONE            )
                              && ( ( classPart                == ClassSource.NONE          )
                               ||  ( classPart.lastModified() <  sourcePart.lastModified() ) );
      boolean javaPreferred   = (srcPrec == SRC_PREC_JAVA) || javaMoreRecent;
      boolean useSrcPart      = (sourcePart != ClassSource.NONE   ) &&
                                (srcPrec    != SRC_PREC_ONLY_CLASS) &&
                                javaPreferred;

      return useSrcPart ? sourcePart : classPart;
    }

  

    /**
     * Load a compilation unit from disk based on a classname. A class file is parsed if one exists
     * matching the classname that is not older than a corresponding source file, otherwise the
     * source file is selected.
     *
     * <p>This method is called by the LibCompilationUnit NTA.  We rely on the result of this method
     * being cached because it will return a newly parsed compilation unit each time it is called.
     *
     * @return the loaded compilation unit, or the provided default compilation unit if no matching
     * compilation unit was found.
     */
    public synchronized CompilationUnit getCompilationUnit(ClassSource src, String typeName, CompilationUnit defaultCompilationUnit) {
      if (src == ClassSource.NONE) return defaultCompilationUnit;
      try {
        final CompilationUnit u = src.parseCompilationUnit(program);

        // check if unit's declared pkg matches `typeName`'s pkg
        // if it doesn't then fail and return the default compilation unit
        int     pkgPartIdx    = typeName.lastIndexOf('.');
        if (pkgPartIdx == -1                        ) return u;

        String  pkgRequested  = typeName.substring(0, pkgPartIdx);
        if (pkgRequested.equals(u.getPackageDecl()) ) return u;

        return defaultCompilationUnit;
      } catch (IOException e) {
        // Attributes can't throw checked exceptions, so convert this to an Error.
        throw new Error(e);
      }
    }

  

    /**
     * Add a package name to available package set.
     */
    public synchronized void addPackage(String packageName) {
      int end = packageName.length();
      while (end > 0 && packages.add(packageName.substring(0, end))) {
        end = packageName.lastIndexOf('.', end - 1);
      }
    }

  

    /**
     * Add a path part to the library class path.
     */
    public synchronized void addClassPath(PathPart pathPart) {
      classPath.add(pathPart);
    }

  

    /**
     * Add a path part to the user class path.
     */
    public synchronized void addSourcePath(PathPart pathPart) {
      sourcePath.add(pathPart);
    }

  

    /**
     * Quick pass, slow fail. Cache existing package names in a concurrent set.
     * @return <code>true</code> if there is a package with the given name on
     * the classpath
     */
    public synchronized boolean isPackage(String packageName) {
      initPaths();
      if (packages.contains(packageName)) {
        return true;
      }
      for (PathPart part : classPath) {
        if (part.hasPackage(packageName)) {
          addPackage(packageName);
          return true;
        }
      }
      for (PathPart part : sourcePath) {
        if (part.hasPackage(packageName)) {
          addPackage(packageName);
          return true;
        }
      }
      return false;
    }

  

    /**
     * @return a copy of the source path parts
     */
    public synchronized Collection<PathPart> getSourcePath() {
      return new ArrayList<PathPart>(sourcePath);
    }

  

    /**
     * @return a copy of the class path parts
     */
    public synchronized Collection<PathPart> getClassPath() {
      return new ArrayList<PathPart>(classPath);
    }


}
