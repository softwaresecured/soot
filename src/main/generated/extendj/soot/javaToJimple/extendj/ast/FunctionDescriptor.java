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
 * @aspect FunctionDescriptor
 * @declaredat /home/olivier/projects/extendj/java8/frontend/FunctionDescriptor.jrag:37
 */
 class FunctionDescriptor extends java.lang.Object {
  
    public final java.util.List<TypeDecl> throwsList;

  
    public final Option<? extends MethodDecl> method;

  
    public final InterfaceDecl fromInterface;

  

    public FunctionDescriptor(InterfaceDecl fromInterface, MethodDecl method,
        java.util.List<TypeDecl> throwsList) {
      this.fromInterface = fromInterface;
      this.method = method.nonWildcardParameterization();
      this.throwsList = throwsList;
    }

  

    public boolean isGeneric() {
      if (method.hasValue()) {
        return method.get().isGeneric();
      } else {
        return false;
      }
    }

  

    public InterfaceDecl fromInterface() {
      return this.fromInterface;
    }

  

    public String toString() {
      if (method.hasValue()) {
        MethodDecl targetMethod = method.get();
        StringBuilder str = new StringBuilder();
        str.append(targetMethod.toString());
        str.append(" throws ");
        if (throwsList.size() > 0) {
          str.append(throwsList.get(0).typeName());
          for (int i = 1; i < throwsList.size(); i++) {
            str.append(", " + throwsList.get(i).toString());
          }
        }
        return str.toString();
      } else {
        return "<unknown>";
      }
    }


}
