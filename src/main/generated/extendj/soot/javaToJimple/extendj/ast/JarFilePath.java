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
 * @declaredat /home/olivier/projects/extendj/java4/frontend/PathPart.jadd:510
 */
public class JarFilePath extends PathPart {
  
    private Collection<String> packageIndex = null;

  
    private final ZipFile jar;

  
    private final String jarPath;

  

    public JarFilePath(String jarPath) throws IOException {
      super(false);
      this.jar = new ZipFile(jarPath);
      this.jarPath = jarPath;
    }

  

    public JarFilePath(File jarFile) throws IOException {
      super(false);
      this.jar = new ZipFile(jarFile);
      this.jarPath = jarFile.getPath();
    }

  

    @Override
    public String getPath() {
      return jarPath;
    }

  

    private static void scanJar(ZipFile jar, Collection<String> packages,
        String fileSuffix) {
      // Add all zip entries to a set so that we can quickly check if the Jar
      // contains a given class.
      for (Enumeration entries = jar.entries(); entries.hasMoreElements(); ) {
        ZipEntry entry = (ZipEntry) entries.nextElement();
        String path = entry.getName();
        if (path.endsWith(fileSuffix)) {
          addPackages(packages, path);
        }
      }
    }

  

    private static void addPackages(Collection<String> packages, String path) {
      String name = path.replace('/', '.');
      int index = path.length();
      do {
        index = path.lastIndexOf('/', index-1);
      } while (index >= 0 && packages.add(name.substring(0, index)));
    }

  

    /**
     * Caches the package index from the Jar file so that subsequent calls to
     * this method are quicker.
     */
    @Override
    public boolean hasPackage(String name) {
      synchronized (this) {
        if (packageIndex == null) {
          packageIndex = new HashSet<String>();
          scanJar(jar, packageIndex, fileSuffix);
        }
      }
      return packageIndex.contains(name);
    }

  

    @Override
    public ClassSource findSource(String name) {
      // ZipFiles always use '/' as separator
      String jarName = name.replace('.', '/') + fileSuffix;
      ZipEntry entry = jar.getEntry(jarName);
      if (entry != null) {
        return new JarClassSource(this, jar, entry, jarPath);
      } else {
        return ClassSource.NONE;
      }
    }

  

    @Override
    public String toString() {
      return "jar:" + jarPath;
    }


}
