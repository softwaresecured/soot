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
 * @declaredat /home/olivier/projects/extendj/java8/grammar/ConstructorReference.ast:4
 * @astdecl ArrayReference : ConstructorReference;
 * @production ArrayReference : {@link ConstructorReference};

 */
public class ArrayReference extends ConstructorReference implements Cloneable {
  /**
   * @aspect Java8PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java8/frontend/PrettyPrint.jadd:35
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getTypeAccess());
    out.print("::new");
  }
  /**
   * @declaredat ASTNode:1
   */
  public ArrayReference() {
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
  public ArrayReference(Access p0) {
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
    congruentTo_FunctionDescriptor_reset();
    isExact_reset();
    potentiallyCompatible_TypeDecl_BodyDecl_reset();
    targetInterface_reset();
    toBlock_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:41
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:45
   */
  public ArrayReference clone() throws CloneNotSupportedException {
    ArrayReference node = (ArrayReference) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:50
   */
  public ArrayReference copy() {
    try {
      ArrayReference node = (ArrayReference) clone();
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
   * @declaredat ASTNode:69
   */
  @Deprecated
  public ArrayReference fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:79
   */
  public ArrayReference treeCopyNoTransform() {
    ArrayReference tree = (ArrayReference) copy();
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
   * @declaredat ASTNode:99
   */
  public ArrayReference treeCopy() {
    ArrayReference tree = (ArrayReference) copy();
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
   * @declaredat ASTNode:113
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
  private void congruentTo_FunctionDescriptor_reset() {
    congruentTo_FunctionDescriptor_computed = null;
    congruentTo_FunctionDescriptor_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map congruentTo_FunctionDescriptor_values;
  /** @apilevel internal */
  protected java.util.Map congruentTo_FunctionDescriptor_computed;
  /**
   * @attribute syn
   * @aspect ConstructorReference
   * @declaredat /home/olivier/projects/extendj/java8/frontend/ConstructorReference.jrag:72
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructorReference", declaredAt="/home/olivier/projects/extendj/java8/frontend/ConstructorReference.jrag:72")
  public boolean congruentTo(FunctionDescriptor fd) {
    Object _parameters = fd;
    if (congruentTo_FunctionDescriptor_computed == null) congruentTo_FunctionDescriptor_computed = new java.util.HashMap(4);
    if (congruentTo_FunctionDescriptor_values == null) congruentTo_FunctionDescriptor_values = new java.util.HashMap(4);
    ASTState state = state();
    if (congruentTo_FunctionDescriptor_values.containsKey(_parameters)
        && congruentTo_FunctionDescriptor_computed.containsKey(_parameters)
        && (congruentTo_FunctionDescriptor_computed.get(_parameters) == ASTState.NON_CYCLE || congruentTo_FunctionDescriptor_computed.get(_parameters) == state().cycle())) {
      return (Boolean) congruentTo_FunctionDescriptor_values.get(_parameters);
    }
    boolean congruentTo_FunctionDescriptor_value = congruentTo_compute(fd);
    if (state().inCircle()) {
      congruentTo_FunctionDescriptor_values.put(_parameters, congruentTo_FunctionDescriptor_value);
      congruentTo_FunctionDescriptor_computed.put(_parameters, state().cycle());
    
    } else {
      congruentTo_FunctionDescriptor_values.put(_parameters, congruentTo_FunctionDescriptor_value);
      congruentTo_FunctionDescriptor_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return congruentTo_FunctionDescriptor_value;
  }
  /** @apilevel internal */
  private boolean congruentTo_compute(FunctionDescriptor fd) {
      if (fd.method.hasValue()) {
        MethodDecl targetMethod = fd.method.get();
        if (targetMethod.getNumParameter() != 1) {
          return false;
        }
        if (!targetMethod.getParameter(0).type()
            .assignConversionTo(fd.fromInterface().typeInt(), null)) {
          return false;
        }
        if (!targetMethod.type().isVoid()) {
          if (!getTypeAccess().type().assignConversionTo(targetMethod.type(), null)) {
            return false;
          }
        }
        return true;
      } else {
        // No target method.
        return false;
      }
    }
  /** @apilevel internal */
  private void isExact_reset() {
    isExact_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle isExact_computed = null;

  /** @apilevel internal */
  protected boolean isExact_value;

  /**
   * @attribute syn
   * @aspect ConstructorReference
   * @declaredat /home/olivier/projects/extendj/java8/frontend/ConstructorReference.jrag:153
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructorReference", declaredAt="/home/olivier/projects/extendj/java8/frontend/ConstructorReference.jrag:153")
  public boolean isExact() {
    ASTState state = state();
    if (isExact_computed == ASTState.NON_CYCLE || isExact_computed == state().cycle()) {
      return isExact_value;
    }
    isExact_value = true;
    if (state().inCircle()) {
      isExact_computed = state().cycle();
    
    } else {
      isExact_computed = ASTState.NON_CYCLE;
    
    }
    return isExact_value;
  }
  /** @apilevel internal */
  private void potentiallyCompatible_TypeDecl_BodyDecl_reset() {
    potentiallyCompatible_TypeDecl_BodyDecl_computed = null;
    potentiallyCompatible_TypeDecl_BodyDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map potentiallyCompatible_TypeDecl_BodyDecl_values;
  /** @apilevel internal */
  protected java.util.Map potentiallyCompatible_TypeDecl_BodyDecl_computed;
  /**
   * @attribute syn
   * @aspect MethodSignature18
   * @declaredat /home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:544
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MethodSignature18", declaredAt="/home/olivier/projects/extendj/java8/frontend/MethodSignature.jrag:544")
  public boolean potentiallyCompatible(TypeDecl type, BodyDecl candidateDecl) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(type);
    _parameters.add(candidateDecl);
    if (potentiallyCompatible_TypeDecl_BodyDecl_computed == null) potentiallyCompatible_TypeDecl_BodyDecl_computed = new java.util.HashMap(4);
    if (potentiallyCompatible_TypeDecl_BodyDecl_values == null) potentiallyCompatible_TypeDecl_BodyDecl_values = new java.util.HashMap(4);
    ASTState state = state();
    if (potentiallyCompatible_TypeDecl_BodyDecl_values.containsKey(_parameters)
        && potentiallyCompatible_TypeDecl_BodyDecl_computed.containsKey(_parameters)
        && (potentiallyCompatible_TypeDecl_BodyDecl_computed.get(_parameters) == ASTState.NON_CYCLE || potentiallyCompatible_TypeDecl_BodyDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) potentiallyCompatible_TypeDecl_BodyDecl_values.get(_parameters);
    }
    boolean potentiallyCompatible_TypeDecl_BodyDecl_value = potentiallyCompatible_compute(type, candidateDecl);
    if (state().inCircle()) {
      potentiallyCompatible_TypeDecl_BodyDecl_values.put(_parameters, potentiallyCompatible_TypeDecl_BodyDecl_value);
      potentiallyCompatible_TypeDecl_BodyDecl_computed.put(_parameters, state().cycle());
    
    } else {
      potentiallyCompatible_TypeDecl_BodyDecl_values.put(_parameters, potentiallyCompatible_TypeDecl_BodyDecl_value);
      potentiallyCompatible_TypeDecl_BodyDecl_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return potentiallyCompatible_TypeDecl_BodyDecl_value;
  }
  /** @apilevel internal */
  private boolean potentiallyCompatible_compute(TypeDecl type, BodyDecl candidateDecl) {
      if (super.potentiallyCompatible(type, candidateDecl) && type.isTypeVariable()) {
        return true;
      } else if (!super.potentiallyCompatible(type, candidateDecl)) {
        return false;
      }
      InterfaceDecl iDecl = (InterfaceDecl) type;
      FunctionDescriptor fd = iDecl.functionDescriptor();
      if (fd.method.hasValue()) {
        return fd.method.get().arity() == 1;
      } else {
        return false;
      }
    }
  /**
   * @attribute syn
   * @aspect Java8NameCheck
   * @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:534
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java8NameCheck", declaredAt="/home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:534")
  public Collection<Problem> nameProblems() {
    {
        Access typeAccess = getTypeAccess();
        while (typeAccess instanceof ArrayTypeAccess) {
          typeAccess = ((ArrayTypeAccess) typeAccess).getAccess();
        }
        if (typeAccess instanceof ParTypeAccess) {
          return Collections.singletonList(error("Cannot create array of generic type"));
        }
        return Collections.emptyList();
      }
  }
  /** @apilevel internal */
  private void targetInterface_reset() {
    targetInterface_computed = null;
    targetInterface_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle targetInterface_computed = null;

  /** @apilevel internal */
  protected InterfaceDecl targetInterface_value;

  /**
   * @attribute syn
   * @aspect TargetType
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:280
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TargetType", declaredAt="/home/olivier/projects/extendj/java8/frontend/TargetType.jrag:280")
  public InterfaceDecl targetInterface() {
    ASTState state = state();
    if (targetInterface_computed == ASTState.NON_CYCLE || targetInterface_computed == state().cycle()) {
      return targetInterface_value;
    }
    targetInterface_value = targetInterface_compute();
    if (state().inCircle()) {
      targetInterface_computed = state().cycle();
    
    } else {
      targetInterface_computed = ASTState.NON_CYCLE;
    
    }
    return targetInterface_value;
  }
  /** @apilevel internal */
  private InterfaceDecl targetInterface_compute() {
      if (targetType().isNull()) {
        return null;
      } else if (!(targetType() instanceof InterfaceDecl)) {
        return null;
      } else {
        InterfaceDecl iDecl = (InterfaceDecl) targetType();
        InterfaceDecl iface = (InterfaceDecl) iDecl.original();
        MethodDecl method = iface.functionDescriptor().method.get();
        TypeDecl returnType = method.type();
        if (!returnType.isVoid() && returnType.involvesTypeParameters()) {
          Constraints constraints = new Constraints();
          GenericInterfaceDecl genericInterface = (GenericInterfaceDecl) iface;
          for (TypeVariable var : genericInterface.getTypeParameters()) {
            constraints.addTypeVariable(var);
          }
          TypeDecl ret = getTypeAccess().type();
          if (ret.isPrimitiveType()) {
            ret = ret.boxed();
          }
          constraints.convertibleFrom(ret, returnType);
          constraints.resolveEqualityConstraints();
          constraints.resolveSupertypeConstraints();
          constraints.resolveSubtypeConstraints();
          Parameterization parameterization = ((ParInterfaceDecl) iDecl).getParameterization();
          java.util.List<TypeDecl> args = new ArrayList<TypeDecl>(constraints.typeArguments());
          for (int i = 0; i < args.size(); ++i) {
            if (args.get(i) == null) {
              args.set(i, parameterization.args.get(i));
            }
          }
          iDecl = (InterfaceDecl) genericInterface.lookupParTypeDecl(args);
        }
        return iDecl;
      }
    }
  /**
   * Build a nested ArrayTypeWithSizeAccess from the normal
   * ArrayTypeAccess.  This is to be used when generating
   * the anonymous inner class which must instantiate a new
   * array according to the array reference.
   * @attribute syn
   * @aspect ConstructorReferenceToClass
   * @declaredat /home/olivier/projects/extendj/java8/backend/ConstructorReferenceToClass.jrag:42
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructorReferenceToClass", declaredAt="/home/olivier/projects/extendj/java8/backend/ConstructorReferenceToClass.jrag:42")
  public ArrayTypeAccess getArrayTypeWithSizeAccess(Expr expr) {
    {
        return recursiveArrayTypeWithSizeAccess((ArrayTypeAccess) getTypeAccess(),
            (Expr) expr.treeCopyNoTransform());
      }
  }
  /**
   * @attribute syn
   * @aspect ConstructorReferenceToClass
   * @declaredat /home/olivier/projects/extendj/java8/backend/ConstructorReferenceToClass.jrag:47
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructorReferenceToClass", declaredAt="/home/olivier/projects/extendj/java8/backend/ConstructorReferenceToClass.jrag:47")
  public ArrayTypeAccess recursiveArrayTypeWithSizeAccess(ArrayTypeAccess access, Expr expr) {
    {
        if (!(access.getAccess() instanceof ArrayTypeAccess)) {
          return new ArrayTypeWithSizeAccess(access.getAccess().treeCopy(), expr);
        } else {
          return new ArrayTypeAccess(
              recursiveArrayTypeWithSizeAccess((ArrayTypeAccess) access.getAccess(), expr));
        }
      }
  }
  /** @apilevel internal */
  private void toBlock_reset() {
    toBlock_computed = null;
    toBlock_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle toBlock_computed = null;

  /** @apilevel internal */
  protected Block toBlock_value;

  /**
   * @attribute syn
   * @aspect ConstructorReferenceToClass
   * @declaredat /home/olivier/projects/extendj/java8/backend/ConstructorReferenceToClass.jrag:97
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructorReferenceToClass", declaredAt="/home/olivier/projects/extendj/java8/backend/ConstructorReferenceToClass.jrag:97")
  public Block toBlock() {
    ASTState state = state();
    if (toBlock_computed == ASTState.NON_CYCLE || toBlock_computed == state().cycle()) {
      return toBlock_value;
    }
    toBlock_value = toBlock_compute();
    if (state().inCircle()) {
      toBlock_computed = state().cycle();
    
    } else {
      toBlock_computed = ASTState.NON_CYCLE;
    
    }
    return toBlock_value;
  }
  /** @apilevel internal */
  private Block toBlock_compute() {
      // First, build an ArrayCreationExpr used in the block.
      MethodDecl targetMethod = targetInterface().functionDescriptor().method.get();
      String paramName = targetMethod.getParameter(0).name();
      VarAccess paramAccess = new VarAccess(paramName);
      ArrayCreationExpr arrayExpr = new ArrayCreationExpr(getArrayTypeWithSizeAccess(paramAccess),
          new Opt());
  
      // Next, build actual block.
      Stmt blockStmt = null;
      if (targetMethod.type().isVoid()) {
        blockStmt = new ExprStmt(arrayExpr);
      } else {
        blockStmt = new ReturnStmt(arrayExpr);
      }
      List<Stmt> stmtList = new List<Stmt>();
      stmtList.add(blockStmt);
      return new Block(stmtList);
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
    // @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:532
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
    for (Problem value : nameProblems()) {
      collection.add(value);
    }
  }
}
