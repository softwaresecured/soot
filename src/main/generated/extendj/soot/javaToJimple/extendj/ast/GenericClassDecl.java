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
/** A generic class declaration. 
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java5/grammar/Generics.ast:2
 * @production GenericClassDecl : {@link ClassDecl} ::= <span class="component">{@link Modifiers}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">[SuperClass:{@link Access}]</span> <span class="component">Implements:{@link Access}*</span> <span class="component">{@link BodyDecl}*</span> <span class="component">TypeParameter:{@link TypeVariable}*</span>;

 */
public class GenericClassDecl extends ClassDecl implements Cloneable, GenericTypeDecl {
  /**
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1504
   */
  public ClassDecl signatureCopy() {
    return new GenericClassDeclSubstituted(
        getModifiers().treeCopyNoTransform(),
        getID(),
        getSuperClassOpt().treeCopyNoTransform(),
        getImplementsList().treeCopyNoTransform(),
        getTypeParameterList().treeCopyNoTransform(),
        this);
  }
  /**
   * @aspect Java5PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java5/frontend/PrettyPrint.jadd:145
   */
  public void prettyPrint(PrettyPrinter out) {
    if (hasDocComment()) {
      out.print(docComment());
    }
    if (!out.isNewLine()) {
      out.println();
    }
    out.print(getModifiers());
    out.print("class ");
    out.print(getID());
    out.print("<");
    out.join(getTypeParameterList(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.print(", ");
      }
    });
    out.print(">");
    if (hasSuperClass()) {
      out.print(" extends ");
      out.print(getSuperClass());
    }
    if (hasImplements()) {
      out.print(" implements ");
      out.join(getImplementss(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(", ");
        }
      });
    }
    out.print(" {");
    out.println();
    out.indent(1);
    out.join(getBodyDecls(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.println();
        out.println();
      }
    });
    if (!out.isNewLine()) {
      out.println();
    }
    out.print("}");
  }
  /**
   * @aspect Generics
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:333
   */
  public TypeDecl makeGeneric(Signatures.ClassSignature s) {
    return (TypeDecl) this;
  }
  /**
   * Helper method used to interject type variable substitution into the type
   * lookup process for generic type scopes.
   * 
   * <p>When the type variables should be visible to a typename lookup from
   * inside this generic type scope, this helper method will check if there
   * exists a type variable with the given name. If so, that type variable is
   * the target for the lookup.
   * @aspect GenericsNameBinding
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:777
   */
  public SimpleSet<TypeDecl> addTypeVariables(SimpleSet<TypeDecl> types,
      String name) {
    GenericTypeDecl original = (GenericTypeDecl) original();
    for (int i = 0; i < original.getNumTypeParameter(); i++) {
      TypeVariable p = original.getTypeParameter(i);
      if (p.name().equals(name)) {
        types = types.add(p);
      }
    }
    return types;
  }
  /**
   * @declaredat ASTNode:1
   */
  public GenericClassDecl() {
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
    children = new ASTNode[6];
    setChild(new Opt(), 1);
    setChild(new List(), 2);
    setChild(new List(), 3);
    setChild(new List(), 4);
    setChild(new Opt(), 5);
  }
  /**
   * @declaredat ASTNode:18
   */
  public GenericClassDecl(Modifiers p0, String p1, Opt<Access> p2, List<Access> p3, List<BodyDecl> p4, List<TypeVariable> p5) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
    setChild(p5, 4);
  }
  /**
   * @declaredat ASTNode:26
   */
  public GenericClassDecl(Modifiers p0, beaver.Symbol p1, Opt<Access> p2, List<Access> p3, List<BodyDecl> p4, List<TypeVariable> p5) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
    setChild(p5, 4);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:35
   */
  protected int numChildren() {
    return 5;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:41
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:45
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    rawType_reset();
    lookupParTypeDecl_Collection_TypeDecl__reset();
    usesTypeVariable_reset();
    subtype_TypeDecl_reset();
    instanceOf_TypeDecl_reset();
    getStandInMethodList_reset();
    strictSubtype_TypeDecl_reset();
    needsSignatureAttribute_reset();
    classSignature_reset();
    lookupParTypeDecl_ParTypeAccess_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:59
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:63
   */
  public GenericClassDecl clone() throws CloneNotSupportedException {
    GenericClassDecl node = (GenericClassDecl) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:68
   */
  public GenericClassDecl copy() {
    try {
      GenericClassDecl node = (GenericClassDecl) clone();
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
   * @declaredat ASTNode:87
   */
  @Deprecated
  public GenericClassDecl fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:97
   */
  public GenericClassDecl treeCopyNoTransform() {
    GenericClassDecl tree = (GenericClassDecl) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 5:
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
   * @declaredat ASTNode:122
   */
  public GenericClassDecl treeCopy() {
    GenericClassDecl tree = (GenericClassDecl) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 5:
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
   * @declaredat ASTNode:141
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((GenericClassDecl) node).tokenString_ID);    
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
   * Replaces the lexeme ID.
   * @param value The new value for the lexeme ID.
   * @apilevel high-level
   */
  public void setID(String value) {
    tokenString_ID = value;
  }
  /**
   * JastAdd-internal setter for lexeme ID using the Beaver parser.
   * @param symbol Symbol containing the new value for the lexeme ID
   * @apilevel internal
   */
  public void setID(beaver.Symbol symbol) {
    if (symbol.value != null && !(symbol.value instanceof String))
    throw new UnsupportedOperationException("setID is only valid for String lexemes");
    tokenString_ID = (String)symbol.value;
    IDstart = symbol.getStart();
    IDend = symbol.getEnd();
  }
  /**
   * Retrieves the value for the lexeme ID.
   * @return The value for the lexeme ID.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Token(name="ID")
  public String getID() {
    return tokenString_ID != null ? tokenString_ID : "";
  }
  /**
   * Replaces the optional node for the SuperClass child. This is the <code>Opt</code>
   * node containing the child SuperClass, not the actual child!
   * @param opt The new node to be used as the optional node for the SuperClass child.
   * @apilevel low-level
   */
  public void setSuperClassOpt(Opt<Access> opt) {
    setChild(opt, 1);
  }
  /**
   * Replaces the (optional) SuperClass child.
   * @param node The new node to be used as the SuperClass child.
   * @apilevel high-level
   */
  public void setSuperClass(Access node) {
    getSuperClassOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional SuperClass child exists.
   * @return {@code true} if the optional SuperClass child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasSuperClass() {
    return getSuperClassOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) SuperClass child.
   * @return The SuperClass child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Access getSuperClass() {
    return (Access) getSuperClassOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the SuperClass child. This is the <code>Opt</code> node containing the child SuperClass, not the actual child!
   * @return The optional node for child the SuperClass child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="SuperClass")
  public Opt<Access> getSuperClassOpt() {
    return (Opt<Access>) getChild(1);
  }
  /**
   * Retrieves the optional node for child SuperClass. This is the <code>Opt</code> node containing the child SuperClass, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child SuperClass.
   * @apilevel low-level
   */
  public Opt<Access> getSuperClassOptNoTransform() {
    return (Opt<Access>) getChildNoTransform(1);
  }
  /**
   * Replaces the Implements list.
   * @param list The new list node to be used as the Implements list.
   * @apilevel high-level
   */
  public void setImplementsList(List<Access> list) {
    setChild(list, 2);
  }
  /**
   * Retrieves the number of children in the Implements list.
   * @return Number of children in the Implements list.
   * @apilevel high-level
   */
  public int getNumImplements() {
    return getImplementsList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Implements list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Implements list.
   * @apilevel low-level
   */
  public int getNumImplementsNoTransform() {
    return getImplementsListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Implements list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Implements list.
   * @apilevel high-level
   */
  public Access getImplements(int i) {
    return (Access) getImplementsList().getChild(i);
  }
  /**
   * Check whether the Implements list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasImplements() {
    return getImplementsList().getNumChild() != 0;
  }
  /**
   * Append an element to the Implements list.
   * @param node The element to append to the Implements list.
   * @apilevel high-level
   */
  public void addImplements(Access node) {
    List<Access> list = (parent == null) ? getImplementsListNoTransform() : getImplementsList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addImplementsNoTransform(Access node) {
    List<Access> list = getImplementsListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Implements list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setImplements(Access node, int i) {
    List<Access> list = getImplementsList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Implements list.
   * @return The node representing the Implements list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Implements")
  public List<Access> getImplementsList() {
    List<Access> list = (List<Access>) getChild(2);
    return list;
  }
  /**
   * Retrieves the Implements list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Implements list.
   * @apilevel low-level
   */
  public List<Access> getImplementsListNoTransform() {
    return (List<Access>) getChildNoTransform(2);
  }
  /**
   * @return the element at index {@code i} in the Implements list without
   * triggering rewrites.
   */
  public Access getImplementsNoTransform(int i) {
    return (Access) getImplementsListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the Implements list.
   * @return The node representing the Implements list.
   * @apilevel high-level
   */
  public List<Access> getImplementss() {
    return getImplementsList();
  }
  /**
   * Retrieves the Implements list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Implements list.
   * @apilevel low-level
   */
  public List<Access> getImplementssNoTransform() {
    return getImplementsListNoTransform();
  }
  /**
   * Replaces the BodyDecl list.
   * @param list The new list node to be used as the BodyDecl list.
   * @apilevel high-level
   */
  public void setBodyDeclList(List<BodyDecl> list) {
    setChild(list, 3);
  }
  /**
   * Retrieves the number of children in the BodyDecl list.
   * @return Number of children in the BodyDecl list.
   * @apilevel high-level
   */
  public int getNumBodyDecl() {
    return getBodyDeclList().getNumChild();
  }
  /**
   * Retrieves the number of children in the BodyDecl list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the BodyDecl list.
   * @apilevel low-level
   */
  public int getNumBodyDeclNoTransform() {
    return getBodyDeclListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the BodyDecl list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the BodyDecl list.
   * @apilevel high-level
   */
  public BodyDecl getBodyDecl(int i) {
    return (BodyDecl) getBodyDeclList().getChild(i);
  }
  /**
   * Check whether the BodyDecl list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasBodyDecl() {
    return getBodyDeclList().getNumChild() != 0;
  }
  /**
   * Append an element to the BodyDecl list.
   * @param node The element to append to the BodyDecl list.
   * @apilevel high-level
   */
  public void addBodyDecl(BodyDecl node) {
    List<BodyDecl> list = (parent == null) ? getBodyDeclListNoTransform() : getBodyDeclList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addBodyDeclNoTransform(BodyDecl node) {
    List<BodyDecl> list = getBodyDeclListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the BodyDecl list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setBodyDecl(BodyDecl node, int i) {
    List<BodyDecl> list = getBodyDeclList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the BodyDecl list.
   * @return The node representing the BodyDecl list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="BodyDecl")
  public List<BodyDecl> getBodyDeclList() {
    List<BodyDecl> list = (List<BodyDecl>) getChild(3);
    return list;
  }
  /**
   * Retrieves the BodyDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the BodyDecl list.
   * @apilevel low-level
   */
  public List<BodyDecl> getBodyDeclListNoTransform() {
    return (List<BodyDecl>) getChildNoTransform(3);
  }
  /**
   * @return the element at index {@code i} in the BodyDecl list without
   * triggering rewrites.
   */
  public BodyDecl getBodyDeclNoTransform(int i) {
    return (BodyDecl) getBodyDeclListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the BodyDecl list.
   * @return The node representing the BodyDecl list.
   * @apilevel high-level
   */
  public List<BodyDecl> getBodyDecls() {
    return getBodyDeclList();
  }
  /**
   * Retrieves the BodyDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the BodyDecl list.
   * @apilevel low-level
   */
  public List<BodyDecl> getBodyDeclsNoTransform() {
    return getBodyDeclListNoTransform();
  }
  /**
   * Replaces the TypeParameter list.
   * @param list The new list node to be used as the TypeParameter list.
   * @apilevel high-level
   */
  public void setTypeParameterList(List<TypeVariable> list) {
    setChild(list, 4);
  }
  /**
   * Retrieves the number of children in the TypeParameter list.
   * @return Number of children in the TypeParameter list.
   * @apilevel high-level
   */
  public int getNumTypeParameter() {
    return getTypeParameterList().getNumChild();
  }
  /**
   * Retrieves the number of children in the TypeParameter list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the TypeParameter list.
   * @apilevel low-level
   */
  public int getNumTypeParameterNoTransform() {
    return getTypeParameterListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the TypeParameter list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the TypeParameter list.
   * @apilevel high-level
   */
  public TypeVariable getTypeParameter(int i) {
    return (TypeVariable) getTypeParameterList().getChild(i);
  }
  /**
   * Check whether the TypeParameter list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasTypeParameter() {
    return getTypeParameterList().getNumChild() != 0;
  }
  /**
   * Append an element to the TypeParameter list.
   * @param node The element to append to the TypeParameter list.
   * @apilevel high-level
   */
  public void addTypeParameter(TypeVariable node) {
    List<TypeVariable> list = (parent == null) ? getTypeParameterListNoTransform() : getTypeParameterList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addTypeParameterNoTransform(TypeVariable node) {
    List<TypeVariable> list = getTypeParameterListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the TypeParameter list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setTypeParameter(TypeVariable node, int i) {
    List<TypeVariable> list = getTypeParameterList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the TypeParameter list.
   * @return The node representing the TypeParameter list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="TypeParameter")
  public List<TypeVariable> getTypeParameterList() {
    List<TypeVariable> list = (List<TypeVariable>) getChild(4);
    return list;
  }
  /**
   * Retrieves the TypeParameter list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeParameter list.
   * @apilevel low-level
   */
  public List<TypeVariable> getTypeParameterListNoTransform() {
    return (List<TypeVariable>) getChildNoTransform(4);
  }
  /**
   * @return the element at index {@code i} in the TypeParameter list without
   * triggering rewrites.
   */
  public TypeVariable getTypeParameterNoTransform(int i) {
    return (TypeVariable) getTypeParameterListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the TypeParameter list.
   * @return The node representing the TypeParameter list.
   * @apilevel high-level
   */
  public List<TypeVariable> getTypeParameters() {
    return getTypeParameterList();
  }
  /**
   * Retrieves the TypeParameter list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeParameter list.
   * @apilevel low-level
   */
  public List<TypeVariable> getTypeParametersNoTransform() {
    return getTypeParameterListNoTransform();
  }
  /**
   * Replaces the (optional) ImplicitConstructor child.
   * @param node The new node to be used as the ImplicitConstructor child.
   * @apilevel high-level
   */
  public void setImplicitConstructor(ConstructorDecl node) {
    getImplicitConstructorOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional ImplicitConstructor child exists.
   * @return {@code true} if the optional ImplicitConstructor child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasImplicitConstructor() {
    return getImplicitConstructorOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) ImplicitConstructor child.
   * @return The ImplicitConstructor child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public ConstructorDecl getImplicitConstructor() {
    return (ConstructorDecl) getImplicitConstructorOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for child ImplicitConstructor. This is the <code>Opt</code> node containing the child ImplicitConstructor, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child ImplicitConstructor.
   * @apilevel low-level
   */
  public Opt<ConstructorDecl> getImplicitConstructorOptNoTransform() {
    return (Opt<ConstructorDecl>) getChildNoTransform(5);
  }
  /**
   * Retrieves the child position of the optional child ImplicitConstructor.
   * @return The the child position of the optional child ImplicitConstructor.
   * @apilevel low-level
   */
  protected int getImplicitConstructorOptChildPosition() {
    return 5;
  }
  /** @apilevel internal */
  private void rawType_reset() {
    rawType_computed = null;
    rawType_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle rawType_computed = null;

  /** @apilevel internal */
  protected TypeDecl rawType_value;

  /**
   * @attribute syn
   * @aspect Generics
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:248
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Generics", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:248")
  public TypeDecl rawType() {
    ASTNode$State state = state();
    if (rawType_computed == ASTNode$State.NON_CYCLE || rawType_computed == state().cycle()) {
      return rawType_value;
    }
    rawType_value = lookupParTypeDecl(Collections.<TypeDecl>emptyList());
    if (state().inCircle()) {
      rawType_computed = state().cycle();
    
    } else {
      rawType_computed = ASTNode$State.NON_CYCLE;
    
    }
    return rawType_value;
  }
  /** @apilevel internal */
  private void lookupParTypeDecl_Collection_TypeDecl__reset() {
    lookupParTypeDecl_Collection_TypeDecl__values = null;
    lookupParTypeDecl_Collection_TypeDecl__list = null;
  }
  /** @apilevel internal */
  protected List lookupParTypeDecl_Collection_TypeDecl__list;
  /** @apilevel internal */
  protected java.util.Map lookupParTypeDecl_Collection_TypeDecl__values;

  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:953
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:953")
  public TypeDecl lookupParTypeDecl(Collection<TypeDecl> typeArgs) {
    Object _parameters = typeArgs;
    if (lookupParTypeDecl_Collection_TypeDecl__values == null) lookupParTypeDecl_Collection_TypeDecl__values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (lookupParTypeDecl_Collection_TypeDecl__values.containsKey(_parameters)) {
      return (TypeDecl) lookupParTypeDecl_Collection_TypeDecl__values.get(_parameters);
    }
    state().enterLazyAttribute();
    TypeDecl lookupParTypeDecl_Collection_TypeDecl__value = lookupParTypeDecl_compute(typeArgs);
    if (lookupParTypeDecl_Collection_TypeDecl__list == null) {
      lookupParTypeDecl_Collection_TypeDecl__list = new List();
      lookupParTypeDecl_Collection_TypeDecl__list.setParent(this);
    }
    lookupParTypeDecl_Collection_TypeDecl__list.add(lookupParTypeDecl_Collection_TypeDecl__value);
    if (lookupParTypeDecl_Collection_TypeDecl__value != null) {
      lookupParTypeDecl_Collection_TypeDecl__value = (TypeDecl) lookupParTypeDecl_Collection_TypeDecl__list.getChild(lookupParTypeDecl_Collection_TypeDecl__list.numChildren - 1);
    }
    lookupParTypeDecl_Collection_TypeDecl__values.put(_parameters, lookupParTypeDecl_Collection_TypeDecl__value);
    state().leaveLazyAttribute();
    return lookupParTypeDecl_Collection_TypeDecl__value;
  }
  /** @apilevel internal */
  private TypeDecl lookupParTypeDecl_compute(Collection<TypeDecl> typeArgs) {
      Parameterization parameterization = new Parameterization(getTypeParameterList(), typeArgs);
      ParClassDecl typeDecl;
      if (typeArgs.isEmpty()) {
        // According to JLSv8 4.6 (Type Erasure), the signature of members in the
        // erased type have no parameterized types or type variables.
        typeDecl = new RawClassDecl(
            getModifiers().treeCopyNoTransform(),
            getID(),
            getTypeParameterList().treeCopyNoTransform(),
            parameterization,
            hasSuperClass() ? new Opt<Access>(getSuperClass().erasedCopy()) : new Opt<Access>(),
            erasedAccessList(getImplementsList()));
      } else {
        typeDecl = new ParClassDecl(
            getModifiers().treeCopyNoTransform(),
            getID(),
            getTypeParameterList().treeCopyNoTransform(),
            parameterization,
            getSuperClassOpt().treeCopyNoTransform(),
            getImplementsList().treeCopyNoTransform());
      }
      return typeDecl;
    }
/** @apilevel internal */
protected ASTNode$State.Cycle usesTypeVariable_cycle = null;
  /** @apilevel internal */
  private void usesTypeVariable_reset() {
    usesTypeVariable_computed = false;
    usesTypeVariable_initialized = false;
    usesTypeVariable_cycle = null;
  }
  /** @apilevel internal */
  protected boolean usesTypeVariable_computed = false;

  /** @apilevel internal */
  protected boolean usesTypeVariable_value;
  /** @apilevel internal */
  protected boolean usesTypeVariable_initialized = false;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:1325")
  public boolean usesTypeVariable() {
    if (usesTypeVariable_computed) {
      return usesTypeVariable_value;
    }
    ASTNode$State state = state();
    if (!usesTypeVariable_initialized) {
      usesTypeVariable_initialized = true;
      usesTypeVariable_value = false;
    }
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      do {
        usesTypeVariable_cycle = state.nextCycle();
        boolean new_usesTypeVariable_value = true;
        if (new_usesTypeVariable_value != usesTypeVariable_value) {
          state.setChangeInCycle();
        }
        usesTypeVariable_value = new_usesTypeVariable_value;
      } while (state.testAndClearChangeInCycle());
      usesTypeVariable_computed = true;

      state.leaveCircle();
    } else if (usesTypeVariable_cycle != state.cycle()) {
      usesTypeVariable_cycle = state.cycle();
      boolean new_usesTypeVariable_value = true;
      if (new_usesTypeVariable_value != usesTypeVariable_value) {
        state.setChangeInCycle();
      }
      usesTypeVariable_value = new_usesTypeVariable_value;
    } else {
    }
    return usesTypeVariable_value;
  }
  /** @apilevel internal */
  private void subtype_TypeDecl_reset() {
    subtype_TypeDecl_values = null;
  }
  protected java.util.Map subtype_TypeDecl_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:492")
  public boolean subtype(TypeDecl type) {
    Object _parameters = type;
    if (subtype_TypeDecl_values == null) subtype_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (subtype_TypeDecl_values.containsKey(_parameters)) {
      Object _cache = subtype_TypeDecl_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      subtype_TypeDecl_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_subtype_TypeDecl_value;
      do {
        _value.cycle = state.nextCycle();
        new_subtype_TypeDecl_value = type.supertypeGenericClassDecl(this);
        if (new_subtype_TypeDecl_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_subtype_TypeDecl_value;
        }
      } while (state.testAndClearChangeInCycle());
      subtype_TypeDecl_values.put(_parameters, new_subtype_TypeDecl_value);

      state.leaveCircle();
      return new_subtype_TypeDecl_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_subtype_TypeDecl_value = type.supertypeGenericClassDecl(this);
      if (new_subtype_TypeDecl_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_subtype_TypeDecl_value;
      }
      return new_subtype_TypeDecl_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:148
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:148")
  public boolean supertypeParClassDecl(ParClassDecl type) {
    boolean supertypeParClassDecl_ParClassDecl_value = type.genericDecl().original().subtype(this);
    return supertypeParClassDecl_ParClassDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:152
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:152")
  public boolean supertypeParInterfaceDecl(ParInterfaceDecl type) {
    boolean supertypeParInterfaceDecl_ParInterfaceDecl_value = type.genericDecl().original().subtype(this);
    return supertypeParInterfaceDecl_ParInterfaceDecl_value;
  }
  /** @apilevel internal */
  private void instanceOf_TypeDecl_reset() {
    instanceOf_TypeDecl_computed = new java.util.HashMap(4);
    instanceOf_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map instanceOf_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map instanceOf_TypeDecl_computed;
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:443
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeWideningAndIdentity", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:443")
  public boolean instanceOf(TypeDecl type) {
    Object _parameters = type;
    if (instanceOf_TypeDecl_computed == null) instanceOf_TypeDecl_computed = new java.util.HashMap(4);
    if (instanceOf_TypeDecl_values == null) instanceOf_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (instanceOf_TypeDecl_values.containsKey(_parameters) && instanceOf_TypeDecl_computed != null
        && instanceOf_TypeDecl_computed.containsKey(_parameters)
        && (instanceOf_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || instanceOf_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) instanceOf_TypeDecl_values.get(_parameters);
    }
    boolean instanceOf_TypeDecl_value = subtype(type);
    if (state().inCircle()) {
      instanceOf_TypeDecl_values.put(_parameters, instanceOf_TypeDecl_value);
      instanceOf_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      instanceOf_TypeDecl_values.put(_parameters, instanceOf_TypeDecl_value);
      instanceOf_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return instanceOf_TypeDecl_value;
  }
  /**
   * A type is reifiable if it either refers to a non-parameterized type,
   * is a raw type, is a parameterized type with only unbound wildcard
   * parameters or is an array type with a reifiable type parameter.
   * 
   * @see "JLS SE7 &sect;4.7"
   * @attribute syn
   * @aspect ReifiableTypes
   * @declaredat /home/olivier/projects/extendj/java5/frontend/ReifiableTypes.jrag:39
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ReifiableTypes", declaredAt="/home/olivier/projects/extendj/java5/frontend/ReifiableTypes.jrag:39")
  public boolean isReifiable() {
    boolean isReifiable_value = false;
    return isReifiable_value;
  }
  /** @apilevel internal */
  private void getStandInMethodList_reset() {
    getStandInMethodList_computed = false;
    
    getStandInMethodList_value = null;
  }
  /** @apilevel internal */
  protected boolean getStandInMethodList_computed = false;

  /** @apilevel internal */
  protected List<StandInMethodDecl> getStandInMethodList_value;

  /**
   * The stand-in method list for the constructors of this generic
   * class.  One stand-in method is generated for each constructor
   * of the generic class.
   * 
   * @return list of stand-in methods
   * @attribute syn
   * @aspect Diamond
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:164
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="Diamond", declaredAt="/home/olivier/projects/extendj/java7/frontend/Diamond.jrag:164")
  public List<StandInMethodDecl> getStandInMethodList() {
    ASTNode$State state = state();
    if (getStandInMethodList_computed) {
      return getStandInMethodList_value;
    }
    state().enterLazyAttribute();
    getStandInMethodList_value = getStandInMethodList_compute();
    getStandInMethodList_value.setParent(this);
    getStandInMethodList_computed = true;
    state().leaveLazyAttribute();
    return getStandInMethodList_value;
  }
  /** @apilevel internal */
  private List<StandInMethodDecl> getStandInMethodList_compute() {
      List<StandInMethodDecl> methods = new List<StandInMethodDecl>();
      List<TypeVariable> typeParams = getTypeParameterList();
      List<TypeVariable> classTypeVars = new List<TypeVariable>();
      List<Access> typeArgs = new List<Access>();
  
      // Copy the list of type parameters.
      int arg = 0;
      for (TypeVariable typeVar : typeParams) {
        String substName = "#" + (arg++);
        typeArgs.add(new TypeAccess(substName));
        List<Access> typeBounds = new List<Access>();
        for (Access typeBound : typeVar.getTypeBoundList()) {
          typeBounds.add((Access) typeBound.treeCopyNoTransform());
        }
        classTypeVars.add(new TypeVariable(
            new Modifiers(),
            substName,
            new List<BodyDecl>(),
            typeBounds));
      }
  
      ParTypeAccess returnType = new ParTypeAccess(
          createQualifiedAccess(),
          typeArgs);
  
      for (ConstructorDecl decl : constructors()) {
        if (decl instanceof ConstructorDeclSubstituted) {
          decl = ((ConstructorDeclSubstituted) decl).getOriginal();
        }
  
        // Filter accessible constructors.
        if (!decl.accessibleFrom(hostType())) {
          continue;
        }
  
        Collection<TypeVariable> originalTypeVars = new LinkedList<TypeVariable>();
        List<TypeVariable> typeVars = new List<TypeVariable>();
        for (TypeVariable typeVar : typeParams) {
          originalTypeVars.add(typeVar);
        }
        for (TypeVariable typeVar : classTypeVars) {
          typeVars.add((TypeVariable) typeVar.treeCopyNoTransform());
        }
  
        if (decl.isGeneric()) {
          GenericConstructorDecl genericDecl = decl.genericDecl();
          List<TypeVariable> typeVariables = new List<TypeVariable>();
          for (int i = 0; i < genericDecl.getNumTypeParameter(); ++i) {
            String substName = "#" + (arg+i);
  
            TypeVariable typeVar = genericDecl.getTypeParameter(i);
            originalTypeVars.add(typeVar);
            List<Access> typeBounds = new List<Access>();
            for (Access typeBound : typeVar.getTypeBoundList()) {
              typeBounds.add((Access) typeBound.treeCopyNoTransform());
            }
            typeVars.add(new TypeVariable(
                  new Modifiers(),
                  substName,
                  new List<BodyDecl>(),
                  typeBounds));
          }
        }
  
        List<ParameterDeclaration> substParameters = new List<ParameterDeclaration>();
        for (ParameterDeclaration param : decl.getParameterList()) {
          substParameters.add(param.substituted(originalTypeVars, typeVars));
        }
  
        List<Access> substExceptions = new List<Access>();
        for (Access exception : decl.getExceptionList()) {
          substExceptions.add(exception.substituted(originalTypeVars, typeVars));
        }
  
        StandInMethodDecl standInMethod = new StandInMethodDecl(
            (Modifiers) decl.getModifiers().treeCopyNoTransform(),
            (Access) returnType.treeCopyNoTransform(),
            "#" + getID(),
            substParameters,
            substExceptions,
            new Opt(new Block()),
            typeVars);
  
        methods.add(standInMethod);
      }
      return methods;
    }
  /** @apilevel internal */
  private void strictSubtype_TypeDecl_reset() {
    strictSubtype_TypeDecl_values = null;
  }
  protected java.util.Map strictSubtype_TypeDecl_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:363")
  public boolean strictSubtype(TypeDecl type) {
    Object _parameters = type;
    if (strictSubtype_TypeDecl_values == null) strictSubtype_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (strictSubtype_TypeDecl_values.containsKey(_parameters)) {
      Object _cache = strictSubtype_TypeDecl_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      strictSubtype_TypeDecl_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_strictSubtype_TypeDecl_value;
      do {
        _value.cycle = state.nextCycle();
        new_strictSubtype_TypeDecl_value = type.strictSupertypeGenericClassDecl(this);
        if (new_strictSubtype_TypeDecl_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_strictSubtype_TypeDecl_value;
        }
      } while (state.testAndClearChangeInCycle());
      strictSubtype_TypeDecl_values.put(_parameters, new_strictSubtype_TypeDecl_value);

      state.leaveCircle();
      return new_strictSubtype_TypeDecl_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_strictSubtype_TypeDecl_value = type.strictSupertypeGenericClassDecl(this);
      if (new_strictSubtype_TypeDecl_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_strictSubtype_TypeDecl_value;
      }
      return new_strictSubtype_TypeDecl_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:149
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:149")
  public boolean strictSupertypeParClassDecl(ParClassDecl type) {
    boolean strictSupertypeParClassDecl_ParClassDecl_value = type.genericDecl().original().strictSubtype(this);
    return strictSupertypeParClassDecl_ParClassDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:153
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:153")
  public boolean strictSupertypeParInterfaceDecl(ParInterfaceDecl type) {
    boolean strictSupertypeParInterfaceDecl_ParInterfaceDecl_value = type.genericDecl().original().strictSubtype(this);
    return strictSupertypeParInterfaceDecl_ParInterfaceDecl_value;
  }
  /** @apilevel internal */
  private void needsSignatureAttribute_reset() {
    needsSignatureAttribute_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle needsSignatureAttribute_computed = null;

  /** @apilevel internal */
  protected boolean needsSignatureAttribute_value;

  /**
   * @attribute syn
   * @aspect GenericsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:209
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:209")
  public boolean needsSignatureAttribute() {
    ASTNode$State state = state();
    if (needsSignatureAttribute_computed == ASTNode$State.NON_CYCLE || needsSignatureAttribute_computed == state().cycle()) {
      return needsSignatureAttribute_value;
    }
    needsSignatureAttribute_value = true;
    if (state().inCircle()) {
      needsSignatureAttribute_computed = state().cycle();
    
    } else {
      needsSignatureAttribute_computed = ASTNode$State.NON_CYCLE;
    
    }
    return needsSignatureAttribute_value;
  }
  /** @apilevel internal */
  private void classSignature_reset() {
    classSignature_computed = null;
    classSignature_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle classSignature_computed = null;

  /** @apilevel internal */
  protected String classSignature_value;

  /**
   * @attribute syn
   * @aspect GenericsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:268
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:268")
  public String classSignature() {
    ASTNode$State state = state();
    if (classSignature_computed == ASTNode$State.NON_CYCLE || classSignature_computed == state().cycle()) {
      return classSignature_value;
    }
    classSignature_value = classSignature_compute();
    if (state().inCircle()) {
      classSignature_computed = state().cycle();
    
    } else {
      classSignature_computed = ASTNode$State.NON_CYCLE;
    
    }
    return classSignature_value;
  }
  /** @apilevel internal */
  private String classSignature_compute() {
      StringBuilder buf = new StringBuilder();
      buf.append("<");
      for (int i = 0; i < getNumTypeParameter(); i++) {
        buf.append(getTypeParameter(i).formalTypeParameter());
      }
      buf.append(">");
      buf.append(super.classSignature());
      return buf.toString();
    }
  /**
   * @attribute syn
   * @aspect Generics
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:243
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Generics", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:243")
  public boolean isGenericType() {
    boolean isGenericType_value = true;
    return isGenericType_value;
  }
  /** @apilevel internal */
  private void lookupParTypeDecl_ParTypeAccess_reset() {
    lookupParTypeDecl_ParTypeAccess_computed = new java.util.HashMap(4);
    lookupParTypeDecl_ParTypeAccess_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map lookupParTypeDecl_ParTypeAccess_values;
  /** @apilevel internal */
  protected java.util.Map lookupParTypeDecl_ParTypeAccess_computed;
  /** Transforms the parameter and calls the lookupParTypeDecl attribute for ArrayList arguments. 
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:945
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:945")
  public TypeDecl lookupParTypeDecl(ParTypeAccess p) {
    Object _parameters = p;
    if (lookupParTypeDecl_ParTypeAccess_computed == null) lookupParTypeDecl_ParTypeAccess_computed = new java.util.HashMap(4);
    if (lookupParTypeDecl_ParTypeAccess_values == null) lookupParTypeDecl_ParTypeAccess_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (lookupParTypeDecl_ParTypeAccess_values.containsKey(_parameters) && lookupParTypeDecl_ParTypeAccess_computed != null
        && lookupParTypeDecl_ParTypeAccess_computed.containsKey(_parameters)
        && (lookupParTypeDecl_ParTypeAccess_computed.get(_parameters) == ASTNode$State.NON_CYCLE || lookupParTypeDecl_ParTypeAccess_computed.get(_parameters) == state().cycle())) {
      return (TypeDecl) lookupParTypeDecl_ParTypeAccess_values.get(_parameters);
    }
    TypeDecl lookupParTypeDecl_ParTypeAccess_value = lookupParTypeDecl_compute(p);
    if (state().inCircle()) {
      lookupParTypeDecl_ParTypeAccess_values.put(_parameters, lookupParTypeDecl_ParTypeAccess_value);
      lookupParTypeDecl_ParTypeAccess_computed.put(_parameters, state().cycle());
    
    } else {
      lookupParTypeDecl_ParTypeAccess_values.put(_parameters, lookupParTypeDecl_ParTypeAccess_value);
      lookupParTypeDecl_ParTypeAccess_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return lookupParTypeDecl_ParTypeAccess_value;
  }
  /** @apilevel internal */
  private TypeDecl lookupParTypeDecl_compute(ParTypeAccess p) {
      Collection<TypeDecl> typeArguments = new ArrayList<TypeDecl>();
      for (Access argument : p.getTypeArgumentList()) {
        typeArguments.add(argument.type());
      }
      return lookupParTypeDecl(typeArguments);
    }
  /**
   * @attribute inh
   * @aspect GenericsTypeCheck
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:684
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="GenericsTypeCheck", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:684")
  public TypeDecl typeThrowable() {
    TypeDecl typeThrowable_value = getParent().Define_typeThrowable(this, null);
    return typeThrowable_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:580
   * @apilevel internal
   */
  public boolean Define_isNestedType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getTypeParameterListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:762
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return true;
    }
    else {
      return super.Define_isNestedType(_callerNode, _childNode);
    }
  }
  protected boolean canDefine_isNestedType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:555
   * @apilevel internal
   */
  public TypeDecl Define_enclosingType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getTypeParameterListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:763
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return this;
    }
    else {
      return super.Define_enclosingType(_callerNode, _childNode);
    }
  }
  protected boolean canDefine_enclosingType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethods.jrag:231
   * @apilevel internal
   */
  public SimpleSet<TypeDecl> Define_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:826
      int index = _callerNode.getIndexOfChild(_childNode);
      {
          SimpleSet<TypeDecl> result = memberTypes(name);
          if (getBodyDecl(index).visibleTypeParameters()) {
            result = addTypeVariables(result, name);
          }
          if (!result.isEmpty()) {
            return result;
          }
          // 8.5.2
          if (isClassDecl() && isStatic() && !isTopLevelType()) {
            for (TypeDecl type : lookupType(name)) {
              if (type.isStatic() || (type.enclosingType() != null && instanceOf(type.enclosingType()))) {
                result = result.add(type);
              }
            }
          } else {
            result = lookupType(name);
          }
          if (!result.isEmpty()) {
            return result;
          }
          return topLevelType().lookupType(name); // Fix to search imports.
          // Include type parameters if not static.
        }
    }
    else if (_callerNode == getTypeParameterListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:804
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      {
          SimpleSet<TypeDecl> result = memberTypes(name);
          result = addTypeVariables(result, name);
          if (!result.isEmpty()) {
            return result;
          }
          // 8.5.2
          if (isClassDecl() && isStatic() && !isTopLevelType()) {
            for (TypeDecl type : lookupType(name)) {
              if (type.isStatic() || (type.enclosingType() != null && instanceOf(type.enclosingType()))) {
                result = result.add(type);
              }
            }
          } else {
            result = lookupType(name);
          }
          if (!result.isEmpty()) {
            return result;
          }
          return topLevelType().lookupType(name); // Fix to search imports.
        }
    }
    else if (_callerNode == getImplementsListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:799
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      {
          SimpleSet<TypeDecl> result = addTypeVariables(ASTNode.<TypeDecl>emptySet(), name);
          return !result.isEmpty() ? result : lookupType(name);
        }
    }
    else if (_callerNode == getSuperClassOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:794
      {
          SimpleSet<TypeDecl> result = addTypeVariables(ASTNode.<TypeDecl>emptySet(), name);
          return !result.isEmpty() ? result : lookupType(name);
        }
    }
    else {
      return super.Define_lookupType(_callerNode, _childNode, name);
    }
  }
  protected boolean canDefine_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsParTypeDecl.jrag:74
   * @apilevel internal
   */
  public TypeDecl Define_genericDecl(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return this;
  }
  protected boolean canDefine_genericDecl(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TypeVariablePositions.jrag:29
   * @apilevel internal
   */
  public int Define_typeVarPosition(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getTypeParameterListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TypeVariablePositions.jrag:43
      int i = _callerNode.getIndexOfChild(_childNode);
      return i;
    }
    else {
      return getParent().Define_typeVarPosition(this, _callerNode);
    }
  }
  protected boolean canDefine_typeVarPosition(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TypeVariablePositions.jrag:32
   * @apilevel internal
   */
  public boolean Define_typeVarInMethod(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getTypeParameterListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/TypeVariablePositions.jrag:44
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return false;
    }
    else {
      return getParent().Define_typeVarInMethod(this, _callerNode);
    }
  }
  protected boolean canDefine_typeVarInMethod(ASTNode _callerNode, ASTNode _childNode) {
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
    // @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:672
    if (instanceOf(typeThrowable())) {
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
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    if (instanceOf(typeThrowable())) {
      collection.add(errorf("generic class %s may not directly or indirectly inherit java.lang.Throwable",
                typeName()));
    }
  }
}
