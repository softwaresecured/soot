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
 * @aspect BytecodeCONSTANT
 * @declaredat /home/olivier/projects/extendj/java4/frontend/BytecodeCONSTANT.jrag:32
 */
 class CONSTANT_Class_Info extends CONSTANT_Info {
  
    public int name_index;

  

    public CONSTANT_Class_Info(AbstractClassfileParser parser) throws IOException {
      super(parser);
      name_index = p.u2();
    }

  

    @Override
    public String toString() {
      return "ClassInfo: " + name();
    }

  

    public String name() {
      String name = ((CONSTANT_Utf8_Info) p.constantPool[name_index]).string();
      name = name.replace('/', '.');
      return name;
    }

  

    public String simpleName() {
      String name = name();
      int pos = name.lastIndexOf('.');
      return name.substring(pos + 1, name.length());
    }

  

    public String packageDecl() {
      String name = name();
      int pos = name.lastIndexOf('.');
      if (pos == -1) {
        return "";
      }
      return name.substring(0, pos);
    }

  

    public Access access() {
      String name = name();
      int pos = name.lastIndexOf('.');
      String typeName = name.substring(pos + 1, name.length());
      String packageName = pos == -1 ? "" : name.substring(0, pos);
      if (typeName.indexOf('$') != -1) {
        return new BytecodeTypeAccess(packageName, typeName);
      } else {
        return new TypeAccess(packageName, typeName);
      }
    }


}
