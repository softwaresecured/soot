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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:319
 * @production TryStmt : {@link Stmt} ::= <span class="component">{@link Block}</span> <span class="component">{@link CatchClause}*</span> <span class="component">[Finally:{@link Block}]</span> <span class="component">ExceptionHandler:{@link Block}</span>;

 */
public class TryStmt extends Stmt implements Cloneable, FinallyHost {
  /**
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:116
   */
  public void collectBranches(Collection<Stmt> c) {
    c.addAll(escapedBranches());
  }
  /**
   * @aspect DefiniteUnassignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1232
   */
  public Block getFinallyBlock() {
    return getFinally();
  }
  /**
   * @aspect ExceptionHandling
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:307
   */
  protected boolean reachedException(TypeDecl catchType) {
    boolean found = false;
    // Found is true if the exception type is caught by a catch clause.
    for (int i = 0; i < getNumCatchClause() && !found; i++) {
      if (getCatchClause(i).handles(catchType)) {
        found = true;
      }
    }
    // If an exception is thrown in the block and the exception is not caught and
    // either there is no finally block or the finally block can complete normally.
    if (!found && (!hasNonEmptyFinally() || getFinally().canCompleteNormally()) ) {
      if (getBlock().reachedException(catchType)) {
        return true;
      }
    }
    // Even if the exception is caught by the catch clauses they may
    // throw new exceptions.
    for (int i = 0; i < getNumCatchClause(); i++) {
      if (getCatchClause(i).reachedException(catchType)) {
        return true;
      }
    }
    return hasNonEmptyFinally() && getFinally().reachedException(catchType);
  }
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:593
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("try ");
    out.print(getBlock());
    out.print(" ");
    out.join(getCatchClauseList(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.print(" ");
      }
    });
    if (hasFinally()) {
      out.print(" finally ");
      out.print(getFinally());
    }
  }
  /**
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:344
   */
  public void emitFinallyCode(Body b) {
    if(hasFinally()) {
      // Clear cached attributes to force re-evaluation of local variables
      // NOTE: NEVER FLUSH. Some parts of extendj assume that they can compare by identity.
      // TODO: Verify we're still outputting something sane.
      getFinally().flushTreeCache();
      getFinally().jimplify2(b);
    }
  }
  /**
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:382
   */
  public void jimplify2(Body b) {
    ArrayList<soot.jimple.Stmt> ranges = exceptionRanges();
    b.addLabel(label_begin());
    ranges.add(label_begin());
    getBlock().jimplify2(b);
    soot.jimple.Stmt label_block_end  = null;
    soot.jimple.Stmt label_end        = null;

    if(getBlock().canCompleteNormally()) {
      if(hasFinally() && getNumCatchClause() != 0) {
        label_block_end = label_block_end();
        b.addLabel(label_block_end);
      }
      emitFinallyCode(b);
      //b.setLine(this);
      if((!hasFinally() || getFinally().canCompleteNormally()) && (getNumCatchClause() != 0 || hasFinally()/*needsFinallyTrap()*/)) {
        label_end = label_end();
        b.add(b.newGotoStmt(label_end, this));
      }
    }

    if (getNumCatchClause() != 0) {
      if (label_block_end == null)
        label_block_end = getCatchClause(0).label();

      ranges.add(label_block_end);
      ranges.add(label_block_end);

      for (CatchClause cc : getCatchClauses()) {
        //beginExceptionRange(b, ranges);
        cc.jimplify2(b);
        if (cc.getBlock().canCompleteNormally()) {
          // b.setLine(cc);
          endExceptionRange(b, ranges, cc.newLabel());
          emitFinallyCode(b);

          if (!hasFinally() || getFinally().canCompleteNormally()) {
            label_end = label_end();
            b.add(b.newGotoStmt(label_end, this));
          }

          beginExceptionRange(b, ranges);
        }
        // b.setLine(cc);
        //endExceptionRange(b, ranges);
      }
    }

    if (hasFinally() /*&& needsFinallyTrap()*/) {
      b.addLabel(label_exception_handler());
      emitExceptionHandler(b);
      //b.setLine(getFinally());
      //if(getFinally().canCompleteNormally())
      //  b.add(b.newGotoStmt(label_end(), this));
    }

    if (label_end != null)
      b.addLabel(label_end);

    // createExceptionTable
    for (CatchClause cc : getCatchClauses()) {
      for (Iterator<soot.jimple.Stmt> iter = ranges.iterator(); iter.hasNext(); ) {
        soot.jimple.Stmt bgn = iter.next();
        soot.jimple.Stmt end = iter.next();
        if (bgn != end) {
          // TODO: Is this still correct in for multi-catch clauses?
          for (TypeDecl t : cc.caughtExceptions())
            b.addTrap(t, bgn, end, cc.label());
        }

        if (end == label_block_end) break;
      }
    }

    if (hasFinally() /*&& needsFinallyTrap()*/) {
      for (Iterator<soot.jimple.Stmt> iter = ranges.iterator(); iter.hasNext(); ) {
        soot.jimple.Stmt bgn =                  iter.next();
        soot.jimple.Stmt end = iter.hasNext() ? iter.next() : label_exception_handler();

        if (bgn != end)
          b.addTrap(typeThrowable(), bgn, end, label_exception_handler());
      }
      /*
      b.addTrap(
        typeThrowable(),
        label_begin(),
        label_exception_handler(),
        label_exception_handler()
      );
      */
    }
  }
  /**
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:501
   */
  public void emitExceptionHandler(Body b) {
    Local l = b.newTemp(typeThrowable().getSootType(), this);
    //b.setLine(this);
    b.add(b.newIdentityStmt(l, b.newCaughtExceptionRef(this), this));
    emitFinallyCode(b);
    if (hasFinally()) {// && getFinally().canCompleteNormally()) {
      soot.jimple.Stmt throwStmt = b.newThrowStmt(l, this);
      throwStmt.addTag(new soot.tagkit.ThrowCreatedByCompilerTag());
      b.add(throwStmt);
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public TryStmt() {
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
    setChild(new List(), 1);
    setChild(new Opt(), 2);
  }
  /**
   * @declaredat ASTNode:15
   */
  public TryStmt(Block p0, List<CatchClause> p1, Opt<Block> p2) {
    setChild(p0, 0);
    setChild(p1, 1);
    setChild(p2, 2);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:21
   */
  protected int numChildren() {
    return 3;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:27
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:31
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    branches_reset();
    escapedBranches_reset();
    assignedAfter_Variable_reset();
    unassignedAfterFinally_Variable_reset();
    assignedAfterFinally_Variable_reset();
    unassignedBefore_Variable_reset();
    unassignedAfter_Variable_reset();
    hasNonEmptyFinally_reset();
    catchableException_TypeDecl_reset();
    getExceptionHandler_reset();
    canCompleteNormally_reset();
    label_begin_reset();
    label_block_end_reset();
    label_end_reset();
    label_finally_reset();
    label_finally_block_reset();
    label_exception_handler_reset();
    label_catch_end_reset();
    exceptionRanges_reset();
    handlesException_TypeDecl_reset();
    typeError_reset();
    typeRuntimeException_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:57
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:61
   */
  public TryStmt clone() throws CloneNotSupportedException {
    TryStmt node = (TryStmt) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:66
   */
  public TryStmt copy() {
    try {
      TryStmt node = (TryStmt) clone();
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
   * @declaredat ASTNode:85
   */
  @Deprecated
  public TryStmt fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:95
   */
  public TryStmt treeCopyNoTransform() {
    TryStmt tree = (TryStmt) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 3:
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
   * @declaredat ASTNode:120
   */
  public TryStmt treeCopy() {
    TryStmt tree = (TryStmt) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 3:
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
   * @declaredat ASTNode:139
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
  /**
   * Replaces the CatchClause list.
   * @param list The new list node to be used as the CatchClause list.
   * @apilevel high-level
   */
  public void setCatchClauseList(List<CatchClause> list) {
    setChild(list, 1);
  }
  /**
   * Retrieves the number of children in the CatchClause list.
   * @return Number of children in the CatchClause list.
   * @apilevel high-level
   */
  public int getNumCatchClause() {
    return getCatchClauseList().getNumChild();
  }
  /**
   * Retrieves the number of children in the CatchClause list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the CatchClause list.
   * @apilevel low-level
   */
  public int getNumCatchClauseNoTransform() {
    return getCatchClauseListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the CatchClause list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the CatchClause list.
   * @apilevel high-level
   */
  public CatchClause getCatchClause(int i) {
    return (CatchClause) getCatchClauseList().getChild(i);
  }
  /**
   * Check whether the CatchClause list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasCatchClause() {
    return getCatchClauseList().getNumChild() != 0;
  }
  /**
   * Append an element to the CatchClause list.
   * @param node The element to append to the CatchClause list.
   * @apilevel high-level
   */
  public void addCatchClause(CatchClause node) {
    List<CatchClause> list = (parent == null) ? getCatchClauseListNoTransform() : getCatchClauseList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addCatchClauseNoTransform(CatchClause node) {
    List<CatchClause> list = getCatchClauseListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the CatchClause list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setCatchClause(CatchClause node, int i) {
    List<CatchClause> list = getCatchClauseList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the CatchClause list.
   * @return The node representing the CatchClause list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="CatchClause")
  public List<CatchClause> getCatchClauseList() {
    List<CatchClause> list = (List<CatchClause>) getChild(1);
    return list;
  }
  /**
   * Retrieves the CatchClause list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the CatchClause list.
   * @apilevel low-level
   */
  public List<CatchClause> getCatchClauseListNoTransform() {
    return (List<CatchClause>) getChildNoTransform(1);
  }
  /**
   * @return the element at index {@code i} in the CatchClause list without
   * triggering rewrites.
   */
  public CatchClause getCatchClauseNoTransform(int i) {
    return (CatchClause) getCatchClauseListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the CatchClause list.
   * @return The node representing the CatchClause list.
   * @apilevel high-level
   */
  public List<CatchClause> getCatchClauses() {
    return getCatchClauseList();
  }
  /**
   * Retrieves the CatchClause list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the CatchClause list.
   * @apilevel low-level
   */
  public List<CatchClause> getCatchClausesNoTransform() {
    return getCatchClauseListNoTransform();
  }
  /**
   * Replaces the optional node for the Finally child. This is the <code>Opt</code>
   * node containing the child Finally, not the actual child!
   * @param opt The new node to be used as the optional node for the Finally child.
   * @apilevel low-level
   */
  public void setFinallyOpt(Opt<Block> opt) {
    setChild(opt, 2);
  }
  /**
   * Replaces the (optional) Finally child.
   * @param node The new node to be used as the Finally child.
   * @apilevel high-level
   */
  public void setFinally(Block node) {
    getFinallyOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional Finally child exists.
   * @return {@code true} if the optional Finally child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasFinally() {
    return getFinallyOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) Finally child.
   * @return The Finally child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Block getFinally() {
    return (Block) getFinallyOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the Finally child. This is the <code>Opt</code> node containing the child Finally, not the actual child!
   * @return The optional node for child the Finally child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="Finally")
  public Opt<Block> getFinallyOpt() {
    return (Opt<Block>) getChild(2);
  }
  /**
   * Retrieves the optional node for child Finally. This is the <code>Opt</code> node containing the child Finally, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Finally.
   * @apilevel low-level
   */
  public Opt<Block> getFinallyOptNoTransform() {
    return (Opt<Block>) getChildNoTransform(2);
  }
  /**
   * Retrieves the ExceptionHandler child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the ExceptionHandler child.
   * @apilevel low-level
   */
  public Block getExceptionHandlerNoTransform() {
    return (Block) getChildNoTransform(3);
  }
  /**
   * Retrieves the child position of the optional child ExceptionHandler.
   * @return The the child position of the optional child ExceptionHandler.
   * @apilevel low-level
   */
  protected int getExceptionHandlerChildPosition() {
    return 3;
  }
  /** @apilevel internal */
  private void branches_reset() {
    branches_computed = null;
    branches_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle branches_computed = null;

  /** @apilevel internal */
  protected Collection<Stmt> branches_value;

  /** All branches that reach this node. 
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:156
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BranchTarget", declaredAt="/home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:156")
  public Collection<Stmt> branches() {
    ASTNode$State state = state();
    if (branches_computed == ASTNode$State.NON_CYCLE || branches_computed == state().cycle()) {
      return branches_value;
    }
    branches_value = branches_compute();
    if (state().inCircle()) {
      branches_computed = state().cycle();
    
    } else {
      branches_computed = ASTNode$State.NON_CYCLE;
    
    }
    return branches_value;
  }
  /** @apilevel internal */
  private Collection<Stmt> branches_compute() {
      Collection<Stmt> set = new HashSet<Stmt>();
      getBlock().collectBranches(set);
      for (int i = 0; i < getNumCatchClause(); i++) {
        getCatchClause(i).collectBranches(set);
      }
      return set;
    }
  /** @apilevel internal */
  private void escapedBranches_reset() {
    escapedBranches_computed = null;
    escapedBranches_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle escapedBranches_computed = null;

  /** @apilevel internal */
  protected Collection<Stmt> escapedBranches_value;

  /** All branches that escape this node. 
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:166
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BranchTarget", declaredAt="/home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:166")
  public Collection<Stmt> escapedBranches() {
    ASTNode$State state = state();
    if (escapedBranches_computed == ASTNode$State.NON_CYCLE || escapedBranches_computed == state().cycle()) {
      return escapedBranches_value;
    }
    escapedBranches_value = escapedBranches_compute();
    if (state().inCircle()) {
      escapedBranches_computed = state().cycle();
    
    } else {
      escapedBranches_computed = ASTNode$State.NON_CYCLE;
    
    }
    return escapedBranches_value;
  }
  /** @apilevel internal */
  private Collection<Stmt> escapedBranches_compute() {
      Collection<Stmt> set = new HashSet<Stmt>();
      if (hasNonEmptyFinally()) {
        // Branches from finally.
        getFinally().collectBranches(set);
      }
      if (!hasFinally() || getFinally().canCompleteNormally()) {
        set.addAll(branches());
      }
      return set;
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
        new_assignedAfter_Variable_value = assignedAfter_compute(v);
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
      boolean new_assignedAfter_Variable_value = assignedAfter_compute(v);
      if (new_assignedAfter_Variable_value != ((Boolean)_value.value)) {
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
      // 16.2.15 4th bullet
      if (!hasNonEmptyFinally()) {
        if (!getBlock().assignedAfter(v)) {
          return false;
        }
        for (int i = 0; i < getNumCatchClause(); i++) {
          if (!getCatchClause(i).getBlock().assignedAfter(v)) {
            return false;
          }
        }
        return true;
      } else {
        // 16.2.15 5th bullet
        if (getFinally().assignedAfter(v)) {
          return true;
        }
        if (!getBlock().assignedAfter(v)) {
          return false;
        }
        for (int i = 0; i < getNumCatchClause(); i++) {
          if (!getCatchClause(i).getBlock().assignedAfter(v)) {
            return false;
          }
        }
        return true;
      }
    }
  /** @apilevel internal */
  private void unassignedAfterFinally_Variable_reset() {
    unassignedAfterFinally_Variable_values = null;
  }
  protected java.util.Map unassignedAfterFinally_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1240")
  public boolean unassignedAfterFinally(Variable v) {
    Object _parameters = v;
    if (unassignedAfterFinally_Variable_values == null) unassignedAfterFinally_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (unassignedAfterFinally_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfterFinally_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      unassignedAfterFinally_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfterFinally_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfterFinally_Variable_value = getFinally().unassignedAfter(v);
        if (new_unassignedAfterFinally_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfterFinally_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfterFinally_Variable_values.put(_parameters, new_unassignedAfterFinally_Variable_value);

      state.leaveCircle();
      return new_unassignedAfterFinally_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfterFinally_Variable_value = getFinally().unassignedAfter(v);
      if (new_unassignedAfterFinally_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfterFinally_Variable_value;
      }
      return new_unassignedAfterFinally_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private void assignedAfterFinally_Variable_reset() {
    assignedAfterFinally_Variable_values = null;
  }
  protected java.util.Map assignedAfterFinally_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1245")
  public boolean assignedAfterFinally(Variable v) {
    Object _parameters = v;
    if (assignedAfterFinally_Variable_values == null) assignedAfterFinally_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (assignedAfterFinally_Variable_values.containsKey(_parameters)) {
      Object _cache = assignedAfterFinally_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      assignedAfterFinally_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_assignedAfterFinally_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_assignedAfterFinally_Variable_value = getFinally().assignedAfter(v);
        if (new_assignedAfterFinally_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_assignedAfterFinally_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      assignedAfterFinally_Variable_values.put(_parameters, new_assignedAfterFinally_Variable_value);

      state.leaveCircle();
      return new_assignedAfterFinally_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_assignedAfterFinally_Variable_value = getFinally().assignedAfter(v);
      if (new_assignedAfterFinally_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_assignedAfterFinally_Variable_value;
      }
      return new_assignedAfterFinally_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private void unassignedBefore_Variable_reset() {
    unassignedBefore_Variable_values = null;
  }
  protected java.util.Map unassignedBefore_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1573")
  public boolean unassignedBefore(Variable v) {
    Object _parameters = v;
    if (unassignedBefore_Variable_values == null) unassignedBefore_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (unassignedBefore_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedBefore_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      unassignedBefore_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedBefore_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedBefore_Variable_value = super.unassignedBefore(v);
        if (new_unassignedBefore_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_unassignedBefore_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedBefore_Variable_values.put(_parameters, new_unassignedBefore_Variable_value);

      state.leaveCircle();
      return new_unassignedBefore_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedBefore_Variable_value = super.unassignedBefore(v);
      if (new_unassignedBefore_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_unassignedBefore_Variable_value;
      }
      return new_unassignedBefore_Variable_value;
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
        new_unassignedAfter_Variable_value = unassignedAfter_compute(v);
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
      boolean new_unassignedAfter_Variable_value = unassignedAfter_compute(v);
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
  private boolean unassignedAfter_compute(Variable v) {
      // 16.2.14 4th bullet
      if (!hasNonEmptyFinally()) {
        if (!getBlock().unassignedAfter(v)) {
          return false;
        }
        for (int i = 0; i < getNumCatchClause(); i++) {
          if (!getCatchClause(i).getBlock().unassignedAfter(v)) {
            return false;
          }
        }
        return true;
      } else {
        return getFinally().unassignedAfter(v);
      }
    }
  /** @apilevel internal */
  private void hasNonEmptyFinally_reset() {
    hasNonEmptyFinally_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle hasNonEmptyFinally_computed = null;

  /** @apilevel internal */
  protected boolean hasNonEmptyFinally_value;

  /**
   * @return <code>true</code> if this TyStmt has a non-empty finally block
   * @attribute syn
   * @aspect ExceptionHandling
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:43
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="/home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:43")
  public boolean hasNonEmptyFinally() {
    ASTNode$State state = state();
    if (hasNonEmptyFinally_computed == ASTNode$State.NON_CYCLE || hasNonEmptyFinally_computed == state().cycle()) {
      return hasNonEmptyFinally_value;
    }
    hasNonEmptyFinally_value = hasFinally() && getFinally().getNumStmt() > 0;
    if (state().inCircle()) {
      hasNonEmptyFinally_computed = state().cycle();
    
    } else {
      hasNonEmptyFinally_computed = ASTNode$State.NON_CYCLE;
    
    }
    return hasNonEmptyFinally_value;
  }
  /** @apilevel internal */
  private void catchableException_TypeDecl_reset() {
    catchableException_TypeDecl_computed = new java.util.HashMap(4);
    catchableException_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map catchableException_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map catchableException_TypeDecl_computed;
  /**
   * The block of the try statement can throw an exception of
   * a type assignable to the given type.
   * @attribute syn
   * @aspect ExceptionHandling
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:289
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="/home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:289")
  public boolean catchableException(TypeDecl type) {
    Object _parameters = type;
    if (catchableException_TypeDecl_computed == null) catchableException_TypeDecl_computed = new java.util.HashMap(4);
    if (catchableException_TypeDecl_values == null) catchableException_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (catchableException_TypeDecl_values.containsKey(_parameters) && catchableException_TypeDecl_computed != null
        && catchableException_TypeDecl_computed.containsKey(_parameters)
        && (catchableException_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || catchableException_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) catchableException_TypeDecl_values.get(_parameters);
    }
    boolean catchableException_TypeDecl_value = getBlock().reachedException(type);
    if (state().inCircle()) {
      catchableException_TypeDecl_values.put(_parameters, catchableException_TypeDecl_value);
      catchableException_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      catchableException_TypeDecl_values.put(_parameters, catchableException_TypeDecl_value);
      catchableException_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return catchableException_TypeDecl_value;
  }
  /** @apilevel internal */
  private void getExceptionHandler_reset() {
    getExceptionHandler_computed = false;
    
    getExceptionHandler_value = null;
  }
  /** @apilevel internal */
  protected boolean getExceptionHandler_computed = false;

  /** @apilevel internal */
  protected Block getExceptionHandler_value;

  /** Copy of the finally block for catch-all exception handling. 
   * @attribute syn nta
   * @aspect NTAFinally
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NTAFinally.jrag:59
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="NTAFinally", declaredAt="/home/olivier/projects/extendj/java4/frontend/NTAFinally.jrag:59")
  public Block getExceptionHandler() {
    ASTNode$State state = state();
    if (getExceptionHandler_computed) {
      return (Block) getChild(getExceptionHandlerChildPosition());
    }
    state().enterLazyAttribute();
    getExceptionHandler_value = getExceptionHandler_compute();
    setChild(getExceptionHandler_value, getExceptionHandlerChildPosition());
    getExceptionHandler_computed = true;
    state().leaveLazyAttribute();
    Block node = (Block) this.getChild(getExceptionHandlerChildPosition());
    return node;
  }
  /** @apilevel internal */
  private Block getExceptionHandler_compute() {
      if (hasNonEmptyFinally()) {
        NTAFinallyBlock ntaBlock = new NTAFinallyBlock(this);
        ntaBlock.addStmt((Block) getFinally().treeCopyNoTransform());
        return ntaBlock;
      } else {
        return new NTAFinallyBlock();
      }
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
    canCompleteNormally_value = canCompleteNormally_compute();
    if (state().inCircle()) {
      canCompleteNormally_computed = state().cycle();
    
    } else {
      canCompleteNormally_computed = ASTNode$State.NON_CYCLE;
    
    }
    return canCompleteNormally_value;
  }
  /** @apilevel internal */
  private boolean canCompleteNormally_compute() {
       boolean anyCatchClauseCompleteNormally = false;
       for (int i = 0; i < getNumCatchClause() && !anyCatchClauseCompleteNormally; i++) {
         anyCatchClauseCompleteNormally = getCatchClause(i).getBlock().canCompleteNormally();
       }
       return (getBlock().canCompleteNormally() || anyCatchClauseCompleteNormally)
         && (!hasNonEmptyFinally() || getFinally().canCompleteNormally());
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
        if (getBlock().modifiedInScope(var)) {
          return true;
        }
        for (CatchClause cc : getCatchClauseList()) {
          if (cc.modifiedInScope(var)) {
            return true;
          }
        }
        return hasNonEmptyFinally() && getFinally().modifiedInScope(var);
      }
  }
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:224
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:224")
  public soot.jimple.Stmt break_label() {
    soot.jimple.Stmt break_label_value = label_finally();
    return break_label_value;
  }
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:250
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:250")
  public soot.jimple.Stmt continue_label() {
    soot.jimple.Stmt continue_label_value = label_finally();
    return continue_label_value;
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
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:363
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:363")
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
  private void label_block_end_reset() {
    label_block_end_computed = null;
    label_block_end_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle label_block_end_computed = null;

  /** @apilevel internal */
  protected soot.jimple.Stmt label_block_end_value;

  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:364
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:364")
  public soot.jimple.Stmt label_block_end() {
    ASTNode$State state = state();
    if (label_block_end_computed == ASTNode$State.NON_CYCLE || label_block_end_computed == state().cycle()) {
      return label_block_end_value;
    }
    label_block_end_value = newLabel();
    if (state().inCircle()) {
      label_block_end_computed = state().cycle();
    
    } else {
      label_block_end_computed = ASTNode$State.NON_CYCLE;
    
    }
    return label_block_end_value;
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
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:365
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:365")
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
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:366
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:366")
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
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:368
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:368")
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
  /** @apilevel internal */
  private void label_catch_end_reset() {
    label_catch_end_computed = null;
    label_catch_end_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle label_catch_end_computed = null;

  /** @apilevel internal */
  protected soot.jimple.Stmt label_catch_end_value;

  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:369
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:369")
  public soot.jimple.Stmt label_catch_end() {
    ASTNode$State state = state();
    if (label_catch_end_computed == ASTNode$State.NON_CYCLE || label_catch_end_computed == state().cycle()) {
      return label_catch_end_value;
    }
    label_catch_end_value = newLabel();
    if (state().inCircle()) {
      label_catch_end_computed = state().cycle();
    
    } else {
      label_catch_end_computed = ASTNode$State.NON_CYCLE;
    
    }
    return label_catch_end_value;
  }
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:371
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:371")
  public boolean needsFinallyTrap() {
    boolean needsFinallyTrap_value = enclosedByExceptionHandler() || getNumCatchClause() != 0;
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
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:479
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:479")
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
  /**
   * @attribute inh
   * @aspect ExceptionHandling
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:93
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="/home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:93")
  public boolean handlesException(TypeDecl exceptionType) {
    Object _parameters = exceptionType;
    if (handlesException_TypeDecl_computed == null) handlesException_TypeDecl_computed = new java.util.HashMap(4);
    if (handlesException_TypeDecl_values == null) handlesException_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (handlesException_TypeDecl_values.containsKey(_parameters) && handlesException_TypeDecl_computed != null
        && handlesException_TypeDecl_computed.containsKey(_parameters)
        && (handlesException_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || handlesException_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) handlesException_TypeDecl_values.get(_parameters);
    }
    boolean handlesException_TypeDecl_value = getParent().Define_handlesException(this, null, exceptionType);
    if (state().inCircle()) {
      handlesException_TypeDecl_values.put(_parameters, handlesException_TypeDecl_value);
      handlesException_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      handlesException_TypeDecl_values.put(_parameters, handlesException_TypeDecl_value);
      handlesException_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return handlesException_TypeDecl_value;
  }
  /** @apilevel internal */
  private void handlesException_TypeDecl_reset() {
    handlesException_TypeDecl_computed = new java.util.HashMap(4);
    handlesException_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map handlesException_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map handlesException_TypeDecl_computed;
  /**
   * @attribute inh
   * @aspect UnreachableStatements
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:197
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="UnreachableStatements", declaredAt="/home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:197")
  public TypeDecl typeError() {
    ASTNode$State state = state();
    if (typeError_computed == ASTNode$State.NON_CYCLE || typeError_computed == state().cycle()) {
      return typeError_value;
    }
    typeError_value = getParent().Define_typeError(this, null);
    if (state().inCircle()) {
      typeError_computed = state().cycle();
    
    } else {
      typeError_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeError_value;
  }
  /** @apilevel internal */
  private void typeError_reset() {
    typeError_computed = null;
    typeError_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeError_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeError_value;

  /**
   * @attribute inh
   * @aspect UnreachableStatements
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:198
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="UnreachableStatements", declaredAt="/home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:198")
  public TypeDecl typeRuntimeException() {
    ASTNode$State state = state();
    if (typeRuntimeException_computed == ASTNode$State.NON_CYCLE || typeRuntimeException_computed == state().cycle()) {
      return typeRuntimeException_value;
    }
    typeRuntimeException_value = getParent().Define_typeRuntimeException(this, null);
    if (state().inCircle()) {
      typeRuntimeException_computed = state().cycle();
    
    } else {
      typeRuntimeException_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeRuntimeException_value;
  }
  /** @apilevel internal */
  private void typeRuntimeException_reset() {
    typeRuntimeException_computed = null;
    typeRuntimeException_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeRuntimeException_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeRuntimeException_value;

  /**
   * @attribute inh
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:373
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:373")
  public boolean enclosedByExceptionHandler() {
    boolean enclosedByExceptionHandler_value = getParent().Define_enclosedByExceptionHandler(this, null);
    return enclosedByExceptionHandler_value;
  }
  /**
   * @attribute inh
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:498
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:498")
  public TypeDecl typeThrowable() {
    TypeDecl typeThrowable_value = getParent().Define_typeThrowable(this, null);
    return typeThrowable_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:273
   * @apilevel internal
   */
  public FinallyHost Define_enclosingFinally(ASTNode _callerNode, ASTNode _childNode, Stmt branch) {
    if (_callerNode == getFinallyOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:280
      return enclosingFinally(branch);
    }
    else {
      int childIndex = this.getIndexOfChild(_callerNode);
      return hasNonEmptyFinally() ? this : enclosingFinally(branch);
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
    if (_callerNode == getFinallyOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:854
      return assignedBefore(v);
    }
    else if (_callerNode == getCatchClauseListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:851
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return getBlock().assignedBefore(v);
    }
    else if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:848
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
    if (_callerNode == getFinallyOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1587
      {
          if (!getBlock().unassignedEverywhere(v)) {
            return false;
      	}
          for (int i = 0; i < getNumCatchClause(); i++) {
            if (!getCatchClause(i).getBlock().checkDUeverywhere(v)) {
              return false;
      	  }
      	}
          return true;
        }
    }
    else if (_callerNode == getCatchClauseListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1576
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      {
          if (!getBlock().unassignedAfter(v)) {
            return false;
          }
          if (!getBlock().unassignedEverywhere(v)) {
            return false;
          }
          return true;
        }
    }
    else if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1571
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
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:115
   * @apilevel internal
   */
  public boolean Define_handlesException(ASTNode _callerNode, ASTNode _childNode, TypeDecl exceptionType) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:266
      {
          for (int i = 0; i < getNumCatchClause(); i++) {
            if (getCatchClause(i).handles(exceptionType)) {
              return true;
            }
          }
          if (hasNonEmptyFinally() && !getFinally().canCompleteNormally()) {
            return true;
          }
          return handlesException(exceptionType);
        }
    }
    else if (_callerNode == getCatchClauseListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:259
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      {
          if (hasNonEmptyFinally() && !getFinally().canCompleteNormally()) {
            return true;
          }
          return handlesException(exceptionType);
        }
    }
    else {
      return getParent().Define_handlesException(this, _callerNode, exceptionType);
    }
  }
  protected boolean canDefine_handlesException(ASTNode _callerNode, ASTNode _childNode, TypeDecl exceptionType) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:49
   * @apilevel internal
   */
  public boolean Define_reachable(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getFinallyOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:176
      return reachable();
    }
    else if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:175
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:182
   * @apilevel internal
   */
  public boolean Define_reachableCatchClause(ASTNode _callerNode, ASTNode _childNode, TypeDecl exceptionType) {
    if (_callerNode == getCatchClauseListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:183
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      {
          for (int i = 0; i < childIndex; i++) {
            if (getCatchClause(i).handles(exceptionType)) {
              return false;
            }
          }
          if (catchableException(exceptionType)) {
            return true;
          }
          if (exceptionType.mayCatch(typeError()) || exceptionType.mayCatch(typeRuntimeException())) {
            return true;
          }
          return false;
        }
    }
    else {
      return getParent().Define_reachableCatchClause(this, _callerNode, exceptionType);
    }
  }
  protected boolean canDefine_reachableCatchClause(ASTNode _callerNode, ASTNode _childNode, TypeDecl exceptionType) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:280
   * @apilevel internal
   */
  public boolean Define_reportUnreachable(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getFinallyOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:216
      return reachable();
    }
    else if (_callerNode == getCatchClauseListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:215
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return reachable();
    }
    else if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:214
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
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:217
   * @apilevel internal
   */
  public Collection<TypeDecl> Define_caughtExceptions(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getCatchClauseListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:219
      int index = _callerNode.getIndexOfChild(_childNode);
      {
          Collection<TypeDecl> exceptions = new HashSet<TypeDecl>();
          getBlock().collectExceptions(exceptions);
          Collection<TypeDecl> caught = new LinkedList<TypeDecl>();
          for (TypeDecl exception : exceptions) {
            // This catch clause handles the exception.
            if (!getCatchClause(index).handles(exception)) {
              continue;
            }
            // No previous catch clause handles the exception.
            boolean already = false;
            for (int i = 0; i < index; ++i) {
              if (getCatchClause(i).handles(exception)) {
                already = true;
                break;
              }
            }
            if (!already) {
              caught.add(exception);
            }
          }
          return caught;
        }
    }
    else {
      return getParent().Define_caughtExceptions(this, _callerNode);
    }
  }
  protected boolean canDefine_caughtExceptions(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:373
   * @apilevel internal
   */
  public boolean Define_enclosedByExceptionHandler(ASTNode _callerNode, ASTNode _childNode) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:379
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
    if (_callerNode == getCatchClauseListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:483
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return exceptionRanges();
    }
    else if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:482
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
}
