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
 * A catch parameter with disjunct exception type.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java7/grammar/MultiCatch.ast:19
 * @astdecl CatchParameterDeclaration : ASTNode ::= Modifiers TypeAccess:Access* <ID:String>;
 * @production CatchParameterDeclaration : {@link ASTNode} ::= <span class="component">{@link Modifiers}</span> <span class="component">TypeAccess:{@link Access}*</span> <span class="component">&lt;ID:String&gt;</span>;

 */
public class CatchParameterDeclaration extends ASTNode<ASTNode> implements Cloneable, Variable, SimpleSet<Variable> {
  /**
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:84
   */
  @Override
  public int size() {
    return 1;
  }
  /**
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:89
   */
  @Override
  public boolean isEmpty() {
    return false;
  }
  /**
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:94
   */
  @Override
  public SimpleSet<Variable> add(Variable o) {
    return new SimpleSetImpl<Variable>(this, o);
  }
  /**
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:99
   */
  @Override
  public boolean contains(Object o) {
    return this == o;
  }
  /**
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:104
   */
  @Override
  public boolean isSingleton() {
    return true;
  }
  /**
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:109
   */
  @Override
  public boolean isSingleton(Variable o) {
    return contains(o);
  }
  /**
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:114
   */
  @Override
  public Variable singletonValue() {
    return this;
  }
  /**
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:119
   */
  @Override
  public Iterator<Variable> iterator() {
    return new SingleItemIterator(this);
  }
  /**
   * @aspect Java7PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PrettyPrint.jadd:35
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getModifiers());
    out.join(getTypeAccessList(), new PrettyPrinter.Joiner() {
      @Override
      public void printSeparator(PrettyPrinter out) {
        out.print(" | ");
      }
    });
    out.print(" ");
    out.print(getID());
  }
  /**
   * @declaredat ASTNode:1
   */
  public CatchParameterDeclaration() {
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
  @ASTNodeAnnotation.Constructor(
    name = {"Modifiers", "TypeAccess", "ID"},
    type = {"Modifiers", "List<Access>", "String"},
    kind = {"Child", "List", "Token"}
  )
  public CatchParameterDeclaration(Modifiers p0, List<Access> p1, String p2) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
  }
  /**
   * @declaredat ASTNode:24
   */
  public CatchParameterDeclaration(Modifiers p0, List<Access> p1, beaver.Symbol p2) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:30
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:36
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:40
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    throwTypes_reset();
    isEffectivelyFinal_reset();
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
  public CatchParameterDeclaration clone() throws CloneNotSupportedException {
    CatchParameterDeclaration node = (CatchParameterDeclaration) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:55
   */
  public CatchParameterDeclaration copy() {
    try {
      CatchParameterDeclaration node = (CatchParameterDeclaration) clone();
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
  public CatchParameterDeclaration fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:84
   */
  public CatchParameterDeclaration treeCopyNoTransform() {
    CatchParameterDeclaration tree = (CatchParameterDeclaration) copy();
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
   * @declaredat ASTNode:104
   */
  public CatchParameterDeclaration treeCopy() {
    CatchParameterDeclaration tree = (CatchParameterDeclaration) copy();
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
   * @declaredat ASTNode:118
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((CatchParameterDeclaration) node).tokenString_ID);    
  }
  /**
   * Replaces the Modifiers child.
   * @param node The new node to replace the Modifiers child.
   * @apilevel high-level
   */
  public void setModifiers(Modifiers node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Modifiers child.
   * @return The current node used as the Modifiers child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Modifiers")
  public Modifiers getModifiers() {
    return (Modifiers) getChild(0);
  }
  /**
   * Retrieves the Modifiers child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Modifiers child.
   * @apilevel low-level
   */
  public Modifiers getModifiersNoTransform() {
    return (Modifiers) getChildNoTransform(0);
  }
  /**
   * Replaces the TypeAccess list.
   * @param list The new list node to be used as the TypeAccess list.
   * @apilevel high-level
   */
  public void setTypeAccessList(List<Access> list) {
    setChild(list, 1);
  }
  /**
   * Retrieves the number of children in the TypeAccess list.
   * @return Number of children in the TypeAccess list.
   * @apilevel high-level
   */
  public int getNumTypeAccess() {
    return getTypeAccessList().getNumChild();
  }
  /**
   * Retrieves the number of children in the TypeAccess list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the TypeAccess list.
   * @apilevel low-level
   */
  public int getNumTypeAccessNoTransform() {
    return getTypeAccessListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the TypeAccess list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the TypeAccess list.
   * @apilevel high-level
   */
  public Access getTypeAccess(int i) {
    return (Access) getTypeAccessList().getChild(i);
  }
  /**
   * Check whether the TypeAccess list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasTypeAccess() {
    return getTypeAccessList().getNumChild() != 0;
  }
  /**
   * Append an element to the TypeAccess list.
   * @param node The element to append to the TypeAccess list.
   * @apilevel high-level
   */
  public void addTypeAccess(Access node) {
    List<Access> list = (parent == null) ? getTypeAccessListNoTransform() : getTypeAccessList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addTypeAccessNoTransform(Access node) {
    List<Access> list = getTypeAccessListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the TypeAccess list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setTypeAccess(Access node, int i) {
    List<Access> list = getTypeAccessList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the TypeAccess list.
   * @return The node representing the TypeAccess list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="TypeAccess")
  public List<Access> getTypeAccessList() {
    List<Access> list = (List<Access>) getChild(1);
    return list;
  }
  /**
   * Retrieves the TypeAccess list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeAccess list.
   * @apilevel low-level
   */
  public List<Access> getTypeAccessListNoTransform() {
    return (List<Access>) getChildNoTransform(1);
  }
  /**
   * @return the element at index {@code i} in the TypeAccess list without
   * triggering rewrites.
   */
  public Access getTypeAccessNoTransform(int i) {
    return (Access) getTypeAccessListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the TypeAccess list.
   * @return The node representing the TypeAccess list.
   * @apilevel high-level
   */
  public List<Access> getTypeAccesss() {
    return getTypeAccessList();
  }
  /**
   * Retrieves the TypeAccess list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeAccess list.
   * @apilevel low-level
   */
  public List<Access> getTypeAccesssNoTransform() {
    return getTypeAccessListNoTransform();
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
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:39
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:39")
  public boolean isParameter() {
    boolean isParameter_value = true;
    return isParameter_value;
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:42
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:42")
  public boolean isClassVariable() {
    boolean isClassVariable_value = false;
    return isClassVariable_value;
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:43
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:43")
  public boolean isInstanceVariable() {
    boolean isInstanceVariable_value = false;
    return isInstanceVariable_value;
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:47
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:47")
  public boolean isLocalVariable() {
    boolean isLocalVariable_value = false;
    return isLocalVariable_value;
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:48
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:48")
  public boolean isField() {
    boolean isField_value = false;
    return isField_value;
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:50
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:50")
  public boolean accessibleFrom(TypeDecl type) {
    boolean accessibleFrom_TypeDecl_value = false;
    return accessibleFrom_TypeDecl_value;
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:51
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:51")
  public boolean isConstant() {
    boolean isConstant_value = false;
    return isConstant_value;
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:52
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:52")
  public boolean isPublic() {
    boolean isPublic_value = false;
    return isPublic_value;
  }
  /**
   * The catch parameter of a multi-catch clause is implicitly final.
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:61
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:61")
  public boolean isFinal() {
    boolean isFinal_value = true;
    return isFinal_value;
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:62
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:62")
  public boolean isVolatile() {
    boolean isVolatile_value = getModifiers().isVolatile();
    return isVolatile_value;
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:63
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:63")
  public boolean isBlank() {
    boolean isBlank_value = false;
    return isBlank_value;
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:64
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:64")
  public boolean isStatic() {
    boolean isStatic_value = false;
    return isStatic_value;
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:66
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:66")
  public String name() {
    String name_value = getID();
    return name_value;
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:68
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:68")
  public boolean hasInit() {
    boolean hasInit_value = false;
    return hasInit_value;
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:69
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:69")
  public Expr getInit() {
    {
        throw new UnsupportedOperationException();
      }
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:72
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:72")
  public Constant constant() {
    {
        throw new UnsupportedOperationException();
      }
  }
  /**
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:77
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:77")
  public boolean isSynthetic() {
    boolean isSynthetic_value = getModifiers().isSynthetic();
    return isSynthetic_value;
  }
  /**
   * Type checking.
   * The types given in a disjunction type may not be
   * subtypes of each other.
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:158
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:158")
  public Collection<Problem> typeProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        for (int i = 0; i < getNumTypeAccess(); ++i) {
          for (int j = 0; j < getNumTypeAccess(); ++j) {
            if (i == j) {
              continue;
            }
            TypeDecl t1 = getTypeAccess(i).type();
            TypeDecl t2 = getTypeAccess(j).type();
            if (t2.instanceOf(t1)) {
              problems.add(errorf(
                  "%s is a subclass of %s, which is not allowed in multi-catch type alternatives",
                  t2.fullName(), t1.fullName()));
            }
          }
        }
        return problems;
      }
  }
  /**
   * A catch parameter declared with a disjunction type has the
   * effective type lub(t1, t2, ...)
   * 
   * @see "JLSv3 &sect;15.12.2.7"
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:218
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:218")
  public TypeDecl type() {
    {
        ArrayList<TypeDecl> list = new ArrayList<TypeDecl>();
        for (int i = 0; i < getNumTypeAccess(); i++) {
          list.add(getTypeAccess(i).type());
        }
        return lookupLUBType(list).lub();
      }
  }
  /**
   * Duplicate declaration checking for catch parameters.
   * @attribute syn
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:234
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:234")
  public Collection<Problem> nameProblems() {
    {
        Collection<Problem> problems = new LinkedList<Problem>();
        SimpleSet<Variable> decls = outerScope().lookupVariable(name());
        for (Variable var : decls) {
          if (var instanceof VariableDeclarator) {
            VariableDeclarator decl = (VariableDeclarator) var;
            if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
              problems.add(errorf("duplicate declaration of catch parameter %s", name()));
            }
          } else if (var instanceof ParameterDeclaration) {
            ParameterDeclaration decl = (ParameterDeclaration) var;
            if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
              problems.add(errorf("duplicate declaration of catch parameter %s", name()));
            }
          } else if (var instanceof InferredParameterDeclaration) {
            InferredParameterDeclaration decl = (InferredParameterDeclaration) var;
            if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
              problems.add(errorf("duplicate declaration of catch parameter %s", name()));
            }
          } else if (var instanceof CatchParameterDeclaration) {
            CatchParameterDeclaration decl = (CatchParameterDeclaration) var;
            if (decl.enclosingBodyDecl() == enclosingBodyDecl()) {
              problems.add(errorf("duplicate declaration of catch parameter %s", name()));
            }
          }
        }
    
        // 8.4.1
        if (!lookupVariable(name()).contains(this)) {
          problems.add(errorf("duplicate declaration of catch parameter %s", name()));
        }
        return problems;
      }
  }
  /** @apilevel internal */
  private void throwTypes_reset() {
    throwTypes_computed = null;
    throwTypes_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle throwTypes_computed = null;

  /** @apilevel internal */
  protected Collection<TypeDecl> throwTypes_value;

  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:56
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:56")
  public Collection<TypeDecl> throwTypes() {
    ASTState state = state();
    if (throwTypes_computed == ASTState.NON_CYCLE || throwTypes_computed == state().cycle()) {
      return throwTypes_value;
    }
    throwTypes_value = catchClause().caughtExceptions();
    if (state().inCircle()) {
      throwTypes_computed = state().cycle();
    
    } else {
      throwTypes_computed = ASTState.NON_CYCLE;
    
    }
    return throwTypes_value;
  }
  /** @apilevel internal */
  private void isEffectivelyFinal_reset() {
    isEffectivelyFinal_computed = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle isEffectivelyFinal_computed = null;

  /** @apilevel internal */
  protected boolean isEffectivelyFinal_value;

  /**
   * @attribute syn
   * @aspect EffectivelyFinal
   * @declaredat /home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:144
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EffectivelyFinal", declaredAt="/home/olivier/projects/extendj/java8/frontend/EffectivelyFinal.jrag:144")
  public boolean isEffectivelyFinal() {
    ASTState state = state();
    if (isEffectivelyFinal_computed == ASTState.NON_CYCLE || isEffectivelyFinal_computed == state().cycle()) {
      return isEffectivelyFinal_value;
    }
    isEffectivelyFinal_value = true;
    if (state().inCircle()) {
      isEffectivelyFinal_computed = state().cycle();
    
    } else {
      isEffectivelyFinal_computed = ASTState.NON_CYCLE;
    
    }
    return isEffectivelyFinal_value;
  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:278
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="/home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:278")
  public boolean isProtected() {
    boolean isProtected_value = getModifiers().isProtected();
    return isProtected_value;
  }
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:280
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Modifiers", declaredAt="/home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:280")
  public boolean isPrivate() {
    boolean isPrivate_value = getModifiers().isPrivate();
    return isPrivate_value;
  }
  /**
   * @attribute syn
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:74
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="EmitJimple", declaredAt="/home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:74")
  public soot.Type sootType() {
    soot.Type sootType_value = type().sootType();
    return sootType_value;
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
   * Inherit the lookupVariable attribute.
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:36
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:36")
  public SimpleSet<Variable> lookupVariable(String name) {
    SimpleSet<Variable> lookupVariable_String_value = getParent().Define_lookupVariable(this, null, name);
    return lookupVariable_String_value;
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:44
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:44")
  public boolean isMethodParameter() {
    boolean isMethodParameter_value = getParent().Define_isMethodParameter(this, null);
    return isMethodParameter_value;
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:45
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:45")
  public boolean isConstructorParameter() {
    boolean isConstructorParameter_value = getParent().Define_isConstructorParameter(this, null);
    return isConstructorParameter_value;
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:46
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:46")
  public boolean isExceptionHandlerParameter() {
    boolean isExceptionHandlerParameter_value = getParent().Define_isExceptionHandlerParameter(this, null);
    return isExceptionHandlerParameter_value;
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:76
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:76")
  public TypeDecl hostType() {
    TypeDecl hostType_value = getParent().Define_hostType(this, null);
    return hostType_value;
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:210
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:210")
  public LUBType lookupLUBType(Collection<TypeDecl> bounds) {
    LUBType lookupLUBType_Collection_TypeDecl__value = getParent().Define_lookupLUBType(this, null, bounds);
    return lookupLUBType_Collection_TypeDecl__value;
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:226
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:226")
  public VariableScope outerScope() {
    VariableScope outerScope_value = getParent().Define_outerScope(this, null);
    return outerScope_value;
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:227
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="MultiCatch", declaredAt="/home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:227")
  public BodyDecl enclosingBodyDecl() {
    BodyDecl enclosingBodyDecl_value = getParent().Define_enclosingBodyDecl(this, null);
    return enclosingBodyDecl_value;
  }
  /**
   * @attribute inh
   * @aspect PreciseRethrow
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:209
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="PreciseRethrow", declaredAt="/home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:209")
  public CatchClause catchClause() {
    CatchClause catchClause_value = getParent().Define_catchClause(this, null);
    return catchClause_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:637
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NestedTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:637")
  public String hostPackage() {
    String hostPackage_value = getParent().Define_hostPackage(this, null);
    return hostPackage_value;
  }
  /**
   * @attribute inh
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1391
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:1391")
  public FieldDecl fieldDecl() {
    FieldDecl fieldDecl_value = getParent().Define_fieldDecl(this, null);
    return fieldDecl_value;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    if (_callerNode == getTypeAccessListNoTransform()) {
      // @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:138
      int i = _callerNode.getIndexOfChild(_childNode);
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
    // @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:151
    {
      java.util.Set<ASTNode> contributors = _map.get(_root);
      if (contributors == null) {
        contributors = new java.util.LinkedHashSet<ASTNode>();
        _map.put((ASTNode) _root, contributors);
      }
      contributors.add(this);
    }
    // @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:229
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
    for (Problem value : typeProblems()) {
      collection.add(value);
    }
    for (Problem value : nameProblems()) {
      collection.add(value);
    }
  }
}
