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
 * A statement that can be reached by {@code break} or {@code continue}.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:289
 * @production BranchTargetStmt : {@link Stmt};

 */
public abstract class BranchTargetStmt extends Stmt implements Cloneable {
  /**
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:112
   */
  public void collectBranches(Collection<Stmt> c) {
    c.addAll(escapedBranches());
  }
  /**
   * @declaredat ASTNode:1
   */
  public BranchTargetStmt() {
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
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:13
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:19
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:23
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    targetBranches_reset();
    escapedBranches_reset();
    branches_reset();
    targetContinues_reset();
    targetBreaks_reset();
    reachableBreak_reset();
    reachableContinue_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:34
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:38
   */
  public BranchTargetStmt clone() throws CloneNotSupportedException {
    BranchTargetStmt node = (BranchTargetStmt) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:49
   */
  @Deprecated
  public abstract BranchTargetStmt fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:57
   */
  public abstract BranchTargetStmt treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:65
   */
  public abstract BranchTargetStmt treeCopy();
  /** @apilevel internal */
  private void targetBranches_reset() {
    targetBranches_computed = null;
    targetBranches_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle targetBranches_computed = null;

  /** @apilevel internal */
  protected Collection<Stmt> targetBranches_value;

  /**
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:88
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BranchTarget", declaredAt="/home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:88")
  public Collection<Stmt> targetBranches() {
    ASTNode$State state = state();
    if (targetBranches_computed == ASTNode$State.NON_CYCLE || targetBranches_computed == state().cycle()) {
      return targetBranches_value;
    }
    targetBranches_value = targetBranches_compute();
    if (state().inCircle()) {
      targetBranches_computed = state().cycle();
    
    } else {
      targetBranches_computed = ASTNode$State.NON_CYCLE;
    
    }
    return targetBranches_value;
  }
  /** @apilevel internal */
  private Collection<Stmt> targetBranches_compute() {
      Collection<Stmt> set = new HashSet<Stmt>();
      for (Stmt branch : branches()) {
        if (potentialTargetOf(branch)) {
          set.add(branch);
        }
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

  /**
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:90
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BranchTarget", declaredAt="/home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:90")
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
      for (Stmt branch : branches()) {
        if (!potentialTargetOf(branch)) {
          set.add(branch);
        } else if (branch instanceof ReturnStmt) {
          set.add(branch);
        }
      }
      return set;
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

  /**
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:92
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BranchTarget", declaredAt="/home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:92")
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
      super.collectBranches(set);
      return set;
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
    boolean potentialTargetOf_Stmt_value = false;
    return potentialTargetOf_Stmt_value;
  }
  /** @apilevel internal */
  private void targetContinues_reset() {
    targetContinues_computed = null;
    targetContinues_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle targetContinues_computed = null;

  /** @apilevel internal */
  protected Collection<ContinueStmt> targetContinues_value;

  /**
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:84
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BranchTarget", declaredAt="/home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:84")
  public Collection<ContinueStmt> targetContinues() {
    ASTNode$State state = state();
    if (targetContinues_computed == ASTNode$State.NON_CYCLE || targetContinues_computed == state().cycle()) {
      return targetContinues_value;
    }
    targetContinues_value = targetContinues_compute();
    if (state().inCircle()) {
      targetContinues_computed = state().cycle();
    
    } else {
      targetContinues_computed = ASTNode$State.NON_CYCLE;
    
    }
    return targetContinues_value;
  }
  /** @apilevel internal */
  private Collection<ContinueStmt> targetContinues_compute() {
      Collection<ContinueStmt> set = new HashSet<ContinueStmt>();
      for (Stmt branch : targetBranches()) {
        if (branch instanceof ContinueStmt) {
          set.add((ContinueStmt) branch);
        }
      }
      if (getParent() instanceof LabeledStmt) {
        for (Stmt branch : ((LabeledStmt) getParent()).targetBranches()) {
          if (branch instanceof ContinueStmt) {
            set.add((ContinueStmt) branch);
          }
        }
      }
      return set;
    }
  /** @apilevel internal */
  private void targetBreaks_reset() {
    targetBreaks_computed = null;
    targetBreaks_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle targetBreaks_computed = null;

  /** @apilevel internal */
  protected Collection<BreakStmt> targetBreaks_value;

  /**
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:86
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BranchTarget", declaredAt="/home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:86")
  public Collection<BreakStmt> targetBreaks() {
    ASTNode$State state = state();
    if (targetBreaks_computed == ASTNode$State.NON_CYCLE || targetBreaks_computed == state().cycle()) {
      return targetBreaks_value;
    }
    targetBreaks_value = targetBreaks_compute();
    if (state().inCircle()) {
      targetBreaks_computed = state().cycle();
    
    } else {
      targetBreaks_computed = ASTNode$State.NON_CYCLE;
    
    }
    return targetBreaks_value;
  }
  /** @apilevel internal */
  private Collection<BreakStmt> targetBreaks_compute() {
      Collection<BreakStmt> set = new HashSet<BreakStmt>();
      for (Stmt branch : targetBranches()) {
        if (branch instanceof BreakStmt) {
          set.add((BreakStmt) branch);
        }
      }
      return set;
    }
  /** @apilevel internal */
  private void reachableBreak_reset() {
    reachableBreak_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle reachableBreak_computed = null;

  /** @apilevel internal */
  protected boolean reachableBreak_value;

  /**
   * @attribute syn
   * @aspect UnreachableStatements
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:85
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="UnreachableStatements", declaredAt="/home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:85")
  public boolean reachableBreak() {
    ASTNode$State state = state();
    if (reachableBreak_computed == ASTNode$State.NON_CYCLE || reachableBreak_computed == state().cycle()) {
      return reachableBreak_value;
    }
    reachableBreak_value = reachableBreak_compute();
    if (state().inCircle()) {
      reachableBreak_computed = state().cycle();
    
    } else {
      reachableBreak_computed = ASTNode$State.NON_CYCLE;
    
    }
    return reachableBreak_value;
  }
  /** @apilevel internal */
  private boolean reachableBreak_compute() {
      for (BreakStmt stmt : targetBreaks()) {
        if (stmt.reachable()) {
          return true;
        }
      }
      return false;
    }
  /** @apilevel internal */
  private void reachableContinue_reset() {
    reachableContinue_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle reachableContinue_computed = null;

  /** @apilevel internal */
  protected boolean reachableContinue_value;

  /**
   * @attribute syn
   * @aspect UnreachableStatements
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:140
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="UnreachableStatements", declaredAt="/home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:140")
  public boolean reachableContinue() {
    ASTNode$State state = state();
    if (reachableContinue_computed == ASTNode$State.NON_CYCLE || reachableContinue_computed == state().cycle()) {
      return reachableContinue_value;
    }
    reachableContinue_value = reachableContinue_compute();
    if (state().inCircle()) {
      reachableContinue_computed = state().cycle();
    
    } else {
      reachableContinue_computed = ASTNode$State.NON_CYCLE;
    
    }
    return reachableContinue_value;
  }
  /** @apilevel internal */
  private boolean reachableContinue_compute() {
      for (Stmt stmt : targetContinues()) {
        if (stmt.reachable()) {
          return true;
        }
      }
      return false;
    }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:273
   * @apilevel internal
   */
  public FinallyHost Define_enclosingFinally(ASTNode _callerNode, ASTNode _childNode, Stmt branch) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return potentialTargetOf(branch) ? null : enclosingFinally(branch);
  }
  protected boolean canDefine_enclosingFinally(ASTNode _callerNode, ASTNode _childNode, Stmt branch) {
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
