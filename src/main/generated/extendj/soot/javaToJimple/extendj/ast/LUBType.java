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
 * Intersection type.
 * Occurs in, for example, conditional expression type analysis.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java5/grammar/Generics.ast:120
 * @astdecl LUBType : ReferenceType ::= Modifiers <ID:String> BodyDecl* TypeBound:Access*;
 * @production LUBType : {@link ReferenceType} ::= <span class="component">{@link Modifiers}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">{@link BodyDecl}*</span> <span class="component">TypeBound:{@link Access}*</span>;

 */
public class LUBType extends ReferenceType implements Cloneable {
  /**
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:709
   */
  public static Collection<TypeDecl> EC(ArrayList<TypeDecl> list) {
    Collection<TypeDecl> result = new HashSet<TypeDecl>();
    boolean first = true;
    for (TypeDecl U : list) {
      // Erased supertype set of U.
      Collection<TypeDecl> EST = LUBType.EST(U);
      if (first) {
        result.addAll(EST);
        first = false;
      } else {
        result.retainAll(EST);
      }
    }
    return result;
  }
  /**
   * The minimal erased candidate set for Tj
   * is MEC = {V | V in EC, forall  W != V in EC, not W <: V}
   * @return minimal erased candidate set for Tj
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:730
   */
  public static Collection<TypeDecl> MEC(ArrayList<TypeDecl> list) {
    Collection<TypeDecl> EC = LUBType.EC(list);
    if (EC.size() == 1) {
      return EC;
    }
    Collection<TypeDecl> MEC = new HashSet<TypeDecl>();
    for (TypeDecl V : EC) {
      boolean keep = true;
      for (TypeDecl W : EC) {
        if (!(V instanceof TypeVariable) && V != W && W.instanceOf(V)) {
          keep = false;
        }
      }
      if (keep) {
        MEC.add(V);
      }
    }
    return MEC;
  }
  /**
   * Relevant invocations of G, Inv(G)
   * Inv(G) = {V | 1 <= i <= k, V in ST(Ui), V = G<...>}
   * @return set of relevant invocations of G, Inv(G)
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:755
   */
  public static Collection<ParTypeDecl> Inv(TypeDecl G, Collection<TypeDecl> Us) {
    Collection<ParTypeDecl> result = new HashSet<ParTypeDecl>();
    for (TypeDecl U : Us) {
      for (TypeDecl V : LUBType.ST(U)) {
        if (V instanceof ParTypeDecl && !V.isRawType() && ((ParTypeDecl) V).genericDecl() == G) {
          result.add((ParTypeDecl) V);
        }
      }
    }
    return result;
  }
  /**
   * @return least containing invocation (lci)
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:770
   */
  public TypeDecl lci(Collection<ParTypeDecl> set, TypeDecl G) {
    ArrayList<TypeDecl> list = new ArrayList<TypeDecl>();
    boolean first = true;
    for (ParTypeDecl decl : set) {
      java.util.List<TypeDecl> declArgs = decl.getParameterization().args;
      if (first) {
        first = false;
        for (TypeDecl arg : declArgs) {
          list.add(arg);
        }
      } else {
        for (int i = 0; i < declArgs.size(); i++) {
          list.set(i, lcta(list.get(i), declArgs.get(i)));
        }
      }
    }
    return ((GenericTypeDecl) G).lookupParTypeDecl(list);
  }
  /**
   * Least containing type arguments.
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:792
   */
  public TypeDecl lcta(TypeDecl X, TypeDecl Y) {
    if (!X.isWildcard() && !Y.isWildcard()) {
      TypeDecl U = X;
      TypeDecl V = Y;
      return U == V ? U : lub(U, V).asWildcardExtends();
    } else if (!X.isWildcard() && Y instanceof WildcardExtendsType) {
      TypeDecl U = X;
      TypeDecl V = ((WildcardExtendsType) Y).getAccess().type();
      return lub(U, V).asWildcardExtends();
    } else if (!Y.isWildcard() && X instanceof WildcardExtendsType) {
      TypeDecl U = Y;
      TypeDecl V = ((WildcardExtendsType) X).getAccess().type();
      return lub(U, V).asWildcardExtends();
    } else if (!X.isWildcard() && Y instanceof WildcardSuperType) {
      TypeDecl U = X;
      TypeDecl V = ((WildcardSuperType) Y).getAccess().type();
      ArrayList<TypeDecl> bounds = new ArrayList<TypeDecl>();
      bounds.add(U);
      bounds.add(V);
      return GLBTypeFactory.glb(bounds).asWildcardSuper();
    } else if (!Y.isWildcard() && X instanceof WildcardSuperType) {
      TypeDecl U = Y;
      TypeDecl V = ((WildcardSuperType) X).getAccess().type();
      ArrayList<TypeDecl> bounds = new ArrayList<TypeDecl>();
      bounds.add(U);
      bounds.add(V);
      return GLBTypeFactory.glb(bounds).asWildcardSuper();
    } else if (X instanceof WildcardExtendsType && Y instanceof WildcardExtendsType) {
      TypeDecl U = ((WildcardExtendsType) X).getAccess().type();
      TypeDecl V = ((WildcardExtendsType) Y).getAccess().type();
      return lub(U, V).asWildcardExtends();
    } else if (X instanceof WildcardExtendsType && Y instanceof WildcardSuperType) {
      TypeDecl U = ((WildcardExtendsType) X).getAccess().type();
      TypeDecl V = ((WildcardSuperType) Y).getAccess().type();
      return U == V ? U : U.typeWildcard();
    } else if (Y instanceof WildcardExtendsType && X instanceof WildcardSuperType) {
      TypeDecl U = ((WildcardExtendsType) Y).getAccess().type();
      TypeDecl V = ((WildcardSuperType) X).getAccess().type();
      return U == V ? U : U.typeWildcard();
    } else if (X instanceof WildcardSuperType && Y instanceof WildcardSuperType) {
      TypeDecl U = ((WildcardSuperType) X).getAccess().type();
      TypeDecl V = ((WildcardSuperType) Y).getAccess().type();
      ArrayList<TypeDecl> bounds = new ArrayList<TypeDecl>();
      bounds.add(U);
      bounds.add(V);
      return GLBTypeFactory.glb(bounds).asWildcardSuper();
    } else {
      throw new Error("lcta not defined for (" + X.getClass().getName()
          + ", " + Y.getClass().getName() + ")");
    }
  }
  /**
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:844
   */
  public TypeDecl lub(TypeDecl X, TypeDecl Y) {
    ArrayList<TypeDecl> list = new ArrayList<TypeDecl>(2);
    list.add(X);
    list.add(Y);
    return lub(list);
  }
  /**
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:851
   */
  public TypeDecl lub(ArrayList<TypeDecl> list) {
    return lookupLUBType(list);
  }
  /** @return erased supertype set of the given type. 
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:856
   */
  public static Collection<TypeDecl> EST(TypeDecl type) {
    Collection<TypeDecl> result = new HashSet<TypeDecl>();
    for (TypeDecl typeDecl : LUBType.ST(type)) {
      if (typeDecl instanceof TypeVariable) {
        result.add(typeDecl);
      } else {
        result.add(typeDecl.erasure());
      }
    }
    return result;
  }
  /** @return supertype set of the given type. 
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:869
   */
  public static Collection<TypeDecl> ST(TypeDecl type) {
    Collection<TypeDecl> result = new HashSet<TypeDecl>();
    LUBType.addSupertypes(result, type);
    return result;
  }
  /**
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:875
   */
  public static void addSupertypes(Collection<TypeDecl> set, TypeDecl t) {
    set.add(t);
    if (t instanceof ClassDecl) {
      ClassDecl type = (ClassDecl) t;
      if (type.hasSuperclass()) {
        addSupertypes(set, type.superclass());
      }
      for (int i = 0; i < type.getNumImplements(); i++) {
        addSupertypes(set, type.getImplements(i).type());
      }
    } else if (t instanceof InterfaceDecl) {
      InterfaceDecl type = (InterfaceDecl) t;
      for (int i = 0; i < type.getNumSuperInterface(); i++) {
        addSupertypes(set, type.getSuperInterface(i).type());
      }
      if (type.getNumSuperInterface() == 0) {
        set.add(type.typeObject());
      }
    } else if (t instanceof TypeVariable) {
      TypeVariable type = (TypeVariable) t;
      for (int i = 0; i < type.getNumTypeBound(); i++) {
        addSupertypes(set, type.getTypeBound(i).type());
      }
      if (type.getNumTypeBound() == 0) {
        set.add(type.typeObject());
      }
    } else if (t instanceof LUBType) {
      LUBType type = (LUBType) t;
      for (int i = 0; i < type.getNumTypeBound(); i++) {
        addSupertypes(set, type.getTypeBound(i).type());
      }
      if (type.getNumTypeBound() == 0) {
        set.add(type.typeObject());
      }
    } else if (! (t instanceof NullType)) {
      throw new Error("Operation not supported for "
          + t.fullName() + ", " + t.getClass().getName());
    }
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1817
   */
  public Collection<InterfaceDecl> implementedInterfaces() {
    Collection<InterfaceDecl> ret = new HashSet<InterfaceDecl>();
    for (int i = 0; i < getNumTypeBound(); i++) {
      ret.addAll(getTypeBound(i).type().implementedInterfaces());
    }
    return ret;
  }
  /**
   * @declaredat ASTNode:1
   */
  public LUBType() {
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
    setChild(new List(), 1);
    setChild(new List(), 2);
  }
  /**
   * @declaredat ASTNode:15
   */
  @ASTNodeAnnotation.Constructor(
    name = {"Modifiers", "ID", "BodyDecl", "TypeBound"},
    type = {"Modifiers", "String", "List<BodyDecl>", "List<Access>"},
    kind = {"Child", "Token", "List", "List"}
  )
  public LUBType(Modifiers p0, String p1, List<BodyDecl> p2, List<Access> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @declaredat ASTNode:26
   */
  public LUBType(Modifiers p0, beaver.Symbol p1, List<BodyDecl> p2, List<Access> p3) {
    setChild(p0, 0);
    setID(p1);
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
    lub_reset();
    sootClass_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:49
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:53
   */
  public LUBType clone() throws CloneNotSupportedException {
    LUBType node = (LUBType) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:58
   */
  public LUBType copy() {
    try {
      LUBType node = (LUBType) clone();
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
   * @declaredat ASTNode:77
   */
  @Deprecated
  public LUBType fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:87
   */
  public LUBType treeCopyNoTransform() {
    LUBType tree = (LUBType) copy();
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
   * @declaredat ASTNode:107
   */
  public LUBType treeCopy() {
    LUBType tree = (LUBType) copy();
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
   * @declaredat ASTNode:121
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((LUBType) node).tokenString_ID);    
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
   * Replaces the BodyDecl list.
   * @param list The new list node to be used as the BodyDecl list.
   * @apilevel high-level
   */
  public void setBodyDeclList(List<BodyDecl> list) {
    setChild(list, 1);
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
    List<BodyDecl> list = (List<BodyDecl>) getChild(1);
    return list;
  }
  /**
   * Retrieves the BodyDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the BodyDecl list.
   * @apilevel low-level
   */
  public List<BodyDecl> getBodyDeclListNoTransform() {
    return (List<BodyDecl>) getChildNoTransform(1);
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
   * Replaces the TypeBound list.
   * @param list The new list node to be used as the TypeBound list.
   * @apilevel high-level
   */
  public void setTypeBoundList(List<Access> list) {
    setChild(list, 2);
  }
  /**
   * Retrieves the number of children in the TypeBound list.
   * @return Number of children in the TypeBound list.
   * @apilevel high-level
   */
  public int getNumTypeBound() {
    return getTypeBoundList().getNumChild();
  }
  /**
   * Retrieves the number of children in the TypeBound list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the TypeBound list.
   * @apilevel low-level
   */
  public int getNumTypeBoundNoTransform() {
    return getTypeBoundListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the TypeBound list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the TypeBound list.
   * @apilevel high-level
   */
  public Access getTypeBound(int i) {
    return (Access) getTypeBoundList().getChild(i);
  }
  /**
   * Check whether the TypeBound list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasTypeBound() {
    return getTypeBoundList().getNumChild() != 0;
  }
  /**
   * Append an element to the TypeBound list.
   * @param node The element to append to the TypeBound list.
   * @apilevel high-level
   */
  public void addTypeBound(Access node) {
    List<Access> list = (parent == null) ? getTypeBoundListNoTransform() : getTypeBoundList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addTypeBoundNoTransform(Access node) {
    List<Access> list = getTypeBoundListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the TypeBound list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setTypeBound(Access node, int i) {
    List<Access> list = getTypeBoundList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the TypeBound list.
   * @return The node representing the TypeBound list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="TypeBound")
  public List<Access> getTypeBoundList() {
    List<Access> list = (List<Access>) getChild(2);
    return list;
  }
  /**
   * Retrieves the TypeBound list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeBound list.
   * @apilevel low-level
   */
  public List<Access> getTypeBoundListNoTransform() {
    return (List<Access>) getChildNoTransform(2);
  }
  /**
   * @return the element at index {@code i} in the TypeBound list without
   * triggering rewrites.
   */
  public Access getTypeBoundNoTransform(int i) {
    return (Access) getTypeBoundListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the TypeBound list.
   * @return The node representing the TypeBound list.
   * @apilevel high-level
   */
  public List<Access> getTypeBounds() {
    return getTypeBoundList();
  }
  /**
   * Retrieves the TypeBound list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeBound list.
   * @apilevel low-level
   */
  public List<Access> getTypeBoundsNoTransform() {
    return getTypeBoundListNoTransform();
  }
  /**
   * @attribute syn
   * @aspect TypeConversion
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:38
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeConversion", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:38")
  public boolean wideningConversionTo(TypeDecl type) {
    {
        for (Access bound : getTypeBoundList()) {
          if (bound.type().instanceOf(type)) {
            return true;
          }
        }
        return false;
      }
  }
  /** @apilevel internal */
  private void lub_reset() {
    lub_computed = null;
    lub_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle lub_computed = null;

  /** @apilevel internal */
  protected TypeDecl lub_value;

  /**
   * @attribute syn
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:690
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericMethodsInference", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:690")
  public TypeDecl lub() {
    ASTState state = state();
    if (lub_computed == ASTState.NON_CYCLE || lub_computed == state().cycle()) {
      return lub_value;
    }
    lub_value = lub_compute();
    if (state().inCircle()) {
      lub_computed = state().cycle();
    
    } else {
      lub_computed = ASTState.NON_CYCLE;
    
    }
    return lub_value;
  }
  /** @apilevel internal */
  private TypeDecl lub_compute() {
      ArrayList<TypeDecl> list = new ArrayList<TypeDecl>();
      for (int i = 0; i < getNumTypeBound(); i++) {
        list.add(getTypeBound(i).type());
      }
      ArrayList<TypeDecl> bounds = new ArrayList<TypeDecl>();
      for (TypeDecl W : LUBType.MEC(list)) {
        TypeDecl C = W instanceof GenericTypeDecl ? lci(Inv(W, list), W) : W;
        bounds.add(C);
      }
      if (bounds.size() == 1) {
        return bounds.iterator().next();
      }
      return lookupLUBType(bounds);
    }
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1805
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:1805")
  public String typeName() {
    {
        if (getNumTypeBound() == 0) {
          return "<NOTYPE>";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getTypeBound(0).type().typeName());
        for (int i = 1; i < getNumTypeBound(); i++) {
          sb.append(" & " + getTypeBound(i).type().typeName());
        }
        return sb.toString();
      }
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:492
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:492")
  public boolean subtype(TypeDecl type) {
    boolean subtype_TypeDecl_value = type.supertypeLUBType(this);
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
    boolean supertypeClassDecl_ClassDecl_value = type.subtype(lub());
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
    boolean supertypeInterfaceDecl_InterfaceDecl_value = type.subtype(lub());
    return supertypeInterfaceDecl_InterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:426
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:426")
  public boolean supertypeGLBType(GLBType type) {
    {
        // TODO(joqvist): changed from Access to TypeDecl, is this correct?
        ArrayList<TypeDecl> bounds = new ArrayList<TypeDecl>(getNumTypeBound());
        for (int i = 0; i < getNumTypeBound(); i++) {
          bounds.add(getTypeBound(i).type());
        }
        return type == lookupGLBType(bounds);
      }
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:363
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:363")
  public boolean strictSubtype(TypeDecl type) {
    boolean strictSubtype_TypeDecl_value = type.strictSupertypeLUBType(this);
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
    boolean strictSupertypeClassDecl_ClassDecl_value = type.strictSubtype(lub());
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
    boolean strictSupertypeInterfaceDecl_InterfaceDecl_value = type.strictSubtype(lub());
    return strictSupertypeInterfaceDecl_InterfaceDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:342
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:342")
  public boolean strictSupertypeGLBType(GLBType type) {
    {
        // TODO(joqvist): changed from Access to TypeDecl, is this correct?
        ArrayList<TypeDecl> bounds = new ArrayList<TypeDecl>(getNumTypeBound());
        for (int i = 0; i < getNumTypeBound(); i++) {
          bounds.add(getTypeBound(i).type());
        }
        return type == lookupGLBType(bounds);
      }
  }
  /** @apilevel internal */
  private void sootClass_reset() {
    sootClass_computed = null;
    sootClass_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle sootClass_computed = null;

  /** @apilevel internal */
  protected SootClass sootClass_value;

  /**
   * @attribute syn
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:63
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EmitJimple", declaredAt="/home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:63")
  public SootClass sootClass() {
    ASTState state = state();
    if (sootClass_computed == ASTState.NON_CYCLE || sootClass_computed == state().cycle()) {
      return sootClass_value;
    }
    sootClass_value = typeObject().sootClass();
    if (state().inCircle()) {
      sootClass_computed = state().cycle();
    
    } else {
      sootClass_computed = ASTState.NON_CYCLE;
    
    }
    return sootClass_value;
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
