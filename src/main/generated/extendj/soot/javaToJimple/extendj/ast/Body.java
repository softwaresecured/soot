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
 * @ast class
 * @aspect EmitJimple
 * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:425
 */
 class Body extends java.lang.Object {
  
    private int nextTempIndex = 0;

  
    private Stack<PatchingChain<Unit>> chains        = new Stack<>();

  

    TypeDecl typeDecl;

  
    soot.jimple.JimpleBody body;

  

    public Body(TypeDecl typeDecl, soot.jimple.JimpleBody body, ASTNode container) {
      this.typeDecl = typeDecl;
      this.body     = body;

      chains.push(body.getUnits());
      //setLine(container);

      // non-statics immediately puke out a `this` and (implicitly) stash it for later use
      if (!body.getMethod().isStatic())
        emitThis(typeDecl, container);
    }

  

    private Local newTemp_noloc(soot.Type type) {
      Local local = Jimple.v().newLocal("temp$" + nextTempIndex++, type);
      body.getLocals().add(local);
      return local;
    }

  

    public Local newTemp(soot.Type type, ASTNode location)
    { return setSrcLoc(newTemp_noloc(type), location); }

  

    public Local newTemp(soot.Value v) {
      if (v == NullConstant.v())
        throw new UnsupportedOperationException("Cannot create a temporary local for null literal");

      Local             local = newTemp_noloc(v.getType());
      soot.jimple.Stmt  stmt;
      if (v instanceof soot.jimple.ParameterRef)
        stmt = newIdentityStmt_noloc(local, v);
      else
        stmt = newAssignStmt_noloc(local, v);

      add(setSrcLoc(stmt, v));
      return setSrcLoc(local, v);
    }

  

    public Local newLocal(String name, soot.Type type, ASTNode location) {
      Local local = Jimple.v().newLocal(name, type);
      body.getLocals().add(setSrcLoc(local, location));
      if (thisName == null && name.equals("this"))
        thisName = local;

      return local;
    }

  

    public Body add(soot.jimple.Stmt stmt) {
      if(list != null) {
        list.add(stmt);
        list = null;
      }

      soot.PatchingChain<Unit> chain = chains.peek();
      if (stmt instanceof IdentityStmt && chain.size() != 0) {
        IdentityStmt idstmt = (IdentityStmt) stmt;
        if(!(idstmt.getRightOp() instanceof CaughtExceptionRef)) {
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

  

    public soot.jimple.Stmt newLabel()
    { return soot.jimple.Jimple.v().newNopStmt(); }

  

    public Body addLabel(soot.jimple.Stmt label) {
      add(label);
      return this;
    }

  

    private Local thisName;

  
    public soot.Local emitThis(TypeDecl typeDecl, ASTNode location) {
      if (thisName != null) return thisName;

      thisName = newLocal("this", typeDecl.getSootType(), location);
      soot.jimple.Stmt stmt;
      if(body.getMethod().isStatic())
        // implicit capture of enclosing instance
        stmt = Jimple.v().newIdentityStmt(thisName, newParameterRef_noloc(typeDecl.getSootType(), 0));
      else
        stmt = Jimple.v().newIdentityStmt(thisName, Jimple.v().newThisRef(typeDecl.sootRef()));

      add(stmt);
      return thisName;
    }

  

    public Body addTrap(TypeDecl type, soot.jimple.Stmt firstStmt, soot.jimple.Stmt lastStmt, soot.jimple.Stmt handler) {
      body.getTraps().add(Jimple.v().newTrap(type.getSootClassDecl(), firstStmt, lastStmt, handler));
      return this;
    }

  

    public soot.jimple.Stmt previousStmt()
    { return (soot.jimple.Stmt)chains.lastElement().getLast(); }

  

    public void addNextStmt(java.util.ArrayList<soot.jimple.Stmt> list)
    { this.list = list; }

  

    java.util.ArrayList<soot.jimple.Stmt> list = null;

  

    public soot.jimple.BinopExpr newXorExpr (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newXorExpr  (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newUshrExpr(soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newUshrExpr (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newSubExpr (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newSubExpr  (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newShrExpr (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newShrExpr  (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newShlExpr (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newShlExpr  (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newRemExpr (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newRemExpr  (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newOrExpr  (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newOrExpr   (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newNeExpr  (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newNeExpr   (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newMulExpr (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newMulExpr  (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newLeExpr  (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newLeExpr   (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newGeExpr  (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newGeExpr   (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newEqExpr  (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newEqExpr   (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newDivExpr (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newDivExpr  (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newCmplExpr(soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newCmplExpr (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newCmpgExpr(soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newCmpgExpr (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newCmpExpr (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newCmpExpr  (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newGtExpr  (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newGtExpr   (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newLtExpr  (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newLtExpr   (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newAddExpr (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newAddExpr  (op1, op2), location, op1, op2); }

  
    public soot.jimple.BinopExpr newAndExpr (soot.Value op1, soot.Value op2, ASTNode location) { return updateTags(Jimple.v().newAndExpr  (op1, op2), location, op1, op2); }

  

    public soot.jimple.UnopExpr newNegExpr   (soot.Value op, ASTNode location) { return updateTags(Jimple.v().newNegExpr   (op), location, op); }

  
    public soot.jimple.UnopExpr newLengthExpr(soot.Value op, ASTNode location) { return updateTags(Jimple.v().newLengthExpr(op), location, op); }

  

    public soot.jimple.CastExpr newCastExpr(Value op, Type t, ASTNode location) {
      soot.jimple.CastExpr expr = Jimple.v().newCastExpr(op, t);
      setSrcLoc(expr.getOpBox(), op);
      return setSrcLoc(expr, location);
    }

  

    public soot.jimple.InstanceOfExpr newInstanceOfExpr(Value op, Type t, ASTNode location) {
      soot.jimple.InstanceOfExpr expr = Jimple.v().newInstanceOfExpr(op, t);
      setSrcLoc(expr.getOpBox(), op);
      return setSrcLoc(expr, location);
    }

  

    public soot.jimple.NewExpr newNewExpr(RefType type, ASTNode location)
    { return setSrcLoc(Jimple.v().newNewExpr(type), location); }

  

    public soot.jimple.NewArrayExpr newNewArrayExpr(Type type, Value size, ASTNode location) {
      soot.jimple.NewArrayExpr expr = Jimple.v().newNewArrayExpr(type, size);
      setSrcLoc(expr.getSizeBox(), size);
      return setSrcLoc(expr, location);
    }

  

    public soot.jimple.NewMultiArrayExpr newNewMultiArrayExpr(ArrayType type, java.util.List<? extends Value> sizes, ASTNode location) {
      soot.jimple.NewMultiArrayExpr expr = Jimple.v().newNewMultiArrayExpr(type, sizes);
      for (int i = 0; i < sizes.size(); i++)
        setSrcLoc(expr.getSizeBox(i), sizes.get(i));

      return setSrcLoc(expr, location);
    }

  

    public soot.jimple.StaticInvokeExpr newStaticInvokeExpr(SootMethodRef method, java.util.List<? extends Value> args, ASTNode location)
    { return newInvokeAddTags(Jimple.v().newStaticInvokeExpr(       method, args), args, location); }

  

    public soot.jimple.SpecialInvokeExpr newSpecialInvokeExpr(Local base, SootMethodRef method, java.util.List<? extends Value> args, ASTNode location)
    { return newInvokeAddTags(Jimple.v().newSpecialInvokeExpr(base, method, args), args, location); }

  

    public soot.jimple.VirtualInvokeExpr newVirtualInvokeExpr(Local base, SootMethodRef method, java.util.List<? extends Value> args, ASTNode location)
    { return newInvokeAddTags(Jimple.v().newVirtualInvokeExpr(base, method, args), args, location); }

  

    public soot.jimple.InterfaceInvokeExpr newInterfaceInvokeExpr(Local base, SootMethodRef method, java.util.List<? extends Value> args, ASTNode location)
    { return newInvokeAddTags(Jimple.v().newInterfaceInvokeExpr(base, method, args), args, location); }

  

    private <T extends soot.jimple.InvokeExpr> T newInvokeAddTags(T expr, java.util.List<? extends Value> args, ASTNode location) {
      assert expr.getMethodRef().parameterTypes().size() == args.size();
      for (int i = 0; i < args.size(); i++)
        setSrcLoc(expr.getArgBox(i), args.get(i));

      return setSrcLoc(expr, location);
    }

  

    public soot.jimple.ThrowStmt newThrowStmt(Value op, ASTNode location) {
      soot.jimple.ThrowStmt stmt = Jimple.v().newThrowStmt(op);
      setSrcLoc(stmt.getOpBox(), op);
      return setSrcLoc(stmt, location);
    }

  

    public soot.jimple.ExitMonitorStmt newExitMonitorStmt(Value op, ASTNode location) {
      soot.jimple.ExitMonitorStmt stmt = Jimple.v().newExitMonitorStmt(op);
      setSrcLoc(stmt.getOpBox(), op);
      return setSrcLoc(stmt, location);
    }

  

    public soot.jimple.EnterMonitorStmt newEnterMonitorStmt(Value op, ASTNode location) {
      soot.jimple.EnterMonitorStmt stmt = Jimple.v().newEnterMonitorStmt(op);
      setSrcLoc(stmt.getOpBox(), op);
      return setSrcLoc(stmt, location);
    }

  

    public soot.jimple.GotoStmt newGotoStmt(Unit target, ASTNode location)
    { return setSrcLoc(Jimple.v().newGotoStmt(target), location); }

  

    public soot.jimple.ReturnVoidStmt newReturnVoidStmt_noloc()
    { return Jimple.v().newReturnVoidStmt(); }

  

    public soot.jimple.ReturnVoidStmt newReturnVoidStmt(ASTNode location)
    { return setSrcLoc(newReturnVoidStmt_noloc(), location); }

  

    public soot.jimple.ReturnStmt newReturnStmt(Value op, ASTNode location) {
      soot.jimple.ReturnStmt stmt = Jimple.v().newReturnStmt(op);
      setSrcLoc(stmt.getOpBox(), op);
      return setSrcLoc(stmt, location);
    }

  

    public soot.jimple.IfStmt newIfStmt(Value op, Unit target, ASTNode location) {
      soot.jimple.IfStmt stmt = Jimple.v().newIfStmt(op, target);
      setSrcLoc(stmt.getConditionBox(), op);
      return setSrcLoc(stmt, location);
    }

  

    private soot.jimple.IdentityStmt newIdentityStmt_noloc(Value local, Value identityRef) {
      soot.jimple.IdentityStmt stmt = Jimple.v().newIdentityStmt(local, identityRef);
      setSrcLoc(stmt.getLeftOpBox(), local);
      setSrcLoc(stmt.getRightOpBox(), identityRef);
      return stmt;
    }

  

    private soot.jimple.AssignStmt newAssignStmt_noloc(Value variable, Value rvalue) {
        soot.jimple.AssignStmt stmt = Jimple.v().newAssignStmt(variable, rvalue);
        setSrcLoc(stmt.getLeftOpBox (), variable);
        setSrcLoc(stmt.getRightOpBox(), rvalue  );
        return stmt;
    }

  

    public soot.jimple.IdentityStmt newIdentityStmt(Value local, Value identityRef, ASTNode location)
    { return setSrcLoc(newIdentityStmt_noloc(local, identityRef), location); }

  

    public soot.jimple.AssignStmt newAssignStmt(Value variable, Value rvalue, ASTNode location)
    { return setSrcLoc(newAssignStmt_noloc(variable, rvalue), location); }

  

    public soot.jimple.InvokeStmt newInvokeStmt(Value op, ASTNode location) {
      soot.jimple.InvokeStmt stmt = Jimple.v().newInvokeStmt(op);
      setSrcLoc(stmt.getInvokeExprBox(), op);
      return setSrcLoc(stmt, location);
    }

  

    public soot.jimple.TableSwitchStmt newTableSwitchStmt(Value key, int lowIndex, int highIndex, java.util.List<? extends Unit> targets, Unit defaultTarget, ASTNode location) {
      soot.jimple.TableSwitchStmt stmt = Jimple.v().newTableSwitchStmt(key, lowIndex, highIndex, targets, defaultTarget);
      setSrcLoc(stmt.getKeyBox(), key);
      return setSrcLoc(stmt, location);
    }

  

    public soot.jimple.LookupSwitchStmt newLookupSwitchStmt(Value key, java.util.List<soot.jimple.IntConstant> lookupValues, java.util.List<? extends Unit> targets, Unit defaultTarget, ASTNode location) {
      soot.jimple.LookupSwitchStmt stmt = Jimple.v().newLookupSwitchStmt(key, lookupValues, targets, defaultTarget);
      stmt.addTag(location.createTagSrcSpan());
      setSrcLoc(stmt.getKeyBox(), key);
      return setSrcLoc(stmt, location);
    }

  

    public soot.jimple.StaticFieldRef newStaticFieldRef(SootFieldRef f, ASTNode location)
    { return setSrcLoc(Jimple.v().newStaticFieldRef(f), location); }

  

    public soot.jimple.ThisRef newThisRef(RefType t, ASTNode location)
    { return setSrcLoc(Jimple.v().newThisRef(t), location); }

  

    // TODO: Disable/remove once we're done debugging.
    // private Set<Integer> dbgEmittedParamRefs = new TreeSet<>();
    private soot.jimple.ParameterRef newParameterRef_noloc(Type paramType, int number) {
      // assert !dbgEmittedParamRefs.contains(number);
      // dbgEmittedParamRefs.add(number);
      return Jimple.v().newParameterRef(paramType, number);
    }

  

    public soot.jimple.ParameterRef newParameterRef(Type paramType, int number, ASTNode location)
    { return setSrcLoc(newParameterRef_noloc(paramType, number), location); }

  

    public soot.jimple.InstanceFieldRef newInstanceFieldRef(Value base, SootFieldRef f, ASTNode location) {
      soot.jimple.InstanceFieldRef ref = Jimple.v().newInstanceFieldRef(base, f);
      setSrcLoc(ref.getBaseBox(), base);
      return setSrcLoc(ref, location);
    }

  

    public soot.jimple.CaughtExceptionRef newCaughtExceptionRef(ASTNode location)
    { return setSrcLoc(Jimple.v().newCaughtExceptionRef(), location); }

  

    public soot.jimple.ArrayRef newArrayRef(Value base, Value index, ASTNode location) {
      soot.jimple.ArrayRef ref = Jimple.v().newArrayRef(base, index);
      setSrcLoc(ref.getBaseBox (), base);
      setSrcLoc(ref.getIndexBox(), index);
      return setSrcLoc(ref, location);
    }

  

    private soot.jimple.BinopExpr updateTags(soot.jimple.BinopExpr expr, ASTNode location, soot.Value op1, soot.Value op2) {
      setSrcLoc(expr.getOp1Box(), op1);
      setSrcLoc(expr.getOp2Box(), op2);
      return setSrcLoc(expr, location);
    }

  

    private soot.jimple.UnopExpr updateTags(soot.jimple.UnopExpr expr, ASTNode location, soot.Value op) {
      setSrcLoc(expr.getOpBox(), op);
      return setSrcLoc(expr, location);
    }

  

    // Either a `soot.tagkit.LineNumberTag` or a `soot.tagkit.SourceLnNamePosTag`
    // depending on how much info is available.
    private java.util.HashMap<soot.Value, soot.tagkit.Tag> valueSrcLocTags = new java.util.HashMap<>();

  
    private soot.tagkit.Tag getSrcLoc(ASTNode    l) { return l.createTagSrcSpan(); }

  
    private soot.tagkit.Tag getSrcLoc(soot.Value l) {
      assert l != null;
      return valueSrcLocTags.get(l);
    }

  

    public <T extends soot.Value      > T setSrcLoc(T v, ASTNode    location) {
      if (v == null                     ) return null;
      //if (valueSrcLocTags.containsKey(v)) return v;

      valueSrcLocTags.put(v, getSrcLoc(location));
      return v;
    }

  

    public <T extends soot.tagkit.Host> T setSrcLoc(T v, ASTNode    location) {
      v.addTag(getSrcLoc(location));
      return v;
    }

  

    public <T extends soot.Value      > T setSrcLoc(T v, soot.Value location) {
      if (getSrcLoc(location) != null)
        valueSrcLocTags.put(v, getSrcLoc(location));

      return v;
    }

  

    public <T extends soot.tagkit.Host> T setSrcLoc(T v, soot.Value location) {
      if (getSrcLoc(location) != null)
        v.addTag(getSrcLoc(location));

      return v;
    }


}
