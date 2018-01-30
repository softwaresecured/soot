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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:141
 * @astdecl NumericType : PrimitiveType;
 * @production NumericType : {@link PrimitiveType};

 */
public abstract class NumericType extends PrimitiveType implements Cloneable {
  /**
   * @declaredat ASTNode:1
   */
  public NumericType() {
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
    setChild(new Opt(), 1);
    setChild(new List(), 2);
  }
  /**
   * @declaredat ASTNode:15
   */
  @ASTNodeAnnotation.Constructor(
    name = {"Modifiers", "ID", "SuperClass", "BodyDecl"},
    type = {"Modifiers", "String", "Opt<Access>", "List<BodyDecl>"},
    kind = {"Child", "Token", "Opt", "List"}
  )
  public NumericType(Modifiers p0, String p1, Opt<Access> p2, List<BodyDecl> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @declaredat ASTNode:26
   */
  public NumericType(Modifiers p0, beaver.Symbol p1, Opt<Access> p2, List<BodyDecl> p3) {
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
    unaryNumericPromotion_reset();
    binaryNumericPromotion_TypeDecl_reset();
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
  public NumericType clone() throws CloneNotSupportedException {
    NumericType node = (NumericType) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:64
   */
  @Deprecated
  public abstract NumericType fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:72
   */
  public abstract NumericType treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:80
   */
  public abstract NumericType treeCopy();
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
   * @aspect NumericPromotion
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:167
   */
  private TypeDecl refined_NumericPromotion_NumericType_binaryNumericPromotion_TypeDecl(TypeDecl type)
{
    if (!type.isNumericType()) {
      return unknownType();
    }
     return unaryNumericPromotion().instanceOf(type) ? type : unaryNumericPromotion();
  }
  /**
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:385
   */
  private Value refined_EmitJimple_NumericType_emitCastTo_Body_Value_TypeDecl_ASTNode(Body b, Value v, TypeDecl type, ASTNode location)
{
    // `isNumericType` == a numeric primitive type or `UnknownType` or boxed numeric type
    // NOTE: not all `is<Kind>{Type}` follow this convention. You must check each one.
    assert type.isNumericType() || type.isObject();

    // primitive -> primitive cast
    // `isPrimitiveType`  == a primitive type  or `UnknownType`
    // NOTE:  This is *NOT* the same convention as `isNumericType`!
    //        `isPrimitiveType` excludes boxed typed, unlike `isNumericType`.
    if (type.isPrimitiveType()) {
      // JVM only has a `{lfd}2i` for integral primitive casts, thus Soot implicitly
      // assumes it'll never see anything weird like a `l2s` cast.
      // Have to cast to `int` first, then to target type.
      //
      // `isPrimitiveType`  == a primitive type  or `UnknownType`
      // NOTE:  This is *NOT* the same convention as `isNumericType`!
      //        `isPrimitiveType` excludes boxed typed, unlike `isNumericType`.
      if (!isInt() && type.isIntegralType() && !(type.isLong() || type.isInt())) {
        TypeDecl  intType = lookupType("int").singletonValue();
        Value     valInt  = emitCastTo(b, v, intType, location);
        return intType.emitCastTo(b, valInt, type, location);
      }

      return b.newCastExpr(v, type.sootType(), location);
    }

    return super.emitCastTo(b, v, type, location);
  }
  /** @apilevel internal */
  private void unaryNumericPromotion_reset() {
    unaryNumericPromotion_computed = null;
    unaryNumericPromotion_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle unaryNumericPromotion_computed = null;

  /** @apilevel internal */
  protected TypeDecl unaryNumericPromotion_value;

  /**
   * @attribute syn
   * @aspect NumericPromotion
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:159
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NumericPromotion", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:159")
  public TypeDecl unaryNumericPromotion() {
    ASTState state = state();
    if (unaryNumericPromotion_computed == ASTState.NON_CYCLE || unaryNumericPromotion_computed == state().cycle()) {
      return unaryNumericPromotion_value;
    }
    unaryNumericPromotion_value = this;
    if (state().inCircle()) {
      unaryNumericPromotion_computed = state().cycle();
    
    } else {
      unaryNumericPromotion_computed = ASTState.NON_CYCLE;
    
    }
    return unaryNumericPromotion_value;
  }
  /** @apilevel internal */
  private void binaryNumericPromotion_TypeDecl_reset() {
    binaryNumericPromotion_TypeDecl_computed = null;
    binaryNumericPromotion_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map binaryNumericPromotion_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map binaryNumericPromotion_TypeDecl_computed;
  /**
   * @attribute syn
   * @aspect NumericPromotion
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:167
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NumericPromotion", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:167")
  public TypeDecl binaryNumericPromotion(TypeDecl type) {
    Object _parameters = type;
    if (binaryNumericPromotion_TypeDecl_computed == null) binaryNumericPromotion_TypeDecl_computed = new java.util.HashMap(4);
    if (binaryNumericPromotion_TypeDecl_values == null) binaryNumericPromotion_TypeDecl_values = new java.util.HashMap(4);
    ASTState state = state();
    if (binaryNumericPromotion_TypeDecl_values.containsKey(_parameters)
        && binaryNumericPromotion_TypeDecl_computed.containsKey(_parameters)
        && (binaryNumericPromotion_TypeDecl_computed.get(_parameters) == ASTState.NON_CYCLE || binaryNumericPromotion_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (TypeDecl) binaryNumericPromotion_TypeDecl_values.get(_parameters);
    }
    TypeDecl binaryNumericPromotion_TypeDecl_value = binaryNumericPromotion_compute(type);
    if (state().inCircle()) {
      binaryNumericPromotion_TypeDecl_values.put(_parameters, binaryNumericPromotion_TypeDecl_value);
      binaryNumericPromotion_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      binaryNumericPromotion_TypeDecl_values.put(_parameters, binaryNumericPromotion_TypeDecl_value);
      binaryNumericPromotion_TypeDecl_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return binaryNumericPromotion_TypeDecl_value;
  }
  /** @apilevel internal */
  private TypeDecl binaryNumericPromotion_compute(TypeDecl type) {
      if (type.isReferenceType()) {
        return refined_NumericPromotion_NumericType_binaryNumericPromotion_TypeDecl(type.unboxed());
      } else {
        return refined_NumericPromotion_NumericType_binaryNumericPromotion_TypeDecl(type);
      }
    }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:186
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:186")
  public boolean isNumericType() {
    boolean isNumericType_value = true;
    return isNumericType_value;
  }
  /**
   * @attribute syn
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:378
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EmitJimple", declaredAt="/home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:378")
  public Value emitCastTo(Body b, Value v, TypeDecl type, ASTNode location) {
    {
        // `isNumericType` == a numeric primitive type or `UnknownType` or boxed numeric type
        // NOTE: not all `is<Kind>{Type}` follow this convention. You must check each one.
        assert type.isNumericType() || type.isObject();
    
        // primitive -> boxed numeric cast; convert to unbox of new type, then box it
        if (type.isNumericType() && !type.isPrimitiveType()) {
          PrimitiveType typeUnboxed = (PrimitiveType)type.unboxed();
          Value         valUnboxed  = emitCastTo(b, v, typeUnboxed, location);
          return typeUnboxed.emitBoxingOperation(b, valUnboxed, location);
        }
    
        return refined_EmitJimple_NumericType_emitCastTo_Body_Value_TypeDecl_ASTNode(b, v, type, location);
      }
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
