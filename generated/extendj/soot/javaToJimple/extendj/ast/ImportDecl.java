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
 * An abstract import declaration.
 * 
 * <p>Multiple concrete subclasses exist for different kind of
 * import declarations. More import declarations were added in Java 5.
 * 
 * See JLS 6 &sect;7.5.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:61
 * @production ImportDecl : {@link ASTNode} ::= <span class="component">{@link Access}</span>;

 */
public abstract class ImportDecl extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @declaredat ASTNode:1
   */
  public ImportDecl() {
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
  public ImportDecl(Access p0) {
    setChild(p0, 0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:17
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:23
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:27
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    importedTypes_String_reset();
    importedTypes_reset();
    importedFields_String_reset();
    importedMethods_String_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:35
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:39
   */
  public ImportDecl clone() throws CloneNotSupportedException {
    ImportDecl node = (ImportDecl) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:50
   */
  @Deprecated
  public abstract ImportDecl fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:58
   */
  public abstract ImportDecl treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:66
   */
  public abstract ImportDecl treeCopy();
  /**
   * Replaces the Access child.
   * @param node The new node to replace the Access child.
   * @apilevel high-level
   */
  public void setAccess(Access node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Access child.
   * @return The current node used as the Access child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Access")
  public Access getAccess() {
    return (Access) getChild(0);
  }
  /**
   * Retrieves the Access child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Access child.
   * @apilevel low-level
   */
  public Access getAccessNoTransform() {
    return (Access) getChildNoTransform(0);
  }
  /** @apilevel internal */
  private void importedTypes_String_reset() {
    importedTypes_String_computed = new java.util.HashMap(4);
    importedTypes_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map importedTypes_String_values;
  /** @apilevel internal */
  protected java.util.Map importedTypes_String_computed;
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:479
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:479")
  public SimpleSet<TypeDecl> importedTypes(String name) {
    Object _parameters = name;
    if (importedTypes_String_computed == null) importedTypes_String_computed = new java.util.HashMap(4);
    if (importedTypes_String_values == null) importedTypes_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (importedTypes_String_values.containsKey(_parameters) && importedTypes_String_computed != null
        && importedTypes_String_computed.containsKey(_parameters)
        && (importedTypes_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || importedTypes_String_computed.get(_parameters) == state().cycle())) {
      return (SimpleSet<TypeDecl>) importedTypes_String_values.get(_parameters);
    }
    SimpleSet<TypeDecl> importedTypes_String_value = emptySet();
    if (state().inCircle()) {
      importedTypes_String_values.put(_parameters, importedTypes_String_value);
      importedTypes_String_computed.put(_parameters, state().cycle());
    
    } else {
      importedTypes_String_values.put(_parameters, importedTypes_String_value);
      importedTypes_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return importedTypes_String_value;
  }
  /** @apilevel internal */
  private void importedTypes_reset() {
    importedTypes_computed = null;
    importedTypes_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle importedTypes_computed = null;

  /** @apilevel internal */
  protected SimpleSet<TypeDecl> importedTypes_value;

  /**
   * For a single-import declaration this will return a SimpleSet
   * containing the TypeDecl for the imported type. For dynamic
   * import declarations this returns the empty set.
   * @return TypeDecl of imported type wrapped in SimpleSet
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:496
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:496")
  public SimpleSet<TypeDecl> importedTypes() {
    ASTNode$State state = state();
    if (importedTypes_computed == ASTNode$State.NON_CYCLE || importedTypes_computed == state().cycle()) {
      return importedTypes_value;
    }
    importedTypes_value = emptySet();
    if (state().inCircle()) {
      importedTypes_computed = state().cycle();
    
    } else {
      importedTypes_computed = ASTNode$State.NON_CYCLE;
    
    }
    return importedTypes_value;
  }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:527
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:527")
  public boolean isOnDemand() {
    boolean isOnDemand_value = false;
    return isOnDemand_value;
  }
  /**
   * @attribute syn
   * @aspect Names
   * @declaredat /home/olivier/projects/extendj/java4/frontend/QualifiedNames.jrag:60
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Names", declaredAt="/home/olivier/projects/extendj/java4/frontend/QualifiedNames.jrag:60")
  public String typeName() {
    {
        Access a = getAccess().lastAccess();
        String name = a.isTypeAccess() ? ((TypeAccess) a).nameWithPackage() : "";
        while (a.hasPrevExpr() && a.prevExpr() instanceof Access) {
          Access pred = (Access) a.prevExpr();
          if (pred.isTypeAccess()) {
            name = ((TypeAccess) pred).nameWithPackage() + "." + name;
          }
          a = pred;
        }
        return name;
      }
  }
  /** @apilevel internal */
  private void importedFields_String_reset() {
    importedFields_String_values = null;
  }
  protected java.util.Map importedFields_String_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="StaticImports", declaredAt="/home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:55")
  public SimpleSet<Variable> importedFields(String name) {
    Object _parameters = name;
    if (importedFields_String_values == null) importedFields_String_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (importedFields_String_values.containsKey(_parameters)) {
      Object _cache = importedFields_String_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (SimpleSet<Variable>) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      importedFields_String_values.put(_parameters, _value);
      _value.value = emptySet();
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      SimpleSet<Variable> new_importedFields_String_value;
      do {
        _value.cycle = state.nextCycle();
        new_importedFields_String_value = emptySet();
        if ((new_importedFields_String_value == null && ((SimpleSet<Variable>)_value.value) != null) || (new_importedFields_String_value != null && !new_importedFields_String_value.equals(((SimpleSet<Variable>)_value.value)))) {
          state.setChangeInCycle();
          _value.value = new_importedFields_String_value;
        }
      } while (state.testAndClearChangeInCycle());
      importedFields_String_values.put(_parameters, new_importedFields_String_value);

      state.leaveCircle();
      return new_importedFields_String_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      SimpleSet<Variable> new_importedFields_String_value = emptySet();
      if ((new_importedFields_String_value == null && ((SimpleSet<Variable>)_value.value) != null) || (new_importedFields_String_value != null && !new_importedFields_String_value.equals(((SimpleSet<Variable>)_value.value)))) {
        state.setChangeInCycle();
        _value.value = new_importedFields_String_value;
      }
      return new_importedFields_String_value;
    } else {
      return (SimpleSet<Variable>) _value.value;
    }
  }
  /** @apilevel internal */
  private void importedMethods_String_reset() {
    importedMethods_String_computed = new java.util.HashMap(4);
    importedMethods_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map importedMethods_String_values;
  /** @apilevel internal */
  protected java.util.Map importedMethods_String_computed;
  /**
   * @attribute syn
   * @aspect StaticImports
   * @declaredat /home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:76
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StaticImports", declaredAt="/home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:76")
  public Collection<MethodDecl> importedMethods(String name) {
    Object _parameters = name;
    if (importedMethods_String_computed == null) importedMethods_String_computed = new java.util.HashMap(4);
    if (importedMethods_String_values == null) importedMethods_String_values = new java.util.HashMap(4);
    ASTNode$State state = state();
    if (importedMethods_String_values.containsKey(_parameters) && importedMethods_String_computed != null
        && importedMethods_String_computed.containsKey(_parameters)
        && (importedMethods_String_computed.get(_parameters) == ASTNode$State.NON_CYCLE || importedMethods_String_computed.get(_parameters) == state().cycle())) {
      return (Collection<MethodDecl>) importedMethods_String_values.get(_parameters);
    }
    Collection<MethodDecl> importedMethods_String_value = Collections.EMPTY_LIST;
    if (state().inCircle()) {
      importedMethods_String_values.put(_parameters, importedMethods_String_value);
      importedMethods_String_computed.put(_parameters, state().cycle());
    
    } else {
      importedMethods_String_values.put(_parameters, importedMethods_String_value);
      importedMethods_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return importedMethods_String_value;
  }
  /**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:525
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:525")
  public String packageName() {
    String packageName_value = getParent().Define_packageName(this, null);
    return packageName_value;
  }
  /**
   * @attribute inh
   * @aspect NameCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:50
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NameCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:50")
  public SimpleSet<TypeDecl> allImportedTypes(String name) {
    SimpleSet<TypeDecl> allImportedTypes_String_value = getParent().Define_allImportedTypes(this, null, name);
    return allImportedTypes_String_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:34
   * @apilevel internal
   */
  public boolean Define_isDest(ASTNode _callerNode, ASTNode _childNode) {
    if (getAccessNoTransform() != null && _callerNode == getAccess()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:42
      return false;
    }
    else {
      return getParent().Define_isDest(this, _callerNode);
    }
  }
  protected boolean canDefine_isDest(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:44
   * @apilevel internal
   */
  public boolean Define_isSource(ASTNode _callerNode, ASTNode _childNode) {
    if (getAccessNoTransform() != null && _callerNode == getAccess()) {
      // @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:51
      return true;
    }
    else {
      return getParent().Define_isSource(this, _callerNode);
    }
  }
  protected boolean canDefine_isSource(ASTNode _callerNode, ASTNode _childNode) {
    return true;
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
