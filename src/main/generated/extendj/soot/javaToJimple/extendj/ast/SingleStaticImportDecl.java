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
 * 7.5.3 A single-static-import declaration imports all accessible (\u00a76.6) static members
 * with a given simple name from a type. This makes these static members available
 * under their simple name in the class and interface declarations of the
 * compilation unit in which the single-static import declaration appears.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java5/grammar/StaticImports.ast:12
 * @astdecl SingleStaticImportDecl : StaticImportDecl ::= <ID:String>;
 * @production SingleStaticImportDecl : {@link StaticImportDecl} ::= <span class="component">&lt;ID:String&gt;</span>;

 */
public class SingleStaticImportDecl extends StaticImportDecl implements Cloneable {
  /**
   * @aspect Java5PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java5/frontend/PrettyPrint.jadd:343
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("import static ");
    out.print(getAccess());
    out.print(".");
    out.print(getID());
    out.print(";");
  }
  /**
   * @declaredat ASTNode:1
   */
  public SingleStaticImportDecl() {
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
    name = {"Access", "ID"},
    type = {"Access", "String"},
    kind = {"Child", "Token"}
  )
  public SingleStaticImportDecl(Access p0, String p1) {
    setChild(p0, 0);
    setID(p1);
  }
  /**
   * @declaredat ASTNode:22
   */
  public SingleStaticImportDecl(Access p0, beaver.Symbol p1) {
    setChild(p0, 0);
    setID(p1);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:27
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:33
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:37
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    importedTypes_reset();
    importedFields_String_reset();
    importedMethods_String_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:44
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:48
   */
  public SingleStaticImportDecl clone() throws CloneNotSupportedException {
    SingleStaticImportDecl node = (SingleStaticImportDecl) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:53
   */
  public SingleStaticImportDecl copy() {
    try {
      SingleStaticImportDecl node = (SingleStaticImportDecl) clone();
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
   * @declaredat ASTNode:72
   */
  @Deprecated
  public SingleStaticImportDecl fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:82
   */
  public SingleStaticImportDecl treeCopyNoTransform() {
    SingleStaticImportDecl tree = (SingleStaticImportDecl) copy();
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
   * @declaredat ASTNode:102
   */
  public SingleStaticImportDecl treeCopy() {
    SingleStaticImportDecl tree = (SingleStaticImportDecl) copy();
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
   * @declaredat ASTNode:116
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((SingleStaticImportDecl) node).tokenString_ID);    
  }
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
/** @apilevel internal */
protected ASTState.Cycle importedTypes_cycle = null;
  /** @apilevel internal */
  private void importedTypes_reset() {
    importedTypes_computed = false;
    importedTypes_initialized = false;
    importedTypes_value = null;
    importedTypes_cycle = null;
  }
  /** @apilevel internal */
  protected boolean importedTypes_computed = false;

  /** @apilevel internal */
  protected SimpleSet<TypeDecl> importedTypes_value;
  /** @apilevel internal */
  protected boolean importedTypes_initialized = false;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="StaticImports", declaredAt="/home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:52")
  public SimpleSet<TypeDecl> importedTypes() {
    if (importedTypes_computed) {
      return importedTypes_value;
    }
    ASTState state = state();
    if (!importedTypes_initialized) {
      importedTypes_initialized = true;
      importedTypes_value = emptySet();
    }
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      do {
        importedTypes_cycle = state.nextCycle();
        SimpleSet<TypeDecl> new_importedTypes_value = getAccess().type().memberTypes(getID());
        if (!AttributeValue.equals(importedTypes_value, new_importedTypes_value)) {
          state.setChangeInCycle();
        }
        importedTypes_value = new_importedTypes_value;
      } while (state.testAndClearChangeInCycle());
      importedTypes_computed = true;

      state.leaveCircle();
    } else if (importedTypes_cycle != state.cycle()) {
      importedTypes_cycle = state.cycle();
      SimpleSet<TypeDecl> new_importedTypes_value = getAccess().type().memberTypes(getID());
      if (!AttributeValue.equals(importedTypes_value, new_importedTypes_value)) {
        state.setChangeInCycle();
      }
      importedTypes_value = new_importedTypes_value;
    } else {
    }
    return importedTypes_value;
  }
  /**
   * @attribute syn
   * @aspect StaticImports
   * @declaredat /home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:55
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StaticImports", declaredAt="/home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:55")
  public SimpleSet<TypeDecl> importedTypes(String name) {
    {
        if (name.equals(getID())) {
          return importedTypes();
        } else {
          return emptySet();
        }
      }
  }
  /** @apilevel internal */
  private void importedFields_String_reset() {
    importedFields_String_values = null;
  }
  protected java.util.Map importedFields_String_values;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true)
  @ASTNodeAnnotation.Source(aspect="StaticImports", declaredAt="/home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:63")
  public SimpleSet<Variable> importedFields(String name) {
    Object _parameters = name;
    if (importedFields_String_values == null) importedFields_String_values = new java.util.HashMap(4);
    ASTState.CircularValue _value;
    if (importedFields_String_values.containsKey(_parameters)) {
      Object _cache = importedFields_String_values.get(_parameters);
      if (!(_cache instanceof ASTState.CircularValue)) {
        return (SimpleSet<Variable>) _cache;
      } else {
        _value = (ASTState.CircularValue) _cache;
      }
    } else {
      _value = new ASTState.CircularValue();
      importedFields_String_values.put(_parameters, _value);
      _value.value = emptySet();
    }
    ASTState state = state();
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      SimpleSet<Variable> new_importedFields_String_value;
      do {
        _value.cycle = state.nextCycle();
        new_importedFields_String_value = importedFields_compute(name);
        if (!AttributeValue.equals(((SimpleSet<Variable>)_value.value), new_importedFields_String_value)) {
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
      if (!AttributeValue.equals(((SimpleSet<Variable>)_value.value), new_importedFields_String_value)) {
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
      if (name.equals(getID())) {
        return super.importedFields(name);
      } else {
        return emptySet();
      }
    }
  /** @apilevel internal */
  private void importedMethods_String_reset() {
    importedMethods_String_computed = null;
    importedMethods_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map importedMethods_String_values;
  /** @apilevel internal */
  protected java.util.Map importedMethods_String_computed;
  /**
   * @attribute syn
   * @aspect StaticImports
   * @declaredat /home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:84
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StaticImports", declaredAt="/home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:84")
  public Collection<MethodDecl> importedMethods(String name) {
    Object _parameters = name;
    if (importedMethods_String_computed == null) importedMethods_String_computed = new java.util.HashMap(4);
    if (importedMethods_String_values == null) importedMethods_String_values = new java.util.HashMap(4);
    ASTState state = state();
    if (importedMethods_String_values.containsKey(_parameters)
        && importedMethods_String_computed.containsKey(_parameters)
        && (importedMethods_String_computed.get(_parameters) == ASTState.NON_CYCLE || importedMethods_String_computed.get(_parameters) == state().cycle())) {
      return (Collection<MethodDecl>) importedMethods_String_values.get(_parameters);
    }
    Collection<MethodDecl> importedMethods_String_value = importedMethods_compute(name);
    if (state().inCircle()) {
      importedMethods_String_values.put(_parameters, importedMethods_String_value);
      importedMethods_String_computed.put(_parameters, state().cycle());
    
    } else {
      importedMethods_String_values.put(_parameters, importedMethods_String_value);
      importedMethods_String_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return importedMethods_String_value;
  }
  /** @apilevel internal */
  private Collection<MethodDecl> importedMethods_compute(String name) {
      if (name.equals(getID())) {
        return super.importedMethods(name);
      } else {
        return Collections.EMPTY_LIST;
      }
    }
  /**
   * @attribute syn
   * @aspect StaticImports
   * @declaredat /home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:106
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StaticImports", declaredAt="/home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:106")
  public TypeDecl type() {
    TypeDecl type_value = getAccess().type();
    return type_value;
  }
  /**
   * The TypeName must be the canonical name of a class or interface type
   * @attribute syn
   * @aspect StaticImports
   * @declaredat /home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:120
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StaticImports", declaredAt="/home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:120")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        if (!getAccess().type().typeName().equals(typeName())
            && !getAccess().type().isUnknown()) {
          problems.add(errorf("Single-type import %s is not the canonical name of type %s",
              typeName(), getAccess().type().typeName()));
        } else {
          if (allImportedTypes(getID()).size() > 1) {
            problems.add(errorf("%s is imported multiple times", getID()));
          }
          if (importedTypes().isSingleton()) {
            TypeDecl type = importedTypes().singletonValue();
            if (type.isPrivate()
                || !(type.isPublic() || type.hostPackage().equals(hostPackage()))) {
              problems.add(errorf("can not access non-public type %s", type.typeName()));
            } else if (!type.isStatic()) {
              problems.add(errorf("can not static-import non-static type %s", type.typeName()));
            }
          }
        }
        return problems;
      }
  }
  /**
   * @attribute syn
   * @aspect StaticImports
   * @declaredat /home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:193
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="StaticImports", declaredAt="/home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:193")
  public String name() {
    String name_value = getID();
    return name_value;
  }
  /**
   * @attribute inh
   * @aspect StaticImports
   * @declaredat /home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:115
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="StaticImports", declaredAt="/home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:115")
  public String hostPackage() {
    String hostPackage_value = getParent().Define_hostPackage(this, null);
    return hostPackage_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    if (getAccessNoTransform() != null && _callerNode == getAccess()) {
      // @declaredat /home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:323
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
    // @declaredat /home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:113
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat /home/olivier/projects/extendj/java5/frontend/StaticImports.jrag:183
    if (importedFields(name()).isEmpty()
              && importedMethods(name()).isEmpty()
              && importedTypes(name()).isEmpty()
              && !getAccess().type().isUnknown()) {
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
    for (Problem value : typeProblems()) {
      collection.add(value);
    }
    if (importedFields(name()).isEmpty()
              && importedMethods(name()).isEmpty()
              && importedTypes(name()).isEmpty()
              && !getAccess().type().isUnknown()) {
      collection.add(errorf("Semantic Error: At least one static member named %s must"
                + " be available in static imported type %s",
                name(), type().fullName()));
    }
  }
}
