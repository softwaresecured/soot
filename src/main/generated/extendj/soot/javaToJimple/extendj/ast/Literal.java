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
 * The abstract base class for all literals.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Literals.ast:4
 * @astdecl Literal : PrimaryExpr ::= <LITERAL:String>;
 * @production Literal : {@link PrimaryExpr} ::= <span class="component">&lt;LITERAL:String&gt;</span>;

 */
public abstract class Literal extends PrimaryExpr implements Cloneable {
  /**
   * @aspect BytecodeCONSTANT
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BytecodeCONSTANT.jrag:96
   */
  public static Literal buildBooleanLiteral(boolean value) {
    return new BooleanLiteral(value ? "true" : "false");
  }
  /**
   * @aspect BytecodeCONSTANT
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BytecodeCONSTANT.jrag:100
   */
  public static Literal buildStringLiteral(String value) {
    return new StringLiteral(value);
  }
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:442
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getLITERAL());
  }
  /**
   * @aspect PrettyPrintUtil
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:117
   */
  @Override public String toString() {
    return getLITERAL();
  }
  /**
   * Escapes a string literal.
   * @param s string to escape
   * @return escaped string literal
   * @aspect PrettyPrintUtil
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:406
   */
  protected static String escape(String s) {
    StringBuilder result = new StringBuilder();
    for (int i=0; i < s.length(); i++) {
      switch(s.charAt(i)) {
        case '\b':
          result.append("\\b");
          break;
        case '\t':
          result.append("\\t");
          break;
        case '\n':
          result.append("\\n");
          break;
        case '\f':
          result.append("\\f");
          break;
        case '\r':
          result.append("\\r");
          break;
        case '\"':
          result.append("\\\"");
          break;
        case '\'':
          result.append("\\\'");
          break;
        case '\\':
          result.append("\\\\");
          break;
        default:
          int value = (int) s.charAt(i);
          if (value < 0x20 || (value > 0x7e)) {
            result.append(asEscape(value));
          } else {
            result.append(s.charAt(i));
          }
      }
    }
    return result.toString();
  }
  /**
   * @aspect PrettyPrintUtil
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:446
   */
  protected static String asEscape(int value) {
    StringBuilder s = new StringBuilder("\\u");
    String hex = Integer.toHexString(value);
    for (int i = 0; i < 4-hex.length(); i++) {
      s.append("0");
    }
    s.append(hex);
    return s.toString();
  }
  /**
   * @declaredat ASTNode:1
   */
  public Literal() {
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
  public Literal(String p0) {
    setLITERAL(p0);
  }
  /**
   * @declaredat ASTNode:20
   */
  public Literal(beaver.Symbol p0) {
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
    constant_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:39
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:43
   */
  public Literal clone() throws CloneNotSupportedException {
    Literal node = (Literal) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:54
   */
  @Deprecated
  public abstract Literal fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:62
   */
  public abstract Literal treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:70
   */
  public abstract Literal treeCopy();
  /**
   * Replaces the lexeme LITERAL.
   * @param value The new value for the lexeme LITERAL.
   * @apilevel high-level
   */
  public void setLITERAL(String value) {
    tokenString_LITERAL = value;
  }
  /** @apilevel internal 
   */
  protected String tokenString_LITERAL;
  /**
   */
  public int LITERALstart;
  /**
   */
  public int LITERALend;
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
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:905
   */
   
  public static Literal buildDoubleLiteral(double value) {
    return new DoubleLiteral(Double.toString(value), Constant.create(value));
  }
  /**
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:917
   */
   
  public static Literal buildFloatLiteral(float value) {
    return new FloatingPointLiteral(Double.toString(value), Constant.create(value));
  }
  /**
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:929
   */
   
  public static Literal buildIntegerLiteral(int value) {
    return new IntegerLiteral("0x"+Integer.toHexString(value), Constant.create(value));
  }
  /**
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:941
   */
   
  public static Literal buildLongLiteral(long value) {
    return new LongLiteral("0x"+Long.toHexString(value), Constant.create(value));
  }
  /**
   * @attribute syn
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:53
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Expressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:53")
  public abstract soot.jimple.Constant eval(Body b);
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
      throw new UnsupportedOperationException("ConstantExpression operation constant"
          + " not supported for type " + getClass().getName());
    }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:383
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:383")
  public boolean isConstant() {
    boolean isConstant_value = true;
    return isConstant_value;
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:435
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:435")
  public boolean isTrue() {
    boolean isTrue_value = false;
    return isTrue_value;
  }
  /**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:438
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ConstantExpression", declaredAt="/home/olivier/projects/extendj/java4/frontend/ConstantExpression.jrag:438")
  public boolean isFalse() {
    boolean isFalse_value = false;
    return isFalse_value;
  }
  /**
   * This is a refactored version of Literal.parseLong which supports
   * binary literals.
   * 
   * There exists only a parseLong, and not a parseInteger. Parsing
   * of regular integer literals works the same, but with stricter
   * bounds requirements on the resulting parsed value.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:175
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="/home/olivier/projects/extendj/java7/frontend/Literals.jrag:175")
  public long parseLong() {
    {
        switch (numericLiteralKind()) {
          case HEXADECIMAL:
            return parseLongHexadecimal();
          case OCTAL:
            return parseLongOctal();
          case BINARY:
            return parseLongBinary();
          case DECIMAL:
          default: // Needed to convince javac that all paths return.
            return parseLongDecimal();
        }
      }
  }
  /**
   * Parse a hexadecimal long literal.
   * 
   * @throws NumberFormatException if the literal is too large.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:194
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="/home/olivier/projects/extendj/java7/frontend/Literals.jrag:194")
  public long parseLongHexadecimal() {
    {
        long val = 0;
        String digits = normalizedValueDigits();
        if (digits.length() > 16) {
          for (int i = 0; i < digits.length()-16; i++)
            if (digits.charAt(i) != '0') {
              throw new NumberFormatException("literal is too large");
            }
        }
        for (int i = 0; i < digits.length(); i++) {
          int c = digits.charAt(i);
          if (c >= 'a' && c <= 'f') {
            c = c - 'a' + 10;
          } else {
            c = c - '0';
          }
          val = val * 16 + c;
        }
        return val;
      }
  }
  /**
   * Parse an octal long literal.
   * 
   * @throws NumberFormatException if the literal is too large.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:220
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="/home/olivier/projects/extendj/java7/frontend/Literals.jrag:220")
  public long parseLongOctal() {
    {
        long val = 0;
        String digits = normalizedValueDigits();
        if (digits.length() > 21) {
          for (int i = 0; i < digits.length() - 21; i++)
            if (i == digits.length() - 21 - 1) {
              if (digits.charAt(i) != '0' && digits.charAt(i) != '1') {
                throw new NumberFormatException("literal is too large");
              }
            } else {
              if (digits.charAt(i) != '0') {
                throw new NumberFormatException("literal is too large");
              }
            }
        }
        for (int i = 0; i < digits.length(); i++) {
          int c = digits.charAt(i) - '0';
          val = val * 8 + c;
        }
        return val;
      }
  }
  /**
   * Parse a binary long literal.
   * 
   * @throws NumberFormatException if the literal is too large.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:247
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="/home/olivier/projects/extendj/java7/frontend/Literals.jrag:247")
  public long parseLongBinary() {
    {
        long val = 0;
        String digits = normalizedValueDigits();
        if (digits.length() > 64) {
          for (int i = 0; i < digits.length()-64; i++)
            if (digits.charAt(i) != '0') {
              throw new NumberFormatException("");
            }
        }
        for (int i = 0; i < digits.length(); ++i) {
          if (digits.charAt(i) == '1') {
            val |= 1L << (digits.length()-i-1);
          }
        }
        return val;
      }
  }
  /**
   * Parse a decimal long literal.
   * @throws NumberFormatException if the literal is too large.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:268
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="/home/olivier/projects/extendj/java7/frontend/Literals.jrag:268")
  public long parseLongDecimal() {
    {
        long val = 0;
        long prev = 0;
        String digits = normalizedValueDigits();
        for (int i = 0; i < digits.length(); i++) {
          prev = val;
          int c = digits.charAt(i);
          if (c >= '0' && c <= '9') {
            c = c - '0';
          } else {
            throw new NumberFormatException("unexpected digit: " + c);
          }
          val = val * 10 + c;
          if (val < prev) {
            boolean negMinValue = i == (digits.length()-1) &&
              isNegative() && val == Long.MIN_VALUE;
            if (!negMinValue) {
              throw new NumberFormatException("");
            }
          }
        }
        if (val == Long.MIN_VALUE) {
          return val;
        }
        if (val < 0) {
          throw new NumberFormatException("");
        }
        return isNegative() ? -val : val;
      }
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
    boolean isNegative_value = false;
    return isNegative_value;
  }
  /**
   * The lowercase digits of this numeric literal,
   * with underscores, leading minus, and type suffix removed.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:331
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="/home/olivier/projects/extendj/java7/frontend/Literals.jrag:331")
  public String normalizedDigits() {
    {
        String lit = getLITERAL().toLowerCase();
        char[] result = new char[lit.length()];
        int i = 0;
        int p = 0;
        // Skip leading minus.
        if (lit.length() > 0 && lit.charAt(i) == '-') {
          i += 1;
        }
        // First we have to filter out underscores to be able to identify if it's an octal literal.
        for (; i < lit.length(); ++i) {
          char c = lit.charAt(i);
          if (c != '_') {
            result[p++] = lit.charAt(i);
          }
        }
        if (p > 1) {
          if (p > 2 && result[0] == '0' && result[1] == 'x') {
            // It's a hexadecimal literal - remove l suffix.
            if (result[p - 1] == 'l') {
              p -= 1;
            }
          } else if (result[p - 1] == 'l' || result[p - 1] == 'f' || result[p - 1] == 'd') {
            // Remove l/f/d suffix.
            p -= 1;
          }
        }
        return new String(result, 0, p);
      }
  }
  /**
   * The trimmed lowercase digits of this literal, excluding
   * prefix, leading minus, suffix, and underscores.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:366
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="/home/olivier/projects/extendj/java7/frontend/Literals.jrag:366")
  public String normalizedValueDigits() {
    {
        String digits = normalizedDigits();
        // Remove prefix.
        if (digits.length() > 1 && digits.charAt(0) == '0') {
          char next = digits.charAt(1);
          if (next == 'x') {
            return digits.substring(2);
          } else if (next == 'b') {
            return digits.substring(2);
          } else {
            return digits.substring(1);
          }
        }
        return digits;
      }
  }
  /**
   * The literal kind tells which kind of literal this is;
   * either a DECIMAL, HEXADECIMAL, OCTAL or BINARY literal.
   * @attribute syn
   * @aspect Java7Literals
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Literals.jrag:386
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Java7Literals", declaredAt="/home/olivier/projects/extendj/java7/frontend/Literals.jrag:386")
  public NumericLiteralKind numericLiteralKind() {
    {
        String digits = normalizedDigits();
        if (digits.length() > 1 && digits.charAt(0) == '0') {
          char next = digits.charAt(1);
          if (next == 'x') {
            return NumericLiteralKind.HEXADECIMAL;
          } else if (next == 'b') {
            return NumericLiteralKind.BINARY;
          } else {
            return NumericLiteralKind.OCTAL;
          }
        }
        return NumericLiteralKind.DECIMAL;
      }
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
