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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:321
 * @astdecl AssertStmt : Stmt ::= Condition:Expr [Message:Expr];
 * @production AssertStmt : {@link Stmt} ::= <span class="component">Condition:{@link Expr}</span> <span class="component">[Message:{@link Expr}]</span>;

 */
public class AssertStmt extends Stmt implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:68
   */
  public void prettyPrint(PrettyPrinter out) {
    if (hasMessage()) {
      out.print("assert ");
      out.print(getCondition());
      out.print(" : ");
      out.print(getMessage());
      out.print(";");
    } else {
      out.print("assert ");
      out.print(getCondition());
      out.print(";");
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public AssertStmt() {
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
    name = {"Condition", "Message"},
    type = {"Expr", "Opt<Expr>"},
    kind = {"Child", "Opt"}
  )
  public AssertStmt(Expr p0, Opt<Expr> p1) {
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
    assignedAfter_Variable_reset();
    unassignedAfter_Variable_reset();
    transformed_reset();
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
  public AssertStmt clone() throws CloneNotSupportedException {
    AssertStmt node = (AssertStmt) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:50
   */
  public AssertStmt copy() {
    try {
      AssertStmt node = (AssertStmt) clone();
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
   * @declaredat ASTNode:69
   */
  @Deprecated
  public AssertStmt fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:79
   */
  public AssertStmt treeCopyNoTransform() {
    AssertStmt tree = (AssertStmt) copy();
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
   * @declaredat ASTNode:99
   */
  public AssertStmt treeCopy() {
    AssertStmt tree = (AssertStmt) copy();
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
   * @declaredat ASTNode:113
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Condition child.
   * @param node The new node to replace the Condition child.
   * @apilevel high-level
   */
  public void setCondition(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Condition child.
   * @return The current node used as the Condition child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Condition")
  public Expr getCondition() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the Condition child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Condition child.
   * @apilevel low-level
   */
  public Expr getConditionNoTransform() {
    return (Expr) getChildNoTransform(0);
  }
  /**
   * Replaces the optional node for the Message child. This is the <code>Opt</code>
   * node containing the child Message, not the actual child!
   * @param opt The new node to be used as the optional node for the Message child.
   * @apilevel low-level
   */
  public void setMessageOpt(Opt<Expr> opt) {
    setChild(opt, 1);
  }
  /**
   * Replaces the (optional) Message child.
   * @param node The new node to be used as the Message child.
   * @apilevel high-level
   */
  public void setMessage(Expr node) {
    getMessageOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional Message child exists.
   * @return {@code true} if the optional Message child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasMessage() {
    return getMessageOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) Message child.
   * @return The Message child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Expr getMessage() {
    return (Expr) getMessageOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the Message child. This is the <code>Opt</code> node containing the child Message, not the actual child!
   * @return The optional node for child the Message child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="Message")
  public Opt<Expr> getMessageOpt() {
    return (Opt<Expr>) getChild(1);
  }
  /**
   * Retrieves the optional node for child Message. This is the <code>Opt</code> node containing the child Message, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Message.
   * @apilevel low-level
   */
  public Opt<Expr> getMessageOptNoTransform() {
    return (Opt<Expr>) getChildNoTransform(1);
  }
  /**
   * @aspect Transformations
   * @declaredat /home/olivier/projects/extendj/java4/backend/Transformations.jrag:83
   */
  private Stmt refined_Transformations_AssertStmt_transformed()
{
    FieldDeclarator assertionsDisabled = hostType().assertionsDisabled();
    Expr condition = (Expr) getCondition().treeCopyNoTransform();
    List<Expr> args = new List<Expr>();
    if (hasMessage()) {
      if (getMessage().type().isString()) {
        args.add(new CastExpr(new TypeAccess("java.lang", "Object"),
            (Expr) getMessage().treeCopyNoTransform()));
      } else {
        args.add(getMessage().treeCopyNoTransform());
      }
    }
    return new IfStmt(
        new LogNotExpr(
            new OrLogicalExpr(
                new BoundFieldAccess(assertionsDisabled),
                condition)),
        new ThrowStmt(
            new ClassInstanceExpr(
                lookupType("java.lang", "AssertionError").createQualifiedAccess(),
                args,
                new Opt())),
        new Opt());
  }
  /** @apilevel internal */
  private void assignedAfter_Variable_reset() {
    assignedAfter_Variable_values = null;
  }
  protected java.util.Map assignedAfter_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:264")
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
        new_assignedAfter_Variable_value = getCondition().assignedAfter(v);
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
      boolean new_assignedAfter_Variable_value = getCondition().assignedAfter(v);
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
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:895")
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
        new_unassignedAfter_Variable_value = getCondition().unassignedAfter(v);
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
      boolean new_unassignedAfter_Variable_value = getCondition().unassignedAfter(v);
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
  private void transformed_reset() {
    transformed_computed = false;
    
    transformed_value = null;
  }
  /** @apilevel internal */
  protected boolean transformed_computed = false;

  /** @apilevel internal */
  protected Stmt transformed_value;

  /**
   * The assert statement is transformed to an if-statement that throws an
   * AssertError if assertions are enabled and the assert condition is false.
   * @attribute syn
   * @aspect Transformations
   * @declaredat /home/olivier/projects/extendj/java4/backend/Transformations.jrag:83
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="Transformations", declaredAt="/home/olivier/projects/extendj/java4/backend/Transformations.jrag:83")
  public Stmt transformed() {
    ASTState state = state();
    if (transformed_computed) {
      return transformed_value;
    }
    state().enterLazyAttribute();
    transformed_value = cloneLocationOnto(refined_Transformations_AssertStmt_transformed());
    transformed_value.setParent(this);
    transformed_computed = true;
    state().leaveLazyAttribute();
    return transformed_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:78
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:78")
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = false;
    return modifiedInScope_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect Java2Rewrites
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Java2Rewrites.jrag:88
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java2Rewrites", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Java2Rewrites.jrag:88")
  public boolean hasAssertStatementRecursive() {
    boolean hasAssertStatementRecursive_value = true;
    return hasAssertStatementRecursive_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:256
   * @apilevel internal
   */
  public boolean Define_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (_callerNode == getMessageOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:520
      return getCondition().assignedAfter(v);
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
    // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:504
    if (!getCondition().type().isBoolean()) {
      {
        java.util.Set<ASTNode> contributors = _map.get(_root);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) _root, contributors);
        }
        contributors.add(this);
      }
    }
    // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:509
    if (hasMessage() && getMessage().type().isVoid()) {
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
    if (!getCondition().type().isBoolean()) {
      collection.add(error("Assert requires boolean condition"));
    }
    if (hasMessage() && getMessage().type().isVoid()) {
      collection.add(error("The detail message of an assert statement may not be void"));
    }
  }
}
