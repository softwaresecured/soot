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
 * @declaredat /home/olivier/projects/extendj/java4/frontend/PathPart.jadd:590
 */
public class SourceFilePath extends PathPart {
  
    private final String filePath;

  

    public SourceFilePath(String path) {
      super(true);
      this.filePath = path;
    }

  

    @Override
    public String getPath() {
      return filePath;
    }

  

    /**
     * <b>Use the parsed CompilationUnit to find the package name of the file!</b>
     */
    @Override
    public boolean hasPackage(String name) {
      return false;
    }

  

    @Override
    public ClassSource findSource(String name) {
      if (filePath.equals(name)) {
        File file = new File(filePath);
        if (file.isFile() && file.canRead()) {
          return new FileClassSource(this, filePath);
        }
      }
      return ClassSource.NONE;
    }

  

    @Override
    public String toString() {
      return filePath;
    }


}
