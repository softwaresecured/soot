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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:296
 * @production SwitchStmt : {@link BranchTargetStmt} ::= <span class="component">{@link Expr}</span> <span class="component">{@link Block}</span>;

 */
public class SwitchStmt extends BranchTargetStmt implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:573
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("switch (");
    out.print(getExpr());
    out.print(") ");
    out.print(getBlock());
  }
  /**
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:82
   */
  protected soot.jimple.SwitchStmt mkIntSwitchStmt(Body b, soot.Immediate expr, soot.jimple.Stmt defStmt
                            ,SortedMap<Integer, soot.jimple.Stmt> key2label) {
    final int  low              = key2label.isEmpty() ? 0 : key2label.firstKey().intValue();
    final int  high             = key2label.isEmpty() ? 0 : key2label. lastKey().intValue();
    final long tableSwitchSize  = 8L + (high - low + 1L) * 4L;
    final long lookupSwitchSize = 4L + key2label.size()  * 8L;

    if (tableSwitchSize < lookupSwitchSize) {
      final ArrayList<soot.jimple.Stmt> targets = new ArrayList<>();
      for (int i = low; i <= high; i++) {
        final soot.jimple.Stmt target = key2label.get(i);
        targets.add(target != null ? target : defStmt);
      }

      return b.newTableSwitchStmt(expr, (int)low, (int)high, targets, defStmt, this);
    }

    final ArrayList<soot.jimple.Stmt> targets = new ArrayList<>();
    final ArrayList<IntConstant     > values  = new ArrayList<>();
    for (Map.Entry<Integer, soot.jimple.Stmt> kv : key2label.entrySet()) {
      targets.add(kv.getValue());
      values .add(soot.jimple.IntConstant.v(kv.getKey()));
    }

    return b.newLookupSwitchStmt(expr, values, targets, defStmt, this);
  }
  /**
   * @aspect StringsInSwitch
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/StringsInSwitch.jrag:176
   */
  private TreeMap<Integer, CaseGroup> stringCaseGroups() {
    int                         caseId = 1;
    TreeMap<Integer, CaseGroup> groups = new TreeMap<Integer, CaseGroup>();
    for (Stmt stmt : getBlock().getStmts()) {
      if (stmt instanceof ConstCase) {
        CaseLabel caseLabel = new CaseLabel((ConstCase)stmt, caseId++);
        int       key       = caseLabel.value().hashCode();

        CaseGroup group = groups.get(key);
        if (group == null) {
          group = new CaseGroup(key);
          groups.put(key, group);
        }

        group.addCase(caseLabel);
      }
    }

    return groups;
  }
  /**
   * @aspect StringsInSwitch
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/StringsInSwitch.jrag:197
   */
  private soot.jimple.InvokeExpr stringMkInvokeEquals(Body body, soot.Local a, soot.Immediate b, ASTNode location) {
    TypeDecl              objType     = lookupType("java.lang", "Object");
    MethodDecl            methDecl    = equalsMethod();
    ArrayList<soot.Type>  paramTypes  = new ArrayList<>();
    ArrayList<soot.Value> paramVals   = new ArrayList<>();
    paramTypes.add(Scene.v().getObjectType());
    paramVals.add(b);
    assert methDecl.getNumParameter() == 1;

    soot.SootMethodRef equalsRef = Scene.v().makeMethodRef(
      objType.getSootClassDecl(),
      methDecl.name(),
      paramTypes,
      methDecl.type().getSootType(),
      methDecl.isStatic()
    );

    return body.newVirtualInvokeExpr(a, equalsRef, paramVals, location);
  }
  /**
   * @aspect StringsInSwitch
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/StringsInSwitch.jrag:217
   */
  private soot.jimple.InvokeExpr stringMkInvokeHashCode(Body b, soot.Local value) {
    // Don't do any optimisations in the initial code-gen. Leave that to soot.
    // if (getExpr().isConstant()) {
    //   int hashCode = getExpr().constant().stringValue().hashCode();
    //   return soot.jimple.IntConstant.v(hashCode);
    // }

    TypeDecl    objType   = lookupType("java.lang", "Object");
    MethodDecl  methDecl  = hashCodeMethod();
    assert methDecl.getNumParameter() == 0;

    soot.SootMethodRef methRef = Scene.v().makeMethodRef(
      objType.getSootClassDecl(),
      methDecl.name(),
      new ArrayList(),
      methDecl.type().getSootType(),
      methDecl.isStatic()
    );

    return b.newVirtualInvokeExpr(value, methRef, new ArrayList(), this);
  }
  /**
   * Generate invocation of method
   * {@code java.lang.Object.hashCode()}.
   * @aspect StringsInSwitch
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/StringsInSwitch.jrag:243
   */
  private MethodDecl hashCodeMethod() {
    TypeDecl objectType = lookupType("java.lang", "Object");
    if (objectType.isUnknown()) {
      throw new Error("Could not find java.lang.Object");
    }
    for (MethodDecl method :
        (Collection<MethodDecl>) objectType.memberMethods("hashCode")) {
      if (method.getNumParameter() == 0) {
        return method;
      }
    }
    throw new Error("Could not find java.lang.Object.hashCode()");
  }
  /**
   * Generate invocation of method
   * {@code java.lang.Object.equals(java.lang.Object)}.
   * @aspect StringsInSwitch
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/StringsInSwitch.jrag:261
   */
  private MethodDecl equalsMethod() {
    TypeDecl objectType = lookupType("java.lang", "Object");
    if (objectType.isUnknown()) {
      throw new Error("Could not find java.lang.Object");
    }
    for (MethodDecl method :
        (Collection<MethodDecl>) objectType.memberMethods("equals")) {
      if (method.getNumParameter() == 1 &&
          method.getParameter(0).getTypeAccess().type() == objectType) {
        return method;
      }
    }
    throw new Error("Could not find java.lang.Object.equals()");
  }
  /**
   * @declaredat ASTNode:1
   */
  public SwitchStmt() {
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
  }
  /**
   * @declaredat ASTNode:13
   */
  public SwitchStmt(Expr p0, Block p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:18
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:24
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:28
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    assignedAfter_Variable_reset();
    unassignedAfter_Variable_reset();
    unassignedAfterLastStmt_Variable_reset();
    caseMap_reset();
    canCompleteNormally_reset();
    enumIndexExpr_reset();
    enumIndices_reset();
    defaultCase_reset();
    end_label_reset();
    typeInt_reset();
    typeLong_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:43
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:47
   */
  public SwitchStmt clone() throws CloneNotSupportedException {
    SwitchStmt node = (SwitchStmt) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:52
   */
  public SwitchStmt copy() {
    try {
      SwitchStmt node = (SwitchStmt) clone();
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
   * @declaredat ASTNode:71
   */
  @Deprecated
  public SwitchStmt fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:81
   */
  public SwitchStmt treeCopyNoTransform() {
    SwitchStmt tree = (SwitchStmt) copy();
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
   * @declaredat ASTNode:101
   */
  public SwitchStmt treeCopy() {
    SwitchStmt tree = (SwitchStmt) copy();
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
   * @declaredat ASTNode:115
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Expr child.
   * @param node The new node to replace the Expr child.
   * @apilevel high-level
   */
  public void setExpr(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Expr child.
   * @return The current node used as the Expr child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Expr")
  public Expr getExpr() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the Expr child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Expr child.
   * @apilevel low-level
   */
  public Expr getExprNoTransform() {
    return (Expr) getChildNoTransform(0);
  }
  /**
   * Replaces the Block child.
   * @param node The new node to replace the Block child.
   * @apilevel high-level
   */
  public void setBlock(Block node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the Block child.
   * @return The current node used as the Block child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Block")
  public Block getBlock() {
    return (Block) getChild(1);
  }
  /**
   * Retrieves the Block child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Block child.
   * @apilevel low-level
   */
  public Block getBlockNoTransform() {
    return (Block) getChildNoTransform(1);
  }
  /**
   * @aspect AutoBoxingCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/AutoBoxingCodegen.jrag:144
   */
   
  public void refined_AutoBoxingCodegen_SwitchStmt_jimplify2(Body b) {
    soot.jimple.Stmt cond_label = newLabel();

    //b.setLine(this);
    b.add(b.newGotoStmt(cond_label, this));
    getBlock().jimplify2(b);
    if(canCompleteNormally()) {
      //b.setLine(this);
      b.add(b.newGotoStmt(end_label(), this));
    }
    b.addLabel(cond_label);

    soot.Value      exprEvalBase = getExpr().eval(b);
    if ((!getExpr().type().isEnumDecl()) && getExpr().type().isReferenceType())
      exprEvalBase = ((ReferenceType)getExpr().type()).emitUnboxingOperation(b, exprEvalBase, getExpr());

    final soot.Immediate    expr    = asImmediate(b, exprEvalBase);
    final soot.jimple.Stmt  defStmt = defaultCase() != null ? defaultCase().label() : end_label();

    final TreeMap<Integer, soot.jimple.Stmt> caseMap = new TreeMap<>();
    if (getExpr().type().isEnumDecl())
      for (ConstCase cc : constCases())
        caseMap.put(enumIndices().get((EnumConstant) cc.getValue().varDecl()), cc.label());
    else
      for (ConstCase cc : constCases())
        caseMap.put(cc.getValue().constant().intValue(), cc.label());

    //b.setLine(this);
    b.add(mkIntSwitchStmt(b, expr, defStmt, caseMap));

    b.addLabel(end_label());
  }
  /**
   * Two implicit switch statements are generated for String typed switch statements.
   * The first is off the hash of the expr and returns the 'case id'.
   * The second is off the 'case id' and jumps to the label for that case.
   * ```scala
   * caseId = expr.hashCode() match {
   * default => <goto actual default>
   * case <hashCode> => expr match {
   * case <case literal i  > => <case-id i  >
   * case <case literal i+1> => <case-id i+1>
   * ...
   * }
   * ...
   * }
   * caseId match {
   * case <case-id> => <goto label for this case-id>
   * ...
   * }
   * ```
   * @aspect StringsInSwitch
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/StringsInSwitch.jrag:99
   */
   
  public void jimplify2(Body b) {
    if (!getExpr().type().isString()) {
      refined_AutoBoxingCodegen_SwitchStmt_jimplify2(b);
      return;
    }

    soot.jimple.Stmt lblMainCondition   = newLabel();
    soot.jimple.Stmt lblSecondarySwitch = newLabel();

    //b.setLine(this);
    b.add(b.newGotoStmt(lblMainCondition, this));

    getBlock().jimplify2(b);
    if(canCompleteNormally()) {
      //b.setLine(this);
      b.add(b.newGotoStmt(end_label(), this));
    }

    b.addLabel(lblMainCondition);

    soot.jimple.Stmt  defStmt   = defaultCase() != null ? defaultCase().label() : end_label();
    soot.Local        caseIdVar = b.newTemp(soot.jimple.IntConstant.v(0));
    soot.Local        condExpr  = b.newTemp(getExpr().eval(b));
    soot.Local        condHash  = b.newTemp(stringMkInvokeHashCode(b, condExpr));

    // prep mappings for insertion
    TreeMap<Integer   , CaseGroup>          groups            = stringCaseGroups();
    HashMap<CaseLabel , soot.jimple.IfStmt> case2ifStmt       = new HashMap<>();
    TreeMap<Integer   , soot.jimple.Stmt>   caseId2caseLabel  = new TreeMap<>();
    TreeMap<Integer   , soot.jimple.Stmt>   hash2groupLabel   = new TreeMap<>();
    // mk group dispatch statements (but don't add them yet)
    for (CaseGroup group : groups.values()) {
      for (CaseLabel case_ : group.cases) {
        caseId2caseLabel.put(case_.caseId, case_.cc.label());

        soot.jimple.AssignStmt  setCaseId =
          b.newAssignStmt(caseIdVar, soot.jimple.IntConstant.v(case_.caseId), case_.cc);
        soot.Immediate          caseVal   = soot.jimple.StringConstant.v(case_.value());
        soot.jimple.InvokeExpr  eqExpr    = stringMkInvokeEquals(b, condExpr, caseVal, case_.cc);
        soot.jimple.IfStmt      ifStmt    = b.newIfStmt(eqExpr, setCaseId, case_.cc);
        case2ifStmt.put(case_, ifStmt);
      }

      if (!group.cases.isEmpty())
        hash2groupLabel.put(group.hashCode, case2ifStmt.get(group.cases.get(0)));
    }

    // insert primary dispatch switch
    //b.setLine(this);
    b.add(mkIntSwitchStmt(b, condHash, defStmt, hash2groupLabel));

    // add the group dispatch bodies
    // phase 1, the `equals` checkers
    for (CaseGroup group : groups.values()) {
      for (CaseLabel case_ : group.cases)
        b.add(case2ifStmt.get(case_));

      b.add(b.newGotoStmt(defStmt, this));
    }

    for (CaseGroup group : groups.values())
    for (CaseLabel case_ : group.cases) {
      soot.jimple.IfStmt if_ = case2ifStmt.get(case_);
      b .add(if_.getTarget()                            )
        .add(b.newGotoStmt(lblSecondarySwitch, case_.cc));
    }

    //b.setLine(this);
    b.add(lblSecondarySwitch);
    b.add(mkIntSwitchStmt(b, caseIdVar, defStmt, caseId2caseLabel));

    b.add(end_label());
  }
  /**
   * @return <code>true</code> if this statement is a potential
   * branch target of the given branch statement.
   * @attribute syn
   * @aspect BranchTarget
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:215
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="BranchTarget", declaredAt="/home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:215")
  public boolean potentialTargetOf(Stmt branch) {
    boolean potentialTargetOf_Stmt_value = branch.canBranchTo(this);
    return potentialTargetOf_Stmt_value;
  }
  /** @apilevel internal */
  private void assignedAfter_Variable_reset() {
    assignedAfter_Variable_values = null;
  }
  protected java.util.Map assignedAfter_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:264")
  public boolean assignedAfter(Variable v) {
    Object _parameters = v;
    if (assignedAfter_Variable_values == null) assignedAfter_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (assignedAfter_Variable_values.containsKey(_parameters)) {
      Object _cache = assignedAfter_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      assignedAfter_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_assignedAfter_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_assignedAfter_Variable_value = assignedAfter_compute(v);
        if (new_assignedAfter_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_assignedAfter_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      assignedAfter_Variable_values.put(_parameters, new_assignedAfter_Variable_value);

      state.leaveCircle();
      return new_assignedAfter_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_assignedAfter_Variable_value = assignedAfter_compute(v);
      if (new_assignedAfter_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_assignedAfter_Variable_value;
      }
      return new_assignedAfter_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /** @apilevel internal */
  private boolean assignedAfter_compute(Variable v) {
      if (!(!noDefaultLabel() || getExpr().assignedAfter(v))) {
        return false;
      }
      if (!(!switchLabelEndsBlock() || getExpr().assignedAfter(v))) {
        return false;
      }
      if (!assignedAfterLastStmt(v)) {
        return false;
      }
      for (BreakStmt stmt : targetBreaks()) {
        if (!stmt.assignedAfterReachedFinallyBlocks(v)) {
          return false;
        }
      }
      return true;
    }
  /**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:708
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteAssignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:708")
  public boolean assignedAfterLastStmt(Variable v) {
    boolean assignedAfterLastStmt_Variable_value = getBlock().assignedAfter(v);
    return assignedAfterLastStmt_Variable_value;
  }
  /** @apilevel internal */
  private void unassignedAfter_Variable_reset() {
    unassignedAfter_Variable_values = null;
  }
  protected java.util.Map unassignedAfter_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:895")
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
        new_unassignedAfter_Variable_value = unassignedAfter_compute(v);
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
      boolean new_unassignedAfter_Variable_value = unassignedAfter_compute(v);
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
  private boolean unassignedAfter_compute(Variable v) {
      if (!(!noDefaultLabel() || getExpr().unassignedAfter(v))) {
        return false;
      }
      if (!(!switchLabelEndsBlock() || getExpr().unassignedAfter(v))) {
        return false;
      }
      if (!unassignedAfterLastStmt(v)) {
        return false;
      }
      for (BreakStmt stmt : targetBreaks()) {
        if (!stmt.unassignedAfterReachedFinallyBlocks(v)) {
          return false;
        }
      }
      return true;
    }
  /** @apilevel internal */
  private void unassignedAfterLastStmt_Variable_reset() {
    unassignedAfterLastStmt_Variable_values = null;
  }
  protected java.util.Map unassignedAfterLastStmt_Variable_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1378")
  public boolean unassignedAfterLastStmt(Variable v) {
    Object _parameters = v;
    if (unassignedAfterLastStmt_Variable_values == null) unassignedAfterLastStmt_Variable_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (unassignedAfterLastStmt_Variable_values.containsKey(_parameters)) {
      Object _cache = unassignedAfterLastStmt_Variable_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (Boolean) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      unassignedAfterLastStmt_Variable_values.put(_parameters, _value);
      _value.value = true;
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      boolean new_unassignedAfterLastStmt_Variable_value;
      do {
        _value.cycle = state.nextCycle();
        new_unassignedAfterLastStmt_Variable_value = getBlock().unassignedAfter(v);
        if (new_unassignedAfterLastStmt_Variable_value != ((Boolean)_value.value)) {
          state.setChangeInCycle();
          _value.value = new_unassignedAfterLastStmt_Variable_value;
        }
      } while (state.testAndClearChangeInCycle());
      unassignedAfterLastStmt_Variable_values.put(_parameters, new_unassignedAfterLastStmt_Variable_value);

      state.leaveCircle();
      return new_unassignedAfterLastStmt_Variable_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      boolean new_unassignedAfterLastStmt_Variable_value = getBlock().unassignedAfter(v);
      if (new_unassignedAfterLastStmt_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_unassignedAfterLastStmt_Variable_value;
      }
      return new_unassignedAfterLastStmt_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * @attribute syn
   * @aspect DefiniteUnassignment
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1381
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="DefiniteUnassignment", declaredAt="/home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1381")
  public boolean switchLabelEndsBlock() {
    boolean switchLabelEndsBlock_value = getBlock().getNumStmt() > 0
          && getBlock().getStmt(getBlock().getNumStmt()-1) instanceof ConstCase;
    return switchLabelEndsBlock_value;
  }
  /** @apilevel internal */
  private void caseMap_reset() {
    caseMap_computed = null;
    caseMap_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle caseMap_computed = null;

  /** @apilevel internal */
  protected Map<Object, Case> caseMap_value;

  /**
   * Maps constant values to case labels.
   * 
   * <p>This is used to accelerate duplicate statement label checking.
   * @attribute syn
   * @aspect NameCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:637
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:637")
  public Map<Object, Case> caseMap() {
    ASTNode$State state = state();
    if (caseMap_computed == ASTNode$State.NON_CYCLE || caseMap_computed == state().cycle()) {
      return caseMap_value;
    }
    caseMap_value = caseMap_compute();
    if (state().inCircle()) {
      caseMap_computed = state().cycle();
    
    } else {
      caseMap_computed = ASTNode$State.NON_CYCLE;
    
    }
    return caseMap_value;
  }
  /** @apilevel internal */
  private Map<Object, Case> caseMap_compute() {
      Map<Object, Case> map = new HashMap<Object, Case>();
      for (Stmt stmt : getBlock().getStmtList()) {
        if (stmt instanceof Case) {
          if (stmt instanceof ConstCase) {
            ConstCase cc = (ConstCase) stmt;
            if (cc.getValue().isConstant()) {
              if (cc.getValue().type().assignableToInt()) {
                Constant v = cc.getValue().constant();
                if (!map.containsKey(v.intValue())) {
                  map.put(v.intValue(), cc);
                }
              }
            }
          }
        }
      }
      return map;
    }
  /**
   * @attribute syn
   * @aspect TypeCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:459
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:459")
  public Collection<Problem> typeProblems() {
    {
        TypeDecl type = getExpr().type();
        if ((!type.isIntegralType() || type.isLong()) && !type.isEnumDecl() && !type.isString()) {
          return Collections.singletonList(
              error("Switch expression must be of type char, byte, short, int, enum, or string"));
        }
        return Collections.emptyList();
      }
  }
  /**
   * @attribute syn
   * @aspect UnreachableStatements
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:96
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="UnreachableStatements", declaredAt="/home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:96")
  public boolean lastStmtCanCompleteNormally() {
    boolean lastStmtCanCompleteNormally_value = getBlock().canCompleteNormally();
    return lastStmtCanCompleteNormally_value;
  }
  /**
   * @attribute syn
   * @aspect UnreachableStatements
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:98
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="UnreachableStatements", declaredAt="/home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:98")
  public boolean noStmts() {
    {
        for (int i = 0; i < getBlock().getNumStmt(); i++) {
          if (!(getBlock().getStmt(i) instanceof Case)) {
            return false;
          }
        }
        return true;
      }
  }
  /**
   * @attribute syn
   * @aspect UnreachableStatements
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:107
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="UnreachableStatements", declaredAt="/home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:107")
  public boolean noStmtsAfterLastLabel() {
    boolean noStmtsAfterLastLabel_value = getBlock().getNumStmt() > 0
          && getBlock().getStmt(getBlock().getNumStmt()-1) instanceof Case;
    return noStmtsAfterLastLabel_value;
  }
  /**
   * @attribute syn
   * @aspect UnreachableStatements
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:111
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="UnreachableStatements", declaredAt="/home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:111")
  public boolean noDefaultLabel() {
    {
        for (int i = 0; i < getBlock().getNumStmt(); i++) {
          if (getBlock().getStmt(i) instanceof DefaultCase) {
            return false;
          }
        }
        return true;
      }
  }
  /** @apilevel internal */
  private void canCompleteNormally_reset() {
    canCompleteNormally_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle canCompleteNormally_computed = null;

  /** @apilevel internal */
  protected boolean canCompleteNormally_value;

  /**
   * @attribute syn
   * @aspect UnreachableStatements
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:50
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="UnreachableStatements", declaredAt="/home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:50")
  public boolean canCompleteNormally() {
    ASTNode$State state = state();
    if (canCompleteNormally_computed == ASTNode$State.NON_CYCLE || canCompleteNormally_computed == state().cycle()) {
      return canCompleteNormally_value;
    }
    canCompleteNormally_value = lastStmtCanCompleteNormally() || noStmts()
          || noStmtsAfterLastLabel()
          || noDefaultLabel() || reachableBreak();
    if (state().inCircle()) {
      canCompleteNormally_computed = state().cycle();
    
    } else {
      canCompleteNormally_computed = ASTNode$State.NON_CYCLE;
    
    }
    return canCompleteNormally_value;
  }
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:78
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:78")
  public boolean modifiedInScope(Variable var) {
    boolean modifiedInScope_Variable_value = getBlock().modifiedInScope(var);
    return modifiedInScope_Variable_value;
  }
  /** @apilevel internal */
  private void enumIndexExpr_reset() {
    enumIndexExpr_computed = false;
    
    enumIndexExpr_value = null;
  }
  /** @apilevel internal */
  protected boolean enumIndexExpr_computed = false;

  /** @apilevel internal */
  protected Expr enumIndexExpr_value;

  /**
   * This is the expression used during code generation to access the enum index field
   * and compute the jump target.
   * @attribute syn
   * @aspect EnumsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EnumsCodegen.jrag:169
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="EnumsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/EnumsCodegen.jrag:169")
  public Expr enumIndexExpr() {
    ASTNode$State state = state();
    if (enumIndexExpr_computed) {
      return enumIndexExpr_value;
    }
    state().enterLazyAttribute();
    enumIndexExpr_value = hostType().createEnumMethod(this).createBoundAccess(new List())
              .qualifiesAccess(
                  new ArrayAccess(((Expr) getExpr().treeCopyNoTransform())
                      .qualifiesAccess(new MethodAccess("ordinal", new List()))));
    enumIndexExpr_value.setParent(this);
    enumIndexExpr_computed = true;
    state().leaveLazyAttribute();
    return enumIndexExpr_value;
  }
  /** @return a collection of the constant cases in this switch statement. 
   * @attribute syn
   * @aspect EnumsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EnumsCodegen.jrag:176
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EnumsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/EnumsCodegen.jrag:176")
  public Collection<ConstCase> constCases() {
    {
        Collection<ConstCase> cases = new LinkedList<ConstCase>();
        for (Stmt stmt : getBlock().getStmtList()) {
          if (stmt instanceof ConstCase) {
            cases.add((ConstCase) stmt);
          }
        }
        return cases;
      }
  }
  /** @apilevel internal */
  private void enumIndices_reset() {
    enumIndices_computed = null;
    enumIndices_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle enumIndices_computed = null;

  /** @apilevel internal */
  protected Map<EnumConstant, Integer> enumIndices_value;

  /**
   * Should only be evaluated on switch statements that have enum constants as
   * the switch cases.
   * @attribute syn
   * @aspect EnumsCodegen
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EnumsCodegen.jrag:190
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EnumsCodegen", declaredAt="/home/olivier/projects/extendj/jimple8/backend/EnumsCodegen.jrag:190")
  public Map<EnumConstant, Integer> enumIndices() {
    ASTNode$State state = state();
    if (enumIndices_computed == ASTNode$State.NON_CYCLE || enumIndices_computed == state().cycle()) {
      return enumIndices_value;
    }
    enumIndices_value = enumIndices_compute();
    if (state().inCircle()) {
      enumIndices_computed = state().cycle();
    
    } else {
      enumIndices_computed = ASTNode$State.NON_CYCLE;
    
    }
    return enumIndices_value;
  }
  /** @apilevel internal */
  private Map<EnumConstant, Integer> enumIndices_compute() {
      Map<EnumConstant, Integer> indexMap = new HashMap<EnumConstant, Integer>();
      int next = 1;
      for (ConstCase cc : constCases()) {
        indexMap.put((EnumConstant) cc.getValue().varDecl(), next);
        next += 1;
      }
      return indexMap;
    }
  /** @apilevel internal */
  private void defaultCase_reset() {
    defaultCase_computed = null;
    defaultCase_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle defaultCase_computed = null;

  /** @apilevel internal */
  protected DefaultCase defaultCase_value;

  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:43
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:43")
  public DefaultCase defaultCase() {
    ASTNode$State state = state();
    if (defaultCase_computed == ASTNode$State.NON_CYCLE || defaultCase_computed == state().cycle()) {
      return defaultCase_value;
    }
    defaultCase_value = defaultCase_compute();
    if (state().inCircle()) {
      defaultCase_computed = state().cycle();
    
    } else {
      defaultCase_computed = ASTNode$State.NON_CYCLE;
    
    }
    return defaultCase_value;
  }
  /** @apilevel internal */
  private DefaultCase defaultCase_compute() {
      for(int i= 0; i < getBlock().getNumStmt(); i++) {
        if(getBlock().getStmt(i) instanceof DefaultCase)
          return (DefaultCase)getBlock().getStmt(i);
      }
      return null;
    }
  /** @apilevel internal */
  private void end_label_reset() {
    end_label_computed = null;
    end_label_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle end_label_computed = null;

  /** @apilevel internal */
  protected soot.jimple.Stmt end_label_value;

  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:51
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:51")
  public soot.jimple.Stmt end_label() {
    ASTNode$State state = state();
    if (end_label_computed == ASTNode$State.NON_CYCLE || end_label_computed == state().cycle()) {
      return end_label_value;
    }
    end_label_value = newLabel();
    if (state().inCircle()) {
      end_label_computed = state().cycle();
    
    } else {
      end_label_computed = ASTNode$State.NON_CYCLE;
    
    }
    return end_label_value;
  }
  /**
   * @attribute syn
   * @aspect Statements
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:224
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Statements", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Statements.jrag:224")
  public soot.jimple.Stmt break_label() {
    soot.jimple.Stmt break_label_value = end_label();
    return break_label_value;
  }
  /**
   * @attribute syn
   * @aspect StringsInSwitch
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/StringsInSwitch.jrag:49
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StringsInSwitch", declaredAt="/home/olivier/projects/extendj/jimple8/backend/StringsInSwitch.jrag:49")
  public boolean isSwitchWithString() {
    boolean isSwitchWithString_value = getExpr().type().isString();
    return isSwitchWithString_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:86
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:86")
  public TypeDecl typeInt() {
    ASTNode$State state = state();
    if (typeInt_computed == ASTNode$State.NON_CYCLE || typeInt_computed == state().cycle()) {
      return typeInt_value;
    }
    typeInt_value = getParent().Define_typeInt(this, null);
    if (state().inCircle()) {
      typeInt_computed = state().cycle();
    
    } else {
      typeInt_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeInt_value;
  }
  /** @apilevel internal */
  private void typeInt_reset() {
    typeInt_computed = null;
    typeInt_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeInt_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeInt_value;

  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:88
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:88")
  public TypeDecl typeLong() {
    ASTNode$State state = state();
    if (typeLong_computed == ASTNode$State.NON_CYCLE || typeLong_computed == state().cycle()) {
      return typeLong_value;
    }
    typeLong_value = getParent().Define_typeLong(this, null);
    if (state().inCircle()) {
      typeLong_computed = state().cycle();
    
    } else {
      typeLong_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeLong_value;
  }
  /** @apilevel internal */
  private void typeLong_reset() {
    typeLong_computed = null;
    typeLong_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeLong_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeLong_value;

  /**
   * @attribute inh
   * @aspect StringsInSwitch
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/StringsInSwitch.jrag:52
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="StringsInSwitch", declaredAt="/home/olivier/projects/extendj/jimple8/backend/StringsInSwitch.jrag:52")
  public TypeDecl typeString() {
    TypeDecl typeString_value = getParent().Define_typeString(this, null);
    return typeString_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:230
   * @apilevel internal
   */
  public Stmt Define_branchTarget(ASTNode _callerNode, ASTNode _childNode, Stmt branch) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return branch.canBranchTo(this) ? this : branchTarget(branch);
  }
  protected boolean canDefine_branchTarget(ASTNode _callerNode, ASTNode _childNode, Stmt branch) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:256
   * @apilevel internal
   */
  public boolean Define_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:729
      return getExpr().assignedAfter(v);
    }
    else if (getExprNoTransform() != null && _callerNode == getExpr()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:711
      {
          if (((ASTNode) v).isDescendantTo(this)) {
            return false;
          }
          boolean result = assignedBefore(v);
          return result;
        }
    }
    else {
      return getParent().Define_assignedBefore(this, _callerNode, v);
    }
  }
  protected boolean canDefine_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:887
   * @apilevel internal
   */
  public boolean Define_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1388
      return getExpr().unassignedAfter(v);
    }
    else if (getExprNoTransform() != null && _callerNode == getExpr()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:1386
      return unassignedBefore(v);
    }
    else {
      return getParent().Define_unassignedBefore(this, _callerNode, v);
    }
  }
  protected boolean canDefine_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:531
   * @apilevel internal
   */
  public boolean Define_insideSwitch(ASTNode _callerNode, ASTNode _childNode) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:534
      return true;
    }
    else {
      return getParent().Define_insideSwitch(this, _callerNode);
    }
  }
  protected boolean canDefine_insideSwitch(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:592
   * @apilevel internal
   */
  public Case Define_previousCase(ASTNode _callerNode, ASTNode _childNode, Case c) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:594
      {
          if (c instanceof ConstCase) {
            ConstCase cc = (ConstCase) c;
            if (cc.getValue().isConstant()) {
              if (cc.getValue().type().assignableToInt()) {
                return caseMap().get(cc.getValue().constant().intValue());
              }
            } else {
              // The label does not have a constant value, so we won't be able to
              // find a duplicate.
              return c;
            }
          } else if (c instanceof DefaultCase) {
            // Default case label.
            for (Stmt stmt : getBlock().getStmtList()) {
              if (stmt instanceof DefaultCase) {
                return (Case) stmt;
              }
            }
          }
          // Fall back on comparing against all case labels.
          for (Stmt stmt : getBlock().getStmtList()) {
            if (stmt instanceof Case && ((Case) stmt).constValue(c)) {
              return (Case) stmt;
            }
            if (stmt == c) {
              return c;
            }
          }
          // This should not happen, since the Case label that evaluates the
          // attribute must be directly nested in this switch statement.
          throw new Error("Case label not found in switch statement list.");
        }
    }
    else {
      return getParent().Define_previousCase(this, _callerNode, c);
    }
  }
  protected boolean canDefine_previousCase(ASTNode _callerNode, ASTNode _childNode, Case c) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:482
   * @apilevel internal
   */
  public TypeDecl Define_switchType(ASTNode _callerNode, ASTNode _childNode) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:483
      return getExpr().type();
    }
    else {
      return getParent().Define_switchType(this, _callerNode);
    }
  }
  protected boolean canDefine_switchType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:49
   * @apilevel internal
   */
  public boolean Define_reachable(ASTNode _callerNode, ASTNode _childNode) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:125
      return reachable();
    }
    else {
      return getParent().Define_reachable(this, _callerNode);
    }
  }
  protected boolean canDefine_reachable(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:280
   * @apilevel internal
   */
  public boolean Define_reportUnreachable(ASTNode _callerNode, ASTNode _childNode) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:218
      return reachable();
    }
    else {
      return getParent().Define_reportUnreachable(this, _callerNode);
    }
  }
  protected boolean canDefine_reportUnreachable(ASTNode _callerNode, ASTNode _childNode) {
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
    // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:457
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
  protected void collect_contributors_TypeDecl_enumSwitchStatements(CompilationUnit _root, java.util.Map<ASTNode, java.util.Set<ASTNode>> _map) {
    // @declaredat /home/olivier/projects/extendj/jimple8/backend/EnumsCodegen.jrag:160
    if (getExpr().type().isEnumDecl()) {
      {
        TypeDecl target = (TypeDecl) (hostType());
        java.util.Set<ASTNode> contributors = _map.get(target);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) target, contributors);
        }
        contributors.add(this);
      }
    }
    super.collect_contributors_TypeDecl_enumSwitchStatements(_root, _map);
  }
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    for (Problem value : typeProblems()) {
      collection.add(value);
    }
  }
  protected void contributeTo_TypeDecl_enumSwitchStatements(LinkedList<SwitchStmt> collection) {
    super.contributeTo_TypeDecl_enumSwitchStatements(collection);
    if (getExpr().type().isEnumDecl()) {
      collection.add(this);
    }
  }
}
