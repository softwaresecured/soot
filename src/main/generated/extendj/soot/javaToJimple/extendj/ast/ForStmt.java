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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:304
 * @astdecl ForStmt : BranchTargetStmt ::= InitStmt:Stmt* [Condition:Expr] UpdateStmt:Stmt* Stmt;
 * @production ForStmt : {@link BranchTargetStmt} ::= <span class="component">InitStmt:{@link Stmt}*</span> <span class="component">[Condition:{@link Expr}]</span> <span class="component">UpdateStmt:{@link Stmt}*</span> <span class="component">{@link Stmt}</span>;

 */
public class ForStmt extends BranchTargetStmt implements Cloneable, VariableScope {
  /**
   * Manually implemented because it is too complex for a template.
   * @aspect PrettyPrintUtil
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:188
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("for (");
    if (getNumInitStmt() > 0) {
      if (getInitStmt(0) instanceof ExprStmt) {
        ExprStmt stmt = (ExprStmt) getInitStmt(0);
        out.print(stmt.getExpr());
        for (int i = 1; i < getNumInitStmt(); i++) {
          out.print(", ");
          stmt = (ExprStmt) getInitStmt(i);
          out.print(stmt.getExpr());
        }
        out.print("; ");
      } else {
        out.print(getInitStmt(0));
        out.print(" ");
      }
    } else {
      out.print(" ; ");
    }

    if (hasCondition()) {
      out.print(getCondition());
    }
    out.print("; ");

    // Print update statements.
    for (int i = 0; i < getNumUpdateStmt(); i++) {
      if (i > 0) {
        out.print(", ");
      }
      ExprStmt update = (ExprStmt) getUpdateStmt(i);
      out.print(update.getExpr());
    }

    out.print(") ");
    if (getStmt() instanceof Block) {
      out.print(getStmt());
    } else {
      out.print("{");
      out.println();
      out.indent(1);
      out.print(getStmt());
      out.println();
      out.print("}");
    }
  }
  /**
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:207
   */
  public void jimpleEmit(Body b) {
    for (Stmt s : getInitStmtList())
      s.jimpleEmit(b);

    b.addLabel(cond_label(b));

    // e.g. `for (;;) {}`
    if (hasCondition())
      getCondition().emitEvalBranch(b);
    else
      b.addGoTo(body_label(b), this);

    //if ((!hasCondition) || getCondition().canBeTrue()) {
      b.addLabel(body_label(b));
      getStmt().jimpleEmit(b);

      b.addLabel(update_label(b));
      for (Stmt s : getUpdateStmtList())
        s.jimpleEmit(b);

      //b.setLine(this);
      b.addGoTo(cond_label(b), this);
    //}

    //if (canCompleteNormally())
      b.addLabel(end_label(b));
  }
  /**
   * @declaredat ASTNode:1
   */
  public ForStmt() {
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
    children = new ASTNode[4];
    setChild(new List(), 0);
    setChild(new Opt(), 1);
    setChild(new List(), 2);
  }
  /**
   * @declaredat ASTNode:16
   */
  @ASTNodeAnnotation.Constructor(
    name = {"InitStmt", "Condition", "UpdateStmt", "Stmt"},
    type = {"List<Stmt>", "Opt<Expr>", "List<Stmt>", "Stmt"},
    kind = {"List", "Opt", "List", "Child"}
  )
  public ForStmt(List<Stmt> p0, Opt<Expr> p1, List<Stmt> p2, Stmt p3) {
    setChild(p0, 0);
    setChild(p1, 1);
    setChild(p2, 2);
    setChild(p3, 3);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:28
   */
  protected int numChildren() {
    return 4;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:34
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:38
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    assignedAfter_Variable_reset();
    unassignedAfter_Variable_reset();
    unassignedAfterInit_Variable_reset();
    unassignedBeforeCondition_Variable_reset();
    unassignedAfterUpdate_Variable_reset();
    localLookup_String_reset();
    localVariableDeclaration_String_reset();
    canCompleteNormally_reset();
    cond_label_Body_reset();
    body_label_Body_reset();
    update_label_Body_reset();
    end_label_Body_reset();
    lookupVariable_String_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:55
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:59
   */
  public ForStmt clone() throws CloneNotSupportedException {
    ForStmt node = (ForStmt) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:64
   */
  public ForStmt copy() {
    try {
      ForStmt node = (ForStmt) clone();
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
   * @declaredat ASTNode:83
   */
  @Deprecated
  public ForStmt fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:93
   */
  public ForStmt treeCopyNoTransform() {
    ForStmt tree = (ForStmt) copy();
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
   * @declaredat ASTNode:113
   */
  public ForStmt treeCopy() {
    ForStmt tree = (ForStmt) copy();
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
   * @declaredat ASTNode:127
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the InitStmt list.
   * @param list The new list node to be used as the InitStmt list.
   * @apilevel high-level
   */
  public void setInitStmtList(List<Stmt> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the InitStmt list.
   * @return Number of children in the InitStmt list.
   * @apilevel high-level
   */
  public int getNumInitStmt() {
    return getInitStmtList().getNumChild();
  }
  /**
   * Retrieves the number of children in the InitStmt list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the InitStmt list.
   * @apilevel low-level
   */
  public int getNumInitStmtNoTransform() {
    return getInitStmtListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the InitStmt list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the InitStmt list.
   * @apilevel high-level
   */
  public Stmt getInitStmt(int i) {
    return (Stmt) getInitStmtList().getChild(i);
  }
  /**
   * Check whether the InitStmt list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasInitStmt() {
    return getInitStmtList().getNumChild() != 0;
  }
  /**
   * Append an element to the InitStmt list.
   * @param node The element to append to the InitStmt list.
   * @apilevel high-level
   */
  public void addInitStmt(Stmt node) {
    List<Stmt> list = (parent == null) ? getInitStmtListNoTransform() : getInitStmtList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addInitStmtNoTransform(Stmt node) {
    List<Stmt> list = getInitStmtListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the InitStmt list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setInitStmt(Stmt node, int i) {
    List<Stmt> list = getInitStmtList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the InitStmt list.
   * @return The node representing the InitStmt list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="InitStmt")
  public List<Stmt> getInitStmtList() {
    List<Stmt> list = (List<Stmt>) getChild(0);
    return list;
  }
  /**
   * Retrieves the InitStmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the InitStmt list.
   * @apilevel low-level
   */
  public List<Stmt> getInitStmtListNoTransform() {
    return (List<Stmt>) getChildNoTransform(0);
  }
  /**
   * @return the element at index {@code i} in the InitStmt list without
   * triggering rewrites.
   */
  public Stmt getInitStmtNoTransform(int i) {
    return (Stmt) getInitStmtListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the InitStmt list.
   * @return The node representing the InitStmt list.
   * @apilevel high-level
   */
  public List<Stmt> getInitStmts() {
    return getInitStmtList();
  }
  /**
   * Retrieves the InitStmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the InitStmt list.
   * @apilevel low-level
   */
  public List<Stmt> getInitStmtsNoTransform() {
    return getInitStmtListNoTransform();
  }
  /**
   * Replaces the optional node for the Condition child. This is the <code>Opt</code>
   * node containing the child Condition, not the actual child!
   * @param opt The new node to be used as the optional node for the Condition child.
   * @apilevel low-level
   */
  public void setConditionOpt(Opt<Expr> opt) {
    setChild(opt, 1);
  }
  /**
   * Replaces the (optional) Condition child.
   * @param node The new node to be used as the Condition child.
   * @apilevel high-level
   */
  public void setCondition(Expr node) {
    getConditionOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional Condition child exists.
   * @return {@code true} if the optional Condition child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasCondition() {
    return getConditionOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) Condition child.
   * @return The Condition child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Expr getCondition() {
    return (Expr) getConditionOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the Condition child. This is the <code>Opt</code> node containing the child Condition, not the actual child!
   * @return The optional node for child the Condition child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="Condition")
  public Opt<Expr> getConditionOpt() {
    return (Opt<Expr>) getChild(1);
  }
  /**
   * Retrieves the optional node for child Condition. This is the <code>Opt</code> node containing the child Condition, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Condition.
   * @apilevel low-level
   */
  public Opt<Expr> getConditionOptNoTransform() {
    return (Opt<Expr>) getChildNoTransform(1);
  }
  /**
   * Replaces the UpdateStmt list.
   * @param list The new list node to be used as the UpdateStmt list.
   * @apilevel high-level
   */
  public void setUpdateStmtList(List<Stmt> list) {
    setChild(list, 2);
  }
  /**
   * Retrieves the number of children in the UpdateStmt list.
   * @return Number of children in the UpdateStmt list.
   * @apilevel high-level
   */
  public int getNumUpdateStmt() {
    return getUpdateStmtList().getNumChild();
  }
  /**
   * Retrieves the number of children in the UpdateStmt list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the UpdateStmt list.
   * @apilevel low-level
   */
  public int getNumUpdateStmtNoTransform() {
    return getUpdateStmtListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the UpdateStmt list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the UpdateStmt list.
   * @apilevel high-level
   */
  public Stmt getUpdateStmt(int i) {
    return (Stmt) getUpdateStmtList().getChild(i);
  }
  /**
   * Check whether the UpdateStmt list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasUpdateStmt() {
    return getUpdateStmtList().getNumChild() != 0;
  }
  /**
   * Append an element to the UpdateStmt list.
   * @param node The element to append to the UpdateStmt list.
   * @apilevel high-level
   */
  public void addUpdateStmt(Stmt node) {
    List<Stmt> list = (parent == null) ? getUpdateStmtListNoTransform() : getUpdateStmtList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addUpdateStmtNoTransform(Stmt node) {
    List<Stmt> list = getUpdateStmtListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the UpdateStmt list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setUpdateStmt(Stmt node, int i) {
    List<Stmt> list = getUpdateStmtList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the UpdateStmt list.
   * @return The node representing the UpdateStmt list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="UpdateStmt")
  public List<Stmt> getUpdateStmtList() {
    List<Stmt> list = (List<Stmt>) getChild(2);
    return list;
  }
  /**
   * Retrieves the UpdateStmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the UpdateStmt list.
   * @apilevel low-level
   */
  public List<Stmt> getUpdateStmtListNoTransform() {
    return (List<Stmt>) getChildNoTransform(2);
  }
  /**
   * @return the element at index {@code i} in the UpdateStmt list without
   * triggering rewrites.
   */
  public Stmt getUpdateStmtNoTransform(int i) {
    return (Stmt) getUpdateStmtListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the UpdateStmt list.
   * @return The node representing the UpdateStmt list.
   * @apilevel high-level
   */
  public List<Stmt> getUpdateStmts() {
    return getUpdateStmtList();
  }
  /**
   * Retrieves the UpdateStmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the UpdateStmt list.
   * @apilevel low-level
   */
  public List<Stmt> getUpdateStmtsNoTransform() {
    return getUpdateStmtListNoTransform();
  }
  /**
   * Replaces the Stmt child.
   * @param node The new node to replace the Stmt child.
   * @apilevel high-level
   */
  public void setStmt(Stmt node) {
    setChild(node, 3);
  }
  /**
   * Retrieves the Stmt child.
   * @return The current node used as the Stmt child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Stmt")
  public Stmt getStmt() {
    return (Stmt) getChild(3);
  }
  /**
   * Retrieves the Stmt child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Stmt child.
   * @apilevel low-level
   */
  public Stmt getStmtNoTransform() {
    return (Stmt) getChildNoTransform(3);
  }
  /**
   * @return <code>true</code> if this statement is a potential
   * branch target of the given branch statement.
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:215
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BranchTarget", declaredAt="/home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:215")
  public boolean potentialTargetOf(Stmt branch) {
    boolean potentialTargetOf_Stmt_value = branch.canBranchTo(this);
    return potentialTargetOf_Stmt_value;
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
        new_assignedAfter_Variable_value = assignedAfter_compute(v);
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
      boolean new_assignedAfter_Variable_value = assignedAfter_compute(v);
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
  private boolean assignedAfter_compute(Variable v) {
      if (!(!hasCondition() || getCondition().assignedAfterFalse(v))) {
        return false;
      }
      for (BreakStmt stmt : targetBreaks()) {
        if (!stmt.assignedAfterReachedFinallyBlocks(v)) {
          return false;
        }
      }
      return true;
    }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:798
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:798")
  public boolean assignedAfterInitialization(Variable v) {
    boolean assignedAfterInitialization_Variable_value = getNumInitStmt() == 0
          ? assignedBefore(v)
          : getInitStmt(getNumInitStmt()-1).assignedAfter(v);
    return assignedAfterInitialization_Variable_value;
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
        new_unassignedAfter_Variable_value = unassignedAfter_compute(v);
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
      boolean new_unassignedAfter_Variable_value = unassignedAfter_compute(v);
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
  private boolean unassignedAfter_compute(Variable v) {
      if (!unassignedBeforeCondition(v)) { // Start a circular evaluation here.
        return false;
      }
      if (!(!hasCondition() || getCondition().unassignedAfterFalse(v))) {
        return false;
      }
      for (BreakStmt stmt : targetBreaks()) {
        if (!stmt.unassignedAfterReachedFinallyBlocks(v)) {
          return false;
        }
      }
      return true;
    }
  /** @apilevel internal */
  private void unassignedAfterInit_Variable_reset() {
    unassignedAfterInit_Variable_values = null;
  }
  protected java.util.Map unassignedAfterInit_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1493")
  public boolean unassignedAfterInit(Variable v) {
    Object _parameters = v;
    if (unassignedAfterInit_Variable_values == null) unassignedAfterInit_Variable_values = new java.util.HashMap(4);
    ASTState.CircularValue _value;
    if (unassignedAfterInit_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfterInit_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTState.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTState.CircularValue) _cache;
      }
    } else {
      _value = new ASTState.CircularValue();
      unassignedAfterInit_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTState state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfterInit_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfterInit_Variable_value = getNumInitStmt() == 0
              ? unassignedBefore(v)
              : getInitStmt(getNumInitStmt()-1).unassignedAfter(v);
        if (((Boolean)_value.value) != new_unassignedAfterInit_Variable_value) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfterInit_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfterInit_Variable_values.put(_parameters, new_unassignedAfterInit_Variable_value);

      state.leaveCircle();
      return new_unassignedAfterInit_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfterInit_Variable_value = getNumInitStmt() == 0
            ? unassignedBefore(v)
            : getInitStmt(getNumInitStmt()-1).unassignedAfter(v);
      if (((Boolean)_value.value) != new_unassignedAfterInit_Variable_value) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfterInit_Variable_value;
      }
      return new_unassignedAfterInit_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private void unassignedBeforeCondition_Variable_reset() {
    unassignedBeforeCondition_Variable_values = null;
  }
  protected java.util.Map unassignedBeforeCondition_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1499")
  public boolean unassignedBeforeCondition(Variable v) {
    Object _parameters = v;
    if (unassignedBeforeCondition_Variable_values == null) unassignedBeforeCondition_Variable_values = new java.util.HashMap(4);
    ASTState.CircularValue _value;
    if (unassignedBeforeCondition_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedBeforeCondition_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTState.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTState.CircularValue) _cache;
      }
    } else {
      _value = new ASTState.CircularValue();
      unassignedBeforeCondition_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTState state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedBeforeCondition_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedBeforeCondition_Variable_value = unassignedBeforeCondition_compute(v);
        if (((Boolean)_value.value) != new_unassignedBeforeCondition_Variable_value) {
          state.setChangeInCycle();
          _value.value = new_unassignedBeforeCondition_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedBeforeCondition_Variable_values.put(_parameters, new_unassignedBeforeCondition_Variable_value);

      state.leaveCircle();
      return new_unassignedBeforeCondition_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedBeforeCondition_Variable_value = unassignedBeforeCondition_compute(v);
      if (((Boolean)_value.value) != new_unassignedBeforeCondition_Variable_value) {
        state.setChangeInCycle();
        _value.value = new_unassignedBeforeCondition_Variable_value;
      }
      return new_unassignedBeforeCondition_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private boolean unassignedBeforeCondition_compute(Variable v) {
      if (!unassignedAfterInit(v)) {
        return false;
      } else if (!unassignedAfterUpdate(v)) {
        return false;
      }
      return true;
    }
  /** @apilevel internal */
  private void unassignedAfterUpdate_Variable_reset() {
    unassignedAfterUpdate_Variable_values = null;
  }
  protected java.util.Map unassignedAfterUpdate_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1514")
  public boolean unassignedAfterUpdate(Variable v) {
    Object _parameters = v;
    if (unassignedAfterUpdate_Variable_values == null) unassignedAfterUpdate_Variable_values = new java.util.HashMap(4);
    ASTState.CircularValue _value;
    if (unassignedAfterUpdate_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfterUpdate_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTState.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTState.CircularValue) _cache;
      }
    } else {
      _value = new ASTState.CircularValue();
      unassignedAfterUpdate_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTState state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfterUpdate_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfterUpdate_Variable_value = unassignedAfterUpdate_compute(v);
        if (((Boolean)_value.value) != new_unassignedAfterUpdate_Variable_value) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfterUpdate_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfterUpdate_Variable_values.put(_parameters, new_unassignedAfterUpdate_Variable_value);

      state.leaveCircle();
      return new_unassignedAfterUpdate_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfterUpdate_Variable_value = unassignedAfterUpdate_compute(v);
      if (((Boolean)_value.value) != new_unassignedAfterUpdate_Variable_value) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfterUpdate_Variable_value;
      }
      return new_unassignedAfterUpdate_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private boolean unassignedAfterUpdate_compute(Variable v) {
      if (!unassignedBeforeCondition(v)) { // Starts a circular evaluation here.
        return false;
      }
      if (getNumUpdateStmt() > 0) {
        return getUpdateStmt(getNumUpdateStmt() - 1).unassignedAfter(v);
      }
      if (!getStmt().unassignedAfter(v)) {
        return false;
      }
      for (ContinueStmt stmt : targetContinues()) {
        if (!stmt.unassignedAfterReachedFinallyBlocks(v)) {
          return false;
        }
      }
      return true;
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
   * @aspect VariableScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:165
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="VariableScope", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:165")
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
      if (v != null) {
        return v;
      }
      return lookupVariable(name);
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
   * @aspect VariableScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:209
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="VariableScope", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:209")
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
    VariableDeclarator localVariableDeclaration_String_value = localVariableDeclaration_compute(name);
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
  private VariableDeclarator localVariableDeclaration_compute(String name) {
      for (Stmt stmt : getInitStmtList()) {
        VariableDeclarator decl = stmt.variableDeclaration(name);
        if (decl != null) {
          return decl;
        }
      }
      return null;
    }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:567
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:567")
  public boolean continueLabel() {
    boolean continueLabel_value = true;
    return continueLabel_value;
  }
  /** @apilevel internal */
  private void canCompleteNormally_reset() {
    canCompleteNormally_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle canCompleteNormally_computed = null;

  /** @apilevel internal */
  protected boolean canCompleteNormally_value;

  /**
   * @attribute syn
   * @aspect UnreachableStatements
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:50
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="UnreachableStatements", declaredAt="/home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:50")
  public boolean canCompleteNormally() {
    ASTState state = state();
    if (canCompleteNormally_computed == ASTState.NON_CYCLE || canCompleteNormally_computed == state().cycle()) {
      return canCompleteNormally_value;
    }
    canCompleteNormally_value = reachable() && hasCondition()
          && (!getCondition().isConstant() || !getCondition().isTrue()) || reachableBreak();
    if (state().inCircle()) {
      canCompleteNormally_computed = state().cycle();
    
    } else {
      canCompleteNormally_computed = ASTState.NON_CYCLE;
    
    }
    return canCompleteNormally_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:78
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:78")
  public boolean modifiedInScope(Variable var) {
    {
        for (Stmt stmt : getInitStmtList()) {
          if (stmt.modifiedInScope(var)) {
            return true;
          }
        }
        for (Stmt stmt : getUpdateStmtList()) {
          if (stmt.modifiedInScope(var)) {
            return true;
          }
        }
        return getStmt().modifiedInScope(var);
      }
  }
  /**
   * @attribute syn
   * @aspect BooleanExpressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:24
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BooleanExpressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:24")
  public boolean definesLabel() {
    boolean definesLabel_value = true;
    return definesLabel_value;
  }
  /** @apilevel internal */
  private void cond_label_Body_reset() {
    cond_label_Body_computed = null;
    cond_label_Body_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map cond_label_Body_values;
  /** @apilevel internal */
  protected java.util.Map cond_label_Body_computed;
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:202
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:202")
  public Body.Label cond_label(Body b) {
    Object _parameters = b;
    if (cond_label_Body_computed == null) cond_label_Body_computed = new java.util.HashMap(4);
    if (cond_label_Body_values == null) cond_label_Body_values = new java.util.HashMap(4);
    ASTState state = state();
    if (cond_label_Body_values.containsKey(_parameters)
        && cond_label_Body_computed.containsKey(_parameters)
        && (cond_label_Body_computed.get(_parameters) == ASTState.NON_CYCLE || cond_label_Body_computed.get(_parameters) == state().cycle())) {
      return (Body.Label) cond_label_Body_values.get(_parameters);
    }
    Body.Label cond_label_Body_value = newLabel(b);
    if (state().inCircle()) {
      cond_label_Body_values.put(_parameters, cond_label_Body_value);
      cond_label_Body_computed.put(_parameters, state().cycle());
    
    } else {
      cond_label_Body_values.put(_parameters, cond_label_Body_value);
      cond_label_Body_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return cond_label_Body_value;
  }
  /** @apilevel internal */
  private void body_label_Body_reset() {
    body_label_Body_computed = null;
    body_label_Body_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map body_label_Body_values;
  /** @apilevel internal */
  protected java.util.Map body_label_Body_computed;
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:203
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:203")
  public Body.Label body_label(Body b) {
    Object _parameters = b;
    if (body_label_Body_computed == null) body_label_Body_computed = new java.util.HashMap(4);
    if (body_label_Body_values == null) body_label_Body_values = new java.util.HashMap(4);
    ASTState state = state();
    if (body_label_Body_values.containsKey(_parameters)
        && body_label_Body_computed.containsKey(_parameters)
        && (body_label_Body_computed.get(_parameters) == ASTState.NON_CYCLE || body_label_Body_computed.get(_parameters) == state().cycle())) {
      return (Body.Label) body_label_Body_values.get(_parameters);
    }
    Body.Label body_label_Body_value = newLabel(b);
    if (state().inCircle()) {
      body_label_Body_values.put(_parameters, body_label_Body_value);
      body_label_Body_computed.put(_parameters, state().cycle());
    
    } else {
      body_label_Body_values.put(_parameters, body_label_Body_value);
      body_label_Body_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return body_label_Body_value;
  }
  /** @apilevel internal */
  private void update_label_Body_reset() {
    update_label_Body_computed = null;
    update_label_Body_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map update_label_Body_values;
  /** @apilevel internal */
  protected java.util.Map update_label_Body_computed;
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:204
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:204")
  public Body.Label update_label(Body b) {
    Object _parameters = b;
    if (update_label_Body_computed == null) update_label_Body_computed = new java.util.HashMap(4);
    if (update_label_Body_values == null) update_label_Body_values = new java.util.HashMap(4);
    ASTState state = state();
    if (update_label_Body_values.containsKey(_parameters)
        && update_label_Body_computed.containsKey(_parameters)
        && (update_label_Body_computed.get(_parameters) == ASTState.NON_CYCLE || update_label_Body_computed.get(_parameters) == state().cycle())) {
      return (Body.Label) update_label_Body_values.get(_parameters);
    }
    Body.Label update_label_Body_value = newLabel(b);
    if (state().inCircle()) {
      update_label_Body_values.put(_parameters, update_label_Body_value);
      update_label_Body_computed.put(_parameters, state().cycle());
    
    } else {
      update_label_Body_values.put(_parameters, update_label_Body_value);
      update_label_Body_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return update_label_Body_value;
  }
  /** @apilevel internal */
  private void end_label_Body_reset() {
    end_label_Body_computed = null;
    end_label_Body_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map end_label_Body_values;
  /** @apilevel internal */
  protected java.util.Map end_label_Body_computed;
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:205
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:205")
  public Body.Label end_label(Body b) {
    Object _parameters = b;
    if (end_label_Body_computed == null) end_label_Body_computed = new java.util.HashMap(4);
    if (end_label_Body_values == null) end_label_Body_values = new java.util.HashMap(4);
    ASTState state = state();
    if (end_label_Body_values.containsKey(_parameters)
        && end_label_Body_computed.containsKey(_parameters)
        && (end_label_Body_computed.get(_parameters) == ASTState.NON_CYCLE || end_label_Body_computed.get(_parameters) == state().cycle())) {
      return (Body.Label) end_label_Body_values.get(_parameters);
    }
    Body.Label end_label_Body_value = newLabel(b);
    if (state().inCircle()) {
      end_label_Body_values.put(_parameters, end_label_Body_value);
      end_label_Body_computed.put(_parameters, state().cycle());
    
    } else {
      end_label_Body_values.put(_parameters, end_label_Body_value);
      end_label_Body_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return end_label_Body_value;
  }
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:235
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:235")
  public Body.Label break_label(Body b) {
    Body.Label break_label_Body_value = end_label(b);
    return break_label_Body_value;
  }
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:250
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:250")
  public Body.Label continue_label(Body b) {
    Body.Label continue_label_Body_value = update_label(b);
    return continue_label_Body_value;
  }
  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:42
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="VariableScope", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:42")
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:230
   * @apilevel internal
   */
  public Stmt Define_branchTarget(ASTNode _callerNode, ASTNode _childNode, Stmt branch) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return branch.canBranchTo(this) ? this : branchTarget(branch);
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:230
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute branchTarget
   */
  protected boolean canDefine_branchTarget(ASTNode _callerNode, ASTNode _childNode, Stmt branch) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:256
   * @apilevel internal
   */
  public boolean Define_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (_callerNode == getUpdateStmtListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:817
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      {
          if (!getStmt().assignedAfter(v)) {
            return false;
          }
          for (ContinueStmt stmt :  targetContinues()) {
            if (!stmt.assignedAfterReachedFinallyBlocks(v)) {
              return false;
            }
          }
          return true;
        }
    }
    else if (getStmtNoTransform() != null && _callerNode == getStmt()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:806
      {
          if (hasCondition() && getCondition().assignedAfterTrue(v)) {
            return true;
          }
          if (!hasCondition() && assignedAfterInitialization(v)) {
            return true;
          }
          return false;
        }
    }
    else if (_callerNode == getConditionOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:803
      return assignedAfterInitialization(v);
    }
    else if (_callerNode == getInitStmtListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:794
      int i = _callerNode.getIndexOfChild(_childNode);
      return i == 0 ? assignedBefore(v) : getInitStmt(i - 1).assignedAfter(v);
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
    if (_callerNode == getUpdateStmtListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1533
      int i = _callerNode.getIndexOfChild(_childNode);
      {
          if (!unassignedBeforeCondition(v)) { // Starts a circular evaluation here.
            return false;
          }
          if (i == 0) {
            if (!getStmt().unassignedAfter(v)) {
              return false;
            }
            for (ContinueStmt stmt : targetContinues()) {
              if (!stmt.unassignedAfterReachedFinallyBlocks(v)) {
                return false;
              }
            }
            return true;
          } else {
            return getUpdateStmt(i - 1).unassignedAfter(v);
          }
        }
    }
    else if (getStmtNoTransform() != null && _callerNode == getStmt()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1509
      return unassignedBeforeCondition(v) && (hasCondition()
            ? getCondition().unassignedAfterTrue(v)
            : unassignedAfterInit(v));
    }
    else if (_callerNode == getConditionOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1490
      return unassignedBeforeCondition(v);
    }
    else if (_callerNode == getInitStmtListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1486
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return childIndex == 0 ? unassignedBefore(v) : getInitStmt(childIndex-1).unassignedAfter(v);
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
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet<Variable> Define_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (getStmtNoTransform() != null && _callerNode == getStmt()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:163
      return localLookup(name);
    }
    else if (_callerNode == getUpdateStmtListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:161
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return localLookup(name);
    }
    else if (_callerNode == getConditionOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:159
      return localLookup(name);
    }
    else if (_callerNode == getInitStmtListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:149
      int index = _callerNode.getIndexOfChild(_childNode);
      {
          for (int i = index - 1; i >= 0; i -= 1) {
            VariableDeclarator decl = getInitStmt(i).variableDeclaration(name);
            if (decl != null) {
              return decl;
            }
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
   * @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:31
   * @apilevel internal
   */
  public VariableScope Define_outerScope(ASTNode _callerNode, ASTNode _childNode) {
    if (getStmtNoTransform() != null && _callerNode == getStmt()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:451
      return this;
    }
    else if (_callerNode == getInitStmtListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:449
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return this;
    }
    else {
      return getParent().Define_outerScope(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:31
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute outerScope
   */
  protected boolean canDefine_outerScope(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:523
   * @apilevel internal
   */
  public boolean Define_insideLoop(ASTNode _callerNode, ASTNode _childNode) {
    if (getStmtNoTransform() != null && _callerNode == getStmt()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:527
      return true;
    }
    else {
      return getParent().Define_insideLoop(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:523
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute insideLoop
   */
  protected boolean canDefine_insideLoop(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:49
   * @apilevel internal
   */
  public boolean Define_reachable(ASTNode _callerNode, ASTNode _childNode) {
    if (getStmtNoTransform() != null && _callerNode == getStmt()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:155
      return reachable()
            && (!hasCondition() || (!getCondition().isConstant() || !getCondition().isFalse()));
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
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:280
   * @apilevel internal
   */
  public boolean Define_reportUnreachable(ASTNode _callerNode, ASTNode _childNode) {
    if (getStmtNoTransform() != null && _callerNode == getStmt()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:211
      return reachable();
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
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:30
   * @apilevel internal
   */
  public boolean Define_inhModifiedInScope(ASTNode _callerNode, ASTNode _childNode, Variable var) {
    if (getStmtNoTransform() != null && _callerNode == getStmt()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:57
      return false;
    }
    else if (_callerNode == getUpdateStmtListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:56
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return modifiedInScope(var);
    }
    else if (_callerNode == getInitStmtListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:55
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return modifiedInScope(var);
    }
    else {
      return getParent().Define_inhModifiedInScope(this, _callerNode, var);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:30
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute inhModifiedInScope
   */
  protected boolean canDefine_inhModifiedInScope(ASTNode _callerNode, ASTNode _childNode, Variable var) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:49
   * @apilevel internal
   */
  public Body.Label Define_condition_false_label(ASTNode _callerNode, ASTNode _childNode, Body b) {
    if (_callerNode == getConditionOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:45
      return end_label(b);
    }
    else {
      return getParent().Define_condition_false_label(this, _callerNode, b);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:49
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute condition_false_label
   */
  protected boolean canDefine_condition_false_label(ASTNode _callerNode, ASTNode _childNode, Body b) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:50
   * @apilevel internal
   */
  public Body.Label Define_condition_true_label(ASTNode _callerNode, ASTNode _childNode, Body b) {
    if (_callerNode == getConditionOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:46
      return body_label(b);
    }
    else {
      return getParent().Define_condition_true_label(this, _callerNode, b);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:50
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute condition_true_label
   */
  protected boolean canDefine_condition_true_label(ASTNode _callerNode, ASTNode _childNode, Body b) {
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
    // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:453
    if (hasCondition() && !getCondition().type().isBoolean()) {
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
    if (hasCondition() && !getCondition().type().isBoolean()) {
      collection.add(errorf("the type of \"%s\" is %s which is not boolean",
                getCondition().prettyPrint(), getCondition().type().name()));
    }
  }
}
