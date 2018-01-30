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
 * @declaredat /home/olivier/projects/extendj/java7/grammar/BasicTWR.ast:1
 * @astdecl BasicTWR : Stmt ::= Resource:ResourceDeclaration Block;
 * @production BasicTWR : {@link Stmt} ::= <span class="component">Resource:{@link ResourceDeclaration}</span> <span class="component">{@link Block}</span>;

 */
public class BasicTWR extends Stmt implements Cloneable, VariableScope {
  /**
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:80
   */
  public void jimpleEmit(Body b) {
    final Body.Label  outerFinallyLbl = b.newLabel(this);
    final Body.Label  tryEndLbl       = b.newLabel(this);
    final TypeDecl    throwableType   = lookupType("java.lang", "Throwable");

    // Store the resource in local.
    final Body.Label resourceBeginLbl = b.addNewLabel(this);
    getResource().jimpleEmit(b);
    final Local      resource_local   = b.local(getResource());
    final Body.Label resourceEndLbl   = b.addNewLabel(this);

    // do the main block
    final Body.Label blockBeginLbl    = b.addNewLabel(this);
    getBlock().jimpleEmit(b);
    final Body.Label blockEndLbl      = b.addNewLabel(this);
    b.addGoTo(outerFinallyLbl, this);

    // If there was an exception when initializing the resource
    // we need to directly rethrow the exception.
    final Local           ex_res                = b.newTemp(throwableType.sootType(), this);
    final Body.CatchLabel resourceExceptionLbl  = b.newCatchLabel(ex_res, this);
    b.addLabel(resourceExceptionLbl);
    b.add(b.newThrowStmt(ex_res, this));
    b.addTrap(throwableType, resourceBeginLbl, resourceEndLbl, resourceExceptionLbl);

    //if (gen.addressOf(blockBeginLbl) != gen.addressOf(blockEndLbl)) {
    {
      final Body.Label      innerFinallyLbl   = b.newLabel(this);

      // Catch primary exception:
      // operand stack: .., #primary
      final Local           ex_primary        = b.newTemp(throwableType.sootType(), this);
      final Body.CatchLabel catchPrimaryLbl   = b.newCatchLabel(ex_primary, this);
      b.addLabel(catchPrimaryLbl);

      // Try-close resource:
      final Body.Label      tryCloseBeginLbl  = b.addNewLabel(this);
      jimpleEmit_closeResIfNotNull(b, resource_local, innerFinallyLbl);
      final Body.Label      tryCloseEndLbl    = b.addNewLabel(this);
      b.addGoTo(innerFinallyLbl, this);

      // Catch suppressed exception.
      // operand stack: .., #primary, #suppressed
      final Local           ex_suppressed       = b.newTemp(throwableType.sootType(), this);
      final Body.CatchLabel catchSuppressedLbl  = b.newCatchLabel(ex_suppressed, this);
      b.addLabel(catchSuppressedLbl);
      b.add(jimpleEmit_methodInvokeStmt(b, addSuppressedMethod(), throwableType,
                                          ex_primary, Collections.singletonList(ex_suppressed)));

      // Inner finally:
      // operand stack: .., #primary
      b.addLabel(innerFinallyLbl);
      b.add(b.newThrowStmt(ex_primary, this));

      // If there was an exception during the block of the try
      // statement, then we should try to close the resource.
      b.addTrap(throwableType, blockBeginLbl, blockEndLbl, catchPrimaryLbl);

      // If an exception occurrs during the automatic closing
      // of a resource after an exception in the try block...
      b.addTrap(throwableType, tryCloseBeginLbl, tryCloseEndLbl, catchSuppressedLbl);
    }

    // Outer finally.
    b.addLabel(outerFinallyLbl);
    jimpleEmit_closeResIfNotNull(b, resource_local, tryEndLbl);

    b.addLabel(tryEndLbl);
  }
  /**
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:150
   */
  private void jimpleEmit_closeResIfNotNull(Body b, Local res_var, Body.Label end) {
    TypeDecl auto_close = lookupType("java.lang", "AutoCloseable");

    b.add(b.newIfStmt(
      b.newEqExpr(res_var, new NullLiteral().eval(b), this),
      end.stmt,
      this));

    b.add(jimpleEmit_methodInvokeStmt(b, closeMethod(), auto_close, res_var, new ArrayList<>()));
  }
  /**
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:161
   */
  private soot.jimple.InvokeStmt jimpleEmit_methodInvokeStmt(Body b, MethodDecl decl, TypeDecl host,
                                       Local base, java.util.List<? extends Immediate> args) {
    assert !decl.isStatic();
    final soot.jimple.InvokeExpr expr =
      host.isInterfaceDecl()  ? b.newInterfaceInvokeExpr(base, decl.sootRef(), args, this)
                              : b.  newVirtualInvokeExpr(base, decl.sootRef(), args, this);
    return b.newInvokeStmt(expr, this);
  }
  /**
   * Lookup the java.lang.Throwable.close() method.
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:174
   */
  private MethodDecl closeMethod() {
    TypeDecl autoCloseableType = lookupType("java.lang", "AutoCloseable");
    if (autoCloseableType.isUnknown()) {
      throw new Error("Could not find java.lang.AutoCloseable");
    }
    for (MethodDecl method : (Collection<MethodDecl>)
        autoCloseableType.memberMethods("close")) {
      if (method.getNumParameter() == 0) {
        return method;
      }
    }
    throw new Error("Could not find java.lang.AutoCloseable.close()");
  }
  /**
   * Lookup the java.lang.Throwable.addSuppressed(Throwable) method.
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:191
   */
  private MethodDecl addSuppressedMethod() {
    TypeDecl throwableType = lookupType("java.lang", "Throwable");
    if (throwableType.isUnknown()) {
      throw new Error("Could not find java.lang.Throwable");
    }
    for (MethodDecl method : (Collection<MethodDecl>)
        throwableType.memberMethods("addSuppressed")) {
      if (method.getNumParameter() == 1 &&
          method.getParameter(0).getTypeAccess().type() == throwableType) {
        return method;
      }
    }
    throw new Error("Could not find java.lang.Throwable.addSuppressed()");
  }
  /**
   * @declaredat ASTNode:1
   */
  public BasicTWR() {
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
    children = new ASTNode[2];
  }
  /**
   * @declaredat ASTNode:13
   */
  @ASTNodeAnnotation.Constructor(
    name = {"Resource", "Block"},
    type = {"ResourceDeclaration", "Block"},
    kind = {"Child", "Child"}
  )
  public BasicTWR(ResourceDeclaration p0, Block p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:23
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:29
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:33
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    localVariableDeclaration_String_reset();
    localLookup_String_reset();
    lookupVariable_String_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:40
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:44
   */
  public BasicTWR clone() throws CloneNotSupportedException {
    BasicTWR node = (BasicTWR) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:49
   */
  public BasicTWR copy() {
    try {
      BasicTWR node = (BasicTWR) clone();
      node.parent = null;
      if (children != null) {
        node.children = (ASTNode[]) children.clone();
      }
      return node;
    } catch (CloneNotSupportedException e) {
      throw new Error("Error: clone not supported for " + getClass().getName());
    }
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:68
   */
  @Deprecated
  public BasicTWR fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:78
   */
  public BasicTWR treeCopyNoTransform() {
    BasicTWR tree = (BasicTWR) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        ASTNode child = (ASTNode) children[i];
        if (child != null) {
          child = child.treeCopyNoTransform();
          tree.setChild(child, i);
        }
      }
    }
    return tree;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:98
   */
  public BasicTWR treeCopy() {
    BasicTWR tree = (BasicTWR) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        ASTNode child = (ASTNode) getChild(i);
        if (child != null) {
          child = child.treeCopy();
          tree.setChild(child, i);
        }
      }
    }
    return tree;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:112
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Resource child.
   * @param node The new node to replace the Resource child.
   * @apilevel high-level
   */
  public void setResource(ResourceDeclaration node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Resource child.
   * @return The current node used as the Resource child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Resource")
  public ResourceDeclaration getResource() {
    return (ResourceDeclaration) getChild(0);
  }
  /**
   * Retrieves the Resource child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Resource child.
   * @apilevel low-level
   */
  public ResourceDeclaration getResourceNoTransform() {
    return (ResourceDeclaration) getChildNoTransform(0);
  }
  /**
   * Replaces the Block child.
   * @param node The new node to replace the Block child.
   * @apilevel high-level
   */
  public void setBlock(Block node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the Block child.
   * @return The current node used as the Block child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Block")
  public Block getBlock() {
    return (Block) getChild(1);
  }
  /**
   * Retrieves the Block child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Block child.
   * @apilevel low-level
   */
  public Block getBlockNoTransform() {
    return (Block) getChildNoTransform(1);
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:78
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:78")
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = getBlock().modifiedInScope(var);
    return modifiedInScope_Variable_value;
  }
  /** @apilevel internal */
  private void localVariableDeclaration_String_reset() {
    localVariableDeclaration_String_computed = null;
    localVariableDeclaration_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map localVariableDeclaration_String_values;
  /** @apilevel internal */
  protected java.util.Map localVariableDeclaration_String_computed;
  /**
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:219
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:219")
  public VariableDeclarator localVariableDeclaration(String name) {
    Object _parameters = name;
    if (localVariableDeclaration_String_computed == null) localVariableDeclaration_String_computed = new java.util.HashMap(4);
    if (localVariableDeclaration_String_values == null) localVariableDeclaration_String_values = new java.util.HashMap(4);
    ASTState state = state();
    if (localVariableDeclaration_String_values.containsKey(_parameters)
        && localVariableDeclaration_String_computed.containsKey(_parameters)
        && (localVariableDeclaration_String_computed.get(_parameters) == ASTState.NON_CYCLE || localVariableDeclaration_String_computed.get(_parameters) == state().cycle())) {
      return (VariableDeclarator) localVariableDeclaration_String_values.get(_parameters);
    }
    VariableDeclarator localVariableDeclaration_String_value = getResource().declaresVariable(name) ? getResource() : null;
    if (state().inCircle()) {
      localVariableDeclaration_String_values.put(_parameters, localVariableDeclaration_String_value);
      localVariableDeclaration_String_computed.put(_parameters, state().cycle());
    
    } else {
      localVariableDeclaration_String_values.put(_parameters, localVariableDeclaration_String_value);
      localVariableDeclaration_String_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return localVariableDeclaration_String_value;
  }
  /** @apilevel internal */
  private void localLookup_String_reset() {
    localLookup_String_computed = null;
    localLookup_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map localLookup_String_values;
  /** @apilevel internal */
  protected java.util.Map localLookup_String_computed;
  /**
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:222
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:222")
  public SimpleSet<Variable> localLookup(String name) {
    Object _parameters = name;
    if (localLookup_String_computed == null) localLookup_String_computed = new java.util.HashMap(4);
    if (localLookup_String_values == null) localLookup_String_values = new java.util.HashMap(4);
    ASTState state = state();
    if (localLookup_String_values.containsKey(_parameters)
        && localLookup_String_computed.containsKey(_parameters)
        && (localLookup_String_computed.get(_parameters) == ASTState.NON_CYCLE || localLookup_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<Variable>) localLookup_String_values.get(_parameters);
    }
    SimpleSet<Variable> localLookup_String_value = localLookup_compute(name);
    if (state().inCircle()) {
      localLookup_String_values.put(_parameters, localLookup_String_value);
      localLookup_String_computed.put(_parameters, state().cycle());
    
    } else {
      localLookup_String_values.put(_parameters, localLookup_String_value);
      localLookup_String_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return localLookup_String_value;
  }
  /** @apilevel internal */
  private SimpleSet<Variable> localLookup_compute(String name) {
      VariableDeclarator v = localVariableDeclaration(name);
      if (v != null) return v;
  
      return lookupVariable(name);
    }
  /**
   * @attribute inh
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:217
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:217")
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/VariableDeclaration.jrag:133
   * @apilevel internal
   */
  public Modifiers Define_declarationModifiers(ASTNode _callerNode, ASTNode _childNode) {
    if (getResourceNoTransform() != null && _callerNode == getResource()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:211
      return getResource().getModifiers();
    }
    else {
      return getParent().Define_declarationModifiers(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/VariableDeclaration.jrag:133
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute declarationModifiers
   */
  protected boolean canDefine_declarationModifiers(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/VariableDeclaration.jrag:144
   * @apilevel internal
   */
  public Access Define_declarationType(ASTNode _callerNode, ASTNode _childNode) {
    if (getResourceNoTransform() != null && _callerNode == getResource()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:213
      return getResource().getResourceType();
    }
    else {
      return getParent().Define_declarationType(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/VariableDeclaration.jrag:144
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute declarationType
   */
  protected boolean canDefine_declarationType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet<Variable> Define_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:215
      return localLookup(name);
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
  /** @apilevel internal */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
  /** @apilevel internal */
  public boolean canRewrite() {
    return false;
  }
}
