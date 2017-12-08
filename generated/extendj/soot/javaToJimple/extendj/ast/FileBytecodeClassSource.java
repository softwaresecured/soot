package soot.javaToJimple.extendj.ast;

import java.util.HashSet;
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
 * @declaredat /home/olivier/projects/extendj/java4/frontend/PathPart.jadd:231
 */
public class FileBytecodeClassSource extends BytecodeClassSource {
  
    private final String filePath;

  

    public FileBytecodeClassSource(PathPart sourcePath, String path) {
      super(sourcePath);
      this.filePath = path;
    }

  

    @Override
    public long lastModified() {
      // last modification time computed only when needed
      File file = new File(filePath);
      return file.lastModified();
    }

  

    @Override
    public InputStream openInputStream() throws IOException {
      File file = new File(filePath);
      return new FileInputStream(file);
    }

  

    @Override
    public String pathName() {
      return filePath;
    }


}
