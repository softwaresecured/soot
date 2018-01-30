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
 * @declaredat /home/olivier/projects/extendj/java5/grammar/Annotations.ast:16
 * @astdecl AnnotatedCompilationUnit : CompilationUnit ::= Modifiers;
 * @production AnnotatedCompilationUnit : {@link CompilationUnit} ::= <span class="component">{@link Modifiers}</span>;

 */
public class AnnotatedCompilationUnit extends CompilationUnit implements Cloneable {
  /**
   * @aspect AnnotationPrettyPrinting
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:787
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getModifiers());
    super.prettyPrint(out);
  }
  /**
   * @declaredat ASTNode:1
   */
  public AnnotatedCompilationUnit() {
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
    setChild(new List(), 0);
    setChild(new List(), 1);
  }
  /**
   * @declaredat ASTNode:15
   */
  @ASTNodeAnnotation.Constructor(
    name = {"PackageDecl", "ImportDecl", "TypeDecl", "Modifiers"},
    type = {"String", "List<ImportDecl>", "List<TypeDecl>", "Modifiers"},
    kind = {"Token", "List", "List", "Child"}
  )
  public AnnotatedCompilationUnit(String p0, List<ImportDecl> p1, List<TypeDecl> p2, Modifiers p3) {
    setPackageDecl(p0);
    setChild(p1, 0);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @declaredat ASTNode:26
   */
  public AnnotatedCompilationUnit(beaver.Symbol p0, List<ImportDecl> p1, List<TypeDecl> p2, Modifiers p3) {
    setPackageDecl(p0);
    setChild(p1, 0);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:33
   */
  protected int numChildren() {
    return 3;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:39
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:43
   */
  public void flushAttrCache() {
    super.flushAttrCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:47
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:51
   */
  public AnnotatedCompilationUnit clone() throws CloneNotSupportedException {
    AnnotatedCompilationUnit node = (AnnotatedCompilationUnit) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:56
   */
  public AnnotatedCompilationUnit copy() {
    try {
      AnnotatedCompilationUnit node = (AnnotatedCompilationUnit) clone();
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
   * @declaredat ASTNode:75
   */
  @Deprecated
  public AnnotatedCompilationUnit fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:85
   */
  public AnnotatedCompilationUnit treeCopyNoTransform() {
    AnnotatedCompilationUnit tree = (AnnotatedCompilationUnit) copy();
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
   * @declaredat ASTNode:105
   */
  public AnnotatedCompilationUnit treeCopy() {
    AnnotatedCompilationUnit tree = (AnnotatedCompilationUnit) copy();
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
   * @declaredat ASTNode:119
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_PackageDecl == ((AnnotatedCompilationUnit) node).tokenString_PackageDecl);    
  }
  /**
   * Replaces the lexeme PackageDecl.
   * @param value The new value for the lexeme PackageDecl.
   * @apilevel high-level
   */
  public void setPackageDecl(String value) {
    tokenString_PackageDecl = value;
  }
  /**
   * JastAdd-internal setter for lexeme PackageDecl using the Beaver parser.
   * @param symbol Symbol containing the new value for the lexeme PackageDecl
   * @apilevel internal
   */
  public void setPackageDecl(beaver.Symbol symbol) {
    if (symbol.value != null && !(symbol.value instanceof String))
    throw new UnsupportedOperationException("setPackageDecl is only valid for String lexemes");
    tokenString_PackageDecl = (String)symbol.value;
    PackageDeclstart = symbol.getStart();
    PackageDeclend = symbol.getEnd();
  }
  /**
   * Retrieves the value for the lexeme PackageDecl.
   * @return The value for the lexeme PackageDecl.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Token(name="PackageDecl")
  public String getPackageDecl() {
    return tokenString_PackageDecl != null ? tokenString_PackageDecl : "";
  }
  /**
   * Replaces the ImportDecl list.
   * @param list The new list node to be used as the ImportDecl list.
   * @apilevel high-level
   */
  public void setImportDeclList(List<ImportDecl> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the ImportDecl list.
   * @return Number of children in the ImportDecl list.
   * @apilevel high-level
   */
  public int getNumImportDecl() {
    return getImportDeclList().getNumChild();
  }
  /**
   * Retrieves the number of children in the ImportDecl list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the ImportDecl list.
   * @apilevel low-level
   */
  public int getNumImportDeclNoTransform() {
    return getImportDeclListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the ImportDecl list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the ImportDecl list.
   * @apilevel high-level
   */
  public ImportDecl getImportDecl(int i) {
    return (ImportDecl) getImportDeclList().getChild(i);
  }
  /**
   * Check whether the ImportDecl list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasImportDecl() {
    return getImportDeclList().getNumChild() != 0;
  }
  /**
   * Append an element to the ImportDecl list.
   * @param node The element to append to the ImportDecl list.
   * @apilevel high-level
   */
  public void addImportDecl(ImportDecl node) {
    List<ImportDecl> list = (parent == null) ? getImportDeclListNoTransform() : getImportDeclList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addImportDeclNoTransform(ImportDecl node) {
    List<ImportDecl> list = getImportDeclListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the ImportDecl list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setImportDecl(ImportDecl node, int i) {
    List<ImportDecl> list = getImportDeclList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the ImportDecl list.
   * @return The node representing the ImportDecl list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="ImportDecl")
  public List<ImportDecl> getImportDeclList() {
    List<ImportDecl> list = (List<ImportDecl>) getChild(0);
    return list;
  }
  /**
   * Retrieves the ImportDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the ImportDecl list.
   * @apilevel low-level
   */
  public List<ImportDecl> getImportDeclListNoTransform() {
    return (List<ImportDecl>) getChildNoTransform(0);
  }
  /**
   * @return the element at index {@code i} in the ImportDecl list without
   * triggering rewrites.
   */
  public ImportDecl getImportDeclNoTransform(int i) {
    return (ImportDecl) getImportDeclListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the ImportDecl list.
   * @return The node representing the ImportDecl list.
   * @apilevel high-level
   */
  public List<ImportDecl> getImportDecls() {
    return getImportDeclList();
  }
  /**
   * Retrieves the ImportDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the ImportDecl list.
   * @apilevel low-level
   */
  public List<ImportDecl> getImportDeclsNoTransform() {
    return getImportDeclListNoTransform();
  }
  /**
   * Replaces the TypeDecl list.
   * @param list The new list node to be used as the TypeDecl list.
   * @apilevel high-level
   */
  public void setTypeDeclList(List<TypeDecl> list) {
    setChild(list, 1);
  }
  /**
   * Retrieves the number of children in the TypeDecl list.
   * @return Number of children in the TypeDecl list.
   * @apilevel high-level
   */
  public int getNumTypeDecl() {
    return getTypeDeclList().getNumChild();
  }
  /**
   * Retrieves the number of children in the TypeDecl list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the TypeDecl list.
   * @apilevel low-level
   */
  public int getNumTypeDeclNoTransform() {
    return getTypeDeclListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the TypeDecl list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the TypeDecl list.
   * @apilevel high-level
   */
  public TypeDecl getTypeDecl(int i) {
    return (TypeDecl) getTypeDeclList().getChild(i);
  }
  /**
   * Check whether the TypeDecl list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasTypeDecl() {
    return getTypeDeclList().getNumChild() != 0;
  }
  /**
   * Append an element to the TypeDecl list.
   * @param node The element to append to the TypeDecl list.
   * @apilevel high-level
   */
  public void addTypeDecl(TypeDecl node) {
    List<TypeDecl> list = (parent == null) ? getTypeDeclListNoTransform() : getTypeDeclList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addTypeDeclNoTransform(TypeDecl node) {
    List<TypeDecl> list = getTypeDeclListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the TypeDecl list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setTypeDecl(TypeDecl node, int i) {
    List<TypeDecl> list = getTypeDeclList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the TypeDecl list.
   * @return The node representing the TypeDecl list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="TypeDecl")
  public List<TypeDecl> getTypeDeclList() {
    List<TypeDecl> list = (List<TypeDecl>) getChild(1);
    return list;
  }
  /**
   * Retrieves the TypeDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeDecl list.
   * @apilevel low-level
   */
  public List<TypeDecl> getTypeDeclListNoTransform() {
    return (List<TypeDecl>) getChildNoTransform(1);
  }
  /**
   * @return the element at index {@code i} in the TypeDecl list without
   * triggering rewrites.
   */
  public TypeDecl getTypeDeclNoTransform(int i) {
    return (TypeDecl) getTypeDeclListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the TypeDecl list.
   * @return The node representing the TypeDecl list.
   * @apilevel high-level
   */
  public List<TypeDecl> getTypeDecls() {
    return getTypeDeclList();
  }
  /**
   * Retrieves the TypeDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeDecl list.
   * @apilevel low-level
   */
  public List<TypeDecl> getTypeDeclsNoTransform() {
    return getTypeDeclListNoTransform();
  }
  /**
   * Replaces the Modifiers child.
   * @param node The new node to replace the Modifiers child.
   * @apilevel high-level
   */
  public void setModifiers(Modifiers node) {
    setChild(node, 2);
  }
  /**
   * Retrieves the Modifiers child.
   * @return The current node used as the Modifiers child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Modifiers")
  public Modifiers getModifiers() {
    return (Modifiers) getChild(2);
  }
  /**
   * Retrieves the Modifiers child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Modifiers child.
   * @apilevel low-level
   */
  public Modifiers getModifiersNoTransform() {
    return (Modifiers) getChildNoTransform(2);
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:131
   * @apilevel internal
   */
  public boolean Define_mayUseAnnotationTarget(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (getModifiersNoTransform() != null && _callerNode == getModifiers()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:134
      return name.equals("PACKAGE");
    }
    else {
      return getParent().Define_mayUseAnnotationTarget(this, _callerNode, name);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:131
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayUseAnnotationTarget
   */
  protected boolean canDefine_mayUseAnnotationTarget(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:637
   * @apilevel internal
   */
  public String Define_hostPackage(ASTNode _callerNode, ASTNode _childNode) {
    if (getModifiersNoTransform() != null && _callerNode == getModifiers()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:780
      return packageName();
    }
    else {
      return super.Define_hostPackage(_callerNode, _childNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:637
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute hostPackage
   */
  protected boolean canDefine_hostPackage(ASTNode _callerNode, ASTNode _childNode) {
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
  /** @apilevel internal */
  protected void collect_contributors_CompilationUnit_problems(CompilationUnit _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:51
    if (!relativeName().endsWith("package-info.java")) {
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
    if (!relativeName().endsWith("package-info.java")) {
      collection.add(error("package annotations should be in a file package-info.java"));
    }
  }
}
