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
/** Represents a type variable of a parameterized type. 
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java5/grammar/Generics.ast:56
 * @astdecl TypeVariable : ReferenceType ::= Modifiers <ID:String> BodyDecl* TypeBound:Access*;
 * @production TypeVariable : {@link ReferenceType} ::= <span class="component">{@link Modifiers}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">{@link BodyDecl}*</span> <span class="component">TypeBound:{@link Access}*</span>;

 */
public class TypeVariable extends ReferenceType implements Cloneable {
  /**
   * @aspect Generics
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:71
   */
  @Override public String toString() {
    return name();
  }
  /**
   * @aspect NewGenerics
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1863
   */
  public Access createQualifiedAccess() {
    return createBoundAccess();
  }
  /**
   * @aspect Java5PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java5/frontend/PrettyPrint.jadd:355
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getID());
    if (hasTypeBound()) {
      out.print(" extends ");
      out.join(getTypeBoundList(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(" & ");
        }
      });
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public TypeVariable() {
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
  public TypeVariable(Modifiers p0, String p1, List<BodyDecl> p2, List<Access> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @declaredat ASTNode:26
   */
  public TypeVariable(Modifiers p0, beaver.Symbol p1, List<BodyDecl> p2, List<Access> p3) {
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
    return true;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:43
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    rewrittenNode_reset();
    unboxed_reset();
    iterableElementType_reset();
    toInterface_reset();
    substituted_Parameterization_reset();
    memberFields_String_reset();
    castingConversionTo_TypeDecl_reset();
    erasure_reset();
    fullName_reset();
    lubType_reset();
    usesTypeVariable_reset();
    accessibleFrom_TypeDecl_reset();
    typeName_reset();
    instanceOf_TypeDecl_reset();
    sameType_TypeVariable_reset();
    typeDescriptor_reset();
    needsSignatureAttribute_reset();
    fieldTypeSignature_reset();
    classTypeSignature_reset();
    classBound_reset();
    interfaceBounds_reset();
    typeVarPosition_reset();
    genericMethodLevel_reset();
    typeVarInMethod_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:71
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:75
   */
  public TypeVariable clone() throws CloneNotSupportedException {
    TypeVariable node = (TypeVariable) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:80
   */
  public TypeVariable copy() {
    try {
      TypeVariable node = (TypeVariable) clone();
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
   * @declaredat ASTNode:99
   */
  @Deprecated
  public TypeVariable fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:109
   */
  public TypeVariable treeCopyNoTransform() {
    TypeVariable tree = (TypeVariable) copy();
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
   * @declaredat ASTNode:129
   */
  public TypeVariable treeCopy() {
    TypeVariable tree = (TypeVariable) copy();
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
   * @declaredat ASTNode:143
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((TypeVariable) node).tokenString_ID);    
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
  /** @apilevel internal */
  private void unboxed_reset() {
    unboxed_computed = null;
    unboxed_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle unboxed_computed = null;

  /** @apilevel internal */
  protected TypeDecl unboxed_value;

  /** Mapping between Reference type and corresponding unboxed Primitive type. 
   * @attribute syn
   * @aspect AutoBoxing
   * @declaredat /home/olivier/projects/extendj/java5/frontend/AutoBoxing.jrag:77
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AutoBoxing", declaredAt="/home/olivier/projects/extendj/java5/frontend/AutoBoxing.jrag:77")
  public TypeDecl unboxed() {
    ASTState state = state();
    if (unboxed_computed == ASTState.NON_CYCLE || unboxed_computed == state().cycle()) {
      return unboxed_value;
    }
    unboxed_value = unboxed_compute();
    if (state().inCircle()) {
      unboxed_computed = state().cycle();
    
    } else {
      unboxed_computed = ASTState.NON_CYCLE;
    
    }
    return unboxed_value;
  }
  /** @apilevel internal */
  private TypeDecl unboxed_compute() {
      for (Access bound: getTypeBoundList()) {
        TypeDecl unboxed = bound.type().unboxed();
        if (!unboxed.isUnknown()) {
          return unboxed;
        }
      }
      return unknownType();
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
      for (Access bound : getTypeBoundList()) {
        TypeDecl boundType = bound.type();
        if (boundType.instanceOf(lookupType("java.lang", "Iterable"))) {
          return boundType.iterableElementType();
        }
      }
      return unknownType();
    }
  /** @apilevel internal */
  private void toInterface_reset() {
    toInterface_computed = false;
    
    toInterface_value = null;
  }
  /** @apilevel internal */
  protected boolean toInterface_computed = false;

  /** @apilevel internal */
  protected InterfaceDecl toInterface_value;

  /** Convert var to interface. 
   * @attribute syn
   * @aspect GreatestLowerBoundFactory
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GLBTypeFactory.jadd:35
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="GreatestLowerBoundFactory", declaredAt="/home/olivier/projects/extendj/java5/frontend/GLBTypeFactory.jadd:35")
  public InterfaceDecl toInterface() {
    ASTState state = state();
    if (toInterface_computed) {
      return toInterface_value;
    }
    state().enterLazyAttribute();
    toInterface_value = toInterface_compute();
    toInterface_value.setParent(this);
    toInterface_computed = true;
    state().leaveLazyAttribute();
    return toInterface_value;
  }
  /** @apilevel internal */
  private InterfaceDecl toInterface_compute() {
      InterfaceDecl ITj = new InterfaceDecl();
      ITj.setID("ITj_" + hashCode());
      // I'm assuming that TypeVariable has no members of its own.
      // TODO: would it be enough to add only public members of a bound
      // that is TypeVariable or ClassDecl and add other (interface)
      // bounds as superinterfaces to ITj?
      // TODO: Is it really necessary to add public members to the new
      // interface? Or is an empty interface more than enough since java
      // has a nominal type system.
      for (int i = 0; i < getNumTypeBound(); i++) {
        TypeDecl bound = getTypeBound(i).type();
        for (int j = 0; j < bound.getNumBodyDecl(); j++) {
          BodyDecl bd = bound.getBodyDecl(j);
          if (bd instanceof FieldDecl) {
            if (((FieldDecl) bd).isPublic()) {
              ITj.addBodyDecl(bd.treeCopyNoTransform());
            }
          } else if (bd instanceof MethodDecl) {
            if (((MethodDecl) bd).isPublic()) {
              ITj.addBodyDecl(bd.treeCopyNoTransform());
            }
          }
        }
      }
      return ITj;
    }
  /**
   * Check if a given type is within the bound of this type, given a specific
   * parameterization of this type.
   * 
   * See JLS SE7 $4.5
   * 
   * @param argument argument type
   * @return {@code true} if the argument type is in the bound of this type
   * @attribute syn
   * @aspect GenericBoundCheck
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericBoundCheck.jrag:61
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericBoundCheck", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericBoundCheck.jrag:61")
  public boolean boundOf(TypeDecl argument) {
    {
        for (int i = 0; i < getNumTypeBound(); ++i) {
          TypeDecl boundType = getTypeBound(i).type();
          if (!argument.subtype(boundType)) {
            return false;
          }
        }
        return true;
      }
  }
  /**
   * @attribute syn
   * @aspect GenericBoundCheck
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericBoundCheck.jrag:73
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericBoundCheck", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericBoundCheck.jrag:73")
  public boolean boundOfWildcard(WildcardType type) {
    boolean boundOfWildcard_WildcardType_value = true;
    return boundOfWildcard_WildcardType_value;
  }
  /**
   * @attribute syn
   * @aspect GenericBoundCheck
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericBoundCheck.jrag:77
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericBoundCheck", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericBoundCheck.jrag:77")
  public boolean boundOfWildcardExtends(WildcardExtendsType type) {
    boolean boundOfWildcardExtends_WildcardExtendsType_value = type.extendsType().subtype(this);
    return boundOfWildcardExtends_WildcardExtendsType_value;
  }
  /**
   * @attribute syn
   * @aspect GenericBoundCheck
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericBoundCheck.jrag:82
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericBoundCheck", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericBoundCheck.jrag:82")
  public boolean boundOfWildcardSuper(WildcardSuperType type) {
    boolean boundOfWildcardSuper_WildcardSuperType_value = type.superType().subtype(this);
    return boundOfWildcardSuper_WildcardSuperType_value;
  }
  /**
   * @attribute syn
   * @aspect GenericBoundCheck
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericBoundCheck.jrag:87
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericBoundCheck", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericBoundCheck.jrag:87")
  public boolean boundOfArray(ArrayDecl type) {
    {
        // An array type is within the bounds of a type variable if the
        // type variable has a single type bound that is an array type bound,
        // or if there are no type bounds for the type variable.
        if (getNumTypeBound() == 1) {
          return getTypeBound(0).type().boundOfArray(type);
        }
        return getNumTypeBound() == 0;
      }
  }
  /**
   * @attribute syn
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:37
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericMethodsInference", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:37")
  public boolean involvesTypeParameters() {
    boolean involvesTypeParameters_value = true;
    return involvesTypeParameters_value;
  }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:383
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:383")
  public Collection<Problem> nameProblems() {
    {
          if (extractSingleType(lookupType(name())) != this) {
            return Collections.singletonList(
                errorf("*** Semantic Error: type variable %s is multiply declared", name()));
          }
          return Collections.emptySet();
      }
  }
  /**
   * @attribute syn
   * @aspect GenericTypeVariables
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericTypeVariables.jrag:58
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericTypeVariables", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericTypeVariables.jrag:58")
  public TypeDecl lowerBound() {
    TypeDecl lowerBound_value = getTypeBound(0).type();
    return lowerBound_value;
  }
  /** @apilevel internal */
  private void substituted_Parameterization_reset() {
    substituted_Parameterization_values = null;
    substituted_Parameterization_proxy = null;
  }
  /** @apilevel internal */
  protected ASTNode substituted_Parameterization_proxy;
  /** @apilevel internal */
  protected java.util.Map substituted_Parameterization_values;

  /**
   * @attribute syn
   * @aspect GenericTypeVariables
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericTypeVariables.jrag:60
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="GenericTypeVariables", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericTypeVariables.jrag:60")
  public TypeVariable substituted(Parameterization par) {
    Object _parameters = par;
    if (substituted_Parameterization_values == null) substituted_Parameterization_values = new java.util.HashMap(4);
    ASTState state = state();
    if (substituted_Parameterization_values.containsKey(_parameters)) {
      return (TypeVariable) substituted_Parameterization_values.get(_parameters);
    }
    state().enterLazyAttribute();
    TypeVariable substituted_Parameterization_value = new SubstitutedTypeVariable(
              new Modifiers(new List<Modifier>()),
              getID(),
              new List<BodyDecl>(),
              getTypeBoundList().treeCopy(),
              par);
    if (substituted_Parameterization_proxy == null) {
      substituted_Parameterization_proxy = new ASTNode();
      substituted_Parameterization_proxy.setParent(this);
    }
    if (substituted_Parameterization_value != null) {
      substituted_Parameterization_value.setParent(substituted_Parameterization_proxy);
      if (substituted_Parameterization_value.mayHaveRewrite()) {
        substituted_Parameterization_value = (TypeVariable) substituted_Parameterization_value.rewrittenNode();
        substituted_Parameterization_value.setParent(substituted_Parameterization_proxy);
      }
    }
    substituted_Parameterization_values.put(_parameters, substituted_Parameterization_value);
    state().leaveLazyAttribute();
    return substituted_Parameterization_value;
  }
  /**
   * Find all member method declarations with the given name.
   * This includes methods inherited from supertypes.
   * @attribute syn
   * @aspect MemberMethods
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:484
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MemberMethods", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:484")
  public Collection<MethodDecl> memberMethods(String name) {
    {
        Collection<MethodDecl> methods = new HashSet<MethodDecl>();
        for (int i = 0; i < getNumTypeBound(); i++) {
          for (MethodDecl method : getTypeBound(i).type().memberMethods(name)) {
            methods.add(method);
          }
        }
        return methods;
      }
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
      SimpleSet<Variable> fields = emptySet();
      for (int i = 0; i < getNumTypeBound(); i++) {
        for (Variable field : getTypeBound(i).type().memberFields(name)) {
          fields = fields.add(field);
        }
      }
      return fields;
    }
  /**
   * @attribute syn
   * @aspect GenricTypeVariablesTypeAnalysis
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericTypeVariables.jrag:101
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenricTypeVariablesTypeAnalysis", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericTypeVariables.jrag:101")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        if (!getTypeBound(0).type().isTypeVariable() && !getTypeBound(0).type().isClassDecl()
            && !getTypeBound(0).type().isInterfaceDecl()) {
          problems.add(errorf("the first type bound must be either a type variable,"
              + " or a class or interface type which %s is not",
              getTypeBound(0).type().fullName()));
        }
        for (int i = 1; i < getNumTypeBound(); i++) {
          if (!getTypeBound(i).type().isInterfaceDecl()) {
            problems.add(errorf("type bound %s must be an interface type which %s is not",
                i, getTypeBound(i).type().fullName()));
          }
        }
        Collection<TypeDecl> typeSet = new HashSet<TypeDecl>();
        for (int i = 0; i < getNumTypeBound(); i++) {
          TypeDecl type = getTypeBound(i).type();
          TypeDecl erasure = type.erasure();
          if (typeSet.contains(erasure)) {
            if (type != erasure) {
              problems.add(errorf("the erasure %s of typebound %s is multiply declared in %s",
                  erasure.fullName(), getTypeBound(i).prettyPrint(), this));
            } else {
              problems.add(errorf("%s is multiply declared", type.fullName()));
            }
          }
          typeSet.add(erasure);
        }
    
        for (int i = 0; i < getNumTypeBound(); i++) {
          TypeDecl type = getTypeBound(i).type();
          for (MethodDecl m : type.methods()) {
            for (int j = i + 1; j < getNumTypeBound(); j++) {
              TypeDecl destType = getTypeBound(j).type();
              for (MethodDecl n : destType.memberMethods(m.name())) {
                if (m.sameSignature(n) && m.type() != n.type()) {
                  problems.add(errorf("the two bounds, %s and %s, in type variable %s have"
                      + " a method %s with conflicting return types %s and %s",
                      type.name(), destType.name(), name(), m.signature(),
                      m.type().name(), n.type().name()));
                }
              }
            }
          }
        }
        return problems;
      }
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
      if (!type.isReferenceType()) {
        return false;
      }
      if (getNumTypeBound() == 0) {
        return true;
      }
      for (int i = 0; i < getNumTypeBound(); i++) {
        if (getTypeBound(i).type().castingConversionTo(type)) {
          return true;
        }
      }
      return false;
    }
  /**
   * @attribute syn
   * @aspect Generics
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:234
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Generics", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:234")
  public boolean isNestedType() {
    boolean isNestedType_value = false;
    return isNestedType_value;
  }
  /** @apilevel internal */
  private void erasure_reset() {
    erasure_computed = null;
    erasure_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle erasure_computed = null;

  /** @apilevel internal */
  protected TypeDecl erasure_value;

  /**
   * @attribute syn
   * @aspect GenericsErasure
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:460
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsErasure", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:460")
  public TypeDecl erasure() {
    ASTState state = state();
    if (erasure_computed == ASTState.NON_CYCLE || erasure_computed == state().cycle()) {
      return erasure_value;
    }
    erasure_value = getTypeBound(0).type().erasure();
    if (state().inCircle()) {
      erasure_computed = state().cycle();
    
    } else {
      erasure_computed = ASTState.NON_CYCLE;
    
    }
    return erasure_value;
  }
  /** @apilevel internal */
  private void fullName_reset() {
    fullName_computed = null;
    fullName_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle fullName_computed = null;

  /** @apilevel internal */
  protected String fullName_value;

  /**
   * @attribute syn
   * @aspect TypeName
   * @declaredat /home/olivier/projects/extendj/java4/frontend/QualifiedNames.jrag:84
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeName", declaredAt="/home/olivier/projects/extendj/java4/frontend/QualifiedNames.jrag:84")
  public String fullName() {
    ASTState state = state();
    if (fullName_computed == ASTState.NON_CYCLE || fullName_computed == state().cycle()) {
      return fullName_value;
    }
    fullName_value = typeVariableContext() + "@" + name();
    if (state().inCircle()) {
      fullName_computed = state().cycle();
    
    } else {
      fullName_computed = ASTState.NON_CYCLE;
    
    }
    return fullName_value;
  }
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:864
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:864")
  public boolean sameSignature(Access a) {
    boolean sameSignature_Access_value = a.type() == this;
    return sameSignature_Access_value;
  }
  /** @apilevel internal */
  private void lubType_reset() {
    lubType_computed = null;
    lubType_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle lubType_computed = null;

  /** @apilevel internal */
  protected TypeDecl lubType_value;

  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1292
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:1292")
  public TypeDecl lubType() {
    ASTState state = state();
    if (lubType_computed == ASTState.NON_CYCLE || lubType_computed == state().cycle()) {
      return lubType_value;
    }
    lubType_value = lubType_compute();
    if (state().inCircle()) {
      lubType_computed = state().cycle();
    
    } else {
      lubType_computed = ASTState.NON_CYCLE;
    
    }
    return lubType_value;
  }
  /** @apilevel internal */
  private TypeDecl lubType_compute() {
      ArrayList<TypeDecl> list = new ArrayList<TypeDecl>();
      for (int i = 0; i < getNumTypeBound(); i++) {
        list.add(getTypeBound(i).type());
      }
      return lookupLUBType(list);
    }
/** @apilevel internal */
protected ASTState.Cycle usesTypeVariable_cycle = null;
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
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:1321")
  public boolean usesTypeVariable() {
    if (usesTypeVariable_computed) {
      return usesTypeVariable_value;
    }
    ASTState state = state();
    if (!usesTypeVariable_initialized) {
      usesTypeVariable_initialized = true;
      usesTypeVariable_value = false;
    }
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      do {
        usesTypeVariable_cycle = state.nextCycle();
        boolean new_usesTypeVariable_value = true;
        if (usesTypeVariable_value != new_usesTypeVariable_value) {
          state.setChangeInCycle();
        }
        usesTypeVariable_value = new_usesTypeVariable_value;
      } while (state.testAndClearChangeInCycle());
      usesTypeVariable_computed = true;

      state.leaveCircle();
    } else if (usesTypeVariable_cycle != state.cycle()) {
      usesTypeVariable_cycle = state.cycle();
      boolean new_usesTypeVariable_value = true;
      if (usesTypeVariable_value != new_usesTypeVariable_value) {
        state.setChangeInCycle();
      }
      usesTypeVariable_value = new_usesTypeVariable_value;
    } else {
    }
    return usesTypeVariable_value;
  }
  /** @apilevel internal */
  private void accessibleFrom_TypeDecl_reset() {
    accessibleFrom_TypeDecl_computed = null;
    accessibleFrom_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map accessibleFrom_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map accessibleFrom_TypeDecl_computed;
  /**
   * @attribute syn
   * @aspect AccessControl
   * @declaredat /home/olivier/projects/extendj/java4/frontend/AccessControl.jrag:72
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessControl", declaredAt="/home/olivier/projects/extendj/java4/frontend/AccessControl.jrag:72")
  public boolean accessibleFrom(TypeDecl type) {
    Object _parameters = type;
    if (accessibleFrom_TypeDecl_computed == null) accessibleFrom_TypeDecl_computed = new java.util.HashMap(4);
    if (accessibleFrom_TypeDecl_values == null) accessibleFrom_TypeDecl_values = new java.util.HashMap(4);
    ASTState state = state();
    if (accessibleFrom_TypeDecl_values.containsKey(_parameters)
        && accessibleFrom_TypeDecl_computed.containsKey(_parameters)
        && (accessibleFrom_TypeDecl_computed.get(_parameters) == ASTState.NON_CYCLE || accessibleFrom_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) accessibleFrom_TypeDecl_values.get(_parameters);
    }
    boolean accessibleFrom_TypeDecl_value = true;
    if (state().inCircle()) {
      accessibleFrom_TypeDecl_values.put(_parameters, accessibleFrom_TypeDecl_value);
      accessibleFrom_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      accessibleFrom_TypeDecl_values.put(_parameters, accessibleFrom_TypeDecl_value);
      accessibleFrom_TypeDecl_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return accessibleFrom_TypeDecl_value;
  }
  /** @apilevel internal */
  private void typeName_reset() {
    typeName_computed = null;
    typeName_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeName_computed = null;

  /** @apilevel internal */
  protected String typeName_value;

  /**
   * The qualified typename of this type.
   * 
   * <p>Includes array suffix and type arguments.
   * @attribute syn
   * @aspect TypeName
   * @declaredat /home/olivier/projects/extendj/java4/frontend/QualifiedNames.jrag:100
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeName", declaredAt="/home/olivier/projects/extendj/java4/frontend/QualifiedNames.jrag:100")
  public String typeName() {
    ASTState state = state();
    if (typeName_computed == ASTState.NON_CYCLE || typeName_computed == state().cycle()) {
      return typeName_value;
    }
    typeName_value = name();
    if (state().inCircle()) {
      typeName_computed = state().cycle();
    
    } else {
      typeName_computed = ASTState.NON_CYCLE;
    
    }
    return typeName_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsParTypeDecl.jrag:104
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsParTypeDecl.jrag:104")
  public boolean isTypeVariable() {
    boolean isTypeVariable_value = true;
    return isTypeVariable_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:69
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:69")
  public boolean supertypeWildcard(WildcardType type) {
    boolean supertypeWildcard_WildcardType_value = true;
    return supertypeWildcard_WildcardType_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:76
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:76")
  public boolean supertypeWildcardExtends(WildcardExtendsType type) {
    boolean supertypeWildcardExtends_WildcardExtendsType_value = type.extendsType().subtype(this);
    return supertypeWildcardExtends_WildcardExtendsType_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:85
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:85")
  public boolean supertypeWildcardSuper(WildcardSuperType type) {
    boolean supertypeWildcardSuper_WildcardSuperType_value = type.superType().subtype(this);
    return supertypeWildcardSuper_WildcardSuperType_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:218
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:218")
  public boolean sameStructure(TypeDecl t) {
    {
        if (this == t) {
          return true;
        }
        if (t instanceof TypeVariable) {
          TypeVariable type = (TypeVariable) t;
          if (type.getNumTypeBound() != getNumTypeBound()) {
            return false;
          }
          for (int i = 0; i < getNumTypeBound(); i++) {
            boolean found = false;
            for (int j = i; !found && j < getNumTypeBound(); j++) {
              if (getTypeBound(i).type().sameStructure(type.getTypeBound(j).type())) {
                found = true;
              }
            }
            if (!found) {
              return false;
            }
          }
          return true;
        } else if (t.usesTypeVariable()) {
          if (getNumTypeBound() > 0) {
            return getTypeBound(0).type().sameStructure(t);
          }
        }
        return false;
      }
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
        for (int i = 0; i < getNumTypeBound(); i++) {
          if (type.subtype(getTypeBound(i).type())) {
            return true;
          }
        }
        return false;
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
    boolean subtype_TypeDecl_value = type.supertypeTypeVariable(this);
    return subtype_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:364
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:364")
  public boolean supertypeTypeVariable(TypeVariable type) {
    {
        if (type == this) {
          return true;
        }
        for (int i = 0; i < getNumTypeBound(); i++) {
          boolean foundSubtype = false;
          for (int j = 0; !foundSubtype && j < type.getNumTypeBound(); j++) {
            if (type.getTypeBound(j).type().subtype(getTypeBound(i).type())) {
              foundSubtype = true;
              break;
            }
          }
          if (!foundSubtype) {
            return false;
          }
        }
        return true;
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
    boolean supertypeClassDecl_ClassDecl_value = false;
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
    boolean supertypeInterfaceDecl_InterfaceDecl_value = false;
    return supertypeInterfaceDecl_InterfaceDecl_value;
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
    boolean instanceOf_TypeDecl_value = subtype(type);
    if (state().inCircle()) {
      instanceOf_TypeDecl_values.put(_parameters, instanceOf_TypeDecl_value);
      instanceOf_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      instanceOf_TypeDecl_values.put(_parameters, instanceOf_TypeDecl_value);
      instanceOf_TypeDecl_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return instanceOf_TypeDecl_value;
  }
  /**
   * Compute the first bound of this type variable.
   * Returns the Object type if this type variable has no bound.
   * @attribute syn
   * @aspect MethodSignature15
   * @declaredat /home/olivier/projects/extendj/java5/frontend/MethodSignature.jrag:532
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature15", declaredAt="/home/olivier/projects/extendj/java5/frontend/MethodSignature.jrag:532")
  public TypeDecl firstBound() {
    {
        if (getNumTypeBound() == 0) {
          return typeObject();
        } else if (getNumTypeBound() == 1) {
          return getTypeBound(0).type();
        } else {
          return lubType();
        }
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
    boolean isReifiable_value = false;
    return isReifiable_value;
  }
  /** @apilevel internal */
  private void sameType_TypeVariable_reset() {
    sameType_TypeVariable_computed = null;
    sameType_TypeVariable_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map sameType_TypeVariable_values;
  /** @apilevel internal */
  protected java.util.Map sameType_TypeVariable_computed;
  /**
   * @attribute syn
   * @aspect FunctionalInterface
   * @declaredat /home/olivier/projects/extendj/java8/frontend/FunctionalInterface.jrag:126
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="FunctionalInterface", declaredAt="/home/olivier/projects/extendj/java8/frontend/FunctionalInterface.jrag:126")
  public boolean sameType(TypeVariable tv2) {
    Object _parameters = tv2;
    if (sameType_TypeVariable_computed == null) sameType_TypeVariable_computed = new java.util.HashMap(4);
    if (sameType_TypeVariable_values == null) sameType_TypeVariable_values = new java.util.HashMap(4);
    ASTState state = state();
    if (sameType_TypeVariable_values.containsKey(_parameters)
        && sameType_TypeVariable_computed.containsKey(_parameters)
        && (sameType_TypeVariable_computed.get(_parameters) == ASTState.NON_CYCLE || sameType_TypeVariable_computed.get(_parameters) == state().cycle())) {
      return (Boolean) sameType_TypeVariable_values.get(_parameters);
    }
    boolean sameType_TypeVariable_value = sameType_compute(tv2);
    if (state().inCircle()) {
      sameType_TypeVariable_values.put(_parameters, sameType_TypeVariable_value);
      sameType_TypeVariable_computed.put(_parameters, state().cycle());
    
    } else {
      sameType_TypeVariable_values.put(_parameters, sameType_TypeVariable_value);
      sameType_TypeVariable_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return sameType_TypeVariable_value;
  }
  /** @apilevel internal */
  private boolean sameType_compute(TypeVariable tv2) {
      TypeVariable tv1 = this;
      if (tv1.getNumTypeBound() != tv2.getNumTypeBound()) {
        return false;
      }
  
      // The bounds have to be the same in the way that a bound
      // that exists in type variable tv1 must exist exactly the same
      // number of times in tv2, but the order doesn't matter.
  
      boolean[] checkedBound = new boolean[tv1.getNumTypeBound()];
  
      for (int j = 0; j < tv1.getNumTypeBound(); j++) {
        boolean found = false;
        for (int k = 0; k < tv2.getNumTypeBound(); k++) {
          if (checkedBound[k]) {
            continue;
          }
          Access a1 = tv1.getTypeBound(j);
          Access a2 = tv2.getTypeBound(k);
  
          if (a1.sameType(a2)) {
            checkedBound[k] = true;
            found = true;
            break;
          }
        }
        if (!found) {
          return false;
        }
      }
      return true;
    }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:68
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:68")
  public boolean strictSupertypeWildcard(WildcardType type) {
    boolean strictSupertypeWildcard_WildcardType_value = true;
    return strictSupertypeWildcard_WildcardType_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:79
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:79")
  public boolean strictSupertypeWildcardSuper(WildcardSuperType type) {
    boolean strictSupertypeWildcardSuper_WildcardSuperType_value = type.superType().strictSubtype(this);
    return strictSupertypeWildcardSuper_WildcardSuperType_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:413
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:413")
  public boolean strictSupertypeArrayDecl(ArrayDecl type) {
    boolean strictSupertypeArrayDecl_ArrayDecl_value = false;
    return strictSupertypeArrayDecl_ArrayDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:363
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:363")
  public boolean strictSubtype(TypeDecl type) {
    boolean strictSubtype_TypeDecl_value = type.strictSupertypeTypeVariable(this);
    return strictSubtype_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:281
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:281")
  public boolean strictSupertypeTypeVariable(TypeVariable type) {
    {
        if (typeVarInMethod() && type.typeVarInMethod()
            && genericMethodLevel() == type.genericMethodLevel()) {
          if (typeVarPosition() == type.typeVarPosition() || this == type) {
            return true;
          }
        } else {
          if (this == type) {
            return true;
          }
        }
        for (int i = 0; i < type.getNumTypeBound(); i++) {
          if (type.getTypeBound(i).type().strictSubtype(this)) {
            return true;
          }
        }
        return false;
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
    {
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:397
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:397")
  public boolean strictSupertypeInterfaceDecl(InterfaceDecl type) {
    {
        return false;
      }
  }
  /**
   * Returns true if this type variable contains a mention of any of the
   * given type variables.
   * @attribute syn
   * @aspect LambdaParametersInference
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TypeCheck.jrag:645
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LambdaParametersInference", declaredAt="/home/olivier/projects/extendj/java8/frontend/TypeCheck.jrag:645")
  public boolean mentionsTypeVariable(List<TypeVariable> vars) {
    {
        for (Access bound : getTypeBoundList()) {
          for (TypeVariable var : vars) {
            if (bound.mentionsTypeVariable(var)) {
              return true;
            }
          }
        }
        return false;
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
    typeDescriptor_value = erasure().typeDescriptor();
    if (state().inCircle()) {
      typeDescriptor_computed = state().cycle();
    
    } else {
      typeDescriptor_computed = ASTState.NON_CYCLE;
    
    }
    return typeDescriptor_value;
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
    needsSignatureAttribute_value = true;
    if (state().inCircle()) {
      needsSignatureAttribute_computed = state().cycle();
    
    } else {
      needsSignatureAttribute_computed = ASTState.NON_CYCLE;
    
    }
    return needsSignatureAttribute_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:319
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:319")
  public String formalTypeParameter() {
    {
        StringBuilder buf = new StringBuilder();
        buf.append(name());
        buf.append(":");
        if (getNumTypeBound() > 0) {
          if (getTypeBound(0).type().isClassDecl()) {
            buf.append(getTypeBound(0).type().fieldTypeSignature());
          } else {
            buf.append(":" + getTypeBound(0).type().fieldTypeSignature());
          }
          for (int i = 1; i < getNumTypeBound(); i++) {
            buf.append(":" + getTypeBound(i).type().fieldTypeSignature());
          }
        }
        return buf.toString();
      }
  }
  /** @apilevel internal */
  private void fieldTypeSignature_reset() {
    fieldTypeSignature_computed = null;
    fieldTypeSignature_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle fieldTypeSignature_computed = null;

  /** @apilevel internal */
  protected String fieldTypeSignature_value;

  /**
   * @attribute syn
   * @aspect GenericsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:336
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:336")
  public String fieldTypeSignature() {
    ASTState state = state();
    if (fieldTypeSignature_computed == ASTState.NON_CYCLE || fieldTypeSignature_computed == state().cycle()) {
      return fieldTypeSignature_value;
    }
    fieldTypeSignature_value = classTypeSignature();
    if (state().inCircle()) {
      fieldTypeSignature_computed = state().cycle();
    
    } else {
      fieldTypeSignature_computed = ASTState.NON_CYCLE;
    
    }
    return fieldTypeSignature_value;
  }
  /** @apilevel internal */
  private void classTypeSignature_reset() {
    classTypeSignature_computed = null;
    classTypeSignature_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle classTypeSignature_computed = null;

  /** @apilevel internal */
  protected String classTypeSignature_value;

  /**
   * @attribute syn
   * @aspect GenericsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:345
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:345")
  public String classTypeSignature() {
    ASTState state = state();
    if (classTypeSignature_computed == ASTState.NON_CYCLE || classTypeSignature_computed == state().cycle()) {
      return classTypeSignature_value;
    }
    classTypeSignature_value = "T" + name() + ";";
    if (state().inCircle()) {
      classTypeSignature_computed = state().cycle();
    
    } else {
      classTypeSignature_computed = ASTState.NON_CYCLE;
    
    }
    return classTypeSignature_value;
  }
  /** @apilevel internal */
  private void classBound_reset() {
    classBound_computed = null;
    classBound_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle classBound_computed = null;

  /** @apilevel internal */
  protected String classBound_value;

  /**
   * @attribute syn
   * @aspect GenericsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:406
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:406")
  public String classBound() {
    ASTState state = state();
    if (classBound_computed == ASTState.NON_CYCLE || classBound_computed == state().cycle()) {
      return classBound_value;
    }
    classBound_value = classBound_compute();
    if (state().inCircle()) {
      classBound_computed = state().cycle();
    
    } else {
      classBound_computed = ASTState.NON_CYCLE;
    
    }
    return classBound_value;
  }
  /** @apilevel internal */
  private String classBound_compute() {
      if (getNumTypeBound() > 0) {
        return getTypeBound(0).type().fieldTypeSignature();
      }
      return "";
    }
  /** @apilevel internal */
  private void interfaceBounds_reset() {
    interfaceBounds_computed = null;
    interfaceBounds_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle interfaceBounds_computed = null;

  /** @apilevel internal */
  protected String interfaceBounds_value;

  /**
   * @attribute syn
   * @aspect GenericsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:413
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:413")
  public String interfaceBounds() {
    ASTState state = state();
    if (interfaceBounds_computed == ASTState.NON_CYCLE || interfaceBounds_computed == state().cycle()) {
      return interfaceBounds_value;
    }
    interfaceBounds_value = interfaceBounds_compute();
    if (state().inCircle()) {
      interfaceBounds_computed = state().cycle();
    
    } else {
      interfaceBounds_computed = ASTState.NON_CYCLE;
    
    }
    return interfaceBounds_value;
  }
  /** @apilevel internal */
  private String interfaceBounds_compute() {
      StringBuilder buf = new StringBuilder();
      for (int i = 1; i < getNumTypeBound(); ++i) {
        buf.append(":");
        buf.append(getTypeBound(i).type().fieldTypeSignature());
      }
      return buf.toString();
    }
  /**
   * Returns a string describing the context of a type variable. If the type
   * variable is part of a type declaration then the context string is the
   * qualified name of the type. If the type variable is declared in a method
   * or constructor declaration then the context string is the qualified
   * signature of the method or constructor.
   * @attribute inh
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:852
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:852")
  public String typeVariableContext() {
    String typeVariableContext_value = getParent().Define_typeVariableContext(this, null);
    return typeVariableContext_value;
  }
  /**
   * @attribute inh
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1290
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:1290")
  public TypeDecl typeObject() {
    TypeDecl typeObject_value = getParent().Define_typeObject(this, null);
    return typeObject_value;
  }
  /**
   * @attribute inh
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1300
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:1300")
  public TypeDecl typeNull() {
    TypeDecl typeNull_value = getParent().Define_typeNull(this, null);
    return typeNull_value;
  }
  /**
   * @attribute inh
   * @aspect TypeVariablePositions
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TypeVariablePositions.jrag:29
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeVariablePositions", declaredAt="/home/olivier/projects/extendj/java8/frontend/TypeVariablePositions.jrag:29")
  public int typeVarPosition() {
    ASTState state = state();
    if (typeVarPosition_computed == ASTState.NON_CYCLE || typeVarPosition_computed == state().cycle()) {
      return typeVarPosition_value;
    }
    typeVarPosition_value = getParent().Define_typeVarPosition(this, null);
    if (state().inCircle()) {
      typeVarPosition_computed = state().cycle();
    
    } else {
      typeVarPosition_computed = ASTState.NON_CYCLE;
    
    }
    return typeVarPosition_value;
  }
  /** @apilevel internal */
  private void typeVarPosition_reset() {
    typeVarPosition_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeVarPosition_computed = null;

  /** @apilevel internal */
  protected int typeVarPosition_value;

  /**
   * @attribute inh
   * @aspect TypeVariablePositions
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TypeVariablePositions.jrag:30
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeVariablePositions", declaredAt="/home/olivier/projects/extendj/java8/frontend/TypeVariablePositions.jrag:30")
  public int genericMethodLevel() {
    ASTState state = state();
    if (genericMethodLevel_computed == ASTState.NON_CYCLE || genericMethodLevel_computed == state().cycle()) {
      return genericMethodLevel_value;
    }
    genericMethodLevel_value = getParent().Define_genericMethodLevel(this, null);
    if (state().inCircle()) {
      genericMethodLevel_computed = state().cycle();
    
    } else {
      genericMethodLevel_computed = ASTState.NON_CYCLE;
    
    }
    return genericMethodLevel_value;
  }
  /** @apilevel internal */
  private void genericMethodLevel_reset() {
    genericMethodLevel_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle genericMethodLevel_computed = null;

  /** @apilevel internal */
  protected int genericMethodLevel_value;

  /**
   * @attribute inh
   * @aspect TypeVariablePositions
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TypeVariablePositions.jrag:32
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeVariablePositions", declaredAt="/home/olivier/projects/extendj/java8/frontend/TypeVariablePositions.jrag:32")
  public boolean typeVarInMethod() {
    ASTState state = state();
    if (typeVarInMethod_computed == ASTState.NON_CYCLE || typeVarInMethod_computed == state().cycle()) {
      return typeVarInMethod_value;
    }
    typeVarInMethod_value = getParent().Define_typeVarInMethod(this, null);
    if (state().inCircle()) {
      typeVarInMethod_computed = state().cycle();
    
    } else {
      typeVarInMethod_computed = ASTState.NON_CYCLE;
    
    }
    return typeVarInMethod_value;
  }
  /** @apilevel internal */
  private void typeVarInMethod_reset() {
    typeVarInMethod_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeVarInMethod_computed = null;

  /** @apilevel internal */
  protected boolean typeVarInMethod_value;

  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getTypeBoundListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/GenericTypeVariables.jrag:35
      int childIndex = _callerNode.getIndexOfChild(_childNode);
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
  /** @apilevel internal */
  public ASTNode rewriteTo() {
    // Declared at /home/olivier/projects/extendj/java5/frontend/GenericTypeVariables.jrag:40
    if (getNumTypeBound() == 0) {
      return rewriteRule0();
    }
    return super.rewriteTo();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericTypeVariables.jrag:40
   * @apilevel internal
   */
  private TypeVariable rewriteRule0() {
{
      return new TypeVariable(
          new Modifiers(),
          getID(),
          new List<BodyDecl>(),
          new List<Access>(new TypeAccess("java.lang", "Object")));
    }  }
  /** @apilevel internal */
  public boolean canRewrite() {
    // Declared at /home/olivier/projects/extendj/java5/frontend/GenericTypeVariables.jrag:40
    if (getNumTypeBound() == 0) {
      return true;
    }
    return false;
  }
  /** @apilevel internal */
  protected void collect_contributors_CompilationUnit_problems(CompilationUnit _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat /home/olivier/projects/extendj/java5/frontend/GenericTypeVariables.jrag:99
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
    for (Problem value : typeProblems()) {
      collection.add(value);
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
