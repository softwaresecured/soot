/* This file was generated with JastAdd2 (http://jastadd.org) version 2.3.0-1-ge75f200 */
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
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java8/grammar/Lambda.ast:11
 * @astdecl LambdaBody : ASTNode;
 * @production LambdaBody : {@link ASTNode};

 */
public abstract class LambdaBody extends ASTNode<ASTNode> implements Cloneable {
  /**
   * Copies the method block for the lambda body.
   * @aspect LambdaToClass
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LambdaAnonymousDecl.jrag:86
   */
  protected abstract Block toBlock();
  /**
   * @declaredat ASTNode:1
   */
  public LambdaBody() {
    super();
  }
  /**
   * Initializes the child array to the correct size.
   * Initializes List and Opt nta children.
   * @apilevel internal
   * @ast method
   * @declaredat ASTNode:10
   */
  public void init$Children() {
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:13
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:19
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:23
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    enclosingLambda_reset();
    lookupVariable_String_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:29
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:33
   */
  public LambdaBody clone() throws CloneNotSupportedException {
    LambdaBody node = (LambdaBody) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:44
   */
  @Deprecated
  public abstract LambdaBody fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:52
   */
  public abstract LambdaBody treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:60
   */
  public abstract LambdaBody treeCopy();
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:43
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:43")
  public abstract boolean modifiedInScope(Variable var);
  /**
   * @attribute syn
   * @aspect LambdaBody
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LambdaBody.jrag:29
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LambdaBody", declaredAt="/home/olivier/projects/extendj/java8/frontend/LambdaBody.jrag:29")
  public abstract boolean isBlockBody();
  /**
   * @attribute syn
   * @aspect LambdaBody
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LambdaBody.jrag:30
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LambdaBody", declaredAt="/home/olivier/projects/extendj/java8/frontend/LambdaBody.jrag:30")
  public abstract boolean isExprBody();
  /**
   * @attribute syn
   * @aspect ReturnCompatible
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LambdaBody.jrag:47
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ReturnCompatible", declaredAt="/home/olivier/projects/extendj/java8/frontend/LambdaBody.jrag:47")
  public abstract Collection<TypeDecl> returnTypes();
  /**
   * @attribute syn
   * @aspect LambdaExpr
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LambdaExpr.jrag:89
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LambdaExpr", declaredAt="/home/olivier/projects/extendj/java8/frontend/LambdaExpr.jrag:89")
  public abstract boolean congruentTo(FunctionDescriptor fd);
  /**
   * @attribute inh
   * @aspect EnclosingLambda
   * @declaredat /home/olivier/projects/extendj/java8/frontend/EnclosingLambda.jrag:29
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="EnclosingLambda", declaredAt="/home/olivier/projects/extendj/java8/frontend/EnclosingLambda.jrag:29")
  public LambdaExpr enclosingLambda() {
    ASTState state = state();
    if (enclosingLambda_computed == ASTState.NON_CYCLE || enclosingLambda_computed == state().cycle()) {
      return enclosingLambda_value;
    }
    enclosingLambda_value = getParent().Define_enclosingLambda(this, null);
    if (state().inCircle()) {
      enclosingLambda_computed = state().cycle();
    
    } else {
      enclosingLambda_computed = ASTState.NON_CYCLE;
    
    }
    return enclosingLambda_value;
  }
  /** @apilevel internal */
  private void enclosingLambda_reset() {
    enclosingLambda_computed = null;
    enclosingLambda_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle enclosingLambda_computed = null;

  /** @apilevel internal */
  protected LambdaExpr enclosingLambda_value;

  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LookupVariable.jrag:30
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="VariableScope", declaredAt="/home/olivier/projects/extendj/java8/frontend/LookupVariable.jrag:30")
  public SimpleSet<Variable> lookupVariable(String name) {
    Object _parameters = name;
    if (lookupVariable_String_computed == null) lookupVariable_String_computed = new java.util.HashMap(4);
    if (lookupVariable_String_values == null) lookupVariable_String_values = new java.util.HashMap(4);
    ASTState state = state();
    if (lookupVariable_String_values.containsKey(_parameters)
        && lookupVariable_String_computed.containsKey(_parameters)
        && (lookupVariable_String_computed.get(_parameters) == ASTState.NON_CYCLE || lookupVariable_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<Variable>) lookupVariable_String_values.get(_parameters);
    }
    SimpleSet<Variable> lookupVariable_String_value = getParent().Define_lookupVariable(this, null, name);
    if (state().inCircle()) {
      lookupVariable_String_values.put(_parameters, lookupVariable_String_value);
      lookupVariable_String_computed.put(_parameters, state().cycle());
    
    } else {
      lookupVariable_String_values.put(_parameters, lookupVariable_String_value);
      lookupVariable_String_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return lookupVariable_String_value;
  }
  /** @apilevel internal */
  private void lookupVariable_String_reset() {
    lookupVariable_String_computed = null;
    lookupVariable_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map lookupVariable_String_values;
  /** @apilevel internal */
  protected java.util.Map lookupVariable_String_computed;
  /**
   * @attribute inh
   * @aspect TypeCheck
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TypeCheck.jrag:32
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="/home/olivier/projects/extendj/java8/frontend/TypeCheck.jrag:32")
  public TypeDecl unknownType() {
    TypeDecl unknownType_value = getParent().Define_unknownType(this, null);
    return unknownType_value;
  }
  /** @apilevel internal */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
  /** @apilevel internal */
  public boolean canRewrite() {
    return false;
  }
}
