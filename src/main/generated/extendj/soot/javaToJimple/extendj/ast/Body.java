package soot.javaToJimple.extendj.ast;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import soot.*;
import soot.jimple.*;
import soot.validation.ValidationException;

// specify classes which would normally alias with classes declared in our pkg (`ast`)
import java.util.List;
import soot.jimple.AssignStmt;
import soot.jimple.CastExpr;
import soot.jimple.Expr;
import soot.jimple.IdentityStmt;
import soot.jimple.IfStmt;
import soot.jimple.InstanceOfExpr;
import soot.jimple.ParameterRef;
import soot.jimple.ReturnStmt;
import soot.jimple.Stmt;
import soot.jimple.ThrowStmt;


class Body {
  class Label {
    Label()                   { stmt = Jimple.v().newNopStmt(); }
    private Label(Stmt s) { stmt = s; }

    final Stmt        stmt;
    private final JimpleBody  hostBody  = body;
  }

  class CatchLabel extends Label
  { CatchLabel(IdentityStmt except_assign) { super(except_assign); } }

  static class RValue {
    final Value v;
    RValue(Immediate    v) { this.v = v; }
    RValue(ConcreteRef  v) { this.v = v; }
    RValue(Expr         v) { this.v = v; }
  }

  static class Assignable {
    final Value v;
    Assignable(ConcreteRef  v) { this.v = v; }
    Assignable(Local        v) { this.v = v; }
  }


  private final JimpleBody                    body          = Jimple.v().newBody();
  final boolean                       isStatic;
  private       int                           nextTempIndex = 0;
  private final Stack<PatchingChain<Unit>>    chains        = new Stack<>();
  private final Map<Variable, Local>          var2local     = new HashMap<>();
  private final Map<ASTNode, ArrayList<Stmt>> node2exceptionRanges = new HashMap<>();

  // static method
  Body(ASTNode location) {
    isStatic = true;
    chains.push(body.getUnits());
    //setLine(location);
  }

  static boolean s_dbg_throwIfBodyValidationFails = false;
  <T extends MethodLikeDecl<T>>
  ArrayList<ValidationException> bodyValidationExceptions(MethodLikeDecl<T> m) {
    class DanglingUnitBox extends ValidationException {
      final UnitBox box;
      private DanglingUnitBox(Unit u, UnitBox box) {
        super(u, "unit contains a box with a dangling reference");
        this.box = box;
      }
    }

    ArrayList<ValidationException> validSalad = new ArrayList<>();

    // assert no dangling unit references
    final Set<Unit> units_in_body = new HashSet<>(body.getUnits());
    for (Unit     u   : body.getUnits())
      for (UnitBox  box : u.getUnitBoxes())
        if (!units_in_body.contains(box.getUnit()))
          validSalad.add(new DanglingUnitBox(u, box));

    // soot's validators generally all assume a body must be assoicated w/ a method
    body.setMethod(m.sootMethod()); {
      //body.validate(validSalad);
    } body.setMethod(null);

    if (s_dbg_throwIfBodyValidationFails && !validSalad.isEmpty()) {
      class TheJimplificationEndTimesAreHere extends Error {
        ArrayList<ValidationException> tators = validSalad;
        TheJimplificationEndTimesAreHere() { super("jimplification has failed!"); }
      }
      throw new TheJimplificationEndTimesAreHere();
    }

    return validSalad;
  }


  // instance method
  Body(TypeDecl instanceType, ASTNode location) {
    isStatic = instanceType == null;
    chains.push(body.getUnits());
    //setLine(location);

    // non-statics immediately puke out a `this`
    if (!isStatic) emitThis(instanceType, location);
  }


  ArrayList<Stmt> lazy_exceptionRanges(ASTNode n)
  { return node2exceptionRanges.computeIfAbsent(n, k -> new ArrayList<>()); }

  <T extends MethodLikeDecl<T>> JimpleBody finishBody(MethodLikeDecl<T> m) {
    assert bodyValidationExceptions(m).isEmpty();
    return body;
  }


  private Local newTemp_noloc(soot.Type type) {
    Local local = Jimple.v().newLocal("temp$" + nextTempIndex++, type);
    body.getLocals().add(local);
    return local;
  }

  // TODO: How do you express multiple type-bounds on a single type-variable in Java?
  Local local(Variable v) {
    Local l = var2local.get(v);
    if (l == null) {
      l = newLocal(v.name(), v.sootType(), (ASTNode)v);
      var2local.put(v, l);
    }

    return l;
  }

  Local newTemp(soot.Type type, ASTNode location)
  { return setSrcLoc(newTemp_noloc(type), location); }

  Local newTemp(Value v) {
    if (v == NullConstant.v())
      throw new UnsupportedOperationException("Cannot create a temporary local for null literal");

    Local local = newTemp_noloc(v.getType());
    Stmt  stmt  = (v instanceof ParameterRef)
            ? newIdentityStmt_noloc(local, (ParameterRef)v)
            :   newAssignStmt_noloc(asAssignable(local), v);

    add(setSrcLoc(stmt, v));
    return setSrcLoc(local, v);
  }

  Local newLocal(String name, soot.Type type, ASTNode location) {
    Local local = Jimple.v().newLocal(name, type);
    body.getLocals().add(setSrcLoc(local, location));
    assert isStatic || !name.equals("this") || (thisVar == null);
    return local;
  }


  Local asLocal(Value v)
  { return (v instanceof Local    ) ? (Local    )v : newTemp(v); }
  Immediate asImmediate(Value v)
  { return (v instanceof Immediate) ? (Immediate)v : newTemp(v); }

  List<Immediate> asImmediate(List<? extends Value> xs)
  { return xs.stream().map(this::asImmediate).collect(Collectors.toList()); }

  private RValue asRValue(Value v) {
    if (v instanceof Expr       ) return new RValue((Expr       )v);
    if (v instanceof ConcreteRef) return new RValue((ConcreteRef)v);
    return new RValue(asImmediate(v));
  }

  static Assignable asAssignable(Local       v) { return new Assignable(v); }
  static Assignable asAssignable(ConcreteRef v) { return new Assignable(v); }
  static Assignable chkAssignable(Value v) {
    assert v instanceof Local || v instanceof ConcreteRef;
    return v instanceof Local
      ? Body.asAssignable((Local      )v)
      : Body.asAssignable((ConcreteRef)v);
  }

  // we can reuse a `Local` just fine, but we can't touch a `ConcreteRef` more than once.
  Value dstRead(Value v) { return v instanceof Local ? v : setSrcLoc((Value)v.clone(), v); }


  Body add(Stmt stmt) {
    if (HACK_singleAddStmtListener != null) {
      HACK_singleAddStmtListener.accept(stmt);
      HACK_singleAddStmtListener = null;
    }

    soot.PatchingChain<Unit> chain = chains.peek();
    if (stmt instanceof IdentityStmt && chain.size() != 0) {
      IdentityStmt idstmt = (IdentityStmt) stmt;
      if (!(idstmt.getRightOp() instanceof CaughtExceptionRef)) {
        soot.Unit s = chain.getFirst();
        while (s instanceof IdentityStmt)
          s = chain.getSuccOf(s);

        if (s != null) {
          chain.insertBefore(stmt, s);
          return this;
        }
      }
    }

    chain.add(stmt);
    return this;
  }


  Label newLabel(ASTNode n) {
    // FXIME: Don't really have enough span-info for arbitrary labels.
    //        Would need a proper syntax tree.
    Label l = this.new Label();
    l.stmt.addTag(n.createTagSrcSpan());
    return l;
  }

  Label newLabel()
  { return this.new Label(); }

  <T extends ASTNode & Variable> CatchLabel newCatchLabel(T param) {
    //setLine(loc);
    Local local = local(param);
    return newCatchLabel(local, param);
  }
  CatchLabel newCatchLabel(Local catch_param, ASTNode loc) {
    //setLine(loc);
    return this.new CatchLabel(newIdentityStmt(catch_param, newCaughtExceptionRef(loc), loc));
  }

  Label addNewLabel(ASTNode location) {
    final Label l = newLabel(location);
    addLabel(l);
    return l;
  }

  Body addLabel(Label label) {
    assert label.hostBody == body;
    add(label.stmt);
    return this;
  }
  Body addGoTo(Label label, ASTNode location) {
    assert label.hostBody == body;
    add(newGotoStmt(label, location));
    return this;
  }


  private Local thisVar;
  Local emitThis(TypeDecl typeDecl, ASTNode location) {
    if (thisVar != null) return thisVar;

    thisVar = newLocal("this", typeDecl.sootType(), location);
    // implicit capture of enclosing instance

    add(Jimple.v().newIdentityStmt(thisVar, isStatic ? newParameterRef_noloc(typeDecl.sootType(), 0)
            : Jimple.v().newThisRef(typeDecl.sootRef())));
    return thisVar;
  }


  Body addTrap(TypeDecl type, Label bgn, Label end, CatchLabel handler)
  { return addTrap(type, bgn.stmt, end.stmt, handler); }

  Body addTrap(TypeDecl t, Stmt bgn, Stmt end, CatchLabel hndlr) {
    body.getTraps().add(Jimple.v().newTrap(t.sootClass(), bgn, end, hndlr.stmt));
    return this;
  }

  Consumer<Stmt> HACK_singleAddStmtListener = null;


  private <T extends BinopExpr>
  T mkBinOp(BiFunction<Immediate, Immediate, T> mk, Value a, Value b, ASTNode loc) {
    T expr = mk.apply(asImmediate(a), asImmediate(b));
    setSrcLoc(expr.getOp1Box(), a);
    setSrcLoc(expr.getOp2Box(), b);
    return setSrcLoc(expr, loc);
  }

  private <T extends UnopExpr>
  T mkUnOp(Function<Value, T> mk, Value a, ASTNode loc) {
    T expr = mk.apply(asImmediate(a));
    setSrcLoc(expr.getOpBox(), a);
    return setSrcLoc(expr, loc);
  }

  BinopExpr      newXorExpr (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newXorExpr  , a, b, loc); }
  BinopExpr      newUshrExpr(Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newUshrExpr , a, b, loc); }
  BinopExpr      newAddExpr (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newAddExpr  , a, b, loc); }
  BinopExpr      newSubExpr (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newSubExpr  , a, b, loc); }
  BinopExpr      newShrExpr (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newShrExpr  , a, b, loc); }
  BinopExpr      newShlExpr (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newShlExpr  , a, b, loc); }
  BinopExpr      newRemExpr (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newRemExpr  , a, b, loc); }
  BinopExpr      newMulExpr (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newMulExpr  , a, b, loc); }
  BinopExpr      newDivExpr (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newDivExpr  , a, b, loc); }
  BinopExpr      newCmplExpr(Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newCmplExpr , a, b, loc); }
  BinopExpr      newCmpgExpr(Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newCmpgExpr , a, b, loc); }
  BinopExpr      newCmpExpr (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newCmpExpr  , a, b, loc); }
  BinopExpr      newAndExpr (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newAndExpr  , a, b, loc); }
  BinopExpr      newOrExpr  (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newOrExpr   , a, b, loc); }
  ConditionExpr  newNeExpr  (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newNeExpr   , a, b, loc); }
  ConditionExpr  newLeExpr  (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newLeExpr   , a, b, loc); }
  ConditionExpr  newGeExpr  (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newGeExpr   , a, b, loc); }
  ConditionExpr  newEqExpr  (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newEqExpr   , a, b, loc); }
  ConditionExpr  newGtExpr  (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newGtExpr   , a, b, loc); }
  ConditionExpr  newLtExpr  (Value a, Value b, ASTNode loc) { return mkBinOp(Jimple.v()::newLtExpr   , a, b, loc); }

  UnopExpr       newNegExpr   (Value a, ASTNode loc) { return mkUnOp(Jimple.v()::newNegExpr   , a, loc); }
  UnopExpr       newLengthExpr(Value a, ASTNode loc) { return mkUnOp(Jimple.v()::newLengthExpr, a, loc); }

  Value          newCastExpr(Value op, Type t, ASTNode location) {
    if (op.getType() == t) return op; // HACK: last ditch attempt to ignore pointless casts

    CastExpr  expr  = Jimple.v().newCastExpr(asImmediate(op), t);
    setSrcLoc(expr.getOpBox(), op);
    return setSrcLoc(expr, location);
  }

  InstanceOfExpr newInstanceOfExpr(Value op, Type t, ASTNode location) {
    InstanceOfExpr  expr  = Jimple.v().newInstanceOfExpr(asImmediate(op), t);
    setSrcLoc(expr.getOpBox(), op);
    return setSrcLoc(expr, location);
  }

  NewExpr newNewExpr(RefType type, ASTNode location)
  { return setSrcLoc(Jimple.v().newNewExpr(type), location); }

  NewArrayExpr newNewArrayExpr(Type type, Value size, ASTNode location) {
    NewArrayExpr  expr  = Jimple.v().newNewArrayExpr(type, asImmediate(size));
    setSrcLoc(expr.getSizeBox(), size);
    return setSrcLoc(expr, location);
  }

  NewMultiArrayExpr newNewMultiArrayExpr(ArrayType type, List<? extends Value> sizes, ASTNode location) {
    NewMultiArrayExpr expr = Jimple.v().newNewMultiArrayExpr(type, asImmediate(sizes));
    for (int i = 0; i < sizes.size(); i++)
      setSrcLoc(expr.getSizeBox(i), sizes.get(i));

    return setSrcLoc(expr, location);
  }

  StaticInvokeExpr              newStaticInvokeExpr(         SootMethodRef m, List<? extends Value> args, ASTNode loc)
  { return newInvokeAddTags(Jimple.v().   newStaticInvokeExpr(   m, asImmediate(args)), args, loc); }

  SpecialInvokeExpr             newSpecialInvokeExpr(Local b, SootMethodRef m, List<? extends Value> args, ASTNode loc)
  { return newInvokeAddTags(Jimple.v().  newSpecialInvokeExpr(b, m, asImmediate(args)), args, loc); }

  VirtualInvokeExpr             newVirtualInvokeExpr(Local b, SootMethodRef m, List<? extends Value> args, ASTNode loc)
  { return newInvokeAddTags(Jimple.v().  newVirtualInvokeExpr(b, m, asImmediate(args)), args, loc); }

  InterfaceInvokeExpr           newInterfaceInvokeExpr(Local b, SootMethodRef m, List<? extends Value> args, ASTNode loc)
  { return newInvokeAddTags(Jimple.v().newInterfaceInvokeExpr(b, m, asImmediate(args)), args, loc); }

  private <T extends InvokeExpr> T newInvokeAddTags(T expr, List<? extends Value> args, ASTNode loc) {
    assert expr.getMethodRef().parameterTypes().size() == args.size();
    for (int i = 0; i < args.size(); i++)
      setSrcLoc(expr.getArgBox(i), args.get(i));

    return setSrcLoc(expr, loc);
  }

  ThrowStmt newThrowStmt(Value op, ASTNode location) {
    ThrowStmt stmt = Jimple.v().newThrowStmt(asImmediate(op));
    setSrcLoc(stmt.getOpBox(), op);
    return setSrcLoc(stmt, location);
  }

  ExitMonitorStmt newExitMonitorStmt(Immediate op, ASTNode location) {
    ExitMonitorStmt stmt  = Jimple.v().newExitMonitorStmt(asImmediate(op));
    setSrcLoc(stmt.getOpBox(), op);
    return setSrcLoc(stmt, location);
  }

  EnterMonitorStmt newEnterMonitorStmt(Value op, ASTNode location) {
    EnterMonitorStmt  stmt  = Jimple.v().newEnterMonitorStmt(asImmediate(op));
    setSrcLoc(stmt.getOpBox(), op);
    return setSrcLoc(stmt, location);
  }

  GotoStmt newGotoStmt(Label label, ASTNode location)
  { return setSrcLoc(Jimple.v().newGotoStmt(label.stmt), location); }

  ReturnVoidStmt newReturnVoidStmt_noloc()
  { return Jimple.v().newReturnVoidStmt(); }

  ReturnVoidStmt newReturnVoidStmt(ASTNode location)
  { return setSrcLoc(newReturnVoidStmt_noloc(), location); }

  ReturnStmt newReturnStmt(Value op, ASTNode location) {
    ReturnStmt  stmt  = Jimple.v().newReturnStmt(asImmediate(op));
    setSrcLoc(stmt.getOpBox(), op);
    return setSrcLoc(stmt, location);
  }

  IfStmt newIfStmt(ConditionExpr op, Unit target, ASTNode location) {
    IfStmt stmt = Jimple.v().newIfStmt(op, target);
    setSrcLoc(stmt.getConditionBox(), op);
    return setSrcLoc(stmt, location);
  }

  private IdentityStmt newIdentityStmt_noloc(Local local, IdentityRef identityRef) {
    IdentityStmt stmt = Jimple.v().newIdentityStmt(local, identityRef);
    setSrcLoc(stmt.getLeftOpBox(), local);
    setSrcLoc(stmt.getRightOpBox(), identityRef);
    return stmt;
  }

  private AssignStmt newAssignStmt_noloc(Assignable variable, Value rvalue) {
    // At least one of the two has to be an Immediate. Why? Because the Soot gods demand it.
    if (!(variable.v instanceof Immediate || rvalue instanceof Immediate))
      rvalue = asImmediate(rvalue);

    AssignStmt stmt = Jimple.v().newAssignStmt(variable.v, asRValue(rvalue).v);
    setSrcLoc(stmt.getLeftOpBox (), variable.v);
    setSrcLoc(stmt.getRightOpBox(), rvalue    );
    return stmt;
  }

  IdentityStmt newIdentityStmt(Local local, IdentityRef identityRef, ASTNode location)
  { return setSrcLoc(newIdentityStmt_noloc(local, identityRef), location); }

  AssignStmt newAssignStmt(Assignable variable, Value rvalue, ASTNode location)
  { return setSrcLoc(newAssignStmt_noloc(variable, rvalue), location); }

  AssignStmt newAssignStmt(Local variable, Value rvalue, ASTNode location)
  { return newAssignStmt(asAssignable(variable), rvalue, location); }

  AssignStmt newAssignStmt(ConcreteRef variable, Value rvalue, ASTNode location)
  { return newAssignStmt(asAssignable(variable), rvalue, location); }



  InvokeStmt newInvokeStmt(InvokeExpr op, ASTNode location) {
    InvokeStmt  stmt  = Jimple.v().newInvokeStmt(op);
    setSrcLoc(stmt.getInvokeExprBox(), op);
    return setSrcLoc(stmt, location);
  }

  TableSwitchStmt newTableSwitchStmt(Value key, int lowIndex, int highIndex, List<? extends Stmt> targets, Stmt defaultTarget, ASTNode location) {
    TableSwitchStmt   stmt  = Jimple.v().newTableSwitchStmt(asImmediate(key), lowIndex, highIndex, targets, defaultTarget);
    setSrcLoc(stmt.getKeyBox(), key);
    return setSrcLoc(stmt, location);
  }

  LookupSwitchStmt newLookupSwitchStmt(Value key, List<IntConstant> lookupValues, List<? extends Stmt> targets, Stmt defaultTarget, ASTNode location) {
    LookupSwitchStmt  stmt  = Jimple.v().newLookupSwitchStmt(asImmediate(key), lookupValues, targets, defaultTarget);
    stmt.addTag(location.createTagSrcSpan());
    setSrcLoc(stmt.getKeyBox(), key);
    return setSrcLoc(stmt, location);
  }

  StaticFieldRef newStaticFieldRef(SootFieldRef f, ASTNode location)
  { return setSrcLoc(Jimple.v().newStaticFieldRef(f), location); }

  ThisRef newThisRef(RefType t, ASTNode location)
  { return setSrcLoc(Jimple.v().newThisRef(t), location); }

  // TODO: Disable/remove once we're done debugging.
  // private Set<Integer> dbgEmittedParamRefs = new TreeSet<>();
  private ParameterRef newParameterRef_noloc(Type paramType, int number) {
    // assert !dbgEmittedParamRefs.contains(number);
    // dbgEmittedParamRefs.add(number);
    return Jimple.v().newParameterRef(paramType, number);
  }

  ParameterRef newParameterRef(Type paramType, int number, ASTNode location)
  { return setSrcLoc(newParameterRef_noloc(paramType, number), location); }

  InstanceFieldRef newInstanceFieldRef(Local base, SootFieldRef f, ASTNode location) {
    InstanceFieldRef  ref = Jimple.v().newInstanceFieldRef(base, f);
    setSrcLoc(ref.getBaseBox(), base);
    return setSrcLoc(ref, location);
  }

  CaughtExceptionRef newCaughtExceptionRef(ASTNode location)
  { return setSrcLoc(Jimple.v().newCaughtExceptionRef(), location); }

  ArrayRef newArrayRef(Local base, Value index, ASTNode location) {
    ArrayRef  ref = Jimple.v().newArrayRef(base, asImmediate(index));
    setSrcLoc(ref.getBaseBox (), base);
    setSrcLoc(ref.getIndexBox(), index);
    return setSrcLoc(ref, location);
  }

  // Either a `soot.tagkit.LineNumberTag` or a `soot.tagkit.SourceLnNamePosTag`
  // depending on how much info is available.
  private HashMap<Value, soot.tagkit.Tag> valueSrcLocTags = new HashMap<>();
  private soot.tagkit.Tag getSrcLoc(ASTNode l) { return l.createTagSrcSpan(); }
  private soot.tagkit.Tag getSrcLoc(Value   l) {
    assert l != null;
    return valueSrcLocTags.get(l);
  }

  <T extends Value           > T setSrcLoc(T v, ASTNode    location) {
    if (v == null                     ) return null;
    //if (valueSrcLocTags.containsKey(v)) return v;

    valueSrcLocTags.put(v, getSrcLoc(location));
    return v;
  }

  <T extends soot.tagkit.Host> T setSrcLoc(T v, ASTNode    location) {
    v.addTag(getSrcLoc(location));
    return v;
  }

  <T extends Value           > T setSrcLoc(T v, Value location) {
    if (getSrcLoc(location) != null)
      valueSrcLocTags.put(v, getSrcLoc(location));

    return v;
  }

  <T extends soot.tagkit.Host> T setSrcLoc(T v, Value location) {
    if (getSrcLoc(location) != null)
      v.addTag(getSrcLoc(location));

    return v;
  }
}
