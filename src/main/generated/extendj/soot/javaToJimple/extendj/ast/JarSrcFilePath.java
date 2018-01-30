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
 * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:67
 */
 class JarSrcFilePath extends PathPart {
  
    private Collection<String> packageIndex = null;

  
    private final ZipFile jar;

  
    private final String jarPath;

  

    public JarSrcFilePath(String jarPath) throws IOException {
      super(true);
      this.jar = new ZipFile(jarPath);
      this.jarPath = jarPath;
    }

  

    @Override
    public String getPath() { return jarPath; }

  

    /**
      * Caches the package index from the Jar file so that subsequent calls to
      * this method are quicker.
      */
    @Override
    public boolean hasPackage(String name) {
      synchronized (this) {
        if (packageIndex == null) {
          packageIndex = new HashSet<String>();

          // Add all zip entries to a set so that we can quickly check if the Jar
          // contains a given class.
          for (Enumeration entries = jar.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String path = entry.getName();
            if (path.endsWith(fileSuffix)) {
              String name_ = path.replace('/', '.');
              int index = path.length();
              do {
                index = path.lastIndexOf('/', index-1);
              } while (index >= 0 && packageIndex.add(name_.substring(0, index)));
            }
          }
        }
      }
      return packageIndex.contains(name);
    }

  

    @Override
    public ClassSource findSource(String name) {
      // ZipFiles always use '/' as separator
      ZipEntry entry = jar.getEntry(name.replace('.', '/') + fileSuffix);
      if (entry == null) return ClassSource.NONE;

      return new ClassSource(this) {
        @Override
        public long lastModified() { return entry.getTime(); }
        @Override
        public InputStream openInputStream() throws IOException
        { return jar.getInputStream(entry); }
        @Override
        public String pathName() { return jarPath; }
      };
    }

  

    @Override
    public String toString() { return "jar:" + jarPath; }


}
