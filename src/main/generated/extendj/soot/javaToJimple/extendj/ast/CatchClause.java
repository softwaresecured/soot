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
 * Abstract superclass for catch clauses.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java7/grammar/MultiCatch.ast:4
 * @astdecl CatchClause : ASTNode ::= Block;
 * @production CatchClause : {@link ASTNode} ::= <span class="component">{@link Block}</span>;

 */
public abstract class CatchClause extends ASTNode<ASTNode> implements Cloneable, VariableScope {
  /**
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:433
   */
  public abstract <T extends ASTNode & Variable> T getParameter();
  /**
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:438
   */
  public void jimpleEmit(Body b) {
    b.addLabel(label_catch(b));
    getBlock().jimpleEmit(b);
  }
  /**
   * @declaredat ASTNode:1
   */
  public CatchClause() {
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
    children = new ASTNode[1];
  }
  /**
   * @declaredat ASTNode:13
   */
  @ASTNodeAnnotation.Constructor(
    name = {"Block"},
    type = {"Block"},
    kind = {"Child"}
  )
  public CatchClause(Block p0) {
    setChild(p0, 0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:22
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:28
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:32
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    parameterDeclaration_String_reset();
    label_catch_Body_reset();
    typeThrowable_reset();
    lookupVariable_String_reset();
    reachableCatchClause_TypeDecl_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:41
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:45
   */
  public CatchClause clone() throws CloneNotSupportedException {
    CatchClause node = (CatchClause) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:56
   */
  @Deprecated
  public abstract CatchClause fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:64
   */
  public abstract CatchClause treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:72
   */
  public abstract CatchClause treeCopy();
  /**
   * Replaces the Block child.
   * @param node The new node to replace the Block child.
   * @apilevel high-level
   */
  public void setBlock(Block node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Block child.
   * @return The current node used as the Block child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Block")
  public Block getBlock() {
    return (Block) getChild(0);
  }
  /**
   * Retrieves the Block child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Block child.
   * @apilevel low-level
   */
  public Block getBlockNoTransform() {
    return (Block) getChildNoTransform(0);
  }
  /**
   * @attribute syn
   * @aspect ExceptionHandling
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:279
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="/home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:279")
  public boolean handles(TypeDecl exceptionType) {
    boolean handles_TypeDecl_value = false;
    return handles_TypeDecl_value;
  }
  /** @apilevel internal */
  private void parameterDeclaration_String_reset() {
    parameterDeclaration_String_computed = null;
    parameterDeclaration_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map parameterDeclaration_String_values;
  /** @apilevel internal */
  protected java.util.Map parameterDeclaration_String_computed;
  /**
   * @attribute syn
   * @aspect VariableScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:192
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="VariableScope", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:192")
  public SimpleSet<Variable> parameterDeclaration(String name) {
    Object _parameters = name;
    if (parameterDeclaration_String_computed == null) parameterDeclaration_String_computed = new java.util.HashMap(4);
    if (parameterDeclaration_String_values == null) parameterDeclaration_String_values = new java.util.HashMap(4);
    ASTState state = state();
    if (parameterDeclaration_String_values.containsKey(_parameters)
        && parameterDeclaration_String_computed.containsKey(_parameters)
        && (parameterDeclaration_String_computed.get(_parameters) == ASTState.NON_CYCLE || parameterDeclaration_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<Variable>) parameterDeclaration_String_values.get(_parameters);
    }
    SimpleSet<Variable> parameterDeclaration_String_value = emptySet();
    if (state().inCircle()) {
      parameterDeclaration_String_values.put(_parameters, parameterDeclaration_String_value);
      parameterDeclaration_String_computed.put(_parameters, state().cycle());
    
    } else {
      parameterDeclaration_String_values.put(_parameters, parameterDeclaration_String_value);
      parameterDeclaration_String_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return parameterDeclaration_String_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:144
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:144")
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = getBlock().modifiedInScope(var);
    return modifiedInScope_Variable_value;
  }
  /** @apilevel internal */
  private void label_catch_Body_reset() {
    label_catch_Body_computed = null;
    label_catch_Body_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map label_catch_Body_values;
  /** @apilevel internal */
  protected java.util.Map label_catch_Body_computed;
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:436
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:436")
  public Body.CatchLabel label_catch(Body b) {
    Object _parameters = b;
    if (label_catch_Body_computed == null) label_catch_Body_computed = new java.util.HashMap(4);
    if (label_catch_Body_values == null) label_catch_Body_values = new java.util.HashMap(4);
    ASTState state = state();
    if (label_catch_Body_values.containsKey(_parameters)
        && label_catch_Body_computed.containsKey(_parameters)
        && (label_catch_Body_computed.get(_parameters) == ASTState.NON_CYCLE || label_catch_Body_computed.get(_parameters) == state().cycle())) {
      return (Body.CatchLabel) label_catch_Body_values.get(_parameters);
    }
    Body.CatchLabel label_catch_Body_value = b.newCatchLabel(getParameter());
    if (state().inCircle()) {
      label_catch_Body_values.put(_parameters, label_catch_Body_value);
      label_catch_Body_computed.put(_parameters, state().cycle());
    
    } else {
      label_catch_Body_values.put(_parameters, label_catch_Body_value);
      label_catch_Body_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return label_catch_Body_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:97
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:97")
  public TypeDecl typeThrowable() {
    ASTState state = state();
    if (typeThrowable_computed == ASTState.NON_CYCLE || typeThrowable_computed == state().cycle()) {
      return typeThrowable_value;
    }
    typeThrowable_value = getParent().Define_typeThrowable(this, null);
    if (state().inCircle()) {
      typeThrowable_computed = state().cycle();
    
    } else {
      typeThrowable_computed = ASTState.NON_CYCLE;
    
    }
    return typeThrowable_value;
  }
  /** @apilevel internal */
  private void typeThrowable_reset() {
    typeThrowable_computed = null;
    typeThrowable_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeThrowable_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeThrowable_value;

  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:44
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="VariableScope", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:44")
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
   * @return true if an exception of type exceptionType is catchable by the catch clause
   * @attribute inh
   * @aspect UnreachableStatements
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:182
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="UnreachableStatements", declaredAt="/home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:182")
  public boolean reachableCatchClause(TypeDecl exceptionType) {
    Object _parameters = exceptionType;
    if (reachableCatchClause_TypeDecl_computed == null) reachableCatchClause_TypeDecl_computed = new java.util.HashMap(4);
    if (reachableCatchClause_TypeDecl_values == null) reachableCatchClause_TypeDecl_values = new java.util.HashMap(4);
    ASTState state = state();
    if (reachableCatchClause_TypeDecl_values.containsKey(_parameters)
        && reachableCatchClause_TypeDecl_computed.containsKey(_parameters)
        && (reachableCatchClause_TypeDecl_computed.get(_parameters) == ASTState.NON_CYCLE || reachableCatchClause_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) reachableCatchClause_TypeDecl_values.get(_parameters);
    }
    boolean reachableCatchClause_TypeDecl_value = getParent().Define_reachableCatchClause(this, null, exceptionType);
    if (state().inCircle()) {
      reachableCatchClause_TypeDecl_values.put(_parameters, reachableCatchClause_TypeDecl_value);
      reachableCatchClause_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      reachableCatchClause_TypeDecl_values.put(_parameters, reachableCatchClause_TypeDecl_value);
      reachableCatchClause_TypeDecl_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return reachableCatchClause_TypeDecl_value;
  }
  /** @apilevel internal */
  private void reachableCatchClause_TypeDecl_reset() {
    reachableCatchClause_TypeDecl_computed = null;
    reachableCatchClause_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map reachableCatchClause_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map reachableCatchClause_TypeDecl_computed;
  /**
   * @attribute inh
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:217
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:217")
  public Collection<TypeDecl> caughtExceptions() {
    Collection<TypeDecl> caughtExceptions_value = getParent().Define_caughtExceptions(this, null);
    return caughtExceptions_value;
  }
  /**
   * @attribute inh
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:280
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:280")
  public boolean reportUnreachable() {
    boolean reportUnreachable_value = getParent().Define_reportUnreachable(this, null);
    return reportUnreachable_value;
  }
  /**
   * @attribute inh
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:469
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="EmitJimple", declaredAt="/home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:469")
  public TypeDecl hostType() {
    TypeDecl hostType_value = getParent().Define_hostType(this, null);
    return hostType_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet<Variable> Define_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:138
      {
          SimpleSet<Variable> result = parameterDeclaration(name);
          if (!result.isEmpty()) {
            return result;
          }
          return lookupVariable(name);
        }
    }
    else {
      return getParent().Define_lookupVariable(this, _callerNode, name);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LookupVariable.jrag:30
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupVariable
   */
  protected boolean canDefine_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:209
   * @apilevel internal
   */
  public CatchClause Define_catchClause(ASTNode _callerNode, ASTNode _childNode) {
    int i = this.getIndexOfChild(_callerNode);
    return this;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:209
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute catchClause
   */
  protected boolean canDefine_catchClause(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:280
   * @apilevel internal
   */
  public boolean Define_reportUnreachable(ASTNode _callerNode, ASTNode _childNode) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:281
      return false;
    }
    else {
      return getParent().Define_reportUnreachable(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:280
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute reportUnreachable
   */
  protected boolean canDefine_reportUnreachable(ASTNode _callerNode, ASTNode _childNode) {
    return true;
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
