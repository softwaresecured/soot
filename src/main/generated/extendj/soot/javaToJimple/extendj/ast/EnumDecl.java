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
 * @declaredat /home/olivier/projects/extendj/java5/grammar/Enums.ast:1
 * @astdecl EnumDecl : ClassDecl ::= Modifiers <ID:String> [SuperClass:Access] Implements:Access* BodyDecl*;
 * @production EnumDecl : {@link ClassDecl} ::= <span class="component">{@link Modifiers}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">[SuperClass:{@link Access}]</span> <span class="component">Implements:{@link Access}*</span> <span class="component">{@link BodyDecl}*</span>;

 */
public class EnumDecl extends ClassDecl implements Cloneable {
  /**
   * Not implemented using generated pretty-print code because we have to separate
   * enum constants and enum body declarations.
   * @aspect Enums
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:683
   */
  public void prettyPrint(PrettyPrinter out) {
    if (!docComment.isEmpty()) {
      out.print(docComment);
    }
    out.print(getModifiers());
    out.print("enum " + name());
    if (getNumImplements() > 0) {
      out.print(" implements ");
      out.join(getImplementsList(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(", ");
        }
      });
    }
    out.print(" {");
    out.println();
    out.indent(1);
    out.pushIndentation();
    boolean inConstantList = true;
    for (int i = 0; i < getNumBodyDecl(); i++) {
      BodyDecl d = getBodyDecl(i);
      if (d instanceof EnumConstant) {
        if (i > 0) {
          out.print(",");
          out.println();
        }
      } else if (!d.isSynthetic()) {
        if (inConstantList) {
          out.println();
          out.print(";");
          inConstantList = false;
        }
        out.println();
        out.println();
      }
      out.print(d);
    }
    out.popIndentation();
    out.println();
    out.print("}");
  }
  /**
   * @declaredat ASTNode:1
   */
  public EnumDecl() {
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
    children = new ASTNode[5];
    setChild(new List(), 1);
    setChild(new List(), 2);
    setChild(new Opt(), 3);
    setChild(new Opt(), 4);
  }
  /**
   * @declaredat ASTNode:17
   */
  @ASTNodeAnnotation.Constructor(
    name = {"Modifiers", "ID", "Implements", "BodyDecl"},
    type = {"Modifiers", "String", "List<Access>", "List<BodyDecl>"},
    kind = {"Child", "Token", "List", "List"}
  )
  public EnumDecl(Modifiers p0, String p1, List<Access> p2, List<BodyDecl> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @declaredat ASTNode:28
   */
  public EnumDecl(Modifiers p0, beaver.Symbol p1, List<Access> p2, List<BodyDecl> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:35
   */
  protected int numChildren() {
    return 3;
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
    isStatic_reset();
    getSuperClassOpt_reset();
    getImplicitConstructorOpt_reset();
    localFieldsMap_reset();
    enumConstants_reset();
    methodsNameMap_reset();
    valuesFieldDeclarator_reset();
    implicitValuesField_reset();
    implicitValuesMethod_reset();
    implicitValueOfMethod_reset();
    unimplementedMethods_reset();
    localMethods_reset();
    flags_reset();
    enumConstantField_EnumConstant_reset();
    fieldDeclarations_reset();
    clinitBodies_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:65
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:69
   */
  public EnumDecl clone() throws CloneNotSupportedException {
    EnumDecl node = (EnumDecl) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:74
   */
  public EnumDecl copy() {
    try {
      EnumDecl node = (EnumDecl) clone();
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
   * @declaredat ASTNode:93
   */
  @Deprecated
  public EnumDecl fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:103
   */
  public EnumDecl treeCopyNoTransform() {
    EnumDecl tree = (EnumDecl) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 3:
        case 4:
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
   * @declaredat ASTNode:129
   */
  public EnumDecl treeCopy() {
    EnumDecl tree = (EnumDecl) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 3:
        case 4:
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
   * @declaredat ASTNode:149
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((EnumDecl) node).tokenString_ID);    
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
   * Replaces the Implements list.
   * @param list The new list node to be used as the Implements list.
   * @apilevel high-level
   */
  public void setImplementsList(List<Access> list) {
    setChild(list, 1);
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
    List<Access> list = (List<Access>) getChild(1);
    return list;
  }
  /**
   * Retrieves the Implements list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Implements list.
   * @apilevel low-level
   */
  public List<Access> getImplementsListNoTransform() {
    return (List<Access>) getChildNoTransform(1);
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
    setChild(list, 2);
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
    List<BodyDecl> list = (List<BodyDecl>) getChild(2);
    return list;
  }
  /**
   * Retrieves the BodyDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the BodyDecl list.
   * @apilevel low-level
   */
  public List<BodyDecl> getBodyDeclListNoTransform() {
    return (List<BodyDecl>) getChildNoTransform(2);
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
    return (Opt<ConstructorDecl>) getChildNoTransform(3);
  }
  /**
   * Retrieves the child position of the optional child ImplicitConstructor.
   * @return The the child position of the optional child ImplicitConstructor.
   * @apilevel low-level
   */
  protected int getImplicitConstructorOptChildPosition() {
    return 3;
  }
  /**
   * This method should not be called. This method throws an exception due to
   * the corresponding child being an NTA shadowing a non-NTA child.
   * @param node
   * @apilevel internal
   */
  public void setSuperClassOpt(Opt<Access> node) {
    throw new Error("Can not replace NTA child SuperClassOpt in EnumDecl!");
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
   * Retrieves the optional node for child SuperClass. This is the <code>Opt</code> node containing the child SuperClass, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child SuperClass.
   * @apilevel low-level
   */
  public Opt<Access> getSuperClassOptNoTransform() {
    return (Opt<Access>) getChildNoTransform(4);
  }
  /**
   * Retrieves the child position of the optional child SuperClass.
   * @return The the child position of the optional child SuperClass.
   * @apilevel low-level
   */
  protected int getSuperClassOptChildPosition() {
    return 4;
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:199
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:199")
  public boolean isValidAnnotationMethodReturnType() {
    boolean isValidAnnotationMethodReturnType_value = true;
    return isValidAnnotationMethodReturnType_value;
  }
  /**
   * @attribute syn
   * @aspect Enums
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:38
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="/home/olivier/projects/extendj/java5/frontend/Enums.jrag:38")
  public boolean isEnumDecl() {
    boolean isEnumDecl_value = true;
    return isEnumDecl_value;
  }
  /** @apilevel internal */
  private void isStatic_reset() {
    isStatic_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle isStatic_computed = null;

  /** @apilevel internal */
  protected boolean isStatic_value;

  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:237
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="/home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:237")
  public boolean isStatic() {
    ASTState state = state();
    if (isStatic_computed == ASTState.NON_CYCLE || isStatic_computed == state().cycle()) {
      return isStatic_value;
    }
    isStatic_value = isNestedType();
    if (state().inCircle()) {
      isStatic_computed = state().cycle();
    
    } else {
      isStatic_computed = ASTState.NON_CYCLE;
    
    }
    return isStatic_value;
  }
  /**
   * @attribute syn
   * @aspect TypeHierarchyCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:368
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:368")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>(super.typeProblems());
        for (MethodDecl m : memberMethods("finalize")) {
          if (m.getNumParameter() == 0 && m.hostType() == this) {
            problems.add(error("an enum may not declare a finalizer"));
          }
        }
        return problems;
      }
  }
  /** @apilevel internal */
  private void getSuperClassOpt_reset() {
    getSuperClassOpt_computed = false;
    
    getSuperClassOpt_value = null;
  }
  /** @apilevel internal */
  protected boolean getSuperClassOpt_computed = false;

  /** @apilevel internal */
  protected Opt<Access> getSuperClassOpt_value;

  /**
   * @attribute syn nta
   * @aspect Enums
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:87
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="/home/olivier/projects/extendj/java5/frontend/Enums.jrag:87")
  public Opt<Access> getSuperClassOpt() {
    ASTState state = state();
    if (getSuperClassOpt_computed) {
      return (Opt<Access>) getChild(getSuperClassOptChildPosition());
    }
    state().enterLazyAttribute();
    getSuperClassOpt_value = new Opt<Access>(new ParTypeAccess(
              new TypeAccess("java.lang", "Enum"),
              new List<Access>(createQualifiedAccess())));
    setChild(getSuperClassOpt_value, getSuperClassOptChildPosition());
    getSuperClassOpt_computed = true;
    state().leaveLazyAttribute();
    Opt<Access> node = (Opt<Access>) this.getChild(getSuperClassOptChildPosition());
    return node;
  }
  /** @apilevel internal */
  private void getImplicitConstructorOpt_reset() {
    getImplicitConstructorOpt_computed = false;
    
    getImplicitConstructorOpt_value = null;
  }
  /** @apilevel internal */
  protected boolean getImplicitConstructorOpt_computed = false;

  /** @apilevel internal */
  protected Opt<ConstructorDecl> getImplicitConstructorOpt_value;

  /**
   * Implicit constructor for enum type.
   * @attribute syn nta
   * @aspect Enums
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:104
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="/home/olivier/projects/extendj/java5/frontend/Enums.jrag:104")
  public Opt<ConstructorDecl> getImplicitConstructorOpt() {
    ASTState state = state();
    if (getImplicitConstructorOpt_computed) {
      return (Opt<ConstructorDecl>) getChild(getImplicitConstructorOptChildPosition());
    }
    state().enterLazyAttribute();
    getImplicitConstructorOpt_value = getImplicitConstructorOpt_compute();
    setChild(getImplicitConstructorOpt_value, getImplicitConstructorOptChildPosition());
    getImplicitConstructorOpt_computed = true;
    state().leaveLazyAttribute();
    Opt<ConstructorDecl> node = (Opt<ConstructorDecl>) this.getChild(getImplicitConstructorOptChildPosition());
    return node;
  }
  /** @apilevel internal */
  private Opt<ConstructorDecl> getImplicitConstructorOpt_compute() {
      if (needsImplicitConstructor()) {
        List<ParameterDeclaration> parameters = new List<ParameterDeclaration>(
            new ParameterDeclaration(new TypeAccess("java.lang", "String"), "p0"),
            new ParameterDeclaration(new TypeAccess("int"), "p1"));
        ConstructorDecl constructor = new ConstructorDecl(
            new Modifiers(new List<Modifier>(new Modifier("synthetic"))),
            name(),
            parameters,
            new List<Access>(),
            new Opt<Stmt>(new ExprStmt(
                new SuperConstructorAccess(
                    "super",
                    new List<Expr>(
                        new VarAccess("p0"),
                        new VarAccess("p1"))))),
            new Block());
        return new Opt<ConstructorDecl>(constructor);
      } else {
        return new Opt<ConstructorDecl>();
      }
    }
  /** @apilevel internal */
  private void localFieldsMap_reset() {
    localFieldsMap_computed = null;
    localFieldsMap_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle localFieldsMap_computed = null;

  /** @apilevel internal */
  protected Map<String, SimpleSet<Variable>> localFieldsMap_value;

  /**
   * @attribute syn
   * @aspect Fields
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:421
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Fields", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:421")
  public Map<String, SimpleSet<Variable>> localFieldsMap() {
    ASTState state = state();
    if (localFieldsMap_computed == ASTState.NON_CYCLE || localFieldsMap_computed == state().cycle()) {
      return localFieldsMap_value;
    }
    localFieldsMap_value = localFieldsMap_compute();
    if (state().inCircle()) {
      localFieldsMap_computed = state().cycle();
    
    } else {
      localFieldsMap_computed = ASTState.NON_CYCLE;
    
    }
    return localFieldsMap_value;
  }
  /** @apilevel internal */
  private Map<String, SimpleSet<Variable>> localFieldsMap_compute() {
      Map<String, SimpleSet<Variable>> map = new HashMap<String, SimpleSet<Variable>>(
          super.localFieldsMap());
      for (BodyDecl decl : getBodyDeclList()) {
        if (decl instanceof EnumConstant) {
          EnumConstant constant = (EnumConstant) decl;
          putSimpleSetElement(map, constant.name(), constant);
        }
      }
      putSimpleSetElement(map, "$VALUES", valuesFieldDeclarator());
      return map;
    }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:242
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="/home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:242")
  public boolean isFinal() {
    {
        for (EnumConstant c : enumConstants()) {
          ClassInstanceExpr e = (ClassInstanceExpr) c.getInit();
          if (e.hasTypeDecl()) {
            return false;
          }
        }
        return true;
      }
  }
  /** @apilevel internal */
  private void enumConstants_reset() {
    enumConstants_computed = null;
    enumConstants_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle enumConstants_computed = null;

  /** @apilevel internal */
  protected Collection<EnumConstant> enumConstants_value;

  /**
   * @attribute syn
   * @aspect Enums
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:406
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="/home/olivier/projects/extendj/java5/frontend/Enums.jrag:406")
  public Collection<EnumConstant> enumConstants() {
    ASTState state = state();
    if (enumConstants_computed == ASTState.NON_CYCLE || enumConstants_computed == state().cycle()) {
      return enumConstants_value;
    }
    enumConstants_value = enumConstants_compute();
    if (state().inCircle()) {
      enumConstants_computed = state().cycle();
    
    } else {
      enumConstants_computed = ASTState.NON_CYCLE;
    
    }
    return enumConstants_value;
  }
  /** @apilevel internal */
  private Collection<EnumConstant> enumConstants_compute() {
      Collection<EnumConstant> list = new ArrayList<EnumConstant>();
      for (int i = 0; i < getNumBodyDecl(); i++) {
        if (getBodyDecl(i).isEnumConstant()) {
          list.add((EnumConstant) getBodyDecl(i));
        }
      }
      return list;
    }
  /** @apilevel internal */
  private void methodsNameMap_reset() {
    methodsNameMap_computed = null;
    methodsNameMap_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle methodsNameMap_computed = null;

  /** @apilevel internal */
  protected Map<String, Collection<MethodDecl>> methodsNameMap_value;

  /**
   * Maps method names to method declarations for methods declared
   * in this type and inherited from supertypes.
   * 
   * <p>Each method name can map to multiple declarations.
   * @attribute syn
   * @aspect MemberMethods
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:499
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MemberMethods", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:499")
  public Map<String, Collection<MethodDecl>> methodsNameMap() {
    ASTState state = state();
    if (methodsNameMap_computed == ASTState.NON_CYCLE || methodsNameMap_computed == state().cycle()) {
      return methodsNameMap_value;
    }
    methodsNameMap_value = methodsNameMap_compute();
    if (state().inCircle()) {
      methodsNameMap_computed = state().cycle();
    
    } else {
      methodsNameMap_computed = ASTState.NON_CYCLE;
    
    }
    return methodsNameMap_value;
  }
  /** @apilevel internal */
  private Map<String, Collection<MethodDecl>> methodsNameMap_compute() {
      Map<String, Collection<MethodDecl>> map = new HashMap<String, Collection<MethodDecl>>();
      // Create a deep copy to avoid aliases.
      for (Map.Entry<String, Collection<MethodDecl>> entry : super.methodsNameMap().entrySet()) {
        map.put(entry.getKey(), new ArrayList<MethodDecl>(entry.getValue()));
      }
      if (compilationUnit().fromSource()) {
        // Add implicit method declarations only if needed.
        addMethodToMap(implicitValuesMethod(), map);
        addMethodToMap(implicitValueOfMethod(), map);
      }
      return map;
    }
  /** @apilevel internal */
  private void valuesFieldDeclarator_reset() {
    valuesFieldDeclarator_computed = null;
    valuesFieldDeclarator_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle valuesFieldDeclarator_computed = null;

  /** @apilevel internal */
  protected FieldDeclarator valuesFieldDeclarator_value;

  /**
   * @attribute syn
   * @aspect Enums
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:456
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="/home/olivier/projects/extendj/java5/frontend/Enums.jrag:456")
  public FieldDeclarator valuesFieldDeclarator() {
    ASTState state = state();
    if (valuesFieldDeclarator_computed == ASTState.NON_CYCLE || valuesFieldDeclarator_computed == state().cycle()) {
      return valuesFieldDeclarator_value;
    }
    valuesFieldDeclarator_value = implicitValuesField().getDeclarator(0);
    if (state().inCircle()) {
      valuesFieldDeclarator_computed = state().cycle();
    
    } else {
      valuesFieldDeclarator_computed = ASTState.NON_CYCLE;
    
    }
    return valuesFieldDeclarator_value;
  }
  /** @apilevel internal */
  private void implicitValuesField_reset() {
    implicitValuesField_computed = false;
    
    implicitValuesField_value = null;
  }
  /** @apilevel internal */
  protected boolean implicitValuesField_computed = false;

  /** @apilevel internal */
  protected FieldDecl implicitValuesField_value;

  /**
   * @attribute syn
   * @aspect Enums
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:459
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="/home/olivier/projects/extendj/java5/frontend/Enums.jrag:459")
  public FieldDecl implicitValuesField() {
    ASTState state = state();
    if (implicitValuesField_computed) {
      return implicitValuesField_value;
    }
    state().enterLazyAttribute();
    implicitValuesField_value = implicitValuesField_compute();
    implicitValuesField_value.setParent(this);
    implicitValuesField_computed = true;
    state().leaveLazyAttribute();
    return implicitValuesField_value;
  }
  /** @apilevel internal */
  private FieldDecl implicitValuesField_compute() {
      int numConstants = enumConstants().size();
      List initValues = new List();
      for (EnumConstant c : enumConstants()) {
        initValues.add(c.createBoundAccess());
      }
      FieldDeclarator values = new FieldDeclarator(
          "$VALUES",
          new List<Dims>(),
          new Opt<Expr>(
              new ArrayCreationExpr(
                new ArrayTypeWithSizeAccess(
                  createQualifiedAccess(),
                  Literal.buildIntegerLiteral(enumConstants().size())),
                new Opt(
                  new ArrayInit(
                    initValues)))));
      return new FieldDecl(
          new Modifiers(new List<Modifier>(
              new Modifier("private"),
              new Modifier("static"),
              new Modifier("final"),
              new Modifier("synthetic"))),
          arrayType().createQualifiedAccess(),
          new List<FieldDeclarator>(values));
    }
  /** @apilevel internal */
  private void implicitValuesMethod_reset() {
    implicitValuesMethod_computed = false;
    
    implicitValuesMethod_value = null;
  }
  /** @apilevel internal */
  protected boolean implicitValuesMethod_computed = false;

  /** @apilevel internal */
  protected MethodDecl implicitValuesMethod_value;

  /**
   * @attribute syn
   * @aspect Enums
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:487
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="/home/olivier/projects/extendj/java5/frontend/Enums.jrag:487")
  public MethodDecl implicitValuesMethod() {
    ASTState state = state();
    if (implicitValuesMethod_computed) {
      return implicitValuesMethod_value;
    }
    state().enterLazyAttribute();
    implicitValuesMethod_value = new MethodDecl(
            new Modifiers(new List<Modifier>(
              new Modifier("public"),
              new Modifier("static"))),
            arrayType().createQualifiedAccess(),
            "values",
            new List(),
            new List(),
            new Opt(
              new Block(
                new List().add(
                  new ReturnStmt(
                    new Opt(
                      new CastExpr(
                        arrayType().createQualifiedAccess(),
                        valuesFieldDeclarator().createBoundAccess().qualifiesAccess(
                          new MethodAccess(
                            "clone",
                            new List())))))))));
    implicitValuesMethod_value.setParent(this);
    implicitValuesMethod_computed = true;
    state().leaveLazyAttribute();
    return implicitValuesMethod_value;
  }
  /** @apilevel internal */
  private void implicitValueOfMethod_reset() {
    implicitValueOfMethod_computed = false;
    
    implicitValueOfMethod_value = null;
  }
  /** @apilevel internal */
  protected boolean implicitValueOfMethod_computed = false;

  /** @apilevel internal */
  protected MethodDecl implicitValueOfMethod_value;

  /**
   * @attribute syn
   * @aspect Enums
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:509
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="/home/olivier/projects/extendj/java5/frontend/Enums.jrag:509")
  public MethodDecl implicitValueOfMethod() {
    ASTState state = state();
    if (implicitValueOfMethod_computed) {
      return implicitValueOfMethod_value;
    }
    state().enterLazyAttribute();
    implicitValueOfMethod_value = new MethodDecl(
            new Modifiers(new List<Modifier>(
                new Modifier("public"),
                new Modifier("static"))),
            createQualifiedAccess(),
            "valueOf",
            new List().add(
              new ParameterDeclaration(
                new Modifiers(new List()),
                typeString().createQualifiedAccess(),
                "s")),
            new List(),
            new Opt(
              new Block(
                new List().add(
                  new ReturnStmt(
                    new Opt(
                      new CastExpr(
                        createQualifiedAccess(),
                        lookupType("java.lang", "Enum").createQualifiedAccess().qualifiesAccess(
                          new MethodAccess(
                            "valueOf",
                            new List().add(
                              createQualifiedAccess().qualifiesAccess(new ClassAccess())
                            ).add(
                              new VarAccess(
                                "s")))))))))));
    implicitValueOfMethod_value.setParent(this);
    implicitValueOfMethod_computed = true;
    state().leaveLazyAttribute();
    return implicitValueOfMethod_value;
  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:235
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="/home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:235")
  public boolean isAbstract() {
    {
        for (int i = 0; i < getNumBodyDecl(); i++) {
          if (getBodyDecl(i) instanceof MethodDecl) {
            MethodDecl m = (MethodDecl) getBodyDecl(i);
            if (m.isAbstract()) {
              return true;
            }
          }
        }
        return false;
      }
  }
  /** @apilevel internal */
  private void unimplementedMethods_reset() {
    unimplementedMethods_computed = null;
    unimplementedMethods_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle unimplementedMethods_computed = null;

  /** @apilevel internal */
  protected Collection<MethodDecl> unimplementedMethods_value;

  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:35
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="/home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:35")
  public Collection<MethodDecl> unimplementedMethods() {
    ASTState state = state();
    if (unimplementedMethods_computed == ASTState.NON_CYCLE || unimplementedMethods_computed == state().cycle()) {
      return unimplementedMethods_value;
    }
    unimplementedMethods_value = unimplementedMethods_compute();
    if (state().inCircle()) {
      unimplementedMethods_computed = state().cycle();
    
    } else {
      unimplementedMethods_computed = ASTState.NON_CYCLE;
    
    }
    return unimplementedMethods_value;
  }
  /** @apilevel internal */
  private Collection<MethodDecl> unimplementedMethods_compute() {
      Collection<MethodDecl> methods = new LinkedList<MethodDecl>();
      for (MethodDecl method : interfacesMethods()) {
        SimpleSet<MethodDecl> set = localMethodsSignature(method.signature());
        if (set.isSingleton()) {
          MethodDecl n = set.singletonValue();
          if (!n.isAbstract()) {
            continue;
          }
        }
        boolean implemented = false;
        set = ancestorMethods(method.signature());
        for (MethodDecl n : set) {
          if (!n.isAbstract()) {
            implemented = true;
            break;
          }
        }
        if (!implemented) {
          methods.add(method);
        }
      }
  
      for (MethodDecl method : localMethods()) {
        if (method.isAbstract()) {
          methods.add(method);
        }
      }
  
      Collection<MethodDecl> unimplemented = new ArrayList<MethodDecl>();
      for (MethodDecl method : methods) {
        if (enumConstants().isEmpty()) {
          unimplemented.add(method);
          continue;
        }
        boolean missing = false;
        for (EnumConstant c : enumConstants()) {
          if (!c.implementsMethod(method)) {
            missing = true;
            break;
          }
        }
        if (missing) {
          unimplemented.add(method);
        }
      }
  
      return unimplemented;
    }
  /**
   * Check that the enum does not contain unimplemented abstract methods.
   * @attribute syn
   * @aspect Enums
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:788
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="/home/olivier/projects/extendj/java5/frontend/Enums.jrag:788")
  public Collection<Problem> modifierProblems() {
    {
        if (!unimplementedMethods().isEmpty()) {
          StringBuilder sb = new StringBuilder();
          sb.append("" + name() + " lacks implementations in one or more "
              + "enum constants for the following methods:\n");
          for (MethodDecl m : unimplementedMethods()) {
            sb.append("  " + m.signature() + " in " + m.hostType().typeName() + "\n");
          }
          return Collections.singletonList(error(sb.toString()));
        }
        return Collections.emptyList();
      }
  }
  /** @apilevel internal */
  private void localMethods_reset() {
    localMethods_computed = null;
    localMethods_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle localMethods_computed = null;

  /** @apilevel internal */
  protected java.util.List<MethodDecl> localMethods_value;

  /**
   * All local method declarations in this type.
   * 
   * <p>Does not include supertype methods.
   * @attribute syn
   * @aspect MemberMethods
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:546
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MemberMethods", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:546")
  public java.util.List<MethodDecl> localMethods() {
    ASTState state = state();
    if (localMethods_computed == ASTState.NON_CYCLE || localMethods_computed == state().cycle()) {
      return localMethods_value;
    }
    localMethods_value = localMethods_compute();
    if (state().inCircle()) {
      localMethods_computed = state().cycle();
    
    } else {
      localMethods_computed = ASTState.NON_CYCLE;
    
    }
    return localMethods_value;
  }
  /** @apilevel internal */
  private java.util.List<MethodDecl> localMethods_compute() {
      ArrayList<MethodDecl> methods = new ArrayList<MethodDecl>(super.localMethods());
      if (compilationUnit().fromSource()) {
        // Add implicit method declarations only if needed.
        methods.add(implicitValuesMethod());
        methods.add(implicitValueOfMethod());
      }
      return methods;
    }
  /** @apilevel internal */
  private void flags_reset() {
    flags_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle flags_computed = null;

  /** @apilevel internal */
  protected int flags_value;

  /**
   * @attribute syn
   * @aspect Flags
   * @declaredat /home/olivier/projects/extendj/java4/backend/Flags.jrag:112
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Flags", declaredAt="/home/olivier/projects/extendj/java4/backend/Flags.jrag:112")
  public int flags() {
    ASTState state = state();
    if (flags_computed == ASTState.NON_CYCLE || flags_computed == state().cycle()) {
      return flags_value;
    }
    flags_value = super.flags() | Modifiers.ACC_ENUM;
    if (state().inCircle()) {
      flags_computed = state().cycle();
    
    } else {
      flags_computed = ASTState.NON_CYCLE;
    
    }
    return flags_value;
  }
  /** @return a collection of the methods and constructors declared in this type. 
   * @attribute syn
   * @aspect GenerateClassfile
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenerateClassfile.jrag:143
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenerateClassfile", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenerateClassfile.jrag:143")
  public ArrayList<BodyDecl> methodsAndConstructors() {
    {
        assert compilationUnit().fromSource();
        ArrayList<BodyDecl> methods = new ArrayList<>(super.methodsAndConstructors());
        methods.add(implicitValuesMethod());
        methods.add(implicitValueOfMethod());
        return methods;
      }
  }
  /**
   * @attribute syn
   * @aspect EnumsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EnumsCodegen.jrag:112
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EnumsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/EnumsCodegen.jrag:112")
  public FieldDeclarator enumConstant(EnumConstant c) {
    FieldDeclarator enumConstant_EnumConstant_value = enumConstantField(c).getDeclarator(0);
    return enumConstant_EnumConstant_value;
  }
  /** @apilevel internal */
  private void enumConstantField_EnumConstant_reset() {
    enumConstantField_EnumConstant_values = null;
    enumConstantField_EnumConstant_proxy = null;
  }
  /** @apilevel internal */
  protected ASTNode enumConstantField_EnumConstant_proxy;
  /** @apilevel internal */
  protected java.util.Map enumConstantField_EnumConstant_values;

  /**
   * @attribute syn
   * @aspect EnumsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EnumsCodegen.jrag:115
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="EnumsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/EnumsCodegen.jrag:115")
  public FieldDecl enumConstantField(EnumConstant c) {
    Object _parameters = c;
    if (enumConstantField_EnumConstant_values == null) enumConstantField_EnumConstant_values = new java.util.HashMap(4);
    ASTState state = state();
    if (enumConstantField_EnumConstant_values.containsKey(_parameters)) {
      return (FieldDecl) enumConstantField_EnumConstant_values.get(_parameters);
    }
    state().enterLazyAttribute();
    FieldDecl enumConstantField_EnumConstant_value = enumConstantField_compute(c);
    if (enumConstantField_EnumConstant_proxy == null) {
      enumConstantField_EnumConstant_proxy = new ASTNode();
      enumConstantField_EnumConstant_proxy.setParent(this);
    }
    if (enumConstantField_EnumConstant_value != null) {
      enumConstantField_EnumConstant_value.setParent(enumConstantField_EnumConstant_proxy);
      if (enumConstantField_EnumConstant_value.mayHaveRewrite()) {
        enumConstantField_EnumConstant_value = (FieldDecl) enumConstantField_EnumConstant_value.rewrittenNode();
        enumConstantField_EnumConstant_value.setParent(enumConstantField_EnumConstant_proxy);
      }
    }
    enumConstantField_EnumConstant_values.put(_parameters, enumConstantField_EnumConstant_value);
    state().leaveLazyAttribute();
    return enumConstantField_EnumConstant_value;
  }
  /** @apilevel internal */
  private FieldDecl enumConstantField_compute(EnumConstant c) {
      return new FieldDecl(
          new Modifiers(new List<>(
              new Modifier("public"),
              new Modifier("static"),
              new Modifier("final"),
              new Modifier("synthetic"))),
          createQualifiedAccess(),
          new List<>(
            new FieldDeclarator(
              c.name(),
              new List<Dims>(),
              new Opt<>(c.getInit()))));
    }
  /** @apilevel internal */
  private void fieldDeclarations_reset() {
    fieldDeclarations_computed = null;
    fieldDeclarations_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle fieldDeclarations_computed = null;

  /** @apilevel internal */
  protected java.util.List<FieldDeclarator> fieldDeclarations_value;

  /** @return a collection of the fields declared in this type. 
   * @attribute syn
   * @aspect GenerateClassfile
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenerateClassfile.jrag:39
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenerateClassfile", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenerateClassfile.jrag:39")
  public java.util.List<FieldDeclarator> fieldDeclarations() {
    ASTState state = state();
    if (fieldDeclarations_computed == ASTState.NON_CYCLE || fieldDeclarations_computed == state().cycle()) {
      return fieldDeclarations_value;
    }
    fieldDeclarations_value = fieldDeclarations_compute();
    if (state().inCircle()) {
      fieldDeclarations_computed = state().cycle();
    
    } else {
      fieldDeclarations_computed = ASTState.NON_CYCLE;
    
    }
    return fieldDeclarations_value;
  }
  /** @apilevel internal */
  private java.util.List<FieldDeclarator> fieldDeclarations_compute() {
      assert compilationUnit().fromSource();
      ArrayList<FieldDeclarator> fields = new ArrayList<>();
      // 8.9.3. Enum Members
      // "... These fields are implicitly declared in the same order as the
      //  corresponding enum constants, before any static fields explicitly
      //  declared in the body of the declaration of E. "
      // Just declare them before anything else.
      for (EnumConstant c : enumConstants())
        fields.add(enumConstant(c));
  
      fields.addAll(super.fieldDeclarations());
      fields.add(valuesFieldDeclarator());
  
      return fields;
    }
  /** @apilevel internal */
  private void clinitBodies_reset() {
    clinitBodies_computed = null;
    clinitBodies_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle clinitBodies_computed = null;

  /** @apilevel internal */
  protected ArrayList<BodyDecl> clinitBodies_value;

  /**
   * @attribute syn
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:484
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EmitJimple", declaredAt="/home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:484")
  public ArrayList<BodyDecl> clinitBodies() {
    ASTState state = state();
    if (clinitBodies_computed == ASTState.NON_CYCLE || clinitBodies_computed == state().cycle()) {
      return clinitBodies_value;
    }
    clinitBodies_value = clinitBodies_compute();
    if (state().inCircle()) {
      clinitBodies_computed = state().cycle();
    
    } else {
      clinitBodies_computed = ASTState.NON_CYCLE;
    
    }
    return clinitBodies_value;
  }
  /** @apilevel internal */
  private ArrayList<BodyDecl> clinitBodies_compute() {
      ArrayList<BodyDecl> lst = new ArrayList<>();
      // 8.9.3 - implicit enum fields are declared before any explicitly declared static fields
      for (EnumConstant c : enumConstants())
        lst.add(enumConstantField(c));
  
      lst.addAll(super.clinitBodies());
      return lst;
    }
  /**
   * @attribute inh
   * @aspect Enums
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:538
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="/home/olivier/projects/extendj/java5/frontend/Enums.jrag:538")
  public TypeDecl typeString() {
    TypeDecl typeString_value = getParent().Define_typeString(this, null);
    return typeString_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:435
   * @apilevel internal
   */
  public boolean Define_mayBeAbstract(ASTNode _callerNode, ASTNode _childNode) {
    if (getModifiersNoTransform() != null && _callerNode == getModifiers()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:60
      return false;
    }
    else {
      return super.Define_mayBeAbstract(_callerNode, _childNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:435
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayBeAbstract
   */
  protected boolean canDefine_mayBeAbstract(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:433
   * @apilevel internal
   */
  public boolean Define_mayBeStatic(ASTNode _callerNode, ASTNode _childNode) {
    if (getModifiersNoTransform() != null && _callerNode == getModifiers()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:67
      return isNestedType();
    }
    else {
      return super.Define_mayBeStatic(_callerNode, _childNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:433
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayBeStatic
   */
  protected boolean canDefine_mayBeStatic(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:130
   * @apilevel internal
   */
  public boolean Define_isOriginalEnumConstructor(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getImplicitConstructorOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:134
      return false;
    }
    else if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:132
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return true;
    }
    else {
      return getParent().Define_isOriginalEnumConstructor(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:130
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isOriginalEnumConstructor
   */
  protected boolean canDefine_isOriginalEnumConstructor(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:434
   * @apilevel internal
   */
  public boolean Define_mayBeFinal(ASTNode _callerNode, ASTNode _childNode) {
    if (getModifiersNoTransform() != null && _callerNode == getModifiers()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:404
      return false;
    }
    else {
      return super.Define_mayBeFinal(_callerNode, _childNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:434
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayBeFinal
   */
  protected boolean canDefine_mayBeFinal(ASTNode _callerNode, ASTNode _childNode) {
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
    // @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:783
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
  /** @apilevel internal */
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    for (Problem value : modifierProblems()) {
      collection.add(value);
    }
  }
}
