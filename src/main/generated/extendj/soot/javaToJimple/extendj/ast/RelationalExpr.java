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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:265
 * @production RelationalExpr : {@link Binary};

 */
public abstract class RelationalExpr extends Binary implements Cloneable {
  /**
   * @aspect BooleanExpressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:102
   */
  public soot.Value eval(Body b) { return emitBooleanCondition(b); }
  /**
   * @aspect BooleanExpressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:229
   */
  public void emitEvalBranch(Body b) {
    //b.setLine(this);
    // if(isTrue())
    //   b.add(b.newGotoStmt(true_label(), this));
    // else if(isFalse())
    //   b.add(b.newGotoStmt(false_label(), this));
    // else
    {
      if ( getLeftOperand().type().isNumericType() &&
          getRightOperand().type().isNumericType()) {
        TypeDecl    type  = binaryNumericPromotedType();
        soot.Value  left  =  getLeftOperand().type().emitCastTo(b,  getLeftOperand(), type);
        soot.Value  right = getRightOperand().type().emitCastTo(b, getRightOperand(), type);

        if (type.isDouble() || type.isFloat() || type.isLong()) {
          Local l;
          if (type.isDouble() || type.isFloat()) {
            // FIXME: Are these correct?
            if(this instanceof GEExpr || this instanceof GTExpr)
              l = asLocal(b, b.newCmplExpr(asImmediate(b, left), asImmediate(b, right), this));
            else
              l = asLocal(b, b.newCmpgExpr(asImmediate(b, left), asImmediate(b, right), this));
          } else
            l =   asLocal(b, b.newCmpExpr (asImmediate(b, left), asImmediate(b, right), this));

          b.add(b.newIfStmt(comparisonInv(b, l, BooleanType.emitConstant(false, b, this)), false_label(), this));
          b.add(b.newGotoStmt(true_label(), this));
        }
        else {
          b.add(b.newIfStmt(comparison(b, left, right), true_label(), this));
          b.add(b.newGotoStmt(false_label(), this));
          //b.add(b.newIfStmt(comparisonInv(b, left, right), false_label(), this));
          //b.add(b.newGotoStmt(true_label(), this));
        }
      }
      else {
        soot.Value left   = getLeftOperand().eval(b);
        soot.Value right  = getRightOperand().eval(b);
        b.add(b.newIfStmt(comparison(b, left, right), true_label(), this));
        b.add(b.newGotoStmt(false_label(), this));
        //b.add(b.newIfStmt(comparisonInv(b, left, right), false_label(), this));
        //b.add(b.newGotoStmt(true_label(), this));
      }
    }
  }
  /**
   * @aspect BooleanExpressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:275
   */
  public soot.Value comparison(Body b, soot.Value left, soot.Value right) {
    throw new Error("comparison not supported for " + getClass().getName());
  }
  /**
   * @aspect BooleanExpressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:297
   */
  public soot.Value comparisonInv(Body b, soot.Value left, soot.Value right) {
    throw new Error("comparisonInv not supported for " + getClass().getName());
  }
  /**
   * @declaredat ASTNode:1
   */
  public RelationalExpr() {
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
  }
  /**
   * @declaredat ASTNode:13
   */
  public RelationalExpr(Expr p0, Expr p1) {
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
    type_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:33
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:37
   */
  public RelationalExpr clone() throws CloneNotSupportedException {
    RelationalExpr node = (RelationalExpr) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:48
   */
  @Deprecated
  public abstract RelationalExpr fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:56
   */
  public abstract RelationalExpr treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:64
   */
  public abstract RelationalExpr treeCopy();
  /**
   * Replaces the LeftOperand child.
   * @param node The new node to replace the LeftOperand child.
   * @apilevel high-level
   */
  public void setLeftOperand(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the LeftOperand child.
   * @return The current node used as the LeftOperand child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="LeftOperand")
  public Expr getLeftOperand() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the LeftOperand child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the LeftOperand child.
   * @apilevel low-level
   */
  public Expr getLeftOperandNoTransform() {
    return (Expr) getChildNoTransform(0);
  }
  /**
   * Replaces the RightOperand child.
   * @param node The new node to replace the RightOperand child.
   * @apilevel high-level
   */
  public void setRightOperand(Expr node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the RightOperand child.
   * @return The current node used as the RightOperand child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="RightOperand")
  public Expr getRightOperand() {
    return (Expr) getChild(1);
  }
  /**
   * Retrieves the RightOperand child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the RightOperand child.
   * @apilevel low-level
   */
  public Expr getRightOperandNoTransform() {
    return (Expr) getChildNoTransform(1);
  }
  /** @apilevel internal */
  private void type_reset() {
    type_computed = null;
    type_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle type_computed = null;

  /** @apilevel internal */
  protected TypeDecl type_value;

  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:296
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:296")
  public TypeDecl type() {
    ASTNode$State state = state();
    if (type_computed == ASTNode$State.NON_CYCLE || type_computed == state().cycle()) {
      return type_value;
    }
    type_value = typeBoolean();
    if (state().inCircle()) {
      type_computed = state().cycle();
    
    } else {
      type_computed = ASTNode$State.NON_CYCLE;
    
    }
    return type_value;
  }
  /**
   * @attribute syn
   * @aspect TypeCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:280
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:280")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        if (!getLeftOperand().type().isNumericType()) {
          problems.add(errorf("%s is not numeric", getLeftOperand().type().typeName()));
        }
        if (!getRightOperand().type().isNumericType()) {
          problems.add(errorf("%s is not numeric", getRightOperand().type().typeName()));
        }
        return problems;
      }
  }
  /**
   * @attribute syn
   * @aspect BooleanExpressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:21
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BooleanExpressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:21")
  public boolean definesLabel() {
    boolean definesLabel_value = false;
    return definesLabel_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:48
   * @apilevel internal
   */
  public soot.jimple.Stmt Define_condition_false_label(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightOperandNoTransform() != null && _callerNode == getRightOperand()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:68
      return false_label();
    }
    else if (getLeftOperandNoTransform() != null && _callerNode == getLeftOperand()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:66
      return false_label();
    }
    else {
      return getParent().Define_condition_false_label(this, _callerNode);
    }
  }
  protected boolean canDefine_condition_false_label(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:49
   * @apilevel internal
   */
  public soot.jimple.Stmt Define_condition_true_label(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightOperandNoTransform() != null && _callerNode == getRightOperand()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:69
      return true_label();
    }
    else if (getLeftOperandNoTransform() != null && _callerNode == getLeftOperand()) {
      // @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:67
      return true_label();
    }
    else {
      return getParent().Define_condition_true_label(this, _callerNode);
    }
  }
  protected boolean canDefine_condition_true_label(ASTNode _callerNode, ASTNode _childNode) {
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
    // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:277
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
    for (Problem value : typeProblems()) {
      collection.add(value);
    }
  }
}
