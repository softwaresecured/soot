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
 * A resource declaration in a try with resources statement.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java7/grammar/TryWithResources.ast:9
 * @astdecl ResourceDeclaration : VariableDeclarator ::= ResourceModifiers ResourceType:Access;
 * @production ResourceDeclaration : {@link VariableDeclarator} ::= <span class="component">{@link ResourceModifiers}</span> <span class="component">ResourceType:{@link Access}</span>;

 */
public class ResourceDeclaration extends VariableDeclarator implements Cloneable {
  /**
   * @aspect Java7PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PrettyPrint.jadd:56
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getTypeAccess());
    out.print(" ");
    out.print(getID());
    out.print(getDimsList());
    if (hasInit()) {
      out.print(" = ");
      out.print(getInit());
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public ResourceDeclaration() {
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
    setChild(new List(), 0);
    setChild(new Opt(), 1);
  }
  /**
   * @declaredat ASTNode:15
   */
  @ASTNodeAnnotation.Constructor(
    name = {"ID", "Dims", "Init", "ResourceModifiers", "ResourceType"},
    type = {"String", "List<Dims>", "Opt<Expr>", "ResourceModifiers", "Access"},
    kind = {"Token", "List", "Opt", "Child", "Child"}
  )
  public ResourceDeclaration(String p0, List<Dims> p1, Opt<Expr> p2, ResourceModifiers p3, Access p4) {
    setID(p0);
    setChild(p1, 0);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
  }
  /**
   * @declaredat ASTNode:27
   */
  public ResourceDeclaration(beaver.Symbol p0, List<Dims> p1, Opt<Expr> p2, ResourceModifiers p3, Access p4) {
    setID(p0);
    setChild(p1, 0);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:35
   */
  protected int numChildren() {
    return 4;
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
    closeAccess_reset();
    getModifiers_reset();
    getTypeAccess_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:52
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:56
   */
  public ResourceDeclaration clone() throws CloneNotSupportedException {
    ResourceDeclaration node = (ResourceDeclaration) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:61
   */
  public ResourceDeclaration copy() {
    try {
      ResourceDeclaration node = (ResourceDeclaration) clone();
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
   * @declaredat ASTNode:80
   */
  @Deprecated
  public ResourceDeclaration fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:90
   */
  public ResourceDeclaration treeCopyNoTransform() {
    ResourceDeclaration tree = (ResourceDeclaration) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 4:
          tree.children[i] = null;
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
   * @declaredat ASTNode:115
   */
  public ResourceDeclaration treeCopy() {
    ResourceDeclaration tree = (ResourceDeclaration) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 4:
          tree.children[i] = null;
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
   * @declaredat ASTNode:134
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((ResourceDeclaration) node).tokenString_ID);    
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
   * Replaces the Dims list.
   * @param list The new list node to be used as the Dims list.
   * @apilevel high-level
   */
  public void setDimsList(List<Dims> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the Dims list.
   * @return Number of children in the Dims list.
   * @apilevel high-level
   */
  public int getNumDims() {
    return getDimsList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Dims list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Dims list.
   * @apilevel low-level
   */
  public int getNumDimsNoTransform() {
    return getDimsListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Dims list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Dims list.
   * @apilevel high-level
   */
  public Dims getDims(int i) {
    return (Dims) getDimsList().getChild(i);
  }
  /**
   * Check whether the Dims list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasDims() {
    return getDimsList().getNumChild() != 0;
  }
  /**
   * Append an element to the Dims list.
   * @param node The element to append to the Dims list.
   * @apilevel high-level
   */
  public void addDims(Dims node) {
    List<Dims> list = (parent == null) ? getDimsListNoTransform() : getDimsList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addDimsNoTransform(Dims node) {
    List<Dims> list = getDimsListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Dims list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setDims(Dims node, int i) {
    List<Dims> list = getDimsList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Dims list.
   * @return The node representing the Dims list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Dims")
  public List<Dims> getDimsList() {
    List<Dims> list = (List<Dims>) getChild(0);
    return list;
  }
  /**
   * Retrieves the Dims list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Dims list.
   * @apilevel low-level
   */
  public List<Dims> getDimsListNoTransform() {
    return (List<Dims>) getChildNoTransform(0);
  }
  /**
   * @return the element at index {@code i} in the Dims list without
   * triggering rewrites.
   */
  public Dims getDimsNoTransform(int i) {
    return (Dims) getDimsListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the Dims list.
   * @return The node representing the Dims list.
   * @apilevel high-level
   */
  public List<Dims> getDimss() {
    return getDimsList();
  }
  /**
   * Retrieves the Dims list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Dims list.
   * @apilevel low-level
   */
  public List<Dims> getDimssNoTransform() {
    return getDimsListNoTransform();
  }
  /**
   * Replaces the optional node for the Init child. This is the <code>Opt</code>
   * node containing the child Init, not the actual child!
   * @param opt The new node to be used as the optional node for the Init child.
   * @apilevel low-level
   */
  public void setInitOpt(Opt<Expr> opt) {
    setChild(opt, 1);
  }
  /**
   * Replaces the (optional) Init child.
   * @param node The new node to be used as the Init child.
   * @apilevel high-level
   */
  public void setInit(Expr node) {
    getInitOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional Init child exists.
   * @return {@code true} if the optional Init child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasInit() {
    return getInitOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) Init child.
   * @return The Init child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Expr getInit() {
    return (Expr) getInitOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the Init child. This is the <code>Opt</code> node containing the child Init, not the actual child!
   * @return The optional node for child the Init child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="Init")
  public Opt<Expr> getInitOpt() {
    return (Opt<Expr>) getChild(1);
  }
  /**
   * Retrieves the optional node for child Init. This is the <code>Opt</code> node containing the child Init, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Init.
   * @apilevel low-level
   */
  public Opt<Expr> getInitOptNoTransform() {
    return (Opt<Expr>) getChildNoTransform(1);
  }
  /**
   * Replaces the ResourceModifiers child.
   * @param node The new node to replace the ResourceModifiers child.
   * @apilevel high-level
   */
  public void setResourceModifiers(ResourceModifiers node) {
    setChild(node, 2);
  }
  /**
   * Retrieves the ResourceModifiers child.
   * @return The current node used as the ResourceModifiers child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="ResourceModifiers")
  public ResourceModifiers getResourceModifiers() {
    return (ResourceModifiers) getChild(2);
  }
  /**
   * Retrieves the ResourceModifiers child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the ResourceModifiers child.
   * @apilevel low-level
   */
  public ResourceModifiers getResourceModifiersNoTransform() {
    return (ResourceModifiers) getChildNoTransform(2);
  }
  /**
   * Replaces the ResourceType child.
   * @param node The new node to replace the ResourceType child.
   * @apilevel high-level
   */
  public void setResourceType(Access node) {
    setChild(node, 3);
  }
  /**
   * Retrieves the ResourceType child.
   * @return The current node used as the ResourceType child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="ResourceType")
  public Access getResourceType() {
    return (Access) getChild(3);
  }
  /**
   * Retrieves the ResourceType child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the ResourceType child.
   * @apilevel low-level
   */
  public Access getResourceTypeNoTransform() {
    return (Access) getChildNoTransform(3);
  }
  /**
   * Retrieves the TypeAccess child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the TypeAccess child.
   * @apilevel low-level
   */
  public Access getTypeAccessNoTransform() {
    return (Access) getChildNoTransform(4);
  }
  /**
   * Retrieves the child position of the optional child TypeAccess.
   * @return The the child position of the optional child TypeAccess.
   * @apilevel low-level
   */
  protected int getTypeAccessChildPosition() {
    return 4;
  }
  /**
   * Type checking for TWR.
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:47
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:47")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        TypeDecl typeAutoCloseable = lookupType("java.lang", "AutoCloseable");
        if (typeAutoCloseable.isUnknown()) {
          problems.add(error("java.lang.AutoCloseable not found"));
        } else if (!getTypeAccess().type().instanceOf(typeAutoCloseable)) {
          problems.add(error("Resource specification must declare an AutoCloseable resource"));
        }
        return problems;
      }
  }
  /** @apilevel internal */
  private void closeAccess_reset() {
    closeAccess_computed = false;
    
    closeAccess_value = null;
  }
  /** @apilevel internal */
  protected boolean closeAccess_computed = false;

  /** @apilevel internal */
  protected MethodAccess closeAccess_value;

  /**
   * A synthetic method access to the resource closing method.
   * 
   * <p>This is used to find the actual close method. This is needed for precise
   * exception analysis.
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:247
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:247")
  public MethodAccess closeAccess() {
    ASTState state = state();
    if (closeAccess_computed) {
      return closeAccess_value;
    }
    state().enterLazyAttribute();
    closeAccess_value = new MethodAccess("close", new List<Expr>());
    closeAccess_value.setParent(this);
    closeAccess_computed = true;
    state().leaveLazyAttribute();
    return closeAccess_value;
  }
  /** @apilevel internal */
  private void getModifiers_reset() {
    getModifiers_computed = null;
    getModifiers_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle getModifiers_computed = null;

  /** @apilevel internal */
  protected Modifiers getModifiers_value;

  /** Modifiers are same as the parent declaration (e.g. VarDeclStmt). 
   * @attribute syn
   * @aspect VariableDeclarationTransformation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/VariableDeclaration.jrag:130
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="VariableDeclarationTransformation", declaredAt="/home/olivier/projects/extendj/java4/frontend/VariableDeclaration.jrag:130")
  public Modifiers getModifiers() {
    ASTState state = state();
    if (getModifiers_computed == ASTState.NON_CYCLE || getModifiers_computed == state().cycle()) {
      return getModifiers_value;
    }
    getModifiers_value = getResourceModifiers();
    if (state().inCircle()) {
      getModifiers_computed = state().cycle();
    
    } else {
      getModifiers_computed = ASTState.NON_CYCLE;
    
    }
    return getModifiers_value;
  }
  /** @apilevel internal */
  private void getTypeAccess_reset() {
    getTypeAccess_computed = false;
    
    getTypeAccess_value = null;
  }
  /** @apilevel internal */
  protected boolean getTypeAccess_computed = false;

  /** @apilevel internal */
  protected Access getTypeAccess_value;

  /**
   * @attribute syn nta
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:300
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:300")
  public Access getTypeAccess() {
    ASTState state = state();
    if (getTypeAccess_computed) {
      return (Access) getChild(getTypeAccessChildPosition());
    }
    state().enterLazyAttribute();
    getTypeAccess_value = getResourceType().treeCopyNoTransform();
    setChild(getTypeAccess_value, getTypeAccessChildPosition());
    getTypeAccess_computed = true;
    state().leaveLazyAttribute();
    Access node = (Access) this.getChild(getTypeAccessChildPosition());
    return node;
  }
  /**
   * Inherit the lookupType attribute in ResourceDeclaration.
   * @attribute inh
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:40
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:40")
  public TypeDecl lookupType(String packageName, String typeName) {
    TypeDecl lookupType_String_String_value = getParent().Define_lookupType(this, null, packageName, typeName);
    return lookupType_String_String_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    if (getResourceTypeNoTransform() != null && _callerNode == getResourceType()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:310
      return NameType.TYPE_NAME;
    }
    else if (getTypeAccessNoTransform() != null && _callerNode == getTypeAccess()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:35
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:116
   * @apilevel internal
   */
  public Collection<MethodDecl> Define_lookupMethod(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (_callerNode == closeAccess_value) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:251
      return getResourceType().type().memberMethods(name);
    }
    else {
      return getParent().Define_lookupMethod(this, _callerNode, name);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:116
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupMethod
   */
  protected boolean canDefine_lookupMethod(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:223
   * @apilevel internal
   */
  public boolean Define_inStaticContext(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == closeAccess_value) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:256
      return false;
    }
    else {
      return getParent().Define_inStaticContext(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:223
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute inStaticContext
   */
  protected boolean canDefine_inStaticContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:434
   * @apilevel internal
   */
  public boolean Define_mayBeFinal(ASTNode _callerNode, ASTNode _childNode) {
    if (getResourceModifiersNoTransform() != null && _callerNode == getResourceModifiers()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:308
      return true;
    }
    else {
      return getParent().Define_mayBeFinal(this, _callerNode);
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
    // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:42
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:194
    if (resourcePreviouslyDeclared(name())) {
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
  /** @apilevel internal */
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    for (Problem value : typeProblems()) {
      collection.add(value);
    }
    if (resourcePreviouslyDeclared(name())) {
      collection.add(errorf("A resource with the name %s has already been declared in this try statement.",
                name()));
    }
  }
}
