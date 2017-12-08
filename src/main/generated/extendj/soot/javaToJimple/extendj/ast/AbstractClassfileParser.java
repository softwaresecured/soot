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
 * @aspect ClassfileParser
 * @declaredat /home/olivier/projects/extendj/java4/frontend/ClassfileParser.jrag:34
 */
public abstract class AbstractClassfileParser extends java.lang.Object {
  

    public static class ClassfileFormatError extends Error {
      public ClassfileFormatError(String message) {
        super(message);
      }
    }

  

    public static final boolean VERBOSE = false;

  

    protected static final int CONSTANT_Class = 7;

  
    protected static final int CONSTANT_FieldRef = 9;

  
    protected static final int CONSTANT_MethodRef = 10;

  
    protected static final int CONSTANT_InterfaceMethodRef = 11;

  
    protected static final int CONSTANT_String = 8;

  
    protected static final int CONSTANT_Integer = 3;

  
    protected static final int CONSTANT_Float = 4;

  
    protected static final int CONSTANT_Long = 5;

  
    protected static final int CONSTANT_Double = 6;

  
    protected static final int CONSTANT_NameAndType = 12;

  
    protected static final int CONSTANT_Utf8 = 1;

  

    private DataInputStream is;

  

    public final String name;

  
    public CONSTANT_Class_Info classInfo;

  
    public CONSTANT_Info[] constantPool = null;

  

    // For Java 5 and later.
    public boolean isInnerClass = false;

  

    public AbstractClassfileParser(InputStream in, String name) {
      this.is = new DataInputStream(new BufferedInputStream(in));
      this.name = name;
    }

  

    public abstract boolean outerClassNameEquals(String name);

  

    public final int next() throws IOException {
      return is.read();
    }

  

    public final int u1() throws IOException {
      return is.readUnsignedByte();
    }

  

    public final int u2() throws IOException {
      return is.readUnsignedShort();
    }

  

    public final int u4() throws IOException {
      return is.readInt();
    }

  

    public final int readInt() throws IOException {
      return is.readInt();
    }

  

    public final float readFloat() throws IOException {
      return is.readFloat();
    }

  

    public final long readLong() throws IOException {
      return is.readLong();
    }

  

    public final double readDouble() throws IOException {
      return is.readDouble();
    }

  

    public final String readUTF() throws IOException {
      return is.readUTF();
    }

  

    public final void skip(int length) throws IOException {
      while (length > 0) {
        length -= is.skip(length);
      }
    }

  

    public void error(String msg) throws ClassfileFormatError {
      throw new ClassfileFormatError(msg);
    }

  

    public final void print(String s) {
    }

  

    public final void println(String s) {
      print(s + "\n");
    }

  

    public final void println() {
      print("\n");
    }

  

    public final void format(String fmt, Object... args) {
      print(String.format(fmt, args));
    }

  

    public final void parseMagic() throws IOException {
      if (next() != 0xca || next() != 0xfe || next() != 0xba || next() != 0xbe) {
        error("magic error");
      }
    }

  

    public final int parseMinor() throws IOException {
      return u2();
    }

  

    public final int parseMajor() throws IOException {
      return u2();
    }

  

    public final String parseThisClass() throws IOException {
      int index = u2();
      CONSTANT_Class_Info info = (CONSTANT_Class_Info) constantPool[index];
      classInfo = info;
      return info.simpleName();
    }

  

    public final Access parseSuperClass() throws IOException {
      int index = u2();
      if (index == 0) {
        return null;
      }
      CONSTANT_Class_Info info = (CONSTANT_Class_Info) constantPool[index];
      return info.access();
    }

  

    public final List parseInterfaces(List list) throws IOException {
      int count = u2();
      for (int i = 0; i < count; i++) {
        CONSTANT_Class_Info info = (CONSTANT_Class_Info) constantPool[u2()];
        list.add(info.access());
      }
      return list;
    }

  

    public void parseFields(TypeDecl typeDecl) throws IOException {
      int count = u2();
      if (VERBOSE) {
        println("Fields (" + count + "):");
      }
      for (int i = 0; i < count; i++) {
        if (VERBOSE) {
          print(" Field nbr " + i + " ");
        }
        FieldInfo fieldInfo = new FieldInfo(this);
        if (!fieldInfo.isSynthetic()) {
          typeDecl.addBodyDecl(fieldInfo.bodyDecl());
        }
      }
    }

  

    public static Modifiers modifiers(int flags) {
      Modifiers m = new Modifiers();
      if ((flags & 0x0001) != 0) {
        m.addModifier(new Modifier("public"));
      }
      if ((flags & 0x0002) != 0) {
        m.addModifier(new Modifier("private"));
      }
      if ((flags & 0x0004) != 0) {
        m.addModifier(new Modifier("protected"));
      }
      if ((flags & 0x0008) != 0) {
        m.addModifier(new Modifier("static"));
      }
      if ((flags & 0x0010) != 0) {
        m.addModifier(new Modifier("final"));
      }
      if ((flags & 0x0020) != 0) {
        m.addModifier(new Modifier("synchronized"));
      }
      if ((flags & 0x0040) != 0) {
        m.addModifier(new Modifier("volatile"));
      }
      if ((flags & 0x0080) != 0) {
        m.addModifier(new Modifier("transient"));
      }
      if ((flags & 0x0100) != 0) {
        m.addModifier(new Modifier("native"));
      }
      if ((flags & 0x0400) != 0) {
        m.addModifier(new Modifier("abstract"));
      }
      if ((flags & 0x0800) != 0) {
        m.addModifier(new Modifier("strictfp"));
      }
      return m;
    }

  

    private void checkLengthAndNull(int index) {
      if (index >= constantPool.length) {
        throw new Error("Trying to access element " + index  + " in constant pool of length "
            + constantPool.length);
      }
      if (constantPool[index] == null) {
        throw new Error("Unexpected null element in constant pool at index " + index);
      }
    }

  

    public boolean validConstantPoolIndex(int index) {
      return index < constantPool.length && constantPool[index] != null;
    }

  

    public CONSTANT_Info getCONSTANT_Info(int index) {
      checkLengthAndNull(index);
      return constantPool[index];
    }

  

    public CONSTANT_Utf8_Info getCONSTANT_Utf8_Info(int index) {
      checkLengthAndNull(index);
      CONSTANT_Info info = constantPool[index];
      if (!(info instanceof CONSTANT_Utf8_Info)) {
        throw new Error("Expected CONSTANT_Utf8_info at " + index + " in constant pool but found "
            + info.getClass().getName());
      }
      return (CONSTANT_Utf8_Info) info;
    }

  

    public CONSTANT_Class_Info getCONSTANT_Class_Info(int index) {
      checkLengthAndNull(index);
      CONSTANT_Info info = constantPool[index];
      if (!(info instanceof CONSTANT_Class_Info)) {
        throw new Error("Expected CONSTANT_Class_info at " + index + " in constant pool but found "
            + info.getClass().getName());
      }
      return (CONSTANT_Class_Info) info;
    }

  

    public void parseConstantPool() throws IOException {
      int count = u2();
      if (VERBOSE) {
        println("constant_pool_count: " + count);
      }
      constantPool = new CONSTANT_Info[count + 1];
      for (int i = 1; i < count; i++) {
        parseConstantPoolEntry(i);
        if (constantPool[i] instanceof CONSTANT_Long_Info
            || constantPool[i] instanceof CONSTANT_Double_Info) {
          i++;
        }
      }
    }

  

    protected abstract void parseConstantPoolEntry(int i) throws IOException;


}
