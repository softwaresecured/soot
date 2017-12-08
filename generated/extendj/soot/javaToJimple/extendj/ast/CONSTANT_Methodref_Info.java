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
 * @declaredat /home/olivier/projects/extendj/java4/frontend/BytecodeCONSTANT.jrag:230
 */
 class CONSTANT_Methodref_Info extends CONSTANT_Info {
  
    public int class_index;

  
    public int name_and_type_index;

  

    public CONSTANT_Methodref_Info(AbstractClassfileParser parser) throws IOException {
      super(parser);
      class_index = p.u2();
      name_and_type_index = p.u2();
    }

  

    @Override
    public String toString() {
      return "MethodRefInfo: " + class_index + " " + name_and_type_index;
    }


}
