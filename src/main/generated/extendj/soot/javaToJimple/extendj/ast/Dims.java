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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:224
 * @astdecl Dims : ASTNode ::= [Expr];
 * @production Dims : {@link ASTNode} ::= <span class="component">[{@link Expr}]</span>;

 */
public class Dims extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:325
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("[");
    out.print(getExprOpt());
    out.print("]");
  }
  /**
   * @declaredat ASTNode:1
   */
  public Dims() {
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
  @ASTNodeAnnotation.Constructor(
    name = {"Expr"},
    type = {"Opt<Expr>"},
    kind = {"Opt"}
  )
  public Dims(Opt<Expr> p0) {
    setChild(p0, 0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:23
   */
  protected int numChildren() {
    return 1;
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
  }
  /** @apilevel internal 
   * @declaredat ASTNode:37
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:41
   */
  public Dims clone() throws CloneNotSupportedException {
    Dims node = (Dims) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:46
   */
  public Dims copy() {
    try {
      Dims node = (Dims) clone();
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
   * @declaredat ASTNode:65
   */
  @Deprecated
  public Dims fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:75
   */
  public Dims treeCopyNoTransform() {
    Dims tree = (Dims) copy();
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
   * @declaredat ASTNode:95
   */
  public Dims treeCopy() {
    Dims tree = (Dims) copy();
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
   * @declaredat ASTNode:109
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the optional node for the Expr child. This is the <code>Opt</code>
   * node containing the child Expr, not the actual child!
   * @param opt The new node to be used as the optional node for the Expr child.
   * @apilevel low-level
   */
  public void setExprOpt(Opt<Expr> opt) {
    setChild(opt, 0);
  }
  /**
   * Replaces the (optional) Expr child.
   * @param node The new node to be used as the Expr child.
   * @apilevel high-level
   */
  public void setExpr(Expr node) {
    getExprOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional Expr child exists.
   * @return {@code true} if the optional Expr child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasExpr() {
    return getExprOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) Expr child.
   * @return The Expr child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Expr getExpr() {
    return (Expr) getExprOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the Expr child. This is the <code>Opt</code> node containing the child Expr, not the actual child!
   * @return The optional node for child the Expr child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="Expr")
  public Opt<Expr> getExprOpt() {
    return (Opt<Expr>) getChild(0);
  }
  /**
   * Retrieves the optional node for child Expr. This is the <code>Opt</code> node containing the child Expr, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Expr.
   * @apilevel low-level
   */
  public Opt<Expr> getExprOptNoTransform() {
    return (Opt<Expr>) getChildNoTransform(0);
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:418
   * @apilevel internal
   */
  public boolean Define_assignmentContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getExprOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:569
      return false;
    }
    else {
      return getParent().Define_assignmentContext(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:418
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute assignmentContext
   */
  protected boolean canDefine_assignmentContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:419
   * @apilevel internal
   */
  public boolean Define_invocationContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getExprOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:570
      return false;
    }
    else {
      return getParent().Define_invocationContext(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:419
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute invocationContext
   */
  protected boolean canDefine_invocationContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:420
   * @apilevel internal
   */
  public boolean Define_castContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getExprOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:571
      return false;
    }
    else {
      return getParent().Define_castContext(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:420
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute castContext
   */
  protected boolean canDefine_castContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:421
   * @apilevel internal
   */
  public boolean Define_stringContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getExprOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:572
      return false;
    }
    else {
      return getParent().Define_stringContext(this, _callerNode);
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
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:422
   * @apilevel internal
   */
  public boolean Define_numericContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getExprOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:573
      return false;
    }
    else {
      return getParent().Define_numericContext(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:422
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute numericContext
   */
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
}
