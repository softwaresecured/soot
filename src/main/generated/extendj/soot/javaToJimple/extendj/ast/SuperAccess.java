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
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:97
 * @astdecl SuperAccess : Access ::= <ID:String>;
 * @production SuperAccess : {@link Access} ::= <span class="component">&lt;ID:String&gt;</span>;

 */
public class SuperAccess extends Access implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:570
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("super");
  }
  /**
   * @declaredat ASTNode:1
   */
  public SuperAccess() {
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
  }
  /**
   * @declaredat ASTNode:12
   */
  @ASTNodeAnnotation.Constructor(
    name = {"ID"},
    type = {"String"},
    kind = {"Token"}
  )
  public SuperAccess(String p0) {
    setID(p0);
  }
  /**
   * @declaredat ASTNode:20
   */
  public SuperAccess(beaver.Symbol p0) {
    setID(p0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:24
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:30
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:34
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    decl_reset();
    type_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:40
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:44
   */
  public SuperAccess clone() throws CloneNotSupportedException {
    SuperAccess node = (SuperAccess) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:49
   */
  public SuperAccess copy() {
    try {
      SuperAccess node = (SuperAccess) clone();
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
   * @declaredat ASTNode:68
   */
  @Deprecated
  public SuperAccess fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:78
   */
  public SuperAccess treeCopyNoTransform() {
    SuperAccess tree = (SuperAccess) copy();
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
   * @declaredat ASTNode:98
   */
  public SuperAccess treeCopy() {
    SuperAccess tree = (SuperAccess) copy();
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
   * @declaredat ASTNode:112
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((SuperAccess) node).tokenString_ID);    
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
  /**
   * @aspect TypeScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:383
   */
  private TypeDecl refined_TypeScopePropagation_SuperAccess_decl()
{ return isQualified() ? qualifier().type() : hostType(); }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:373
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:373")
  public SimpleSet<TypeDecl> decls() {
    SimpleSet<TypeDecl> decls_value = emptySet();
    return decls_value;
  }
  /** @apilevel internal */
  private void decl_reset() {
    decl_computed = null;
    decl_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle decl_computed = null;

  /** @apilevel internal */
  protected TypeDecl decl_value;

  /**
   * @return the type whose supertype this super access references.
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:383
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:383")
  public TypeDecl decl() {
    ASTState state = state();
    if (decl_computed == ASTState.NON_CYCLE || decl_computed == state().cycle()) {
      return decl_value;
    }
    decl_value = decl_compute();
    if (state().inCircle()) {
      decl_computed = state().cycle();
    
    } else {
      decl_computed = ASTState.NON_CYCLE;
    
    }
    return decl_value;
  }
  /** @apilevel internal */
  private TypeDecl decl_compute() {
      TypeDecl typeDecl;
      if (isQualified()) {
        typeDecl = qualifier().type();
      } else {
        typeDecl = hostType();
        while (typeDecl instanceof LambdaAnonymousDecl) {
          typeDecl = typeDecl.enclosingType();
        }
      }
  
      if (typeDecl instanceof ParTypeDecl) {
        typeDecl = ((ParTypeDecl) typeDecl).genericDecl();
      }
      return typeDecl;
    }
  /**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:56
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="AccessTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:56")
  public boolean isSuperAccess() {
    boolean isSuperAccess_value = true;
    return isSuperAccess_value;
  }
  /**
   * Defines the expected kind of name for the left hand side in a qualified
   * expression.
   * @attribute syn
   * @aspect SyntacticClassification
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:60
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SyntacticClassification", declaredAt="/home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:60")
  public NameType predNameType() {
    NameType predNameType_value = NameType.TYPE_NAME;
    return predNameType_value;
  }
  /** @apilevel internal */
  private void type_reset() {
    type_computed = null;
    type_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle type_computed = null;

  /** @apilevel internal */
  protected TypeDecl type_value;

  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:295
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:295")
  public TypeDecl type() {
    ASTState state = state();
    if (type_computed == ASTState.NON_CYCLE || type_computed == state().cycle()) {
      return type_value;
    }
    type_value = type_compute();
    if (state().inCircle()) {
      type_computed = state().cycle();
    
    } else {
      type_computed = ASTState.NON_CYCLE;
    
    }
    return type_value;
  }
  /** @apilevel internal */
  private TypeDecl type_compute() {
      TypeDecl typeDecl = decl();
      if (typeDecl.isInterfaceDecl()) {
        if (isQualified() && qualifier().type() == typeDecl) {
          return typeDecl;
        }
      }
      if (!typeDecl.isClassDecl()) {
        return unknownType();
      }
      ClassDecl classDecl = (ClassDecl) typeDecl;
      if (!classDecl.hasSuperclass()) {
        return unknownType();
      }
      return classDecl.superclass();
    }
  /**
   * @attribute syn
   * @aspect TypeHierarchyCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:151
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:151")
  public Collection<Problem> typeHierarchyProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        if (isQualified()) {
          if (decl().isInterfaceDecl()) {
            InterfaceDecl decl = (InterfaceDecl) decl();
            if (hostType().isClassDecl()) {
              ClassDecl hostDecl = (ClassDecl) hostType();
              InterfaceDecl found = null;
              for (int i = 0; i < hostDecl.getNumImplements(); i++) {
                if (hostDecl.getImplements(i).type() == decl) {
                  found = (InterfaceDecl) hostDecl.getImplements(i).type();
                  break;
                }
              }
              if (found == null) {
                // 15.12.1 - fourth bullet
                problems.add(errorf("Type %s is not a direct superinterface of %s",
                    decl().typeName(), hostType().typeName()));
                return problems;
              }
              InterfaceDecl foundRedundant = null;
              for (int i = 0; i < hostDecl.getNumImplements(); i++) {
                if (hostDecl.getImplements(i).type() != found && hostDecl.getImplements(i).type().strictSubtype(found)) {
                  foundRedundant = (InterfaceDecl) hostDecl.getImplements(i).type();
                  break;
                }
              }
              if (foundRedundant != null) {
                // 15.12.1 - fourth bullet
                problems.add(errorf("Type %s cannot be used as qualifier, it is extended by implemented interface %s and is redundant",
                    decl().typeName(), foundRedundant.typeName()));
                return problems;
              }
              if (hasNextAccess() && nextAccess() instanceof MethodAccess) {
                MethodAccess methodAccess = (MethodAccess) nextAccess();
                if (hostDecl.hasOverridingMethodInSuper(methodAccess.decl())) {
                  problems.add(errorf("Cannot make a super reference to method %s,"
                      + " there is a more specific override",
                      methodAccess.decl().fullSignature()));
                }
              }
            } else if (hostType().isInterfaceDecl()) {
              InterfaceDecl hostDecl = (InterfaceDecl) hostType();
              InterfaceDecl found = null;
              for (int i = 0; i < hostDecl.getNumSuperInterface(); i++) {
                if (hostDecl.getSuperInterface(i).type() == decl) {
                  found = (InterfaceDecl) hostDecl.getSuperInterface(i).type();
                  break;
                }
              }
              if (found == null) {
                // 15.12.1 - fourth bullet
                problems.add(errorf("Type %s is not a direct superinterface of %s",
                    decl().typeName(), hostType().typeName()));
                return problems;
              }
              InterfaceDecl foundRedundant = null;
              for (int i = 0; i < hostDecl.getNumSuperInterface(); i++) {
                if (hostDecl.getSuperInterface(i).type() != found && hostDecl.getSuperInterface(i).type().strictSubtype(found)) {
                  foundRedundant = (InterfaceDecl) hostDecl.getSuperInterface(i).type();
                  break;
                }
              }
              if (foundRedundant != null) {
                // 15.12.1 - fourth bullet
                problems.add(errorf("Type %s cannot be used as qualifier, it is extended by"
                    + " implemented interface %s and is redundant",
                    decl().typeName(), foundRedundant.typeName()));
                return problems;
              }
              if (hasNextAccess() && nextAccess() instanceof MethodAccess) {
                MethodAccess methodAccess = (MethodAccess) nextAccess();
                if (hostDecl.hasOverridingMethodInSuper(methodAccess.decl())) {
                  problems.add(errorf("Cannot make a super reference to method %s,"
                      + " there is a more specific override",
                      methodAccess.decl().fullSignature()));
                }
              }
            } else {
              problems.add(error("Illegal context for super access"));
            }
    
            if (hasNextAccess() && nextAccess() instanceof MethodAccess) {
              if (((MethodAccess) nextAccess()).decl().isStatic()) {
                problems.add(error("Cannot reference static interface methods with super"));
              }
            }
    
            if (!hostType().strictSubtype(decl())) {
              problems.add(errorf("Type %s is not a superinterface for %s",
                  decl().typeName(), hostType().typeName()));
            }
          } else if (!hostType().isInnerTypeOf(decl()) && hostType() != decl()) {
            problems.add(error("qualified super must name an enclosing type"));
          }
          if (inStaticContext()) {
            problems.add(error("*** Qualified super may not occur in static context"));
          }
        }
        // 8.8.5.1
        // JLSv7 8.8.7.1
        TypeDecl constructorHostType = enclosingExplicitConstructorHostType();
        if (constructorHostType != null && (constructorHostType == decl())) {
          problems.add(error("super may not be accessed in an explicit constructor invocation"));
        }
        // 8.4.3.2
        if (inStaticContext()) {
          problems.add(error("super may not be accessed in a static context"));
        }
        return problems;
      }
  }
  /** @return the host type that needs an implicit super accessor for this super access. 
   * @attribute syn
   * @aspect InnerClasses
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:145
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="InnerClasses", declaredAt="/home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:145")
  public TypeDecl superAccessorTarget() {
    {
        TypeDecl targetDecl = type();
        TypeDecl enclosing = hostType();
        do {
          enclosing = enclosing.enclosingType();
        } while (!enclosing.instanceOf(targetDecl));
        return enclosing;
      }
  }
  /**
   * @attribute syn
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:397
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Expressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:397")
  public Local eval(Body b) {
    Local eval_Body_value = emitThis(b, decl().isInterfaceDecl() ? hostType() :decl());
    return eval_Body_value;
  }
  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:201
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:201")
  public boolean inExplicitConstructorInvocation() {
    boolean inExplicitConstructorInvocation_value = getParent().Define_inExplicitConstructorInvocation(this, null);
    return inExplicitConstructorInvocation_value;
  }
  /**
   * @attribute inh
   * @aspect TypeHierarchyCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:212
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeHierarchyCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:212")
  public TypeDecl enclosingExplicitConstructorHostType() {
    TypeDecl enclosingExplicitConstructorHostType_value = getParent().Define_enclosingExplicitConstructorHostType(this, null);
    return enclosingExplicitConstructorHostType_value;
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
    // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:149
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
    for (Problem value : typeHierarchyProblems()) {
      collection.add(value);
    }
  }
}
