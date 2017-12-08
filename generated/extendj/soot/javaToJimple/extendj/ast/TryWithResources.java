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
 * The JSR 334 try with resources statement.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java7/grammar/TryWithResources.ast:4
 * @production TryWithResources : {@link TryStmt} ::= <span class="component">Resource:{@link ResourceDeclaration}*</span> <span class="component">{@link Block}</span> <span class="component">{@link CatchClause}*</span> <span class="component">[Finally:{@link Block}]</span>;

 */
public class TryWithResources extends TryStmt implements Cloneable, VariableScope {
  /**
   * @aspect Java7PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PrettyPrint.jadd:68
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("try (");
    out.join(getResourceList(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.print("; ");
      }
    });
    out.print(") ");
    out.print(getBlock());
    if (hasCatchClause()) {
      out.print(" ");
      out.join(getCatchClauseList(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(" ");
        }
      });
    }
    if (hasFinally()) {
      out.print(" finally ");
      out.print(getFinally());
    }
  }
  /**
   * Returns {@code true} if the try-with-resources statement can throw
   * an exception of type (or a subtype of) catchType.
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:236
   */
  protected boolean reachedException(TypeDecl catchType) {
    boolean found = false;
    // Found is true if the exception type is caught by a catch clause.
    for (int i = 0; i < getNumCatchClause() && !found; i++) {
      if (getCatchClause(i).handles(catchType)) {
        found = true;
      }
    }
    // If an exception is thrown in the block and the exception is not caught and
    // either there is no finally block or the finally block can complete normally.
    if (!found && (!hasNonEmptyFinally() || getFinally().canCompleteNormally()) ) {
      if (catchableException(catchType)) {
        return true;
      }
    }
    // Even if the exception is caught by the catch clauses they may
    // throw new exceptions.
    for (int i = 0; i < getNumCatchClause(); i++) {
      if (getCatchClause(i).reachedException(catchType)) {
        return true;
      }
    }
    return hasNonEmptyFinally() && getFinally().reachedException(catchType);
  }
  /**
   * @declaredat ASTNode:1
   */
  public TryWithResources() {
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
    children = new ASTNode[5];
    setChild(new List(), 0);
    setChild(new List(), 2);
    setChild(new Opt(), 3);
  }
  /**
   * @declaredat ASTNode:16
   */
  public TryWithResources(List<ResourceDeclaration> p0, Block p1, List<CatchClause> p2, Opt<Block> p3) {
    setChild(p0, 0);
    setChild(p1, 1);
    setChild(p2, 2);
    setChild(p3, 3);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:23
   */
  protected int numChildren() {
    return 4;
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
    localLookup_String_reset();
    localVariableDeclaration_String_reset();
    assignedAfter_Variable_reset();
    catchableException_TypeDecl_reset();
    getTransformed_reset();
    handlesException_TypeDecl_reset();
    typeError_reset();
    typeRuntimeException_reset();
    lookupVariable_String_reset();
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
  public TryWithResources clone() throws CloneNotSupportedException {
    TryWithResources node = (TryWithResources) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:55
   */
  public TryWithResources copy() {
    try {
      TryWithResources node = (TryWithResources) clone();
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
  public TryWithResources fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:84
   */
  public TryWithResources treeCopyNoTransform() {
    TryWithResources tree = (TryWithResources) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 4:
          tree.children[i] = null;
          continue;
        }
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
   * @declaredat ASTNode:109
   */
  public TryWithResources treeCopy() {
    TryWithResources tree = (TryWithResources) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 4:
          tree.children[i] = null;
          continue;
        }
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
   * @declaredat ASTNode:128
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Resource list.
   * @param list The new list node to be used as the Resource list.
   * @apilevel high-level
   */
  public void setResourceList(List<ResourceDeclaration> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the Resource list.
   * @return Number of children in the Resource list.
   * @apilevel high-level
   */
  public int getNumResource() {
    return getResourceList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Resource list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Resource list.
   * @apilevel low-level
   */
  public int getNumResourceNoTransform() {
    return getResourceListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Resource list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Resource list.
   * @apilevel high-level
   */
  public ResourceDeclaration getResource(int i) {
    return (ResourceDeclaration) getResourceList().getChild(i);
  }
  /**
   * Check whether the Resource list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasResource() {
    return getResourceList().getNumChild() != 0;
  }
  /**
   * Append an element to the Resource list.
   * @param node The element to append to the Resource list.
   * @apilevel high-level
   */
  public void addResource(ResourceDeclaration node) {
    List<ResourceDeclaration> list = (parent == null) ? getResourceListNoTransform() : getResourceList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addResourceNoTransform(ResourceDeclaration node) {
    List<ResourceDeclaration> list = getResourceListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Resource list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setResource(ResourceDeclaration node, int i) {
    List<ResourceDeclaration> list = getResourceList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Resource list.
   * @return The node representing the Resource list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Resource")
  public List<ResourceDeclaration> getResourceList() {
    List<ResourceDeclaration> list = (List<ResourceDeclaration>) getChild(0);
    return list;
  }
  /**
   * Retrieves the Resource list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Resource list.
   * @apilevel low-level
   */
  public List<ResourceDeclaration> getResourceListNoTransform() {
    return (List<ResourceDeclaration>) getChildNoTransform(0);
  }
  /**
   * @return the element at index {@code i} in the Resource list without
   * triggering rewrites.
   */
  public ResourceDeclaration getResourceNoTransform(int i) {
    return (ResourceDeclaration) getResourceListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the Resource list.
   * @return The node representing the Resource list.
   * @apilevel high-level
   */
  public List<ResourceDeclaration> getResources() {
    return getResourceList();
  }
  /**
   * Retrieves the Resource list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Resource list.
   * @apilevel low-level
   */
  public List<ResourceDeclaration> getResourcesNoTransform() {
    return getResourceListNoTransform();
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
   * Replaces the CatchClause list.
   * @param list The new list node to be used as the CatchClause list.
   * @apilevel high-level
   */
  public void setCatchClauseList(List<CatchClause> list) {
    setChild(list, 2);
  }
  /**
   * Retrieves the number of children in the CatchClause list.
   * @return Number of children in the CatchClause list.
   * @apilevel high-level
   */
  public int getNumCatchClause() {
    return getCatchClauseList().getNumChild();
  }
  /**
   * Retrieves the number of children in the CatchClause list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the CatchClause list.
   * @apilevel low-level
   */
  public int getNumCatchClauseNoTransform() {
    return getCatchClauseListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the CatchClause list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the CatchClause list.
   * @apilevel high-level
   */
  public CatchClause getCatchClause(int i) {
    return (CatchClause) getCatchClauseList().getChild(i);
  }
  /**
   * Check whether the CatchClause list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasCatchClause() {
    return getCatchClauseList().getNumChild() != 0;
  }
  /**
   * Append an element to the CatchClause list.
   * @param node The element to append to the CatchClause list.
   * @apilevel high-level
   */
  public void addCatchClause(CatchClause node) {
    List<CatchClause> list = (parent == null) ? getCatchClauseListNoTransform() : getCatchClauseList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addCatchClauseNoTransform(CatchClause node) {
    List<CatchClause> list = getCatchClauseListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the CatchClause list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setCatchClause(CatchClause node, int i) {
    List<CatchClause> list = getCatchClauseList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the CatchClause list.
   * @return The node representing the CatchClause list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="CatchClause")
  public List<CatchClause> getCatchClauseList() {
    List<CatchClause> list = (List<CatchClause>) getChild(2);
    return list;
  }
  /**
   * Retrieves the CatchClause list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the CatchClause list.
   * @apilevel low-level
   */
  public List<CatchClause> getCatchClauseListNoTransform() {
    return (List<CatchClause>) getChildNoTransform(2);
  }
  /**
   * @return the element at index {@code i} in the CatchClause list without
   * triggering rewrites.
   */
  public CatchClause getCatchClauseNoTransform(int i) {
    return (CatchClause) getCatchClauseListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the CatchClause list.
   * @return The node representing the CatchClause list.
   * @apilevel high-level
   */
  public List<CatchClause> getCatchClauses() {
    return getCatchClauseList();
  }
  /**
   * Retrieves the CatchClause list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the CatchClause list.
   * @apilevel low-level
   */
  public List<CatchClause> getCatchClausesNoTransform() {
    return getCatchClauseListNoTransform();
  }
  /**
   * Replaces the optional node for the Finally child. This is the <code>Opt</code>
   * node containing the child Finally, not the actual child!
   * @param opt The new node to be used as the optional node for the Finally child.
   * @apilevel low-level
   */
  public void setFinallyOpt(Opt<Block> opt) {
    setChild(opt, 3);
  }
  /**
   * Replaces the (optional) Finally child.
   * @param node The new node to be used as the Finally child.
   * @apilevel high-level
   */
  public void setFinally(Block node) {
    getFinallyOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional Finally child exists.
   * @return {@code true} if the optional Finally child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasFinally() {
    return getFinallyOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) Finally child.
   * @return The Finally child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Block getFinally() {
    return (Block) getFinallyOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the Finally child. This is the <code>Opt</code> node containing the child Finally, not the actual child!
   * @return The optional node for child the Finally child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="Finally")
  public Opt<Block> getFinallyOpt() {
    return (Opt<Block>) getChild(3);
  }
  /**
   * Retrieves the optional node for child Finally. This is the <code>Opt</code> node containing the child Finally, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Finally.
   * @apilevel low-level
   */
  public Opt<Block> getFinallyOptNoTransform() {
    return (Opt<Block>) getChildNoTransform(3);
  }
  /**
   * Retrieves the ExceptionHandler child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the ExceptionHandler child.
   * @apilevel low-level
   */
  public Block getExceptionHandlerNoTransform() {
    return (Block) getChildNoTransform(4);
  }
  /**
   * Retrieves the child position of the optional child ExceptionHandler.
   * @return The the child position of the optional child ExceptionHandler.
   * @apilevel low-level
   */
  protected int getExceptionHandlerChildPosition() {
    return 4;
  }
  /**
   * Exception error checks.
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:63
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:63")
  public Collection<Problem> exceptionHandlingProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        // Check exception handling of exceptions on auto closing of resource.
        for (ResourceDeclaration resource : getResourceList()) {
          MethodDecl close = lookupClose(resource);
          if (close == null) {
            continue;
          }
          for (Access exception : close.getExceptionList()) {
            TypeDecl exceptionType = exception.type();
            if (!twrHandlesException(exceptionType)) {
              problems.add(errorf(
                  "automatic closing of resource %s may raise the uncaught exception %s; "
                  + "it must be caught or declared as being thrown",
                  resource.name(), exceptionType.fullName()));
            }
          }
        }
        return problems;
      }
  }
  /**
   * This attribute computes whether or not the TWR statement
   * has a catch clause which handles the exception.
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:88
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:88")
  public boolean catchHandlesException(TypeDecl exceptionType) {
    {
        for (int i = 0; i < getNumCatchClause(); i++) {
          if (getCatchClause(i).handles(exceptionType)) {
            return true;
          }
        }
        return false;
      }
  }
  /**
   * Returns true if exceptions of type exceptionType are handled
   * in the try-with-resources statement or any containing statement
   * within the directly enclosing method or initializer block.
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:102
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:102")
  public boolean twrHandlesException(TypeDecl exceptionType) {
    {
        if (catchHandlesException(exceptionType)) {
          return true;
        }
        if (hasNonEmptyFinally() && !getFinally().canCompleteNormally()) {
          return true;
        }
        return handlesException(exceptionType);
      }
  }
  /**
   * Lookup the close method declaration for the resource which is being used.
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:127
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:127")
  public MethodDecl lookupClose(ResourceDeclaration resource) {
    {
        TypeDecl resourceType = resource.getTypeAccess().type();
        for (MethodDecl method : (Collection<MethodDecl>) resourceType.memberMethods("close")) {
          if (method.getNumParameter() == 0) {
            return method;
          }
        }
        // We should not throw a runtime exception here. If there is no close
        // method it likely means that the resource type is not a subtype of
        // java.lang.AutoCloseable and type checking will report this error.
        return null;
      }
  }
  /** @apilevel internal */
  private void localLookup_String_reset() {
    localLookup_String_computed = new java.util.HashMap(4);
    localLookup_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map localLookup_String_values;
  /** @apilevel internal */
  protected java.util.Map localLookup_String_computed;
  /**
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:173
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:173")
  public SimpleSet<Variable> localLookup(String name) {
    Object _parameters = name;
    if (localLookup_String_computed == null) localLookup_String_computed = new java.util.HashMap(4);
    if (localLookup_String_values == null) localLookup_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (localLookup_String_values.containsKey(_parameters) && localLookup_String_computed != null
        && localLookup_String_computed.containsKey(_parameters)
        && (localLookup_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || localLookup_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<Variable>) localLookup_String_values.get(_parameters);
    }
    SimpleSet<Variable> localLookup_String_value = localLookup_compute(name);
    if (state().inCircle()) {
      localLookup_String_values.put(_parameters, localLookup_String_value);
      localLookup_String_computed.put(_parameters, state().cycle());
    
    } else {
      localLookup_String_values.put(_parameters, localLookup_String_value);
      localLookup_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return localLookup_String_value;
  }
  /** @apilevel internal */
  private SimpleSet<Variable> localLookup_compute(String name) {
      VariableDeclarator v = localVariableDeclaration(name);
      if (v != null) {
        return v;
      }
      return lookupVariable(name);
    }
  /** @apilevel internal */
  private void localVariableDeclaration_String_reset() {
    localVariableDeclaration_String_computed = new java.util.HashMap(4);
    localVariableDeclaration_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map localVariableDeclaration_String_values;
  /** @apilevel internal */
  protected java.util.Map localVariableDeclaration_String_computed;
  /**
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:181
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:181")
  public VariableDeclarator localVariableDeclaration(String name) {
    Object _parameters = name;
    if (localVariableDeclaration_String_computed == null) localVariableDeclaration_String_computed = new java.util.HashMap(4);
    if (localVariableDeclaration_String_values == null) localVariableDeclaration_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (localVariableDeclaration_String_values.containsKey(_parameters) && localVariableDeclaration_String_computed != null
        && localVariableDeclaration_String_computed.containsKey(_parameters)
        && (localVariableDeclaration_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || localVariableDeclaration_String_computed.get(_parameters) == state().cycle())) {
      return (VariableDeclarator) localVariableDeclaration_String_values.get(_parameters);
    }
    VariableDeclarator localVariableDeclaration_String_value = localVariableDeclaration_compute(name);
    if (state().inCircle()) {
      localVariableDeclaration_String_values.put(_parameters, localVariableDeclaration_String_value);
      localVariableDeclaration_String_computed.put(_parameters, state().cycle());
    
    } else {
      localVariableDeclaration_String_values.put(_parameters, localVariableDeclaration_String_value);
      localVariableDeclaration_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return localVariableDeclaration_String_value;
  }
  /** @apilevel internal */
  private VariableDeclarator localVariableDeclaration_compute(String name) {
      for (ResourceDeclaration resource : getResourceList()) {
        if (resource.declaresVariable(name)) {
          return resource;
        }
      }
      return null;
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
        new_assignedAfter_Variable_value = getBlock().assignedAfter(v);
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
      boolean new_assignedAfter_Variable_value = getBlock().assignedAfter(v);
      if (new_assignedAfter_Variable_value != ((Boolean)_value.value)) {
        state.setChangeInCycle();
        _value.value = new_assignedAfter_Variable_value;
      }
      return new_assignedAfter_Variable_value;
    } else {
      return (Boolean) _value.value;
    }
  }
  /**
   * True if the automatic closing of resources in this try-with-resources statement
   * may throw an exception of type catchType.
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:265
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:265")
  public boolean resourceClosingException(TypeDecl catchType) {
    {
        for (ResourceDeclaration resource : getResourceList()) {
          MethodDecl close = lookupClose(resource);
          if (close == null) {
            continue;
          }
          for (Access exception : close.getExceptionList()) {
            TypeDecl exceptionType = exception.type();
            if (catchType.mayCatch(exception.type())) {
              return true;
            }
          }
        }
        return false;
      }
  }
  /**
   * True if the resource initialization of this try-with-resources statement
   * may throw an exception of type catchType.
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:285
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:285")
  public boolean resourceInitializationException(TypeDecl catchType) {
    {
        for (ResourceDeclaration resource : getResourceList()) {
          if (resource.reachedException(catchType)) {
            return true;
          }
        }
        return false;
      }
  }
  /** @apilevel internal */
  private void catchableException_TypeDecl_reset() {
    catchableException_TypeDecl_computed = new java.util.HashMap(4);
    catchableException_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map catchableException_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map catchableException_TypeDecl_computed;
  /**
   * The block of the try statement can throw an exception of
   * a type assignable to the given type.
   * @attribute syn
   * @aspect ExceptionHandling
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:289
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ExceptionHandling", declaredAt="/home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:289")
  public boolean catchableException(TypeDecl type) {
    Object _parameters = type;
    if (catchableException_TypeDecl_computed == null) catchableException_TypeDecl_computed = new java.util.HashMap(4);
    if (catchableException_TypeDecl_values == null) catchableException_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (catchableException_TypeDecl_values.containsKey(_parameters) && catchableException_TypeDecl_computed != null
        && catchableException_TypeDecl_computed.containsKey(_parameters)
        && (catchableException_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || catchableException_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) catchableException_TypeDecl_values.get(_parameters);
    }
    boolean catchableException_TypeDecl_value = getBlock().reachedException(type)
          || resourceClosingException(type)
          || resourceInitializationException(type);
    if (state().inCircle()) {
      catchableException_TypeDecl_values.put(_parameters, catchableException_TypeDecl_value);
      catchableException_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      catchableException_TypeDecl_values.put(_parameters, catchableException_TypeDecl_value);
      catchableException_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return catchableException_TypeDecl_value;
  }
  /** @apilevel internal */
  private void getTransformed_reset() {
    getTransformed_computed = false;
    
    getTransformed_value = null;
  }
  /** @apilevel internal */
  protected boolean getTransformed_computed = false;

  /** @apilevel internal */
  protected Stmt getTransformed_value;

  /**
   * An NTA which is used for code generation.
   * 
   * This NTA will handle the recursive nature of code
   * generation for try-with-resources statements.
   * @attribute syn
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:39
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/jimple8/backend/TryWithResources.jrag:39")
  public Stmt getTransformed() {
    ASTNode$State state = state();
    if (getTransformed_computed) {
      return getTransformed_value;
    }
    state().enterLazyAttribute();
    getTransformed_value = getTransformed_compute();
    getTransformed_value.setParent(this);
    getTransformed_computed = true;
    state().leaveLazyAttribute();
    return getTransformed_value;
  }
  /** @apilevel internal */
  private Stmt getTransformed_compute() {
      if (getNumCatchClause() == 0 && !hasNonEmptyFinally()) {
        // Transform to BasicTWR.
        Block block;
        if (getNumResource() == 1) {
          block = (Block) getBlock().treeCopy();
        } else {
          block = new Block();
          List<ResourceDeclaration> resourceListTail = new List<ResourceDeclaration>();
          for (int i = 1; i < getNumResource(); ++i) {
            resourceListTail.add((ResourceDeclaration) getResource(i).treeCopyNoTransform());
          }
          block.addStmt(new TryWithResources(
                resourceListTail,
                (Block) getBlock().treeCopyNoTransform(),
                new List<CatchClause>(),
                new Opt<Block>()));
        }
        return new BasicTWR(
            (ResourceDeclaration)
            getResource(0).treeCopyNoTransform(),
            block);
      } else {
        // Transform to surrounding try stmt.
        Block block = new Block();
        block.addStmt(new TryWithResources(
              (List<ResourceDeclaration>)
              getResourceList().treeCopyNoTransform(),
              (Block) getBlock().treeCopyNoTransform(),
              new List<CatchClause>(),
              new Opt<Block>()));
  
        return new TryStmt(block,
            (List<CatchClause>) getCatchClauseList().treeCopyNoTransform(),
            (Opt<Block>) getFinallyOpt().treeCopyNoTransform());
      }
    }
  /**
   * Inherit the handlesException attribute from methoddecl.
   * @attribute inh
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:115
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:115")
  public boolean handlesException(TypeDecl exceptionType) {
    Object _parameters = exceptionType;
    if (handlesException_TypeDecl_computed == null) handlesException_TypeDecl_computed = new java.util.HashMap(4);
    if (handlesException_TypeDecl_values == null) handlesException_TypeDecl_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (handlesException_TypeDecl_values.containsKey(_parameters) && handlesException_TypeDecl_computed != null
        && handlesException_TypeDecl_computed.containsKey(_parameters)
        && (handlesException_TypeDecl_computed.get(_parameters) == ASTNode$State.NON_CYCLE || handlesException_TypeDecl_computed.get(_parameters) == state().cycle())) {
      return (Boolean) handlesException_TypeDecl_values.get(_parameters);
    }
    boolean handlesException_TypeDecl_value = getParent().Define_handlesException(this, null, exceptionType);
    if (state().inCircle()) {
      handlesException_TypeDecl_values.put(_parameters, handlesException_TypeDecl_value);
      handlesException_TypeDecl_computed.put(_parameters, state().cycle());
    
    } else {
      handlesException_TypeDecl_values.put(_parameters, handlesException_TypeDecl_value);
      handlesException_TypeDecl_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return handlesException_TypeDecl_value;
  }
  /** @apilevel internal */
  private void handlesException_TypeDecl_reset() {
    handlesException_TypeDecl_computed = new java.util.HashMap(4);
    handlesException_TypeDecl_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map handlesException_TypeDecl_values;
  /** @apilevel internal */
  protected java.util.Map handlesException_TypeDecl_computed;
  /**
   * @attribute inh
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:140
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:140")
  public TypeDecl typeError() {
    ASTNode$State state = state();
    if (typeError_computed == ASTNode$State.NON_CYCLE || typeError_computed == state().cycle()) {
      return typeError_value;
    }
    typeError_value = getParent().Define_typeError(this, null);
    if (state().inCircle()) {
      typeError_computed = state().cycle();
    
    } else {
      typeError_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeError_value;
  }
  /** @apilevel internal */
  private void typeError_reset() {
    typeError_computed = null;
    typeError_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeError_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeError_value;

  /**
   * @attribute inh
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:142
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:142")
  public TypeDecl typeRuntimeException() {
    ASTNode$State state = state();
    if (typeRuntimeException_computed == ASTNode$State.NON_CYCLE || typeRuntimeException_computed == state().cycle()) {
      return typeRuntimeException_value;
    }
    typeRuntimeException_value = getParent().Define_typeRuntimeException(this, null);
    if (state().inCircle()) {
      typeRuntimeException_computed = state().cycle();
    
    } else {
      typeRuntimeException_computed = ASTNode$State.NON_CYCLE;
    
    }
    return typeRuntimeException_value;
  }
  /** @apilevel internal */
  private void typeRuntimeException_reset() {
    typeRuntimeException_computed = null;
    typeRuntimeException_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle typeRuntimeException_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeRuntimeException_value;

  /**
   * @attribute inh
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:192
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:192")
  public SimpleSet<Variable> lookupVariable(String name) {
    Object _parameters = name;
    if (lookupVariable_String_computed == null) lookupVariable_String_computed = new java.util.HashMap(4);
    if (lookupVariable_String_values == null) lookupVariable_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (lookupVariable_String_values.containsKey(_parameters) && lookupVariable_String_computed != null
        && lookupVariable_String_computed.containsKey(_parameters)
        && (lookupVariable_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || lookupVariable_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<Variable>) lookupVariable_String_values.get(_parameters);
    }
    SimpleSet<Variable> lookupVariable_String_value = getParent().Define_lookupVariable(this, null, name);
    if (state().inCircle()) {
      lookupVariable_String_values.put(_parameters, lookupVariable_String_value);
      lookupVariable_String_computed.put(_parameters, state().cycle());
    
    } else {
      lookupVariable_String_values.put(_parameters, lookupVariable_String_value);
      lookupVariable_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return lookupVariable_String_value;
  }
  /** @apilevel internal */
  private void lookupVariable_String_reset() {
    lookupVariable_String_computed = new java.util.HashMap(4);
    lookupVariable_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map lookupVariable_String_values;
  /** @apilevel internal */
  protected java.util.Map lookupVariable_String_computed;
  /**
   * @attribute inh
   * @aspect TryWithResources
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:198
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TryWithResources", declaredAt="/home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:198")
  public boolean resourcePreviouslyDeclared(String name) {
    boolean resourcePreviouslyDeclared_String_value = getParent().Define_resourcePreviouslyDeclared(this, null, name);
    return resourcePreviouslyDeclared_String_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:115
   * @apilevel internal
   */
  public boolean Define_handlesException(ASTNode _callerNode, ASTNode _childNode, TypeDecl exceptionType) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:120
      return twrHandlesException(exceptionType);
    }
    else if (_callerNode == getResourceListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:117
      int i = _callerNode.getIndexOfChild(_childNode);
      return twrHandlesException(exceptionType);
    }
    else {
      return super.Define_handlesException(_callerNode, _childNode, exceptionType);
    }
  }
  protected boolean canDefine_handlesException(ASTNode _callerNode, ASTNode _childNode, TypeDecl exceptionType) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/UnreachableStatements.jrag:182
   * @apilevel internal
   */
  public boolean Define_reachableCatchClause(ASTNode _callerNode, ASTNode _childNode, TypeDecl exceptionType) {
    if (_callerNode == getCatchClauseListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:144
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      {
          for (int i = 0; i < childIndex; i++) {
            if (getCatchClause(i).handles(exceptionType)) {
              return false;
            }
          }
          if (catchableException(exceptionType)) {
            return true;
          }
          if (exceptionType.mayCatch(typeError()) || exceptionType.mayCatch(typeRuntimeException())) {
            return true;
          }
          return false;
        }
    }
    else {
      return super.Define_reachableCatchClause(_callerNode, _childNode, exceptionType);
    }
  }
  protected boolean canDefine_reachableCatchClause(ASTNode _callerNode, ASTNode _childNode, TypeDecl exceptionType) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet<Variable> Define_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (_callerNode == getResourceListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:164
      int index = _callerNode.getIndexOfChild(_childNode);
      {
          for (int i = index - 1; i >= 0; --i) {
            if (getResource(i).declaresVariable(name)) {
              return getResource(i);
            }
          }
          return lookupVariable(name);
        }
    }
    else if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:162
      return localLookup(name);
    }
    else {
      return getParent().Define_lookupVariable(this, _callerNode, name);
    }
  }
  protected boolean canDefine_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:31
   * @apilevel internal
   */
  public VariableScope Define_outerScope(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getResourceListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:194
      int i = _callerNode.getIndexOfChild(_childNode);
      return this;
    }
    else {
      return getParent().Define_outerScope(this, _callerNode);
    }
  }
  protected boolean canDefine_outerScope(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:198
   * @apilevel internal
   */
  public boolean Define_resourcePreviouslyDeclared(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (_callerNode == getResourceListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:200
      int index = _callerNode.getIndexOfChild(_childNode);
      {
          for (int i = 0; i < index; ++i) {
            if (getResource(i).name().equals(name)) {
              return true;
            }
          }
          return false;
        }
    }
    else {
      return getParent().Define_resourcePreviouslyDeclared(this, _callerNode, name);
    }
  }
  protected boolean canDefine_resourcePreviouslyDeclared(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:256
   * @apilevel internal
   */
  public boolean Define_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    if (getBlockNoTransform() != null && _callerNode == getBlock()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:227
      return getNumResource() == 0
            ? assignedBefore(v)
            : getResource(getNumResource() - 1).assignedAfter(v);
    }
    else if (_callerNode == getResourceListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:224
      int index = _callerNode.getIndexOfChild(_childNode);
      return index == 0 ? assignedBefore(v) : getResource(index - 1).assignedAfter(v);
    }
    else {
      return super.Define_assignedBefore(_callerNode, _childNode, v);
    }
  }
  protected boolean canDefine_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/VariableDeclaration.jrag:133
   * @apilevel internal
   */
  public Modifiers Define_declarationModifiers(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getResourceListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:308
      int index = _callerNode.getIndexOfChild(_childNode);
      return getResource(index).getResourceModifiers();
    }
    else {
      return getParent().Define_declarationModifiers(this, _callerNode);
    }
  }
  protected boolean canDefine_declarationModifiers(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/VariableDeclaration.jrag:144
   * @apilevel internal
   */
  public Access Define_declarationType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getResourceListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:311
      int index = _callerNode.getIndexOfChild(_childNode);
      return getResource(index).getResourceType();
    }
    else {
      return getParent().Define_declarationType(this, _callerNode);
    }
  }
  protected boolean canDefine_declarationType(ASTNode _callerNode, ASTNode _childNode) {
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
    // @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:58
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
  protected void contributeTo_CompilationUnit_problems(LinkedList<Problem> collection) {
    super.contributeTo_CompilationUnit_problems(collection);
    for (Problem value : exceptionHandlingProblems()) {
      collection.add(value);
    }
  }
}
