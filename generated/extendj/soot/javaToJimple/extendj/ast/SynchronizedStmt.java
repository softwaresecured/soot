/* This file was generated with JastAdd2 (http://jastadd.org) version 2.2.2 */
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
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:311
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
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:358
   */
  public void emitFinallyCode(Body b) {
    //b.setLine(this);
    b.add(b.newExitMonitorStmt(monitor(b), this));
  }
  /**
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:519
   */
  public void jimplify2(Body b) {
    //b.setLine(this);
    b.add(b.newEnterMonitorStmt(monitor(b), this));
    b.addLabel(label_begin());
    exceptionRanges().add(label_begin());
    getBlock().jimplify2(b);
    if(getBlock().canCompleteNormally()) {
      emitFinallyCode(b);
      b.add(b.newGotoStmt(label_end(), this));
    }
    b.addLabel(label_exception_handler());

    // emitExceptionHandler
    Local l = b.newTemp(typeThrowable().getSootType(), this);
    b.add(b.newIdentityStmt(l, b.newCaughtExceptionRef(this), this));
    emitFinallyCode(b);
    soot.jimple.Stmt throwStmt = b.newThrowStmt(l, this);
    throwStmt.addTag(new soot.tagkit.ThrowCreatedByCompilerTag());
    b.add(throwStmt);
    b.addLabel(label_end());

    // createExceptionTable
    for(Iterator iter = exceptionRanges().iterator(); iter.hasNext(); ) {
      soot.jimple.Stmt stmtBegin = (soot.jimple.Stmt)iter.next();
      soot.jimple.Stmt stmtEnd;
      if(iter.hasNext())
        stmtEnd = (soot.jimple.Stmt)iter.next();
      else
        stmtEnd = label_end();
      if(stmtBegin != stmtEnd)
        b.addTrap(typeThrowable(), stmtBegin, stmtEnd, label_exception_handler());
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
  public SynchronizedStmt(Expr p0, Block p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:18
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:24
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:28
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    assignedAfter_Variable_reset();
    unassignedAfter_Variable_reset();
    getMonitorExit_reset();
    canCompleteNormally_reset();
    monitor_Body_reset();
    exceptionRanges_reset();
    label_begin_reset();
    label_end_reset();
    label_finally_reset();
    label_finally_block_reset();
    label_exception_handler_reset();
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
    ASTNode$State.CircularValue _value;
    if (assignedAfter_Variable_values.containsKey(_parameters)) {
      Object _cache = assignedAfter_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      assignedAfter_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_assignedAfter_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_assignedAfter_Variable_value = getBlock().assignedAfter(v);
        if (new_assignedAfter_Variable_value != ((Boolean)_value.value)) {
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
      if (new_assignedAfter_Variable_value != ((Boolean)_value.value)) {
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
    ASTNode$State.CircularValue _value;
    if (unassignedAfter_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfter_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      unassignedAfter_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfter_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfter_Variable_value = getBlock().unassignedAfter(v);
        if (new_unassignedAfter_Variable_value != ((Boolean)_value.value)) {
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
      if (new_unassignedAfter_Variable_value != ((Boolean)_value.value)) {
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
    ASTNode$State state = state();
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
  protected ASTNode$State.Cycle canCompleteNormally_computed = null;

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
    ASTNode$State state = state();
    if (canCompleteNormally_computed == ASTNode$State.NON_CYCLE || canCompleteNormally_computed == state().cycle()) {
      return canCompleteNormally_value;
    }
    canCompleteNormally_value = getBlock().canCompleteNormally();
    if (state().inCircle()) {
      canCompleteNormally_computed = state().cycle();
    
    } else {
      canCompleteNormally_computed = ASTNode$State.NON_CYCLE;
    
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
    monitor_Body_computed = new java.util.HashMap(4);
    monitor_Body_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map monitor_Body_values;
  /** @apilevel internal */
  protected java.util.Map monitor_Body_computed;
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:354
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:354")
  public soot.Local monitor(Body b) {
    Object _parameters = b;
    if (monitor_Body_computed == null) monitor_Body_computed = new java.util.HashMap(4);
    if (monitor_Body_values == null) monitor_Body_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (monitor_Body_values.containsKey(_parameters) && monitor_Body_computed != null
        && monitor_Body_computed.containsKey(_parameters)
        && (monitor_Body_computed.get(_parameters) == ASTNode$State.NON_CYCLE || monitor_Body_computed.get(_parameters) == state().cycle())) {
      return (soot.Local) monitor_Body_values.get(_parameters);
    }
    soot.Local monitor_Body_value = monitor_compute(b);
    if (state().inCircle()) {
      monitor_Body_values.put(_parameters, monitor_Body_value);
      monitor_Body_computed.put(_parameters, state().cycle());
    
    } else {
      monitor_Body_values.put(_parameters, monitor_Body_value);
      monitor_Body_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return monitor_Body_value;
  }
  /** @apilevel internal */
  private soot.Local monitor_compute(Body b) {
      return b.newTemp(getExpr().eval(b));
    }
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:372
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:372")
  public boolean needsFinallyTrap() {
    boolean needsFinallyTrap_value = enclosedByExceptionHandler();
    return needsFinallyTrap_value;
  }
  /** @apilevel internal */
  private void exceptionRanges_reset() {
    exceptionRanges_computed = null;
    exceptionRanges_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle exceptionRanges_computed = null;

  /** @apilevel internal */
  protected ArrayList<soot.jimple.Stmt> exceptionRanges_value;

  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:480
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:480")
  public ArrayList<soot.jimple.Stmt> exceptionRanges() {
    ASTNode$State state = state();
    if (exceptionRanges_computed == ASTNode$State.NON_CYCLE || exceptionRanges_computed == state().cycle()) {
      return exceptionRanges_value;
    }
    exceptionRanges_value = new ArrayList<>();
    if (state().inCircle()) {
      exceptionRanges_computed = state().cycle();
    
    } else {
      exceptionRanges_computed = ASTNode$State.NON_CYCLE;
    
    }
    return exceptionRanges_value;
  }
  /** @apilevel internal */
  private void label_begin_reset() {
    label_begin_computed = null;
    label_begin_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle label_begin_computed = null;

  /** @apilevel internal */
  protected soot.jimple.Stmt label_begin_value;

  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:513
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:513")
  public soot.jimple.Stmt label_begin() {
    ASTNode$State state = state();
    if (label_begin_computed == ASTNode$State.NON_CYCLE || label_begin_computed == state().cycle()) {
      return label_begin_value;
    }
    label_begin_value = newLabel();
    if (state().inCircle()) {
      label_begin_computed = state().cycle();
    
    } else {
      label_begin_computed = ASTNode$State.NON_CYCLE;
    
    }
    return label_begin_value;
  }
  /** @apilevel internal */
  private void label_end_reset() {
    label_end_computed = null;
    label_end_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle label_end_computed = null;

  /** @apilevel internal */
  protected soot.jimple.Stmt label_end_value;

  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:514
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:514")
  public soot.jimple.Stmt label_end() {
    ASTNode$State state = state();
    if (label_end_computed == ASTNode$State.NON_CYCLE || label_end_computed == state().cycle()) {
      return label_end_value;
    }
    label_end_value = newLabel();
    if (state().inCircle()) {
      label_end_computed = state().cycle();
    
    } else {
      label_end_computed = ASTNode$State.NON_CYCLE;
    
    }
    return label_end_value;
  }
  /** @apilevel internal */
  private void label_finally_reset() {
    label_finally_computed = null;
    label_finally_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle label_finally_computed = null;

  /** @apilevel internal */
  protected soot.jimple.Stmt label_finally_value;

  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:515
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:515")
  public soot.jimple.Stmt label_finally() {
    ASTNode$State state = state();
    if (label_finally_computed == ASTNode$State.NON_CYCLE || label_finally_computed == state().cycle()) {
      return label_finally_value;
    }
    label_finally_value = newLabel();
    if (state().inCircle()) {
      label_finally_computed = state().cycle();
    
    } else {
      label_finally_computed = ASTNode$State.NON_CYCLE;
    
    }
    return label_finally_value;
  }
  /** @apilevel internal */
  private void label_finally_block_reset() {
    label_finally_block_computed = null;
    label_finally_block_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle label_finally_block_computed = null;

  /** @apilevel internal */
  protected soot.jimple.Stmt label_finally_block_value;

  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:341
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:341")
  public soot.jimple.Stmt label_finally_block() {
    ASTNode$State state = state();
    if (label_finally_block_computed == ASTNode$State.NON_CYCLE || label_finally_block_computed == state().cycle()) {
      return label_finally_block_value;
    }
    label_finally_block_value = newLabel();
    if (state().inCircle()) {
      label_finally_block_computed = state().cycle();
    
    } else {
      label_finally_block_computed = ASTNode$State.NON_CYCLE;
    
    }
    return label_finally_block_value;
  }
  /** @apilevel internal */
  private void label_exception_handler_reset() {
    label_exception_handler_computed = null;
    label_exception_handler_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle label_exception_handler_computed = null;

  /** @apilevel internal */
  protected soot.jimple.Stmt label_exception_handler_value;

  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:517
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:517")
  public soot.jimple.Stmt label_exception_handler() {
    ASTNode$State state = state();
    if (label_exception_handler_computed == ASTNode$State.NON_CYCLE || label_exception_handler_computed == state().cycle()) {
      return label_exception_handler_value;
    }
    label_exception_handler_value = newLabel();
    if (state().inCircle()) {
      label_exception_handler_computed = state().cycle();
    
    } else {
      label_exception_handler_computed = ASTNode$State.NON_CYCLE;
    
    }
    return label_exception_handler_value;
  }
  /**
   * @attribute inh
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:374
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:374")
  public boolean enclosedByExceptionHandler() {
    boolean enclosedByExceptionHandler_value = getParent().Define_enclosedByExceptionHandler(this, null);
    return enclosedByExceptionHandler_value;
  }
  /**
   * @attribute inh
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:499
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:499")
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
  protected boolean canDefine_reportUnreachable(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:373
   * @apilevel internal
   */
  public boolean Define_enclosedByExceptionHandler(ASTNode _callerNode, ASTNode _childNode) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:380
      return true;
    }
    else {
      return getParent().Define_enclosedByExceptionHandler(this, _callerNode);
    }
  }
  protected boolean canDefine_enclosedByExceptionHandler(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:475
   * @apilevel internal
   */
  public ArrayList Define_exceptionRanges(ASTNode _callerNode, ASTNode _childNode) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:484
      return exceptionRanges();
    }
    else {
      return getParent().Define_exceptionRanges(this, _callerNode);
    }
  }
  protected boolean canDefine_exceptionRanges(ASTNode _callerNode, ASTNode _childNode) {
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
  protected void collect_contributors_CompilationUnit_problems(CompilationUnit _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:486
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
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    if (!getExpr().type().isReferenceType() || getExpr().type().isNull()) {
      collection.add(error("*** The type of the expression must be a reference"));
    }
  }
}
