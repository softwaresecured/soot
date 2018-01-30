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
 * A catch clause that can catch a single exception type.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java7/grammar/MultiCatch.ast:9
 * @astdecl BasicCatch : CatchClause ::= Parameter:ParameterDeclaration Block;
 * @production BasicCatch : {@link CatchClause} ::= <span class="component">Parameter:{@link ParameterDeclaration}</span> <span class="component">{@link Block}</span>;

 */
public class BasicCatch extends CatchClause implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:88
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("catch (");
    out.print(getParameter());
    out.print(") ");
    out.print(getBlock());
  }
  /**
   * @declaredat ASTNode:1
   */
  public BasicCatch() {
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
  @ASTNodeAnnotation.Constructor(
    name = {"Parameter", "Block"},
    type = {"ParameterDeclaration", "Block"},
    kind = {"Child", "Child"}
  )
  public BasicCatch(ParameterDeclaration p0, Block p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:23
   */
  protected int numChildren() {
    return 2;
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
    parameterDeclaration_String_reset();
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
  public BasicCatch clone() throws CloneNotSupportedException {
    BasicCatch node = (BasicCatch) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:47
   */
  public BasicCatch copy() {
    try {
      BasicCatch node = (BasicCatch) clone();
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
  public BasicCatch fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:76
   */
  public BasicCatch treeCopyNoTransform() {
    BasicCatch tree = (BasicCatch) copy();
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
  public BasicCatch treeCopy() {
    BasicCatch tree = (BasicCatch) copy();
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
   * Replaces the Parameter child.
   * @param node The new node to replace the Parameter child.
   * @apilevel high-level
   */
  public void setParameter(ParameterDeclaration node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Parameter child.
   * @return The current node used as the Parameter child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Parameter")
  public ParameterDeclaration getParameter() {
    return (ParameterDeclaration) getChild(0);
  }
  /**
   * Retrieves the Parameter child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Parameter child.
   * @apilevel low-level
   */
  public ParameterDeclaration getParameterNoTransform() {
    return (ParameterDeclaration) getChildNoTransform(0);
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
   * @attribute syn
   * @aspect ExceptionHandling
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:279
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="/home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:279")
  public boolean handles(TypeDecl exceptionType) {
    boolean handles_TypeDecl_value = !getParameter().type().isUnknown()
          && exceptionType.instanceOf(getParameter().type());
    return handles_TypeDecl_value;
  }
  /** @apilevel internal */
  private void parameterDeclaration_String_reset() {
    parameterDeclaration_String_computed = null;
    parameterDeclaration_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map parameterDeclaration_String_values;
  /** @apilevel internal */
  protected java.util.Map parameterDeclaration_String_computed;
  /**
   * @attribute syn
   * @aspect VariableScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:192
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="VariableScope", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:192")
  public SimpleSet<Variable> parameterDeclaration(String name) {
    Object _parameters = name;
    if (parameterDeclaration_String_computed == null) parameterDeclaration_String_computed = new java.util.HashMap(4);
    if (parameterDeclaration_String_values == null) parameterDeclaration_String_values = new java.util.HashMap(4);
    ASTState state = state();
    if (parameterDeclaration_String_values.containsKey(_parameters)
        && parameterDeclaration_String_computed.containsKey(_parameters)
        && (parameterDeclaration_String_computed.get(_parameters) == ASTState.NON_CYCLE || parameterDeclaration_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<Variable>) parameterDeclaration_String_values.get(_parameters);
    }
    SimpleSet<Variable> parameterDeclaration_String_value = getParameter().name().equals(name)
          ? getParameter()
          : ASTNode.<Variable>emptySet();
    if (state().inCircle()) {
      parameterDeclaration_String_values.put(_parameters, parameterDeclaration_String_value);
      parameterDeclaration_String_computed.put(_parameters, state().cycle());
    
    } else {
      parameterDeclaration_String_values.put(_parameters, parameterDeclaration_String_value);
      parameterDeclaration_String_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return parameterDeclaration_String_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet<Variable> Define_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (getParameterNoTransform() != null && _callerNode == getParameter()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:145
      return parameterDeclaration(name);
    }
    else {
      return super.Define_lookupVariable(_callerNode, _childNode, name);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LookupVariable.jrag:30
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupVariable
   */
  protected boolean canDefine_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:31
   * @apilevel internal
   */
  public VariableScope Define_outerScope(ASTNode _callerNode, ASTNode _childNode) {
    if (getParameterNoTransform() != null && _callerNode == getParameter()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:443
      return this;
    }
    else {
      return getParent().Define_outerScope(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:31
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute outerScope
   */
  protected boolean canDefine_outerScope(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    if (getParameterNoTransform() != null && _callerNode == getParameter()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:109
      return NameType.TYPE_NAME;
    }
    else {
      return getParent().Define_nameType(this, _callerNode);
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:49
   * @apilevel internal
   */
  public boolean Define_reachable(ASTNode _callerNode, ASTNode _childNode) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:177
      return reachableCatchClause(getParameter().type());
    }
    else {
      return getParent().Define_reachable(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:49
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute reachable
   */
  protected boolean canDefine_reachable(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:44
   * @apilevel internal
   */
  public boolean Define_isMethodParameter(ASTNode _callerNode, ASTNode _childNode) {
    if (getParameterNoTransform() != null && _callerNode == getParameter()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/VariableDeclaration.jrag:95
      return false;
    }
    else {
      return getParent().Define_isMethodParameter(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:44
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isMethodParameter
   */
  protected boolean canDefine_isMethodParameter(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:45
   * @apilevel internal
   */
  public boolean Define_isConstructorParameter(ASTNode _callerNode, ASTNode _childNode) {
    if (getParameterNoTransform() != null && _callerNode == getParameter()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/VariableDeclaration.jrag:96
      return false;
    }
    else {
      return getParent().Define_isConstructorParameter(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:45
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isConstructorParameter
   */
  protected boolean canDefine_isConstructorParameter(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:46
   * @apilevel internal
   */
  public boolean Define_isExceptionHandlerParameter(ASTNode _callerNode, ASTNode _childNode) {
    if (getParameterNoTransform() != null && _callerNode == getParameter()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/VariableDeclaration.jrag:97
      return true;
    }
    else {
      return getParent().Define_isExceptionHandlerParameter(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:46
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isExceptionHandlerParameter
   */
  protected boolean canDefine_isExceptionHandlerParameter(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/VariableArityParameters.jrag:46
   * @apilevel internal
   */
  public boolean Define_variableArityValid(ASTNode _callerNode, ASTNode _childNode) {
    if (getParameterNoTransform() != null && _callerNode == getParameter()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/VariableArityParameters.jrag:43
      return false;
    }
    else {
      return getParent().Define_variableArityValid(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/VariableArityParameters.jrag:46
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute variableArityValid
   */
  protected boolean canDefine_variableArityValid(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:30
   * @apilevel internal
   */
  public boolean Define_inhModifiedInScope(ASTNode _callerNode, ASTNode _childNode, Variable var) {
    if (getParameterNoTransform() != null && _callerNode == getParameter()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:75
      return getBlock().modifiedInScope(var);
    }
    else {
      return getParent().Define_inhModifiedInScope(this, _callerNode, var);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:30
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute inhModifiedInScope
   */
  protected boolean canDefine_inhModifiedInScope(ASTNode _callerNode, ASTNode _childNode, Variable var) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:202
   * @apilevel internal
   */
  public boolean Define_isCatchParam(ASTNode _callerNode, ASTNode _childNode) {
    if (getParameterNoTransform() != null && _callerNode == getParameter()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:206
      return true;
    }
    else {
      return getParent().Define_isCatchParam(this, _callerNode);
    }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:202
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isCatchParam
   */
  protected boolean canDefine_isCatchParam(ASTNode _callerNode, ASTNode _childNode) {
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
    // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:493
    if (!getParameter().type().instanceOf(typeThrowable())) {
      {
        java.util.Set<ASTNode> contributors = _map.get(_root);
        if (contributors == null) {
          contributors = new java.util.LinkedHashSet<ASTNode>();
          _map.put((ASTNode) _root, contributors);
        }
        contributors.add(this);
      }
    }
    // @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:283
    if (!getBlock().reachable() && reportUnreachable()) {
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
    if (!getParameter().type().instanceOf(typeThrowable())) {
      collection.add(error("*** The catch variable must extend Throwable"));
    }
    if (!getBlock().reachable() && reportUnreachable()) {
      collection.add(errorf("the exception %s is not thrown in the body of the try statement",
                getParameter().type().fullName()));
    }
  }
}
