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
/** A superconstructor invocation. 
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:90
 * @production SuperConstructorAccess : {@link ConstructorAccess};

 */
public class SuperConstructorAccess extends ConstructorAccess implements Cloneable {
  /**
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:544
   */
  public soot.Value eval(Body b) {
    Local                     base          = b.emitThis(hostType(), this); // this
    // FIXME: Add a proper attribute instead of doing this stupid thing to get our ctor-decl.
    ConstructorDecl           currCtor      = hostingCtorHack();
    ConstructorDecl           superCtorDecl = decl().erasedConstructor();
    ArrayList<soot.Immediate> list          = new ArrayList<>();

    // fwd this$0/this$1
    if (superCtorDecl.needsEnclosing()) {
      if (hasPrevExpr() && !prevExpr().isTypeAccess()) {
        list.add(asImmediate(b, prevExpr().eval(b)));
      } else if (hostType().needsSuperEnclosing()) {
        int   offset  = hostType().needsEnclosing() ? 1 : 0;
        Local enclose = currCtor.getExplicitisedParameters().getChild(offset).local;
        list.add(asImmediate(b, enclose));
      } else {
        list.add(emitThis(b, superConstructorQualifier(superCtorDecl.hostType().enclosingType())));
      }
    }

    // fwd args
    for (int i = 0; i < getNumArg(); i++)
      list.add(asImmediate(b,
        getArg(i).type().emitCastTo(b, getArg(i), superCtorDecl.getParameter(i).type().erasure()))); // MethodInvocationConversion

    // fwd captured variables from our own params.
    // NOTE: this class's capture fields are not yet initialised.
    // TODO: Certain indirectly captured variables should be fwd'd via a field lookup instead
    for (Map.Entry<Variable, ParameterDeclaration> kv : currCtor.getExplicitisedCaptureParameters().entrySet()) {
      if (hostType().enclosingVariablesHosted().contains(kv.getKey())) continue;

      list.add(asImmediate(b, kv.getValue().local));
    }

    // FIXME: Add forwarding of captured variables hosted in a superclass??

    // is this case even possible/useful?
    //assert !(superCtorDecl.isPrivate() && superCtorDecl.hostType() != hostType());
    // if (superCtorDecl.isPrivate() && ctorDecl.hostType() != hostType()) {
    //   list.add(asImmediate(b, soot.jimple.NullConstant.v()));
    //   b.add(
    //     b.newInvokeStmt(
    //       b.newSpecialInvokeExpr(base, superCtorDecl.createAccessor().sootRef(), list, this),
    //       this
    //     )
    //   );
    //   return base;
    // }

    return b.newSpecialInvokeExpr(base, superCtorDecl.sootRef(), list, this);
  }
  /**
   * @declaredat ASTNode:1
   */
  public SuperConstructorAccess() {
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
    setChild(new List(), 0);
  }
  /**
   * @declaredat ASTNode:14
   */
  public SuperConstructorAccess(String p0, List<Expr> p1) {
    setID(p0);
    setChild(p1, 0);
  }
  /**
   * @declaredat ASTNode:18
   */
  public SuperConstructorAccess(beaver.Symbol p0, List<Expr> p1) {
    setID(p0);
    setChild(p1, 0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:23
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:29
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:33
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    unassignedAfter_Variable_reset();
    decls_reset();
    transformedVariableArity_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:40
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:44
   */
  public SuperConstructorAccess clone() throws CloneNotSupportedException {
    SuperConstructorAccess node = (SuperConstructorAccess) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:49
   */
  public SuperConstructorAccess copy() {
    try {
      SuperConstructorAccess node = (SuperConstructorAccess) clone();
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
   * @declaredat ASTNode:68
   */
  @Deprecated
  public SuperConstructorAccess fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:78
   */
  public SuperConstructorAccess treeCopyNoTransform() {
    SuperConstructorAccess tree = (SuperConstructorAccess) copy();
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
   * @declaredat ASTNode:98
   */
  public SuperConstructorAccess treeCopy() {
    SuperConstructorAccess tree = (SuperConstructorAccess) copy();
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
   * @declaredat ASTNode:112
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((SuperConstructorAccess) node).tokenString_ID);    
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
   * Replaces the Arg list.
   * @param list The new list node to be used as the Arg list.
   * @apilevel high-level
   */
  public void setArgList(List<Expr> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the Arg list.
   * @return Number of children in the Arg list.
   * @apilevel high-level
   */
  public int getNumArg() {
    return getArgList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Arg list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Arg list.
   * @apilevel low-level
   */
  public int getNumArgNoTransform() {
    return getArgListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Arg list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Arg list.
   * @apilevel high-level
   */
  public Expr getArg(int i) {
    return (Expr) getArgList().getChild(i);
  }
  /**
   * Check whether the Arg list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasArg() {
    return getArgList().getNumChild() != 0;
  }
  /**
   * Append an element to the Arg list.
   * @param node The element to append to the Arg list.
   * @apilevel high-level
   */
  public void addArg(Expr node) {
    List<Expr> list = (parent == null) ? getArgListNoTransform() : getArgList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addArgNoTransform(Expr node) {
    List<Expr> list = getArgListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Arg list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setArg(Expr node, int i) {
    List<Expr> list = getArgList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Arg list.
   * @return The node representing the Arg list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Arg")
  public List<Expr> getArgList() {
    List<Expr> list = (List<Expr>) getChild(0);
    return list;
  }
  /**
   * Retrieves the Arg list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Arg list.
   * @apilevel low-level
   */
  public List<Expr> getArgListNoTransform() {
    return (List<Expr>) getChildNoTransform(0);
  }
  /**
   * @return the element at index {@code i} in the Arg list without
   * triggering rewrites.
   */
  public Expr getArgNoTransform(int i) {
    return (Expr) getArgListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the Arg list.
   * @return The node representing the Arg list.
   * @apilevel high-level
   */
  public List<Expr> getArgs() {
    return getArgList();
  }
  /**
   * Retrieves the Arg list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Arg list.
   * @apilevel low-level
   */
  public List<Expr> getArgsNoTransform() {
    return getArgListNoTransform();
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:268
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:268")
  public boolean assignedAfter(Variable v) {
    boolean assignedAfter_Variable_value = v.isInstanceVariable() ? assignedBefore(v) : v.isClassVariable();
    return assignedAfter_Variable_value;
  }
  /** @apilevel internal */
  private void unassignedAfter_Variable_reset() {
    unassignedAfter_Variable_values = null;
  }
  protected java.util.Map unassignedAfter_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:899")
  public boolean unassignedAfter(Variable v) {
    Object _parameters = v;
    if (unassignedAfter_Variable_values == null) unassignedAfter_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (unassignedAfter_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfter_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      unassignedAfter_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfter_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfter_Variable_value = v.isInstanceVariable() ? unassignedBefore(v) : !v.isClassVariable();
        if (new_unassignedAfter_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfter_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfter_Variable_values.put(_parameters, new_unassignedAfter_Variable_value);

      state.leaveCircle();
      return new_unassignedAfter_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfter_Variable_value = v.isInstanceVariable() ? unassignedBefore(v) : !v.isClassVariable();
      if (new_unassignedAfter_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfter_Variable_value;
      }
      return new_unassignedAfter_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private void decls_reset() {
    decls_computed = null;
    decls_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle decls_computed = null;

  /** @apilevel internal */
  protected SimpleSet<ConstructorDecl> decls_value;

  /**
   * @attribute syn
   * @aspect ConstructScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:97
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructScope", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:97")
  public SimpleSet<ConstructorDecl> decls() {
    ASTNode$State state = state();
    if (decls_computed == ASTNode$State.NON_CYCLE || decls_computed == state().cycle()) {
      return decls_value;
    }
    decls_value = decls_compute();
    if (state().inCircle()) {
      decls_computed = state().cycle();
    
    } else {
      decls_computed = ASTNode$State.NON_CYCLE;
    
    }
    return decls_value;
  }
  /** @apilevel internal */
  private SimpleSet<ConstructorDecl> decls_compute() {
      Collection<ConstructorDecl> c = hasPrevExpr() && !prevExpr().isTypeAccess()
          ? hostType().lookupSuperConstructor()
          : lookupSuperConstructor();
      return chooseConstructor(c, getArgList());
    }
  /**
   * @attribute syn
   * @aspect Names
   * @declaredat /home/olivier/projects/extendj/java4/frontend/QualifiedNames.jrag:38
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Names", declaredAt="/home/olivier/projects/extendj/java4/frontend/QualifiedNames.jrag:38")
  public String name() {
    String name_value = "super";
    return name_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:80
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:80")
  public boolean isSuperConstructorAccess() {
    boolean isSuperConstructorAccess_value = true;
    return isSuperConstructorAccess_value;
  }
  /**
   * Defines the expected kind of name for the left hand side in a qualified
   * expression.
   * @attribute syn
   * @aspect SyntacticClassification
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:60
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SyntacticClassification", declaredAt="/home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:60")
  public NameType predNameType() {
    NameType predNameType_value = NameType.EXPRESSION_NAME;
    return predNameType_value;
  }
  /**
   * @attribute syn
   * @aspect TypeHierarchyCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:106
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:106")
  public Collection<Problem> typeHierarchyProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        // JLS 5?: 8.8.5.1
        // JLS 7: 8.8.7.1
        TypeDecl c = hostType();
        TypeDecl s = c.isClassDecl() ? ((ClassDecl) c).superclass() : unknownType();
        if (isQualified()) {
          if (!s.isInnerType() || s.inStaticContext()) {
            problems.add(errorf("the super type %s of %s is not an inner class",
                s.typeName(), c.typeName()));
          } else if (!qualifier().type().instanceOf(s.enclosingType())) {
            problems.add(errorf(
                "the type of this primary expression, %s is not enclosing the super type, %s, of %s",
                qualifier().type().typeName(), s.typeName(), c.typeName()));
          }
        }
        if (!isQualified() && s.isInnerType() && !c.hasEnclosingType(s.enclosingType())) {
          problems.add(errorf("superconstructor call in constructor for class %s requires "
              + "enclosing instance for the enclosing class %s of superclass %s",
              c.name(), s.enclosingType().name(), s.typeName()));
        }
        if (s.isInnerType() && hostType().instanceOf(s.enclosingType())) {
          problems.add(error("cannot reference 'this' before supertype constructor has been called"));
        }
        return problems;
      }
  }
  /** @apilevel internal */
  private void transformedVariableArity_reset() {
    transformedVariableArity_computed = false;
    
    transformedVariableArity_value = null;
  }
  /** @apilevel internal */
  protected boolean transformedVariableArity_computed = false;

  /** @apilevel internal */
  protected ConstructorAccess transformedVariableArity_value;

  /**
   * @attribute syn
   * @aspect VariableArityParametersCodegen
   * @declaredat /home/olivier/projects/extendj/java5/backend/VariableArityParametersCodegen.jrag:132
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="VariableArityParametersCodegen", declaredAt="/home/olivier/projects/extendj/java5/backend/VariableArityParametersCodegen.jrag:132")
  public ConstructorAccess transformedVariableArity() {
    ASTNode$State state = state();
    if (transformedVariableArity_computed) {
      return transformedVariableArity_value;
    }
    state().enterLazyAttribute();
    transformedVariableArity_value = transformedVariableArity_compute();
    transformedVariableArity_value.setParent(this);
    transformedVariableArity_computed = true;
    state().leaveLazyAttribute();
    return transformedVariableArity_value;
  }
  /** @apilevel internal */
  private ConstructorAccess transformedVariableArity_compute() {
      ConstructorDecl decl = decl();
      // Arguments to normal parameters.
      List<Expr> args = new List<Expr>();
      for (int i = 0; i < decl.getNumParameter() - 1; i++) {
        args.add(getArg(i).treeCopyNoTransform());
      }
      // Arguments to variable arity parameters.
      List<Expr> last = new List<Expr>();
      for (int i = decl.getNumParameter() - 1; i < getNumArg(); i++) {
        last.add(getArg(i).treeCopyNoTransform());
      }
      // Build an array holding arguments.
      Access typeAccess = decl.lastParameter().type().elementType().createQualifiedAccess();
      for (int i = 0; i < decl.lastParameter().type().dimension(); i++) {
        typeAccess = new ArrayTypeAccess(typeAccess);
      }
      args.add(new ArrayCreationExpr(typeAccess, new Opt(new ArrayInit(last))));
      return new SuperConstructorAccess(getID(), args);
    }
  /**
   * @attribute syn
   * @aspect ResolverDependencies
   * @declaredat /home/olivier/projects/extendj/soot8/backend/ResolverDependencies.jrag:10
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ResolverDependencies", declaredAt="/home/olivier/projects/extendj/soot8/backend/ResolverDependencies.jrag:10")
  public TypeDecl sootSigDepType() {
    TypeDecl sootSigDepType_value = decl().erasedConstructor().hostType().elementType().erasure();
    return sootSigDepType_value;
  }
  /**
   * @attribute inh
   * @aspect ConstructScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:43
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="ConstructScope", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:43")
  public Collection<ConstructorDecl> lookupSuperConstructor() {
    Collection<ConstructorDecl> lookupSuperConstructor_value = getParent().Define_lookupSuperConstructor(this, null);
    return lookupSuperConstructor_value;
  }
  /**
   * @attribute inh
   * @aspect TypeCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:663
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:663")
  public TypeDecl enclosingInstance() {
    TypeDecl enclosingInstance_value = getParent().Define_enclosingInstance(this, null);
    return enclosingInstance_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:109
   * @apilevel internal
   */
  public boolean Define_hasPackage(ASTNode _callerNode, ASTNode _childNode, String packageName) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:114
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return unqualifiedScope().hasPackage(packageName);
    }
    else {
      return super.Define_hasPackage(_callerNode, _childNode, packageName);
    }
  }
  protected boolean canDefine_hasPackage(ASTNode _callerNode, ASTNode _childNode, String packageName) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet<Variable> Define_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:245
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return unqualifiedScope().lookupVariable(name);
    }
    else {
      return super.Define_lookupVariable(_callerNode, _childNode, name);
    }
  }
  protected boolean canDefine_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:188
   * @apilevel internal
   */
  public boolean Define_inExplicitConstructorInvocation(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:192
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return true;
    }
    else {
      return super.Define_inExplicitConstructorInvocation(_callerNode, _childNode);
    }
  }
  protected boolean canDefine_inExplicitConstructorInvocation(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:196
   * @apilevel internal
   */
  public TypeDecl Define_enclosingExplicitConstructorHostType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getArgListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:201
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return hostType();
    }
    else {
      return super.Define_enclosingExplicitConstructorHostType(_callerNode, _childNode);
    }
  }
  protected boolean canDefine_enclosingExplicitConstructorHostType(ASTNode _callerNode, ASTNode _childNode) {
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
    // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:104
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
  protected void collect_contributors_CompilationUnit_signatureDependencies(CompilationUnit _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat /home/olivier/projects/extendj/soot8/backend/ResolverDependencies.jrag:41
    if (sootSigDepType().sootDependencyNeeded()) {
      {
        java.util.Set<ASTNode> contributors = _map.get(_root);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) _root, contributors);
        }
        contributors.add(this);
      }
    }
    super.collect_contributors_CompilationUnit_signatureDependencies(_root, _map);
  }
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    for (Problem value : typeHierarchyProblems()) {
      collection.add(value);
    }
  }
  protected void contributeTo_CompilationUnit_signatureDependencies(HashSet<Type> collection) {
    super.contributeTo_CompilationUnit_signatureDependencies(collection);
    if (sootSigDepType().sootDependencyNeeded()) {
      collection.add(sootSigDepType().getSootType());
    }
  }
}
