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
 * @declaredat /home/olivier/projects/extendj/java5/grammar/Annotations.ast:13
 * @astdecl ElementArrayValue : ElementValue ::= ElementValue*;
 * @production ElementArrayValue : {@link ElementValue} ::= <span class="component">{@link ElementValue}*</span>;

 */
public class ElementArrayValue extends ElementValue implements Cloneable {
  /**
   * @aspect Java5PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java5/frontend/PrettyPrint.jadd:89
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("{ ");
    out.join(getElementValueList(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.print(", ");
      }
    });
    out.print(" }");
  }
  /**
   * @declaredat ASTNode:1
   */
  public ElementArrayValue() {
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
    setChild(new List(), 0);
  }
  /**
   * @declaredat ASTNode:14
   */
  @ASTNodeAnnotation.Constructor(
    name = {"ElementValue"},
    type = {"List<ElementValue>"},
    kind = {"List"}
  )
  public ElementArrayValue(List<ElementValue> p0) {
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
  public ElementArrayValue clone() throws CloneNotSupportedException {
    ElementArrayValue node = (ElementArrayValue) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:46
   */
  public ElementArrayValue copy() {
    try {
      ElementArrayValue node = (ElementArrayValue) clone();
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
  public ElementArrayValue fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:75
   */
  public ElementArrayValue treeCopyNoTransform() {
    ElementArrayValue tree = (ElementArrayValue) copy();
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
  public ElementArrayValue treeCopy() {
    ElementArrayValue tree = (ElementArrayValue) copy();
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
   * Replaces the ElementValue list.
   * @param list The new list node to be used as the ElementValue list.
   * @apilevel high-level
   */
  public void setElementValueList(List<ElementValue> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the ElementValue list.
   * @return Number of children in the ElementValue list.
   * @apilevel high-level
   */
  public int getNumElementValue() {
    return getElementValueList().getNumChild();
  }
  /**
   * Retrieves the number of children in the ElementValue list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the ElementValue list.
   * @apilevel low-level
   */
  public int getNumElementValueNoTransform() {
    return getElementValueListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the ElementValue list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the ElementValue list.
   * @apilevel high-level
   */
  public ElementValue getElementValue(int i) {
    return (ElementValue) getElementValueList().getChild(i);
  }
  /**
   * Check whether the ElementValue list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasElementValue() {
    return getElementValueList().getNumChild() != 0;
  }
  /**
   * Append an element to the ElementValue list.
   * @param node The element to append to the ElementValue list.
   * @apilevel high-level
   */
  public void addElementValue(ElementValue node) {
    List<ElementValue> list = (parent == null) ? getElementValueListNoTransform() : getElementValueList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addElementValueNoTransform(ElementValue node) {
    List<ElementValue> list = getElementValueListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the ElementValue list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setElementValue(ElementValue node, int i) {
    List<ElementValue> list = getElementValueList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the ElementValue list.
   * @return The node representing the ElementValue list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="ElementValue")
  public List<ElementValue> getElementValueList() {
    List<ElementValue> list = (List<ElementValue>) getChild(0);
    return list;
  }
  /**
   * Retrieves the ElementValue list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the ElementValue list.
   * @apilevel low-level
   */
  public List<ElementValue> getElementValueListNoTransform() {
    return (List<ElementValue>) getChildNoTransform(0);
  }
  /**
   * @return the element at index {@code i} in the ElementValue list without
   * triggering rewrites.
   */
  public ElementValue getElementValueNoTransform(int i) {
    return (ElementValue) getElementValueListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the ElementValue list.
   * @return The node representing the ElementValue list.
   * @apilevel high-level
   */
  public List<ElementValue> getElementValues() {
    return getElementValueList();
  }
  /**
   * Retrieves the ElementValue list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the ElementValue list.
   * @apilevel low-level
   */
  public List<ElementValue> getElementValuesNoTransform() {
    return getElementValueListNoTransform();
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:111
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:111")
  public boolean validTarget(Annotation a) {
    {
        for (int i = 0;  i < getNumElementValue(); i++) {
          if (getElementValue(i).validTarget(a)) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:285
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:285")
  public ElementValue definesElementTypeValue(String name) {
    {
        for (int i = 0; i < getNumElementValue(); i++) {
          if (getElementValue(i).definesElementTypeValue(name) != null) {
            return getElementValue(i).definesElementTypeValue(name);
          }
        }
        return null;
      }
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:448
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:448")
  public boolean hasValue(String annot) {
    {
        for (int i = 0;  i < getNumElementValue(); i++) {
          if (getElementValue(i).hasValue(annot)) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:703
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:703")
  public boolean commensurateWithArrayDecl(ArrayDecl type) {
    {
        for (int i = 0; i < getNumElementValue(); i++) {
          if (!type.componentType().commensurateWith(getElementValue(i))) {
            return false;
          }
        }
        return true;
      }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:278
   * @apilevel internal
   */
  public ElementValue Define_lookupElementTypeValue(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (_callerNode == getElementValueListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:280
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return definesElementTypeValue(name);
    }
    else {
      return getParent().Define_lookupElementTypeValue(this, _callerNode, name);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:278
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupElementTypeValue
   */
  protected boolean canDefine_lookupElementTypeValue(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:723
   * @apilevel internal
   */
  public TypeDecl Define_declType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return declType().elementType();
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
    return super.rewriteTo();
  }
  /** @apilevel internal */
  public boolean canRewrite() {
    return false;
  }
}
