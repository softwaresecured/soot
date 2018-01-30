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
 * @aspect BytecodeReader
 * @declaredat /home/olivier/projects/extendj/java8/frontend/BytecodeReader.jrag:35
 */
public class BytecodeParser extends AbstractClassfileParser implements Flags {
  

    public String outerClassName;

  

    public BytecodeParser(InputStream in, String name) {
      super(in, name);
    }

  

    @Override
    public boolean outerClassNameEquals(String name) {
      return outerClassName != null && outerClassName.equals(name);
    }

  

    public CompilationUnit parse(TypeDecl outerTypeDecl, String outerClassName,
        Program classPath, boolean isInner)
        throws FileNotFoundException, IOException {
      isInnerClass = isInner;
      return parse(outerTypeDecl, outerClassName, classPath);
    }

  

    public CompilationUnit parse(TypeDecl outerTypeDecl, String outerClassName, Program program)
        throws FileNotFoundException, IOException {

      if (VERBOSE) {
        println("Parsing byte codes in " + name);
      }

      try {
        this.outerClassName = outerClassName;
        parseMagic();
        int minor = parseMinor();
        int major = parseMajor();
        if (AbstractClassfileParser.VERBOSE) {
          println(String.format("Classfile version: %d.%d", major, minor));
        }
        if (major > 52) {
          error(String.format("Can not parse classfile version %d.%d."
                + " Classfile versions up to 52.x (Java 8) are supported by"
                + " this version of the compiler.",
                major, minor));
        }
        parseConstantPool();
        CompilationUnit cu = new CompilationUnit();
        TypeDecl typeDecl = parseTypeDecl();
        cu.setPackageDecl(classInfo.packageDecl());
        cu.addTypeDecl(typeDecl);
        parseFields(typeDecl);
        parseMethods(typeDecl);
        new Attributes.TypeAttributes(this, typeDecl, outerTypeDecl, program);
        return cu;
      } catch (Error e) {
        throw new IOException("Failed to parse classfile: " + name, e);
      }
    }

  

    public TypeDecl parseTypeDecl() throws IOException {
      int flags = u2();
      Modifiers modifiers = modifiers(flags & 0xfddf);
      if ((flags & (ACC_INTERFACE | ACC_ENUM)) == ACC_ENUM) {
        // Modifiers <ID:String> /[SuperClass:Access]/ Implements:Access* BodyDecl*;
        EnumDecl decl = new EnumDecl();
        decl.setModifiers(modifiers);
        decl.setID(parseThisClass());
        Access superClass = parseSuperClass();
        // TODO trying to overwrite NTA?
        decl.setImplementsList(parseInterfaces(new List()));
        return decl;
      } else if ((flags & ACC_INTERFACE) == 0) {
        ClassDecl decl = new ClassDecl();
        decl.setModifiers(modifiers);
        decl.setID(parseThisClass());
        Access superClass = parseSuperClass();
        decl.setSuperClassOpt(
            superClass == null
            ? new Opt()
            : new Opt(superClass));
        decl.setImplementsList(parseInterfaces(new List()));
        return decl;
      } else if ((flags & ACC_ANNOTATION) == 0) {
        InterfaceDecl decl = new InterfaceDecl();
        decl.setModifiers(modifiers);
        decl.setID(parseThisClass());
        Access superClass = parseSuperClass();
        decl.setSuperInterfaceList(parseInterfaces(
            superClass == null
            ? new List()
            : new List().add(superClass)));
        return decl;
      } else {
        AnnotationDecl decl = new AnnotationDecl();
        decl.setModifiers(modifiers);
        decl.setID(parseThisClass());
        Access superClass = parseSuperClass();
        parseInterfaces(
            superClass == null
            ? new List()
            : new List().add(superClass));
        return decl;
      }
    }

  

    public static Access fromClassName(String s) {
      // Sample ClassName: a/b/c$d$e
      // the package name ends at the last '/'
      // after that follows a list of type names separated by '$'
      // all except the first are nested types

      String packageName = "";
      int index = s.lastIndexOf('/');
      if (index != -1) {
        packageName = s.substring(0, index).replace('/', '.');
      }
      String typeName = s.substring(index + 1, s.length());
      if (typeName.indexOf('$') != -1) {
        return new BytecodeTypeAccess(packageName, typeName);
      } else {
        return new TypeAccess(packageName, typeName);
      }
    }

  

    public void parseMethods(TypeDecl typeDecl) throws IOException {
      int count = u2();
      if (VERBOSE) {
        println("Methods (" + count + "):");
      }
      for (int i = 0; i < count; i++) {
        if (VERBOSE) {
          print("  Method nbr " + i + " ");
        }
        MethodInfo info = new MethodInfo(this);
        if (!info.isSynthetic() && !info.name.equals("<clinit>")) {
          BodyDecl bodyDecl = info.bodyDecl();
          if (bodyDecl instanceof MethodDecl && typeDecl instanceof InterfaceDecl) {
            MethodDecl methodDecl = (MethodDecl) bodyDecl;
            boolean foundAbstract = false;
            boolean foundStatic = false;
            for (int j = 0; j < methodDecl.getModifiers().getNumModifier(); j++) {
              Modifier modifier = methodDecl.getModifiers().getModifier(j);
              if (modifier.getID().equals("abstract")) {
                foundAbstract = true;
              } else if (modifier.getID().equals("static")) {
                foundStatic = true;
              }
            }
            if (!foundAbstract && !foundStatic) {
              methodDecl.getModifiers().addModifier(new Modifier("default"));
            }
          }
          typeDecl.addBodyDecl(bodyDecl);
        }
      }
    }

  

    @Override
    public void parseConstantPoolEntry(int i) throws IOException {
      int tag = u1();
      switch (tag) {
        case CONSTANT_Class:
          constantPool[i] = new CONSTANT_Class_Info(this);
          break;
        case CONSTANT_FieldRef:
          constantPool[i] = new CONSTANT_Fieldref_Info(this);
          break;
        case CONSTANT_MethodRef:
          constantPool[i] = new CONSTANT_Methodref_Info(this);
          break;
        case CONSTANT_InterfaceMethodRef:
          constantPool[i] = new CONSTANT_InterfaceMethodref_Info(this);
          break;
        case CONSTANT_String:
          constantPool[i] = new CONSTANT_String_Info(this);
          break;
        case CONSTANT_Integer:
          constantPool[i] = new CONSTANT_Integer_Info(this);
          break;
        case CONSTANT_Float:
          constantPool[i] = new CONSTANT_Float_Info(this);
          break;
        case CONSTANT_Long:
          constantPool[i] = new CONSTANT_Long_Info(this);
          break;
        case CONSTANT_Double:
          constantPool[i] = new CONSTANT_Double_Info(this);
          break;
        case CONSTANT_NameAndType:
          constantPool[i] = new CONSTANT_NameAndType_Info(this);
          break;
        case CONSTANT_Utf8:
          constantPool[i] = new CONSTANT_Utf8_Info(this);
          break;
        case 15:
          u1();
          u2();
          break;
        case 16:
          u2();
          break;
        case 18:
          u2();
          u2();
        default:
          println("Unknown entry: " + tag);
      }
    }


}
