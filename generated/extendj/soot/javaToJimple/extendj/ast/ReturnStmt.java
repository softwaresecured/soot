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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:308
 * @production ReturnStmt : {@link Stmt} ::= <span class="component">[Result:{@link Expr}]</span> <span class="component">[Finally:{@link Block}]</span>;

 */
public class ReturnStmt extends Stmt implements Cloneable {
  /**
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:108
   */
  public void collectBranches(Collection<Stmt> c) {
    c.add(this);
  }
  /**
   * @aspect NodeConstructors
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NodeConstructors.jrag:76
   */
  public ReturnStmt(Expr expr) {
    this(new Opt(expr));
  }
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:542
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("return");
    if (hasResult()) {
      out.print(" ");
      out.print(getResult());
    }
    out.print(";");
  }
  /**
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:300
   */
  public void jimplify2(Body b) {
    Local result = null;
    if (hasResult()) {
      TypeDecl type = returnType();
      if (type.isVoid()) throw new Error("Can not return a value from a void body");

      result = asLocal(b,
        getResult().type().emitCastTo(b,
          getResult().eval(b),
          type,
          getResult()
        ),
        type.getSootType()
      );
    }

    ArrayList<soot.jimple.Stmt> list = exceptionRanges();
    if(!inSynchronizedBlock())
      endExceptionRange(b, list, newLabel());

    for(Iterator<FinallyHost> iter = finallyIterator(); iter.hasNext(); )
      iter.next().emitFinallyCode(b);

    //b.setLine(this);
    if(inSynchronizedBlock())
      endExceptionRange(b, list, newLabel());

    soot.jimple.Stmt retStmt = hasResult()  ? b.newReturnStmt(result, this)
                                            : b.newReturnVoidStmt(this);
    b.add(retStmt);

    beginExceptionRange(b, list);
  }
  /**
   * @declaredat ASTNode:1
   */
  public ReturnStmt() {
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
    setChild(new Opt(), 0);
    setChild(new Opt(), 1);
  }
  /**
   * @declaredat ASTNode:15
   */
  public ReturnStmt(Opt<Expr> p0) {
    setChild(p0, 0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:19
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:25
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:29
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    assignedAfter_Variable_reset();
    unassignedAfterReachedFinallyBlocks_Variable_reset();
    assignedAfterReachedFinallyBlocks_Variable_reset();
    unassignedAfter_Variable_reset();
    getFinallyOpt_reset();
    canCompleteNormally_reset();
    inSynchronizedBlock_reset();
    enclosingLambda_reset();
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
  public ReturnStmt clone() throws CloneNotSupportedException {
    ReturnStmt node = (ReturnStmt) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:50
   */
  public ReturnStmt copy() {
    try {
      ReturnStmt node = (ReturnStmt) clone();
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
  public ReturnStmt fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:79
   */
  public ReturnStmt treeCopyNoTransform() {
    ReturnStmt tree = (ReturnStmt) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 1:
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
   * @declaredat ASTNode:104
   */
  public ReturnStmt treeCopy() {
    ReturnStmt tree = (ReturnStmt) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 1:
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
   * @declaredat ASTNode:123
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the optional node for the Result child. This is the <code>Opt</code>
   * node containing the child Result, not the actual child!
   * @param opt The new node to be used as the optional node for the Result child.
   * @apilevel low-level
   */
  public void setResultOpt(Opt<Expr> opt) {
    setChild(opt, 0);
  }
  /**
   * Replaces the (optional) Result child.
   * @param node The new node to be used as the Result child.
   * @apilevel high-level
   */
  public void setResult(Expr node) {
    getResultOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional Result child exists.
   * @return {@code true} if the optional Result child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasResult() {
    return getResultOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) Result child.
   * @return The Result child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Expr getResult() {
    return (Expr) getResultOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the Result child. This is the <code>Opt</code> node containing the child Result, not the actual child!
   * @return The optional node for child the Result child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="Result")
  public Opt<Expr> getResultOpt() {
    return (Opt<Expr>) getChild(0);
  }
  /**
   * Retrieves the optional node for child Result. This is the <code>Opt</code> node containing the child Result, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Result.
   * @apilevel low-level
   */
  public Opt<Expr> getResultOptNoTransform() {
    return (Opt<Expr>) getChildNoTransform(0);
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
    return (Opt<Block>) getChildNoTransform(1);
  }
  /**
   * Retrieves the child position of the optional child Finally.
   * @return The the child position of the optional child Finally.
   * @apilevel low-level
   */
  protected int getFinallyOptChildPosition() {
    return 1;
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
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1279")
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
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1327")
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
      if (hasResult() ? getResult().assignedAfter(v) : assignedBefore(v)) {
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NTAFinally.jrag:54
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="NTAFinally", declaredAt="/home/olivier/projects/extendj/java4/frontend/NTAFinally.jrag:54")
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
   * @aspect TypeCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:541
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:541")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        if (enclosingLambda() == null || enclosingLambda().hostType() != hostType()) {
          if (hasResult() && !returnType().isVoid()) {
            if (!getResult().type().assignConversionTo(returnType(), getResult())) {
              problems.add(errorf("return value must be an instance of %s which %s is not",
                  returnType().typeName(), getResult().type().typeName()));
            }
          }
          // 8.4.5 8.8.5
          if (returnType().isVoid() && hasResult()) {
            problems.add(error("return statement may not have an expression in void methods"));
          }
          // 8.4.5
          if (!returnType().isVoid() && !hasResult()) {
            problems.add(error("return statement must have an expression in non void methods"));
          }
          if (enclosingBodyDecl() instanceof InstanceInitializer
              || enclosingBodyDecl() instanceof StaticInitializer) {
            problems.add(error("Initializers may not return"));
          }
        } else {
          if (hasResult() && !returnType().isVoid() && !(getResult() instanceof LambdaExpr)) {
            if (!getResult().type().assignConversionTo(returnType(), getResult())) {
              problems.add(errorf("return value must be an instance of %s which %s is not",
                  returnType().typeName(), getResult().type().typeName()));
            }
          }
        }
        return problems;
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
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:276
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:276")
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
  /**
   * @attribute inh
   * @aspect TypeCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:534
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:534")
  public TypeDecl returnType() {
    TypeDecl returnType_value = getParent().Define_returnType(this, null);
    return returnType_value;
  }
  /**
   * @attribute inh
   * @aspect EnclosingLambda
   * @declaredat /home/olivier/projects/extendj/java8/frontend/EnclosingLambda.jrag:33
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="EnclosingLambda", declaredAt="/home/olivier/projects/extendj/java8/frontend/EnclosingLambda.jrag:33")
  public LambdaExpr enclosingLambda() {
    ASTNode$State state = state();
    if (enclosingLambda_computed == ASTNode$State.NON_CYCLE || enclosingLambda_computed == state().cycle()) {
      return enclosingLambda_value;
    }
    enclosingLambda_value = getParent().Define_enclosingLambda(this, null);
    if (state().inCircle()) {
      enclosingLambda_computed = state().cycle();
    
    } else {
      enclosingLambda_computed = ASTNode$State.NON_CYCLE;
    
    }
    return enclosingLambda_value;
  }
  /** @apilevel internal */
  private void enclosingLambda_reset() {
    enclosingLambda_computed = null;
    enclosingLambda_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle enclosingLambda_computed = null;

  /** @apilevel internal */
  protected LambdaExpr enclosingLambda_value;

  /**
   * @attribute inh
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:475
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:475")
  public ArrayList exceptionRanges() {
    ArrayList exceptionRanges_value = getParent().Define_exceptionRanges(this, null);
    return exceptionRanges_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:256
   * @apilevel internal
   */
  public boolean Define_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (_callerNode == getResultOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:837
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
    if (_callerNode == getResultOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1560
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
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:69
   * @apilevel internal
   */
  public TypeDecl Define_assignConvertedType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getResultOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:73
      return returnType();
    }
    else {
      return getParent().Define_assignConvertedType(this, _callerNode);
    }
  }
  protected boolean canDefine_assignConvertedType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:31
   * @apilevel internal
   */
  public TypeDecl Define_targetType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getResultOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:41
      return returnType();
    }
    else {
      return getParent().Define_targetType(this, _callerNode);
    }
  }
  protected boolean canDefine_targetType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:234
   * @apilevel internal
   */
  public boolean Define_assignmentContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getResultOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:367
      return true;
    }
    else {
      return getParent().Define_assignmentContext(this, _callerNode);
    }
  }
  protected boolean canDefine_assignmentContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:235
   * @apilevel internal
   */
  public boolean Define_invocationContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getResultOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:368
      return false;
    }
    else {
      return getParent().Define_invocationContext(this, _callerNode);
    }
  }
  protected boolean canDefine_invocationContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:236
   * @apilevel internal
   */
  public boolean Define_castContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getResultOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:369
      return false;
    }
    else {
      return getParent().Define_castContext(this, _callerNode);
    }
  }
  protected boolean canDefine_castContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:237
   * @apilevel internal
   */
  public boolean Define_stringContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getResultOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:370
      return false;
    }
    else {
      return getParent().Define_stringContext(this, _callerNode);
    }
  }
  protected boolean canDefine_stringContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:238
   * @apilevel internal
   */
  public boolean Define_numericContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getResultOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:371
      return false;
    }
    else {
      return getParent().Define_numericContext(this, _callerNode);
    }
  }
  protected boolean canDefine_numericContext(ASTNode _callerNode, ASTNode _childNode) {
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
    // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:539
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
  protected void collect_contributors_BlockLambdaBody_lambdaReturns(Program _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat /home/olivier/projects/extendj/java8/frontend/LambdaBody.jrag:52
    if ((enclosingLambda() != null) && (enclosingLambda().hostType() == hostType())) {
      {
        BlockLambdaBody target = (BlockLambdaBody) ((BlockLambdaBody) enclosingLambda().getLambdaBody());
        java.util.Set<ASTNode> contributors = _map.get(target);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) target, contributors);
        }
        contributors.add(this);
      }
    }
    super.collect_contributors_BlockLambdaBody_lambdaReturns(_root, _map);
  }
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    for (Problem value : typeProblems()) {
      collection.add(value);
    }
  }
  protected void contributeTo_BlockLambdaBody_lambdaReturns(ArrayList<ReturnStmt> collection) {
    super.contributeTo_BlockLambdaBody_lambdaReturns(collection);
    if ((enclosingLambda() != null) && (enclosingLambda().hostType() == hostType())) {
      collection.add(this);
    }
  }
}
