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
 * @aspect EmitJimple
 * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:100
 */
 class ClassInitMethodWrapper extends java.lang.Object implements MethodLikeDecl<ClassInitMethodWrapper> {
  
    final TypeDecl typeDecl;

  
    ClassInitMethodWrapper(TypeDecl d) { typeDecl = d.erasure(); }

  

    public int flags()         { return soot.Modifier.STATIC; }

  
    public String name()          { return "<clinit>"; }

  
    public TypeDecl type()          { return typeDecl.typeVoid(); }

  
    public TypeDecl hostType()      { return typeDecl; }

  
    public Iterable<Access               > getExceptions() { return Collections.emptyList(); }

  
    public Iterable<ParameterDeclaration > getParameters() { return Collections.emptyList(); }

  

    public ClassInitMethodWrapper typeErased() { return this; }

  
    public JimpleBody jimpleBody() {
      Body b = new Body(typeDecl);

      for (BodyDecl bodyDecl : typeDecl.clinitBodies()) {
        if (bodyDecl instanceof FieldDecl) {
          for (FieldDeclarator f : ((FieldDecl)bodyDecl).getDeclarators()) {
            if (!f.isStatic()) continue;
            if (!f.hasInit ()) continue;

            // b.setLine(f);
            Value expr = f.getInit().evalAndCast(b, f.type()); // AssignConversion
            b.add(b.newAssignStmt(
              b.newStaticFieldRef(f.sootRef(), f),
              expr,
              f));
          }
        }

        if (bodyDecl instanceof StaticInitializer)
          bodyDecl.jimpleEmit(b);
      }

      b.add(b.newReturnVoidStmt_noloc());
      return b.finishBody(this);
    }


}
