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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:154
 * @astdecl ClassDecl : ReferenceType ::= Modifiers <ID:String> [SuperClass:Access] Implements:Access* BodyDecl* [ImplicitConstructor:ConstructorDecl];
 * @production ClassDecl : {@link ReferenceType} ::= <span class="component">{@link Modifiers}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">[SuperClass:{@link Access}]</span> <span class="component">Implements:{@link Access}*</span> <span class="component">{@link BodyDecl}*</span> <span class="component">[ImplicitConstructor:{@link ConstructorDecl}]</span>;

 */
public class ClassDecl extends ReferenceType implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:147
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
   * @aspect SuperClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:661
   */
  public boolean hasSuperclass() {
    return !isObject();
  }
  /**
   * @aspect SuperClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:665
   */
  public TypeDecl superclass() {
    if (isObject()) {
      return unknownType();
    } else if (hasSuperClass()) {
      return getSuperClass().type();
    } else {
      return typeObject();
    }
  }
  /**
   * Check compatibility of interface method and superclass method.
   * @param m interface method
   * @param n superclass method
   * @aspect TypeHierarchyCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:481
   */
  private void interfaceMethodCompatibleWithInherited(Collection<Problem> problems,
      MethodDecl m, MethodDecl n) {
    if (n.isAbstract()) {
      checkAbstractMethodDecls(problems, m, n);
    }
    if (n.isStatic()) {
      problems.add(error("Xa static method may not hide an instance method"));
    }
    if (!n.isAbstract() && !n.isPublic()) {
      problems.add(errorf("Xoverriding access modifier error for %s in %s and %s",
          m.fullSignature(), m.hostType().typeName(), n.hostType().typeName()));
    }
    if (!n.mayOverride(m) && !m.mayOverride(m)) {
      problems.add(errorf("Xthe return type of method %s in %s does not match"
          + " the return type of method %s in %s and may thus not be overridden",
          m.fullSignature(), m.hostType().typeName(), n.fullSignature(), n.hostType().typeName()));
    }

    if (!n.isAbstract()) {
      // If n implements and overrides method m in the interface then it
      // may not throw more checked exceptions.
      for (Access e: n.getExceptionList()) {
        if (e.type().isCheckedException()) {
          boolean found = false;
          for (Access declException: m.getExceptionList()) {
            if (e.type().instanceOf(declException.type())) {
              found = true;
              break;
            }
          }
          if (!found) {
            problems.add(errorf(
                "%s in %s may not throw more checked exceptions than overridden method %s in %s",
                n.fullSignature(), n.hostType().typeName(), m.fullSignature(),
                m.hostType().typeName()));
          }
        }
      }
    }
  }
  /**
   * @aspect Generics
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:270
   */
  public TypeDecl makeGeneric(Signatures.ClassSignature s) {
    // NOTE: we are overwriting List- and Opt- children here using setSuperClassOpt
    // and setImplementsList. This is dangerous since those children are in some
    // cases NTAs, and we should not use set methods to try to overwrite NTA values.
    // However, we have to do this here in order to not trigger rewrites that in
    // turn need to access certain (inherited) lookup attributes, and we are reasonably
    // sure that we are in fact not overwriting NTA children. We exclude EnumDecl here
    // because its [SuperClass] and Implements* are in fact NTAs.
    // /Jesper 2015-01-22
    if (this instanceof EnumDecl) {
      return this; // Enum superclass and superinterfaces are NTAs.
    }
    if (s.hasFormalTypeParameters()) {
      ASTNode node = getParent();
      int index = node.getIndexOfChild(this);
      node.setChild(
          new GenericClassDecl(
              getModifiersNoTransform(),
              getID(),
              s.hasSuperclassSignature()
                  ? new Opt(s.superclassSignature())
                  : getSuperClassOptNoTransform(),
              s.hasSuperinterfaceSignature()
                  ? s.superinterfaceSignature()
                  : getImplementsListNoTransform(),
              getBodyDeclListNoTransform(),
              s.typeParameters()),
          index);
      return (TypeDecl) node.getChildNoTransform(index);
    } else {
      if (s.hasSuperclassSignature()) {
        setSuperClassOpt(new Opt(s.superclassSignature()));
      }
      if (s.hasSuperinterfaceSignature()) {
        setImplementsList(s.superinterfaceSignature());
      }
      return this;
    }
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1494
   */
  public ClassDecl signatureCopy() {
    return new ClassDeclSubstituted(
        getModifiers().treeCopyNoTransform(),
        getID(),
        getSuperClassOpt().treeCopyNoTransform(),
        getImplementsList().treeCopyNoTransform(),
        this);
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1584
   */
  public ClassDecl erasedCopy() {
    return new ClassDeclErased(
        getModifiers().treeCopyNoTransform(),
        getID(),
        hasSuperClass() ? new Opt<Access>(getSuperClass().erasedCopy()) : new Opt<Access>(),
        erasedAccessList(getImplementsList()),
        this);
  }
  /**
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:41
   */
  protected SootClass refined_EmitJimple_ClassDecl_mkSootClass() {
    SootClass sc = new SootClass(jvmName(), flags());
    Scene.v().addClass(sc);
    sc.setApplicationClass();

    // The real super-class is set later on in `ReferenceType.jimpleDeclare()`.
    // TODO:  Why not set it immediately to the actual super-class, even if it
    //        isn't fully populated?
    //        This might be related to why soot doesn't require a superclass for
    //        interfaces
    if (!isObject())
      sc.setSuperclass(typeObject().sootClass());

    return sc;
  }
  /**
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:171
   */
  protected void jimpleDeclare_setupSuperclass(SootClass sc) {
    if (hasSuperclass())
      sc.setSuperclass(superclass().sootClass());
  }
  /**
   * @declaredat ASTNode:1
   */
  public ClassDecl() {
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
    setChild(new Opt(), 1);
    setChild(new List(), 2);
    setChild(new List(), 3);
    setChild(new Opt(), 4);
  }
  /**
   * @declaredat ASTNode:17
   */
  @ASTNodeAnnotation.Constructor(
    name = {"Modifiers", "ID", "SuperClass", "Implements", "BodyDecl"},
    type = {"Modifiers", "String", "Opt<Access>", "List<Access>", "List<BodyDecl>"},
    kind = {"Child", "Token", "Opt", "List", "List"}
  )
  public ClassDecl(Modifiers p0, String p1, Opt<Access> p2, List<Access> p3, List<BodyDecl> p4) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
  }
  /**
   * @declaredat ASTNode:29
   */
  public ClassDecl(Modifiers p0, beaver.Symbol p1, Opt<Access> p2, List<Access> p3, List<BodyDecl> p4) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:37
   */
  protected int numChildren() {
    return 4;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:43
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:47
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    constructors_reset();
    getImplicitConstructorOpt_reset();
    methods_reset();
    ancestorMethods_String_reset();
    memberFieldsMap_reset();
    memberFields_String_reset();
    unimplementedMethods_reset();
    hasAbstract_reset();
    castingConversionTo_TypeDecl_reset();
    isString_reset();
    isObject_reset();
    instanceOf_TypeDecl_reset();
    superInterfaces_reset();
    isCircular_reset();
    typeDescriptor_reset();
    iterableElementType_reset();
    erasedAncestorMethodsMap_reset();
    implementedInterfaces_reset();
    hasOverridingMethodInSuper_MethodDecl_reset();
    bridgeCandidates_String_reset();
    needsSignatureAttribute_reset();
    classSignature_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:73
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:77
   */
  public ClassDecl clone() throws CloneNotSupportedException {
    ClassDecl node = (ClassDecl) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:82
   */
  public ClassDecl copy() {
    try {
      ClassDecl node = (ClassDecl) clone();
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
   * @declaredat ASTNode:101
   */
  @Deprecated
  public ClassDecl fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:111
   */
  public ClassDecl treeCopyNoTransform() {
    ClassDecl tree = (ClassDecl) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
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
   * @declaredat ASTNode:136
   */
  public ClassDecl treeCopy() {
    ClassDecl tree = (ClassDecl) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
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
   * @declaredat ASTNode:155
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((ClassDecl) node).tokenString_ID);    
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
    return (Opt<ConstructorDecl>) getChildNoTransform(4);
  }
  /**
   * Retrieves the child position of the optional child ImplicitConstructor.
   * @return The the child position of the optional child ImplicitConstructor.
   * @apilevel low-level
   */
  protected int getImplicitConstructorOptChildPosition() {
    return 4;
  }
  /**
   * @aspect EmitJimpleRefinements
   * @declaredat /home/olivier/projects/extendj/soot8/backend/EmitJimpleRefinements.jrag:20
   */
   
  protected SootClass refined_EmitJimpleRefinements_ClassDecl_mkSootClass() {
    if (Scene.v().containsClass(jvmName())) {
      SootClass   sc              = Scene.v().getSootClass(jvmName());
      //fix for test case 653: if there's a class java.lang.Object etc. on the command line
      //prefer that class over the Coffi class that may already have been loaded from bytecode
      SootMethod  m               = sc.getMethodByNameUnsafe("<clinit>");
      boolean     coffiMethodSrc  = m != null && m.getSource() instanceof CoffiMethodSource;
      if (!coffiMethodSrc) return sc;
    }

    return refined_EmitJimple_ClassDecl_mkSootClass();
  }
  /**
   * @aspect IncrementalJimple
   * @declaredat /home/olivier/projects/extendj/soot8/backend/IncrementalJimple.jrag:12
   */
   
    protected SootClass mkSootClass() {
        if (Scene.v().isIncrementalBuild() && Scene.v().containsClass(jvmName()))
            Scene.v().removeClass(Scene.v().getSootClass(jvmName()));

        return refined_EmitJimpleRefinements_ClassDecl_mkSootClass();
    }
  /**
   * @aspect ConstructorLookup
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:149
   */
  private Collection<ConstructorDecl> refined_ConstructorLookup_ClassDecl_constructors()
{
    Collection<ConstructorDecl> c = new ArrayList<ConstructorDecl>(super.constructors());
    if (hasImplicitConstructor()) {
      c.add(getImplicitConstructor());
    }
    return c;
  }
  /**
   * @aspect TypeConversion
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:103
   */
  private boolean refined_TypeConversion_ClassDecl_castingConversionTo_TypeDecl(TypeDecl type)
{
    if (type.isArrayDecl()) {
      return isObject();
    } else if (type.isClassDecl()) {
      return this == type || instanceOf(type) || type.instanceOf(this);
    } else if (type.isInterfaceDecl()) {
      return !isFinal() || instanceOf(type);
    } else return super.castingConversionTo(type);
  }
  /**
   * @aspect TypeHierarchyCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:368
   */
  private Collection<Problem> refined_TypeHierarchyCheck_ClassDecl_typeProblems()
{
    Collection<Problem> problems = new LinkedList<Problem>(super.typeProblems());

    // Check if methods inherited from interfaces are compatible with the
    // overriding method in this class or method inherited from ancestor class.
    for (MethodDecl decl : interfacesMethods()) {
      // First we check locally declared methods, then if no local method
      // overrides the interface method we check ancestor methods.  We check
      // ancestor types in superclass order and stop at the first overriding method.
      boolean overridden = false;
      ClassDecl hostType = this;
      while (!overridden) {
        for (MethodDecl m : hostType.localMethodsSignature(decl.signature())) {
          if (m.overrideCandidate(decl)) {
            overridden = true;
            if (!m.mayOverride(decl)) {
              problems.add(errorf(
                  "the return type of method %s in %s does not match the return type of"
                  + " method %s in %s and may thus not be overridden",
                  m.fullSignature(), hostType.typeName(), decl.fullSignature(),
                  decl.hostType().typeName()));
            }
          }
        }
        if (!hostType.hasSuperclass()) {
          break;
        }
        hostType = (ClassDecl) hostType.superclass();
      }
    }
    return problems;
  }
  /**
   * @aspect TypeHierarchyCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:403
   */
  private Collection<Problem> refined_TypeHierarchyCheck_ClassDecl_nameProblems()
{
    Collection<Problem> problems = new LinkedList<Problem>(super.nameProblems());
    if (hasSuperClass() && !getSuperClass().type().isClassDecl()) {
      problems.add(errorf("a class may only inherit another class, which %s is not",
          getSuperClass().type().typeName()));
    }
    if (isObject() && hasSuperClass()) {
      problems.add(error("class Object may not have a superclass"));
    }
    if (isObject() && getNumImplements() != 0) {
      problems.add(error("class Object may not implement an interface"));
    }

    // 8.1.3
    if (isCircular()) {
      problems.add(errorf("circular inheritance dependency in %s", typeName()));
    }

    // 8.1.4
    Collection<TypeDecl> set = new HashSet<TypeDecl>();
    for (int i = 0; i < getNumImplements(); i++) {
      TypeDecl decl = getImplements(i).type();
      if (!decl.isInterfaceDecl() && !decl.isUnknown()) {
        problems.add(errorf("type %s can not implement the non-interface type %s",
            fullName(), decl.fullName()));
      }
      if (set.contains(decl)) {
        problems.add(errorf("type %s is mentionened multiple times in implements clause",
            decl.fullName()));
      }
      set.add(decl);
    }

    for (MethodDecl m : interfacesMethods()) {
      if (localMethodsSignature(m.signature()).isEmpty()) {
        SimpleSet<MethodDecl> s = superclass().methodsSignature(m.signature());
        for (MethodDecl n : s) {
          if (n.accessibleFrom(this)) {
            interfaceMethodCompatibleWithInherited(problems, m, n);
          }
        }
        if (s.isEmpty()) {
          for (MethodDecl n : interfacesMethodsSignature(m.signature())) {
            // TODO(joqvist): don't report this error twice.
            checkAbstractMethodDecls(problems, m, n);
          }
        }
      }
    }

    // Check if there is a matching accessible super constructor for the default constructor.
    if (hasImplicitConstructor()) {
      Stmt superCall = getImplicitConstructor().getParsedConstructorInvocation();
      SuperConstructorAccess superAccess =
          (SuperConstructorAccess) ((ExprStmt) superCall).getExpr();
      if (superAccess.decls().isEmpty()) {
        problems.add(errorf(
            "can not generate default constructor for class %s because "
            + "superclass %s has no accessible zero-argument constructor",
            name(), superclass().name()));
      } else if (!isAnonymous() && superclass().isInnerType()
          && !hasEnclosingType(superclass().enclosingType())) {
        problems.add(errorf(
            "can not generate default constructor for class %s because its "
            + "superclass %s is an inner type of %s but %s is not an inner type of %s or a "
            + "subtype thereof",
            name(), superclass().typeName(), superclass().enclosingType().name(),
            name(), superclass().enclosingType().name()));
      }
    }
    return problems;
  }
  /**
   * @aspect Generics
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:120
   */
  private boolean refined_Generics_ClassDecl_castingConversionTo_TypeDecl(TypeDecl type)
{
    TypeDecl S = this;
    TypeDecl T = type;
    if (T instanceof TypeVariable) {
      TypeVariable t = (TypeVariable) T;
      if (t.getNumTypeBound() == 0) {
        return true;
      }
      for (int i = 0; i < t.getNumTypeBound(); i++) {
        if (castingConversionTo(t.getTypeBound(i).type())) {
          return true;
        }
      }
      return false;
    }
    if (S.erasure() != S || T.erasure() != T) {
      return S.erasure().castingConversionTo(T.erasure());
    }
    return refined_TypeConversion_ClassDecl_castingConversionTo_TypeDecl(type);
  }
  /**
   * @attribute syn
   * @aspect AccessControl
   * @declaredat /home/olivier/projects/extendj/java4/frontend/AccessControl.jrag:186
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessControl", declaredAt="/home/olivier/projects/extendj/java4/frontend/AccessControl.jrag:186")
  public Collection<Problem> accessControlProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
    
        // 8.1.1.2 final Classes
        TypeDecl typeDecl = superclass();
        if (!typeDecl.isUnknown() && !typeDecl.accessibleFromExtend(this)) {
          problems.add(errorf("class %s may not extend non accessible type %s",
              fullName(), typeDecl.fullName()));
        }
    
        if (hasSuperclass() && !superclass().accessibleFrom(this)) {
          problems.add(errorf("a superclass must be accessible which %s is not",
              superclass().name()));
        }
    
        // 8.1.4
        for (int i = 0; i < getNumImplements(); i++) {
          TypeDecl decl = getImplements(i).type();
          if (!decl.isCircular() && !decl.accessibleFrom(this)) {
            problems.add(errorf("class %s can not implement non accessible type %s",
                fullName(), decl.fullName()));
          }
        }
        return problems;
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:95
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:95")
  public Constant cast(Constant c) {
    Constant cast_Constant_value = Constant.create(c.stringValue());
    return cast_Constant_value;
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:195
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:195")
  public Constant add(Constant c1, Constant c2) {
    Constant add_Constant_Constant_value = Constant.create(c1.stringValue() + c2.stringValue());
    return add_Constant_Constant_value;
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:299
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:299")
  public Constant questionColon(Constant cond, Constant c1, Constant c2) {
    Constant questionColon_Constant_Constant_Constant_value = Constant.create(cond.booleanValue() ? c1.stringValue() : c2.stringValue());
    return questionColon_Constant_Constant_Constant_value;
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:499
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:499")
  public boolean eqIsTrue(Expr left, Expr right) {
    boolean eqIsTrue_Expr_Expr_value = isString() && left.constant().stringValue().equals(right.constant().stringValue());
    return eqIsTrue_Expr_Expr_value;
  }
  /**
   * @attribute syn
   * @aspect ErrorCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ErrorCheck.jrag:46
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ErrorCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/ErrorCheck.jrag:46")
  public int lineNumber() {
    int lineNumber_value = getLine(IDstart);
    return lineNumber_value;
  }
  /**
   * Computes compile errors for each checked exception thrown by the default
   * constructor of this class.
   * @attribute syn
   * @aspect ExceptionHandling
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:388
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="/home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:388")
  public Collection<Problem> exceptionHandlingProblems() {
    {
        if (!hasImplicitConstructor() || isAnonymous()) {
          // If this class is anonymous, then exceptions are checked by the code
          // instantiating the anonymous class.
          return Collections.emptyList();
        }
        Collection<Problem> problems = new LinkedList<Problem>();
        Stmt superCall = getImplicitConstructor().getParsedConstructorInvocation();
        SuperConstructorAccess superAccess = (SuperConstructorAccess) ((ExprStmt) superCall).getExpr();
        for (Access exception : superAccess.decl().getExceptionList()) {
          if (exception.type().isCheckedException()) {
            problems.add(errorf(
                "default constructor for class %s throws unchecked exception %s via "
                + "superclass constructor", name(), exception.type().fullName()));
          }
        }
        return problems;
      }
  }
  /**
   * @attribute syn
   * @aspect ConstructScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:47
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructScope", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:47")
  public Collection<ConstructorDecl> lookupSuperConstructor() {
    Collection<ConstructorDecl> lookupSuperConstructor_value = hasSuperclass() ? superclass().constructors() : Collections.<ConstructorDecl>emptyList();
    return lookupSuperConstructor_value;
  }
  /** @apilevel internal */
  private void constructors_reset() {
    constructors_computed = null;
    constructors_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle constructors_computed = null;

  /** @apilevel internal */
  protected Collection<ConstructorDecl> constructors_value;

  /**
   * @attribute syn
   * @aspect ConstructorLookup
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:139
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructorLookup", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:139")
  public Collection<ConstructorDecl> constructors() {
    ASTState state = state();
    if (constructors_computed == ASTState.NON_CYCLE || constructors_computed == state().cycle()) {
      return constructors_value;
    }
    constructors_value = constructors_compute();
    if (state().inCircle()) {
      constructors_computed = state().cycle();
    
    } else {
      constructors_computed = ASTState.NON_CYCLE;
    
    }
    return constructors_value;
  }
  /** @apilevel internal */
  private Collection<ConstructorDecl> constructors_compute() {
      Collection<ConstructorDecl> constructors = new ArrayList<ConstructorDecl>(refined_ConstructorLookup_ClassDecl_constructors());
      Collection<ConstructorDecl> transformed = new ArrayList<ConstructorDecl>(constructors);
      for (ConstructorDecl decl : constructors) {
        if (decl.transformed() != decl) {
          transformed.add(decl.transformed());
        }
      }
      return transformed;
    }
  /**
   * A class declaration requires an implicit constructor if it has no
   * explicit constructor.
   * @return <code>true</code> if this class requires an implicit default
   * contstructor.
   * @attribute syn
   * @aspect ImplicitConstructor
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:225
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ImplicitConstructor", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:225")
  public boolean needsImplicitConstructor() {
    boolean needsImplicitConstructor_value = compilationUnit().fromSource() && !hasExplicitConstructor();
    return needsImplicitConstructor_value;
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
   * @attribute syn nta
   * @aspect ImplicitConstructor
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:248
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="ImplicitConstructor", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:248")
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
        Modifiers modifiers = new Modifiers();
        if (isPublic()) {
          modifiers.addModifier(new Modifier("public"));
        } else if (isProtected()) {
          modifiers.addModifier(new Modifier("protected"));
        } else if (isPrivate()) {
          modifiers.addModifier(new Modifier("private"));
        }
        ConstructorDecl constructor = new ConstructorDecl(
            modifiers,
            name(),
            new List(),
            new List(),
            new Opt(),
            new Block());
        constructor.setParsedConstructorInvocation(new ExprStmt(
            new SuperConstructorAccess("super", new List())));
        constructor.setImplicitConstructor();
        return new Opt<ConstructorDecl>(constructor);
      } else {
        return new Opt<ConstructorDecl>();
      }
    }
  /**
   * @attribute syn
   * @aspect ImplicitConstructor
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:332
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ImplicitConstructor", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:332")
  public boolean hasExplicitConstructor() {
    {
        for (int i = 0; i < getNumBodyDecl(); i++) {
          if (getBodyDecl(i) instanceof ConstructorDecl) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * Find method declarations inherited from superinterfaces with the given
   * signature.
   * The result can be multiple method declarations.
   * @attribute syn
   * @aspect MemberMethods
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:581
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MemberMethods", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:581")
  public SimpleSet<MethodDecl> interfacesMethodsSignature(String signature) {
    {
        SimpleSet<MethodDecl> result = interfacesMethodsSignatureMap().get(signature);
        if (result != null) {
          return result;
        } else {
          return emptySet();
        }
      }
  }
  /** @apilevel internal */
  private void methods_reset() {
    methods_computed = null;
    methods_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle methods_computed = null;

  /** @apilevel internal */
  protected java.util.List<MethodDecl> methods_value;

  /**
   * Finds all visible methods for this type (includes inherited methods).
   * 
   * <p>Shadowed declarations are not included in the result.
   * @attribute syn
   * @aspect MemberMethods
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:636
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MemberMethods", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:636")
  public java.util.List<MethodDecl> methods() {
    ASTState state = state();
    if (methods_computed == ASTState.NON_CYCLE || methods_computed == state().cycle()) {
      return methods_value;
    }
    methods_value = methods_compute();
    if (state().inCircle()) {
      methods_computed = state().cycle();
    
    } else {
      methods_computed = ASTState.NON_CYCLE;
    
    }
    return methods_value;
  }
  /** @apilevel internal */
  private java.util.List<MethodDecl> methods_compute() {
      Map<String, SimpleSet<MethodDecl>> localMap = localMethodsSignatureMap();
      ArrayList<MethodDecl> methods = new ArrayList<MethodDecl>(localMethods());
      Set<String> fromSuperClass = new HashSet<String>();
      if (hasSuperclass()) {
        for (MethodDecl m : superclass().methods()) {
          if (!m.isPrivate()
              && m.accessibleFrom(this)
              && !localMap.containsKey(m.signature())) {
            methods.add(m);
            if (!m.isAbstract()) {
              fromSuperClass.add(m.signature());
            }
          }
        }
      }
      for (MethodDecl m : interfacesMethods()) {
        if (!m.isStatic()
            && m.accessibleFrom(this)
            && !localMap.containsKey(m.signature())
            && !hasOverridingMethodInSuper(m)
            && !fromSuperClass.contains(m.signature())) {
          methods.add(m);
        }
      }
      return methods;
    }
  /** @apilevel internal */
  private void ancestorMethods_String_reset() {
    ancestorMethods_String_computed = null;
    ancestorMethods_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map ancestorMethods_String_values;
  /** @apilevel internal */
  protected java.util.Map ancestorMethods_String_computed;
  /**
   * Finds methods with the same signature declared in ancestors types.  This
   * is used when checking correct overriding, hiding, and implementation of
   * abstract methods.
   * @attribute syn
   * @aspect AncestorMethods
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:743
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AncestorMethods", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:743")
  public SimpleSet<MethodDecl> ancestorMethods(String signature) {
    Object _parameters = signature;
    if (ancestorMethods_String_computed == null) ancestorMethods_String_computed = new java.util.HashMap(4);
    if (ancestorMethods_String_values == null) ancestorMethods_String_values = new java.util.HashMap(4);
    ASTState state = state();
    if (ancestorMethods_String_values.containsKey(_parameters)
        && ancestorMethods_String_computed.containsKey(_parameters)
        && (ancestorMethods_String_computed.get(_parameters) == ASTState.NON_CYCLE || ancestorMethods_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<MethodDecl>) ancestorMethods_String_values.get(_parameters);
    }
    SimpleSet<MethodDecl> ancestorMethods_String_value = ancestorMethods_compute(signature);
    if (state().inCircle()) {
      ancestorMethods_String_values.put(_parameters, ancestorMethods_String_value);
      ancestorMethods_String_computed.put(_parameters, state().cycle());
    
    } else {
      ancestorMethods_String_values.put(_parameters, ancestorMethods_String_value);
      ancestorMethods_String_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return ancestorMethods_String_value;
  }
  /** @apilevel internal */
  private SimpleSet<MethodDecl> ancestorMethods_compute(String signature) {
      SimpleSet<MethodDecl> result = emptySet();
      if (hasSuperclass()) {
        for (MethodDecl method : superclass().localMethodsSignature(signature)) {
          if (!method.isPrivate()) {
            result = result.add(method);
          }
        }
      }
      // Always add interface methods to the ancestorMethods set so that their
      // access modifiers are checked against local overriding methods.
      for (MethodDecl method : interfacesMethodsSignature(signature)) {
        result = result.add(method);
      }
      if (!hasSuperclass()) {
        return result;
      }
      if (result.isSingleton()) {
        MethodDecl m = result.singletonValue();
        if (!m.isAbstract()) {
          boolean done = true;
          for (MethodDecl n : superclass().ancestorMethods(signature)) {
            if (n.isPrivate() || !n.accessibleFrom(m.hostType())) {
              done = false;
            }
          }
          if (done) {
            return result;
          }
        }
      }
      for (MethodDecl m : superclass().ancestorMethods(signature)) {
        result = result.add(m);
      }
      return result;
    }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:678
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:678")
  public SimpleSet<TypeDecl> memberTypes(String name) {
    {
        SimpleSet<TypeDecl> result = localTypeDecls(name);
        if (!result.isEmpty()) {
          return result;
        }
        for (InterfaceDecl iface : superInterfaces()) {
          for (TypeDecl decl : iface.memberTypes(name)) {
            if (!decl.isPrivate() && decl.accessibleFrom(this)) {
              result = result.add(decl);
            }
          }
        }
        if (hasSuperclass()) {
          for (TypeDecl decl : superclass().memberTypes(name)) {
            if (!decl.isPrivate() && decl.accessibleFrom(this)) {
              result = result.add(decl);
            }
          }
        }
        return result;
      }
  }
  /** @apilevel internal */
  private void memberFieldsMap_reset() {
    memberFieldsMap_computed = null;
    memberFieldsMap_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle memberFieldsMap_computed = null;

  /** @apilevel internal */
  protected Map<String, SimpleSet<Variable>> memberFieldsMap_value;

  /**
   * @attribute syn
   * @aspect Fields
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:387
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Fields", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:387")
  public Map<String, SimpleSet<Variable>> memberFieldsMap() {
    ASTState state = state();
    if (memberFieldsMap_computed == ASTState.NON_CYCLE || memberFieldsMap_computed == state().cycle()) {
      return memberFieldsMap_value;
    }
    memberFieldsMap_value = memberFieldsMap_compute();
    if (state().inCircle()) {
      memberFieldsMap_computed = state().cycle();
    
    } else {
      memberFieldsMap_computed = ASTState.NON_CYCLE;
    
    }
    return memberFieldsMap_value;
  }
  /** @apilevel internal */
  private Map<String, SimpleSet<Variable>> memberFieldsMap_compute() {
      Map<String, SimpleSet<Variable>> map =
          new HashMap<String, SimpleSet<Variable>>(localFieldsMap());
      if (hasSuperclass()) {
        Iterator<Variable> iter = superclass().fieldsIterator();
        while (iter.hasNext()) {
          Variable decl = iter.next();
          if (!decl.isPrivate() && decl.accessibleFrom(this)
              && !localFieldsMap().containsKey(decl.name())) {
            putSimpleSetElement(map, decl.name(), decl);
          }
        }
      }
      for (InterfaceDecl iface : superInterfaces()) {
        Iterator<Variable> iter = iface.fieldsIterator();
        while (iter.hasNext()) {
          Variable decl = iter.next();
          if (!decl.isPrivate() && decl.accessibleFrom(this)
              && !localFieldsMap().containsKey(decl.name())) {
            putSimpleSetElement(map, decl.name(), decl);
          }
        }
      }
      return map;
    }
  /** @apilevel internal */
  private void memberFields_String_reset() {
    memberFields_String_computed = null;
    memberFields_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map memberFields_String_values;
  /** @apilevel internal */
  protected java.util.Map memberFields_String_computed;
  /**
   * @attribute syn
   * @aspect Fields
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:456
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Fields", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:456")
  public SimpleSet<Variable> memberFields(String name) {
    Object _parameters = name;
    if (memberFields_String_computed == null) memberFields_String_computed = new java.util.HashMap(4);
    if (memberFields_String_values == null) memberFields_String_values = new java.util.HashMap(4);
    ASTState state = state();
    if (memberFields_String_values.containsKey(_parameters)
        && memberFields_String_computed.containsKey(_parameters)
        && (memberFields_String_computed.get(_parameters) == ASTState.NON_CYCLE || memberFields_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<Variable>) memberFields_String_values.get(_parameters);
    }
    SimpleSet<Variable> memberFields_String_value = memberFields_compute(name);
    if (state().inCircle()) {
      memberFields_String_values.put(_parameters, memberFields_String_value);
      memberFields_String_computed.put(_parameters, state().cycle());
    
    } else {
      memberFields_String_values.put(_parameters, memberFields_String_value);
      memberFields_String_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return memberFields_String_value;
  }
  /** @apilevel internal */
  private SimpleSet<Variable> memberFields_compute(String name) {
      SimpleSet<Variable> fields = localFields(name);
      if (!fields.isEmpty()) {
        return fields; // This causes hiding of fields in superclass and interfaces.
      }
      if (hasSuperclass()) {
        Iterator<Variable> iter = superclass().memberFields(name).iterator();
        while (iter.hasNext()) {
          Variable decl = iter.next();
          if (!decl.isPrivate() && decl.accessibleFrom(this)) {
            fields = fields.add(decl);
          }
        }
      }
      for (InterfaceDecl iface : superInterfaces()) {
        Iterator<Variable> iter = iface.memberFields(name).iterator();
        while (iter.hasNext()) {
          Variable decl = iter.next();
          if (!decl.isPrivate() && decl.accessibleFrom(this)) {
            fields = fields.add(decl);
          }
        }
      }
      return fields;
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
      Collection<MethodDecl> methods = new ArrayList<MethodDecl>();
      for (MethodDecl m : interfacesMethods()) {
        boolean implemented = false;
        SimpleSet<MethodDecl> result = localMethodsSignature(m.signature());
        if (result.isSingleton()) {
          MethodDecl n = result.singletonValue();
          if (!n.isAbstract()) {
            implemented = true;
          }
        }
        if (!implemented) {
          result = ancestorMethods(m.signature());
          for (MethodDecl n : result) {
            if (!n.isAbstract() && n.isPublic()) {
              implemented = true;
              break;
            }
          }
        }
        if (!implemented) {
          methods.add(m);
        }
      }
  
      if (hasSuperclass()) {
        for (MethodDecl m : superclass().unimplementedMethods()) {
          SimpleSet<MethodDecl> result = localMethodsSignature(m.signature());
          if (result.isSingleton()) {
            MethodDecl n = result.singletonValue();
            if (n.isAbstract() || !n.overrides(m)) {
              methods.add(m);
            }
          } else {
            methods.add(m);
          }
        }
      }
  
      for (MethodDecl m : localMethods()) {
        if (m.isAbstract()) {
          methods.add(m);
        }
      }
      return methods;
    }
  /** @apilevel internal */
  private void hasAbstract_reset() {
    hasAbstract_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle hasAbstract_computed = null;

  /** @apilevel internal */
  protected boolean hasAbstract_value;

  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:33
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="/home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:33")
  public boolean hasAbstract() {
    ASTState state = state();
    if (hasAbstract_computed == ASTState.NON_CYCLE || hasAbstract_computed == state().cycle()) {
      return hasAbstract_value;
    }
    hasAbstract_value = !unimplementedMethods().isEmpty();
    if (state().inCircle()) {
      hasAbstract_computed = state().cycle();
    
    } else {
      hasAbstract_computed = ASTState.NON_CYCLE;
    
    }
    return hasAbstract_value;
  }
  /**
   * @attribute syn
   * @aspect PrettyPrintUtil
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:312
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PrettyPrintUtil", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:312")
  public boolean hasModifiers() {
    boolean hasModifiers_value = getModifiers().getNumModifier() > 0;
    return hasModifiers_value;
  }
  /** @apilevel internal */
  private void castingConversionTo_TypeDecl_reset() {
    castingConversionTo_TypeDecl_computed = null;
    castingConversionTo_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map castingConversionTo_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map castingConversionTo_TypeDecl_computed;
  /**
   * @attribute syn
   * @aspect TypeConversion
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:100
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeConversion", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:100")
  public boolean castingConversionTo(TypeDecl type) {
    Object _parameters = type;
    if (castingConversionTo_TypeDecl_computed == null) castingConversionTo_TypeDecl_computed = new java.util.HashMap(4);
    if (castingConversionTo_TypeDecl_values == null) castingConversionTo_TypeDecl_values = new java.util.HashMap(4);
    ASTState state = state();
    if (castingConversionTo_TypeDecl_values.containsKey(_parameters)
        && castingConversionTo_TypeDecl_computed.containsKey(_parameters)
        && (castingConversionTo_TypeDecl_computed.get(_parameters) == ASTState.NON_CYCLE || castingConversionTo_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) castingConversionTo_TypeDecl_values.get(_parameters);
    }
    boolean castingConversionTo_TypeDecl_value = castingConversionTo_compute(type);
    if (state().inCircle()) {
      castingConversionTo_TypeDecl_values.put(_parameters, castingConversionTo_TypeDecl_value);
      castingConversionTo_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      castingConversionTo_TypeDecl_values.put(_parameters, castingConversionTo_TypeDecl_value);
      castingConversionTo_TypeDecl_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return castingConversionTo_TypeDecl_value;
  }
  /** @apilevel internal */
  private boolean castingConversionTo_compute(TypeDecl type) {
      if (refined_Generics_ClassDecl_castingConversionTo_TypeDecl(type)) {
        return true;
      }
      boolean canUnboxThis = !unboxed().isUnknown();
      boolean canUnboxType = !type.unboxed().isUnknown();
      if (canUnboxThis && !canUnboxType) {
        return unboxed().wideningConversionTo(type);
      }
      return false;
    }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:222
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:222")
  public boolean isClassDecl() {
    boolean isClassDecl_value = true;
    return isClassDecl_value;
  }
  /** @apilevel internal */
  private void isString_reset() {
    isString_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle isString_computed = null;

  /** @apilevel internal */
  protected boolean isString_value;

  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:239
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:239")
  public boolean isString() {
    ASTState state = state();
    if (isString_computed == ASTState.NON_CYCLE || isString_computed == state().cycle()) {
      return isString_value;
    }
    isString_value = fullName().equals("java.lang.String");
    if (state().inCircle()) {
      isString_computed = state().cycle();
    
    } else {
      isString_computed = ASTState.NON_CYCLE;
    
    }
    return isString_value;
  }
  /** @apilevel internal */
  private void isObject_reset() {
    isObject_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle isObject_computed = null;

  /** @apilevel internal */
  protected boolean isObject_value;

  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:242
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:242")
  public boolean isObject() {
    ASTState state = state();
    if (isObject_computed == ASTState.NON_CYCLE || isObject_computed == state().cycle()) {
      return isObject_value;
    }
    isObject_value = name().equals("Object") && packageName().equals("java.lang");
    if (state().inCircle()) {
      isObject_computed = state().cycle();
    
    } else {
      isObject_computed = ASTState.NON_CYCLE;
    
    }
    return isObject_value;
  }
  /** @apilevel internal */
  private void instanceOf_TypeDecl_reset() {
    instanceOf_TypeDecl_computed = null;
    instanceOf_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map instanceOf_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map instanceOf_TypeDecl_computed;
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:442
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeWideningAndIdentity", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:442")
  public boolean instanceOf(TypeDecl type) {
    Object _parameters = type;
    if (instanceOf_TypeDecl_computed == null) instanceOf_TypeDecl_computed = new java.util.HashMap(4);
    if (instanceOf_TypeDecl_values == null) instanceOf_TypeDecl_values = new java.util.HashMap(4);
    ASTState state = state();
    if (instanceOf_TypeDecl_values.containsKey(_parameters)
        && instanceOf_TypeDecl_computed.containsKey(_parameters)
        && (instanceOf_TypeDecl_computed.get(_parameters) == ASTState.NON_CYCLE || instanceOf_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) instanceOf_TypeDecl_values.get(_parameters);
    }
    boolean instanceOf_TypeDecl_value = instanceOf_compute(type);
    if (state().inCircle()) {
      instanceOf_TypeDecl_values.put(_parameters, instanceOf_TypeDecl_value);
      instanceOf_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      instanceOf_TypeDecl_values.put(_parameters, instanceOf_TypeDecl_value);
      instanceOf_TypeDecl_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return instanceOf_TypeDecl_value;
  }
  /** @apilevel internal */
  private boolean instanceOf_compute(TypeDecl type) {
      return subtype(type);
    }
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:458
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeWideningAndIdentity", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:458")
  public boolean isSupertypeOfClassDecl(ClassDecl type) {
    {
        if (super.isSupertypeOfClassDecl(type)) {
          return true;
        }
        return type.hasSuperclass() && type.superclass().instanceOf(this);
      }
  }
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:477
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeWideningAndIdentity", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:477")
  public boolean isSupertypeOfInterfaceDecl(InterfaceDecl type) {
    boolean isSupertypeOfInterfaceDecl_InterfaceDecl_value = isObject();
    return isSupertypeOfInterfaceDecl_InterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:491
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeWideningAndIdentity", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:491")
  public boolean isSupertypeOfArrayDecl(ArrayDecl type) {
    {
        if (super.isSupertypeOfArrayDecl(type)) {
          return true;
        }
        return type.hasSuperclass() && type.superclass().instanceOf(this);
      }
  }
  /**
   * @attribute syn
   * @aspect NestedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:592
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:592")
  public boolean isInnerClass() {
    boolean isInnerClass_value = isNestedType() && !isStatic() && enclosingType().isClassDecl();
    return isInnerClass_value;
  }
  /** @apilevel internal */
  private void superInterfaces_reset() {
    superInterfaces_computed = null;
    superInterfaces_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle superInterfaces_computed = null;

  /** @apilevel internal */
  protected Collection<InterfaceDecl> superInterfaces_value;

  /**
   * The direct superinterfaces of this type, in the order of the implements clause.
   * 
   * @return the interfaces directly implemented by this type.
   * @attribute syn
   * @aspect SuperClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:686
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SuperClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:686")
  public Collection<InterfaceDecl> superInterfaces() {
    ASTState state = state();
    if (superInterfaces_computed == ASTState.NON_CYCLE || superInterfaces_computed == state().cycle()) {
      return superInterfaces_value;
    }
    superInterfaces_value = superInterfaces_compute();
    if (state().inCircle()) {
      superInterfaces_computed = state().cycle();
    
    } else {
      superInterfaces_computed = ASTState.NON_CYCLE;
    
    }
    return superInterfaces_value;
  }
  /** @apilevel internal */
  private Collection<InterfaceDecl> superInterfaces_compute() {
      if (isObject()) {
        return Collections.emptyList();
      } else {
        Collection<InterfaceDecl> interfaces = new ArrayList<InterfaceDecl>();
        for (Access access : getImplementsList()) {
          TypeDecl implemented = access.type();
          if (implemented.isInterfaceDecl()) {
            // It is an error if implemented is not an interface (error check exists).
            interfaces.add((InterfaceDecl) implemented);
          }
        }
        return interfaces;
      }
    }
/** @apilevel internal */
protected ASTState.Cycle isCircular_cycle = null;
  /** @apilevel internal */
  private void isCircular_reset() {
    isCircular_computed = false;
    isCircular_initialized = false;
    isCircular_cycle = null;
  }
  /** @apilevel internal */
  protected boolean isCircular_computed = false;

  /** @apilevel internal */
  protected boolean isCircular_value;
  /** @apilevel internal */
  protected boolean isCircular_initialized = false;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="Circularity", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:720")
  public boolean isCircular() {
    if (isCircular_computed) {
      return isCircular_value;
    }
    ASTState state = state();
    if (!isCircular_initialized) {
      isCircular_initialized = true;
      isCircular_value = true;
    }
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      do {
        isCircular_cycle = state.nextCycle();
        boolean new_isCircular_value = isCircular_compute();
        if (isCircular_value != new_isCircular_value) {
          state.setChangeInCycle();
        }
        isCircular_value = new_isCircular_value;
      } while (state.testAndClearChangeInCycle());
      isCircular_computed = true;

      state.leaveCircle();
    } else if (isCircular_cycle != state.cycle()) {
      isCircular_cycle = state.cycle();
      boolean new_isCircular_value = isCircular_compute();
      if (isCircular_value != new_isCircular_value) {
        state.setChangeInCycle();
      }
      isCircular_value = new_isCircular_value;
    } else {
    }
    return isCircular_value;
  }
  /** @apilevel internal */
  private boolean isCircular_compute() {
      if (hasSuperClass()) {
        Access a = getSuperClass().lastAccess();
        while (a != null) {
          if (a.type().isCircular()) {
            return true;
          }
          a = (a.isQualified() && a.qualifier().isTypeAccess()) ? (Access) a.qualifier() : null;
        }
      }
      for (int i = 0; i < getNumImplements(); i++) {
        Access a = getImplements(i).lastAccess();
        while (a != null) {
          if (a.type().isCircular()) {
            return true;
          }
          a = (a.isQualified() && a.qualifier().isTypeAccess()) ? (Access) a.qualifier() : null;
        }
      }
      return false;
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
        Collection<Problem> problems = refined_TypeHierarchyCheck_ClassDecl_typeProblems();
    
        if (hasSuperclass()) {
          // JLS SE7 8.4.8.4
          // Check for duplicate methods inherited from parameterized supertype.
          if (superclass().isParameterizedType()) {
            Map<String, SimpleSet<MethodDecl>> localMap = localMethodsSignatureMap();
            Map<String, SimpleSet<MethodDecl>> methodMap = superclass().localMethodsSignatureMap();
            for (Map.Entry<String, SimpleSet<MethodDecl>> entry: methodMap.entrySet()) {
              String signature = entry.getKey();
              if (!localMap.containsKey(signature)) {
                // Not locally overridden.
                SimpleSet<MethodDecl> set = entry.getValue();
                Iterator<MethodDecl> iter = set.iterator();
                iter.next();
                while (iter.hasNext()) {
                  iter.next();
                  problems.add(errorf(
                      "method with signature %s is multiply declared when inherited from %s",
                      signature, superclass().typeName()));
                }
              }
            }
          }
        }
        return problems;
      }
  }
  /**
   * @attribute syn
   * @aspect TypeHierarchyCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:403
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:403")
  public Collection<Problem> nameProblems() {
    {
        Collection<Problem> problems = refined_TypeHierarchyCheck_ClassDecl_nameProblems();
        for (SimpleSet<MethodDecl> set : methodsSignatureMap().values()) {
          if (set.size() > 1) {
            boolean foundClassAbstract = false;
            MethodDecl foundNonAbstract = null;
            for (MethodDecl m : set) {
              if (!m.isAbstract()) {
                foundNonAbstract = m;
              } else if (m.hostType().isClassDecl() && m.hostType() != this) {
                foundClassAbstract = true;
              }
            }
            // 8.4.8.1
            if (foundNonAbstract != null && !foundClassAbstract) {
              problems.add(errorf("Method %s is multiply declared in %s",
                  foundNonAbstract.fullSignature(), typeName()));
            }
          }
        }
        return problems;
      }
  }
  /** @apilevel internal */
  private void typeDescriptor_reset() {
    typeDescriptor_computed = null;
    typeDescriptor_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeDescriptor_computed = null;

  /** @apilevel internal */
  protected String typeDescriptor_value;

  /**
   * @attribute syn
   * @aspect ConstantPoolNames
   * @declaredat /home/olivier/projects/extendj/java4/backend/ConstantPoolNames.jrag:78
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantPoolNames", declaredAt="/home/olivier/projects/extendj/java4/backend/ConstantPoolNames.jrag:78")
  public String typeDescriptor() {
    ASTState state = state();
    if (typeDescriptor_computed == ASTState.NON_CYCLE || typeDescriptor_computed == state().cycle()) {
      return typeDescriptor_value;
    }
    typeDescriptor_value = "L" + constantPoolName() + ";";
    if (state().inCircle()) {
      typeDescriptor_computed = state().cycle();
    
    } else {
      typeDescriptor_computed = ASTState.NON_CYCLE;
    
    }
    return typeDescriptor_value;
  }
  /**
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:76
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="InnerClasses", declaredAt="/home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:76")
  public boolean hasMethod(MethodDecl decl) {
    boolean hasMethod_MethodDecl_value = super.hasMethod(decl) || hasSuperclass() && superclass().hasMethod(decl);
    return hasMethod_MethodDecl_value;
  }
  /**
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:480
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="InnerClasses", declaredAt="/home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:480")
  public TypeDecl superEnclosing() {
    {
        return superclass().erasure().enclosing();
      }
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:347
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:347")
  public Annotation annotation(TypeDecl typeDecl) {
    {
        Annotation a = super.annotation(typeDecl);
        if (a != null) {
          return a;
        }
        if (hasSuperclass()) {
          // If the queried annotation is itself annotation with @Inherited then
          // delegate the query to the superclass.
          if (typeDecl.annotation(lookupType("java.lang.annotation", "Inherited")) != null) {
            return superclass().annotation(typeDecl);
          }
        }
        return null;
      }
  }
  /** @apilevel internal */
  private void iterableElementType_reset() {
    iterableElementType_computed = null;
    iterableElementType_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle iterableElementType_computed = null;

  /** @apilevel internal */
  protected TypeDecl iterableElementType_value;

  /**
   * Computes the element type of a type implementing java.lang.Iterable.
   * Returns UnknownType if this type does not implement java.lang.Iterable.
   * @attribute syn
   * @aspect EnhancedFor
   * @declaredat /home/olivier/projects/extendj/java5/frontend/EnhancedFor.jrag:77
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EnhancedFor", declaredAt="/home/olivier/projects/extendj/java5/frontend/EnhancedFor.jrag:77")
  public TypeDecl iterableElementType() {
    ASTState state = state();
    if (iterableElementType_computed == ASTState.NON_CYCLE || iterableElementType_computed == state().cycle()) {
      return iterableElementType_value;
    }
    iterableElementType_value = iterableElementType_compute();
    if (state().inCircle()) {
      iterableElementType_computed = state().cycle();
    
    } else {
      iterableElementType_computed = ASTState.NON_CYCLE;
    
    }
    return iterableElementType_value;
  }
  /** @apilevel internal */
  private TypeDecl iterableElementType_compute() {
      TypeDecl type = unknownType();
      for (Access iface : getImplementsList()) {
        type = iface.type().iterableElementType();
        if (!type.isUnknown()) {
          break;
        }
      }
      if (type.isUnknown() && hasSuperclass()) {
        return superclass().iterableElementType();
      } else {
        return type;
      }
    }
  /** @apilevel internal */
  private void erasedAncestorMethodsMap_reset() {
    erasedAncestorMethodsMap_computed = null;
    erasedAncestorMethodsMap_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle erasedAncestorMethodsMap_computed = null;

  /** @apilevel internal */
  protected Map<String, SimpleSet<MethodDecl>> erasedAncestorMethodsMap_value;

  /**
   * @attribute syn
   * @aspect GenericsTypeCheck
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:535
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsTypeCheck", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:535")
  public Map<String, SimpleSet<MethodDecl>> erasedAncestorMethodsMap() {
    ASTState state = state();
    if (erasedAncestorMethodsMap_computed == ASTState.NON_CYCLE || erasedAncestorMethodsMap_computed == state().cycle()) {
      return erasedAncestorMethodsMap_value;
    }
    erasedAncestorMethodsMap_value = erasedAncestorMethodsMap_compute();
    if (state().inCircle()) {
      erasedAncestorMethodsMap_computed = state().cycle();
    
    } else {
      erasedAncestorMethodsMap_computed = ASTState.NON_CYCLE;
    
    }
    return erasedAncestorMethodsMap_value;
  }
  /** @apilevel internal */
  private Map<String, SimpleSet<MethodDecl>> erasedAncestorMethodsMap_compute() {
      Map<String, SimpleSet<MethodDecl>> localMap = localMethodsSignatureMap();
      Map<String, SimpleSet<MethodDecl>> map = new HashMap<String, SimpleSet<MethodDecl>>();
      if (hasSuperclass()) {
        for (MethodDecl m : superclass().localMethods()) {
          if (!m.isPrivate() && m.accessibleFrom(this) && m.erasedMethod() != m) {
            // Map erased signature to substituted method.
            putSimpleSetElement(map, m.erasedMethod().signature(), m);
          }
        }
        mergeMap(map, superclass().erasedAncestorMethodsMap());
      }
      for (MethodDecl m : interfacesMethods()) {
        if (m.accessibleFrom(this) && m.erasedMethod() != m) {
          String erasedSignature = m.erasedMethod().signature();
          // Map erased signature to substituted method.
          putSimpleSetElement(map, erasedSignature, m);
        }
      }
      return map;
    }
  /** @apilevel internal */
  private void implementedInterfaces_reset() {
    implementedInterfaces_computed = null;
    implementedInterfaces_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle implementedInterfaces_computed = null;

  /** @apilevel internal */
  protected Collection<InterfaceDecl> implementedInterfaces_value;

  /**
   * @attribute syn
   * @aspect GenericsTypeCheck
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:640
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsTypeCheck", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:640")
  public Collection<InterfaceDecl> implementedInterfaces() {
    ASTState state = state();
    if (implementedInterfaces_computed == ASTState.NON_CYCLE || implementedInterfaces_computed == state().cycle()) {
      return implementedInterfaces_value;
    }
    implementedInterfaces_value = implementedInterfaces_compute();
    if (state().inCircle()) {
      implementedInterfaces_computed = state().cycle();
    
    } else {
      implementedInterfaces_computed = ASTState.NON_CYCLE;
    
    }
    return implementedInterfaces_value;
  }
  /** @apilevel internal */
  private Collection<InterfaceDecl> implementedInterfaces_compute() {
      Collection<InterfaceDecl> set = new HashSet<InterfaceDecl>();
      if (hasSuperclass()) {
        set.addAll(superclass().implementedInterfaces());
      }
      for (InterfaceDecl decl : superInterfaces()) {
        set.add(decl);
        set.addAll(decl.implementedInterfaces());
      }
      return set;
    }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:492
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:492")
  public boolean subtype(TypeDecl type) {
    boolean subtype_TypeDecl_value = type.supertypeClassDecl(this);
    return subtype_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:507
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:507")
  public boolean supertypeClassDecl(ClassDecl type) {
    boolean supertypeClassDecl_ClassDecl_value = super.supertypeClassDecl(type) || type.hasSuperclass() && type.superclass().subtype(this);
    return supertypeClassDecl_ClassDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:523
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:523")
  public boolean supertypeInterfaceDecl(InterfaceDecl type) {
    boolean supertypeInterfaceDecl_InterfaceDecl_value = isObject();
    return supertypeInterfaceDecl_InterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:539
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:539")
  public boolean supertypeArrayDecl(ArrayDecl type) {
    {
        if (super.supertypeArrayDecl(type)) {
          return true;
        }
        return type.hasSuperclass() && type.superclass().subtype(this);
      }
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
    boolean isReifiable_value = !isInnerClass() || enclosingType().isReifiable();
    return isReifiable_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:363
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:363")
  public boolean strictSubtype(TypeDecl type) {
    boolean strictSubtype_TypeDecl_value = type.strictSupertypeClassDecl(this);
    return strictSubtype_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:378
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:378")
  public boolean strictSupertypeClassDecl(ClassDecl type) {
    boolean strictSupertypeClassDecl_ClassDecl_value = super.strictSupertypeClassDecl(type) || type.hasSuperclass()
          && type.superclass() != null && type.superclass().strictSubtype(this);
    return strictSupertypeClassDecl_ClassDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:397
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:397")
  public boolean strictSupertypeInterfaceDecl(InterfaceDecl type) {
    boolean strictSupertypeInterfaceDecl_InterfaceDecl_value = isObject();
    return strictSupertypeInterfaceDecl_InterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:413
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:413")
  public boolean strictSupertypeArrayDecl(ArrayDecl type) {
    {
        if (super.strictSupertypeArrayDecl(type)) {
          return true;
        }
        return type.hasSuperclass() && type.superclass() != null
            && type.superclass().strictSubtype(this);
      }
  }
  /** @apilevel internal */
  private void hasOverridingMethodInSuper_MethodDecl_reset() {
    hasOverridingMethodInSuper_MethodDecl_computed = null;
    hasOverridingMethodInSuper_MethodDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map hasOverridingMethodInSuper_MethodDecl_values;
  /** @apilevel internal */
  protected java.util.Map hasOverridingMethodInSuper_MethodDecl_computed;
  /**
   * @attribute syn
   * @aspect MethodSignature18
   * @declaredat /home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:1274
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature18", declaredAt="/home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:1274")
  public boolean hasOverridingMethodInSuper(MethodDecl m) {
    Object _parameters = m;
    if (hasOverridingMethodInSuper_MethodDecl_computed == null) hasOverridingMethodInSuper_MethodDecl_computed = new java.util.HashMap(4);
    if (hasOverridingMethodInSuper_MethodDecl_values == null) hasOverridingMethodInSuper_MethodDecl_values = new java.util.HashMap(4);
    ASTState state = state();
    if (hasOverridingMethodInSuper_MethodDecl_values.containsKey(_parameters)
        && hasOverridingMethodInSuper_MethodDecl_computed.containsKey(_parameters)
        && (hasOverridingMethodInSuper_MethodDecl_computed.get(_parameters) == ASTState.NON_CYCLE || hasOverridingMethodInSuper_MethodDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) hasOverridingMethodInSuper_MethodDecl_values.get(_parameters);
    }
    boolean hasOverridingMethodInSuper_MethodDecl_value = hasOverridingMethodInSuper_compute(m);
    if (state().inCircle()) {
      hasOverridingMethodInSuper_MethodDecl_values.put(_parameters, hasOverridingMethodInSuper_MethodDecl_value);
      hasOverridingMethodInSuper_MethodDecl_computed.put(_parameters, state().cycle());
    
    } else {
      hasOverridingMethodInSuper_MethodDecl_values.put(_parameters, hasOverridingMethodInSuper_MethodDecl_value);
      hasOverridingMethodInSuper_MethodDecl_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return hasOverridingMethodInSuper_MethodDecl_value;
  }
  /** @apilevel internal */
  private boolean hasOverridingMethodInSuper_compute(MethodDecl m) {
      for (MethodDecl superMethod : interfacesMethods()) {
        if (m != superMethod && superMethod.overrides(m)) {
          return true;
        }
  
      }
      if (hasSuperclass()) {
        for (MethodDecl superMethod : superclass().methods()) {
          if (m != superMethod && superMethod.overrides(m)) {
            return true;
          }
        }
      }
  
      return false;
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
        ArrayList<BodyDecl> methods = new ArrayList<BodyDecl>();
        if (hasImplicitConstructor()) {
          methods.add(getImplicitConstructor());
        }
        methods.addAll(super.methodsAndConstructors());
        return methods;
      }
  }
  /** @apilevel internal */
  private void bridgeCandidates_String_reset() {
    bridgeCandidates_String_computed = null;
    bridgeCandidates_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map bridgeCandidates_String_values;
  /** @apilevel internal */
  protected java.util.Map bridgeCandidates_String_computed;
  /**
   * @attribute syn
   * @aspect GenericsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:142
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:142")
  public SimpleSet<MethodDecl> bridgeCandidates(String signature) {
    Object _parameters = signature;
    if (bridgeCandidates_String_computed == null) bridgeCandidates_String_computed = new java.util.HashMap(4);
    if (bridgeCandidates_String_values == null) bridgeCandidates_String_values = new java.util.HashMap(4);
    ASTState state = state();
    if (bridgeCandidates_String_values.containsKey(_parameters)
        && bridgeCandidates_String_computed.containsKey(_parameters)
        && (bridgeCandidates_String_computed.get(_parameters) == ASTState.NON_CYCLE || bridgeCandidates_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<MethodDecl>) bridgeCandidates_String_values.get(_parameters);
    }
    SimpleSet<MethodDecl> bridgeCandidates_String_value = bridgeCandidates_compute(signature);
    if (state().inCircle()) {
      bridgeCandidates_String_values.put(_parameters, bridgeCandidates_String_value);
      bridgeCandidates_String_computed.put(_parameters, state().cycle());
    
    } else {
      bridgeCandidates_String_values.put(_parameters, bridgeCandidates_String_value);
      bridgeCandidates_String_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return bridgeCandidates_String_value;
  }
  /** @apilevel internal */
  private SimpleSet<MethodDecl> bridgeCandidates_compute(String signature) {
      SimpleSet<MethodDecl> set = ancestorMethods(signature);
      for (MethodDecl method : interfacesMethodsSignature(signature)) {
        set = set.add(method);
      }
      return set;
    }
  /** @apilevel internal */
  private void needsSignatureAttribute_reset() {
    needsSignatureAttribute_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle needsSignatureAttribute_computed = null;

  /** @apilevel internal */
  protected boolean needsSignatureAttribute_value;

  /**
   * @attribute syn
   * @aspect GenericsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:217
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:217")
  public boolean needsSignatureAttribute() {
    ASTState state = state();
    if (needsSignatureAttribute_computed == ASTState.NON_CYCLE || needsSignatureAttribute_computed == state().cycle()) {
      return needsSignatureAttribute_value;
    }
    needsSignatureAttribute_value = needsSignatureAttribute_compute();
    if (state().inCircle()) {
      needsSignatureAttribute_computed = state().cycle();
    
    } else {
      needsSignatureAttribute_computed = ASTState.NON_CYCLE;
    
    }
    return needsSignatureAttribute_value;
  }
  /** @apilevel internal */
  private boolean needsSignatureAttribute_compute() {
      if (hasSuperclass() && superclass().needsSignatureAttribute()) {
        return true;
      }
      for (InterfaceDecl iface : superInterfaces()) {
        if (iface.needsSignatureAttribute()) {
          return true;
        }
      }
      return false;
    }
  /** @apilevel internal */
  private void classSignature_reset() {
    classSignature_computed = null;
    classSignature_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle classSignature_computed = null;

  /** @apilevel internal */
  protected String classSignature_value;

  /**
   * @attribute syn
   * @aspect GenericsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:276
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:276")
  public String classSignature() {
    ASTState state = state();
    if (classSignature_computed == ASTState.NON_CYCLE || classSignature_computed == state().cycle()) {
      return classSignature_value;
    }
    classSignature_value = classSignature_compute();
    if (state().inCircle()) {
      classSignature_computed = state().cycle();
    
    } else {
      classSignature_computed = ASTState.NON_CYCLE;
    
    }
    return classSignature_value;
  }
  /** @apilevel internal */
  private String classSignature_compute() {
      StringBuilder buf = new StringBuilder();
      if (hasSuperclass()) {
        buf.append(superclass().classTypeSignature());
      }
      for (InterfaceDecl iface : superInterfaces()) {
        buf.append(iface.classTypeSignature());
      }
      return buf.toString();
    }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethods.jrag:231
   * @apilevel internal
   */
  public SimpleSet<TypeDecl> Define_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (_callerNode == getImplicitConstructorOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:539
      return localLookupType(name);
    }
    else {
      return super.Define_lookupType(_callerNode, _childNode, name);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethods.jrag:231
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupType
   */
  protected boolean canDefine_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:434
   * @apilevel internal
   */
  public boolean Define_mayBeFinal(ASTNode _callerNode, ASTNode _childNode) {
    if (getModifiersNoTransform() != null && _callerNode == getModifiers()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:309
      return true;
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
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getImplementsListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:98
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return NameType.TYPE_NAME;
    }
    else if (_callerNode == getSuperClassOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:97
      return NameType.TYPE_NAME;
    }
    else {
      return super.Define_nameType(_callerNode, _childNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute nameType
   */
  protected boolean canDefine_nameType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:551
   * @apilevel internal
   */
  public TypeDecl Define_enclosingType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getImplicitConstructorOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:541
      return this;
    }
    else {
      return super.Define_enclosingType(_callerNode, _childNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:551
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute enclosingType
   */
  protected boolean canDefine_enclosingType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:76
   * @apilevel internal
   */
  public TypeDecl Define_hostType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getImplementsListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:646
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return hostType();
    }
    else if (_callerNode == getSuperClassOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:645
      return hostType();
    }
    else {
      return super.Define_hostType(_callerNode, _childNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:76
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute hostType
   */
  protected boolean canDefine_hostType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/SuppressWarnings.jrag:37
   * @apilevel internal
   */
  public boolean Define_withinSuppressWarnings(ASTNode _callerNode, ASTNode _childNode, String annot) {
    if (_callerNode == getImplementsListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:414
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return hasAnnotationSuppressWarnings(annot) || withinSuppressWarnings(annot);
    }
    else if (_callerNode == getSuperClassOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:411
      return hasAnnotationSuppressWarnings(annot) || withinSuppressWarnings(annot);
    }
    else {
      return getParent().Define_withinSuppressWarnings(this, _callerNode, annot);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/SuppressWarnings.jrag:37
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute withinSuppressWarnings
   */
  protected boolean canDefine_withinSuppressWarnings(ASTNode _callerNode, ASTNode _childNode, String annot) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:533
   * @apilevel internal
   */
  public boolean Define_withinDeprecatedAnnotation(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getImplementsListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:541
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return isDeprecated() || withinDeprecatedAnnotation();
    }
    else if (_callerNode == getSuperClassOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:538
      return isDeprecated() || withinDeprecatedAnnotation();
    }
    else {
      return super.Define_withinDeprecatedAnnotation(_callerNode, _childNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:533
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute withinDeprecatedAnnotation
   */
  protected boolean canDefine_withinDeprecatedAnnotation(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:383
   * @apilevel internal
   */
  public boolean Define_inExtendsOrImplements(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getImplementsListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:381
      int i = _callerNode.getIndexOfChild(_childNode);
      return true;
    }
    else if (_callerNode == getSuperClassOptNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:380
      return true;
    }
    else {
      return getParent().Define_inExtendsOrImplements(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:383
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute inExtendsOrImplements
   */
  protected boolean canDefine_inExtendsOrImplements(ASTNode _callerNode, ASTNode _childNode) {
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
    // @declaredat /home/olivier/projects/extendj/java4/frontend/AccessControl.jrag:184
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:382
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:121
    if (!superclass().isUnknown() && superclass().isFinal()) {
      {
        java.util.Set<ASTNode> contributors = _map.get(_root);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) _root, contributors);
        }
        contributors.add(this);
      }
    }
    // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:366
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
  protected void collect_contributors_TypeDecl_accessors(CompilationUnit _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {


  
getImplicitConstructorOpt().collect_contributors_TypeDecl_accessors(_root, _map);
    super.collect_contributors_TypeDecl_accessors(_root, _map);
  }
  /** @apilevel internal */
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    for (Problem value : accessControlProblems()) {
      collection.add(value);
    }
    for (Problem value : exceptionHandlingProblems()) {
      collection.add(value);
    }
    if (!superclass().isUnknown() && superclass().isFinal()) {
      collection.add(errorf("class %s may not extend final class %s", fullName(), superclass().fullName()));
    }
    for (Problem value : typeProblems()) {
      collection.add(value);
    }
  }
}
