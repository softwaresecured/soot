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
 * An abstract access.
 * Concrete subclasses include field, method, and type access.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:69
 * @production Access : {@link Expr};

 */
public abstract class Access extends Expr implements Cloneable {
  /**
   * Used by the parser to build a method access from a parsed, potentially qualified, name.
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:451
   */
  public Access buildMethodAccess(List<Expr> arguments) {
    throw new Error("Can not build method access from access of type "
        + getClass().getSimpleName());
  }
  /**
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:474
   */
  public Access addArrayDims(List list) {
    Access a = this;
    for (int i = 0; i < list.getNumChildNoTransform(); i++) {
      Dims dims = (Dims) list.getChildNoTransform(i);
      Opt<Expr> opt = dims.getExprOpt();
      if (opt.getNumChildNoTransform() == 1) {
        a = new ArrayTypeWithSizeAccess(a, (Expr) opt.getChildNoTransform(0));
      } else {
        a = new ArrayTypeAccess(a);
      }
      a.setStart(dims.start());
      a.setEnd(dims.end());
    }
    return a;
  }
  /**
   * Builds a copy of this Access node where all occurrences
   * of type variables in the original type parameter list have been replaced
   * by the substitution type parameters.
   * 
   * <p>This should only be used to generate candidate methods for Diamond type
   * inference.
   * 
   * @return the substituted Access node
   * @aspect Diamond
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:397
   */
  public Access substituted(Collection<TypeVariable> original,
      List<TypeVariable> substitution) {
    return (Access) treeCopyNoTransform();
  }
  /**
   * Checks that two type accesses are the same, while taking type variable
   * substitution into account.
   * @aspect FunctionalInterface
   * @declaredat /home/olivier/projects/extendj/java8/frontend/FunctionalInterface.jrag:164
   */
  public boolean sameType(Access a) {
    if (this instanceof ArrayTypeAccess && a instanceof ArrayTypeAccess) {
      ArrayTypeAccess at1 = (ArrayTypeAccess) this;
      ArrayTypeAccess at2 = (ArrayTypeAccess) a;
      return at1.sameType(at2);
    } else if (this instanceof AbstractWildcard && a instanceof AbstractWildcard) {
      AbstractWildcard w1 = (AbstractWildcard) this;
      AbstractWildcard w2 = (AbstractWildcard) a;
      return w1.sameType(w2);
    } else if (this instanceof TypeAccess && a instanceof TypeAccess) {
      TypeAccess t1 = (TypeAccess) this;
      TypeAccess t2 = (TypeAccess) a;
      return t1.sameType(t2);
    } else if (this instanceof ParTypeAccess && a instanceof ParTypeAccess) {
      ParTypeAccess pta1 = (ParTypeAccess) this;
      ParTypeAccess pta2 = (ParTypeAccess) a;
      return pta1.sameType(pta2);
    } else {
      return false;
    }
  }
  /**
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:473
   */
  public soot.Local emitThis(Body b, TypeDecl targetDecl) {
    //b.setLine(this);
    if(targetDecl == hostType())
      return b.emitThis(hostType(), this);
    else {
      TypeDecl enclosing = hostType();
      Local base;
      if(inExplicitConstructorInvocation()) {
        base = asLocal(b,
          hostingCtorHack().getExplicitisedParameters().getChild(0).local
          //b.newParameterRef(enclosing.enclosingType().getSootType(), 0, this)
        );
        enclosing = enclosing.enclosing();
      }
      else {
        base = b.emitThis(hostType(), this);
      }
      while(enclosing != targetDecl) {
        Local next = b.newTemp(enclosing.enclosingType().getSootType(), this);
        b.add(
          b.newAssignStmt(
            next,
            b.newInstanceFieldRef(
              base,
              enclosing.getSootField("this$0", enclosing.enclosingType()).makeRef(),
              this
            ),
            this
          )
        );
        base = next;
        enclosing = enclosing.enclosingType();
      }
      return base;
    }
  }
  /**
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:703
   */
  public void addArraySize(Body b, ArrayList list) {
  }
  /**
   * @declaredat ASTNode:1
   */
  public Access() {
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
    type_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:28
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:32
   */
  public Access clone() throws CloneNotSupportedException {
    Access node = (Access) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:43
   */
  @Deprecated
  public abstract Access fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:51
   */
  public abstract Access treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:59
   */
  public abstract Access treeCopy();
  /**
   * Find the outermost qualified expression this access, if this access is
   * qualified. Otherwise, returns this access.
   * 
   * <p>For example, if {@code unqualifiedScope()} is evaluated for the {@code
   * MethodAccess} inside the expression {@code Dot(FieldAccess,
   * Dot(FieldAccess, MethodAcceess))}, then the outermost {@code Dot} is the
   * unqualified scope of the {@code MethodAccess}.
   * @attribute syn
   * @aspect LookupMethod
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:60
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupMethod", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:60")
  public Expr unqualifiedScope() {
    Expr unqualifiedScope_value = isQualified() ? nestedScope() : this;
    return unqualifiedScope_value;
  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:164
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:164")
  public boolean isQualified() {
    boolean isQualified_value = hasPrevExpr();
    return isQualified_value;
  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:167
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:167")
  public Expr qualifier() {
    Expr qualifier_value = prevExpr();
    return qualifier_value;
  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:174
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:174")
  public Access lastAccess() {
    Access lastAccess_value = this;
    return lastAccess_value;
  }
  /**
   * @attribute syn
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:184
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="QualifiedNames", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:184")
  public boolean hasPrevExpr() {
    boolean hasPrevExpr_value = isRightChildOfDot();
    return hasPrevExpr_value;
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
    {
        switch (nameType()) {
          case PACKAGE_NAME:
            return NameType.PACKAGE_NAME;
          case TYPE_NAME:
          case PACKAGE_OR_TYPE_NAME:
            return NameType.PACKAGE_OR_TYPE_NAME;
          case AMBIGUOUS_NAME:
          case EXPRESSION_NAME:
            return NameType.AMBIGUOUS_NAME;
          case NOT_CLASSIFIED:
          default:
            return NameType.NOT_CLASSIFIED;
        }
      }
  }
  /** @apilevel internal */
  private void type_reset() {
    type_computed = null;
    type_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle type_computed = null;

  /** @apilevel internal */
  protected TypeDecl type_value;

  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:296
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:296")
  public TypeDecl type() {
    ASTNode$State state = state();
    if (type_computed == ASTNode$State.NON_CYCLE || type_computed == state().cycle()) {
      return type_value;
    }
    type_value = unknownType();
    if (state().inCircle()) {
      type_computed = state().cycle();
    
    } else {
      type_computed = ASTNode$State.NON_CYCLE;
    
    }
    return type_value;
  }
  /**
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:57
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="InnerClasses", declaredAt="/home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:57")
  public TypeDecl fieldQualifierType() {
    {
        throw new Error("Can not evaluate fieldQualifierType() on node of type "
            + getClass().getSimpleName());
      }
  }
  /**
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:135
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="InnerClasses", declaredAt="/home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:135")
  public TypeDecl superConstructorQualifier(TypeDecl targetEnclosingType) {
    {
        TypeDecl enclosing = hostType();
        while (!enclosing.instanceOf(targetEnclosingType)) {
          enclosing = enclosing.enclosingType();
        }
        return enclosing;
      }
  }
  /**
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:394
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="InnerClasses", declaredAt="/home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:394")
  public boolean requiresAccessor() {
    {
        throw new Error("Can not evaluate requiresAccessor() on node of type "
            + getClass().getSimpleName());
      }
  }
  /**
   * Creates a copy of this access where parameterized types have been erased.
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1597
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:1597")
  public Access erasedCopy() {
    Access erasedCopy_value = treeCopyNoTransform();
    return erasedCopy_value;
  }
  /**
   * WARNING: this attribute is not the same as TypeDecl.isWildcard,
   * which returns true for any wildcard type (even bounded wildcard types).
   * @return {@code true} if this is an unbounded wildcard access
   * @attribute syn
   * @aspect ReifiableTypes
   * @declaredat /home/olivier/projects/extendj/java5/frontend/ReifiableTypes.jrag:106
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ReifiableTypes", declaredAt="/home/olivier/projects/extendj/java5/frontend/ReifiableTypes.jrag:106")
  public boolean isWildcard() {
    boolean isWildcard_value = false;
    return isWildcard_value;
  }
  /**
   * @attribute syn
   * @aspect Diamond
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:87
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Diamond", declaredAt="/home/olivier/projects/extendj/java7/frontend/Diamond.jrag:87")
  public boolean isDiamond() {
    boolean isDiamond_value = false;
    return isDiamond_value;
  }
  /**
   * @attribute syn
   * @aspect LambdaParametersInference
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TypeCheck.jrag:620
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LambdaParametersInference", declaredAt="/home/olivier/projects/extendj/java8/frontend/TypeCheck.jrag:620")
  public boolean mentionsTypeVariable(TypeVariable var) {
    boolean mentionsTypeVariable_TypeVariable_value = false;
    return mentionsTypeVariable_TypeVariable_value;
  }
  /**
   * @return {@code true} if this access is a method call of a non-static method.
   * @attribute syn
   * @aspect GenerateClassfile
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenerateClassfile.jrag:138
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenerateClassfile", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenerateClassfile.jrag:138")
  public boolean isInstanceMethodAccess() {
    boolean isInstanceMethodAccess_value = false;
    return isInstanceMethodAccess_value;
  }
  /**
   * Find the outermost qualified expression of a qualified access.
   * 
   * <p>It is an error if this attribute is evaluated on an unqualified
   * access.
   * 
   * @throws UnsupportedOperationException if evaluated on an unqualified
   * access.
   * @attribute inh
   * @aspect LookupMethod
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:71
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupMethod", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:71")
  public Expr nestedScope() {
    Expr nestedScope_value = getParent().Define_nestedScope(this, null);
    return nestedScope_value;
  }
  /**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:325
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:325")
  public TypeDecl unknownType() {
    TypeDecl unknownType_value = getParent().Define_unknownType(this, null);
    return unknownType_value;
  }
  /**
   * @attribute inh
   * @aspect VariableScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:372
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="VariableScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:372")
  public Variable unknownField() {
    Variable unknownField_value = getParent().Define_unknownField(this, null);
    return unknownField_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:403
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:403")
  public boolean withinSuppressWarnings(String annot) {
    boolean withinSuppressWarnings_String_value = getParent().Define_withinSuppressWarnings(this, null, annot);
    return withinSuppressWarnings_String_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:534
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Annotations", declaredAt="/home/olivier/projects/extendj/java5/frontend/Annotations.jrag:534")
  public boolean withinDeprecatedAnnotation() {
    boolean withinDeprecatedAnnotation_value = getParent().Define_withinDeprecatedAnnotation(this, null);
    return withinDeprecatedAnnotation_value;
  }
  /**
   * @attribute inh
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:342
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Expressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:342")
  public boolean inExplicitConstructorInvocation() {
    boolean inExplicitConstructorInvocation_value = getParent().Define_inExplicitConstructorInvocation(this, null);
    return inExplicitConstructorInvocation_value;
  }
  /**
   * @attribute inh
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:538
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Expressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:538")
  public ConstructorDecl hostingCtorHack() {
    ConstructorDecl hostingCtorHack_value = getParent().Define_hostingCtorHack(this, null);
    return hostingCtorHack_value;
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
}
