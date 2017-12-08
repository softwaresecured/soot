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
 * @ast interface
 * @aspect BytecodeSignatures
 * @declaredat /home/olivier/projects/extendj/java5/frontend/BytecodeSignatures.jrag:393
 */
public interface Flags {

     
    public int ACC_PUBLIC = 0x0001;

     
    public int ACC_PRIVATE = 0x0002;

     
    public int ACC_PROTECTED = 0x0004;

     
    public int ACC_STATIC = 0x0008;

     
    public int ACC_FINAL = 0x0010;

     
    public int ACC_SUPER = 0x0020;

     
    public int ACC_SYNCHRONIZED = 0x0020;

     
    public int ACC_VOLATILE = 0x0040;

     
    public int ACC_BRIDGE = 0x0040;

     
    public int ACC_TRANSIENT = 0x0080;

     
    public int ACC_VARARGS = 0x0080;

     
    public int ACC_NATIVE = 0x0100;

     
    public int ACC_INTERFACE = 0x0200;

     
    public int ACC_ABSTRACT = 0x0400;

     
    public int ACC_STRICT = 0x0800;

     
    public int ACC_SYNTHETIC = 0x1000;

     
    public int ACC_ANNOTATION = 0x2000;

     
    public int ACC_ENUM = 0x4000;
}
