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
 * Java long integer literal. Can store any number that fits in 64 bits
 * of data, or less.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Literals.ast:41
 * @astdecl LongLiteral : Literal;
 * @production LongLiteral : {@link Literal};

 */
public class LongLiteral extends Literal implements Cloneable, NumericLiteral {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:448
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getLITERAL());
  }
  /**
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:934
   */
  protected Constant constant = null;
  /**
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:936
   */
  public LongLiteral(String literal, Constant constant) {
    this(literal);
    this.constant = constant;
  }
  /**
   * @declaredat ASTNode:1
   */
  public LongLiteral() {
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
    name = {"LITERAL"},
    type = {"String"},
    kind = {"Token"}
  )
  public LongLiteral(String p0) {
    setLITERAL(p0);
  }
  /**
   * @declaredat ASTNode:20
   */
  public LongLiteral(beaver.Symbol p0) {
    setLITERAL(p0);
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
    type_reset();
    constant_reset();
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
  public LongLiteral clone() throws CloneNotSupportedException {
    LongLiteral node = (LongLiteral) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:49
   */
  public LongLiteral copy() {
    try {
      LongLiteral node = (LongLiteral) clone();
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
  public LongLiteral fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:78
   */
  public LongLiteral treeCopyNoTransform() {
    LongLiteral tree = (LongLiteral) copy();
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
  public LongLiteral treeCopy() {
    LongLiteral tree = (LongLiteral) copy();
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
    return super.is$Equal(node) && (tokenString_LITERAL == ((LongLiteral) node).tokenString_LITERAL);    
  }
  /**
   * Replaces the lexeme LITERAL.
   * @param value The new value for the lexeme LITERAL.
   * @apilevel high-level
   */
  public void setLITERAL(String value) {
    tokenString_LITERAL = value;
  }
  /**
   * JastAdd-internal setter for lexeme LITERAL using the Beaver parser.
   * @param symbol Symbol containing the new value for the lexeme LITERAL
   * @apilevel internal
   */
  public void setLITERAL(beaver.Symbol symbol) {
    if (symbol.value != null && !(symbol.value instanceof String))
    throw new UnsupportedOperationException("setLITERAL is only valid for String lexemes");
    tokenString_LITERAL = (String)symbol.value;
    LITERALstart = symbol.getStart();
    LITERALend = symbol.getEnd();
  }
  /**
   * Retrieves the value for the lexeme LITERAL.
   * @return The value for the lexeme LITERAL.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Token(name="LITERAL")
  public String getLITERAL() {
    return tokenString_LITERAL != null ? tokenString_LITERAL : "";
  }
  /**
   * @attribute syn
   * @aspect PositiveLiterals
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PositiveLiterals.jrag:36
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="PositiveLiterals", declaredAt="/home/olivier/projects/extendj/java4/frontend/PositiveLiterals.jrag:36")
  public boolean isPositive() {
    boolean isPositive_value = !getLITERAL().startsWith("-");
    return isPositive_value;
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
    type_value = typeLong();
    if (state().inCircle()) {
      type_computed = state().cycle();
    
    } else {
      type_computed = ASTState.NON_CYCLE;
    
    }
    return type_value;
  }
  /**
   * @attribute syn
   * @aspect TypeCheck
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:761
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeCheck", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:761")
  public Problem sizeError() {
    Problem sizeError_value = errorf("The integer literal \"%s\" is too large for type long.", getLITERAL());
    return sizeError_value;
  }
  /** @apilevel internal */
  private void constant_reset() {
    constant_computed = null;
    constant_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle constant_computed = null;

  /** @apilevel internal */
  protected Constant constant_value;

  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:38
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:38")
  public Constant constant() {
    ASTState state = state();
    if (constant_computed == ASTState.NON_CYCLE || constant_computed == state().cycle()) {
      return constant_value;
    }
    constant_value = constant_compute();
    if (state().inCircle()) {
      constant_computed = state().cycle();
    
    } else {
      constant_computed = ASTState.NON_CYCLE;
    
    }
    return constant_value;
  }
  /** @apilevel internal */
  private Constant constant_compute() {
      if (constant != null) {
        return constant;
      }
      try {
        return Constant.create(parseLong());
      } catch (NumberFormatException e) {
        Constant c = Constant.create(0L);
        c.error = true;
        return c;
      }
    }
  /**
   * @attribute syn
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:53
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Expressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:53")
  public soot.jimple.Constant eval(Body b) {
    soot.jimple.Constant eval_Body_value = b.setSrcLoc(LongConstant  .v(constant().  longValue()), this);
    return eval_Body_value;
  }
  /**
   * This is only meaningful for numeric literals.
   * 
   * @return {@code true} if this literal starts with a minus sign.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:303
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="/home/olivier/projects/extendj/java7/frontend/Literals.jrag:303")
  public boolean isNegative() {
    boolean isNegative_value = getLITERAL().charAt(0) == '-';
    return isNegative_value;
  }
  /**
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:317
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="/home/olivier/projects/extendj/java7/frontend/Literals.jrag:317")
  public boolean isHex() {
    boolean isHex_value = numericLiteralKind() == NumericLiteralKind.HEXADECIMAL;
    return isHex_value;
  }
  /**
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:319
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="/home/olivier/projects/extendj/java7/frontend/Literals.jrag:319")
  public boolean isOctal() {
    boolean isOctal_value = numericLiteralKind() == NumericLiteralKind.OCTAL;
    return isOctal_value;
  }
  /**
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:321
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="/home/olivier/projects/extendj/java7/frontend/Literals.jrag:321")
  public boolean isDecimal() {
    boolean isDecimal_value = numericLiteralKind() == NumericLiteralKind.DECIMAL;
    return isDecimal_value;
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
    // @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:764
    if (constant().error) {
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
    if (constant().error) {
      collection.add(sizeError());
    }
  }
}
