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
 * @declaredat /home/olivier/projects/extendj/java5/grammar/Annotations.ast:2
 * @astdecl AnnotationDecl : InterfaceDecl ::= SuperInterface:Access*;
 * @production AnnotationDecl : {@link InterfaceDecl} ::= <span class="component">SuperInterface:{@link Access}*</span>;

 */
public class AnnotationDecl extends InterfaceDecl implements Cloneable {
  /**
   * @aspect Java5PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java5/frontend/PrettyPrint.jadd:49
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getModifiers());
    out.print("@interface ");
    out.print(getID());
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
   * @declaredat ASTNode:1
   */
  public AnnotationDecl() {
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
    name = {"Modifiers", "ID", "BodyDecl"},
    type = {"Modifiers", "String", "List<BodyDecl>"},
    kind = {"Child", "Token", "List"}
  )
  public AnnotationDecl(Modifiers p0, String p1, List<BodyDecl> p2) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
  }
  /**
   * @declaredat ASTNode:25
   */
  public AnnotationDecl(Modifiers p0, beaver.Symbol p1, List<BodyDecl> p2) {
    setChild(p0, 0);
    setID(p1);
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
    getSuperInterfaceList_reset();
    containsElementOf_TypeDecl_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:47
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:51
   */
  public AnnotationDecl clone() throws CloneNotSupportedException {
    AnnotationDecl node = (AnnotationDecl) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:56
   */
  public AnnotationDecl copy() {
    try {
      AnnotationDecl node = (AnnotationDecl) clone();
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
   * @declaredat ASTNode:75
   */
  @Deprecated
  public AnnotationDecl fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:85
   */
  public AnnotationDecl treeCopyNoTransform() {
    AnnotationDecl tree = (AnnotationDecl) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 2:
          tree.children[i] = new List();
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
   * @declaredat ASTNode:110
   */
  public AnnotationDecl treeCopy() {
    AnnotationDecl tree = (AnnotationDecl) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 2:
          tree.children[i] = new List();
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
   * @declaredat ASTNode:129
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((AnnotationDecl) node).tokenString_ID);    
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
   * This method should not be called. This method throws an exception due to
   * the corresponding child being an NTA shadowing a non-NTA child.
   * @param node
   * @apilevel internal
   */
  public void setSuperInterfaceList(List<Access> node) {
    throw new Error("Can not replace NTA child SuperInterfaceList in AnnotationDecl!");
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
   * Retrieves the child position of the SuperInterface list.
   * @return The the child position of the SuperInterface list.
   * @apilevel low-level
   */
  protected int getSuperInterfaceListChildPosition() {
    return 2;
  }
  /**
   * Retrieves the SuperInterface list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the SuperInterface list.
   * @apilevel low-level
   */
  public List<Access> getSuperInterfaceListNoTransform() {
    return (List<Access>) getChildNoTransform(2);
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
  /** @apilevel internal */
  private void getSuperInterfaceList_reset() {
    getSuperInterfaceList_computed = false;
    
    getSuperInterfaceList_value = null;
  }
  /** @apilevel internal */
  protected boolean getSuperInterfaceList_computed = false;

  /** @apilevel internal */
  protected List getSuperInterfaceList_value;

  /**
   * @attribute syn nta
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:170
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:170")
  public List getSuperInterfaceList() {
    ASTState state = state();
    if (getSuperInterfaceList_computed) {
      return (List) getChild(getSuperInterfaceListChildPosition());
    }
    state().enterLazyAttribute();
    getSuperInterfaceList_value = getSuperInterfaceList_compute();
    setChild(getSuperInterfaceList_value, getSuperInterfaceListChildPosition());
    getSuperInterfaceList_computed = true;
    state().leaveLazyAttribute();
    List node = (List) this.getChild(getSuperInterfaceListChildPosition());
    return node;
  }
  /** @apilevel internal */
  private List getSuperInterfaceList_compute() {
      return new List().add(new TypeAccess("java.lang.annotation", "Annotation"));
    }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:176
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:176")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        for (int i = 0; i < getNumBodyDecl(); i++) {
          if (getBodyDecl(i) instanceof MethodDecl) {
            MethodDecl m = (MethodDecl) getBodyDecl(i);
            if (!m.type().isValidAnnotationMethodReturnType()) {
              problems.add(m.error("invalid type for annotation member"));
            }
            if (m.annotationMethodOverride()) {
              problems.add(m.errorf("annotation method overrides %s", m.signature()));
            }
          }
        }
        if (containsElementOf(this)) {
          problems.add(error("cyclic annotation element type"));
        }
        return problems;
      }
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
  /** @apilevel internal */
  private void containsElementOf_TypeDecl_reset() {
    containsElementOf_TypeDecl_values = null;
  }
  protected java.util.Map containsElementOf_TypeDecl_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:236")
  public boolean containsElementOf(TypeDecl typeDecl) {
    Object _parameters = typeDecl;
    if (containsElementOf_TypeDecl_values == null) containsElementOf_TypeDecl_values = new java.util.HashMap(4);
    ASTState.CircularValue _value;
    if (containsElementOf_TypeDecl_values.containsKey(_parameters)) {
      Object _cache = containsElementOf_TypeDecl_values.get(_parameters);
      if (!(_cache instanceof ASTState.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTState.CircularValue) _cache;
      }
    } else {
      _value = new ASTState.CircularValue();
      containsElementOf_TypeDecl_values.put(_parameters, _value);
      _value.value = false;
    }
    ASTState state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_containsElementOf_TypeDecl_value;
      do {
        _value.cycle = state.nextCycle();
        new_containsElementOf_TypeDecl_value = containsElementOf_compute(typeDecl);
        if (((Boolean)_value.value) != new_containsElementOf_TypeDecl_value) {
          state.setChangeInCycle();
          _value.value = new_containsElementOf_TypeDecl_value;
        }
      } while (state.testAndClearChangeInCycle());
      containsElementOf_TypeDecl_values.put(_parameters, new_containsElementOf_TypeDecl_value);

      state.leaveCircle();
      return new_containsElementOf_TypeDecl_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_containsElementOf_TypeDecl_value = containsElementOf_compute(typeDecl);
      if (((Boolean)_value.value) != new_containsElementOf_TypeDecl_value) {
        state.setChangeInCycle();
        _value.value = new_containsElementOf_TypeDecl_value;
      }
      return new_containsElementOf_TypeDecl_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private boolean containsElementOf_compute(TypeDecl typeDecl) {
      for (int i = 0; i < getNumBodyDecl(); i++) {
        if (getBodyDecl(i) instanceof MethodDecl) {
          MethodDecl m = (MethodDecl) getBodyDecl(i);
          if (m.type() == typeDecl) {
            return true;
          }
          if (m.type() instanceof AnnotationDecl && ((AnnotationDecl) m.type()).containsElementOf(typeDecl)) {
            return true;
          }
        }
      }
      return false;
    }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:771
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:771")
  public boolean isAnnotationDecl() {
    boolean isAnnotationDecl_value = true;
    return isAnnotationDecl_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:131
   * @apilevel internal
   */
  public boolean Define_mayUseAnnotationTarget(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (getModifiersNoTransform() != null && _callerNode == getModifiers()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:141
      return name.equals("ANNOTATION_TYPE") || name.equals("TYPE");
    }
    else {
      return super.Define_mayUseAnnotationTarget(_callerNode, _childNode, name);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:131
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayUseAnnotationTarget
   */
  protected boolean canDefine_mayUseAnnotationTarget(ASTNode _callerNode, ASTNode _childNode, String name) {
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
    // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:174
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
}
