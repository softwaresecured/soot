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
 * @declaredat /home/olivier/projects/extendj/java8/grammar/Lambda.ast:6
 * @production InferredParameterDeclaration : {@link ASTNode} ::= <span class="component">&lt;ID:String&gt;</span>;

 */
public class InferredParameterDeclaration extends ASTNode<ASTNode> implements Cloneable, SimpleSet<Variable>, Variable {
  /**
   * @aspect DataStructures
   * @declaredat /home/olivier/projects/extendj/java8/frontend/DataStructures.jrag:34
   */
  @Override
  public int size() {
    return 1;
  }
  /**
   * @aspect DataStructures
   * @declaredat /home/olivier/projects/extendj/java8/frontend/DataStructures.jrag:39
   */
  @Override
  public boolean isEmpty() {
    return false;
  }
  /**
   * @aspect DataStructures
   * @declaredat /home/olivier/projects/extendj/java8/frontend/DataStructures.jrag:44
   */
  public SimpleSet<Variable> add(Variable o) {
    return new SimpleSetImpl<Variable>(this, o);
  }
  /**
   * @aspect DataStructures
   * @declaredat /home/olivier/projects/extendj/java8/frontend/DataStructures.jrag:48
   */
  @Override
  public boolean contains(Object o) {
    return this == o;
  }
  /**
   * @aspect DataStructures
   * @declaredat /home/olivier/projects/extendj/java8/frontend/DataStructures.jrag:53
   */
  @Override
  public boolean isSingleton() {
    return true;
  }
  /**
   * @aspect DataStructures
   * @declaredat /home/olivier/projects/extendj/java8/frontend/DataStructures.jrag:58
   */
  @Override
  public boolean isSingleton(Variable o) {
    return contains(o);
  }
  /**
   * @aspect DataStructures
   * @declaredat /home/olivier/projects/extendj/java8/frontend/DataStructures.jrag:63
   */
  @Override
  public Variable singletonValue() {
    return this;
  }
  /**
   * @aspect DataStructures
   * @declaredat /home/olivier/projects/extendj/java8/frontend/DataStructures.jrag:68
   */
  public Iterator<Variable> iterator() {
    return new SingleItemIterator(this);
  }
  /**
   * @aspect Java8PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java8/frontend/PrettyPrint.jadd:95
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(name());
  }
  /**
   * @declaredat ASTNode:1
   */
  public InferredParameterDeclaration() {
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
  public InferredParameterDeclaration(String p0) {
    setID(p0);
  }
  /**
   * @declaredat ASTNode:15
   */
  public InferredParameterDeclaration(beaver.Symbol p0) {
    setID(p0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:19
   */
  protected int numChildren() {
    return 0;
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
    isEffectivelyFinal_reset();
    enclosingLambda_reset();
    lookupVariable_String_reset();
    inferredType_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:37
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:41
   */
  public InferredParameterDeclaration clone() throws CloneNotSupportedException {
    InferredParameterDeclaration node = (InferredParameterDeclaration) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:46
   */
  public InferredParameterDeclaration copy() {
    try {
      InferredParameterDeclaration node = (InferredParameterDeclaration) clone();
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
   * @declaredat ASTNode:65
   */
  @Deprecated
  public InferredParameterDeclaration fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:75
   */
  public InferredParameterDeclaration treeCopyNoTransform() {
    InferredParameterDeclaration tree = (InferredParameterDeclaration) copy();
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
   * @declaredat ASTNode:95
   */
  public InferredParameterDeclaration treeCopy() {
    InferredParameterDeclaration tree = (InferredParameterDeclaration) copy();
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
   * @declaredat ASTNode:109
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((InferredParameterDeclaration) node).tokenString_ID);    
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
  private void isEffectivelyFinal_reset() {
    isEffectivelyFinal_computed = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle isEffectivelyFinal_computed = null;

  /** @apilevel internal */
  protected boolean isEffectivelyFinal_value;

  /**
   * @attribute syn
   * @aspect EffectivelyFinal
   * @declaredat /home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:136
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EffectivelyFinal", declaredAt="/home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:136")
  public boolean isEffectivelyFinal() {
    ASTNode$State state = state();
    if (isEffectivelyFinal_computed == ASTNode$State.NON_CYCLE || isEffectivelyFinal_computed == state().cycle()) {
      return isEffectivelyFinal_value;
    }
    isEffectivelyFinal_value = isFinal() || !inhModifiedInScope(this);
    if (state().inCircle()) {
      isEffectivelyFinal_computed = state().cycle();
    
    } else {
      isEffectivelyFinal_computed = ASTNode$State.NON_CYCLE;
    
    }
    return isEffectivelyFinal_value;
  }
  /**
   * @attribute syn
   * @aspect Java8NameCheck
   * @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:41
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java8NameCheck", declaredAt="/home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:41")
  public Collection<Problem> nameProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        SimpleSet<Variable> decls = outerScope().lookupVariable(name());
        for (Variable var : decls) {
          if (var instanceof VariableDeclarator) {
            VariableDeclarator decl = (VariableDeclarator) var;
            if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
              problems.add(errorf("duplicate declaration of parameter %s", name()));
            }
          } else if (var instanceof ParameterDeclaration) {
            ParameterDeclaration decl = (ParameterDeclaration) var;
            if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
              problems.add(errorf("duplicate declaration of parameter %s", name()));
            }
          } else if (var instanceof InferredParameterDeclaration) {
            InferredParameterDeclaration decl = (InferredParameterDeclaration) var;
            if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
              problems.add(errorf("duplicate declaration of parameter %s", name()));
            }
          } else if (var instanceof CatchParameterDeclaration) {
            CatchParameterDeclaration decl = (CatchParameterDeclaration) var;
            if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
              problems.add(errorf("duplicate declaration of parameter %s", name()));
            }
          }
        }
    
        // 8.4.1
        if (!lookupVariable(name()).contains(this)) {
          problems.add(errorf("duplicate declaration of parameter %s", name()));
        }
        return problems;
      }
  }
  /**
   * @attribute syn
   * @aspect Names
   * @declaredat /home/olivier/projects/extendj/java8/frontend/QualifiedNames.jrag:29
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Names", declaredAt="/home/olivier/projects/extendj/java8/frontend/QualifiedNames.jrag:29")
  public String name() {
    String name_value = getID();
    return name_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:30
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:30")
  public boolean isParameter() {
    boolean isParameter_value = true;
    return isParameter_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:31
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:31")
  public boolean isClassVariable() {
    boolean isClassVariable_value = false;
    return isClassVariable_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:32
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:32")
  public boolean isInstanceVariable() {
    boolean isInstanceVariable_value = false;
    return isInstanceVariable_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:33
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:33")
  public boolean isConstructorParameter() {
    boolean isConstructorParameter_value = false;
    return isConstructorParameter_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:34
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:34")
  public boolean isExceptionHandlerParameter() {
    boolean isExceptionHandlerParameter_value = false;
    return isExceptionHandlerParameter_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:35
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:35")
  public boolean isMethodParameter() {
    boolean isMethodParameter_value = false;
    return isMethodParameter_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:36
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:36")
  public boolean isLocalVariable() {
    boolean isLocalVariable_value = false;
    return isLocalVariable_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:37
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:37")
  public boolean isField() {
    boolean isField_value = false;
    return isField_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:38
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:38")
  public boolean isFinal() {
    boolean isFinal_value = false;
    return isFinal_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:39
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:39")
  public boolean isVolatile() {
    boolean isVolatile_value = false;
    return isVolatile_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:40
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:40")
  public boolean isBlank() {
    boolean isBlank_value = true;
    return isBlank_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:41
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:41")
  public boolean isStatic() {
    boolean isStatic_value = false;
    return isStatic_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:42
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:42")
  public boolean isSynthetic() {
    boolean isSynthetic_value = false;
    return isSynthetic_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:44
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:44")
  public Modifiers getModifiers() {
    Modifiers getModifiers_value = null;
    return getModifiers_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:46
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:46")
  public boolean hasInit() {
    boolean hasInit_value = false;
    return hasInit_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:48
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:48")
  public boolean isConstant() {
    boolean isConstant_value = false;
    return isConstant_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:50
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:50")
  public boolean isPublic() {
    boolean isPublic_value = false;
    return isPublic_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:52
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:52")
  public boolean accessibleFrom(TypeDecl type) {
    boolean accessibleFrom_TypeDecl_value = false;
    return accessibleFrom_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:54
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:54")
  public Expr getInit() {
    {
        throw new UnsupportedOperationException();
      }
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:58
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:58")
  public Constant constant() {
    {
        throw new UnsupportedOperationException();
      }
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:62
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:62")
  public Collection<TypeDecl> throwTypes() {
    Collection<TypeDecl> throwTypes_value = null;
    return throwTypes_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:75
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:75")
  public TypeDecl hostType() {
    TypeDecl hostType_value = enclosingLambdaType();
    return hostType_value;
  }
  /**
   * @attribute syn
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:81
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:81")
  public TypeDecl type() {
    TypeDecl type_value = inferredType();
    return type_value;
  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:280
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="/home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:280")
  public boolean isProtected() {
    boolean isProtected_value = getModifiers().isProtected();
    return isProtected_value;
  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:282
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="/home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:282")
  public boolean isPrivate() {
    boolean isPrivate_value = getModifiers().isPrivate();
    return isPrivate_value;
  }
  /**
   * @attribute syn
   * @aspect EnclosingCapture
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/ScopeCapture.jrag:84
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EnclosingCapture", declaredAt="/home/olivier/projects/extendj/jimple8/backend/ScopeCapture.jrag:84")
  public String capturedParamName() {
    String capturedParamName_value = "val$" + name();
    return capturedParamName_value;
  }
  /**
   * @attribute inh
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:30
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:30")
  public boolean inhModifiedInScope(Variable var) {
    boolean inhModifiedInScope_Variable_value = getParent().Define_inhModifiedInScope(this, null, var);
    return inhModifiedInScope_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect EnclosingLambda
   * @declaredat /home/olivier/projects/extendj/java8/frontend/EnclosingLambda.jrag:35
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="EnclosingLambda", declaredAt="/home/olivier/projects/extendj/java8/frontend/EnclosingLambda.jrag:35")
  public LambdaExpr enclosingLambda() {
    ASTNode$State state = state();
    if (enclosingLambda_computed == ASTNode$State.NON_CYCLE || enclosingLambda_computed == state().cycle()) {
      return enclosingLambda_value;
    }
    enclosingLambda_value = getParent().Define_enclosingLambda(this, null);
    if (state().inCircle()) {
      enclosingLambda_computed = state().cycle();
    
    } else {
      enclosingLambda_computed = ASTNode$State.NON_CYCLE;
    
    }
    return enclosingLambda_value;
  }
  /** @apilevel internal */
  private void enclosingLambda_reset() {
    enclosingLambda_computed = null;
    enclosingLambda_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle enclosingLambda_computed = null;

  /** @apilevel internal */
  protected LambdaExpr enclosingLambda_value;

  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LookupVariable.jrag:31
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="VariableScope", declaredAt="/home/olivier/projects/extendj/java8/frontend/LookupVariable.jrag:31")
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
   * @aspect Java8NameCheck
   * @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:30
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Java8NameCheck", declaredAt="/home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:30")
  public BodyDecl enclosingBodyDecl() {
    BodyDecl enclosingBodyDecl_value = getParent().Define_enclosingBodyDecl(this, null);
    return enclosingBodyDecl_value;
  }
  /**
   * @attribute inh
   * @aspect Java8NameCheck
   * @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:31
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Java8NameCheck", declaredAt="/home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:31")
  public VariableScope outerScope() {
    VariableScope outerScope_value = getParent().Define_outerScope(this, null);
    return outerScope_value;
  }
  /**
   * @attribute inh
   * @aspect TypeCheck
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TypeCheck.jrag:31
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="/home/olivier/projects/extendj/java8/frontend/TypeCheck.jrag:31")
  public TypeDecl unknownType() {
    TypeDecl unknownType_value = getParent().Define_unknownType(this, null);
    return unknownType_value;
  }
  /**
   * @attribute inh
   * @aspect LambdaParametersInference
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TypeCheck.jrag:502
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LambdaParametersInference", declaredAt="/home/olivier/projects/extendj/java8/frontend/TypeCheck.jrag:502")
  public TypeDecl inferredType() {
    ASTNode$State state = state();
    if (inferredType_computed == ASTNode$State.NON_CYCLE || inferredType_computed == state().cycle()) {
      return inferredType_value;
    }
    inferredType_value = getParent().Define_inferredType(this, null);
    if (state().inCircle()) {
      inferredType_computed = state().cycle();
    
    } else {
      inferredType_computed = ASTNode$State.NON_CYCLE;
    
    }
    return inferredType_value;
  }
  /** @apilevel internal */
  private void inferredType_reset() {
    inferredType_computed = null;
    inferredType_value = null;
  }
  /** @apilevel internal */
  protected ASTNode$State.Cycle inferredType_computed = null;

  /** @apilevel internal */
  protected TypeDecl inferredType_value;

  /**
   * @attribute inh
   * @aspect Variables
   * @declaredat /home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:77
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="Variables", declaredAt="/home/olivier/projects/extendj/java8/frontend/VariableDeclaration.jrag:77")
  public TypeDecl enclosingLambdaType() {
    TypeDecl enclosingLambdaType_value = getParent().Define_enclosingLambdaType(this, null);
    return enclosingLambdaType_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:641
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:641")
  public String hostPackage() {
    String hostPackage_value = getParent().Define_hostPackage(this, null);
    return hostPackage_value;
  }
  /**
   * @attribute inh
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1385
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:1385")
  public FieldDecl fieldDecl() {
    FieldDecl fieldDecl_value = getParent().Define_fieldDecl(this, null);
    return fieldDecl_value;
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
    // @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:39
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
