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
/**
 * Intersection type.
 * Occurs in, for example, conditional expression type analysis.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java5/grammar/Generics.ast:120
 * @production LUBType : {@link ReferenceType} ::= <span class="component">{@link Modifiers}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">{@link BodyDecl}*</span> <span class="component">TypeBound:{@link Access}*</span>;

 */
public class LUBType extends ReferenceType implements Cloneable {
  /**
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:687
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
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:708
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
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:733
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
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:748
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
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:770
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
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:822
   */
  public TypeDecl lub(TypeDecl X, TypeDecl Y) {
    ArrayList<TypeDecl> list = new ArrayList<TypeDecl>(2);
    list.add(X);
    list.add(Y);
    return lub(list);
  }
  /**
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:829
   */
  public TypeDecl lub(ArrayList<TypeDecl> list) {
    return lookupLUBType(list);
  }
  /** @return erased supertype set of the given type. 
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:834
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
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:847
   */
  public static Collection<TypeDecl> ST(TypeDecl type) {
    Collection<TypeDecl> result = new HashSet<TypeDecl>();
    LUBType.addSupertypes(result, type);
    return result;
  }
  /**
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:853
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
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1811
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
  public LUBType(Modifiers p0, String p1, List<BodyDecl> p2, List<Access> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @declaredat ASTNode:21
   */
  public LUBType(Modifiers p0, beaver.Symbol p1, List<BodyDecl> p2, List<Access> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:28
   */
  protected int numChildren() {
    return 3;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:34
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:38
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    lub_reset();
    subtype_TypeDecl_reset();
    strictSubtype_TypeDecl_reset();
    getSootClassDecl_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:46
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:50
   */
  public LUBType clone() throws CloneNotSupportedException {
    LUBType node = (LUBType) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:55
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
   * @declaredat ASTNode:74
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
   * @declaredat ASTNode:84
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
   * @declaredat ASTNode:104
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
   * @declaredat ASTNode:118
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
  protected ASTNode$State.Cycle lub_computed = null;

  /** @apilevel internal */
  protected TypeDecl lub_value;

  /**
   * @attribute syn
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:668
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericMethodsInference", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:668")
  public TypeDecl lub() {
    ASTNode$State state = state();
    if (lub_computed == ASTNode$State.NON_CYCLE || lub_computed == state().cycle()) {
      return lub_value;
    }
    lub_value = lub_compute();
    if (state().inCircle()) {
      lub_computed = state().cycle();
    
    } else {
      lub_computed = ASTNode$State.NON_CYCLE;
    
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
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1799
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:1799")
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
        new_subtype_TypeDecl_value = type.supertypeLUBType(this);
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
      boolean new_subtype_TypeDecl_value = type.supertypeLUBType(this);
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
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:524
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:524")
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
        new_strictSubtype_TypeDecl_value = type.strictSupertypeLUBType(this);
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
      boolean new_strictSubtype_TypeDecl_value = type.strictSupertypeLUBType(this);
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
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:398
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:398")
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
  private void getSootClassDecl_reset() {
    getSootClassDecl_computed = null;
    getSootClassDecl_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle getSootClassDecl_computed = null;

  /** @apilevel internal */
  protected SootClass getSootClassDecl_value;

  /**
   * @attribute syn
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:29
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EmitJimple", declaredAt="/home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:29")
  public SootClass getSootClassDecl() {
    ASTNode$State state = state();
    if (getSootClassDecl_computed == ASTNode$State.NON_CYCLE || getSootClassDecl_computed == state().cycle()) {
      return getSootClassDecl_value;
    }
    getSootClassDecl_value = typeObject().getSootClassDecl();
    if (state().inCircle()) {
      getSootClassDecl_computed = state().cycle();
    
    } else {
      getSootClassDecl_computed = ASTNode$State.NON_CYCLE;
    
    }
    return getSootClassDecl_value;
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
