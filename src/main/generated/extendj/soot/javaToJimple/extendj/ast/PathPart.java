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
 * @aspect PathPart
 * @declaredat /home/olivier/projects/extendj/java4/frontend/PathPart.jadd:304
 */
public abstract class PathPart extends java.lang.Object {
  
    /**
     * This is {@code true} if this path part represents a source path, {@code
     * true} if this path part represents a bytecode class path.
     */
    protected final boolean isSource;

  

    /**
     * The file suffix of source files in this path part.
     */
    protected final String fileSuffix;

  

    protected PathPart(boolean isSource) {
      this.isSource = isSource;
      this.fileSuffix = isSource ? ".java" : ".class";
    }

  

    /**
     * @return the path which this path part represents
     */
    abstract public String getPath();

  

    /**
     * Test if a package is available in this path part.
     *
     * <p>The implementations of this method may use caching to improve the
     * efficiency of subsequent calls to the method.
     *
     * @return {@code true} if the given package name exists in this path part
     */
    abstract public boolean hasPackage(String name);

  

    public static PathPart createSourcePath(String fileName) {
      return createPathPart(fileName, true);
    }

  

    public static PathPart createClassPath(String fileName) {
      return createPathPart(fileName, false);
    }

  

    private static PathPart createPathPart(String path, boolean fromSource) {
      try {
        File file = new File(path);
        if (file.isDirectory()) {
          if (fromSource) {
            return new SourceFolderPath(path);
          } else {
            return new ClassFolderPath(path);
          }
        } else if (file.isFile()) {
          return new JarFilePath(path);
        }
      } catch (IOException e) {
        System.err.println("Warning: can not open class path " + path);
      }
      return null;
    }

  

    /**
     * Retrieves a compilation unit based on the canonical name.
     * @param name the canonical name of the class to lookup
     * @return {@code null} if a compilation unit with the given name is not
     * available in this PathPart.
     */
    public ClassSource findSource(String name) {
      return ClassSource.NONE;
    }

  

    /**
     * Load the compilation unit of a class.
     * @param program
     * @param name The canonical name of the class.
     */
    public CompilationUnit getCompilationUnit(Program program, String name) throws IOException {
      ClassSource source = findSource(name);
      if (source == ClassSource.NONE) {
        throw new IOException(String.format("%s file not found: %s",
              isSource ? "Source" : "Class", name));
      }
      return source.parseCompilationUnit(program);
    }


}
