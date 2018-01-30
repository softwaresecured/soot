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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:311
 * @astdecl SynchronizedStmt : Stmt ::= Expr Block MonitorExit;
 * @production SynchronizedStmt : {@link Stmt} ::= <span class="component">{@link Expr}</span> <span class="component">{@link Block}</span> <span class="component">{@link MonitorExit}</span>;

 */
public class SynchronizedStmt extends Stmt implements Cloneable, FinallyHost {
  /**
   * @aspect DefiniteUnassignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1236
   */
  public Block getFinallyBlock() {
    return getMonitorExit();
  }
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:579
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("synchronized (");
    out.print(getExpr());
    out.print(") ");
    out.print(getBlock());
  }
  /**
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:295
   */
  public void emitFinallyCode(Body b) {
    //b.setLine(this);
    b.add(b.newExitMonitorStmt(monitor(b), this));
  }
  /**
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:476
   */
  public void jimpleEmit(Body b) {
    final Body.Label lbl_end = b.newLabel(this);
    //b.setLine(this);
    b.add(b.newEnterMonitorStmt(monitor(b), this));

    final Body.Label lbl_begin = b.addNewLabel(this);
    exceptionRanges(b).add(lbl_begin.stmt);

    getBlock().jimpleEmit(b);

    //if (getBlock().canCompleteNormally()) {
      emitFinallyCode(b);
      b.addGoTo(lbl_end, this);
    //}

    // emitExceptionHandler
    FinallyInfo info = finally_info(b);
    b.addLabel(info.label_catch);
    b.addLabel(info.label_finally);

    emitFinallyCode(b);
    b.add(info.rethrow);

    // end-label
    b.addLabel(lbl_end);

    // createExceptionTable
    // TODO: This looks... odd. Is there a reason we're adding inter-weaving exception handlers?
    for (Iterator<soot.jimple.Stmt> iter = exceptionRanges(b).iterator(); iter.hasNext(); ) {
      soot.jimple.Stmt stmtBegin = iter.next();
      soot.jimple.Stmt stmtEnd   = iter.hasNext() ? iter.next() : lbl_end.stmt;
      if (stmtBegin != stmtEnd)
        b.addTrap(typeThrowable(), stmtBegin, stmtEnd, info.label_catch);
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public SynchronizedStmt() {
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
    children = new ASTNode[3];
  }
  /**
   * @declaredat ASTNode:13
   */
  @ASTNodeAnnotation.Constructor(
    name = {"Expr", "Block"},
    type = {"Expr", "Block"},
    kind = {"Child", "Child"}
  )
  public SynchronizedStmt(Expr p0, Block p1) {
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
    assignedAfter_Variable_reset();
    unassignedAfter_Variable_reset();
    getMonitorExit_reset();
    canCompleteNormally_reset();
    monitor_Body_reset();
    finally_info_Body_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:43
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:47
   */
  public SynchronizedStmt clone() throws CloneNotSupportedException {
    SynchronizedStmt node = (SynchronizedStmt) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:52
   */
  public SynchronizedStmt copy() {
    try {
      SynchronizedStmt node = (SynchronizedStmt) clone();
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
   * @declaredat ASTNode:71
   */
  @Deprecated
  public SynchronizedStmt fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:81
   */
  public SynchronizedStmt treeCopyNoTransform() {
    SynchronizedStmt tree = (SynchronizedStmt) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 2:
          tree.children[i] = null;
          continue;
        }
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
   * @declaredat ASTNode:106
   */
  public SynchronizedStmt treeCopy() {
    SynchronizedStmt tree = (SynchronizedStmt) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 2:
          tree.children[i] = null;
          continue;
        }
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
   * @declaredat ASTNode:125
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Expr child.
   * @param node The new node to replace the Expr child.
   * @apilevel high-level
   */
  public void setExpr(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Expr child.
   * @return The current node used as the Expr child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Expr")
  public Expr getExpr() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the Expr child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Expr child.
   * @apilevel low-level
   */
  public Expr getExprNoTransform() {
    return (Expr) getChildNoTransform(0);
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
   * Retrieves the MonitorExit child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the MonitorExit child.
   * @apilevel low-level
   */
  public MonitorExit getMonitorExitNoTransform() {
    return (MonitorExit) getChildNoTransform(2);
  }
  /**
   * Retrieves the child position of the optional child MonitorExit.
   * @return The the child position of the optional child MonitorExit.
   * @apilevel low-level
   */
  protected int getMonitorExitChildPosition() {
    return 2;
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
  /**
   * @attribute syn
   * @aspect DefiniteUnassignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1243
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1243")
  public boolean unassignedAfterFinally(Variable v) {
    boolean unassignedAfterFinally_Variable_value = true;
    return unassignedAfterFinally_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteUnassignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1248
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1248")
  public boolean assignedAfterFinally(Variable v) {
    boolean assignedAfterFinally_Variable_value = false;
    return assignedAfterFinally_Variable_value;
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
  /** @apilevel internal */
  private void getMonitorExit_reset() {
    getMonitorExit_computed = false;
    
    getMonitorExit_value = null;
  }
  /** @apilevel internal */
  protected boolean getMonitorExit_computed = false;

  /** @apilevel internal */
  protected MonitorExit getMonitorExit_value;

  /**
   * @attribute syn nta
   * @aspect MonitorExit
   * @declaredat /home/olivier/projects/extendj/java4/frontend/MonitorExit.jrag:32
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="MonitorExit", declaredAt="/home/olivier/projects/extendj/java4/frontend/MonitorExit.jrag:32")
  public MonitorExit getMonitorExit() {
    ASTState state = state();
    if (getMonitorExit_computed) {
      return (MonitorExit) getChild(getMonitorExitChildPosition());
    }
    state().enterLazyAttribute();
    getMonitorExit_value = new MonitorExit(this);
    setChild(getMonitorExit_value, getMonitorExitChildPosition());
    getMonitorExit_computed = true;
    state().leaveLazyAttribute();
    MonitorExit node = (MonitorExit) this.getChild(getMonitorExitChildPosition());
    return node;
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
    canCompleteNormally_value = getBlock().canCompleteNormally();
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
    boolean modifiedInScope_Variable_value = getBlock().modifiedInScope(var);
    return modifiedInScope_Variable_value;
  }
  /** @apilevel internal */
  private void monitor_Body_reset() {
    monitor_Body_computed = null;
    monitor_Body_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map monitor_Body_values;
  /** @apilevel internal */
  protected java.util.Map monitor_Body_computed;
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:294
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:294")
  public soot.Local monitor(Body b) {
    Object _parameters = b;
    if (monitor_Body_computed == null) monitor_Body_computed = new java.util.HashMap(4);
    if (monitor_Body_values == null) monitor_Body_values = new java.util.HashMap(4);
    ASTState state = state();
    if (monitor_Body_values.containsKey(_parameters)
        && monitor_Body_computed.containsKey(_parameters)
        && (monitor_Body_computed.get(_parameters) == ASTState.NON_CYCLE || monitor_Body_computed.get(_parameters) == state().cycle())) {
      return (soot.Local) monitor_Body_values.get(_parameters);
    }
    soot.Local monitor_Body_value = b.newTemp(getExpr().eval(b));
    if (state().inCircle()) {
      monitor_Body_values.put(_parameters, monitor_Body_value);
      monitor_Body_computed.put(_parameters, state().cycle());
    
    } else {
      monitor_Body_values.put(_parameters, monitor_Body_value);
      monitor_Body_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return monitor_Body_value;
  }
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:423
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:423")
  public ArrayList<soot.jimple.Stmt> exceptionRanges(Body b) {
    ArrayList<soot.jimple.Stmt> exceptionRanges_Body_value = b.lazy_exceptionRanges(this);
    return exceptionRanges_Body_value;
  }
  /** @apilevel internal */
  private void finally_info_Body_reset() {
    finally_info_Body_computed = null;
    finally_info_Body_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map finally_info_Body_values;
  /** @apilevel internal */
  protected java.util.Map finally_info_Body_computed;
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:472
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:472")
  public FinallyInfo finally_info(Body b) {
    Object _parameters = b;
    if (finally_info_Body_computed == null) finally_info_Body_computed = new java.util.HashMap(4);
    if (finally_info_Body_values == null) finally_info_Body_values = new java.util.HashMap(4);
    ASTState state = state();
    if (finally_info_Body_values.containsKey(_parameters)
        && finally_info_Body_computed.containsKey(_parameters)
        && (finally_info_Body_computed.get(_parameters) == ASTState.NON_CYCLE || finally_info_Body_computed.get(_parameters) == state().cycle())) {
      return (FinallyInfo) finally_info_Body_values.get(_parameters);
    }
    FinallyInfo finally_info_Body_value = new FinallyInfo(b, typeThrowable(), this);
    if (state().inCircle()) {
      finally_info_Body_values.put(_parameters, finally_info_Body_value);
      finally_info_Body_computed.put(_parameters, state().cycle());
    
    } else {
      finally_info_Body_values.put(_parameters, finally_info_Body_value);
      finally_info_Body_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return finally_info_Body_value;
  }
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:474
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:474")
  public Body.CatchLabel label_catch(Body b) {
    Body.CatchLabel label_catch_Body_value = finally_info(b).label_catch;
    return label_catch_Body_value;
  }
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:475
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:475")
  public Body.Label label_finally(Body b) {
    Body.Label label_finally_Body_value = finally_info(b).label_finally;
    return label_finally_Body_value;
  }
  /**
   * @attribute inh
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:308
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:308")
  public boolean enclosedByExceptionHandler() {
    boolean enclosedByExceptionHandler_value = getParent().Define_enclosedByExceptionHandler(this, null);
    return enclosedByExceptionHandler_value;
  }
  /**
   * @attribute inh
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:444
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:444")
  public TypeDecl typeThrowable() {
    TypeDecl typeThrowable_value = getParent().Define_typeThrowable(this, null);
    return typeThrowable_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:273
   * @apilevel internal
   */
  public FinallyHost Define_enclosingFinally(ASTNode _callerNode, ASTNode _childNode, Stmt branch) {
    if (getMonitorExitNoTransform() != null && _callerNode == getMonitorExit()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:284
      return enclosingFinally(branch);
    }
    else {
      int childIndex = this.getIndexOfChild(_callerNode);
      return this;
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:273
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute enclosingFinally
   */
  protected boolean canDefine_enclosingFinally(ASTNode _callerNode, ASTNode _childNode, Stmt branch) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:256
   * @apilevel internal
   */
  public boolean Define_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:845
      return getExpr().assignedAfter(v);
    }
    else if (getExprNoTransform() != null && _callerNode == getExpr()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:843
      return assignedBefore(v);
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
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1568
      return getExpr().unassignedAfter(v);
    }
    else if (getExprNoTransform() != null && _callerNode == getExpr()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1566
      return unassignedBefore(v);
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:49
   * @apilevel internal
   */
  public boolean Define_reachable(ASTNode _callerNode, ASTNode _childNode) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:165
      return reachable();
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
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:217
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
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:307
   * @apilevel internal
   */
  public boolean Define_enclosedByExceptionHandler(ASTNode _callerNode, ASTNode _childNode) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:314
      return true;
    }
    else {
      return getParent().Define_enclosedByExceptionHandler(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:307
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute enclosedByExceptionHandler
   */
  protected boolean canDefine_enclosedByExceptionHandler(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:413
   * @apilevel internal
   */
  public ArrayList<soot.jimple.Stmt> Define_exceptionRanges(ASTNode _callerNode, ASTNode _childNode, Body b) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:428
      return exceptionRanges(b);
    }
    else {
      return getParent().Define_exceptionRanges(this, _callerNode, b);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:413
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute exceptionRanges
   */
  protected boolean canDefine_exceptionRanges(ASTNode _callerNode, ASTNode _childNode, Body b) {
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
    // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:488
    if (!getExpr().type().isReferenceType() || getExpr().type().isNull()) {
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
    if (!getExpr().type().isReferenceType() || getExpr().type().isNull()) {
      collection.add(error("*** The type of the expression must be a reference"));
    }
  }
}
