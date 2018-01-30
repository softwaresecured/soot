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
 * @aspect BytecodeCONSTANT
 * @declaredat /home/olivier/projects/extendj/java4/frontend/BytecodeCONSTANT.jrag:282
 */
 class CONSTANT_Utf8_Info extends CONSTANT_Info {
  
    public String string;

  

    public CONSTANT_Utf8_Info(AbstractClassfileParser parser) throws IOException {
      super(parser);
      string = p.readUTF();
    }

  

    @Override
    public String toString() {
      return "Utf8Info: " + string;
    }

  

    @Override
    public Expr expr() {
      return Literal.buildStringLiteral(string);
    }

  

    public String string() {
      return string;
    }


}
