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
 * 7.5 Import Declarations
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java5/grammar/StaticImports.ast:4
 * @production StaticImportDecl : {@link ImportDecl};

 */
public abstract class StaticImportDecl extends ImportDecl implements Cloneable {
  /**
   * @declaredat ASTNode:1
   */
  public StaticImportDecl() {
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
  public StaticImportDecl(Access p0) {
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
    importedFields_String_reset();
    importedMethods_String_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:34
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:38
   */
  public StaticImportDecl clone() throws CloneNotSupportedException {
    StaticImportDecl node = (StaticImportDecl) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:49
   */
  @Deprecated
  public abstract StaticImportDecl fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:57
   */
  public abstract StaticImportDecl treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:65
   */
  public abstract StaticImportDecl treeCopy();
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
  /**
   * @attribute syn
   * @aspect StaticImports
   * @declaredat /home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:98
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StaticImports", declaredAt="/home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:98")
  public abstract TypeDecl type();
  /** @apilevel internal */
  private void importedTypes_String_reset() {
    importedTypes_String_values = null;
  }
  protected java.util.Map importedTypes_String_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="StaticImports", declaredAt="/home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:42")
  public SimpleSet<TypeDecl> importedTypes(String name) {
    Object _parameters = name;
    if (importedTypes_String_values == null) importedTypes_String_values = new java.util.HashMap(4);
    ASTNode$State.CircularValue _value;
    if (importedTypes_String_values.containsKey(_parameters)) {
      Object _cache = importedTypes_String_values.get(_parameters);
      if (!(_cache instanceof ASTNode$State.CircularValue)) {
        return (SimpleSet<TypeDecl>) _cache;
      } else {
        _value = (ASTNode$State.CircularValue) _cache;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      importedTypes_String_values.put(_parameters, _value);
      _value.value = emptySet();
    }
    ASTNode$State state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      SimpleSet<TypeDecl> new_importedTypes_String_value;
      do {
        _value.cycle = state.nextCycle();
        new_importedTypes_String_value = importedTypes_compute(name);
        if ((new_importedTypes_String_value == null && ((SimpleSet<TypeDecl>)_value.value) != null) || (new_importedTypes_String_value != null && !new_importedTypes_String_value.equals(((SimpleSet<TypeDecl>)_value.value)))) {
          state.setChangeInCycle();
          _value.value = new_importedTypes_String_value;
        }
      } while (state.testAndClearChangeInCycle());
      importedTypes_String_values.put(_parameters, new_importedTypes_String_value);

      state.leaveCircle();
      return new_importedTypes_String_value;
    } else if (_value.cycle != state.cycle()) {
      _value.cycle = state.cycle();
      SimpleSet<TypeDecl> new_importedTypes_String_value = importedTypes_compute(name);
      if ((new_importedTypes_String_value == null && ((SimpleSet<TypeDecl>)_value.value) != null) || (new_importedTypes_String_value != null && !new_importedTypes_String_value.equals(((SimpleSet<TypeDecl>)_value.value)))) {
        state.setChangeInCycle();
        _value.value = new_importedTypes_String_value;
      }
      return new_importedTypes_String_value;
    } else {
      return (SimpleSet<TypeDecl>) _value.value;
    }
  }
  /** @apilevel internal */
  private SimpleSet<TypeDecl> importedTypes_compute(String name) {
      SimpleSet<TypeDecl> set = emptySet();
      for (TypeDecl type : type().memberTypes(name)) {
        if (type.isStatic() && type.accessibleFromPackage(packageName())) {
          set = set.add(type);
        }
      }
      return set;
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
        new_importedFields_String_value = importedFields_compute(name);
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
      SimpleSet<Variable> new_importedFields_String_value = importedFields_compute(name);
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
  private SimpleSet<Variable> importedFields_compute(String name) {
      SimpleSet<Variable> set = emptySet();
      for (Variable decl : type().memberFields(name)) {
        if (decl.isStatic() && (decl.isPublic() || (!decl.isPrivate()
            && decl.hostType().topLevelType().packageName().equals(packageName())))) {
          set = set.add(decl);
        }
      }
      return set;
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
    Collection<MethodDecl> importedMethods_String_value = importedMethods_compute(name);
    if (state().inCircle()) {
      importedMethods_String_values.put(_parameters, importedMethods_String_value);
      importedMethods_String_computed.put(_parameters, state().cycle());
    
    } else {
      importedMethods_String_values.put(_parameters, importedMethods_String_value);
      importedMethods_String_computed.put(_parameters, ASTNode$State.NON_CYCLE);
    
    }
    return importedMethods_String_value;
  }
  /** @apilevel internal */
  private Collection<MethodDecl> importedMethods_compute(String name) {
      Collection<MethodDecl> set = new HashSet<MethodDecl>();
      for (MethodDecl method : type().memberMethods(name)) {
        if (method.isStatic()
            && (method.isPublic() || (!method.isPrivate()
                && method.hostType().topLevelType().packageName().equals(packageName())))) {
          set.add(method);
        }
      }
      return set;
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
