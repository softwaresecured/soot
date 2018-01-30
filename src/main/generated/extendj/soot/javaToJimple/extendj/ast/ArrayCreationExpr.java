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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:223
 * @astdecl ArrayCreationExpr : PrimaryExpr ::= TypeAccess:Access [ArrayInit];
 * @production ArrayCreationExpr : {@link PrimaryExpr} ::= <span class="component">TypeAccess:{@link Access}</span> <span class="component">[{@link ArrayInit}]</span>;

 */
public class ArrayCreationExpr extends PrimaryExpr implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:40
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("new ");
    out.print(getTypeAccess());
    if (hasArrayInit()) {
      out.print(" ");
      out.print(getArrayInit());
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public ArrayCreationExpr() {
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
    setChild(new Opt(), 1);
  }
  /**
   * @declaredat ASTNode:14
   */
  @ASTNodeAnnotation.Constructor(
    name = {"TypeAccess", "ArrayInit"},
    type = {"Access", "Opt<ArrayInit>"},
    kind = {"Child", "Opt"}
  )
  public ArrayCreationExpr(Access p0, Opt<ArrayInit> p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:24
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:30
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:34
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    unassignedAfterCreation_Variable_reset();
    unassignedAfter_Variable_reset();
    type_reset();
    numArrays_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:42
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:46
   */
  public ArrayCreationExpr clone() throws CloneNotSupportedException {
    ArrayCreationExpr node = (ArrayCreationExpr) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:51
   */
  public ArrayCreationExpr copy() {
    try {
      ArrayCreationExpr node = (ArrayCreationExpr) clone();
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
   * @declaredat ASTNode:70
   */
  @Deprecated
  public ArrayCreationExpr fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:80
   */
  public ArrayCreationExpr treeCopyNoTransform() {
    ArrayCreationExpr tree = (ArrayCreationExpr) copy();
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
   * @declaredat ASTNode:100
   */
  public ArrayCreationExpr treeCopy() {
    ArrayCreationExpr tree = (ArrayCreationExpr) copy();
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
   * @declaredat ASTNode:114
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the TypeAccess child.
   * @param node The new node to replace the TypeAccess child.
   * @apilevel high-level
   */
  public void setTypeAccess(Access node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the TypeAccess child.
   * @return The current node used as the TypeAccess child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="TypeAccess")
  public Access getTypeAccess() {
    return (Access) getChild(0);
  }
  /**
   * Retrieves the TypeAccess child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the TypeAccess child.
   * @apilevel low-level
   */
  public Access getTypeAccessNoTransform() {
    return (Access) getChildNoTransform(0);
  }
  /**
   * Replaces the optional node for the ArrayInit child. This is the <code>Opt</code>
   * node containing the child ArrayInit, not the actual child!
   * @param opt The new node to be used as the optional node for the ArrayInit child.
   * @apilevel low-level
   */
  public void setArrayInitOpt(Opt<ArrayInit> opt) {
    setChild(opt, 1);
  }
  /**
   * Replaces the (optional) ArrayInit child.
   * @param node The new node to be used as the ArrayInit child.
   * @apilevel high-level
   */
  public void setArrayInit(ArrayInit node) {
    getArrayInitOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional ArrayInit child exists.
   * @return {@code true} if the optional ArrayInit child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasArrayInit() {
    return getArrayInitOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) ArrayInit child.
   * @return The ArrayInit child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public ArrayInit getArrayInit() {
    return (ArrayInit) getArrayInitOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the ArrayInit child. This is the <code>Opt</code> node containing the child ArrayInit, not the actual child!
   * @return The optional node for child the ArrayInit child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="ArrayInit")
  public Opt<ArrayInit> getArrayInitOpt() {
    return (Opt<ArrayInit>) getChild(1);
  }
  /**
   * Retrieves the optional node for child ArrayInit. This is the <code>Opt</code> node containing the child ArrayInit, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child ArrayInit.
   * @apilevel low-level
   */
  public Opt<ArrayInit> getArrayInitOptNoTransform() {
    return (Opt<ArrayInit>) getChildNoTransform(1);
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:538
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:538")
  public boolean assignedAfterCreation(Variable v) {
    boolean assignedAfterCreation_Variable_value = getTypeAccess().assignedAfter(v);
    return assignedAfterCreation_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:268
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:268")
  public boolean assignedAfter(Variable v) {
    boolean assignedAfter_Variable_value = hasArrayInit() ? getArrayInit().assignedAfter(v) : assignedAfterCreation(v);
    return assignedAfter_Variable_value;
  }
  /** @apilevel internal */
  private void unassignedAfterCreation_Variable_reset() {
    unassignedAfterCreation_Variable_values = null;
  }
  protected java.util.Map unassignedAfterCreation_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1147")
  public boolean unassignedAfterCreation(Variable v) {
    Object _parameters = v;
    if (unassignedAfterCreation_Variable_values == null) unassignedAfterCreation_Variable_values = new java.util.HashMap(4);
    ASTState.CircularValue _value;
    if (unassignedAfterCreation_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfterCreation_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTState.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTState.CircularValue) _cache;
      }
    } else {
      _value = new ASTState.CircularValue();
      unassignedAfterCreation_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTState state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfterCreation_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfterCreation_Variable_value = getTypeAccess().unassignedAfter(v);
        if (((Boolean)_value.value) != new_unassignedAfterCreation_Variable_value) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfterCreation_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfterCreation_Variable_values.put(_parameters, new_unassignedAfterCreation_Variable_value);

      state.leaveCircle();
      return new_unassignedAfterCreation_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfterCreation_Variable_value = getTypeAccess().unassignedAfter(v);
      if (((Boolean)_value.value) != new_unassignedAfterCreation_Variable_value) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfterCreation_Variable_value;
      }
      return new_unassignedAfterCreation_Variable_value;
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
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:899")
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
        new_unassignedAfter_Variable_value = hasArrayInit() ? getArrayInit().unassignedAfter(v) : unassignedAfterCreation(v);
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
      boolean new_unassignedAfter_Variable_value = hasArrayInit() ? getArrayInit().unassignedAfter(v) : unassignedAfterCreation(v);
      if (((Boolean)_value.value) != new_unassignedAfter_Variable_value) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfter_Variable_value;
      }
      return new_unassignedAfter_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private void type_reset() {
    type_computed = null;
    type_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle type_computed = null;

  /** @apilevel internal */
  protected TypeDecl type_value;

  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:295
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:295")
  public TypeDecl type() {
    ASTState state = state();
    if (type_computed == ASTState.NON_CYCLE || type_computed == state().cycle()) {
      return type_value;
    }
    type_value = getTypeAccess().type();
    if (state().inCircle()) {
      type_computed = state().cycle();
    
    } else {
      type_computed = ASTState.NON_CYCLE;
    
    }
    return type_value;
  }
  /** @apilevel internal */
  private void numArrays_reset() {
    numArrays_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle numArrays_computed = null;

  /** @apilevel internal */
  protected int numArrays_value;

  /**
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:112
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="InnerClasses", declaredAt="/home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:112")
  public int numArrays() {
    ASTState state = state();
    if (numArrays_computed == ASTState.NON_CYCLE || numArrays_computed == state().cycle()) {
      return numArrays_value;
    }
    numArrays_value = numArrays_compute();
    if (state().inCircle()) {
      numArrays_computed = state().cycle();
    
    } else {
      numArrays_computed = ASTState.NON_CYCLE;
    
    }
    return numArrays_value;
  }
  /** @apilevel internal */
  private int numArrays_compute() {
      int dims = type().dimension();
      Access a = getTypeAccess();
      while (a instanceof ArrayTypeAccess && !(a instanceof ArrayTypeWithSizeAccess)) {
        dims -= 1;
        a = ((ArrayTypeAccess) a).getAccess();
      }
      return dims;
    }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:145
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:145")
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = hasArrayInit() && getArrayInit().modifiedInScope(var);
    return modifiedInScope_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:42
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Expressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:42")
  public Value eval(Body b) {
    {
        if (hasArrayInit()) return getArrayInit().eval(b);
    
        ArrayList<Value> list = new ArrayList<>();
        getTypeAccess().evalArraySize(b, list);
        if (numArrays() == 1)
          return b.newNewArrayExpr(
            type().componentType().sootType(),
            list.get(0),
            this);
    
        return b.newNewMultiArrayExpr(
            (soot.ArrayType)type().sootType(),
            list,
            this);
      }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:256
   * @apilevel internal
   */
  public boolean Define_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (_callerNode == getArrayInitOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:544
      return assignedAfterCreation(v);
    }
    else {
      return getParent().Define_assignedBefore(this, _callerNode, v);
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:887
   * @apilevel internal
   */
  public boolean Define_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (_callerNode == getArrayInitOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1153
      return unassignedAfterCreation(v);
    }
    else {
      return getParent().Define_unassignedBefore(this, _callerNode, v);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:887
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute unassignedBefore
   */
  protected boolean canDefine_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    if (getTypeAccessNoTransform() != null && _callerNode == getTypeAccess()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:110
      return NameType.TYPE_NAME;
    }
    else {
      return getParent().Define_nameType(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute nameType
   */
  protected boolean canDefine_nameType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:723
   * @apilevel internal
   */
  public TypeDecl Define_declType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getArrayInitOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:282
      return type();
    }
    else {
      return getParent().Define_declType(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:723
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute declType
   */
  protected boolean canDefine_declType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:104
   * @apilevel internal
   */
  public TypeDecl Define_expectedType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getArrayInitOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:108
      return type().componentType();
    }
    else {
      return getParent().Define_expectedType(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:104
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute expectedType
   */
  protected boolean canDefine_expectedType(ASTNode _callerNode, ASTNode _childNode) {
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
    // @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:480
    if (!type().isReifiable()) {
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
    if (!type().isReifiable()) {
      collection.add(error("can not create array with non-reifiable element type"));
    }
  }
}
