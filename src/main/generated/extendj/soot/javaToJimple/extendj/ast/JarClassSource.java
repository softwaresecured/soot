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
 * @declaredat /home/olivier/projects/extendj/java4/frontend/PathPart.jadd:258
 */
public class JarClassSource extends BytecodeClassSource {
  
    private final ZipFile jar;

  
    private final ZipEntry entry;

  
    private final String jarPath;

  

    public JarClassSource(PathPart sourcePath, ZipFile jar, ZipEntry entry,
        String jarPath) {
      super(sourcePath);
      this.jar = jar;
      this.entry = entry;
      this.jarPath = jarPath;
    }

  

    public String jarFilePath() {
      return entry.getName();
    }

  

    @Override
    public long lastModified() {
      return entry.getTime();
    }

  

    @Override
    public InputStream openInputStream() throws IOException {
      return jar.getInputStream(entry);
    }

  

    @Override
    public String pathName() {
      return jarPath;
    }

  

    @Override
    public String relativeName() {
      return entry.getName();
    }

  

    @Override
    public String sourceName() {
      return pathName() + ":" + relativeName();
    }


}
