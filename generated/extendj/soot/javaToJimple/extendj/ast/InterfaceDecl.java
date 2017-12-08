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
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:155
 * @production InterfaceDecl : {@link ReferenceType} ::= <span class="component">{@link Modifiers}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">SuperInterface:{@link Access}*</span> <span class="component">{@link BodyDecl}*</span>;

 */
public class InterfaceDecl extends ReferenceType implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:402
   */
  public void prettyPrint(PrettyPrinter out) {
    if (hasDocComment()) {
      out.print(docComment());
    }
    if (!out.isNewLine()) {
      out.println();
    }
    out.print(getModifiers());
    out.print("interface ");
    out.print(getID());
    if (hasSuperInterface()) {
      out.print(" extends ");
      out.join(getSuperInterfaceList(), new PrettyPrinter.Joiner() {
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
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:310
   */
  public TypeDecl makeGeneric(Signatures.ClassSignature s) {
    if (s.hasFormalTypeParameters()) {
      ASTNode node = getParent();
      int index = node.getIndexOfChild(this);
      node.setChild(
          new GenericInterfaceDecl(
              getModifiersNoTransform(),
              getID(),
              s.hasSuperinterfaceSignature()
                  ? s.superinterfaceSignature()
                  : getSuperInterfaceListNoTransform(),
              getBodyDeclListNoTransform(),
              s.typeParameters()),
          index);
      return (TypeDecl) node.getChildNoTransform(index);
    } else {
      if (s.hasSuperinterfaceSignature()) {
        setSuperInterfaceList(s.superinterfaceSignature());
      }
      return this;
    }
  }
  /**
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:137
   */
  protected void jimplify1_setupSuperclass(SootClass sc)
  { sc.setSuperclass(typeObject().getSootClassDecl()); }
  /**
   * @declaredat ASTNode:1
   */
  public InterfaceDecl() {
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
  public InterfaceDecl(Modifiers p0, String p1, List<Access> p2, List<BodyDecl> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @declaredat ASTNode:21
   */
  public InterfaceDecl(Modifiers p0, beaver.Symbol p1, List<Access> p2, List<BodyDecl> p3) {
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
    methodsSignatureMap_reset();
    ancestorMethods_String_reset();
    memberFieldsMap_reset();
    memberFields_String_reset();
    isStatic_reset();
    castingConversionTo_TypeDecl_reset();
    instanceOf_TypeDecl_reset();
    superInterfaces_reset();
    isCircular_reset();
    typeDescriptor_reset();
    iterableElementType_reset();
    erasedAncestorMethodsMap_reset();
    implementedInterfaces_reset();
    subtype_TypeDecl_reset();
    hasAnnotationFunctionalInterface_reset();
    hasFunctionDescriptor_reset();
    functionDescriptor_reset();
    isFunctionalInterface_reset();
    isFunctional_reset();
    collectAbstractMethods_reset();
    strictSubtype_TypeDecl_reset();
    hasOverridingMethodInSuper_MethodDecl_reset();
    sootClass_reset();
    flags_reset();
    bridgeCandidates_String_reset();
    needsSignatureAttribute_reset();
    classSignature_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:69
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:73
   */
  public InterfaceDecl clone() throws CloneNotSupportedException {
    InterfaceDecl node = (InterfaceDecl) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:78
   */
  public InterfaceDecl copy() {
    try {
      InterfaceDecl node = (InterfaceDecl) clone();
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
   * @declaredat ASTNode:97
   */
  @Deprecated
  public InterfaceDecl fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:107
   */
  public InterfaceDecl treeCopyNoTransform() {
    InterfaceDecl tree = (InterfaceDecl) copy();
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
   * @declaredat ASTNode:127
   */
  public InterfaceDecl treeCopy() {
    InterfaceDecl tree = (InterfaceDecl) copy();
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
   * @declaredat ASTNode:141
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((InterfaceDecl) node).tokenString_ID);    
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
   * Replaces the SuperInterface list.
   * @param list The new list node to be used as the SuperInterface list.
   * @apilevel high-level
   */
  public void setSuperInterfaceList(List<Access> list) {
    setChild(list, 1);
  }
  /**
   * Retrieves the number of children in the SuperInterface list.
   * @return Number of children in the SuperInterface list.
   * @apilevel high-level
   */
  public int getNumSuperInterface() {
    return getSuperInterfaceList().getNumChild();
  }
  /**
   * Retrieves the number of children in the SuperInterface list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the SuperInterface list.
   * @apilevel low-level
   */
  public int getNumSuperInterfaceNoTransform() {
    return getSuperInterfaceListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the SuperInterface list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the SuperInterface list.
   * @apilevel high-level
   */
  public Access getSuperInterface(int i) {
    return (Access) getSuperInterfaceList().getChild(i);
  }
  /**
   * Check whether the SuperInterface list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasSuperInterface() {
    return getSuperInterfaceList().getNumChild() != 0;
  }
  /**
   * Append an element to the SuperInterface list.
   * @param node The element to append to the SuperInterface list.
   * @apilevel high-level
   */
  public void addSuperInterface(Access node) {
    List<Access> list = (parent == null) ? getSuperInterfaceListNoTransform() : getSuperInterfaceList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addSuperInterfaceNoTransform(Access node) {
    List<Access> list = getSuperInterfaceListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the SuperInterface list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setSuperInterface(Access node, int i) {
    List<Access> list = getSuperInterfaceList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the SuperInterface list.
   * @return The node representing the SuperInterface list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="SuperInterface")
  public List<Access> getSuperInterfaceList() {
    List<Access> list = (List<Access>) getChild(1);
    return list;
  }
  /**
   * Retrieves the SuperInterface list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the SuperInterface list.
   * @apilevel low-level
   */
  public List<Access> getSuperInterfaceListNoTransform() {
    return (List<Access>) getChildNoTransform(1);
  }
  /**
   * @return the element at index {@code i} in the SuperInterface list without
   * triggering rewrites.
   */
  public Access getSuperInterfaceNoTransform(int i) {
    return (Access) getSuperInterfaceListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the SuperInterface list.
   * @return The node representing the SuperInterface list.
   * @apilevel high-level
   */
  public List<Access> getSuperInterfaces() {
    return getSuperInterfaceList();
  }
  /**
   * Retrieves the SuperInterface list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the SuperInterface list.
   * @apilevel low-level
   */
  public List<Access> getSuperInterfacesNoTransform() {
    return getSuperInterfaceListNoTransform();
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
   * @aspect Generics
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:141
   */
  private boolean refined_Generics_InterfaceDecl_castingConversionTo_TypeDecl(TypeDecl type)
{
    TypeDecl S = this;
    TypeDecl T = type;
    if (T.isArrayDecl()) {
      return T.instanceOf(S);
    } else if (T.isReferenceType() && !T.isFinal()) {
      return true;
    } else {
      return T.instanceOf(S);
    }
  }
  /**
   * @attribute syn
   * @aspect AccessControl
   * @declaredat /home/olivier/projects/extendj/java4/frontend/AccessControl.jrag:214
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessControl", declaredAt="/home/olivier/projects/extendj/java4/frontend/AccessControl.jrag:214")
  public Collection<Problem> accessControlProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
    
        if (!isCircular()) {
          // 9.1.2
          Collection<TypeDecl> interfaces = new HashSet<TypeDecl>();
          for (Access access : getSuperInterfaceList()) {
            TypeDecl decl = access.type();
            if (!decl.isInterfaceDecl() && !decl.isUnknown()) {
              problems.add(errorf("interface %s can not extend non interface type %s",
                  fullName(), decl.fullName()));
            }
            if (!decl.isCircular() && !decl.accessibleFrom(this)) {
              problems.add(errorf("interface %s can not extend non accessible type %s",
                  fullName(), decl.fullName()));
            }
            if (interfaces.contains(decl)) {
              problems.add(errorf(
                  "extended interface %s is mentionened multiple times in extends clause",
                  decl.fullName()));
            }
            interfaces.add(decl);
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
    Collection<ConstructorDecl> lookupSuperConstructor_value = typeObject().constructors();
    return lookupSuperConstructor_value;
  }
  /** @apilevel internal */
  private void methodsSignatureMap_reset() {
    methodsSignatureMap_computed = null;
    methodsSignatureMap_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle methodsSignatureMap_computed = null;

  /** @apilevel internal */
  protected Map<String, SimpleSet<MethodDecl>> methodsSignatureMap_value;

  /**
   * Map method signatures to sets of method declarations
   * for this type.
   * 
   * <p>Includes all supertype method declarations.
   * @attribute syn
   * @aspect MemberMethods
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:679
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MemberMethods", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:679")
  public Map<String, SimpleSet<MethodDecl>> methodsSignatureMap() {
    ASTNode$State state = state();
    if (methodsSignatureMap_computed == ASTNode$State.NON_CYCLE || methodsSignatureMap_computed == state().cycle()) {
      return methodsSignatureMap_value;
    }
    methodsSignatureMap_value = methodsSignatureMap_compute();
    if (state().inCircle()) {
      methodsSignatureMap_computed = state().cycle();
    
    } else {
      methodsSignatureMap_computed = ASTNode$State.NON_CYCLE;
    
    }
    return methodsSignatureMap_value;
  }
  /** @apilevel internal */
  private Map<String, SimpleSet<MethodDecl>> methodsSignatureMap_compute() {
      Map<String, SimpleSet<MethodDecl>> localMap = localMethodsSignatureMap();
      Map<String, SimpleSet<MethodDecl>> map = new HashMap<String, SimpleSet<MethodDecl>>(localMap);
      for (Iterator<MethodDecl> iter = interfacesMethodsIterator(); iter.hasNext(); ) {
        MethodDecl m = iter.next();
        if (!m.isStatic() && m.accessibleFrom(this)
            && !localMap.containsKey(m.signature())
            && !hasOverridingMethodInSuper(m)) {
          putSimpleSetElement(map, m.signature(), m);
        }
      }
      for (Iterator<MethodDecl> iter = typeObject().methodsIterator(); iter.hasNext(); ) {
        MethodDecl m = iter.next();
        if (m.isPublic() && !map.containsKey(m.signature())) {
          putSimpleSetElement(map, m.signature(), m);
        }
      }
      return map;
    }
  /** @apilevel internal */
  private void ancestorMethods_String_reset() {
    ancestorMethods_String_computed = new java.util.HashMap(4);
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:761
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AncestorMethods", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:761")
  public SimpleSet<MethodDecl> ancestorMethods(String signature) {
    Object _parameters = signature;
    if (ancestorMethods_String_computed == null) ancestorMethods_String_computed = new java.util.HashMap(4);
    if (ancestorMethods_String_values == null) ancestorMethods_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (ancestorMethods_String_values.containsKey(_parameters) && ancestorMethods_String_computed != null
        && ancestorMethods_String_computed.containsKey(_parameters)
        && (ancestorMethods_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || ancestorMethods_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<MethodDecl>) ancestorMethods_String_values.get(_parameters);
    }
    SimpleSet<MethodDecl> ancestorMethods_String_value = ancestorMethods_compute(signature);
    if (state().inCircle()) {
      ancestorMethods_String_values.put(_parameters, ancestorMethods_String_value);
      ancestorMethods_String_computed.put(_parameters, state().cycle());
    
    } else {
      ancestorMethods_String_values.put(_parameters, ancestorMethods_String_value);
      ancestorMethods_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return ancestorMethods_String_value;
  }
  /** @apilevel internal */
  private SimpleSet<MethodDecl> ancestorMethods_compute(String signature) {
      SimpleSet<MethodDecl> result = emptySet();
      for (Iterator<TypeDecl> outerIter = interfacesIterator(); outerIter.hasNext(); ) {
        TypeDecl typeDecl = outerIter.next();
        for (MethodDecl m : typeDecl.methodsSignature(signature)) {
          result = result.add(m);
        }
      }
      if (!interfacesIterator().hasNext()) {
        for (MethodDecl m : typeObject().methodsSignature(signature)) {
          if (m.isPublic()) {
            result = result.add(m);
          }
        }
      }
      return result;
    }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:674
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:674")
  public SimpleSet<TypeDecl> memberTypes(String name) {
    {
        SimpleSet<TypeDecl> result = localTypeDecls(name);
        if (!result.isEmpty()) {
          return result;
        }
        for (Iterator<TypeDecl> outerIter = interfacesIterator(); outerIter.hasNext(); ) {
          TypeDecl typeDecl = outerIter.next();
          for (TypeDecl decl : typeDecl.memberTypes(name)) {
            if (!decl.isPrivate()) {
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
  protected ASTNode$State.Cycle memberFieldsMap_computed = null;

  /** @apilevel internal */
  protected Map<String, SimpleSet<Variable>> memberFieldsMap_value;

  /**
   * @attribute syn
   * @aspect Fields
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:419
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Fields", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:419")
  public Map<String, SimpleSet<Variable>> memberFieldsMap() {
    ASTNode$State state = state();
    if (memberFieldsMap_computed == ASTNode$State.NON_CYCLE || memberFieldsMap_computed == state().cycle()) {
      return memberFieldsMap_value;
    }
    memberFieldsMap_value = memberFieldsMap_compute();
    if (state().inCircle()) {
      memberFieldsMap_computed = state().cycle();
    
    } else {
      memberFieldsMap_computed = ASTNode$State.NON_CYCLE;
    
    }
    return memberFieldsMap_value;
  }
  /** @apilevel internal */
  private Map<String, SimpleSet<Variable>> memberFieldsMap_compute() {
      Map<String, SimpleSet<Variable>> map =
          new HashMap<String, SimpleSet<Variable>>(localFieldsMap());
      Iterator<TypeDecl> outerIter = interfacesIterator();
      while (outerIter.hasNext()) {
        TypeDecl typeDecl = outerIter.next();
        Iterator<Variable> iter = typeDecl.fieldsIterator();
        while (iter.hasNext()) {
          Variable f = iter.next();
          if (f.accessibleFrom(this) && !f.isPrivate() && !localFieldsMap().containsKey(f.name())) {
            putSimpleSetElement(map, f.name(), f);
          }
        }
      }
      return map;
    }
  /** @apilevel internal */
  private void memberFields_String_reset() {
    memberFields_String_computed = new java.util.HashMap(4);
    memberFields_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map memberFields_String_values;
  /** @apilevel internal */
  protected java.util.Map memberFields_String_computed;
  /**
   * @attribute syn
   * @aspect Fields
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:492
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Fields", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:492")
  public SimpleSet<Variable> memberFields(String name) {
    Object _parameters = name;
    if (memberFields_String_computed == null) memberFields_String_computed = new java.util.HashMap(4);
    if (memberFields_String_values == null) memberFields_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (memberFields_String_values.containsKey(_parameters) && memberFields_String_computed != null
        && memberFields_String_computed.containsKey(_parameters)
        && (memberFields_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || memberFields_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<Variable>) memberFields_String_values.get(_parameters);
    }
    SimpleSet<Variable> memberFields_String_value = memberFields_compute(name);
    if (state().inCircle()) {
      memberFields_String_values.put(_parameters, memberFields_String_value);
      memberFields_String_computed.put(_parameters, state().cycle());
    
    } else {
      memberFields_String_values.put(_parameters, memberFields_String_value);
      memberFields_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return memberFields_String_value;
  }
  /** @apilevel internal */
  private SimpleSet<Variable> memberFields_compute(String name) {
      SimpleSet<Variable> fields = localFields(name);
      if (!fields.isEmpty()) {
        return fields;
      }
      for (Iterator<TypeDecl> outerIter = interfacesIterator(); outerIter.hasNext(); ) {
        TypeDecl typeDecl = outerIter.next();
        Iterator<Variable> iter = typeDecl.memberFields(name).iterator();
        while (iter.hasNext()) {
          Variable f = iter.next();
          if (f.accessibleFrom(this) && !f.isPrivate()) {
            fields = fields.add(f);
          }
        }
      }
      return fields;
    }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:237
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="/home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:237")
  public boolean isAbstract() {
    boolean isAbstract_value = true;
    return isAbstract_value;
  }
  /** @apilevel internal */
  private void isStatic_reset() {
    isStatic_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isStatic_computed = null;

  /** @apilevel internal */
  protected boolean isStatic_value;

  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:239
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="/home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:239")
  public boolean isStatic() {
    ASTNode$State state = state();
    if (isStatic_computed == ASTNode$State.NON_CYCLE || isStatic_computed == state().cycle()) {
      return isStatic_value;
    }
    isStatic_value = getModifiers().isStatic() || isMemberType();
    if (state().inCircle()) {
      isStatic_computed = state().cycle();
    
    } else {
      isStatic_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isStatic_value;
  }
  /**
   * @attribute syn
   * @aspect PrettyPrintUtil
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:235
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PrettyPrintUtil", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:235")
  public boolean hasModifiers() {
    boolean hasModifiers_value = getModifiers().getNumModifier() > 0;
    return hasModifiers_value;
  }
  /** @apilevel internal */
  private void castingConversionTo_TypeDecl_reset() {
    castingConversionTo_TypeDecl_computed = new java.util.HashMap(4);
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
    ASTNode$State state = state();
    if (castingConversionTo_TypeDecl_values.containsKey(_parameters) && castingConversionTo_TypeDecl_computed != null
        && castingConversionTo_TypeDecl_computed.containsKey(_parameters)
        && (castingConversionTo_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || castingConversionTo_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) castingConversionTo_TypeDecl_values.get(_parameters);
    }
    boolean castingConversionTo_TypeDecl_value = castingConversionTo_compute(type);
    if (state().inCircle()) {
      castingConversionTo_TypeDecl_values.put(_parameters, castingConversionTo_TypeDecl_value);
      castingConversionTo_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      castingConversionTo_TypeDecl_values.put(_parameters, castingConversionTo_TypeDecl_value);
      castingConversionTo_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return castingConversionTo_TypeDecl_value;
  }
  /** @apilevel internal */
  private boolean castingConversionTo_compute(TypeDecl type) {
      if (refined_Generics_InterfaceDecl_castingConversionTo_TypeDecl(type)) {
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:227
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:227")
  public boolean isInterfaceDecl() {
    boolean isInterfaceDecl_value = true;
    return isInterfaceDecl_value;
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
    boolean instanceOf_TypeDecl_value = instanceOf_compute(type);
    if (state().inCircle()) {
      instanceOf_TypeDecl_values.put(_parameters, instanceOf_TypeDecl_value);
      instanceOf_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      instanceOf_TypeDecl_values.put(_parameters, instanceOf_TypeDecl_value);
      instanceOf_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:459
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeWideningAndIdentity", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:459")
  public boolean isSupertypeOfClassDecl(ClassDecl type) {
    {
        if (super.isSupertypeOfClassDecl(type)) {
          return true;
        }
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl typeDecl = (TypeDecl) iter.next();
          if (typeDecl.instanceOf(this)) {
            return true;
          }
        }
        return type.hasSuperclass() && type.superclass().instanceOf(this);
      }
  }
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:479
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeWideningAndIdentity", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:479")
  public boolean isSupertypeOfInterfaceDecl(InterfaceDecl type) {
    {
        if (super.isSupertypeOfInterfaceDecl(type)) {
          return true;
        }
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl superinterface = (TypeDecl) iter.next();
          if (superinterface.instanceOf(this)) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect TypeWideningAndIdentity
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:494
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeWideningAndIdentity", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:494")
  public boolean isSupertypeOfArrayDecl(ArrayDecl type) {
    {
        if (super.isSupertypeOfArrayDecl(type)) {
          return true;
        }
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl typeDecl = (TypeDecl) iter.next();
          if (typeDecl.instanceOf(this)) {
            return true;
          }
        }
        return false;
      }
  }
  /** @apilevel internal */
  private void superInterfaces_reset() {
    superInterfaces_computed = null;
    superInterfaces_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle superInterfaces_computed = null;

  /** @apilevel internal */
  protected Collection<TypeDecl> superInterfaces_value;

  /**
   * @return the interfaces directly implemented by this type.
   * @attribute syn
   * @aspect SuperClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:696
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SuperClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:696")
  public Collection<TypeDecl> superInterfaces() {
    ASTNode$State state = state();
    if (superInterfaces_computed == ASTNode$State.NON_CYCLE || superInterfaces_computed == state().cycle()) {
      return superInterfaces_value;
    }
    superInterfaces_value = superInterfaces_compute();
    if (state().inCircle()) {
      superInterfaces_computed = state().cycle();
    
    } else {
      superInterfaces_computed = ASTNode$State.NON_CYCLE;
    
    }
    return superInterfaces_value;
  }
  /** @apilevel internal */
  private Collection<TypeDecl> superInterfaces_compute() {
      Collection<TypeDecl> interfaces = new ArrayList<TypeDecl>();
      for (Access access : getSuperInterfaceList()) {
        TypeDecl implemented = access.type();
        if (implemented.isInterfaceDecl()) { // TODO(joqvist): unclear why this is needed.
          interfaces.add(implemented);
        }
      }
      return interfaces;
    }
/** @apilevel internal */
protected ASTNode$State.Cycle isCircular_cycle = null;
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
  @ASTNodeAnnotation.Source(aspect="Circularity", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:728")
  public boolean isCircular() {
    if (isCircular_computed) {
      return isCircular_value;
    }
    ASTNode$State state = state();
    if (!isCircular_initialized) {
      isCircular_initialized = true;
      isCircular_value = true;
    }
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      do {
        isCircular_cycle = state.nextCycle();
        boolean new_isCircular_value = isCircular_compute();
        if (new_isCircular_value != isCircular_value) {
          state.setChangeInCycle();
        }
        isCircular_value = new_isCircular_value;
      } while (state.testAndClearChangeInCycle());
      isCircular_computed = true;

      state.leaveCircle();
    } else if (isCircular_cycle != state.cycle()) {
      isCircular_cycle = state.cycle();
      boolean new_isCircular_value = isCircular_compute();
      if (new_isCircular_value != isCircular_value) {
        state.setChangeInCycle();
      }
      isCircular_value = new_isCircular_value;
    } else {
    }
    return isCircular_value;
  }
  /** @apilevel internal */
  private boolean isCircular_compute() {
      for (int i = 0; i < getNumSuperInterface(); i++) {
        Access a = getSuperInterface(i).lastAccess();
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:511
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:511")
  public Collection<Problem> typeHierarchyProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        //9.6.3.8
        if (hasAnnotationFunctionalInterface() && !isFunctional()) {
          problems.add(errorf("%s is not a functional interface", name()));
        }
    
        if (isCircular()) {
          problems.add(errorf("circular inheritance dependency in %s", typeName()));
        } else {
          for (int i = 0; i < getNumSuperInterface(); i++) {
            TypeDecl typeDecl = getSuperInterface(i).type();
            if (typeDecl.isCircular()) {
              problems.add(errorf("circular inheritance dependency in %s", typeName()));
            }
          }
        }
        for (SimpleSet<MethodDecl> set : methodsSignatureMap().values()) {
          if (set.size() > 1) {
            Iterator i2 = set.iterator();
            MethodDecl m = (MethodDecl) i2.next();
            while (i2.hasNext()) {
              MethodDecl n = (MethodDecl) i2.next();
              checkInterfaceMethodDecls(problems, m, n);
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
  protected ASTNode$State.Cycle typeDescriptor_computed = null;

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
    ASTNode$State state = state();
    if (typeDescriptor_computed == ASTNode$State.NON_CYCLE || typeDescriptor_computed == state().cycle()) {
      return typeDescriptor_value;
    }
    typeDescriptor_value = "L" + constantPoolName() + ";";
    if (state().inCircle()) {
      typeDescriptor_computed = state().cycle();
    
    } else {
      typeDescriptor_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeDescriptor_value;
  }
  /** @apilevel internal */
  private void iterableElementType_reset() {
    iterableElementType_computed = null;
    iterableElementType_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle iterableElementType_computed = null;

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
    ASTNode$State state = state();
    if (iterableElementType_computed == ASTNode$State.NON_CYCLE || iterableElementType_computed == state().cycle()) {
      return iterableElementType_value;
    }
    iterableElementType_value = iterableElementType_compute();
    if (state().inCircle()) {
      iterableElementType_computed = state().cycle();
    
    } else {
      iterableElementType_computed = ASTNode$State.NON_CYCLE;
    
    }
    return iterableElementType_value;
  }
  /** @apilevel internal */
  private TypeDecl iterableElementType_compute() {
      TypeDecl type = super.iterableElementType();
      if (!type.isUnknown()) {
        return type;
      } else {
        for (Access iface : getSuperInterfaceList()) {
          type = iface.type().iterableElementType();
          if (!type.isUnknown()) {
            break;
          }
        }
        return type;
      }
    }
  /** @apilevel internal */
  private void erasedAncestorMethodsMap_reset() {
    erasedAncestorMethodsMap_computed = null;
    erasedAncestorMethodsMap_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle erasedAncestorMethodsMap_computed = null;

  /** @apilevel internal */
  protected Map<String, SimpleSet<MethodDecl>> erasedAncestorMethodsMap_value;

  /**
   * @attribute syn
   * @aspect GenericsTypeCheck
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:537
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsTypeCheck", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:537")
  public Map<String, SimpleSet<MethodDecl>> erasedAncestorMethodsMap() {
    ASTNode$State state = state();
    if (erasedAncestorMethodsMap_computed == ASTNode$State.NON_CYCLE || erasedAncestorMethodsMap_computed == state().cycle()) {
      return erasedAncestorMethodsMap_value;
    }
    erasedAncestorMethodsMap_value = erasedAncestorMethodsMap_compute();
    if (state().inCircle()) {
      erasedAncestorMethodsMap_computed = state().cycle();
    
    } else {
      erasedAncestorMethodsMap_computed = ASTNode$State.NON_CYCLE;
    
    }
    return erasedAncestorMethodsMap_value;
  }
  /** @apilevel internal */
  private Map<String, SimpleSet<MethodDecl>> erasedAncestorMethodsMap_compute() {
      Map<String, SimpleSet<MethodDecl>> localMap = localMethodsSignatureMap();
      Map<String, SimpleSet<MethodDecl>> map = new HashMap<String, SimpleSet<MethodDecl>>(localMap);
      for (Iterator<MethodDecl> iter = interfacesMethodsIterator(); iter.hasNext(); ) {
        MethodDecl m = (MethodDecl) iter.next();
        if (m.accessibleFrom(this) && m.erasedMethod() != m) {
          String erasedSignature = m.erasedMethod().signature();
          if (!localMap.containsKey(erasedSignature)) {
            // Map erased signature to substituted method.
            putSimpleSetElement(map, m.erasedMethod().signature(), m);
          }
        }
      }
      for (Iterator<MethodDecl> iter = typeObject().methodsIterator(); iter.hasNext(); ) {
        MethodDecl m = (MethodDecl) iter.next();
        if (m.isPublic() && m.erasedMethod() != m) {
          String erasedSignature = m.erasedMethod().signature();
          if (!localMap.containsKey(erasedSignature)) {
            // Map erased signature to substituted method.
            putSimpleSetElement(map, m.erasedMethod().signature(), m);
          }
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
  protected ASTNode$State.Cycle implementedInterfaces_computed = null;

  /** @apilevel internal */
  protected Collection<InterfaceDecl> implementedInterfaces_value;

  /**
   * @attribute syn
   * @aspect GenericsTypeCheck
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:646
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsTypeCheck", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:646")
  public Collection<InterfaceDecl> implementedInterfaces() {
    ASTNode$State state = state();
    if (implementedInterfaces_computed == ASTNode$State.NON_CYCLE || implementedInterfaces_computed == state().cycle()) {
      return implementedInterfaces_value;
    }
    implementedInterfaces_value = implementedInterfaces_compute();
    if (state().inCircle()) {
      implementedInterfaces_computed = state().cycle();
    
    } else {
      implementedInterfaces_computed = ASTNode$State.NON_CYCLE;
    
    }
    return implementedInterfaces_value;
  }
  /** @apilevel internal */
  private Collection<InterfaceDecl> implementedInterfaces_compute() {
      HashSet<InterfaceDecl> set= new HashSet<InterfaceDecl>();
      set.addAll(typeObject().implementedInterfaces());
      for (Iterator<TypeDecl> iter = interfacesIterator(); iter.hasNext(); ) {
        InterfaceDecl decl = (InterfaceDecl) iter.next();
        set.add(decl);
        set.addAll(decl.implementedInterfaces());
      }
      return set;
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
        new_subtype_TypeDecl_value = type.supertypeInterfaceDecl(this);
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
      boolean new_subtype_TypeDecl_value = type.supertypeInterfaceDecl(this);
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
    {
        if (super.supertypeClassDecl(type)) {
          return true;
        }
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl typeDecl = iter.next();
          if (typeDecl.subtype(this)) {
            return true;
          }
        }
        return type.hasSuperclass() && type.superclass().subtype(this);
      }
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:524
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:524")
  public boolean supertypeInterfaceDecl(InterfaceDecl type) {
    {
        if (super.supertypeInterfaceDecl(type)) {
          return true;
        }
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl superinterface = iter.next();
          if (superinterface.subtype(this)) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect GenericsSubtype
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:541
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsSubtype", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericsSubtype.jrag:541")
  public boolean supertypeArrayDecl(ArrayDecl type) {
    {
        if (super.supertypeArrayDecl(type)) {
          return true;
        }
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl typeDecl = iter.next();
          if (typeDecl.subtype(this)) {
            return true;
          }
        }
        return false;
      }
  }
  /** @apilevel internal */
  private void hasAnnotationFunctionalInterface_reset() {
    hasAnnotationFunctionalInterface_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle hasAnnotationFunctionalInterface_computed = null;

  /** @apilevel internal */
  protected boolean hasAnnotationFunctionalInterface_value;

  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java8/frontend/Annotations.jrag:29
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java8/frontend/Annotations.jrag:29")
  public boolean hasAnnotationFunctionalInterface() {
    ASTNode$State state = state();
    if (hasAnnotationFunctionalInterface_computed == ASTNode$State.NON_CYCLE || hasAnnotationFunctionalInterface_computed == state().cycle()) {
      return hasAnnotationFunctionalInterface_value;
    }
    hasAnnotationFunctionalInterface_value = getModifiers().hasAnnotationFunctionalInterface();
    if (state().inCircle()) {
      hasAnnotationFunctionalInterface_computed = state().cycle();
    
    } else {
      hasAnnotationFunctionalInterface_computed = ASTNode$State.NON_CYCLE;
    
    }
    return hasAnnotationFunctionalInterface_value;
  }
  /** @apilevel internal */
  private void hasFunctionDescriptor_reset() {
    hasFunctionDescriptor_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle hasFunctionDescriptor_computed = null;

  /** @apilevel internal */
  protected boolean hasFunctionDescriptor_value;

  /**
   * @attribute syn
   * @aspect FunctionDescriptor
   * @declaredat /home/olivier/projects/extendj/java8/frontend/FunctionDescriptor.jrag:97
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="FunctionDescriptor", declaredAt="/home/olivier/projects/extendj/java8/frontend/FunctionDescriptor.jrag:97")
  public boolean hasFunctionDescriptor() {
    ASTNode$State state = state();
    if (hasFunctionDescriptor_computed == ASTNode$State.NON_CYCLE || hasFunctionDescriptor_computed == state().cycle()) {
      return hasFunctionDescriptor_value;
    }
    hasFunctionDescriptor_value = hasFunctionDescriptor_compute();
    if (state().inCircle()) {
      hasFunctionDescriptor_computed = state().cycle();
    
    } else {
      hasFunctionDescriptor_computed = ASTNode$State.NON_CYCLE;
    
    }
    return hasFunctionDescriptor_value;
  }
  /** @apilevel internal */
  private boolean hasFunctionDescriptor_compute() {
      return functionDescriptor() != null;
    }
  /** @apilevel internal */
  private void functionDescriptor_reset() {
    functionDescriptor_computed = null;
    functionDescriptor_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle functionDescriptor_computed = null;

  /** @apilevel internal */
  protected FunctionDescriptor functionDescriptor_value;

  /**
   * Builds a function type from this interface.
   * 
   * If the interface can not be used to construct a valid function
   * type then {@code null} is returned.
   * @attribute syn
   * @aspect FunctionDescriptor
   * @declaredat /home/olivier/projects/extendj/java8/frontend/FunctionDescriptor.jrag:108
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="FunctionDescriptor", declaredAt="/home/olivier/projects/extendj/java8/frontend/FunctionDescriptor.jrag:108")
  public FunctionDescriptor functionDescriptor() {
    ASTNode$State state = state();
    if (functionDescriptor_computed == ASTNode$State.NON_CYCLE || functionDescriptor_computed == state().cycle()) {
      return functionDescriptor_value;
    }
    functionDescriptor_value = functionDescriptor_compute();
    if (state().inCircle()) {
      functionDescriptor_computed = state().cycle();
    
    } else {
      functionDescriptor_computed = ASTNode$State.NON_CYCLE;
    
    }
    return functionDescriptor_value;
  }
  /** @apilevel internal */
  private FunctionDescriptor functionDescriptor_compute() {
      java.util.List<MethodDecl> methods = collectAbstractMethods();
  
      if (methods.isEmpty()) {
        // No abstract method in this interface.
        return null;
      } else if (methods.size() == 1) {
        MethodDecl m = methods.get(0);
        ArrayList<TypeDecl> throwsList = new ArrayList<TypeDecl>();
        for (Access exception : m.getExceptionList()) {
          throwsList.add(exception.type());
        }
        return new FunctionDescriptor(this, m, throwsList);
      } else {
        MethodDecl foundMethod = null;
  
        for (MethodDecl current : methods) {
          foundMethod = current;
          for (MethodDecl inner : methods) {
            if (!current.subsignatureTo(inner) || !current.returnTypeSubstitutableFor(inner)) {
              foundMethod = null;
            }
          }
          if (foundMethod != null) {
            break;
          }
        }
  
        if (foundMethod == null) {
          return null;
        }
  
        // Now the throws-list needs to be computed as stated in 9.8.
        ArrayList<Access> descriptorThrows = new ArrayList<Access>();
        for (MethodDecl current : methods) {
          for (Access exception : current.getExceptionList()) {
            boolean alreadyInserted = false;
            for (Access found : descriptorThrows) {
              if (found.sameType(exception)) {
                alreadyInserted = true;
                break;
              }
            }
            if (alreadyInserted) {
              continue;
            }
  
            boolean foundIncompatibleClause = false;
            // Has to be the subtype to at least one exception in each clause.
            if (foundMethod.isGeneric()) {
              for (MethodDecl inner : methods) {
                if (!inner.subtypeThrowsClause(exception)) {
                  foundIncompatibleClause = true;
                  break;
                }
              }
            } else {
              for (MethodDecl inner : methods) {
                if (!inner.subtypeThrowsClauseErased(exception)) {
                  foundIncompatibleClause = true;
                  break;
                }
              }
            }
  
            if (!foundIncompatibleClause) {
              // Was subtype to one exception in every clause
              descriptorThrows.add(exception);
            }
          }
        }
  
        // Found a suitable method and finished building throws-list,
        // now the descriptor just needs to be put together.
        if (descriptorThrows.size() == 0) {
          return new FunctionDescriptor(this, foundMethod, Collections.<TypeDecl>emptyList());
        } else {
          ArrayList<TypeDecl> throwsList = new ArrayList<TypeDecl>();
  
          // All type variables must be replaced with foundMethods
          // type variables if the descriptor is generic.
          if (foundMethod.isGeneric()) {
            GenericMethodDecl foundGeneric = foundMethod.genericDecl();
            for (Access exception : descriptorThrows) {
              if (exception.type() instanceof TypeVariable) {
                TypeVariable foundVar = (TypeVariable) exception.type();
                TypeVariable original = foundGeneric.getTypeParameter(foundVar.typeVarPosition());
                throwsList.add(original);
              } else {
                throwsList.add(exception.type());
              }
            }
          } else {
            // All throwed types must be erased if the descriptor is not generic.
            for (Access exception : descriptorThrows) {
              throwsList.add(exception.type().erasure());
            }
          }
          return new FunctionDescriptor(this, foundMethod, throwsList);
        }
      }
    }
  /** @apilevel internal */
  private void isFunctionalInterface_reset() {
    isFunctionalInterface_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isFunctionalInterface_computed = null;

  /** @apilevel internal */
  protected boolean isFunctionalInterface_value;

  /**
   * @attribute syn
   * @aspect FunctionalInterface
   * @declaredat /home/olivier/projects/extendj/java8/frontend/FunctionalInterface.jrag:30
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="FunctionalInterface", declaredAt="/home/olivier/projects/extendj/java8/frontend/FunctionalInterface.jrag:30")
  public boolean isFunctionalInterface() {
    ASTNode$State state = state();
    if (isFunctionalInterface_computed == ASTNode$State.NON_CYCLE || isFunctionalInterface_computed == state().cycle()) {
      return isFunctionalInterface_value;
    }
    isFunctionalInterface_value = isFunctional();
    if (state().inCircle()) {
      isFunctionalInterface_computed = state().cycle();
    
    } else {
      isFunctionalInterface_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isFunctionalInterface_value;
  }
  /** @apilevel internal */
  private void isFunctional_reset() {
    isFunctional_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isFunctional_computed = null;

  /** @apilevel internal */
  protected boolean isFunctional_value;

  /**
   * @attribute syn
   * @aspect FunctionalInterface
   * @declaredat /home/olivier/projects/extendj/java8/frontend/FunctionalInterface.jrag:33
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="FunctionalInterface", declaredAt="/home/olivier/projects/extendj/java8/frontend/FunctionalInterface.jrag:33")
  public boolean isFunctional() {
    ASTNode$State state = state();
    if (isFunctional_computed == ASTNode$State.NON_CYCLE || isFunctional_computed == state().cycle()) {
      return isFunctional_value;
    }
    isFunctional_value = isFunctional_compute();
    if (state().inCircle()) {
      isFunctional_computed = state().cycle();
    
    } else {
      isFunctional_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isFunctional_value;
  }
  /** @apilevel internal */
  private boolean isFunctional_compute() {
      java.util.List<MethodDecl> methods = collectAbstractMethods();
      boolean foundMethod = false;
  
      if (methods.isEmpty()) {
        return false;
      } else if (methods.size() == 1) {
        return true;
      } else {
        for (MethodDecl current : methods) {
          foundMethod = true;
          for (MethodDecl inner : methods) {
            if (!current.subsignatureTo(inner) || !current.returnTypeSubstitutableFor(inner)) {
              foundMethod = false;
            }
          }
          if (foundMethod) {
            break;
          }
        }
      }
      return foundMethod;
    }
  /** @apilevel internal */
  private void collectAbstractMethods_reset() {
    collectAbstractMethods_computed = null;
    collectAbstractMethods_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle collectAbstractMethods_computed = null;

  /** @apilevel internal */
  protected java.util.List<MethodDecl> collectAbstractMethods_value;

  /**
   * Collects all abstract methods in an interface. Used to compute
   * a function type from a functional interface.
   * @attribute syn
   * @aspect FunctionalInterface
   * @declaredat /home/olivier/projects/extendj/java8/frontend/FunctionalInterface.jrag:317
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="FunctionalInterface", declaredAt="/home/olivier/projects/extendj/java8/frontend/FunctionalInterface.jrag:317")
  public java.util.List<MethodDecl> collectAbstractMethods() {
    ASTNode$State state = state();
    if (collectAbstractMethods_computed == ASTNode$State.NON_CYCLE || collectAbstractMethods_computed == state().cycle()) {
      return collectAbstractMethods_value;
    }
    collectAbstractMethods_value = collectAbstractMethods_compute();
    if (state().inCircle()) {
      collectAbstractMethods_computed = state().cycle();
    
    } else {
      collectAbstractMethods_computed = ASTNode$State.NON_CYCLE;
    
    }
    return collectAbstractMethods_value;
  }
  /** @apilevel internal */
  private java.util.List<MethodDecl> collectAbstractMethods_compute() {
      java.util.List<MethodDecl> methods = new ArrayList<MethodDecl>();
      Map<String, SimpleSet<MethodDecl>> map = localMethodsSignatureMap();
      Map<String, SimpleSet<MethodDecl>> objectMethods = typeObject().methodsSignatureMap();
      MethodDecl inObject;
  
      for (Map.Entry<String, SimpleSet<MethodDecl>> entry: map.entrySet()) {
        SimpleSet<MethodDecl> set = entry.getValue();
        MethodDecl m = set.iterator().next();
  
        SimpleSet<MethodDecl> objectSet = objectMethods.get(m.signature());
        if (m.isAbstract()) {
          if (objectSet == null || objectSet.isEmpty()) {
            methods.add(m);
          } else {
            inObject = objectSet.iterator().next();
            if (!inObject.isPublic()) {
              methods.add(m);
            }
          }
        }
      }
  
      for (Iterator outerIter = interfacesIterator(); outerIter.hasNext();) {
        TypeDecl typeDecl = (TypeDecl) outerIter.next();
        for (Iterator<MethodDecl> iter = typeDecl.methodsIterator(); iter.hasNext();) {
          MethodDecl m = iter.next();
          if (m.isAbstract() && !m.isPrivate() && m.accessibleFrom(this)) {
            SimpleSet<MethodDecl> objectSet = objectMethods.get(m.signature());
            if (objectSet == null || objectSet.isEmpty()) {
              methods.add(m);
            } else {
              inObject = objectSet.iterator().next();
              if (!inObject.isPublic()) {
                methods.add(m);
              }
            }
          }
        }
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
        new_strictSubtype_TypeDecl_value = type.strictSupertypeInterfaceDecl(this);
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
      boolean new_strictSubtype_TypeDecl_value = type.strictSupertypeInterfaceDecl(this);
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
    {
        if (super.strictSupertypeClassDecl(type)) {
          return true;
        }
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl typeDecl = iter.next();
          if (typeDecl.strictSubtype(this)) {
            return true;
          }
        }
        return type.hasSuperclass() && type.superclass() != null
            && type.superclass().strictSubtype(this);
      }
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:398
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:398")
  public boolean strictSupertypeInterfaceDecl(InterfaceDecl type) {
    {
        if (super.strictSupertypeInterfaceDecl(type)) {
          return true;
        }
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl superinterface = iter.next();
          if (superinterface.strictSubtype(this)) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect StrictSubtype
   * @declaredat /home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:415
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StrictSubtype", declaredAt="/home/olivier/projects/extendj/java8/frontend/GenericsSubtype.jrag:415")
  public boolean strictSupertypeArrayDecl(ArrayDecl type) {
    {
        if (super.strictSupertypeArrayDecl(type)) {
          return true;
        }
        for (Iterator<TypeDecl> iter = type.interfacesIterator(); iter.hasNext(); ) {
          TypeDecl typeDecl = iter.next();
          if (typeDecl.strictSubtype(this)) {
            return true;
          }
        }
        return false;
      }
  }
  /** @apilevel internal */
  private void hasOverridingMethodInSuper_MethodDecl_reset() {
    hasOverridingMethodInSuper_MethodDecl_computed = new java.util.HashMap(4);
    hasOverridingMethodInSuper_MethodDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map hasOverridingMethodInSuper_MethodDecl_values;
  /** @apilevel internal */
  protected java.util.Map hasOverridingMethodInSuper_MethodDecl_computed;
  /**
   * @attribute syn
   * @aspect MethodSignature18
   * @declaredat /home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:1190
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature18", declaredAt="/home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:1190")
  public boolean hasOverridingMethodInSuper(MethodDecl m) {
    Object _parameters = m;
    if (hasOverridingMethodInSuper_MethodDecl_computed == null) hasOverridingMethodInSuper_MethodDecl_computed = new java.util.HashMap(4);
    if (hasOverridingMethodInSuper_MethodDecl_values == null) hasOverridingMethodInSuper_MethodDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (hasOverridingMethodInSuper_MethodDecl_values.containsKey(_parameters) && hasOverridingMethodInSuper_MethodDecl_computed != null
        && hasOverridingMethodInSuper_MethodDecl_computed.containsKey(_parameters)
        && (hasOverridingMethodInSuper_MethodDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || hasOverridingMethodInSuper_MethodDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) hasOverridingMethodInSuper_MethodDecl_values.get(_parameters);
    }
    boolean hasOverridingMethodInSuper_MethodDecl_value = hasOverridingMethodInSuper_compute(m);
    if (state().inCircle()) {
      hasOverridingMethodInSuper_MethodDecl_values.put(_parameters, hasOverridingMethodInSuper_MethodDecl_value);
      hasOverridingMethodInSuper_MethodDecl_computed.put(_parameters, state().cycle());
    
    } else {
      hasOverridingMethodInSuper_MethodDecl_values.put(_parameters, hasOverridingMethodInSuper_MethodDecl_value);
      hasOverridingMethodInSuper_MethodDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return hasOverridingMethodInSuper_MethodDecl_value;
  }
  /** @apilevel internal */
  private boolean hasOverridingMethodInSuper_compute(MethodDecl m) {
      for (Iterator<TypeDecl> outerIter = interfacesIterator(); outerIter.hasNext(); ) {
        TypeDecl typeDecl = outerIter.next();
        for (Iterator iter = typeDecl.methodsIterator(); iter.hasNext(); ) {
          MethodDecl superMethod = (MethodDecl) iter.next();
          if (m != superMethod && superMethod.overrides(m)) {
            return true;
          }
        }
      }
      return false;
    }
  /** @apilevel internal */
  private void sootClass_reset() {
    sootClass_computed = null;
    sootClass_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle sootClass_computed = null;

  /** @apilevel internal */
  protected SootClass sootClass_value;

  /**
   * @attribute syn
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:55
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EmitJimple", declaredAt="/home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:55")
  public SootClass sootClass() {
    ASTNode$State state = state();
    if (sootClass_computed == ASTNode$State.NON_CYCLE || sootClass_computed == state().cycle()) {
      return sootClass_value;
    }
    sootClass_value = sootClass_compute();
    if (state().inCircle()) {
      sootClass_computed = state().cycle();
    
    } else {
      sootClass_computed = ASTNode$State.NON_CYCLE;
    
    }
    return sootClass_value;
  }
  /** @apilevel internal */
  private SootClass sootClass_compute() {
      SootClass sc = SootResolver.v().makeClassRef(jvmName());
      sc.setModifiers(flags()); // turn it into an interface
      return sc;
    }
  /** @apilevel internal */
  private void flags_reset() {
    flags_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle flags_computed = null;

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
    ASTNode$State state = state();
    if (flags_computed == ASTNode$State.NON_CYCLE || flags_computed == state().cycle()) {
      return flags_value;
    }
    flags_value = super.flags() | soot.Modifier.INTERFACE;
    if (state().inCircle()) {
      flags_computed = state().cycle();
    
    } else {
      flags_computed = ASTNode$State.NON_CYCLE;
    
    }
    return flags_value;
  }
  /** @apilevel internal */
  private void bridgeCandidates_String_reset() {
    bridgeCandidates_String_computed = new java.util.HashMap(4);
    bridgeCandidates_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map bridgeCandidates_String_values;
  /** @apilevel internal */
  protected java.util.Map bridgeCandidates_String_computed;
  /**
   * @attribute syn
   * @aspect GenericsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:134
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenericsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:134")
  public SimpleSet<MethodDecl> bridgeCandidates(String signature) {
    Object _parameters = signature;
    if (bridgeCandidates_String_computed == null) bridgeCandidates_String_computed = new java.util.HashMap(4);
    if (bridgeCandidates_String_values == null) bridgeCandidates_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (bridgeCandidates_String_values.containsKey(_parameters) && bridgeCandidates_String_computed != null
        && bridgeCandidates_String_computed.containsKey(_parameters)
        && (bridgeCandidates_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || bridgeCandidates_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<MethodDecl>) bridgeCandidates_String_values.get(_parameters);
    }
    SimpleSet<MethodDecl> bridgeCandidates_String_value = ancestorMethods(signature);
    if (state().inCircle()) {
      bridgeCandidates_String_values.put(_parameters, bridgeCandidates_String_value);
      bridgeCandidates_String_computed.put(_parameters, state().cycle());
    
    } else {
      bridgeCandidates_String_values.put(_parameters, bridgeCandidates_String_value);
      bridgeCandidates_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return bridgeCandidates_String_value;
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
    needsSignatureAttribute_value = needsSignatureAttribute_compute();
    if (state().inCircle()) {
      needsSignatureAttribute_computed = state().cycle();
    
    } else {
      needsSignatureAttribute_computed = ASTNode$State.NON_CYCLE;
    
    }
    return needsSignatureAttribute_value;
  }
  /** @apilevel internal */
  private boolean needsSignatureAttribute_compute() {
      for (Iterator<TypeDecl> iter = interfacesIterator(); iter.hasNext(); ) {
        if (iter.next().needsSignatureAttribute()) {
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
      buf.append(typeObject().classTypeSignature());
      for (Iterator<TypeDecl> iter = interfacesIterator(); iter.hasNext(); ) {
        buf.append(iter.next().classTypeSignature());
      }
      return buf.toString();
    }
  /**
   * @attribute inh
   * @aspect TypeConversion
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:113
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeConversion", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:113")
  public MethodDecl unknownMethod() {
    MethodDecl unknownMethod_value = getParent().Define_unknownMethod(this, null);
    return unknownMethod_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getSuperInterfaceListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:99
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return NameType.TYPE_NAME;
    }
    else {
      return super.Define_nameType(_callerNode, _childNode);
    }
  }
  protected boolean canDefine_nameType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:76
   * @apilevel internal
   */
  public TypeDecl Define_hostType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getSuperInterfaceListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:651
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return hostType();
    }
    else {
      return super.Define_hostType(_callerNode, _childNode);
    }
  }
  protected boolean canDefine_hostType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/SuppressWarnings.jrag:37
   * @apilevel internal
   */
  public boolean Define_withinSuppressWarnings(ASTNode _callerNode, ASTNode _childNode, String annot) {
    if (_callerNode == getSuperInterfaceListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:420
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return hasAnnotationSuppressWarnings(annot) || withinSuppressWarnings(annot);
    }
    else {
      return getParent().Define_withinSuppressWarnings(this, _callerNode, annot);
    }
  }
  protected boolean canDefine_withinSuppressWarnings(ASTNode _callerNode, ASTNode _childNode, String annot) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:536
   * @apilevel internal
   */
  public boolean Define_withinDeprecatedAnnotation(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getSuperInterfaceListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:547
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return isDeprecated() || withinDeprecatedAnnotation();
    }
    else {
      return getParent().Define_withinDeprecatedAnnotation(this, _callerNode);
    }
  }
  protected boolean canDefine_withinDeprecatedAnnotation(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:383
   * @apilevel internal
   */
  public boolean Define_inExtendsOrImplements(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getSuperInterfaceListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:382
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return true;
    }
    else {
      return getParent().Define_inExtendsOrImplements(this, _callerNode);
    }
  }
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
  protected void collect_contributors_CompilationUnit_problems(CompilationUnit _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat /home/olivier/projects/extendj/java4/frontend/AccessControl.jrag:212
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:509
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
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    for (Problem value : accessControlProblems()) {
      collection.add(value);
    }
    for (Problem value : typeHierarchyProblems()) {
      collection.add(value);
    }
  }
}
