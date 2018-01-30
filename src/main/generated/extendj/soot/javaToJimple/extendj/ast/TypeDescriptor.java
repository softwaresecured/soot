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
 * @aspect BytecodeDescriptor
 * @declaredat /home/olivier/projects/extendj/java5/frontend/BytecodeDescriptor.jrag:289
 */
 class TypeDescriptor extends java.lang.Object {
  
    private AbstractClassfileParser p;

  
    private String descriptor;

  

    public TypeDescriptor(AbstractClassfileParser parser, String descriptor) {
      p = parser;
      this.descriptor = descriptor;
    }

  

    public boolean isBoolean() {
      return descriptor.charAt(0) == 'Z';
    }

  

    public Access type() {
      return type(descriptor);
    }

  

    public Access type(String s) {
      char c = s.charAt(0);
      switch (c) {
        case 'B':
          return new PrimitiveTypeAccess("byte");
        case 'C':
          return new PrimitiveTypeAccess("char");
        case 'D':
          return new PrimitiveTypeAccess("double");
        case 'F':
          return new PrimitiveTypeAccess("float");
        case 'I':
          return new PrimitiveTypeAccess("int");
        case 'J':
          return new PrimitiveTypeAccess("long");
        case 'S':
          return new PrimitiveTypeAccess("short");
        case 'Z':
          return new PrimitiveTypeAccess("boolean");
        case 'L':
          return BytecodeParser.fromClassName(s.substring(1, s.length() - 1));
        case '[':
          return new ArrayTypeAccess(type(s.substring(1)));
        case 'V':
          return new PrimitiveTypeAccess("void");
        default:
          throw new Error("Error: unknown Type in TypeDescriptor: " + s);
      }
    }

  

    public List parameterList() {
      List list = new List();
      String s = descriptor;
      while (!s.equals("")) {
        s = typeList(s, list);
      }
      return list;
    }

  
    public List parameterListSkipFirst() {
      List list = new List();
      String s = descriptor;
      if (!s.equals("")) {
        s = typeList(s, new List()); // skip first
      }
      while (!s.equals("")) {
        s = typeList(s, list);
      }
      return list;
    }

  

    public String typeList(String s, List l) {
      char c = s.charAt(0);
      switch (c) {
        case 'B':
          l.add(new ParameterDeclaration(new Modifiers(),
                new PrimitiveTypeAccess("byte"), "p" + l.getNumChildNoTransform()));
          return s.substring(1);
        case 'C':
          l.add(new ParameterDeclaration(new Modifiers(),
                new PrimitiveTypeAccess("char"), "p" + l.getNumChildNoTransform()));
          return s.substring(1);
        case 'D':
          l.add(new ParameterDeclaration(new Modifiers(),
                new PrimitiveTypeAccess("double"), "p" + l.getNumChildNoTransform()));
          return s.substring(1);
        case 'F':
          l.add(new ParameterDeclaration(new Modifiers(),
                new PrimitiveTypeAccess("float"), "p" + l.getNumChildNoTransform()));
          return s.substring(1);
        case 'I':
          l.add(new ParameterDeclaration(new Modifiers(),
                new PrimitiveTypeAccess("int"), "p" + l.getNumChildNoTransform()));
          return s.substring(1);
        case 'J':
          l.add(new ParameterDeclaration(new Modifiers(),
                new PrimitiveTypeAccess("long"), "p" + l.getNumChildNoTransform()));
          return s.substring(1);
        case 'S':
          l.add(new ParameterDeclaration(new Modifiers(),
                new PrimitiveTypeAccess("short"), "p" + l.getNumChildNoTransform()));
          return s.substring(1);
        case 'Z':
          l.add(new ParameterDeclaration(new Modifiers(),
                new PrimitiveTypeAccess("boolean"), "p" + l.getNumChildNoTransform()));
          return s.substring(1);
        case 'L':
          int pos = s.indexOf(';');
          String s1 = s.substring(1, pos);
          String s2 = s.substring(pos + 1, s.length());
          l.add(new ParameterDeclaration(new Modifiers(),
                BytecodeParser.fromClassName(s1),
                "p" + l.getNumChildNoTransform()));
          return s2;
        case '[':
          int i = 1;
          while (s.charAt(i) == '[') {
            i++;
          }
          // Dummy name is replaced later.
          ArrayTypeAccess bottom = new ArrayTypeAccess(new ParseName());
          ArrayTypeAccess top = bottom;
          for (int j = 0; j < i - 1; j++) {
            top = new ArrayTypeAccess(top);
          }
          l.add(new ParameterDeclaration(new Modifiers(), top, "p" + l.getNumChild()));
          return arrayTypeList(s.substring(i), bottom);
        default:
          throw new Error("Error: unknown Type in TypeDescriptor: " + s);
      }

    }

  

    public String arrayTypeList(String s, ArrayTypeAccess typeAccess) {
      char c = s.charAt(0);
      switch (c) {
        case 'B':
          typeAccess.setAccess(new PrimitiveTypeAccess("byte"));
          return s.substring(1);
        case 'C':
          typeAccess.setAccess(new PrimitiveTypeAccess("char"));
          return s.substring(1);
        case 'D':
          typeAccess.setAccess(new PrimitiveTypeAccess("double"));
          return s.substring(1);
        case 'F':
          typeAccess.setAccess(new PrimitiveTypeAccess("float"));
          return s.substring(1);
        case 'I':
          typeAccess.setAccess(new PrimitiveTypeAccess("int"));
          return s.substring(1);
        case 'J':
          typeAccess.setAccess(new PrimitiveTypeAccess("long"));
          return s.substring(1);
        case 'S':
          typeAccess.setAccess(new PrimitiveTypeAccess("short"));
          return s.substring(1);
        case 'Z':
          typeAccess.setAccess(new PrimitiveTypeAccess("boolean"));
          return s.substring(1);
        case 'L':
          int pos = s.indexOf(';');
          String s1 = s.substring(1, pos);
          String s2 = s.substring(pos + 1, s.length());
          typeAccess.setAccess(BytecodeParser.fromClassName(s1));
          return s2;
        default:
          throw new Error("Error: unknown Type in TypeDescriptor: " + s);
      }
    }


}
