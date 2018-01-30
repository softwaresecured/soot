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
 * @declaredat /home/olivier/projects/extendj/java5/grammar/Annotations.ast:8
 * @astdecl ElementValuePair : ASTNode ::= <Name:String> ElementValue;
 * @production ElementValuePair : {@link ASTNode} ::= <span class="component">&lt;Name:String&gt;</span> <span class="component">{@link ElementValue}</span>;

 */
public class ElementValuePair extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @aspect Java5PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java5/frontend/PrettyPrint.jadd:102
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getName());
    out.print(" = ");
    out.print(getElementValue());
  }
  /**
   * @declaredat ASTNode:1
   */
  public ElementValuePair() {
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
  }
  /**
   * @declaredat ASTNode:13
   */
  @ASTNodeAnnotation.Constructor(
    name = {"Name", "ElementValue"},
    type = {"String", "ElementValue"},
    kind = {"Token", "Child"}
  )
  public ElementValuePair(String p0, ElementValue p1) {
    setName(p0);
    setChild(p1, 0);
  }
  /**
   * @declaredat ASTNode:22
   */
  public ElementValuePair(beaver.Symbol p0, ElementValue p1) {
    setName(p0);
    setChild(p1, 0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:27
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:33
   */
  public boolean mayHaveRewrite() {
    return true;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:37
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    rewrittenNode_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:42
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:46
   */
  public ElementValuePair clone() throws CloneNotSupportedException {
    ElementValuePair node = (ElementValuePair) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:51
   */
  public ElementValuePair copy() {
    try {
      ElementValuePair node = (ElementValuePair) clone();
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
   * @declaredat ASTNode:70
   */
  @Deprecated
  public ElementValuePair fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:80
   */
  public ElementValuePair treeCopyNoTransform() {
    ElementValuePair tree = (ElementValuePair) copy();
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
   * @declaredat ASTNode:100
   */
  public ElementValuePair treeCopy() {
    ElementValuePair tree = (ElementValuePair) copy();
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
   * @declaredat ASTNode:114
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_Name == ((ElementValuePair) node).tokenString_Name);    
  }
  /**
   * Replaces the lexeme Name.
   * @param value The new value for the lexeme Name.
   * @apilevel high-level
   */
  public void setName(String value) {
    tokenString_Name = value;
  }
  /** @apilevel internal 
   */
  protected String tokenString_Name;
  /**
   */
  public int Namestart;
  /**
   */
  public int Nameend;
  /**
   * JastAdd-internal setter for lexeme Name using the Beaver parser.
   * @param symbol Symbol containing the new value for the lexeme Name
   * @apilevel internal
   */
  public void setName(beaver.Symbol symbol) {
    if (symbol.value != null && !(symbol.value instanceof String))
    throw new UnsupportedOperationException("setName is only valid for String lexemes");
    tokenString_Name = (String)symbol.value;
    Namestart = symbol.getStart();
    Nameend = symbol.getEnd();
  }
  /**
   * Retrieves the value for the lexeme Name.
   * @return The value for the lexeme Name.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Token(name="Name")
  public String getName() {
    return tokenString_Name != null ? tokenString_Name : "";
  }
  /**
   * Replaces the ElementValue child.
   * @param node The new node to replace the ElementValue child.
   * @apilevel high-level
   */
  public void setElementValue(ElementValue node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the ElementValue child.
   * @return The current node used as the ElementValue child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="ElementValue")
  public ElementValue getElementValue() {
    return (ElementValue) getChild(0);
  }
  /**
   * Retrieves the ElementValue child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the ElementValue child.
   * @apilevel low-level
   */
  public ElementValue getElementValueNoTransform() {
    return (ElementValue) getChildNoTransform(0);
  }
  /**
   * The return type of this method defines the element type of the element-value
   * pair. An ElementValueArrayInitializer is similar to a normal array initializer
   * (\ufffd10.6), except that annotations are permitted in place of expressions.
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:634
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:634")
  public TypeDecl type() {
    {
        SimpleSet<MethodDecl> set = enclosingAnnotationDecl()
            .localMethodsSignature(getName() + "()");
        if (set.isSingleton()) {
          return set.singletonValue().type();
        } else {
          return unknownType();
        }
      }
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:644
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:644")
  public TypeDecl unknownType() {
    TypeDecl unknownType_value = getParent().Define_unknownType(this, null);
    return unknownType_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:646
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:646")
  public TypeDecl enclosingAnnotationDecl() {
    TypeDecl enclosingAnnotationDecl_value = getParent().Define_enclosingAnnotationDecl(this, null);
    return enclosingAnnotationDecl_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:723
   * @apilevel internal
   */
  public TypeDecl Define_declType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return type();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:723
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute declType
   */
  protected boolean canDefine_declType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /** @apilevel internal */
  public ASTNode rewriteTo() {
    // Declared at /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:751
    if (type().isArrayDecl() && getElementValue() instanceof ElementConstantValue) {
      return rewriteRule0();
    }
    return super.rewriteTo();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:751
   * @apilevel internal
   */
  private ElementValuePair rewriteRule0() {
{
      return new ElementValuePair(getName(),
          new ElementArrayValue(new List().add(getElementValue())));
    }  }
  /** @apilevel internal */
  public boolean canRewrite() {
    // Declared at /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:751
    if (type().isArrayDecl() && getElementValue() instanceof ElementConstantValue) {
      return true;
    }
    return false;
  }
  /** @apilevel internal */
  protected void collect_contributors_CompilationUnit_problems(CompilationUnit _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:716
    if (!type().commensurateWith(getElementValue())) {
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
    if (!type().commensurateWith(getElementValue())) {
      collection.add(errorf("can not construct annotation with %s = %s; %s is not commensurate with %s",
                getName(), getElementValue().prettyPrint(),
                type().typeName(), getElementValue().type().typeName()));
    }
  }
  /** @apilevel internal */
  private void rewrittenNode_reset() {
    rewrittenNode_computed = false;
    rewrittenNode_initialized = false;
    rewrittenNode_value = null;
    rewrittenNode_cycle = null;
  }
/** @apilevel internal */
protected ASTState.Cycle rewrittenNode_cycle = null;
  /** @apilevel internal */
  protected boolean rewrittenNode_computed = false;

  /** @apilevel internal */
  protected ASTNode rewrittenNode_value;
  /** @apilevel internal */
  protected boolean rewrittenNode_initialized = false;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="", declaredAt=":0")
  public ASTNode rewrittenNode() {
    if (rewrittenNode_computed) {
      return rewrittenNode_value;
    }
    ASTState state = state();
    if (!rewrittenNode_initialized) {
      rewrittenNode_initialized = true;
      rewrittenNode_value = this;
      if (rewrittenNode_value != null) {
        rewrittenNode_value.setParent(getParent());
      }
    }
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      do {
        rewrittenNode_cycle = state.nextCycle();
        ASTNode new_rewrittenNode_value = rewrittenNode_value.rewriteTo();
        if (new_rewrittenNode_value != rewrittenNode_value || new_rewrittenNode_value.canRewrite()) {
          state.setChangeInCycle();
        }
        rewrittenNode_value = new_rewrittenNode_value;
        if (rewrittenNode_value != null) {
          rewrittenNode_value.setParent(getParent());
        }
      } while (state.testAndClearChangeInCycle());
      rewrittenNode_computed = true;

      state.leaveCircle();
    } else if (rewrittenNode_cycle != state.cycle()) {
      rewrittenNode_cycle = state.cycle();
      ASTNode new_rewrittenNode_value = rewrittenNode_value.rewriteTo();
      if (new_rewrittenNode_value != rewrittenNode_value || new_rewrittenNode_value.canRewrite()) {
        state.setChangeInCycle();
      }
      rewrittenNode_value = new_rewrittenNode_value;
      if (rewrittenNode_value != null) {
        rewrittenNode_value.setParent(getParent());
      }
    } else {
    }
    return rewrittenNode_value;
  }
}
