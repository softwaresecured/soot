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
 * @declaredat /home/olivier/projects/extendj/java5/grammar/Generics.ast:83
 * @astdecl FieldDeclSubstituted : FieldDecl ::= <Original:FieldDecl>;
 * @production FieldDeclSubstituted : {@link FieldDecl} ::= <span class="component">&lt;Original:FieldDecl&gt;</span>;

 */
public class FieldDeclSubstituted extends FieldDecl implements Cloneable {
  /**
   * @declaredat ASTNode:1
   */
  public FieldDeclSubstituted() {
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
    setChild(new List(), 2);
  }
  /**
   * @declaredat ASTNode:14
   */
  @ASTNodeAnnotation.Constructor(
    name = {"Modifiers", "TypeAccess", "Declarator", "Original"},
    type = {"Modifiers", "Access", "List<FieldDeclarator>", "FieldDecl"},
    kind = {"Child", "Child", "List", "Token"}
  )
  public FieldDeclSubstituted(Modifiers p0, Access p1, List<FieldDeclarator> p2, FieldDecl p3) {
    setChild(p0, 0);
    setChild(p1, 1);
    setChild(p2, 2);
    setOriginal(p3);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:26
   */
  protected int numChildren() {
    return 3;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:32
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:36
   */
  public void flushAttrCache() {
    super.flushAttrCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:40
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:44
   */
  public FieldDeclSubstituted clone() throws CloneNotSupportedException {
    FieldDeclSubstituted node = (FieldDeclSubstituted) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:49
   */
  public FieldDeclSubstituted copy() {
    try {
      FieldDeclSubstituted node = (FieldDeclSubstituted) clone();
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
   * @declaredat ASTNode:68
   */
  @Deprecated
  public FieldDeclSubstituted fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:78
   */
  public FieldDeclSubstituted treeCopyNoTransform() {
    FieldDeclSubstituted tree = (FieldDeclSubstituted) copy();
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
   * @declaredat ASTNode:98
   */
  public FieldDeclSubstituted treeCopy() {
    FieldDeclSubstituted tree = (FieldDeclSubstituted) copy();
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
   * @declaredat ASTNode:112
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenFieldDecl_Original == ((FieldDeclSubstituted) node).tokenFieldDecl_Original);    
  }
  /**
   * Replaces the Modifiers child.
   * @param node The new node to replace the Modifiers child.
   * @apilevel high-level
   */
  public void setModifiers(Modifiers node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Modifiers child.
   * @return The current node used as the Modifiers child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Modifiers")
  public Modifiers getModifiers() {
    return (Modifiers) getChild(0);
  }
  /**
   * Retrieves the Modifiers child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Modifiers child.
   * @apilevel low-level
   */
  public Modifiers getModifiersNoTransform() {
    return (Modifiers) getChildNoTransform(0);
  }
  /**
   * Replaces the TypeAccess child.
   * @param node The new node to replace the TypeAccess child.
   * @apilevel high-level
   */
  public void setTypeAccess(Access node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the TypeAccess child.
   * @return The current node used as the TypeAccess child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="TypeAccess")
  public Access getTypeAccess() {
    return (Access) getChild(1);
  }
  /**
   * Retrieves the TypeAccess child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the TypeAccess child.
   * @apilevel low-level
   */
  public Access getTypeAccessNoTransform() {
    return (Access) getChildNoTransform(1);
  }
  /**
   * Replaces the Declarator list.
   * @param list The new list node to be used as the Declarator list.
   * @apilevel high-level
   */
  public void setDeclaratorList(List<FieldDeclarator> list) {
    setChild(list, 2);
  }
  /**
   * Retrieves the number of children in the Declarator list.
   * @return Number of children in the Declarator list.
   * @apilevel high-level
   */
  public int getNumDeclarator() {
    return getDeclaratorList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Declarator list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Declarator list.
   * @apilevel low-level
   */
  public int getNumDeclaratorNoTransform() {
    return getDeclaratorListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Declarator list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Declarator list.
   * @apilevel high-level
   */
  public FieldDeclarator getDeclarator(int i) {
    return (FieldDeclarator) getDeclaratorList().getChild(i);
  }
  /**
   * Check whether the Declarator list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasDeclarator() {
    return getDeclaratorList().getNumChild() != 0;
  }
  /**
   * Append an element to the Declarator list.
   * @param node The element to append to the Declarator list.
   * @apilevel high-level
   */
  public void addDeclarator(FieldDeclarator node) {
    List<FieldDeclarator> list = (parent == null) ? getDeclaratorListNoTransform() : getDeclaratorList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addDeclaratorNoTransform(FieldDeclarator node) {
    List<FieldDeclarator> list = getDeclaratorListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Declarator list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setDeclarator(FieldDeclarator node, int i) {
    List<FieldDeclarator> list = getDeclaratorList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Declarator list.
   * @return The node representing the Declarator list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Declarator")
  public List<FieldDeclarator> getDeclaratorList() {
    List<FieldDeclarator> list = (List<FieldDeclarator>) getChild(2);
    return list;
  }
  /**
   * Retrieves the Declarator list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Declarator list.
   * @apilevel low-level
   */
  public List<FieldDeclarator> getDeclaratorListNoTransform() {
    return (List<FieldDeclarator>) getChildNoTransform(2);
  }
  /**
   * @return the element at index {@code i} in the Declarator list without
   * triggering rewrites.
   */
  public FieldDeclarator getDeclaratorNoTransform(int i) {
    return (FieldDeclarator) getDeclaratorListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the Declarator list.
   * @return The node representing the Declarator list.
   * @apilevel high-level
   */
  public List<FieldDeclarator> getDeclarators() {
    return getDeclaratorList();
  }
  /**
   * Retrieves the Declarator list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Declarator list.
   * @apilevel low-level
   */
  public List<FieldDeclarator> getDeclaratorsNoTransform() {
    return getDeclaratorListNoTransform();
  }
  /**
   * Replaces the lexeme Original.
   * @param value The new value for the lexeme Original.
   * @apilevel high-level
   */
  public void setOriginal(FieldDecl value) {
    tokenFieldDecl_Original = value;
  }
  /** @apilevel internal 
   */
  protected FieldDecl tokenFieldDecl_Original;
  /**
   * Retrieves the value for the lexeme Original.
   * @return The value for the lexeme Original.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Token(name="Original")
  public FieldDecl getOriginal() {
    return tokenFieldDecl_Original;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1650
   * @apilevel internal
   */
  public FieldDeclarator Define_erasedField(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getDeclaratorListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1654
      int index = _callerNode.getIndexOfChild(_childNode);
      return getOriginal().getDeclarator(index);
    }
    else {
      return super.Define_erasedField(_callerNode, _childNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1650
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute erasedField
   */
  protected boolean canDefine_erasedField(ASTNode _callerNode, ASTNode _childNode) {
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
