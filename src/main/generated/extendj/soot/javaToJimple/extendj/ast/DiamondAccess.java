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
 * Type access for a generic class with an empty type parameter list.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java7/grammar/Diamond.ast:4
 * @astdecl DiamondAccess : Access ::= TypeAccess:Access;
 * @production DiamondAccess : {@link Access} ::= <span class="component">TypeAccess:{@link Access}</span>;

 */
public class DiamondAccess extends Access implements Cloneable {
  /**
   * Choose a constructor for the diamond operator using stand-in
   * methods. One stand-in method is generated for each constructor
   * of the generic type. Type inference is then used to select the
   * type arguments for the method, which can be used as the type
   * arguments of the generic type.
   * @aspect Diamond
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:119
   */
  protected SimpleSet<MethodDecl> chooseConstructor() {
    TypeDecl type = getTypeAccess().type();

    assert type instanceof ParClassDecl;

    GenericClassDecl genericType = (GenericClassDecl) ((ParClassDecl) type).genericDecl();

    List<MethodDecl> placeholderMethods = genericType.getStandInMethodList();
    return methodAccess().maxSpecific(placeholderMethods);
  }
  /**
   * @aspect Java7PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PrettyPrint.jadd:46
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getTypeAccess());
    out.print("<>");
  }
  /**
   * @declaredat ASTNode:1
   */
  public DiamondAccess() {
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
    children = new ASTNode[1];
  }
  /**
   * @declaredat ASTNode:13
   */
  @ASTNodeAnnotation.Constructor(
    name = {"TypeAccess"},
    type = {"Access"},
    kind = {"Child"}
  )
  public DiamondAccess(Access p0) {
    setChild(p0, 0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:22
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:28
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:32
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    methodAccess_reset();
    type_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:38
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:42
   */
  public DiamondAccess clone() throws CloneNotSupportedException {
    DiamondAccess node = (DiamondAccess) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:47
   */
  public DiamondAccess copy() {
    try {
      DiamondAccess node = (DiamondAccess) clone();
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
   * @declaredat ASTNode:66
   */
  @Deprecated
  public DiamondAccess fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:76
   */
  public DiamondAccess treeCopyNoTransform() {
    DiamondAccess tree = (DiamondAccess) copy();
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
   * @declaredat ASTNode:96
   */
  public DiamondAccess treeCopy() {
    DiamondAccess tree = (DiamondAccess) copy();
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
   * @declaredat ASTNode:110
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the TypeAccess child.
   * @param node The new node to replace the TypeAccess child.
   * @apilevel high-level
   */
  public void setTypeAccess(Access node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the TypeAccess child.
   * @return The current node used as the TypeAccess child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="TypeAccess")
  public Access getTypeAccess() {
    return (Access) getChild(0);
  }
  /**
   * Retrieves the TypeAccess child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the TypeAccess child.
   * @apilevel low-level
   */
  public Access getTypeAccessNoTransform() {
    return (Access) getChildNoTransform(0);
  }
  /** @apilevel internal */
  private void methodAccess_reset() {
    methodAccess_computed = false;
    
    methodAccess_value = null;
  }
  /** @apilevel internal */
  protected boolean methodAccess_computed = false;

  /** @apilevel internal */
  protected MethodAccess methodAccess_value;

  /**
   * Creates a synthetic method access that is used to perform type inference
   * for the diamond access.
   * @attribute syn
   * @aspect Diamond
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:56
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="Diamond", declaredAt="/home/olivier/projects/extendj/java7/frontend/Diamond.jrag:56")
  public MethodAccess methodAccess() {
    ASTState state = state();
    if (methodAccess_computed) {
      return methodAccess_value;
    }
    state().enterLazyAttribute();
    methodAccess_value = new MethodAccess("#" + getTypeAccess().type().name(),
              getClassInstanceExpr().getArgList().treeCopyNoTransform());
    methodAccess_value.setParent(this);
    methodAccess_computed = true;
    state().leaveLazyAttribute();
    return methodAccess_value;
  }
  /** @apilevel internal */
  private void type_reset() {
    type_computed = null;
    type_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle type_computed = null;

  /** @apilevel internal */
  protected TypeDecl type_value;

  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:295
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:295")
  public TypeDecl type() {
    ASTState state = state();
    if (type_computed == ASTState.NON_CYCLE || type_computed == state().cycle()) {
      return type_value;
    }
    type_value = type_compute();
    if (state().inCircle()) {
      type_computed = state().cycle();
    
    } else {
      type_computed = ASTState.NON_CYCLE;
    
    }
    return type_value;
  }
  /** @apilevel internal */
  private TypeDecl type_compute() {
      TypeDecl accessType = getTypeAccess().type();
  
      if (isAnonymousDecl()) {
        return accessType;
      }
  
      if (getClassInstanceExpr() == null) {
        // It is an error if the DiamondAccess does not occurr
        // within a class instance creation expression, but this
        // error is handled in typeCheck.
        return accessType;
      }
  
      if (!(accessType instanceof ParClassDecl)) {
        // It is an error if the TypeDecl of a DiamondAccess is not
        // a generic type, but this error is handled in typeCheck.
        return accessType;
      }
  
      SimpleSet<MethodDecl> maxSpecific = chooseConstructor();
  
      if (maxSpecific.isEmpty()) {
        return getTypeAccess().type();
      }
  
      MethodDecl constructor = maxSpecific.iterator().next();
      return constructor.type();
    }
  /**
   * @attribute syn
   * @aspect Diamond
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:99
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Diamond", declaredAt="/home/olivier/projects/extendj/java7/frontend/Diamond.jrag:99")
  public boolean isDiamond() {
    boolean isDiamond_value = true;
    return isDiamond_value;
  }
  /**
   * Checks if this diamond access is legal.
   * 
   * <p>The diamond access is not legal if it either is part of an inner class
   * declaration, if it is used to access a non-generic type, or if it is
   * part of a call to a generic constructor with explicit type arguments.
   * @attribute syn
   * @aspect Diamond
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:338
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Diamond", declaredAt="/home/olivier/projects/extendj/java7/frontend/Diamond.jrag:338")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        if (isAnonymousDecl()) {
          problems.add(error("the diamond operator can not be used with anonymous classes"));
        }
        if (isExplicitGenericConstructorAccess()) {
          problems.add(error("the diamond operator may not be used with generic "
              + "constructors with explicit type parameters"));
        }
        if (getClassInstanceExpr() == null) {
          problems.add(error("the diamond operator can only be used in class instance expressions"));
        }
        if (!(getTypeAccess().type() instanceof ParClassDecl)) {
          problems.add(error("the diamond operator can only be used to instantiate generic classes"));
        }
        return problems;
      }
  }
  /**
   * @attribute syn
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:42
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Expressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:42")
  public Value eval(Body b) {
    Value eval_Body_value = eval_fail_unerased_generics();
    return eval_Body_value;
  }
  /**
   * @return the nearest enclosing class instance expression, or {@code null}
   * if there is no enclosing class instance expression.
   * @attribute inh
   * @aspect Diamond
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:106
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Diamond", declaredAt="/home/olivier/projects/extendj/java7/frontend/Diamond.jrag:106")
  public ClassInstanceExpr getClassInstanceExpr() {
    ClassInstanceExpr getClassInstanceExpr_value = getParent().Define_getClassInstanceExpr(this, null);
    return getClassInstanceExpr_value;
  }
  /**
   * @return true if this access is part of an anonymous class declaration
   * @attribute inh
   * @aspect Diamond
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:307
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Diamond", declaredAt="/home/olivier/projects/extendj/java7/frontend/Diamond.jrag:307")
  public boolean isAnonymousDecl() {
    boolean isAnonymousDecl_value = getParent().Define_isAnonymousDecl(this, null);
    return isAnonymousDecl_value;
  }
  /**
   * @return true if the Access is part of a generic constructor invocation
   * with explicit type arguments
   * @attribute inh
   * @aspect Diamond
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:323
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Diamond", declaredAt="/home/olivier/projects/extendj/java7/frontend/Diamond.jrag:323")
  public boolean isExplicitGenericConstructorAccess() {
    boolean isExplicitGenericConstructorAccess_value = getParent().Define_isExplicitGenericConstructorAccess(this, null);
    return isExplicitGenericConstructorAccess_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethods.jrag:231
   * @apilevel internal
   */
  public SimpleSet<TypeDecl> Define_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (_callerNode == methodAccess_value) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:61
      return unqualifiedScope().lookupType(name);
    }
    else {
      return getParent().Define_lookupType(this, _callerNode, name);
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
    // @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:329
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
