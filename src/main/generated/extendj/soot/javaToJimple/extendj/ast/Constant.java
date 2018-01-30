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
 * @aspect Constant
 * @declaredat /home/olivier/projects/extendj/java4/frontend/Constant.jadd:33
 */
public class Constant extends java.lang.Object {
  
    static class ConstantInt extends Constant {
      private int value;
      public ConstantInt(int i) { this.value = i; }
      @Override public int intValue() { return value; }
      @Override public long longValue() { return value; }
      @Override public float floatValue() { return value; }
      @Override public double doubleValue() { return value; }
      @Override public String stringValue() { return new Integer(value).toString(); }
    }

  
    static class ConstantLong extends Constant {
      private long value;
      public ConstantLong(long l) { this.value = l; }
      @Override public int intValue() { return (int)value; }
      @Override public long longValue() { return value; }
      @Override public float floatValue() { return value; }
      @Override public double doubleValue() { return value; }
      @Override public String stringValue() { return new Long(value).toString(); }
    }

  
    static class ConstantFloat extends Constant {
      private float value;
      public ConstantFloat(float f) { this.value = f; }
      @Override public int intValue() { return (int)value; }
      @Override public long longValue() { return (long)value; }
      @Override public float floatValue() { return value; }
      @Override public double doubleValue() { return value; }
      @Override public String stringValue() { return new Float(value).toString(); }
    }

  
    static class ConstantDouble extends Constant {
      private double value;
      public ConstantDouble(double d) { this.value = d; }
      @Override public int intValue() { return (int)value; }
      @Override public long longValue() { return (long)value; }
      @Override public float floatValue() { return (float)value; }
      @Override public double doubleValue() { return value; }
      @Override public String stringValue() { return new Double(value).toString(); }
    }

  
    static class ConstantChar extends Constant {
      private char value;
      public ConstantChar(char c) { this.value = c; }
      @Override public int intValue() { return value; }
      @Override public long longValue() { return value; }
      @Override public float floatValue() { return value; }
      @Override public double doubleValue() { return value; }
      @Override public String stringValue() { return new Character(value).toString(); }
    }

  
    static class ConstantBoolean extends Constant {
      private boolean value;
      public ConstantBoolean(boolean b) { this.value = b; }
      @Override public boolean booleanValue() { return value; }
      @Override public String stringValue() { return new Boolean(value).toString(); }
    }

  
    static class ConstantString extends Constant {
      private String value;
      public ConstantString(String s) { this.value = s; }
      @Override public String stringValue() { return value; }
    }

  

    public int intValue() { throw new UnsupportedOperationException(); }

  
    public long longValue() { throw new UnsupportedOperationException(); }

  
    public float floatValue() { throw new UnsupportedOperationException(); }

  
    public double doubleValue() { throw new UnsupportedOperationException(); }

  
    public boolean booleanValue() { throw new UnsupportedOperationException(getClass().getName()); }

  
    public String stringValue() { throw new UnsupportedOperationException(); }

  

    protected Constant() { }

  

    public boolean error = false;

  

    static Constant create(int i) { return new ConstantInt(i); }

  
    static Constant create(long l) { return new ConstantLong(l); }

  
    static Constant create(float f) { return new ConstantFloat(f); }

  
    static Constant create(double d) { return new ConstantDouble(d); }

  
    static Constant create(boolean b) { return new ConstantBoolean(b); }

  
    static Constant create(char c) { return new ConstantChar(c); }

  
    static Constant create(String s) { return new ConstantString(s); }


}
