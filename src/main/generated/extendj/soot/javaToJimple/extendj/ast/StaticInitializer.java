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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:162
 * @astdecl StaticInitializer : BodyDecl ::= Block;
 * @production StaticInitializer : {@link BodyDecl} ::= <span class="component">{@link Block}</span>;

 */
public class StaticInitializer extends BodyDecl implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:559
   */
  public void prettyPrint(PrettyPrinter out) {
    if (!blockIsEmpty()) {
      out.print("static ");
      out.print(getBlock());
    }
  }
  /**
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:615
   */
  public void jimpleEmit(Body b) { getBlock().jimpleEmit(b); }
  /**
   * @declaredat ASTNode:1
   */
  public StaticInitializer() {
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
  public StaticInitializer(Block p0) {
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
    assignedAfter_Variable_reset();
    unassignedAfter_Variable_reset();
    handlesException_TypeDecl_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:39
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:43
   */
  public StaticInitializer clone() throws CloneNotSupportedException {
    StaticInitializer node = (StaticInitializer) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:48
   */
  public StaticInitializer copy() {
    try {
      StaticInitializer node = (StaticInitializer) clone();
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
   * @declaredat ASTNode:67
   */
  @Deprecated
  public StaticInitializer fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:77
   */
  public StaticInitializer treeCopyNoTransform() {
    StaticInitializer tree = (StaticInitializer) copy();
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
   * @declaredat ASTNode:97
   */
  public StaticInitializer treeCopy() {
    StaticInitializer tree = (StaticInitializer) copy();
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
   * @declaredat ASTNode:111
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
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
  /** @apilevel internal */
  private void assignedAfter_Variable_reset() {
    assignedAfter_Variable_values = null;
  }
  protected java.util.Map assignedAfter_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:272")
  public boolean assignedAfter(Variable v) {
    Object _parameters = v;
    if (assignedAfter_Variable_values == null) assignedAfter_Variable_values = new java.util.HashMap(4);
    ASTState.CircularValue _value;
    if (assignedAfter_Variable_values.containsKey(_parameters)) {
      Object _cache = assignedAfter_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTState.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTState.CircularValue) _cache;
      }
    } else {
      _value = new ASTState.CircularValue();
      assignedAfter_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTState state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_assignedAfter_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_assignedAfter_Variable_value = getBlock().assignedAfter(v);
        if (((Boolean)_value.value) != new_assignedAfter_Variable_value) {
          state.setChangeInCycle();
          _value.value = new_assignedAfter_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      assignedAfter_Variable_values.put(_parameters, new_assignedAfter_Variable_value);

      state.leaveCircle();
      return new_assignedAfter_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_assignedAfter_Variable_value = getBlock().assignedAfter(v);
      if (((Boolean)_value.value) != new_assignedAfter_Variable_value) {
        state.setChangeInCycle();
        _value.value = new_assignedAfter_Variable_value;
      }
      return new_assignedAfter_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private void unassignedAfter_Variable_reset() {
    unassignedAfter_Variable_values = null;
  }
  protected java.util.Map unassignedAfter_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:911")
  public boolean unassignedAfter(Variable v) {
    Object _parameters = v;
    if (unassignedAfter_Variable_values == null) unassignedAfter_Variable_values = new java.util.HashMap(4);
    ASTState.CircularValue _value;
    if (unassignedAfter_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfter_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTState.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTState.CircularValue) _cache;
      }
    } else {
      _value = new ASTState.CircularValue();
      unassignedAfter_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTState state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfter_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfter_Variable_value = getBlock().unassignedAfter(v);
        if (((Boolean)_value.value) != new_unassignedAfter_Variable_value) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfter_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfter_Variable_values.put(_parameters, new_unassignedAfter_Variable_value);

      state.leaveCircle();
      return new_unassignedAfter_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfter_Variable_value = getBlock().unassignedAfter(v);
      if (((Boolean)_value.value) != new_unassignedAfter_Variable_value) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfter_Variable_value;
      }
      return new_unassignedAfter_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * @attribute syn
   * @aspect PrettyPrintUtil
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:288
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PrettyPrintUtil", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:288")
  public boolean blockIsEmpty() {
    boolean blockIsEmpty_value = getBlock().getNumStmt() == 0;
    return blockIsEmpty_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsParTypeDecl.jrag:98
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsParTypeDecl.jrag:98")
  public boolean visibleTypeParameters() {
    boolean visibleTypeParameters_value = false;
    return visibleTypeParameters_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:40
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:40")
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = getBlock().modifiedInScope(var);
    return modifiedInScope_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect ExceptionHandling
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:92
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="/home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:92")
  public boolean handlesException(TypeDecl exceptionType) {
    Object _parameters = exceptionType;
    if (handlesException_TypeDecl_computed == null) handlesException_TypeDecl_computed = new java.util.HashMap(4);
    if (handlesException_TypeDecl_values == null) handlesException_TypeDecl_values = new java.util.HashMap(4);
    ASTState state = state();
    if (handlesException_TypeDecl_values.containsKey(_parameters)
        && handlesException_TypeDecl_computed.containsKey(_parameters)
        && (handlesException_TypeDecl_computed.get(_parameters) == ASTState.NON_CYCLE || handlesException_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) handlesException_TypeDecl_values.get(_parameters);
    }
    boolean handlesException_TypeDecl_value = getParent().Define_handlesException(this, null, exceptionType);
    if (state().inCircle()) {
      handlesException_TypeDecl_values.put(_parameters, handlesException_TypeDecl_value);
      handlesException_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      handlesException_TypeDecl_values.put(_parameters, handlesException_TypeDecl_value);
      handlesException_TypeDecl_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return handlesException_TypeDecl_value;
  }
  /** @apilevel internal */
  private void handlesException_TypeDecl_reset() {
    handlesException_TypeDecl_computed = null;
    handlesException_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map handlesException_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map handlesException_TypeDecl_computed;
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:256
   * @apilevel internal
   */
  public boolean Define_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:551
      return assignedBefore(v);
    }
    else {
      return super.Define_assignedBefore(_callerNode, _childNode, v);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:256
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute assignedBefore
   */
  protected boolean canDefine_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:112
   * @apilevel internal
   */
  public boolean Define_handlesException(ASTNode _callerNode, ASTNode _childNode, TypeDecl exceptionType) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:256
      return hostType().isAnonymous() && handlesException(exceptionType);
    }
    else {
      return getParent().Define_handlesException(this, _callerNode, exceptionType);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:112
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute handlesException
   */
  protected boolean canDefine_handlesException(ASTNode _callerNode, ASTNode _childNode, TypeDecl exceptionType) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:669
   * @apilevel internal
   */
  public TypeDecl Define_enclosingInstance(ASTNode _callerNode, ASTNode _childNode) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:673
      return null;
    }
    else {
      return getParent().Define_enclosingInstance(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:669
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute enclosingInstance
   */
  protected boolean canDefine_enclosingInstance(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:223
   * @apilevel internal
   */
  public boolean Define_inStaticContext(ASTNode _callerNode, ASTNode _childNode) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:228
      return true;
    }
    else {
      return getParent().Define_inStaticContext(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:223
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute inStaticContext
   */
  protected boolean canDefine_inStaticContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:49
   * @apilevel internal
   */
  public boolean Define_reachable(ASTNode _callerNode, ASTNode _childNode) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:63
      return true;
    }
    else {
      return getParent().Define_reachable(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:49
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute reachable
   */
  protected boolean canDefine_reachable(ASTNode _callerNode, ASTNode _childNode) {
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
  /** @apilevel internal */
  protected void collect_contributors_CompilationUnit_problems(CompilationUnit _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:214
    if (hostType().isInnerClass()) {
      {
        java.util.Set<ASTNode> contributors = _map.get(_root);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) _root, contributors);
        }
        contributors.add(this);
      }
    }
    // @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:38
    if (!getBlock().canCompleteNormally()) {
      {
        java.util.Set<ASTNode> contributors = _map.get(_root);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) _root, contributors);
        }
        contributors.add(this);
      }
    }
    super.collect_contributors_CompilationUnit_problems(_root, _map);
  }
  /** @apilevel internal */
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    if (hostType().isInnerClass()) {
      collection.add(error("*** Inner classes may not declare static initializers"));
    }
    if (!getBlock().canCompleteNormally()) {
      collection.add(errorf("static initializer in %s can not complete normally", hostType().fullName()));
    }
  }
}
