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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:307
 * @production ContinueStmt : {@link Stmt} ::= <span class="component">&lt;Label:String&gt;</span> <span class="component">[Finally:{@link Block}]</span>;

 */
public class ContinueStmt extends Stmt implements Cloneable {
  /**
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:100
   */
  public void collectBranches(Collection<Stmt> c) {
    c.add(this);
  }
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:306
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("continue");
    if (hasLabel()) {
      out.print(" ");
      out.print(getLabel());
    }
    out.print(";");
  }
  /**
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:259
   */
  public void jimplify2(Body b) {
    ArrayList<soot.jimple.Stmt> list = exceptionRanges();

    if (!inSynchronizedBlock())
      endExceptionRange(b, list, newLabel());

    for (Iterator<FinallyHost> iter = finallyIterator(); iter.hasNext(); )
        iter.next().emitFinallyCode(b);

    if (inSynchronizedBlock())
      endExceptionRange(b, list, newLabel());

    //b.setLine(this);
    b.add(b.newGotoStmt(targetStmt().continue_label(), this));
    beginExceptionRange(b, list);
  }
  /**
   * @declaredat ASTNode:1
   */
  public ContinueStmt() {
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
    setChild(new Opt(), 0);
  }
  /**
   * @declaredat ASTNode:14
   */
  public ContinueStmt(String p0) {
    setLabel(p0);
  }
  /**
   * @declaredat ASTNode:17
   */
  public ContinueStmt(beaver.Symbol p0) {
    setLabel(p0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:21
   */
  protected int numChildren() {
    return 0;
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
    targetStmt_reset();
    assignedAfter_Variable_reset();
    unassignedAfterReachedFinallyBlocks_Variable_reset();
    assignedAfterReachedFinallyBlocks_Variable_reset();
    unassignedAfter_Variable_reset();
    getFinallyOpt_reset();
    canCompleteNormally_reset();
    inSynchronizedBlock_reset();
    lookupLabel_String_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:44
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:48
   */
  public ContinueStmt clone() throws CloneNotSupportedException {
    ContinueStmt node = (ContinueStmt) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:53
   */
  public ContinueStmt copy() {
    try {
      ContinueStmt node = (ContinueStmt) clone();
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
   * @declaredat ASTNode:72
   */
  @Deprecated
  public ContinueStmt fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:82
   */
  public ContinueStmt treeCopyNoTransform() {
    ContinueStmt tree = (ContinueStmt) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 0:
          tree.children[i] = new Opt();
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
   * @declaredat ASTNode:107
   */
  public ContinueStmt treeCopy() {
    ContinueStmt tree = (ContinueStmt) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 0:
          tree.children[i] = new Opt();
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
   * @declaredat ASTNode:126
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_Label == ((ContinueStmt) node).tokenString_Label);    
  }
  /**
   * Replaces the lexeme Label.
   * @param value The new value for the lexeme Label.
   * @apilevel high-level
   */
  public void setLabel(String value) {
    tokenString_Label = value;
  }
  /** @apilevel internal 
   */
  protected String tokenString_Label;
  /**
   */
  public int Labelstart;
  /**
   */
  public int Labelend;
  /**
   * JastAdd-internal setter for lexeme Label using the Beaver parser.
   * @param symbol Symbol containing the new value for the lexeme Label
   * @apilevel internal
   */
  public void setLabel(beaver.Symbol symbol) {
    if (symbol.value != null && !(symbol.value instanceof String))
    throw new UnsupportedOperationException("setLabel is only valid for String lexemes");
    tokenString_Label = (String)symbol.value;
    Labelstart = symbol.getStart();
    Labelend = symbol.getEnd();
  }
  /**
   * Retrieves the value for the lexeme Label.
   * @return The value for the lexeme Label.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Token(name="Label")
  public String getLabel() {
    return tokenString_Label != null ? tokenString_Label : "";
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
   * Retrieves the optional node for child Finally. This is the <code>Opt</code> node containing the child Finally, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Finally.
   * @apilevel low-level
   */
  public Opt<Block> getFinallyOptNoTransform() {
    return (Opt<Block>) getChildNoTransform(0);
  }
  /**
   * Retrieves the child position of the optional child Finally.
   * @return The the child position of the optional child Finally.
   * @apilevel low-level
   */
  protected int getFinallyOptChildPosition() {
    return 0;
  }
  /**
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:120
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BranchTarget", declaredAt="/home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:120")
  public boolean hasLabel() {
    boolean hasLabel_value = !getLabel().equals("");
    return hasLabel_value;
  }
  /**
   * @return <code>true</code> if this statement can branch to
   * the target statement.
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:182
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BranchTarget", declaredAt="/home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:182")
  public boolean canBranchTo(BranchTargetStmt target) {
    boolean canBranchTo_BranchTargetStmt_value = !hasLabel();
    return canBranchTo_BranchTargetStmt_value;
  }
  /**
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:184
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BranchTarget", declaredAt="/home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:184")
  public boolean canBranchTo(LabeledStmt target) {
    boolean canBranchTo_LabeledStmt_value = hasLabel() && target.getLabel().equals(getLabel());
    return canBranchTo_LabeledStmt_value;
  }
  /**
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:186
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BranchTarget", declaredAt="/home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:186")
  public boolean canBranchTo(SwitchStmt target) {
    boolean canBranchTo_SwitchStmt_value = false;
    return canBranchTo_SwitchStmt_value;
  }
  /** @apilevel internal */
  private void targetStmt_reset() {
    targetStmt_computed = null;
    targetStmt_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle targetStmt_computed = null;

  /** @apilevel internal */
  protected Stmt targetStmt_value;

  /**
   * Find the target statement for break and continue. This can be a try
   * statement with a finally block that can not complete normally.
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:46
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BranchTarget", declaredAt="/home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:46")
  public Stmt targetStmt() {
    ASTNode$State state = state();
    if (targetStmt_computed == ASTNode$State.NON_CYCLE || targetStmt_computed == state().cycle()) {
      return targetStmt_value;
    }
    targetStmt_value = branchTarget(this);
    if (state().inCircle()) {
      targetStmt_computed = state().cycle();
    
    } else {
      targetStmt_computed = ASTNode$State.NON_CYCLE;
    
    }
    return targetStmt_value;
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
        new_assignedAfter_Variable_value = true;
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
      boolean new_assignedAfter_Variable_value = true;
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
  private void unassignedAfterReachedFinallyBlocks_Variable_reset() {
    unassignedAfterReachedFinallyBlocks_Variable_values = null;
  }
  protected java.util.Map unassignedAfterReachedFinallyBlocks_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1265")
  public boolean unassignedAfterReachedFinallyBlocks(Variable v) {
    Object _parameters = v;
    if (unassignedAfterReachedFinallyBlocks_Variable_values == null) unassignedAfterReachedFinallyBlocks_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (unassignedAfterReachedFinallyBlocks_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfterReachedFinallyBlocks_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      unassignedAfterReachedFinallyBlocks_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfterReachedFinallyBlocks_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfterReachedFinallyBlocks_Variable_value = unassignedAfterReachedFinallyBlocks_compute(v);
        if (new_unassignedAfterReachedFinallyBlocks_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfterReachedFinallyBlocks_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfterReachedFinallyBlocks_Variable_values.put(_parameters, new_unassignedAfterReachedFinallyBlocks_Variable_value);

      state.leaveCircle();
      return new_unassignedAfterReachedFinallyBlocks_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfterReachedFinallyBlocks_Variable_value = unassignedAfterReachedFinallyBlocks_compute(v);
      if (new_unassignedAfterReachedFinallyBlocks_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfterReachedFinallyBlocks_Variable_value;
      }
      return new_unassignedAfterReachedFinallyBlocks_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private boolean unassignedAfterReachedFinallyBlocks_compute(Variable v) {
      Iterator<FinallyHost> iter = finallyIterator();
      if (!unassignedBefore(v) && !iter.hasNext()) {
        return false;
      }
      while (iter.hasNext()) {
        FinallyHost f = iter.next();
        if (!f.unassignedAfterFinally(v)) {
          return false;
        }
      }
      return true;
    }
  /** @apilevel internal */
  private void assignedAfterReachedFinallyBlocks_Variable_reset() {
    assignedAfterReachedFinallyBlocks_Variable_values = null;
  }
  protected java.util.Map assignedAfterReachedFinallyBlocks_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1310")
  public boolean assignedAfterReachedFinallyBlocks(Variable v) {
    Object _parameters = v;
    if (assignedAfterReachedFinallyBlocks_Variable_values == null) assignedAfterReachedFinallyBlocks_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (assignedAfterReachedFinallyBlocks_Variable_values.containsKey(_parameters)) {
      Object _cache = assignedAfterReachedFinallyBlocks_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      assignedAfterReachedFinallyBlocks_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_assignedAfterReachedFinallyBlocks_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_assignedAfterReachedFinallyBlocks_Variable_value = assignedAfterReachedFinallyBlocks_compute(v);
        if (new_assignedAfterReachedFinallyBlocks_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_assignedAfterReachedFinallyBlocks_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      assignedAfterReachedFinallyBlocks_Variable_values.put(_parameters, new_assignedAfterReachedFinallyBlocks_Variable_value);

      state.leaveCircle();
      return new_assignedAfterReachedFinallyBlocks_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_assignedAfterReachedFinallyBlocks_Variable_value = assignedAfterReachedFinallyBlocks_compute(v);
      if (new_assignedAfterReachedFinallyBlocks_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_assignedAfterReachedFinallyBlocks_Variable_value;
      }
      return new_assignedAfterReachedFinallyBlocks_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private boolean assignedAfterReachedFinallyBlocks_compute(Variable v) {
      if (assignedBefore(v)) {
        return true;
      }
      Iterator<FinallyHost> iter = finallyIterator();
      if (!iter.hasNext()) {
        return false;
      }
      while (iter.hasNext()) {
        FinallyHost f = iter.next();
        if (!f.assignedAfterFinally(v)) {
          return false;
        }
      }
      return true;
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
        new_unassignedAfter_Variable_value = true;
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
      boolean new_unassignedAfter_Variable_value = true;
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
  private void getFinallyOpt_reset() {
    getFinallyOpt_computed = false;
    
    getFinallyOpt_value = null;
  }
  /** @apilevel internal */
  protected boolean getFinallyOpt_computed = false;

  /** @apilevel internal */
  protected Opt<Block> getFinallyOpt_value;

  /**
   * @attribute syn nta
   * @aspect NTAFinally
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NTAFinally.jrag:50
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="NTAFinally", declaredAt="/home/olivier/projects/extendj/java4/frontend/NTAFinally.jrag:50")
  public Opt<Block> getFinallyOpt() {
    ASTNode$State state = state();
    if (getFinallyOpt_computed) {
      return (Opt<Block>) getChild(getFinallyOptChildPosition());
    }
    state().enterLazyAttribute();
    getFinallyOpt_value = getFinallyOpt_compute();
    setChild(getFinallyOpt_value, getFinallyOptChildPosition());
    getFinallyOpt_computed = true;
    state().leaveLazyAttribute();
    Opt<Block> node = (Opt<Block>) this.getChild(getFinallyOptChildPosition());
    return node;
  }
  /** @apilevel internal */
  private Opt<Block> getFinallyOpt_compute() {
      return branchFinallyOpt();
    }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:552
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:552")
  public Collection<Problem> nameProblems() {
    {
        if (!insideLoop()) {
          return Collections.singletonList(error("continue outside loop"));
        } else if (hasLabel()) {
          LabeledStmt label = lookupLabel(getLabel());
          if (label == null) {
            return Collections.singletonList(
                error("labeled continue must have visible matching label"));
          } else if (!label.getStmt().continueLabel()) {
            return Collections.singletonList(errorf("%s is not a loop label", getLabel()));
          }
        }
        return Collections.emptyList();
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
    canCompleteNormally_value = false;
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
    boolean modifiedInScope_Variable_value = false;
    return modifiedInScope_Variable_value;
  }
  /** @apilevel internal */
  private void inSynchronizedBlock_reset() {
    inSynchronizedBlock_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle inSynchronizedBlock_computed = null;

  /** @apilevel internal */
  protected boolean inSynchronizedBlock_value;

  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:284
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:284")
  public boolean inSynchronizedBlock() {
    ASTNode$State state = state();
    if (inSynchronizedBlock_computed == ASTNode$State.NON_CYCLE || inSynchronizedBlock_computed == state().cycle()) {
      return inSynchronizedBlock_value;
    }
    inSynchronizedBlock_value = inSynchronizedBlock_compute();
    if (state().inCircle()) {
      inSynchronizedBlock_computed = state().cycle();
    
    } else {
      inSynchronizedBlock_computed = ASTNode$State.NON_CYCLE;
    
    }
    return inSynchronizedBlock_value;
  }
  /** @apilevel internal */
  private boolean inSynchronizedBlock_compute() {
      Iterator<FinallyHost> i = finallyIterator();
      return i.hasNext() && i.next() instanceof SynchronizedStmt;
    }
  /** Lookup visible label. 
   * @attribute inh
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:255
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="BranchTarget", declaredAt="/home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:255")
  public LabeledStmt lookupLabel(String name) {
    Object _parameters = name;
    if (lookupLabel_String_computed == null) lookupLabel_String_computed = new java.util.HashMap(4);
    if (lookupLabel_String_values == null) lookupLabel_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (lookupLabel_String_values.containsKey(_parameters) && lookupLabel_String_computed != null
        && lookupLabel_String_computed.containsKey(_parameters)
        && (lookupLabel_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || lookupLabel_String_computed.get(_parameters) == state().cycle())) {
      return (LabeledStmt) lookupLabel_String_values.get(_parameters);
    }
    LabeledStmt lookupLabel_String_value = getParent().Define_lookupLabel(this, null, name);
    if (state().inCircle()) {
      lookupLabel_String_values.put(_parameters, lookupLabel_String_value);
      lookupLabel_String_computed.put(_parameters, state().cycle());
    
    } else {
      lookupLabel_String_values.put(_parameters, lookupLabel_String_value);
      lookupLabel_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return lookupLabel_String_value;
  }
  /** @apilevel internal */
  private void lookupLabel_String_reset() {
    lookupLabel_String_computed = new java.util.HashMap(4);
    lookupLabel_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map lookupLabel_String_values;
  /** @apilevel internal */
  protected java.util.Map lookupLabel_String_computed;
  /**
   * @attribute inh
   * @aspect NameCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:523
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:523")
  public boolean insideLoop() {
    boolean insideLoop_value = getParent().Define_insideLoop(this, null);
    return insideLoop_value;
  }
  /**
   * @attribute inh
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:476
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:476")
  public ArrayList exceptionRanges() {
    ArrayList exceptionRanges_value = getParent().Define_exceptionRanges(this, null);
    return exceptionRanges_value;
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
    // @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:550
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    super.collect_contributors_CompilationUnit_problems(_root, _map);
  }
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    for (Problem value : nameProblems()) {
      collection.add(value);
    }
  }
}
