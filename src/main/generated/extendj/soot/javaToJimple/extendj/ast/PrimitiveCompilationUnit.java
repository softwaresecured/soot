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
 * A synthetic type of compilation unit containing the
 * synthetic type declarations for primitive types.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:51
 * @astdecl PrimitiveCompilationUnit : CompilationUnit;
 * @production PrimitiveCompilationUnit : {@link CompilationUnit};

 */
public class PrimitiveCompilationUnit extends CompilationUnit implements Cloneable {
  /**
   * @declaredat ASTNode:1
   */
  public PrimitiveCompilationUnit() {
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
    setChild(new List(), 0);
    setChild(new List(), 1);
  }
  /**
   * @declaredat ASTNode:15
   */
  @ASTNodeAnnotation.Constructor(
    name = {"PackageDecl", "ImportDecl", "TypeDecl"},
    type = {"String", "List<ImportDecl>", "List<TypeDecl>"},
    kind = {"Token", "List", "List"}
  )
  public PrimitiveCompilationUnit(String p0, List<ImportDecl> p1, List<TypeDecl> p2) {
    setPackageDecl(p0);
    setChild(p1, 0);
    setChild(p2, 1);
  }
  /**
   * @declaredat ASTNode:25
   */
  public PrimitiveCompilationUnit(beaver.Symbol p0, List<ImportDecl> p1, List<TypeDecl> p2) {
    setPackageDecl(p0);
    setChild(p1, 0);
    setChild(p2, 1);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:31
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:37
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:41
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    typeBoolean_reset();
    typeByte_reset();
    typeShort_reset();
    typeChar_reset();
    typeInt_reset();
    typeLong_reset();
    typeFloat_reset();
    typeDouble_reset();
    typeVoid_reset();
    typeNull_reset();
    unknownType_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:56
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:60
   */
  public PrimitiveCompilationUnit clone() throws CloneNotSupportedException {
    PrimitiveCompilationUnit node = (PrimitiveCompilationUnit) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:65
   */
  public PrimitiveCompilationUnit copy() {
    try {
      PrimitiveCompilationUnit node = (PrimitiveCompilationUnit) clone();
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
   * @declaredat ASTNode:84
   */
  @Deprecated
  public PrimitiveCompilationUnit fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:94
   */
  public PrimitiveCompilationUnit treeCopyNoTransform() {
    PrimitiveCompilationUnit tree = (PrimitiveCompilationUnit) copy();
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
   * @declaredat ASTNode:114
   */
  public PrimitiveCompilationUnit treeCopy() {
    PrimitiveCompilationUnit tree = (PrimitiveCompilationUnit) copy();
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
   * @declaredat ASTNode:128
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_PackageDecl == ((PrimitiveCompilationUnit) node).tokenString_PackageDecl);    
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
  /** @apilevel internal */
  private void typeBoolean_reset() {
    typeBoolean_computed = false;
    
    typeBoolean_value = null;
  }
  /** @apilevel internal */
  protected boolean typeBoolean_computed = false;

  /** @apilevel internal */
  protected TypeDecl typeBoolean_value;

  /**
   * @attribute syn
   * @aspect PrimitiveTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:37
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="PrimitiveTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:37")
  public TypeDecl typeBoolean() {
    ASTState state = state();
    if (typeBoolean_computed) {
      return typeBoolean_value;
    }
    state().enterLazyAttribute();
    typeBoolean_value = typeBoolean_compute();
    typeBoolean_value.setParent(this);
    typeBoolean_computed = true;
    state().leaveLazyAttribute();
    return typeBoolean_value;
  }
  /** @apilevel internal */
  private TypeDecl typeBoolean_compute() {
      BooleanType type = new BooleanType();
      type.setModifiers(new Modifiers(new List().add(new Modifier("public"))));
      type.setID("boolean");
      type.setSuperClass(unknownType().createQualifiedAccess());
      return type;
    }
  /** @apilevel internal */
  private void typeByte_reset() {
    typeByte_computed = false;
    
    typeByte_value = null;
  }
  /** @apilevel internal */
  protected boolean typeByte_computed = false;

  /** @apilevel internal */
  protected TypeDecl typeByte_value;

  /**
   * @attribute syn
   * @aspect PrimitiveTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:45
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="PrimitiveTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:45")
  public TypeDecl typeByte() {
    ASTState state = state();
    if (typeByte_computed) {
      return typeByte_value;
    }
    state().enterLazyAttribute();
    typeByte_value = typeByte_compute();
    typeByte_value.setParent(this);
    typeByte_computed = true;
    state().leaveLazyAttribute();
    return typeByte_value;
  }
  /** @apilevel internal */
  private TypeDecl typeByte_compute() {
      ByteType type = new ByteType();
      type.setModifiers(new Modifiers(new List().add(new Modifier("public"))));
      type.setID("byte");
      type.setSuperClass(typeShort().createQualifiedAccess());
      return type;
    }
  /** @apilevel internal */
  private void typeShort_reset() {
    typeShort_computed = false;
    
    typeShort_value = null;
  }
  /** @apilevel internal */
  protected boolean typeShort_computed = false;

  /** @apilevel internal */
  protected TypeDecl typeShort_value;

  /**
   * @attribute syn
   * @aspect PrimitiveTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:53
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="PrimitiveTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:53")
  public TypeDecl typeShort() {
    ASTState state = state();
    if (typeShort_computed) {
      return typeShort_value;
    }
    state().enterLazyAttribute();
    typeShort_value = typeShort_compute();
    typeShort_value.setParent(this);
    typeShort_computed = true;
    state().leaveLazyAttribute();
    return typeShort_value;
  }
  /** @apilevel internal */
  private TypeDecl typeShort_compute() {
      ShortType type = new ShortType();
      type.setModifiers(new Modifiers(new List().add(new Modifier("public"))));
      type.setID("short");
      type.setSuperClass(typeInt().createQualifiedAccess());
      return type;
    }
  /** @apilevel internal */
  private void typeChar_reset() {
    typeChar_computed = false;
    
    typeChar_value = null;
  }
  /** @apilevel internal */
  protected boolean typeChar_computed = false;

  /** @apilevel internal */
  protected TypeDecl typeChar_value;

  /**
   * @attribute syn
   * @aspect PrimitiveTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:61
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="PrimitiveTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:61")
  public TypeDecl typeChar() {
    ASTState state = state();
    if (typeChar_computed) {
      return typeChar_value;
    }
    state().enterLazyAttribute();
    typeChar_value = typeChar_compute();
    typeChar_value.setParent(this);
    typeChar_computed = true;
    state().leaveLazyAttribute();
    return typeChar_value;
  }
  /** @apilevel internal */
  private TypeDecl typeChar_compute() {
      CharType type = new CharType();
      type.setModifiers(new Modifiers(new List().add(new Modifier("public"))));
      type.setID("char");
      type.setSuperClass(typeInt().createQualifiedAccess());
      return type;
    }
  /** @apilevel internal */
  private void typeInt_reset() {
    typeInt_computed = false;
    
    typeInt_value = null;
  }
  /** @apilevel internal */
  protected boolean typeInt_computed = false;

  /** @apilevel internal */
  protected TypeDecl typeInt_value;

  /**
   * @attribute syn
   * @aspect PrimitiveTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:69
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="PrimitiveTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:69")
  public TypeDecl typeInt() {
    ASTState state = state();
    if (typeInt_computed) {
      return typeInt_value;
    }
    state().enterLazyAttribute();
    typeInt_value = typeInt_compute();
    typeInt_value.setParent(this);
    typeInt_computed = true;
    state().leaveLazyAttribute();
    return typeInt_value;
  }
  /** @apilevel internal */
  private TypeDecl typeInt_compute() {
      IntType type = new IntType();
      type.setModifiers(new Modifiers(new List().add(new Modifier("public"))));
      type.setID("int");
      type.setSuperClass(typeLong().createQualifiedAccess());
      return type;
    }
  /** @apilevel internal */
  private void typeLong_reset() {
    typeLong_computed = false;
    
    typeLong_value = null;
  }
  /** @apilevel internal */
  protected boolean typeLong_computed = false;

  /** @apilevel internal */
  protected TypeDecl typeLong_value;

  /**
   * @attribute syn
   * @aspect PrimitiveTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:77
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="PrimitiveTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:77")
  public TypeDecl typeLong() {
    ASTState state = state();
    if (typeLong_computed) {
      return typeLong_value;
    }
    state().enterLazyAttribute();
    typeLong_value = typeLong_compute();
    typeLong_value.setParent(this);
    typeLong_computed = true;
    state().leaveLazyAttribute();
    return typeLong_value;
  }
  /** @apilevel internal */
  private TypeDecl typeLong_compute() {
      LongType type = new LongType();
      type.setModifiers(new Modifiers(new List().add(new Modifier("public"))));
      type.setID("long");
      // TODO(joqivst): investigate this.
      // Float does not seem right here, keeping it because the old code does this.
      type.setSuperClass(typeFloat().createQualifiedAccess());
      return type;
    }
  /** @apilevel internal */
  private void typeFloat_reset() {
    typeFloat_computed = false;
    
    typeFloat_value = null;
  }
  /** @apilevel internal */
  protected boolean typeFloat_computed = false;

  /** @apilevel internal */
  protected TypeDecl typeFloat_value;

  /**
   * @attribute syn
   * @aspect PrimitiveTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:87
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="PrimitiveTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:87")
  public TypeDecl typeFloat() {
    ASTState state = state();
    if (typeFloat_computed) {
      return typeFloat_value;
    }
    state().enterLazyAttribute();
    typeFloat_value = typeFloat_compute();
    typeFloat_value.setParent(this);
    typeFloat_computed = true;
    state().leaveLazyAttribute();
    return typeFloat_value;
  }
  /** @apilevel internal */
  private TypeDecl typeFloat_compute() {
      FloatType type = new FloatType();
      type.setModifiers(new Modifiers(new List().add(new Modifier("public"))));
      type.setID("float");
      type.setSuperClass(typeDouble().createQualifiedAccess());
      return type;
    }
  /** @apilevel internal */
  private void typeDouble_reset() {
    typeDouble_computed = false;
    
    typeDouble_value = null;
  }
  /** @apilevel internal */
  protected boolean typeDouble_computed = false;

  /** @apilevel internal */
  protected TypeDecl typeDouble_value;

  /**
   * @attribute syn
   * @aspect PrimitiveTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:95
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="PrimitiveTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:95")
  public TypeDecl typeDouble() {
    ASTState state = state();
    if (typeDouble_computed) {
      return typeDouble_value;
    }
    state().enterLazyAttribute();
    typeDouble_value = typeDouble_compute();
    typeDouble_value.setParent(this);
    typeDouble_computed = true;
    state().leaveLazyAttribute();
    return typeDouble_value;
  }
  /** @apilevel internal */
  private TypeDecl typeDouble_compute() {
      DoubleType type = new DoubleType();
      type.setModifiers(new Modifiers(new List().add(new Modifier("public"))));
      type.setID("double");
      type.setSuperClass(unknownType().createQualifiedAccess());
      return type;
    }
  /** @apilevel internal */
  private void typeVoid_reset() {
    typeVoid_computed = false;
    
    typeVoid_value = null;
  }
  /** @apilevel internal */
  protected boolean typeVoid_computed = false;

  /** @apilevel internal */
  protected TypeDecl typeVoid_value;

  /**
   * @attribute syn
   * @aspect PrimitiveTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:103
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="PrimitiveTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:103")
  public TypeDecl typeVoid() {
    ASTState state = state();
    if (typeVoid_computed) {
      return typeVoid_value;
    }
    state().enterLazyAttribute();
    typeVoid_value = typeVoid_compute();
    typeVoid_value.setParent(this);
    typeVoid_computed = true;
    state().leaveLazyAttribute();
    return typeVoid_value;
  }
  /** @apilevel internal */
  private TypeDecl typeVoid_compute() {
      VoidType classDecl = new VoidType();
      classDecl.setModifiers(new Modifiers(new List().add(new Modifier("public"))));
      classDecl.setID("void");
      return classDecl;
    }
  /** @apilevel internal */
  private void typeNull_reset() {
    typeNull_computed = false;
    
    typeNull_value = null;
  }
  /** @apilevel internal */
  protected boolean typeNull_computed = false;

  /** @apilevel internal */
  protected TypeDecl typeNull_value;

  /**
   * @attribute syn
   * @aspect PrimitiveTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:110
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="PrimitiveTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:110")
  public TypeDecl typeNull() {
    ASTState state = state();
    if (typeNull_computed) {
      return typeNull_value;
    }
    state().enterLazyAttribute();
    typeNull_value = typeNull_compute();
    typeNull_value.setParent(this);
    typeNull_computed = true;
    state().leaveLazyAttribute();
    return typeNull_value;
  }
  /** @apilevel internal */
  private TypeDecl typeNull_compute() {
      NullType classDecl = new NullType();
      classDecl.setModifiers(new Modifiers(new List().add(new Modifier("public"))));
      classDecl.setID("null");
      return classDecl;
    }
  /** @apilevel internal */
  private void unknownType_reset() {
    unknownType_computed = false;
    
    unknownType_value = null;
  }
  /** @apilevel internal */
  protected boolean unknownType_computed = false;

  /** @apilevel internal */
  protected TypeDecl unknownType_value;

  /**
   * @attribute syn
   * @aspect PrimitiveTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:117
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="PrimitiveTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:117")
  public TypeDecl unknownType() {
    ASTState state = state();
    if (unknownType_computed) {
      return unknownType_value;
    }
    state().enterLazyAttribute();
    unknownType_value = unknownType_compute();
    unknownType_value.setParent(this);
    unknownType_computed = true;
    state().leaveLazyAttribute();
    return unknownType_value;
  }
  /** @apilevel internal */
  private TypeDecl unknownType_compute() {
      ClassDecl classDecl = new UnknownType();
      classDecl.setModifiers(new Modifiers(new List().add(new Modifier("public"))));
      classDecl.setID("Unknown");
      MethodDecl methodDecl = new MethodDecl(
          new Modifiers(new List().add(
            new Modifier("public")
          )),
          new PrimitiveTypeAccess("Unknown"),
          "unknown",
          new List(),
          new List(),
          new Opt()
      );
      classDecl.addBodyDecl(methodDecl);
      FieldDeclarator unknown = new FieldDeclarator(
          "unknown",
          new List<Dims>(),
          new Opt<Expr>());
      FieldDecl fieldDecl = new FieldDecl(
          new Modifiers(new List<Modifier>(new Modifier("public"))),
          new PrimitiveTypeAccess("Unknown"),
          new List<FieldDeclarator>(unknown)
      );
      classDecl.addBodyDecl(fieldDecl);
      ConstructorDecl constrDecl = new ConstructorDecl(
        new Modifiers(new List().add(new Modifier("public"))),
        "Unknown",
        new List(),
        new List(),
        new Opt(),
        new Block()
      );
      classDecl.addBodyDecl(constrDecl);
      return classDecl;
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
