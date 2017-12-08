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
 * @aspect InnerClasses
 * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:495
 */
 class ConstructorSignatureMapper extends java.lang.Object {
  
    public final ConstructorDecl decl;

  

    public ConstructorSignatureMapper(ConstructorDecl decl) {
      this.decl = decl;
    }

  

    @Override
    public int hashCode() {
      return decl.signature().hashCode();
    }

  

    @Override
    public boolean equals(Object o) {
      if (o instanceof ConstructorSignatureMapper) {
        ConstructorSignatureMapper other = (ConstructorSignatureMapper) o;
        return decl.signature().equals(other.decl.signature());
      }
      return false;
    }


}
