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
 * @declaredat /home/olivier/projects/extendj/java8/grammar/ConstructorReference.ast:3
 * @production ClassReference : {@link ConstructorReference} ::= <span class="component">TypeArgument:{@link Access}*</span>;

 */
public class ClassReference extends ConstructorReference implements Cloneable {
  /**
   * @aspect Java8PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java8/frontend/PrettyPrint.jadd:42
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getTypeAccess());
    out.print("::");
    if (hasTypeArgument()) {
      out.print("<");
      out.join(getTypeArguments(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(", ");
        }
      });
      out.print(">");
    }
    out.print("new");
  }
  /**
   * @declaredat ASTNode:1
   */
  public ClassReference() {
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
    setChild(new List(), 1);
  }
  /**
   * @declaredat ASTNode:14
   */
  public ClassReference(Access p0, List<Access> p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:19
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:25
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:29
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    targetConstructor_FunctionDescriptor_reset();
    syntheticInstanceExpr_FunctionDescriptor_reset();
    congruentTo_FunctionDescriptor_reset();
    potentiallyApplicableConstructors_FunctionDescriptor_reset();
    exactCompileTimeDeclaration_reset();
    isExact_reset();
    potentiallyCompatible_TypeDecl_BodyDecl_reset();
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
  public ClassReference clone() throws CloneNotSupportedException {
    ClassReference node = (ClassReference) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:50
   */
  public ClassReference copy() {
    try {
      ClassReference node = (ClassReference) clone();
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
  public ClassReference fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:79
   */
  public ClassReference treeCopyNoTransform() {
    ClassReference tree = (ClassReference) copy();
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
  public ClassReference treeCopy() {
    ClassReference tree = (ClassReference) copy();
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
  /**
   * Replaces the TypeArgument list.
   * @param list The new list node to be used as the TypeArgument list.
   * @apilevel high-level
   */
  public void setTypeArgumentList(List<Access> list) {
    setChild(list, 1);
  }
  /**
   * Retrieves the number of children in the TypeArgument list.
   * @return Number of children in the TypeArgument list.
   * @apilevel high-level
   */
  public int getNumTypeArgument() {
    return getTypeArgumentList().getNumChild();
  }
  /**
   * Retrieves the number of children in the TypeArgument list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the TypeArgument list.
   * @apilevel low-level
   */
  public int getNumTypeArgumentNoTransform() {
    return getTypeArgumentListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the TypeArgument list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the TypeArgument list.
   * @apilevel high-level
   */
  public Access getTypeArgument(int i) {
    return (Access) getTypeArgumentList().getChild(i);
  }
  /**
   * Check whether the TypeArgument list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasTypeArgument() {
    return getTypeArgumentList().getNumChild() != 0;
  }
  /**
   * Append an element to the TypeArgument list.
   * @param node The element to append to the TypeArgument list.
   * @apilevel high-level
   */
  public void addTypeArgument(Access node) {
    List<Access> list = (parent == null) ? getTypeArgumentListNoTransform() : getTypeArgumentList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addTypeArgumentNoTransform(Access node) {
    List<Access> list = getTypeArgumentListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the TypeArgument list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setTypeArgument(Access node, int i) {
    List<Access> list = getTypeArgumentList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the TypeArgument list.
   * @return The node representing the TypeArgument list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="TypeArgument")
  public List<Access> getTypeArgumentList() {
    List<Access> list = (List<Access>) getChild(1);
    return list;
  }
  /**
   * Retrieves the TypeArgument list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeArgument list.
   * @apilevel low-level
   */
  public List<Access> getTypeArgumentListNoTransform() {
    return (List<Access>) getChildNoTransform(1);
  }
  /**
   * @return the element at index {@code i} in the TypeArgument list without
   * triggering rewrites.
   */
  public Access getTypeArgumentNoTransform(int i) {
    return (Access) getTypeArgumentListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the TypeArgument list.
   * @return The node representing the TypeArgument list.
   * @apilevel high-level
   */
  public List<Access> getTypeArguments() {
    return getTypeArgumentList();
  }
  /**
   * Retrieves the TypeArgument list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeArgument list.
   * @apilevel low-level
   */
  public List<Access> getTypeArgumentsNoTransform() {
    return getTypeArgumentListNoTransform();
  }
  /** @apilevel internal */
  private void targetConstructor_FunctionDescriptor_reset() {
    targetConstructor_FunctionDescriptor_computed = new java.util.HashMap(4);
    targetConstructor_FunctionDescriptor_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map targetConstructor_FunctionDescriptor_values;
  /** @apilevel internal */
  protected java.util.Map targetConstructor_FunctionDescriptor_computed;
  /**
   * @attribute syn
   * @aspect ConstructorReference
   * @declaredat /home/olivier/projects/extendj/java8/frontend/ConstructorReference.jrag:32
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructorReference", declaredAt="/home/olivier/projects/extendj/java8/frontend/ConstructorReference.jrag:32")
  public ConstructorDecl targetConstructor(FunctionDescriptor fd) {
    Object _parameters = fd;
    if (targetConstructor_FunctionDescriptor_computed == null) targetConstructor_FunctionDescriptor_computed = new java.util.HashMap(4);
    if (targetConstructor_FunctionDescriptor_values == null) targetConstructor_FunctionDescriptor_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (targetConstructor_FunctionDescriptor_values.containsKey(_parameters) && targetConstructor_FunctionDescriptor_computed != null
        && targetConstructor_FunctionDescriptor_computed.containsKey(_parameters)
        && (targetConstructor_FunctionDescriptor_computed.get(_parameters) == ASTNode$State.NON_CYCLE || targetConstructor_FunctionDescriptor_computed.get(_parameters) == state().cycle())) {
      return (ConstructorDecl) targetConstructor_FunctionDescriptor_values.get(_parameters);
    }
    ConstructorDecl targetConstructor_FunctionDescriptor_value = syntheticInstanceExpr(fd).decl();
    if (state().inCircle()) {
      targetConstructor_FunctionDescriptor_values.put(_parameters, targetConstructor_FunctionDescriptor_value);
      targetConstructor_FunctionDescriptor_computed.put(_parameters, state().cycle());
    
    } else {
      targetConstructor_FunctionDescriptor_values.put(_parameters, targetConstructor_FunctionDescriptor_value);
      targetConstructor_FunctionDescriptor_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return targetConstructor_FunctionDescriptor_value;
  }
  /** @apilevel internal */
  private void syntheticInstanceExpr_FunctionDescriptor_reset() {
    syntheticInstanceExpr_FunctionDescriptor_values = null;
    syntheticInstanceExpr_FunctionDescriptor_list = null;
  }
  /** @apilevel internal */
  protected List syntheticInstanceExpr_FunctionDescriptor_list;
  /** @apilevel internal */
  protected java.util.Map syntheticInstanceExpr_FunctionDescriptor_values;

  /**
   * @attribute syn
   * @aspect ConstructorReference
   * @declaredat /home/olivier/projects/extendj/java8/frontend/ConstructorReference.jrag:35
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="ConstructorReference", declaredAt="/home/olivier/projects/extendj/java8/frontend/ConstructorReference.jrag:35")
  public ClassInstanceExpr syntheticInstanceExpr(FunctionDescriptor fd) {
    Object _parameters = fd;
    if (syntheticInstanceExpr_FunctionDescriptor_values == null) syntheticInstanceExpr_FunctionDescriptor_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (syntheticInstanceExpr_FunctionDescriptor_values.containsKey(_parameters)) {
      return (ClassInstanceExpr) syntheticInstanceExpr_FunctionDescriptor_values.get(_parameters);
    }
    state().enterLazyAttribute();
    ClassInstanceExpr syntheticInstanceExpr_FunctionDescriptor_value = syntheticInstanceExpr_compute(fd);
    if (syntheticInstanceExpr_FunctionDescriptor_list == null) {
      syntheticInstanceExpr_FunctionDescriptor_list = new List();
      syntheticInstanceExpr_FunctionDescriptor_list.setParent(this);
    }
    syntheticInstanceExpr_FunctionDescriptor_list.add(syntheticInstanceExpr_FunctionDescriptor_value);
    if (syntheticInstanceExpr_FunctionDescriptor_value != null) {
      syntheticInstanceExpr_FunctionDescriptor_value = (ClassInstanceExpr) syntheticInstanceExpr_FunctionDescriptor_list.getChild(syntheticInstanceExpr_FunctionDescriptor_list.numChildren - 1);
    }
    syntheticInstanceExpr_FunctionDescriptor_values.put(_parameters, syntheticInstanceExpr_FunctionDescriptor_value);
    state().leaveLazyAttribute();
    return syntheticInstanceExpr_FunctionDescriptor_value;
  }
  /** @apilevel internal */
  private ClassInstanceExpr syntheticInstanceExpr_compute(FunctionDescriptor fd) {
      List<Expr> arguments = new List<Expr>();
      if (fd.method.hasValue()) {
        MethodDecl targetMethod = fd.method.get();
        for (int i = 0; i < targetMethod.getNumParameter(); i++) {
          TypeDecl argumentType = targetMethod.getParameter(i).type();
          arguments.add(new SyntheticTypeAccess(argumentType));
        }
      }
  
      ClassInstanceExpr instanceExpr = null;
      if (hasTypeArgument()) {
        instanceExpr = new ParConstructorReferenceAccess(
            (Access) getTypeAccess().treeCopyNoTransform(), arguments,
            new Opt(), (List<Access>) getTypeArgumentList().treeCopyNoTransform(), fd);
      } else {
        // Must check for raw reference type, and in that case infer using diamond (JLS 15.13.1)
        boolean checkDiamond = true;
        if (getTypeAccess().type().hostType() != null && !getTypeAccess().type().isStatic()
            && getTypeAccess().type().hostType().isRawType()) {
          checkDiamond = false;
        }
        if (getTypeAccess().type().isRawType() && checkDiamond) {
          DiamondAccess diamond = new DiamondAccess((Access) getTypeAccess().treeCopyNoTransform());
          instanceExpr = new ConstructorReferenceAccess(diamond, arguments, fd);
        } else {
          instanceExpr = new ConstructorReferenceAccess(
              (Access) getTypeAccess().treeCopyNoTransform(), arguments, fd);
        }
      }
      return instanceExpr;
    }
  /** @apilevel internal */
  private void congruentTo_FunctionDescriptor_reset() {
    congruentTo_FunctionDescriptor_computed = new java.util.HashMap(4);
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
    ASTNode$State state = state();
    if (congruentTo_FunctionDescriptor_values.containsKey(_parameters) && congruentTo_FunctionDescriptor_computed != null
        && congruentTo_FunctionDescriptor_computed.containsKey(_parameters)
        && (congruentTo_FunctionDescriptor_computed.get(_parameters) == ASTNode$State.NON_CYCLE || congruentTo_FunctionDescriptor_computed.get(_parameters) == state().cycle())) {
      return (Boolean) congruentTo_FunctionDescriptor_values.get(_parameters);
    }
    boolean congruentTo_FunctionDescriptor_value = congruentTo_compute(fd);
    if (state().inCircle()) {
      congruentTo_FunctionDescriptor_values.put(_parameters, congruentTo_FunctionDescriptor_value);
      congruentTo_FunctionDescriptor_computed.put(_parameters, state().cycle());
    
    } else {
      congruentTo_FunctionDescriptor_values.put(_parameters, congruentTo_FunctionDescriptor_value);
      congruentTo_FunctionDescriptor_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return congruentTo_FunctionDescriptor_value;
  }
  /** @apilevel internal */
  private boolean congruentTo_compute(FunctionDescriptor fd) {
      ConstructorDecl decl = targetConstructor(fd);
      if (unknownConstructor() == decl) {
        return false;
      }
      if (fd.method.hasValue()) {
        MethodDecl targetMethod = fd.method.get();
        if (!targetMethod.type().isVoid()) {
          TypeDecl returnType = syntheticInstanceExpr(fd).type();
          if (!returnType.assignConversionTo(targetMethod.type(), null)) {
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
  private void potentiallyApplicableConstructors_FunctionDescriptor_reset() {
    potentiallyApplicableConstructors_FunctionDescriptor_computed = new java.util.HashMap(4);
    potentiallyApplicableConstructors_FunctionDescriptor_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map potentiallyApplicableConstructors_FunctionDescriptor_values;
  /** @apilevel internal */
  protected java.util.Map potentiallyApplicableConstructors_FunctionDescriptor_computed;
  /**
   * Find potentially applicable constructors for a class reference matching a given
   * function descriptor.
   * @attribute syn
   * @aspect ConstructorReference
   * @declaredat /home/olivier/projects/extendj/java8/frontend/ConstructorReference.jrag:120
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructorReference", declaredAt="/home/olivier/projects/extendj/java8/frontend/ConstructorReference.jrag:120")
  public java.util.List<ConstructorDecl> potentiallyApplicableConstructors(FunctionDescriptor fd) {
    Object _parameters = fd;
    if (potentiallyApplicableConstructors_FunctionDescriptor_computed == null) potentiallyApplicableConstructors_FunctionDescriptor_computed = new java.util.HashMap(4);
    if (potentiallyApplicableConstructors_FunctionDescriptor_values == null) potentiallyApplicableConstructors_FunctionDescriptor_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (potentiallyApplicableConstructors_FunctionDescriptor_values.containsKey(_parameters) && potentiallyApplicableConstructors_FunctionDescriptor_computed != null
        && potentiallyApplicableConstructors_FunctionDescriptor_computed.containsKey(_parameters)
        && (potentiallyApplicableConstructors_FunctionDescriptor_computed.get(_parameters) == ASTNode$State.NON_CYCLE || potentiallyApplicableConstructors_FunctionDescriptor_computed.get(_parameters) == state().cycle())) {
      return (java.util.List<ConstructorDecl>) potentiallyApplicableConstructors_FunctionDescriptor_values.get(_parameters);
    }
    java.util.List<ConstructorDecl> potentiallyApplicableConstructors_FunctionDescriptor_value = potentiallyApplicableConstructors_compute(fd);
    if (state().inCircle()) {
      potentiallyApplicableConstructors_FunctionDescriptor_values.put(_parameters, potentiallyApplicableConstructors_FunctionDescriptor_value);
      potentiallyApplicableConstructors_FunctionDescriptor_computed.put(_parameters, state().cycle());
    
    } else {
      potentiallyApplicableConstructors_FunctionDescriptor_values.put(_parameters, potentiallyApplicableConstructors_FunctionDescriptor_value);
      potentiallyApplicableConstructors_FunctionDescriptor_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return potentiallyApplicableConstructors_FunctionDescriptor_value;
  }
  /** @apilevel internal */
  private java.util.List<ConstructorDecl> potentiallyApplicableConstructors_compute(FunctionDescriptor fd) {
      if (fd.method.hasValue()) {
        MethodDecl targetMethod = fd.method.get();
        TypeDecl classType = syntheticInstanceExpr(fd).type();
        Collection<ConstructorDecl> col = classType.constructors();
        ArrayList<ConstructorDecl> applicable = new ArrayList<ConstructorDecl>();
        for (ConstructorDecl decl : col) {
          if (!decl.accessibleFrom(hostType())) {
            continue;
          }
          if (!(decl.arity() == targetMethod.arity())) {
            continue;
          }
          if (hasTypeArgument()) {
            if (!decl.isGeneric()) {
              continue;
            }
            GenericConstructorDecl genDecl = decl.genericDecl();
            if (!(getNumTypeArgument() == genDecl.getNumTypeParameter())) {
              continue;
            }
          }
          applicable.add(decl);
        }
        return applicable;
      } else {
        // No target method.
        return Collections.emptyList();
      }
    }
  /** @apilevel internal */
  private void exactCompileTimeDeclaration_reset() {
    exactCompileTimeDeclaration_computed = null;
    exactCompileTimeDeclaration_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle exactCompileTimeDeclaration_computed = null;

  /** @apilevel internal */
  protected ConstructorDecl exactCompileTimeDeclaration_value;

  /**
   * @attribute syn
   * @aspect ConstructorReference
   * @declaredat /home/olivier/projects/extendj/java8/frontend/ConstructorReference.jrag:155
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstructorReference", declaredAt="/home/olivier/projects/extendj/java8/frontend/ConstructorReference.jrag:155")
  public ConstructorDecl exactCompileTimeDeclaration() {
    ASTNode$State state = state();
    if (exactCompileTimeDeclaration_computed == ASTNode$State.NON_CYCLE || exactCompileTimeDeclaration_computed == state().cycle()) {
      return exactCompileTimeDeclaration_value;
    }
    exactCompileTimeDeclaration_value = exactCompileTimeDeclaration_compute();
    if (state().inCircle()) {
      exactCompileTimeDeclaration_computed = state().cycle();
    
    } else {
      exactCompileTimeDeclaration_computed = ASTNode$State.NON_CYCLE;
    
    }
    return exactCompileTimeDeclaration_value;
  }
  /** @apilevel internal */
  private ConstructorDecl exactCompileTimeDeclaration_compute() {
      if (getTypeAccess().type().isRawType()) {
        if (getTypeAccess().type().hostType() == null || getTypeAccess().type().isStatic()
            || getTypeAccess().type().hostType().isRawType()) {
          return unknownConstructor();
        }
      }
      TypeDecl classType = getTypeAccess().type();
      Collection<ConstructorDecl> col = classType.constructors();
      ArrayList<ConstructorDecl> applicable = new ArrayList<ConstructorDecl>();
      int foundCompatible = 0;
      ConstructorDecl latestDecl = null;
  
      for (ConstructorDecl decl : col) {
        if (decl.accessibleFrom(hostType())) {
          foundCompatible++;
          latestDecl = decl;
        }
      }
      if (foundCompatible != 1) {
        return unknownConstructor();
      }
      if (latestDecl.isVariableArity()) {
        return unknownConstructor();
      }
      if (latestDecl.isGeneric()) {
        GenericConstructorDecl genericDecl = latestDecl.genericDecl();
        if (getNumTypeArgument() == genericDecl.getNumTypeParameter()) {
          return latestDecl;
        } else {
          return unknownConstructor();
        }
      }
      return latestDecl;
    }
  /** @apilevel internal */
  private void isExact_reset() {
    isExact_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isExact_computed = null;

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
    ASTNode$State state = state();
    if (isExact_computed == ASTNode$State.NON_CYCLE || isExact_computed == state().cycle()) {
      return isExact_value;
    }
    isExact_value = exactCompileTimeDeclaration() != unknownConstructor();
    if (state().inCircle()) {
      isExact_computed = state().cycle();
    
    } else {
      isExact_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isExact_value;
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
    boolean potentiallyCompatible_TypeDecl_BodyDecl_value = potentiallyCompatible_compute(type, candidateDecl);
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
  private boolean potentiallyCompatible_compute(TypeDecl type, BodyDecl candidateDecl) {
      if (super.potentiallyCompatible(type, candidateDecl) && type.isTypeVariable()) {
        return true;
      } else if (!super.potentiallyCompatible(type, candidateDecl)) {
        return false;
      }
  
      InterfaceDecl iDecl = (InterfaceDecl) type;
      FunctionDescriptor fd = iDecl.functionDescriptor();
  
      boolean foundMethod = false;
      if (fd.method.hasValue()) {
        MethodDecl targetMethod = fd.method.get();
        for (ConstructorDecl decl : potentiallyApplicableConstructors(fd)) {
          if (targetMethod.arity() == decl.arity()) {
            foundMethod = true;
            break;
          }
        }
      }
      return foundMethod;
    }
  /**
   * @attribute syn
   * @aspect Java8NameCheck
   * @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:522
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java8NameCheck", declaredAt="/home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:522")
  public Collection<Problem> nameProblems() {
    {
        for (int i = 0; i < getNumTypeArgument(); i++) {
          if (getTypeArgument(i) instanceof AbstractWildcard) {
            return Collections.singletonList(
                error("Wildcard not allowed in constructor reference type argument lists"));
          }
        }
        return Collections.emptyList();
      }
  }
  /** @apilevel internal */
  private void toBlock_reset() {
    toBlock_computed = null;
    toBlock_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle toBlock_computed = null;

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
    ASTNode$State state = state();
    if (toBlock_computed == ASTNode$State.NON_CYCLE || toBlock_computed == state().cycle()) {
      return toBlock_value;
    }
    toBlock_value = toBlock_compute();
    if (state().inCircle()) {
      toBlock_computed = state().cycle();
    
    } else {
      toBlock_computed = ASTNode$State.NON_CYCLE;
    
    }
    return toBlock_value;
  }
  /** @apilevel internal */
  private Block toBlock_compute() {
      List<Expr> arguments = new List<Expr>();
      MethodDecl targetMethod = targetInterface().functionDescriptor().method.get();
      for (int i = 0; i < targetMethod.getNumParameter(); i++) {
        String paramName = targetMethod.getParameter(i).name();
        arguments.add(new VarAccess(paramName));
      }
  
      ClassInstanceExpr instanceExpr = null;
      if (hasTypeArgument()) {
        instanceExpr = new ParClassInstanceExpr((Access) getTypeAccess().treeCopyNoTransform(),
            arguments, new Opt(), (List<Access>) getTypeArgumentList().treeCopyNoTransform());
      } else {
        if (getTypeAccess().type().isRawType()) {
          DiamondAccess diamond = new DiamondAccess((Access) getTypeAccess().treeCopyNoTransform());
          instanceExpr = new ClassInstanceExpr(diamond, arguments);
        } else {
          instanceExpr = new ClassInstanceExpr((Access) getTypeAccess().treeCopyNoTransform(),
              arguments);
        }
      }
      Stmt blockStmt = null;
      if (targetMethod.type().isVoid()) {
        blockStmt = new ExprStmt(instanceExpr);
      } else {
        blockStmt = new ReturnStmt(instanceExpr);
      }
      List<Stmt> stmtList = new List<Stmt>();
      stmtList.add(blockStmt);
      return new Block(stmtList);
    }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getTypeArgumentListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java8/frontend/ConstructorReference.jrag:69
      int childIndex = _callerNode.getIndexOfChild(_childNode);
      return NameType.TYPE_NAME;
    }
    else {
      return super.Define_nameType(_callerNode, _childNode);
    }
  }
  protected boolean canDefine_nameType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:31
   * @apilevel internal
   */
  public TypeDecl Define_targetType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == toClass_value) {
      // @declaredat /home/olivier/projects/extendj/java8/backend/ToClassInherited.jrag:54
      {
          FunctionDescriptor fd = targetInterface().functionDescriptor();
          if (fd.method.hasValue()) {
            return fd.method.get().type();
          } else {
            return unknownType();
          }
        }
    }
    else {
      return getParent().Define_targetType(this, _callerNode);
    }
  }
  protected boolean canDefine_targetType(ASTNode _callerNode, ASTNode _childNode) {
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
    // @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:520
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
    for (Problem value : nameProblems()) {
      collection.add(value);
    }
  }
}
