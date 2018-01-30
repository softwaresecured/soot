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
 * @declaredat /home/olivier/projects/extendj/java5/grammar/Annotations.ast:6
 * @astdecl Annotation : Modifier ::= <ID:String> Access ElementValuePair*;
 * @production Annotation : {@link Modifier} ::= <span class="component">&lt;ID:String&gt;</span> <span class="component">{@link Access}</span> <span class="component">{@link ElementValuePair}*</span>;

 */
public class Annotation extends Modifier implements Cloneable {
  /**
   * @aspect Java5PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java5/frontend/PrettyPrint.jadd:35
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("@");
    out.print(getAccess());
    if (hasElementValuePair()) {
      out.print("(");
      out.join(getElementValuePairList(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(", ");
        }
      });
      out.print(")");
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public Annotation() {
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
    setChild(new List(), 1);
  }
  /**
   * @declaredat ASTNode:14
   */
  @ASTNodeAnnotation.Constructor(
    name = {"ID", "Access", "ElementValuePair"},
    type = {"String", "Access", "List<ElementValuePair>"},
    kind = {"Token", "Child", "List"}
  )
  public Annotation(String p0, Access p1, List<ElementValuePair> p2) {
    setID(p0);
    setChild(p1, 0);
    setChild(p2, 1);
  }
  /**
   * @declaredat ASTNode:24
   */
  public Annotation(beaver.Symbol p0, Access p1, List<ElementValuePair> p2) {
    setID(p0);
    setChild(p1, 0);
    setChild(p2, 1);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:30
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:36
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:40
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    decl_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:45
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:49
   */
  public Annotation clone() throws CloneNotSupportedException {
    Annotation node = (Annotation) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:54
   */
  public Annotation copy() {
    try {
      Annotation node = (Annotation) clone();
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
   * @declaredat ASTNode:73
   */
  @Deprecated
  public Annotation fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:83
   */
  public Annotation treeCopyNoTransform() {
    Annotation tree = (Annotation) copy();
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
   * @declaredat ASTNode:103
   */
  public Annotation treeCopy() {
    Annotation tree = (Annotation) copy();
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
   * @declaredat ASTNode:117
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((Annotation) node).tokenString_ID);    
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
   * Replaces the Access child.
   * @param node The new node to replace the Access child.
   * @apilevel high-level
   */
  public void setAccess(Access node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Access child.
   * @return The current node used as the Access child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Access")
  public Access getAccess() {
    return (Access) getChild(0);
  }
  /**
   * Retrieves the Access child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Access child.
   * @apilevel low-level
   */
  public Access getAccessNoTransform() {
    return (Access) getChildNoTransform(0);
  }
  /**
   * Replaces the ElementValuePair list.
   * @param list The new list node to be used as the ElementValuePair list.
   * @apilevel high-level
   */
  public void setElementValuePairList(List<ElementValuePair> list) {
    setChild(list, 1);
  }
  /**
   * Retrieves the number of children in the ElementValuePair list.
   * @return Number of children in the ElementValuePair list.
   * @apilevel high-level
   */
  public int getNumElementValuePair() {
    return getElementValuePairList().getNumChild();
  }
  /**
   * Retrieves the number of children in the ElementValuePair list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the ElementValuePair list.
   * @apilevel low-level
   */
  public int getNumElementValuePairNoTransform() {
    return getElementValuePairListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the ElementValuePair list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the ElementValuePair list.
   * @apilevel high-level
   */
  public ElementValuePair getElementValuePair(int i) {
    return (ElementValuePair) getElementValuePairList().getChild(i);
  }
  /**
   * Check whether the ElementValuePair list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasElementValuePair() {
    return getElementValuePairList().getNumChild() != 0;
  }
  /**
   * Append an element to the ElementValuePair list.
   * @param node The element to append to the ElementValuePair list.
   * @apilevel high-level
   */
  public void addElementValuePair(ElementValuePair node) {
    List<ElementValuePair> list = (parent == null) ? getElementValuePairListNoTransform() : getElementValuePairList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addElementValuePairNoTransform(ElementValuePair node) {
    List<ElementValuePair> list = getElementValuePairListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the ElementValuePair list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setElementValuePair(ElementValuePair node, int i) {
    List<ElementValuePair> list = getElementValuePairList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the ElementValuePair list.
   * @return The node representing the ElementValuePair list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="ElementValuePair")
  public List<ElementValuePair> getElementValuePairList() {
    List<ElementValuePair> list = (List<ElementValuePair>) getChild(1);
    return list;
  }
  /**
   * Retrieves the ElementValuePair list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the ElementValuePair list.
   * @apilevel low-level
   */
  public List<ElementValuePair> getElementValuePairListNoTransform() {
    return (List<ElementValuePair>) getChildNoTransform(1);
  }
  /**
   * @return the element at index {@code i} in the ElementValuePair list without
   * triggering rewrites.
   */
  public ElementValuePair getElementValuePairNoTransform(int i) {
    return (ElementValuePair) getElementValuePairListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the ElementValuePair list.
   * @return The node representing the ElementValuePair list.
   * @apilevel high-level
   */
  public List<ElementValuePair> getElementValuePairs() {
    return getElementValuePairList();
  }
  /**
   * Retrieves the ElementValuePair list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the ElementValuePair list.
   * @apilevel low-level
   */
  public List<ElementValuePair> getElementValuePairsNoTransform() {
    return getElementValuePairListNoTransform();
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:71
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:71")
  public Collection<Problem> modifierProblems() {
    {
        if (inComplexAnnotation()) {
          return Collections.emptyList();
        }
        if (decl() instanceof AnnotationDecl) {
          AnnotationDecl T = (AnnotationDecl) decl();
          Annotation m = T.annotation(lookupType("java.lang.annotation", "Target"));
          if (m != null && m.getNumElementValuePair() == 1
              && m.getElementValuePair(0).getName().equals("value")) {
            ElementValue v = m.getElementValuePair(0).getElementValue();
            if (!v.validTarget(this)) {
              return Collections.singletonList(errorf(
                  "annotation type %s is not applicable to this kind of declaration",
                  T.typeName()));
            }
          }
        }
        return Collections.emptyList();
      }
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:342
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:342")
  public boolean isAnnotation(String packageName, String name) {
    boolean isAnnotation_String_String_value = decl().isType(packageName, name);
    return isAnnotation_String_String_value;
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:368
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:368")
  public Collection<Problem> overrideProblems() {
    {
        if (decl().fullName().equals("java.lang.Override")
            && enclosingBodyDecl() instanceof MethodDecl) {
          MethodDecl method = (MethodDecl) enclosingBodyDecl();
          TypeDecl host = method.hostType();
          SimpleSet<MethodDecl> ancestors = host.ancestorMethods(method.signature());
          boolean found = false;
          for (MethodDecl decl : ancestors) {
            if (method.overrides(decl)) {
              found = true;
              break;
            }
          }
          if (!found) {
            TypeDecl typeObject = lookupType("java.lang", "Object");
            SimpleSet<MethodDecl> overrides = typeObject.localMethodsSignature(method.signature());
            if (overrides.isEmpty() || !overrides.iterator().next().isPublic()) {
              return Collections.singletonList(
                  error("method does not override a method from a supertype"));
            }
          }
        }
        return Collections.emptyList();
      }
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:555
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:555")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        if (!decl().isAnnotationDecl()) {
          /* TypeName names the annotation type corresponding to the annotation. It is a
          compile-time error if TypeName does not name an annotation type.*/
          if (!decl().isUnknown()) {
            problems.add(errorf("%s is not an annotation type", decl().typeName()));
          }
        } else {
          TypeDecl typeDecl = decl();
          /* It is a compile-time error if a declaration is annotated with more than one
          annotation for a given annotation type.*/
          if (lookupAnnotation(typeDecl) != this) {
            problems.add(errorf("duplicate annotation %s", typeDecl.typeName()));
          }
          /* Annotations must contain an element-value pair for every element of the
          corresponding annotation type, except for those elements with default
          values, or a compile-time error occurs. Annotations may, but are not
          required to, contain element-value pairs for elements with default values.*/
          for (int i = 0; i < typeDecl.getNumBodyDecl(); i++) {
            if (typeDecl.getBodyDecl(i) instanceof MethodDecl) {
              MethodDecl decl = (MethodDecl) typeDecl.getBodyDecl(i);
              if (elementValueFor(decl.name()) == null
                  && (!(decl instanceof AnnotationMethodDecl)
                      || !((AnnotationMethodDecl) decl).hasDefaultValue())) {
                problems.add(errorf("missing value for %s", decl.name()));
              }
            }
          }
          /* The Identifier in an ElementValuePair must be the simple name of one of the
          elements of the annotation type identified by TypeName in the containing
          annotation. Otherwise, a compile-time error occurs. (In other words, the
          identifier in an element-value pair must also be a method name in the interface
          identified by TypeName.) */
          for (int i = 0; i < getNumElementValuePair(); i++) {
            ElementValuePair pair = getElementValuePair(i);
            if (typeDecl.memberMethods(pair.getName()).isEmpty()) {
              problems.add(errorf("can not find element named %s in %s",
                  pair.getName(), typeDecl.typeName()));
            }
          }
        }
        return problems;
      }
  }
  /** @apilevel internal */
  private void decl_reset() {
    decl_computed = null;
    decl_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle decl_computed = null;

  /** @apilevel internal */
  protected TypeDecl decl_value;

  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:600
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:600")
  public TypeDecl decl() {
    ASTState state = state();
    if (decl_computed == ASTState.NON_CYCLE || decl_computed == state().cycle()) {
      return decl_value;
    }
    decl_value = getAccess().type();
    if (state().inCircle()) {
      decl_computed = state().cycle();
    
    } else {
      decl_computed = ASTState.NON_CYCLE;
    
    }
    return decl_value;
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:615
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:615")
  public ElementValue elementValueFor(String name) {
    {
        for (int i = 0; i < getNumElementValuePair(); i++) {
          ElementValuePair pair = getElementValuePair(i);
          if (pair.getName().equals(name)) {
            return pair.getElementValue();
          }
        }
        return null;
      }
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:737
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:737")
  public TypeDecl type() {
    TypeDecl type_value = getAccess().type();
    return type_value;
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:767
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:767")
  public boolean isMetaAnnotation() {
    boolean isMetaAnnotation_value = hostType().isAnnotationDecl();
    return isMetaAnnotation_value;
  }
  /**
   * @return {@code true} if this annotation is used inside another annotation, i.e. it is
   * used in a complex annoation.
   * @attribute inh
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:95
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:95")
  public boolean inComplexAnnotation() {
    boolean inComplexAnnotation_value = getParent().Define_inComplexAnnotation(this, null);
    return inComplexAnnotation_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:107
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:107")
  public TypeDecl lookupType(String packageName, String typeName) {
    TypeDecl lookupType_String_String_value = getParent().Define_lookupType(this, null, packageName, typeName);
    return lookupType_String_String_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:131
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:131")
  public boolean mayUseAnnotationTarget(String name) {
    boolean mayUseAnnotationTarget_String_value = getParent().Define_mayUseAnnotationTarget(this, null, name);
    return mayUseAnnotationTarget_String_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:390
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:390")
  public BodyDecl enclosingBodyDecl() {
    BodyDecl enclosingBodyDecl_value = getParent().Define_enclosingBodyDecl(this, null);
    return enclosingBodyDecl_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:602
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:602")
  public Annotation lookupAnnotation(TypeDecl typeDecl) {
    Annotation lookupAnnotation_TypeDecl_value = getParent().Define_lookupAnnotation(this, null, typeDecl);
    return lookupAnnotation_TypeDecl_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:769
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:769")
  public TypeDecl hostType() {
    TypeDecl hostType_value = getParent().Define_hostType(this, null);
    return hostType_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:95
   * @apilevel internal
   */
  public boolean Define_inComplexAnnotation(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:95
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute inComplexAnnotation
   */
  protected boolean canDefine_inComplexAnnotation(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:648
   * @apilevel internal
   */
  public TypeDecl Define_enclosingAnnotationDecl(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getElementValuePairListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:650
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return decl();
    }
    else {
      return getParent().Define_enclosingAnnotationDecl(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:648
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute enclosingAnnotationDecl
   */
  protected boolean canDefine_enclosingAnnotationDecl(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    if (getAccessNoTransform() != null && _callerNode == getAccess()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:776
      return NameType.TYPE_NAME;
    }
    else {
      return getParent().Define_nameType(this, _callerNode);
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
    return super.rewriteTo();
  }
  /** @apilevel internal */
  public boolean canRewrite() {
    return false;
  }
  /** @apilevel internal */
  protected void collect_contributors_CompilationUnit_problems(CompilationUnit _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:65
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:366
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:553
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
    for (Problem value : overrideProblems()) {
      collection.add(value);
    }
    for (Problem value : typeProblems()) {
      collection.add(value);
    }
  }
}
