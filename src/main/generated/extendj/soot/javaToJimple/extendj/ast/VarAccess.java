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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:81
 * @astdecl VarAccess : Access ::= <ID:String>;
 * @production VarAccess : {@link Access} ::= <span class="component">&lt;ID:String&gt;</span>;

 */
public class VarAccess extends Access implements Cloneable {
  /**
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:579
   */
  protected boolean checkDUeverywhere(Variable v) {
    if (isDest() && decl() == v) {
      return false;
    }
    return super.checkDUeverywhere(v);
  }
  /**
   * @aspect NameCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:330
   */
  public BodyDecl closestBodyDecl(TypeDecl t) {
    ASTNode node = this;
    while (!(node.getParent().getParent() instanceof Program)
        && node.getParent().getParent() != t) {
      node = node.getParent();
    }
    if (node instanceof BodyDecl) {
      return (BodyDecl) node;
    }
    return null;
  }
  /**
   * @aspect NodeConstructors
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NodeConstructors.jrag:52
   */
  public VarAccess(String name, int start, int end) {
    this(name);
    this.start = this.IDstart = start;
    this.end = this.IDend = end;
  }
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:626
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getID());
  }
  /**
   * @aspect PrettyPrintUtil
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:97
   */
  @Override public String toString() {
    return name();
  }
  /**
   * @aspect InnerClasses
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:210
   */
  public void collectEnclosingVariables(Collection<Variable> vars, TypeDecl typeDecl) {
    Variable v = decl();
    if (!v.isInstanceVariable() && !v.isClassVariable() && v.hostType() == typeDecl) {
      vars.add(v);
    }
    super.collectEnclosingVariables(vars, typeDecl);
  }
  /**
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:179
   */
  protected Value evalField(Body b, Variable v) {
    assert v.isField();

    if (v.hostType().isArrayDecl() && v.name().equals("length"))
      return b.newLengthExpr(createLoadQualifier(b), this);

    if (v.isStatic()) {
      if (isQualified() && !qualifier().isTypeAccess())
        b.newTemp(qualifier().eval(b));

      if (requiresAccessor()) {
        SootMethodRef fn = fieldQualifierType().erasure().fieldAccessor(v).sootRef();
        return b.newStaticInvokeExpr(fn, Collections.emptyList(), this);
      }

      return b.newStaticFieldRef(sootRef(), this);
    }

    if (requiresAccessor()) {
      SootMethodRef fn = fieldQualifierType().erasure().fieldAccessor(v).sootRef();
      return b.newStaticInvokeExpr(fn, Collections.singletonList(base(b)), this);
    }

    return b.newInstanceFieldRef(createLoadQualifier(b), sootRef(), this);
  }
  /**
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:233
   */
  private SootFieldRef sootRef() {
    Variable v = decl();
    assert v.isField();
    if (v instanceof FieldDeclarator)
      v = ((FieldDeclarator) v).erasedField();

    return Scene.v().makeFieldRef(
      fieldQualifierType().sootClass(),
      v.name(),
      v.type().sootType(),
      v.isStatic()
    );
  }
  /**
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:259
   */
  protected void emitStore(Body b, Value lvalue, Value rvalue, ASTNode location) {
    Variable v = decl();
    if (v.isField() && requiresAccessor()) {
      InvokeExpr invoke;
      if (((FieldDeclarator)v).erasedField().isStatic()) {
        invoke = b.newStaticInvokeExpr(
          fieldQualifierType().erasure().fieldAccessor(v).sootRef(),
          Collections.singletonList(rvalue),
          this);
      } else {
        invoke = b.newStaticInvokeExpr(
          fieldQualifierType().erasure().fieldWriteAccessor(v).sootRef(),
          Arrays.asList(base(b), rvalue),
          this);
      }

      b.add(b.newInvokeStmt(invoke, this));
      return;
    }

    super.emitStore(b, lvalue, rvalue, location);
  }
  /**
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:283
   */
  public Value emitLoadLocalInNestedClass(Body b) {
    if (inExplicitConstructorInvocation() && enclosingBodyDecl() instanceof ConstructorDecl) {
      ConstructorDecl ctor = ((ConstructorDecl)enclosingBodyDecl());
      for (ParameterDeclaration p : ctor.getExplicitisedParameters())
        if (p.name().equals(decl().name())) return b.local(p);

      throw new RuntimeException("attempted to access a invalid/undeclared ctor param?");
    }

    return b.newInstanceFieldRef(
        b.emitThis(hostType(), this),
        Scene.v().makeFieldRef(
          hostType().sootClass(),
          decl().capturedParamName(),
          decl().type().sootType(),
          false),
        this
        //hostType().sootClass().getField("val$" + v.name(), v.type().sootType()).makeRef()
      );
  }
  /**
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:304
   */
  public Local createLoadQualifier(Body b) {
    Variable v = decl();
    if (v.isField()) {
      FieldDeclarator f = ((FieldDeclarator)v).erasedField();
      if (hasPrevExpr())
        return b.asLocal(prevExpr().eval(b));

      if (f.isInstanceVariable())
        return emitThis(b, fieldQualifierType().erasure());
    }

    throw new Error("createLoadQualifier not supported for " + v.getClass().getName());
  }
  /**
   * @declaredat ASTNode:1
   */
  public VarAccess() {
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
  /**
   * @declaredat ASTNode:12
   */
  @ASTNodeAnnotation.Constructor(
    name = {"ID"},
    type = {"String"},
    kind = {"Token"}
  )
  public VarAccess(String p0) {
    setID(p0);
  }
  /**
   * @declaredat ASTNode:20
   */
  public VarAccess(beaver.Symbol p0) {
    setID(p0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:24
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:30
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:34
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    isConstant_reset();
    unassignedAfter_Variable_reset();
    decls_reset();
    decl_reset();
    isFieldAccess_reset();
    type_reset();
    base_Body_reset();
    enclosingLambda_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:46
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:50
   */
  public VarAccess clone() throws CloneNotSupportedException {
    VarAccess node = (VarAccess) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:55
   */
  public VarAccess copy() {
    try {
      VarAccess node = (VarAccess) clone();
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
   * @declaredat ASTNode:74
   */
  @Deprecated
  public VarAccess fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:84
   */
  public VarAccess treeCopyNoTransform() {
    VarAccess tree = (VarAccess) copy();
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
   * @declaredat ASTNode:104
   */
  public VarAccess treeCopy() {
    VarAccess tree = (VarAccess) copy();
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
   * @declaredat ASTNode:118
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((VarAccess) node).tokenString_ID);    
  }
  /**
   * Replaces the lexeme ID.
   * @param value The new value for the lexeme ID.
   * @apilevel high-level
   */
  public void setID(String value) {
    tokenString_ID = value;
  }
  /** @apilevel internal 
   */
  protected String tokenString_ID;
  /**
   */
  public int IDstart;
  /**
   */
  public int IDend;
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
   * @aspect InnerClasses
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:62
   */
  private TypeDecl refined_InnerClasses_VarAccess_fieldQualifierType()
{
    if (hasPrevExpr()) {
      return prevExpr().type();
    }
    TypeDecl typeDecl = hostType();
    while (typeDecl != null && !typeDecl.hasField(name())) {
      typeDecl = typeDecl.enclosingType();
    }
    if (typeDecl != null) {
      return typeDecl;
    }
    return decl().hostType();
  }
  /**
   * @aspect GenericsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenericsCodegen.jrag:93
   */
  private TypeDecl refined_GenericsCodegen_VarAccess_fieldQualifierType()
{
    TypeDecl typeDecl = refined_InnerClasses_VarAccess_fieldQualifierType();
    return typeDecl == null ? null : typeDecl.erasure();
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:32
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:32")
  public Constant constant() {
    Constant constant_value = type().cast(decl().constant());
    return constant_value;
  }
/** @apilevel internal */
protected ASTState.Cycle isConstant_cycle = null;
  /** @apilevel internal */
  private void isConstant_reset() {
    isConstant_computed = false;
    isConstant_initialized = false;
    isConstant_cycle = null;
  }
  /** @apilevel internal */
  protected boolean isConstant_computed = false;

  /** @apilevel internal */
  protected boolean isConstant_value;
  /** @apilevel internal */
  protected boolean isConstant_initialized = false;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:423")
  public boolean isConstant() {
    if (isConstant_computed) {
      return isConstant_value;
    }
    ASTState state = state();
    if (!isConstant_initialized) {
      isConstant_initialized = true;
      isConstant_value = false;
    }
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      do {
        isConstant_cycle = state.nextCycle();
        boolean new_isConstant_value = isConstant_compute();
        if (isConstant_value != new_isConstant_value) {
          state.setChangeInCycle();
        }
        isConstant_value = new_isConstant_value;
      } while (state.testAndClearChangeInCycle());
      isConstant_computed = true;

      state.leaveCircle();
    } else if (isConstant_cycle != state.cycle()) {
      isConstant_cycle = state.cycle();
      boolean new_isConstant_value = isConstant_compute();
      if (isConstant_value != new_isConstant_value) {
        state.setChangeInCycle();
      }
      isConstant_value = new_isConstant_value;
    } else {
    }
    return isConstant_value;
  }
  /** @apilevel internal */
  private boolean isConstant_compute() {
      Variable v = decl();
      if (v.isField()) {
        return v.isConstant() && (!isQualified() || (isQualified() && qualifier().isTypeAccess()));
      } else {
        return v.isFinal() && v.hasInit()
            && v.getInit().isConstant() && (v.type().isPrimitive() || v.type().isString())
            && (!isQualified() || (isQualified() && qualifier().isTypeAccess()));
      }
    }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:77
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:77")
  public Variable varDecl() {
    Variable varDecl_value = decl();
    return varDecl_value;
  }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:111
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:111")
  public Collection<Problem> definiteAssignmentProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        if (isSource()) {
          if (decl() instanceof Declarator) {
            Declarator v = (Declarator) decl();
            if (v.isField()) {
              if (v.isFinal() && !v.hasInit() && !isQualified() && !assignedBefore(v)) {
                problems.add(errorf("Final field %s is not assigned before used", v.name()));
              }
            } else if (!v.isValue()) {
              if (v.isBlankFinal()) {
                if (!assignedBefore(v)) {
                  problems.add(errorf("Final variable %s is not assigned before used", v.name()));
                }
              } else {
                // We can not use v.hasInit() here as a quick test for assignedness, because
                // v is a variable and the initialization may not have been reached from the
                // current access, e.g., if declared in a previous switch branch.
                if (!assignedBefore(v)) {
                  problems.add(errorf("Local variable %s is not assigned before used", v.name()));
                }
              }
            }
          }
        }
        if (isDest()) {
          Variable v = decl();
          if (v.isFinal() && v.isBlank() && !hostType().instanceOf(v.hostType())) {
            // Blank final field.
            problems.add(error("The final variable is not a blank final in this context, "
                + "so it may not be assigned."));
          } else if (v.isFinal() && isQualified()
              && (!qualifier().isThisAccess() || ((Access) qualifier()).isQualified())) {
            problems.add(errorf("the blank final field %s may only be assigned by simple name",
                  v.name()));
          } else if (v instanceof VariableDeclarator) {
            // Local variable.
            VariableDeclarator var = (VariableDeclarator) v;
            if (!var.isValue()
                // TODO(joqvist): use inherited attribute instead.
                && var.getParent().getParent().getParent() instanceof SwitchStmt
                && var.isFinal()) {
              if (!unassignedBefore(var)) {
                problems.add(errorf("Final variable %s may only be assigned once", var.name()));
              }
            } else if (var.isValue()) {
              if (var.hasInit() || !unassignedBefore(var)) {
                problems.add(errorf("Final variable %s may only be assigned once", var.name()));
              }
            } else if (var.isBlankFinal()) {
              if (var.hasInit() || !unassignedBefore(var)) {
                problems.add(errorf("Final variable %s may only be assigned once", var.name()));
              }
            }
          } else if (v.isField()) {
            // Field.
            if (v.isFinal()) {
              if (v.hasInit()) {
                problems.add(errorf("already initialized final field %s can not be assigned",
                      v.name()));
              } else {
                BodyDecl bodyDecl = enclosingBodyDecl();
                if (!(bodyDecl instanceof ConstructorDecl)
                    && !(bodyDecl instanceof InstanceInitializer)
                    && !(bodyDecl instanceof StaticInitializer)
                    && !(bodyDecl instanceof FieldDecl)) {
                  problems.add(errorf(
                      "final field %s may only be assigned in constructors and initializers",
                      v.name()));
                } else if (!unassignedBefore(v)) {
                  problems.add(errorf("blank final field %s may only be assigned once", v.name()));
                }
              }
            }
          } else if (v.isParameter()) {
            // 8.4.1
            if (v.isFinal()) {
              problems.add(errorf("Final parameter %s may not be assigned", v.name()));
            }
          }
        }
        return problems;
      }
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
  private void unassignedAfter_Variable_reset() {
    unassignedAfter_Variable_values = null;
  }
  protected java.util.Map unassignedAfter_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:899")
  public boolean unassignedAfter(Variable v) {
    Object _parameters = v;
    if (unassignedAfter_Variable_values == null) unassignedAfter_Variable_values = new java.util.HashMap(4);
    ASTState.CircularValue _value;
    if (unassignedAfter_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfter_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTState.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTState.CircularValue) _cache;
      }
    } else {
      _value = new ASTState.CircularValue();
      unassignedAfter_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTState state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfter_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfter_Variable_value = unassignedBefore(v);
        if (((Boolean)_value.value) != new_unassignedAfter_Variable_value) {
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
      if (((Boolean)_value.value) != new_unassignedAfter_Variable_value) {
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
  protected ASTState.Cycle decls_computed = null;

  /** @apilevel internal */
  protected SimpleSet<Variable> decls_value;

  /**
   * @attribute syn
   * @aspect VariableScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:388
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="VariableScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:388")
  public SimpleSet<Variable> decls() {
    ASTState state = state();
    if (decls_computed == ASTState.NON_CYCLE || decls_computed == state().cycle()) {
      return decls_value;
    }
    decls_value = decls_compute();
    if (state().inCircle()) {
      decls_computed = state().cycle();
    
    } else {
      decls_computed = ASTState.NON_CYCLE;
    
    }
    return decls_value;
  }
  /** @apilevel internal */
  private SimpleSet<Variable> decls_compute() {
      SimpleSet<Variable> result = lookupVariable(name());
      if (result.isSingleton()) {
        Variable v = result.singletonValue();
        if (!isQualified() && inStaticContext()) {
          if (v.isInstanceVariable() && !hostType().memberFields(v.name()).isEmpty()) {
            return emptySet();
          }
        } else if (isQualified() && qualifier().staticContextQualifier()) {
          if (v.isInstanceVariable()) {
            return emptySet();
          }
        }
      }
      return result;
    }
  /** @apilevel internal */
  private void decl_reset() {
    decl_computed = null;
    decl_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle decl_computed = null;

  /** @apilevel internal */
  protected Variable decl_value;

  /**
   * @attribute syn
   * @aspect VariableScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:405
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="VariableScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:405")
  public Variable decl() {
    ASTState state = state();
    if (decl_computed == ASTState.NON_CYCLE || decl_computed == state().cycle()) {
      return decl_value;
    }
    decl_value = decl_compute();
    if (state().inCircle()) {
      decl_computed = state().cycle();
    
    } else {
      decl_computed = ASTState.NON_CYCLE;
    
    }
    return decl_value;
  }
  /** @apilevel internal */
  private Variable decl_compute() {
      SimpleSet<Variable> decls = decls();
      if (decls.isSingleton()) {
        return decls.singletonValue();
      }
      return unknownField();
    }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:280
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:280")
  public Collection<Problem> nameProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        if (decls().isEmpty() && (!isQualified() || !qualifier().type().isUnknown()
              || qualifier().isPackageAccess())) {
          problems.add(errorf("no field named %s is accessible", name()));
        }
    
        if (decls().size() > 1) {
          StringBuilder sb = new StringBuilder();
          sb.append("several fields named " + name());
          ArrayList<String> fields = new ArrayList<String>();
          for (Variable v : decls()) {
            fields.add(String.format("%n    %s %s declared in %s",
                v.type().typeName(), v.name(), v.hostType().typeName()));
          }
          Collections.sort(fields);
          for (String line : fields) {
            sb.append(line);
          }
          problems.add(error(sb.toString()));
        }
    
        // 8.8.5.1
        if (inExplicitConstructorInvocation() && !isQualified() && decl().isInstanceVariable()
            && hostType() == decl().hostType()) {
          problems.add(errorf("instance variable %s may not be accessed in an explicit constructor invocation",
              name()));
        }
    
        Variable var = decl();
        if (!var.isClassVariable() && !var.isInstanceVariable() && var.hostType() != hostType()
            && !var.isEffectivelyFinal()) {
          problems.add(error("A parameter/variable used but not declared in an inner class must be"
              + " final or effectively final"));
        }
    
        // 8.3.2.3
        if ((decl().isInstanceVariable() || decl().isClassVariable()) && !isQualified()) {
          if (hostType() != null && !declaredBefore(decl())) {
            if (inSameInitializer() && !simpleAssignment() && inDeclaringClass()) {
              BodyDecl b = closestBodyDecl(hostType());
              problems.add(errorf("variable %s is used in %s before it is declared",
                  decl().name(), b.prettyPrint()));
            }
          }
        }
    
        if (!var.isClassVariable() && !var.isInstanceVariable() && enclosingLambda() != null) {
          if (var instanceof ParameterDeclaration) {
            ParameterDeclaration decl = (ParameterDeclaration) var;
            if (decl.enclosingLambda() != enclosingLambda()) {
              // 15.27.2
              if (!decl.isEffectivelyFinal()) {
                problems.add(errorf("Parameter %s must be effectively final", var.name()));
              }
            }
          } else if (var instanceof InferredParameterDeclaration) {
            InferredParameterDeclaration decl = (InferredParameterDeclaration) var;
            if (decl.enclosingLambda() != enclosingLambda()) {
              // 15.27.2
              if (!decl.isEffectivelyFinal()) {
                problems.add(errorf("Parameter %s must be effectively final", var.name()));
              }
            }
          } else if (var instanceof VariableDeclarator) {
            VariableDeclarator decl = (VariableDeclarator) var;
            if (decl.enclosingLambda() != enclosingLambda()) {
              // 15.27.2
              if (!decl.isEffectivelyFinal()) {
                problems.add(errorf("Variable %s must be effectively final", var.name()));
              }
              // 15.27.2
              if (!enclosingLambda().assignedBefore(decl)) {
                problems.add(errorf("Variable %s must be definitely assigned before used in a lambda",
                    var.name()));
              }
            }
          }
        }
        return problems;
      }
  }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:342
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:342")
  public boolean inSameInitializer() {
    {
        BodyDecl b = closestBodyDecl(decl().hostType());
        if (b == null) {
          return false;
        }
        if (b instanceof FieldDecl && ((FieldDecl) b).isStatic() == decl().isStatic()) {
          // TODO(joqvist): fixme
          return true;
        }
        if (b instanceof InstanceInitializer && !decl().isStatic()) {
          return true;
        }
        if (b instanceof StaticInitializer && decl().isStatic()) {
          return true;
        }
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:360
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:360")
  public boolean simpleAssignment() {
    boolean simpleAssignment_value = isDest() && getParent() instanceof AssignSimpleExpr;
    return simpleAssignment_value;
  }
  /**
   * @attribute syn
   * @aspect NameCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:362
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:362")
  public boolean inDeclaringClass() {
    boolean inDeclaringClass_value = hostType() == decl().hostType();
    return inDeclaringClass_value;
  }
  /**
   * @attribute syn
   * @aspect Names
   * @declaredat /home/olivier/projects/extendj/java4/frontend/QualifiedNames.jrag:35
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Names", declaredAt="/home/olivier/projects/extendj/java4/frontend/QualifiedNames.jrag:35")
  public String name() {
    String name_value = getID();
    return name_value;
  }
  /** @apilevel internal */
  private void isFieldAccess_reset() {
    isFieldAccess_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle isFieldAccess_computed = null;

  /** @apilevel internal */
  protected boolean isFieldAccess_value;

  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:53
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:53")
  public boolean isFieldAccess() {
    ASTState state = state();
    if (isFieldAccess_computed == ASTState.NON_CYCLE || isFieldAccess_computed == state().cycle()) {
      return isFieldAccess_value;
    }
    isFieldAccess_value = decl().isClassVariable() || decl().isInstanceVariable();
    if (state().inCircle()) {
      isFieldAccess_computed = state().cycle();
    
    } else {
      isFieldAccess_computed = ASTState.NON_CYCLE;
    
    }
    return isFieldAccess_value;
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
    NameType predNameType_value = NameType.AMBIGUOUS_NAME;
    return predNameType_value;
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
    type_value = decl().type();
    if (state().inCircle()) {
      type_computed = state().cycle();
    
    } else {
      type_computed = ASTState.NON_CYCLE;
    
    }
    return type_value;
  }
  /**
   * @attribute syn
   * @aspect TypeCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:33
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:33")
  public boolean isVariable() {
    boolean isVariable_value = true;
    return isVariable_value;
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
        TypeDecl typeDecl = refined_GenericsCodegen_VarAccess_fieldQualifierType();
        if (typeDecl != null) {
          return typeDecl;
        }
        return decl().hostType();
      }
  }
  /**
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:399
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="InnerClasses", declaredAt="/home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:399")
  public boolean requiresAccessor() {
    {
        Variable v = decl();
        if (!(v instanceof FieldDeclarator)) {
          return false;
        }
        FieldDeclarator f = (FieldDeclarator) v;
        if (f.isPrivate() && v.hostType() != hostType()) {
          return true;
        }
        if (f.isProtected() && !f.hostPackage().equals(hostPackage())
            && !hostType().hasField(v.name())) {
          return true;
        }
        return false;
      }
  }
  /**
   * @attribute syn
   * @aspect Enums
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:645
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="/home/olivier/projects/extendj/java5/frontend/Enums.jrag:645")
  public boolean isEnumConstant() {
    boolean isEnumConstant_value = varDecl() instanceof EnumConstant;
    return isEnumConstant_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:33
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:33")
  public Collection<TypeDecl> throwTypes() {
    Collection<TypeDecl> throwTypes_value = decl().throwTypes();
    return throwTypes_value;
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
    boolean isVariable_Variable_value = decl() == var;
    return isVariable_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:42
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Expressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:42")
  public Value eval(Body b) {
    {
        Variable v = decl();
    
        if (v instanceof FieldDeclarator) {
          FieldDeclarator f       = ((FieldDeclarator)v).erasedField();
          Value           result  = evalField(b, f);
    
          if (f.type() != v.type())
            result = f.type().emitCastTo(b, result, v.type(), this);
    
          return result;
        }
    
        if (v instanceof VariableDeclarator   ||
            v instanceof ParameterDeclaration ||
            v instanceof CatchParameterDeclaration) {
          if (v.hostType() != hostType())
            return emitLoadLocalInNestedClass(b);
    
          return b.local(v);
        }
    
        if (v instanceof EnumConstant)
          return evalField(b, v);
    
        return eval_fail("unknown/unhandled variable type: " + v.getClass().getName());
      }
  }
  /** @apilevel internal */
  private void base_Body_reset() {
    base_Body_computed = null;
    base_Body_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map base_Body_values;
  /** @apilevel internal */
  protected java.util.Map base_Body_computed;
  /**
   * @attribute syn
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:249
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Expressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:249")
  public Local base(Body b) {
    Object _parameters = b;
    if (base_Body_computed == null) base_Body_computed = new java.util.HashMap(4);
    if (base_Body_values == null) base_Body_values = new java.util.HashMap(4);
    ASTState state = state();
    if (base_Body_values.containsKey(_parameters)
        && base_Body_computed.containsKey(_parameters)
        && (base_Body_computed.get(_parameters) == ASTState.NON_CYCLE || base_Body_computed.get(_parameters) == state().cycle())) {
      return (Local) base_Body_values.get(_parameters);
    }
    Local base_Body_value = b.asLocal(createLoadQualifier(b));
    if (state().inCircle()) {
      base_Body_values.put(_parameters, base_Body_value);
      base_Body_computed.put(_parameters, state().cycle());
    
    } else {
      base_Body_values.put(_parameters, base_Body_value);
      base_Body_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return base_Body_value;
  }
  /**
   * @attribute syn
   * @aspect GenerateClassfile
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenerateClassfile.jrag:74
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenerateClassfile", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenerateClassfile.jrag:74")
  public boolean requiresFieldAccessor() {
    boolean requiresFieldAccessor_value = requiresAccessor() && isSource();
    return requiresFieldAccessor_value;
  }
  /**
   * @attribute syn
   * @aspect GenerateClassfile
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/GenerateClassfile.jrag:83
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="GenerateClassfile", declaredAt="/home/olivier/projects/extendj/jimple8/backend/GenerateClassfile.jrag:83")
  public boolean requiresFieldWriteAccessor() {
    boolean requiresFieldWriteAccessor_value = requiresAccessor() && isDest();
    return requiresFieldWriteAccessor_value;
  }
  /**
   * @attribute syn
   * @aspect ResolverDependencies
   * @declaredat /home/olivier/projects/extendj/soot8/backend/ResolverDependencies.jrag:6
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ResolverDependencies", declaredAt="/home/olivier/projects/extendj/soot8/backend/ResolverDependencies.jrag:6")
  public TypeDecl sootSigDepType() {
    TypeDecl sootSigDepType_value = fieldQualifierType()                 .elementType().erasure();
    return sootSigDepType_value;
  }
  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:199
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:199")
  public boolean inExplicitConstructorInvocation() {
    boolean inExplicitConstructorInvocation_value = getParent().Define_inExplicitConstructorInvocation(this, null);
    return inExplicitConstructorInvocation_value;
  }
  /** Checks if this var access is inside an instance initializer for an enum type. 
   * @attribute inh
   * @aspect Enums
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:563
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Enums", declaredAt="/home/olivier/projects/extendj/java5/frontend/Enums.jrag:563")
  public boolean inEnumInitializer() {
    boolean inEnumInitializer_value = getParent().Define_inEnumInitializer(this, null);
    return inEnumInitializer_value;
  }
  /**
   * @attribute inh
   * @aspect EnclosingLambda
   * @declaredat /home/olivier/projects/extendj/java8/frontend/EnclosingLambda.jrag:32
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="EnclosingLambda", declaredAt="/home/olivier/projects/extendj/java8/frontend/EnclosingLambda.jrag:32")
  public LambdaExpr enclosingLambda() {
    ASTState state = state();
    if (enclosingLambda_computed == ASTState.NON_CYCLE || enclosingLambda_computed == state().cycle()) {
      return enclosingLambda_value;
    }
    enclosingLambda_value = getParent().Define_enclosingLambda(this, null);
    if (state().inCircle()) {
      enclosingLambda_computed = state().cycle();
    
    } else {
      enclosingLambda_computed = ASTState.NON_CYCLE;
    
    }
    return enclosingLambda_value;
  }
  /** @apilevel internal */
  private void enclosingLambda_reset() {
    enclosingLambda_computed = null;
    enclosingLambda_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle enclosingLambda_computed = null;

  /** @apilevel internal */
  protected LambdaExpr enclosingLambda_value;

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
    // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:109
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:278
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:504
    if (decl().isField()
              && decl().getModifiers().hasDeprecatedAnnotation()
              && !withinDeprecatedAnnotation()
              && hostType().topLevelType() != decl().hostType().topLevelType()
              && !withinSuppressWarnings("deprecation")) {
      {
        java.util.Set<ASTNode> contributors = _map.get(_root);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) _root, contributors);
        }
        contributors.add(this);
      }
    }
    // @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:577
    if (decl().isStatic()
              && decl().hostType() == hostType()
              && !isConstant()
              && inEnumInitializer()) {
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
  protected void collect_contributors_TypeDecl_accessors(CompilationUnit _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat /home/olivier/projects/extendj/jimple8/backend/GenerateClassfile.jrag:68
    if (requiresFieldAccessor() || requiresFieldWriteAccessor()) {
      {
        TypeDecl target = (TypeDecl) (fieldQualifierType());
        java.util.Set<ASTNode> contributors = _map.get(target);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) target, contributors);
        }
        contributors.add(this);
      }
    }
    // @declaredat /home/olivier/projects/extendj/jimple8/backend/GenerateClassfile.jrag:77
    if (requiresFieldWriteAccessor()) {
      {
        TypeDecl target = (TypeDecl) (fieldQualifierType());
        java.util.Set<ASTNode> contributors = _map.get(target);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) target, contributors);
        }
        contributors.add(this);
      }
    }
    super.collect_contributors_TypeDecl_accessors(_root, _map);
  }
  /** @apilevel internal */
  protected void collect_contributors_CompilationUnit_signatureDependencies(CompilationUnit _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat /home/olivier/projects/extendj/soot8/backend/ResolverDependencies.jrag:21
    if (sootSigDepType().sootDependencyNeeded() && decl() instanceof FieldDeclarator) {
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
  /** @apilevel internal */
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    for (Problem value : definiteAssignmentProblems()) {
      collection.add(value);
    }
    for (Problem value : nameProblems()) {
      collection.add(value);
    }
    if (decl().isField()
              && decl().getModifiers().hasDeprecatedAnnotation()
              && !withinDeprecatedAnnotation()
              && hostType().topLevelType() != decl().hostType().topLevelType()
              && !withinSuppressWarnings("deprecation")) {
      collection.add(warning(decl().name() + " in " + decl().hostType().typeName() + " has been deprecated"));
    }
    if (decl().isStatic()
              && decl().hostType() == hostType()
              && !isConstant()
              && inEnumInitializer()) {
      collection.add(error("may not reference a static field of an enum type from here"));
    }
  }
  /** @apilevel internal */
  protected void contributeTo_TypeDecl_accessors(HashSet<BodyDecl> collection) {
    super.contributeTo_TypeDecl_accessors(collection);
    if (requiresFieldAccessor() || requiresFieldWriteAccessor()) {
      collection.add(fieldQualifierType().erasure().fieldAccessor(((FieldDeclarator)decl()).erasedField()));
    }
    if (requiresFieldWriteAccessor()) {
      collection.add(fieldQualifierType().erasure().fieldWriteAccessor(((FieldDeclarator)decl()).erasedField()));
    }
  }
  /** @apilevel internal */
  protected void contributeTo_CompilationUnit_signatureDependencies(HashSet<Type> collection) {
    super.contributeTo_CompilationUnit_signatureDependencies(collection);
    if (sootSigDepType().sootDependencyNeeded() && decl() instanceof FieldDeclarator) {
      collection.add(sootSigDepType().sootType());
    }
  }
}
