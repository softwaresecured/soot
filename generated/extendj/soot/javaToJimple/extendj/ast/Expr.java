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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:194
 * @production Expr : {@link ASTNode};

 */
public abstract class Expr extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @aspect TypeScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:635
   */
  public SimpleSet<TypeDecl> keepAccessibleTypes(SimpleSet<TypeDecl> types) {
    SimpleSet<TypeDecl> result = emptySet();
    TypeDecl hostType = hostType();
    for (TypeDecl type : types) {
      if ((hostType != null && type.accessibleFrom(hostType))
          || (hostType == null && type.accessibleFromPackage(hostPackage()))) {
        result = result.add(type);
      }
    }
    return result;
  }
  /**
   * Remove fields that are not accessible when using this Expr as qualifier
   * @return a set containing the accessible fields
   * @aspect VariableScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:289
   */
  public SimpleSet<Variable> keepAccessibleFields(SimpleSet<Variable> fields) {
    SimpleSet<Variable> newSet = emptySet();
    for (Variable f : fields) {
      if (mayAccess(f)) {
        newSet = newSet.add(f);
      }
    }
    return newSet;
  }
  /**
   * @see "JLS $6.6.2.1"
   * @return true if the expression may access the given field
   * @aspect VariableScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:314
   */
  public boolean mayAccess(Variable f) { return hostType().mayAccess(f); }
  /**
   * Creates a qualified expression. This will not be subject to rewriting.
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:201
   */
  public Access qualifiesAccess(Access access) {
    Dot dot = new Dot(this, access);
    dot.setStart(this.getStart());
    dot.setEnd(access.getEnd());
    return dot;
  }
  /**
   * Infer type arguments based on the actual arguments and result assignment type.
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:91
   */
  public Collection<TypeDecl> computeConstraints(
      TypeDecl resultType,
      List<ParameterDeclaration> params,
      List<Expr> args,
      List<TypeVariable> typeParams) {
    Constraints constraints = new Constraints();

    // Store type parameters.
    for (TypeVariable p : typeParams) {
      constraints.addTypeVariable(p);
    }

    // Add initial constraints.
    for (int i = 0; i < args.getNumChild(); i++) {
      TypeDecl A = args.getChild(i).type();
      int index = i >= params.getNumChild() ? params.getNumChild() - 1 : i;
      TypeDecl F = params.getChild(index).type();
      if (params.getChild(index) instanceof VariableArityParameterDeclaration
         && (args.getNumChild() != params.getNumChild() || !A.isArrayDecl())) {
        F = F.componentType();
      }
      constraints.convertibleTo(A, F);
    }

    if (constraints.rawAccess) {
      return new ArrayList<TypeDecl>();
    }

    constraints.resolveEqualityConstraints();

    constraints.resolveSupertypeConstraints();

    if (constraints.unresolvedTypeArguments()) {
      TypeDecl S = assignConvertedType();
      if (S.isUnboxedPrimitive()) {
        S = S.boxed();
      }
      TypeDecl R = resultType;
      if (R.isVoid()) {
        R = typeObject();
      }
      constraints.convertibleFrom(S, R);
      // Build the last constraints by substituting type bounds using the
      // already-inferred type variables.
      Collection<TypeDecl> substArgs = new ArrayList<TypeDecl>();
      for (TypeVariable p : typeParams) {
        TypeDecl arg = constraints.constraintsMap.get(p).typeArgument;
        if (arg != null) {
          substArgs.add(arg);
        } else {
          substArgs.add(p);
        }
      }
      Parameterization partialSubst = new Parameterization(typeParams, substArgs);
      for (TypeVariable p : typeParams) {
        if (p.hasTypeBound() && !p.getTypeBound(0).isTypeAccess("java.lang", "Object")) {
          TypeDecl Bp = constraints.constraintsMap.get(p).typeArgument;
          if (Bp == null) {
            Bp = p;
          }
          TypeDecl Bi_sub = p.substituted(partialSubst).lowerBound();
          constraints.convertibleFrom(Bi_sub, p);
          constraints.convertibleTo(Bp, Bi_sub);
        }
      }
      constraints.resolveEqualityConstraints();
      constraints.resolveSupertypeConstraints();
      constraints.resolveSubtypeConstraints();
    }

    return constraints.typeArguments();
  }
  /**
   * @aspect MethodSignature15
   * @declaredat /home/olivier/projects/extendj/java5/frontend/MethodSignature.jrag:160
   */
  protected static SimpleSet<ConstructorDecl> mostSpecific(
      SimpleSet<ConstructorDecl> maxSpecific, ConstructorDecl decl) {
    if (maxSpecific.isEmpty()) {
      maxSpecific = maxSpecific.add(decl);
    } else {
      ConstructorDecl other = maxSpecific.iterator().next();
      if (decl.moreSpecificThan(other)) {
        maxSpecific = ASTNode.<ConstructorDecl>emptySet().add(decl);
      } else if (!other.moreSpecificThan(decl)) {
        maxSpecific = maxSpecific.add(decl);
      }
    }
    return maxSpecific;
  }
  /**
   * @aspect MethodSignature18
   * @declaredat /home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:1111
   */
  protected static boolean moreSpecificThan(ConstructorDecl m1, ConstructorDecl m2,
      List<Expr> argList) {
    if (m1 instanceof ParConstructorDecl) {
      return m1.moreSpecificThan(m2);
    }
    if (m1.getNumParameter() == 0) {
      return false;
    }
    if (!m1.isVariableArity() && !m2.isVariableArity()) {
      for (int i = 0; i < m1.getNumParameter(); i++) {
        Expr arg = argList.getChild(i);
        if (!arg.moreSpecificThan(m1.getParameter(i).type(), m2.getParameter(i).type())) {
          return false;
        }
      }
      return true;
    }

    boolean expandVarargs = m1.isVariableArity() && m2.isVariableArity();

    int num = argList.getNumChild();
    for (int i = 0; i < num; i++) {
      ParameterDeclaration p1 = i < m1.getNumParameter()
          ? m1.getParameter(i)
          : m1.getParameter(m1.getNumParameter() - 1);
      ParameterDeclaration p2 = i < m2.getNumParameter()
          ? m2.getParameter(i)
          : m2.getParameter(m2.getNumParameter() - 1);
      TypeDecl t1 = expandVarargs && p1.isVariableArity() ? p1.type().componentType() : p1.type();
      TypeDecl t2 = expandVarargs && p2.isVariableArity() ? p2.type().componentType() : p2.type();
      Expr arg = argList.getChild(i);
      if (!arg.moreSpecificThan(t1, t2)) {
          return false;
      }
    }
    num++;
    if (m2.getNumParameter() == num) {
      ParameterDeclaration p1 = num < m1.getNumParameter()
          ? m1.getParameter(num)
          : m1.getParameter(m1.getNumParameter() - 1);
      ParameterDeclaration p2 = num < m2.getNumParameter()
          ? m2.getParameter(num)
          : m2.getParameter(m2.getNumParameter() - 1);
      TypeDecl t1 = expandVarargs && p1.isVariableArity() ? p1.type().componentType() : p1.type();
      TypeDecl t2 = expandVarargs && p2.isVariableArity() ? p2.type().componentType() : p2.type();
      if (!t1.instanceOf(t2) && !t1.withinBounds(t2)) {
        return false;
      }
    }
    return true;
  }
  /**
   * @aspect MethodSignature18
   * @declaredat /home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:1163
   */
  protected static SimpleSet<ConstructorDecl> mostSpecific(
      SimpleSet<ConstructorDecl> maxSpecific, ConstructorDecl decl, List<Expr> argList) {
    SimpleSet<ConstructorDecl> newMax;
    if (maxSpecific.isEmpty()) {
      newMax = maxSpecific.add(decl);
    } else {
      boolean foundStricter = false;
      newMax = emptySet();
      for (ConstructorDecl toCompare : maxSpecific) {
        if (!(moreSpecificThan(decl, toCompare, argList)
            && !moreSpecificThan(toCompare, decl, argList))) {
          newMax = newMax.add(toCompare);
        }
        if (!moreSpecificThan(decl, toCompare, argList)
            && moreSpecificThan(toCompare, decl, argList)) {
          foundStricter = true;
        }
      }
      if (!foundStricter) {
        newMax = newMax.add(decl);
      }
    }
    return newMax;
  }
  /**
   * @aspect BooleanExpressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:105
   */
  protected soot.Value emitBooleanCondition(Body b) {
    //b.setLine(this);
    emitEvalBranch(b);
    soot.jimple.Stmt end_label = newLabel();
    b.addLabel(false_label());
    Local result = b.newTemp(soot.BooleanType.v(), this);
    b.add(b.newAssignStmt(result, BooleanType.emitConstant(false, b, this), this));
    b.add(b.newGotoStmt(end_label, this));
    b.addLabel(true_label());
    b.add(b.newAssignStmt(result, BooleanType.emitConstant(true, b, this), this));
    b.addLabel(end_label);
    return result;
  }
  /**
   * @aspect BooleanExpressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:158
   */
  public void refined_BooleanExpressions_Expr_emitEvalBranch(Body b) {
    //b.setLine(this);
    // const-expr
    if (isTrue() || isFalse()) {
      soot.jimple.Stmt lbl = isTrue() ? true_label() : false_label();
      b.add(b.newGotoStmt(lbl, this));
      return;
    }

    b.add(
      b.newIfStmt(
        b.newEqExpr(
          asImmediate(b, eval(b)),
          BooleanType.emitConstant(false, b, this),
          this
        ),
        false_label(),
        this
      )
    );
    b.add(b.newGotoStmt(true_label(), this));
  }
  /**
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:53
   */
  public soot.Value eval(Body b) {
    throw new Error("Operation eval not supported for " + getClass().getName());
  }
  /**
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:313
   */
  public soot.Value emitStore(Body b, soot.Value lvalue, soot.Value rvalue, ASTNode location) {
    //b.setLine(this);
    b.add(
      b.newAssignStmt(
        lvalue,
        asLocal(b, rvalue, lvalue.getType()),
        location
      )
    );
    return rvalue;
  }
  /**
   * @declaredat ASTNode:1
   */
  public Expr() {
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
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:13
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:19
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:23
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    unassignedAfterFalse_Variable_reset();
    unassignedAfterTrue_Variable_reset();
    unassignedAfter_Variable_reset();
    inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__reset();
    stmtCompatible_reset();
    compatibleStrictContext_TypeDecl_reset();
    compatibleLooseContext_TypeDecl_reset();
    pertinentToApplicability_Expr_BodyDecl_int_reset();
    moreSpecificThan_TypeDecl_TypeDecl_reset();
    potentiallyCompatible_TypeDecl_BodyDecl_reset();
    isBooleanExpression_reset();
    isNumericExpression_reset();
    isPolyExpression_reset();
    assignConversionTo_TypeDecl_reset();
    false_label_reset();
    true_label_reset();
    targetType_reset();
    assignmentContext_reset();
    invocationContext_reset();
    castContext_reset();
    stringContext_reset();
    numericContext_reset();
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
  public Expr clone() throws CloneNotSupportedException {
    Expr node = (Expr) super.clone();
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
  public abstract Expr fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:72
   */
  public abstract Expr treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:80
   */
  public abstract Expr treeCopy();
  /**
   * @aspect MethodSignature18
   * @declaredat /home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:964
   */
   
  protected SimpleSet<ConstructorDecl> chooseConstructor(
      Collection<ConstructorDecl> constructors, List<Expr> argList) {
    SimpleSet<ConstructorDecl> potentiallyApplicable = emptySet();

    // Select potentially applicable constructors.
    for (ConstructorDecl decl : constructors) {
      if (decl.potentiallyApplicable(argList) && decl.accessibleFrom(hostType())) {
        if (decl.isGeneric()) {
          GenericConstructorDecl gc = decl.genericDecl();
          decl = gc.lookupParConstructorDecl(
              inferTypeArguments(
                  gc.type(),
                  gc.getParameterList(),
                  argList,
                  gc.getTypeParameterList()));
        }
        potentiallyApplicable = potentiallyApplicable.add(decl);
      }
    }

    // First phase.
    SimpleSet<ConstructorDecl> maxSpecific = emptySet();
    for (ConstructorDecl decl : potentiallyApplicable) {
      if (decl.applicableByStrictInvocation(this, argList)) {
        maxSpecific = mostSpecific(maxSpecific, decl, argList);
      }
    }

    // Second phase.
    if (maxSpecific.isEmpty()) {
      for (ConstructorDecl decl : potentiallyApplicable) {
        if (decl.applicableByLooseInvocation(this, argList)) {
          maxSpecific = mostSpecific(maxSpecific, decl, argList);
        }
      }
    }

    // Third phase.
    if (maxSpecific.isEmpty()) {
      for (ConstructorDecl decl : potentiallyApplicable) {
        if (decl.isVariableArity() && decl.applicableByVariableArityInvocation(this, argList)) {
          maxSpecific = mostSpecific(maxSpecific, decl, argList);
        }
      }
    }
    return maxSpecific;
  }
  /**
   * @aspect AutoBoxingCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/AutoBoxingCodegen.jrag:120
   */
   
  public void emitEvalBranch(Body b) {
    if (!type().isReferenceType()) {
      refined_BooleanExpressions_Expr_emitEvalBranch(b);
      return;
    }

    //b.setLine(this);
    b.add(
      b.newIfStmt(
        b.newEqExpr(
          asImmediate(b, ((ReferenceType)type()).emitUnboxingOperation(b, eval(b), this)),
          BooleanType.emitConstant(false, b, this),
          this
        ),
        false_label(),
        this
      )
    );
    b.add(b.newGotoStmt(true_label(), this));
  }
  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:296
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:296")
  public abstract TypeDecl type();
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:32
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:32")
  public Constant constant() {
    {
        throw new UnsupportedOperationException("ConstantExpression operation constant"
            + " not supported for type " + getClass().getName());
      }
  }
  /**
   * representableIn(T) is true if and only if the the expression is a
   * compile-time constant of type byte, char, short or int, and the value
   * of the expression can be represented (by an expression) in the type T
   * where T must be byte, char or short.
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:328
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:328")
  public boolean representableIn(TypeDecl t) {
    {
        if (!type().isByte() && !type().isChar() && !type().isShort() && !type().isInt()) {
          return false;
        }
        if (t.isByte()) {
          return constant().intValue() >= Byte.MIN_VALUE && constant().intValue() <= Byte.MAX_VALUE;
        }
        if (t.isChar()) {
          return constant().intValue() >= Character.MIN_VALUE
              && constant().intValue() <= Character.MAX_VALUE;
        }
        if (t.isShort()) {
          return constant().intValue() >= Short.MIN_VALUE && constant().intValue() <= Short.MAX_VALUE;
        }
        if (t.isInt()) {
          return constant().intValue() >= Integer.MIN_VALUE
              && constant().intValue() <= Integer.MAX_VALUE;
        }
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:383
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:383")
  public boolean isConstant() {
    boolean isConstant_value = false;
    return isConstant_value;
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:435
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:435")
  public boolean isTrue() {
    boolean isTrue_value = isConstant() && type() instanceof BooleanType && constant().booleanValue();
    return isTrue_value;
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:438
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:438")
  public boolean isFalse() {
    boolean isFalse_value = isConstant() && type() instanceof BooleanType && !constant().booleanValue();
    return isFalse_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:77
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:77")
  public Variable varDecl() {
    Variable varDecl_value = null;
    return varDecl_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:377
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:377")
  public boolean assignedAfterFalse(Variable v) {
    boolean assignedAfterFalse_Variable_value = isTrue() || assignedAfter(v);
    return assignedAfterFalse_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:375
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:375")
  public boolean assignedAfterTrue(Variable v) {
    boolean assignedAfterTrue_Variable_value = isFalse() || assignedAfter(v);
    return assignedAfterTrue_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:268
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:268")
  public boolean assignedAfter(Variable v) {
    boolean assignedAfter_Variable_value = assignedBefore(v);
    return assignedAfter_Variable_value;
  }
  /** @apilevel internal */
  private void unassignedAfterFalse_Variable_reset() {
    unassignedAfterFalse_Variable_values = null;
  }
  protected java.util.Map unassignedAfterFalse_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:907")
  public boolean unassignedAfterFalse(Variable v) {
    Object _parameters = v;
    if (unassignedAfterFalse_Variable_values == null) unassignedAfterFalse_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (unassignedAfterFalse_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfterFalse_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      unassignedAfterFalse_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfterFalse_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfterFalse_Variable_value = isTrue() || unassignedAfter(v);
        if (new_unassignedAfterFalse_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfterFalse_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfterFalse_Variable_values.put(_parameters, new_unassignedAfterFalse_Variable_value);

      state.leaveCircle();
      return new_unassignedAfterFalse_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfterFalse_Variable_value = isTrue() || unassignedAfter(v);
      if (new_unassignedAfterFalse_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfterFalse_Variable_value;
      }
      return new_unassignedAfterFalse_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private void unassignedAfterTrue_Variable_reset() {
    unassignedAfterTrue_Variable_values = null;
  }
  protected java.util.Map unassignedAfterTrue_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:905")
  public boolean unassignedAfterTrue(Variable v) {
    Object _parameters = v;
    if (unassignedAfterTrue_Variable_values == null) unassignedAfterTrue_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (unassignedAfterTrue_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfterTrue_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      unassignedAfterTrue_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfterTrue_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfterTrue_Variable_value = isFalse() || unassignedAfter(v);
        if (new_unassignedAfterTrue_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfterTrue_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfterTrue_Variable_values.put(_parameters, new_unassignedAfterTrue_Variable_value);

      state.leaveCircle();
      return new_unassignedAfterTrue_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfterTrue_Variable_value = isFalse() || unassignedAfter(v);
      if (new_unassignedAfterTrue_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfterTrue_Variable_value;
      }
      return new_unassignedAfterTrue_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
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
        new_unassignedAfter_Variable_value = unassignedBefore(v);
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
      boolean new_unassignedAfter_Variable_value = unassignedBefore(v);
      if (new_unassignedAfter_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfter_Variable_value;
      }
      return new_unassignedAfter_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * Compute the most specific constructor in a collection.
   * The constructor is invoked with the arguments specified in argList.
   * The curent context (this) is used to evaluate the hostType for accessibility.
   * @attribute syn
   * @aspect ConstructScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:65
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructScope", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:65")
  public SimpleSet<ConstructorDecl> mostSpecificConstructor(Collection<ConstructorDecl> constructors) {
    {
        SimpleSet<ConstructorDecl> maxSpecific = emptySet();
        for (ConstructorDecl decl : constructors) {
          if (applicableAndAccessible(decl)) {
            if (maxSpecific.isEmpty()) {
              maxSpecific = maxSpecific.add(decl);
            } else {
              ConstructorDecl other = maxSpecific.iterator().next();
              if (decl.moreSpecificThan(other)) {
                maxSpecific = ASTNode.<ConstructorDecl>emptySet().add(decl);
              } else if (!other.moreSpecificThan(decl)) {
                maxSpecific = maxSpecific.add(decl);
              }
            }
          }
        }
        return maxSpecific;
      }
  }
  /**
   * @attribute syn
   * @aspect ConstructScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:85
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructScope", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:85")
  public boolean applicableAndAccessible(ConstructorDecl decl) {
    boolean applicableAndAccessible_ConstructorDecl_value = false;
    return applicableAndAccessible_ConstructorDecl_value;
  }
  /**
   * @attribute syn
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:106
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupFullyQualifiedTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:106")
  public boolean hasQualifiedPackage(String packageName) {
    boolean hasQualifiedPackage_String_value = false;
    return hasQualifiedPackage_String_value;
  }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:608
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:608")
  public SimpleSet<TypeDecl> qualifiedLookupType(String name) {
    SimpleSet<TypeDecl> qualifiedLookupType_String_value = keepAccessibleTypes(type().memberTypes(name));
    return qualifiedLookupType_String_value;
  }
  /**
   * @attribute syn
   * @aspect VariableScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:264
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="VariableScope", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:264")
  public SimpleSet<Variable> qualifiedLookupVariable(String name) {
    {
        if (type().accessibleFrom(hostType())) {
          return keepAccessibleFields(type().memberFields(name));
        }
        return emptySet();
      }
  }
  /**
   * @attribute syn
   * @aspect PositiveLiterals
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PositiveLiterals.jrag:36
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PositiveLiterals", declaredAt="/home/olivier/projects/extendj/java4/frontend/PositiveLiterals.jrag:36")
  public boolean isPositive() {
    boolean isPositive_value = false;
    return isPositive_value;
  }
  /**
   * @attribute syn
   * @aspect Names
   * @declaredat /home/olivier/projects/extendj/java4/frontend/QualifiedNames.jrag:43
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Names", declaredAt="/home/olivier/projects/extendj/java4/frontend/QualifiedNames.jrag:43")
  public String packageName() {
    String packageName_value = "";
    return packageName_value;
  }
  /**
   * @attribute syn
   * @aspect Names
   * @declaredat /home/olivier/projects/extendj/java4/frontend/QualifiedNames.jrag:73
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Names", declaredAt="/home/olivier/projects/extendj/java4/frontend/QualifiedNames.jrag:73")
  public String typeName() {
    String typeName_value = "";
    return typeName_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:35
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:35")
  public boolean isTypeAccess() {
    boolean isTypeAccess_value = false;
    return isTypeAccess_value;
  }
  /**
   * Tests if this is a qualified type access expression for
   * the given type.
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:43
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:43")
  public boolean isTypeAccess(String packageName, String type) {
    boolean isTypeAccess_String_String_value = false;
    return isTypeAccess_String_String_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:47
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:47")
  public boolean isMethodAccess() {
    boolean isMethodAccess_value = false;
    return isMethodAccess_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:51
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:51")
  public boolean isFieldAccess() {
    boolean isFieldAccess_value = false;
    return isFieldAccess_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:56
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:56")
  public boolean isSuperAccess() {
    boolean isSuperAccess_value = false;
    return isSuperAccess_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:62
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:62")
  public boolean isThisAccess() {
    boolean isThisAccess_value = false;
    return isThisAccess_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:68
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:68")
  public boolean isPackageAccess() {
    boolean isPackageAccess_value = false;
    return isPackageAccess_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:72
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:72")
  public boolean isArrayAccess() {
    boolean isArrayAccess_value = false;
    return isArrayAccess_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:76
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:76")
  public boolean isClassAccess() {
    boolean isClassAccess_value = false;
    return isClassAccess_value;
  }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:80
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:80")
  public boolean isSuperConstructorAccess() {
    boolean isSuperConstructorAccess_value = false;
    return isSuperConstructorAccess_value;
  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:177
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:177")
  public Dot parentDot() {
    Dot parentDot_value = getParent() instanceof Dot ?
        (Dot) getParent() : null;
    return parentDot_value;
  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:180
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:180")
  public boolean hasParentDot() {
    boolean hasParentDot_value = parentDot() != null;
    return hasParentDot_value;
  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:182
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:182")
  public boolean hasNextAccess() {
    boolean hasNextAccess_value = isLeftChildOfDot();
    return hasNextAccess_value;
  }
  /**
   * @attribute syn
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:493
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameResolution", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:493")
  public boolean isParseName() {
    boolean isParseName_value = false;
    return isParseName_value;
  }
  /**
   * @attribute syn
   * @aspect NestedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:563
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:563")
  public Stmt enclosingStmt() {
    {
        ASTNode node = this;
        while (node != null && !(node instanceof Stmt)) {
          node = node.getParent();
        }
        return (Stmt) node;
      }
  }
  /**
   * @attribute syn
   * @aspect TypeCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:33
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:33")
  public boolean isVariable() {
    boolean isVariable_value = false;
    return isVariable_value;
  }
  /**
   * @attribute syn
   * @aspect TypeHierarchyCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:47
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:47")
  public boolean isUnknown() {
    boolean isUnknown_value = type().isUnknown();
    return isUnknown_value;
  }
  /**
   * @attribute syn
   * @aspect TypeHierarchyCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:224
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:224")
  public boolean staticContextQualifier() {
    boolean staticContextQualifier_value = false;
    return staticContextQualifier_value;
  }
  /**
   * @attribute syn
   * @aspect Enums
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:663
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="/home/olivier/projects/extendj/java5/frontend/Enums.jrag:663")
  public boolean isEnumConstant() {
    boolean isEnumConstant_value = false;
    return isEnumConstant_value;
  }
  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1605
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:1605")
  public Expr erasedCopy() {
    Expr erasedCopy_value = treeCopyNoTransform();
    return erasedCopy_value;
  }
  /** @apilevel internal */
  private void inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__reset() {
    inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__computed = new java.util.HashMap(4);
    inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__values = null;
  }
  /** @apilevel internal */
  protected java.util.Map inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__values;
  /** @apilevel internal */
  protected java.util.Map inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__computed;
  /**
   * Infers type arguments for a generic method invocation.
   * @attribute syn
   * @aspect MethodSignature15
   * @declaredat /home/olivier/projects/extendj/java5/frontend/MethodSignature.jrag:452
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature15", declaredAt="/home/olivier/projects/extendj/java5/frontend/MethodSignature.jrag:452")
  public ArrayList<TypeDecl> inferTypeArguments(TypeDecl resultType, List<ParameterDeclaration> params, List<Expr> args, List<TypeVariable> typeParams) {
    java.util.List _parameters = new java.util.ArrayList(4);
    _parameters.add(resultType);
    _parameters.add(params);
    _parameters.add(args);
    _parameters.add(typeParams);
    if (inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__computed == null) inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__computed = new java.util.HashMap(4);
    if (inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__values == null) inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__values.containsKey(_parameters) && inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__computed != null
        && inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__computed.containsKey(_parameters)
        && (inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__computed.get(_parameters) == ASTNode$State.NON_CYCLE || inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__computed.get(_parameters) == state().cycle())) {
      return (ArrayList<TypeDecl>) inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__values.get(_parameters);
    }
    ArrayList<TypeDecl> inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__value = inferTypeArguments_compute(resultType, params, args, typeParams);
    if (state().inCircle()) {
      inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__values.put(_parameters, inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__value);
      inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__computed.put(_parameters, state().cycle());
    
    } else {
      inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__values.put(_parameters, inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__value);
      inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return inferTypeArguments_TypeDecl_List_ParameterDeclaration__List_Expr__List_TypeVariable__value;
  }
  /** @apilevel internal */
  private ArrayList<TypeDecl> inferTypeArguments_compute(TypeDecl resultType, List<ParameterDeclaration> params, List<Expr> args, List<TypeVariable> typeParams) {
      ArrayList<TypeDecl> typeArguments = new ArrayList<TypeDecl>();
      Collection<TypeDecl> arguments = computeConstraints(
          resultType,
          params,
          args,
          typeParams);
      if (arguments.isEmpty()) {
        return typeArguments;
      }
      int i = 0;
      for (TypeDecl type : arguments) {
        if (type == null) {
          TypeVariable v = typeParams.getChild(i);
          type = v.firstBound();
        } else if (type.isTypeVariable()) {
          TypeVariable v = (TypeVariable) type;
          // Replace the type variable if it is not defined in an outer scope.
          // This is needed for handling constructor reference type inference (in Java 8).
          // TODO(joqvist): is this needed also for Java 5?
          if (enclosingBlock() != null) {
            if (enclosingBlock().lookupType(v.name()) != v) {
              type = v.firstBound();
            }
          } else {
            if (enclosingMemberDecl().lookupType(v.name()) != v) {
              type = v.firstBound();
            }
          }
        }
        typeArguments.add(type);
        i += 1;
      }
      return typeArguments;
    }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:33
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:33")
  public Collection<TypeDecl> throwTypes() {
    {
        Collection<TypeDecl> tts = new LinkedList<TypeDecl>();
        tts.add(type());
        return tts;
      }
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:145
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:145")
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = false;
    return modifiedInScope_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:196
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:196")
  public boolean isVariable(Variable var) {
    boolean isVariable_Variable_value = false;
    return isVariable_Variable_value;
  }
  /** @apilevel internal */
  private void stmtCompatible_reset() {
    stmtCompatible_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle stmtCompatible_computed = null;

  /** @apilevel internal */
  protected boolean stmtCompatible_value;

  /**
   * @attribute syn
   * @aspect StmtCompatible
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LambdaExpr.jrag:141
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StmtCompatible", declaredAt="/home/olivier/projects/extendj/java8/frontend/LambdaExpr.jrag:141")
  public boolean stmtCompatible() {
    ASTNode$State state = state();
    if (stmtCompatible_computed == ASTNode$State.NON_CYCLE || stmtCompatible_computed == state().cycle()) {
      return stmtCompatible_value;
    }
    stmtCompatible_value = false;
    if (state().inCircle()) {
      stmtCompatible_computed = state().cycle();
    
    } else {
      stmtCompatible_computed = ASTNode$State.NON_CYCLE;
    
    }
    return stmtCompatible_value;
  }
  /** @apilevel internal */
  private void compatibleStrictContext_TypeDecl_reset() {
    compatibleStrictContext_TypeDecl_computed = new java.util.HashMap(4);
    compatibleStrictContext_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map compatibleStrictContext_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map compatibleStrictContext_TypeDecl_computed;
  /** Used to compute compatibility during phase 1 of overload resolution. 
   * @attribute syn
   * @aspect MethodSignature18
   * @declaredat /home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:50
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature18", declaredAt="/home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:50")
  public boolean compatibleStrictContext(TypeDecl type) {
    Object _parameters = type;
    if (compatibleStrictContext_TypeDecl_computed == null) compatibleStrictContext_TypeDecl_computed = new java.util.HashMap(4);
    if (compatibleStrictContext_TypeDecl_values == null) compatibleStrictContext_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (compatibleStrictContext_TypeDecl_values.containsKey(_parameters) && compatibleStrictContext_TypeDecl_computed != null
        && compatibleStrictContext_TypeDecl_computed.containsKey(_parameters)
        && (compatibleStrictContext_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || compatibleStrictContext_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) compatibleStrictContext_TypeDecl_values.get(_parameters);
    }
    boolean compatibleStrictContext_TypeDecl_value = type().subtype(type);
    if (state().inCircle()) {
      compatibleStrictContext_TypeDecl_values.put(_parameters, compatibleStrictContext_TypeDecl_value);
      compatibleStrictContext_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      compatibleStrictContext_TypeDecl_values.put(_parameters, compatibleStrictContext_TypeDecl_value);
      compatibleStrictContext_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return compatibleStrictContext_TypeDecl_value;
  }
  /** @apilevel internal */
  private void compatibleLooseContext_TypeDecl_reset() {
    compatibleLooseContext_TypeDecl_computed = new java.util.HashMap(4);
    compatibleLooseContext_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map compatibleLooseContext_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map compatibleLooseContext_TypeDecl_computed;
  /**
   * @attribute syn
   * @aspect MethodSignature18
   * @declaredat /home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:94
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature18", declaredAt="/home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:94")
  public boolean compatibleLooseContext(TypeDecl type) {
    Object _parameters = type;
    if (compatibleLooseContext_TypeDecl_computed == null) compatibleLooseContext_TypeDecl_computed = new java.util.HashMap(4);
    if (compatibleLooseContext_TypeDecl_values == null) compatibleLooseContext_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (compatibleLooseContext_TypeDecl_values.containsKey(_parameters) && compatibleLooseContext_TypeDecl_computed != null
        && compatibleLooseContext_TypeDecl_computed.containsKey(_parameters)
        && (compatibleLooseContext_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || compatibleLooseContext_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) compatibleLooseContext_TypeDecl_values.get(_parameters);
    }
    boolean compatibleLooseContext_TypeDecl_value = type().methodInvocationConversionTo(type)
          || type().boxed().withinBounds(type);
    if (state().inCircle()) {
      compatibleLooseContext_TypeDecl_values.put(_parameters, compatibleLooseContext_TypeDecl_value);
      compatibleLooseContext_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      compatibleLooseContext_TypeDecl_values.put(_parameters, compatibleLooseContext_TypeDecl_value);
      compatibleLooseContext_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return compatibleLooseContext_TypeDecl_value;
  }
  /** @apilevel internal */
  private void pertinentToApplicability_Expr_BodyDecl_int_reset() {
    pertinentToApplicability_Expr_BodyDecl_int_computed = new java.util.HashMap(4);
    pertinentToApplicability_Expr_BodyDecl_int_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map pertinentToApplicability_Expr_BodyDecl_int_values;
  /** @apilevel internal */
  protected java.util.Map pertinentToApplicability_Expr_BodyDecl_int_computed;
  /**
   * @attribute syn
   * @aspect MethodSignature18
   * @declaredat /home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:122
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature18", declaredAt="/home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:122")
  public boolean pertinentToApplicability(Expr access, BodyDecl decl, int argIndex) {
    java.util.List _parameters = new java.util.ArrayList(3);
    _parameters.add(access);
    _parameters.add(decl);
    _parameters.add(argIndex);
    if (pertinentToApplicability_Expr_BodyDecl_int_computed == null) pertinentToApplicability_Expr_BodyDecl_int_computed = new java.util.HashMap(4);
    if (pertinentToApplicability_Expr_BodyDecl_int_values == null) pertinentToApplicability_Expr_BodyDecl_int_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (pertinentToApplicability_Expr_BodyDecl_int_values.containsKey(_parameters) && pertinentToApplicability_Expr_BodyDecl_int_computed != null
        && pertinentToApplicability_Expr_BodyDecl_int_computed.containsKey(_parameters)
        && (pertinentToApplicability_Expr_BodyDecl_int_computed.get(_parameters) == ASTNode$State.NON_CYCLE || pertinentToApplicability_Expr_BodyDecl_int_computed.get(_parameters) == state().cycle())) {
      return (Boolean) pertinentToApplicability_Expr_BodyDecl_int_values.get(_parameters);
    }
    boolean pertinentToApplicability_Expr_BodyDecl_int_value = true;
    if (state().inCircle()) {
      pertinentToApplicability_Expr_BodyDecl_int_values.put(_parameters, pertinentToApplicability_Expr_BodyDecl_int_value);
      pertinentToApplicability_Expr_BodyDecl_int_computed.put(_parameters, state().cycle());
    
    } else {
      pertinentToApplicability_Expr_BodyDecl_int_values.put(_parameters, pertinentToApplicability_Expr_BodyDecl_int_value);
      pertinentToApplicability_Expr_BodyDecl_int_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return pertinentToApplicability_Expr_BodyDecl_int_value;
  }
  /** @apilevel internal */
  private void moreSpecificThan_TypeDecl_TypeDecl_reset() {
    moreSpecificThan_TypeDecl_TypeDecl_computed = new java.util.HashMap(4);
    moreSpecificThan_TypeDecl_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map moreSpecificThan_TypeDecl_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map moreSpecificThan_TypeDecl_TypeDecl_computed;
  /**
   * Computes which type is more specific for a specific argument, as defined in 15.12.2.5
   * @param type1
   * @param type2
   * @return {@code true} if type1 is more specific than type2, {@code false} otherwise
   * @attribute syn
   * @aspect MethodSignature18
   * @declaredat /home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:248
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature18", declaredAt="/home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:248")
  public boolean moreSpecificThan(TypeDecl type1, TypeDecl type2) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(type1);
    _parameters.add(type2);
    if (moreSpecificThan_TypeDecl_TypeDecl_computed == null) moreSpecificThan_TypeDecl_TypeDecl_computed = new java.util.HashMap(4);
    if (moreSpecificThan_TypeDecl_TypeDecl_values == null) moreSpecificThan_TypeDecl_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (moreSpecificThan_TypeDecl_TypeDecl_values.containsKey(_parameters) && moreSpecificThan_TypeDecl_TypeDecl_computed != null
        && moreSpecificThan_TypeDecl_TypeDecl_computed.containsKey(_parameters)
        && (moreSpecificThan_TypeDecl_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || moreSpecificThan_TypeDecl_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) moreSpecificThan_TypeDecl_TypeDecl_values.get(_parameters);
    }
    boolean moreSpecificThan_TypeDecl_TypeDecl_value = type1.instanceOf(type2) || type1.withinBounds(type2);
    if (state().inCircle()) {
      moreSpecificThan_TypeDecl_TypeDecl_values.put(_parameters, moreSpecificThan_TypeDecl_TypeDecl_value);
      moreSpecificThan_TypeDecl_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      moreSpecificThan_TypeDecl_TypeDecl_values.put(_parameters, moreSpecificThan_TypeDecl_TypeDecl_value);
      moreSpecificThan_TypeDecl_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return moreSpecificThan_TypeDecl_TypeDecl_value;
  }
  /** @apilevel internal */
  private void potentiallyCompatible_TypeDecl_BodyDecl_reset() {
    potentiallyCompatible_TypeDecl_BodyDecl_computed = new java.util.HashMap(4);
    potentiallyCompatible_TypeDecl_BodyDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map potentiallyCompatible_TypeDecl_BodyDecl_values;
  /** @apilevel internal */
  protected java.util.Map potentiallyCompatible_TypeDecl_BodyDecl_computed;
  /**
   * @attribute syn
   * @aspect MethodSignature18
   * @declaredat /home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:503
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature18", declaredAt="/home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:503")
  public boolean potentiallyCompatible(TypeDecl type, BodyDecl candidateDecl) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(type);
    _parameters.add(candidateDecl);
    if (potentiallyCompatible_TypeDecl_BodyDecl_computed == null) potentiallyCompatible_TypeDecl_BodyDecl_computed = new java.util.HashMap(4);
    if (potentiallyCompatible_TypeDecl_BodyDecl_values == null) potentiallyCompatible_TypeDecl_BodyDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (potentiallyCompatible_TypeDecl_BodyDecl_values.containsKey(_parameters) && potentiallyCompatible_TypeDecl_BodyDecl_computed != null
        && potentiallyCompatible_TypeDecl_BodyDecl_computed.containsKey(_parameters)
        && (potentiallyCompatible_TypeDecl_BodyDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || potentiallyCompatible_TypeDecl_BodyDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) potentiallyCompatible_TypeDecl_BodyDecl_values.get(_parameters);
    }
    boolean potentiallyCompatible_TypeDecl_BodyDecl_value = true;
    if (state().inCircle()) {
      potentiallyCompatible_TypeDecl_BodyDecl_values.put(_parameters, potentiallyCompatible_TypeDecl_BodyDecl_value);
      potentiallyCompatible_TypeDecl_BodyDecl_computed.put(_parameters, state().cycle());
    
    } else {
      potentiallyCompatible_TypeDecl_BodyDecl_values.put(_parameters, potentiallyCompatible_TypeDecl_BodyDecl_value);
      potentiallyCompatible_TypeDecl_BodyDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return potentiallyCompatible_TypeDecl_BodyDecl_value;
  }
  /** @apilevel internal */
  private void isBooleanExpression_reset() {
    isBooleanExpression_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isBooleanExpression_computed = null;

  /** @apilevel internal */
  protected boolean isBooleanExpression_value;

  /**
   * @attribute syn
   * @aspect PolyExpressions
   * @declaredat /home/olivier/projects/extendj/java8/frontend/PolyExpressions.jrag:29
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PolyExpressions", declaredAt="/home/olivier/projects/extendj/java8/frontend/PolyExpressions.jrag:29")
  public boolean isBooleanExpression() {
    ASTNode$State state = state();
    if (isBooleanExpression_computed == ASTNode$State.NON_CYCLE || isBooleanExpression_computed == state().cycle()) {
      return isBooleanExpression_value;
    }
    isBooleanExpression_value = !isPolyExpression() && type().isBoolean();
    if (state().inCircle()) {
      isBooleanExpression_computed = state().cycle();
    
    } else {
      isBooleanExpression_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isBooleanExpression_value;
  }
  /** @apilevel internal */
  private void isNumericExpression_reset() {
    isNumericExpression_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isNumericExpression_computed = null;

  /** @apilevel internal */
  protected boolean isNumericExpression_value;

  /**
   * @attribute syn
   * @aspect PolyExpressions
   * @declaredat /home/olivier/projects/extendj/java8/frontend/PolyExpressions.jrag:60
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PolyExpressions", declaredAt="/home/olivier/projects/extendj/java8/frontend/PolyExpressions.jrag:60")
  public boolean isNumericExpression() {
    ASTNode$State state = state();
    if (isNumericExpression_computed == ASTNode$State.NON_CYCLE || isNumericExpression_computed == state().cycle()) {
      return isNumericExpression_value;
    }
    isNumericExpression_value = !isPolyExpression() && type().isNumericType();
    if (state().inCircle()) {
      isNumericExpression_computed = state().cycle();
    
    } else {
      isNumericExpression_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isNumericExpression_value;
  }
  /**
   * @attribute syn
   * @aspect PolyExpressions
   * @declaredat /home/olivier/projects/extendj/java8/frontend/PolyExpressions.jrag:72
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PolyExpressions", declaredAt="/home/olivier/projects/extendj/java8/frontend/PolyExpressions.jrag:72")
  public boolean isNullLiteral() {
    boolean isNullLiteral_value = false;
    return isNullLiteral_value;
  }
  /** @apilevel internal */
  private void isPolyExpression_reset() {
    isPolyExpression_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isPolyExpression_computed = null;

  /** @apilevel internal */
  protected boolean isPolyExpression_value;

  /**
   * @attribute syn
   * @aspect PolyExpressions
   * @declaredat /home/olivier/projects/extendj/java8/frontend/PolyExpressions.jrag:86
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PolyExpressions", declaredAt="/home/olivier/projects/extendj/java8/frontend/PolyExpressions.jrag:86")
  public boolean isPolyExpression() {
    ASTNode$State state = state();
    if (isPolyExpression_computed == ASTNode$State.NON_CYCLE || isPolyExpression_computed == state().cycle()) {
      return isPolyExpression_value;
    }
    isPolyExpression_value = false;
    if (state().inCircle()) {
      isPolyExpression_computed = state().cycle();
    
    } else {
      isPolyExpression_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isPolyExpression_value;
  }
  /** @apilevel internal */
  private void assignConversionTo_TypeDecl_reset() {
    assignConversionTo_TypeDecl_computed = new java.util.HashMap(4);
    assignConversionTo_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map assignConversionTo_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map assignConversionTo_TypeDecl_computed;
  /**
   * @attribute syn
   * @aspect PolyExpressions
   * @declaredat /home/olivier/projects/extendj/java8/frontend/PolyExpressions.jrag:149
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PolyExpressions", declaredAt="/home/olivier/projects/extendj/java8/frontend/PolyExpressions.jrag:149")
  public boolean assignConversionTo(TypeDecl type) {
    Object _parameters = type;
    if (assignConversionTo_TypeDecl_computed == null) assignConversionTo_TypeDecl_computed = new java.util.HashMap(4);
    if (assignConversionTo_TypeDecl_values == null) assignConversionTo_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (assignConversionTo_TypeDecl_values.containsKey(_parameters) && assignConversionTo_TypeDecl_computed != null
        && assignConversionTo_TypeDecl_computed.containsKey(_parameters)
        && (assignConversionTo_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || assignConversionTo_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) assignConversionTo_TypeDecl_values.get(_parameters);
    }
    boolean assignConversionTo_TypeDecl_value = type().assignConversionTo(type, this);
    if (state().inCircle()) {
      assignConversionTo_TypeDecl_values.put(_parameters, assignConversionTo_TypeDecl_value);
      assignConversionTo_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      assignConversionTo_TypeDecl_values.put(_parameters, assignConversionTo_TypeDecl_value);
      assignConversionTo_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return assignConversionTo_TypeDecl_value;
  }
  /** @apilevel internal */
  private void false_label_reset() {
    false_label_computed = null;
    false_label_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle false_label_computed = null;

  /** @apilevel internal */
  protected soot.jimple.Stmt false_label_value;

  /**
   * @attribute syn
   * @aspect BooleanExpressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:16
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BooleanExpressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:16")
  public soot.jimple.Stmt false_label() {
    ASTNode$State state = state();
    if (false_label_computed == ASTNode$State.NON_CYCLE || false_label_computed == state().cycle()) {
      return false_label_value;
    }
    false_label_value = getParent().definesLabel() ? condition_false_label() : newLabel();
    if (state().inCircle()) {
      false_label_computed = state().cycle();
    
    } else {
      false_label_computed = ASTNode$State.NON_CYCLE;
    
    }
    return false_label_value;
  }
  /** @apilevel internal */
  private void true_label_reset() {
    true_label_computed = null;
    true_label_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle true_label_computed = null;

  /** @apilevel internal */
  protected soot.jimple.Stmt true_label_value;

  /**
   * @attribute syn
   * @aspect BooleanExpressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:18
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BooleanExpressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:18")
  public soot.jimple.Stmt true_label() {
    ASTNode$State state = state();
    if (true_label_computed == ASTNode$State.NON_CYCLE || true_label_computed == state().cycle()) {
      return true_label_value;
    }
    true_label_value = getParent().definesLabel() ? condition_true_label() : newLabel();
    if (state().inCircle()) {
      true_label_computed = state().cycle();
    
    } else {
      true_label_computed = ASTNode$State.NON_CYCLE;
    
    }
    return true_label_value;
  }
  /**
   * @attribute syn
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:778
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EmitJimple", declaredAt="/home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:778")
  public int srcSpanStart() {
    int srcSpanStart_value = recursiveSpanStart();
    return srcSpanStart_value;
  }
  /**
   * @attribute syn
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:779
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EmitJimple", declaredAt="/home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:779")
  public int srcSpanEnd() {
    int srcSpanEnd_value = recursiveSpanEnd();
    return srcSpanEnd_value;
  }
  /**
   * @attribute syn
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:37
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Expressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:37")
  public MethodDecl stringBufferAppendMethodForType(TypeDecl arg) {
    {
        TypeDecl  sb          = lookupType("java.lang", "StringBuffer");
        TypeDecl  argPromoted = arg.stringPromotion();
    
        for (MethodDecl m : sb.memberMethods("append")) {
          if (m.getNumParameter()       != 1          ) continue;
          if (m.getParameter(0).type()  != argPromoted) continue;
    
          return m;
        }
    
        throw new Error(
          "no appropriate `StringBuffer.append` method found for type: " + argPromoted.jvmName());
      }
  }
  /**
   * @attribute syn
   * @aspect ResolverDependencies
   * @declaredat /home/olivier/projects/extendj/soot8/backend/ResolverDependencies.jrag:4
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ResolverDependencies", declaredAt="/home/olivier/projects/extendj/soot8/backend/ResolverDependencies.jrag:4")
  public TypeDecl sootHierarchyDepType() {
    TypeDecl sootHierarchyDepType_value = type().elementType().erasure();
    return sootHierarchyDepType_value;
  }
  /**
   * @attribute inh
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:34
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:34")
  public boolean isDest() {
    boolean isDest_value = getParent().Define_isDest(this, null);
    return isDest_value;
  }
  /**
   * @attribute inh
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:44
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:44")
  public boolean isSource() {
    boolean isSource_value = getParent().Define_isSource(this, null);
    return isSource_value;
  }
  /**
   * @attribute inh
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:66
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:66")
  public boolean isIncOrDec() {
    boolean isIncOrDec_value = getParent().Define_isIncOrDec(this, null);
    return isIncOrDec_value;
  }
  /**
   * @attribute inh
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:266
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:266")
  public boolean assignedBefore(Variable v) {
    boolean assignedBefore_Variable_value = getParent().Define_assignedBefore(this, null, v);
    return assignedBefore_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect DefiniteUnassignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:897
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:897")
  public boolean unassignedBefore(Variable v) {
    boolean unassignedBefore_Variable_value = getParent().Define_unassignedBefore(this, null, v);
    return unassignedBefore_Variable_value;
  }
  /**
   * Find all visible methods with the given name in the local
   * scope or a qualified scope.
   * @attribute inh
   * @aspect LookupMethod
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:84
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupMethod", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:84")
  public Collection<MethodDecl> lookupMethod(String name) {
    Collection<MethodDecl> lookupMethod_String_value = getParent().Define_lookupMethod(this, null, name);
    return lookupMethod_String_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:74
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:74")
  public TypeDecl typeBoolean() {
    TypeDecl typeBoolean_value = getParent().Define_typeBoolean(this, null);
    return typeBoolean_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:75
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:75")
  public TypeDecl typeByte() {
    TypeDecl typeByte_value = getParent().Define_typeByte(this, null);
    return typeByte_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:76
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:76")
  public TypeDecl typeShort() {
    TypeDecl typeShort_value = getParent().Define_typeShort(this, null);
    return typeShort_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:77
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:77")
  public TypeDecl typeChar() {
    TypeDecl typeChar_value = getParent().Define_typeChar(this, null);
    return typeChar_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:78
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:78")
  public TypeDecl typeInt() {
    TypeDecl typeInt_value = getParent().Define_typeInt(this, null);
    return typeInt_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:79
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:79")
  public TypeDecl typeLong() {
    TypeDecl typeLong_value = getParent().Define_typeLong(this, null);
    return typeLong_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:80
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:80")
  public TypeDecl typeFloat() {
    TypeDecl typeFloat_value = getParent().Define_typeFloat(this, null);
    return typeFloat_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:81
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:81")
  public TypeDecl typeDouble() {
    TypeDecl typeDouble_value = getParent().Define_typeDouble(this, null);
    return typeDouble_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:82
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:82")
  public TypeDecl typeString() {
    TypeDecl typeString_value = getParent().Define_typeString(this, null);
    return typeString_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:83
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:83")
  public TypeDecl typeVoid() {
    TypeDecl typeVoid_value = getParent().Define_typeVoid(this, null);
    return typeVoid_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:84
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:84")
  public TypeDecl typeNull() {
    TypeDecl typeNull_value = getParent().Define_typeNull(this, null);
    return typeNull_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:97
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:97")
  public TypeDecl unknownType() {
    TypeDecl unknownType_value = getParent().Define_unknownType(this, null);
    return unknownType_value;
  }
  /**
   * @attribute inh
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:109
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupFullyQualifiedTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:109")
  public boolean hasPackage(String packageName) {
    boolean hasPackage_String_value = getParent().Define_hasPackage(this, null, packageName);
    return hasPackage_String_value;
  }
  /**
   * @attribute inh
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:123
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupFullyQualifiedTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:123")
  public TypeDecl lookupType(String packageName, String typeName) {
    TypeDecl lookupType_String_String_value = getParent().Define_lookupType(this, null, packageName, typeName);
    return lookupType_String_String_value;
  }
  /**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:397
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:397")
  public SimpleSet<TypeDecl> lookupType(String name) {
    SimpleSet<TypeDecl> lookupType_String_value = getParent().Define_lookupType(this, null, name);
    return lookupType_String_value;
  }
  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:43
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="VariableScope", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:43")
  public SimpleSet<Variable> lookupVariable(String name) {
    SimpleSet<Variable> lookupVariable_String_value = getParent().Define_lookupVariable(this, null, name);
    return lookupVariable_String_value;
  }
  /**
   * @return the directly enclosing member declaration, or {@code null} if there is none.
   * @attribute inh
   * @aspect NameCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:376
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:376")
  public BodyDecl enclosingMemberDecl() {
    BodyDecl enclosingMemberDecl_value = getParent().Define_enclosingMemberDecl(this, null);
    return enclosingMemberDecl_value;
  }
  /**
   * @attribute inh
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:86
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:86")
  public boolean isLeftChildOfDot() {
    boolean isLeftChildOfDot_value = getParent().Define_isLeftChildOfDot(this, null);
    return isLeftChildOfDot_value;
  }
  /**
   * @attribute inh
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:101
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:101")
  public boolean isRightChildOfDot() {
    boolean isRightChildOfDot_value = getParent().Define_isRightChildOfDot(this, null);
    return isRightChildOfDot_value;
  }
  /**
   * @attribute inh
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:118
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:118")
  public Expr prevExpr() {
    Expr prevExpr_value = getParent().Define_prevExpr(this, null);
    return prevExpr_value;
  }
  /**
   * @attribute inh
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:142
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:142")
  public Access nextAccess() {
    Access nextAccess_value = getParent().Define_nextAccess(this, null);
    return nextAccess_value;
  }
  /**
   * @attribute inh
   * @aspect SyntacticClassification
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SyntacticClassification", declaredAt="/home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36")
  public NameType nameType() {
    NameType nameType_value = getParent().Define_nameType(this, null);
    return nameType_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:571
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:571")
  public BodyDecl enclosingBodyDecl() {
    BodyDecl enclosingBodyDecl_value = getParent().Define_enclosingBodyDecl(this, null);
    return enclosingBodyDecl_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:640
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:640")
  public String hostPackage() {
    String hostPackage_value = getParent().Define_hostPackage(this, null);
    return hostPackage_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:657
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:657")
  public TypeDecl hostType() {
    TypeDecl hostType_value = getParent().Define_hostType(this, null);
    return hostType_value;
  }
  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:33
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:33")
  public String methodHost() {
    String methodHost_value = getParent().Define_methodHost(this, null);
    return methodHost_value;
  }
  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:207
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:207")
  public boolean inStaticContext() {
    boolean inStaticContext_value = getParent().Define_inStaticContext(this, null);
    return inStaticContext_value;
  }
  /**
   * The assign converted type is used in type inference to get the
   * target type of an inferred method invocation.
   * @attribute inh
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:69
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="GenericMethodsInference", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:69")
  public TypeDecl assignConvertedType() {
    TypeDecl assignConvertedType_value = getParent().Define_assignConvertedType(this, null);
    return assignConvertedType_value;
  }
  /**
   * @attribute inh
   * @aspect GenericMethodsInference
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:86
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="GenericMethodsInference", declaredAt="/home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:86")
  public TypeDecl typeObject() {
    TypeDecl typeObject_value = getParent().Define_typeObject(this, null);
    return typeObject_value;
  }
  /**
   * @attribute inh
   * @aspect GenericsTypeAnalysis
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:383
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="GenericsTypeAnalysis", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:383")
  public boolean inExtendsOrImplements() {
    boolean inExtendsOrImplements_value = getParent().Define_inExtendsOrImplements(this, null);
    return inExtendsOrImplements_value;
  }
  /**
   * @return the directly enclosing block of this statement.
   * @attribute inh
   * @aspect MethodSignature15
   * @declaredat /home/olivier/projects/extendj/java5/frontend/MethodSignature.jrag:426
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="MethodSignature15", declaredAt="/home/olivier/projects/extendj/java5/frontend/MethodSignature.jrag:426")
  public Block enclosingBlock() {
    Block enclosingBlock_value = getParent().Define_enclosingBlock(this, null);
    return enclosingBlock_value;
  }
  /**
   * @attribute inh
   * @aspect TargetType
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:31
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TargetType", declaredAt="/home/olivier/projects/extendj/java8/frontend/TargetType.jrag:31")
  public TypeDecl targetType() {
    ASTNode$State state = state();
    if (targetType_computed == ASTNode$State.NON_CYCLE || targetType_computed == state().cycle()) {
      return targetType_value;
    }
    targetType_value = getParent().Define_targetType(this, null);
    if (state().inCircle()) {
      targetType_computed = state().cycle();
    
    } else {
      targetType_computed = ASTNode$State.NON_CYCLE;
    
    }
    return targetType_value;
  }
  /** @apilevel internal */
  private void targetType_reset() {
    targetType_computed = null;
    targetType_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle targetType_computed = null;

  /** @apilevel internal */
  protected TypeDecl targetType_value;

  /**
   * @attribute inh
   * @aspect Contexts
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:234
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Contexts", declaredAt="/home/olivier/projects/extendj/java8/frontend/TargetType.jrag:234")
  public boolean assignmentContext() {
    ASTNode$State state = state();
    if (assignmentContext_computed == ASTNode$State.NON_CYCLE || assignmentContext_computed == state().cycle()) {
      return assignmentContext_value;
    }
    assignmentContext_value = getParent().Define_assignmentContext(this, null);
    if (state().inCircle()) {
      assignmentContext_computed = state().cycle();
    
    } else {
      assignmentContext_computed = ASTNode$State.NON_CYCLE;
    
    }
    return assignmentContext_value;
  }
  /** @apilevel internal */
  private void assignmentContext_reset() {
    assignmentContext_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle assignmentContext_computed = null;

  /** @apilevel internal */
  protected boolean assignmentContext_value;

  /**
   * @attribute inh
   * @aspect Contexts
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:235
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Contexts", declaredAt="/home/olivier/projects/extendj/java8/frontend/TargetType.jrag:235")
  public boolean invocationContext() {
    ASTNode$State state = state();
    if (invocationContext_computed == ASTNode$State.NON_CYCLE || invocationContext_computed == state().cycle()) {
      return invocationContext_value;
    }
    invocationContext_value = getParent().Define_invocationContext(this, null);
    if (state().inCircle()) {
      invocationContext_computed = state().cycle();
    
    } else {
      invocationContext_computed = ASTNode$State.NON_CYCLE;
    
    }
    return invocationContext_value;
  }
  /** @apilevel internal */
  private void invocationContext_reset() {
    invocationContext_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle invocationContext_computed = null;

  /** @apilevel internal */
  protected boolean invocationContext_value;

  /**
   * @attribute inh
   * @aspect Contexts
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:236
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Contexts", declaredAt="/home/olivier/projects/extendj/java8/frontend/TargetType.jrag:236")
  public boolean castContext() {
    ASTNode$State state = state();
    if (castContext_computed == ASTNode$State.NON_CYCLE || castContext_computed == state().cycle()) {
      return castContext_value;
    }
    castContext_value = getParent().Define_castContext(this, null);
    if (state().inCircle()) {
      castContext_computed = state().cycle();
    
    } else {
      castContext_computed = ASTNode$State.NON_CYCLE;
    
    }
    return castContext_value;
  }
  /** @apilevel internal */
  private void castContext_reset() {
    castContext_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle castContext_computed = null;

  /** @apilevel internal */
  protected boolean castContext_value;

  /**
   * @attribute inh
   * @aspect Contexts
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:237
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Contexts", declaredAt="/home/olivier/projects/extendj/java8/frontend/TargetType.jrag:237")
  public boolean stringContext() {
    ASTNode$State state = state();
    if (stringContext_computed == ASTNode$State.NON_CYCLE || stringContext_computed == state().cycle()) {
      return stringContext_value;
    }
    stringContext_value = getParent().Define_stringContext(this, null);
    if (state().inCircle()) {
      stringContext_computed = state().cycle();
    
    } else {
      stringContext_computed = ASTNode$State.NON_CYCLE;
    
    }
    return stringContext_value;
  }
  /** @apilevel internal */
  private void stringContext_reset() {
    stringContext_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle stringContext_computed = null;

  /** @apilevel internal */
  protected boolean stringContext_value;

  /**
   * @attribute inh
   * @aspect Contexts
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:238
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Contexts", declaredAt="/home/olivier/projects/extendj/java8/frontend/TargetType.jrag:238")
  public boolean numericContext() {
    ASTNode$State state = state();
    if (numericContext_computed == ASTNode$State.NON_CYCLE || numericContext_computed == state().cycle()) {
      return numericContext_value;
    }
    numericContext_value = getParent().Define_numericContext(this, null);
    if (state().inCircle()) {
      numericContext_computed = state().cycle();
    
    } else {
      numericContext_computed = ASTNode$State.NON_CYCLE;
    
    }
    return numericContext_value;
  }
  /** @apilevel internal */
  private void numericContext_reset() {
    numericContext_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle numericContext_computed = null;

  /** @apilevel internal */
  protected boolean numericContext_value;

  /**
   * @attribute inh
   * @aspect BooleanExpressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:48
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="BooleanExpressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:48")
  public soot.jimple.Stmt condition_false_label() {
    soot.jimple.Stmt condition_false_label_value = getParent().Define_condition_false_label(this, null);
    return condition_false_label_value;
  }
  /**
   * @attribute inh
   * @aspect BooleanExpressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:49
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="BooleanExpressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:49")
  public soot.jimple.Stmt condition_true_label() {
    soot.jimple.Stmt condition_true_label_value = getParent().Define_condition_true_label(this, null);
    return condition_true_label_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:86
   * @apilevel internal
   */
  public boolean Define_isLeftChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_isLeftChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:101
   * @apilevel internal
   */
  public boolean Define_isRightChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  protected boolean canDefine_isRightChildOfDot(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:118
   * @apilevel internal
   */
  public Expr Define_prevExpr(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return prevExprError();
  }
  protected boolean canDefine_prevExpr(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:142
   * @apilevel internal
   */
  public Access Define_nextAccess(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return nextAccessError();
  }
  protected boolean canDefine_nextAccess(ASTNode _callerNode, ASTNode _childNode) {
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
  protected void collect_contributors_CompilationUnit_hierarchyDependencies(CompilationUnit _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat /home/olivier/projects/extendj/soot8/backend/ResolverDependencies.jrag:15
    if (sootHierarchyDepType().sootDependencyNeeded()) {
      {
        java.util.Set<ASTNode> contributors = _map.get(_root);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) _root, contributors);
        }
        contributors.add(this);
      }
    }
    super.collect_contributors_CompilationUnit_hierarchyDependencies(_root, _map);
  }
  protected void contributeTo_CompilationUnit_hierarchyDependencies(HashSet<Type> collection) {
    super.contributeTo_CompilationUnit_hierarchyDependencies(collection);
    if (sootHierarchyDepType().sootDependencyNeeded()) {
      collection.add(sootHierarchyDepType().getSootType());
    }
  }
}
