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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:248
 * @astdecl AddExpr : AdditiveExpr;
 * @production AddExpr : {@link AdditiveExpr};

 */
public class AddExpr extends AdditiveExpr implements Cloneable {
  /**
   * @declaredat ASTNode:1
   */
  public AddExpr() {
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
  @ASTNodeAnnotation.Constructor(
    name = {"LeftOperand", "RightOperand"},
    type = {"Expr", "Expr"},
    kind = {"Child", "Child"}
  )
  public AddExpr(Expr p0, Expr p1) {
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
    type_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:38
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:42
   */
  public AddExpr clone() throws CloneNotSupportedException {
    AddExpr node = (AddExpr) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:47
   */
  public AddExpr copy() {
    try {
      AddExpr node = (AddExpr) clone();
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
   * @declaredat ASTNode:66
   */
  @Deprecated
  public AddExpr fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:76
   */
  public AddExpr treeCopyNoTransform() {
    AddExpr tree = (AddExpr) copy();
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
   * @declaredat ASTNode:96
   */
  public AddExpr treeCopy() {
    AddExpr tree = (AddExpr) copy();
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
   * @declaredat ASTNode:110
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
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
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:32
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:32")
  public Constant constant() {
    Constant constant_value = type().add(getLeftOperand().constant(), getRightOperand().constant());
    return constant_value;
  }
  /** The operator string used for pretty printing this expression. 
   * @attribute syn
   * @aspect PrettyPrintUtil
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:353
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PrettyPrintUtil", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:353")
  public String printOp() {
    String printOp_value = "+";
    return printOp_value;
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
    type_value = type_compute();
    if (state().inCircle()) {
      type_computed = state().cycle();
    
    } else {
      type_computed = ASTState.NON_CYCLE;
    
    }
    return type_value;
  }
  /** @apilevel internal */
  private TypeDecl type_compute() {
      TypeDecl left = getLeftOperand().type();
      TypeDecl right = getRightOperand().type();
      if (!left.isString() && !right.isString()) {
        return super.type();
      } else {
        if (left.isVoid() || right.isVoid()) {
          return unknownType();
        }
        // pick the string type
        return left.isString() ? left : right;
      }
    }
  /**
   * @attribute syn
   * @aspect TypeCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:223
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:223")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        TypeDecl left = getLeftOperand().type();
        TypeDecl right = getRightOperand().type();
        if (!left.isString() && !right.isString()) {
          return super.typeProblems();
        } else if (left.isVoid()) {
          problems.add(error("The type void of the left hand side is not numeric"));
        } else if (right.isVoid()) {
          problems.add(error("The type void of the right hand side is not numeric"));
        }
        return problems;
      }
  }
  /**
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:128
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="InnerClasses", declaredAt="/home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:128")
  public boolean isStringAdd() {
    boolean isStringAdd_value = type().isString() && !isConstant();
    return isStringAdd_value;
  }
  /**
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:131
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="InnerClasses", declaredAt="/home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:131")
  public boolean firstStringAddPart() {
    boolean firstStringAddPart_value = type().isString() && !getLeftOperand().isStringAdd();
    return firstStringAddPart_value;
  }
  /**
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:132
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="InnerClasses", declaredAt="/home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:132")
  public boolean lastStringAddPart() {
    boolean lastStringAddPart_value = !getParent().isStringAdd();
    return lastStringAddPart_value;
  }
  /**
   * @attribute syn
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:734
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Expressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:734")
  public Value emitOperation(Body b, Value l, Value r) {
    Value emitOperation_Body_Value_Value_value = b.newAddExpr  (l, r, this);
    return emitOperation_Body_Value_Value_value;
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
        if (type().isString() && isConstant())
          return soot.jimple.StringConstant.v(constant().stringValue());
    
        if (isStringAdd()) {
          Local v;
          if (firstStringAddPart()) {
            // new StringBuffer
            v = b.newTemp(b.newNewExpr(
              lookupType("java.lang", "StringBuffer").sootRef(), this));
            //b.setLine(this);
            b.add(b.newInvokeStmt(
              b.newSpecialInvokeExpr(v,
              Scene.v().getMethod("<java.lang.StringBuffer: void <init>()>").makeRef(),
              Collections.emptyList(),
              this
            ), this));
            //b.setLine(this);
            b.add(b.newInvokeStmt(
              b.newVirtualInvokeExpr(v,
                stringBufferAppendMethodForType(getLeftOperand().type()).sootRef(),
                Collections.singletonList(getLeftOperand().eval(b)),
                this
              ), this));
          }
          else
            v = b.asLocal(getLeftOperand().eval(b));
    
          // append
          //b.setLine(this);
          b.add(b.newInvokeStmt(
            b.newVirtualInvokeExpr(v,
              stringBufferAppendMethodForType(getRightOperand().type()).sootRef(),
              Collections.singletonList(getRightOperand().eval(b)),
              this
            ), this));
    
          if (lastStringAddPart())
            return b.newTemp(b.newVirtualInvokeExpr(v,
              Scene.v().getMethod("<java.lang.StringBuffer: java.lang.String toString()>").makeRef(),
              Collections.emptyList(),
              this
            ));
    
          return v;
        }
    
        return super.eval(b);
      }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:69
   * @apilevel internal
   */
  public TypeDecl Define_assignConvertedType(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightOperandNoTransform() != null && _callerNode == getRightOperand()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:92
      return getLeftOperand().type().isString() ? typeObject() : typeNull();
    }
    else if (getLeftOperandNoTransform() != null && _callerNode == getLeftOperand()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:88
      return getRightOperand().type().isString() ? typeObject() : typeNull();
    }
    else {
      return getParent().Define_assignConvertedType(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:69
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute assignConvertedType
   */
  protected boolean canDefine_assignConvertedType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:31
   * @apilevel internal
   */
  public TypeDecl Define_targetType(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightOperandNoTransform() != null && _callerNode == getRightOperand()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:71
      {
          if (getRightOperand().stringContext()) {
            return getLeftOperand().type();
          } else if (!getLeftOperand().isPolyExpression() && !getRightOperand().isPolyExpression()) {
            return type();
          } else {
            return targetType();
          }
        }
    }
    else if (getLeftOperandNoTransform() != null && _callerNode == getLeftOperand()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:62
      {
          if (getLeftOperand().stringContext()) {
            return getRightOperand().type();
          } else if (!getLeftOperand().isPolyExpression() && !getRightOperand().isPolyExpression()) {
            return type();
          } else {
            return targetType();
          }
        }
    }
    else {
      return super.Define_targetType(_callerNode, _childNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:31
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute targetType
   */
  protected boolean canDefine_targetType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:421
   * @apilevel internal
   */
  public boolean Define_stringContext(ASTNode _callerNode, ASTNode _childNode) {
    if (getRightOperandNoTransform() != null && _callerNode == getRightOperand()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:456
      {
          if (!getRightOperand().isPolyExpression() && !getLeftOperand().isPolyExpression()) {
            if (getLeftOperand().type().isString() && !getRightOperand().type().isString()) {
              return true;
            }
          }
          return false;
        }
    }
    else if (getLeftOperandNoTransform() != null && _callerNode == getLeftOperand()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:448
      {
          if (!getRightOperand().isPolyExpression() && !getLeftOperand().isPolyExpression()) {
            if (getRightOperand().type().isString() && !getLeftOperand().type().isString()) {
              return true;
            }
          }
          return false;
        }
    }
    else {
      return super.Define_stringContext(_callerNode, _childNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:421
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute stringContext
   */
  protected boolean canDefine_stringContext(ASTNode _callerNode, ASTNode _childNode) {
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
