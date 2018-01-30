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
 * @declaredat /home/olivier/projects/extendj/java5/grammar/Generics.ast:65
 * @astdecl AbstractWildcard : Access;
 * @production AbstractWildcard : {@link Access};

 */
public abstract class AbstractWildcard extends Access implements Cloneable {
  /**
   * @aspect FunctionalInterface
   * @declaredat /home/olivier/projects/extendj/java8/frontend/FunctionalInterface.jrag:253
   */
  public boolean sameType(AbstractWildcard w) {
    if (this instanceof Wildcard && w instanceof Wildcard) {
      return true;
    } else if (this instanceof WildcardExtends && w instanceof WildcardExtends) {
      Access a1 = ((WildcardExtends) this).getAccess();
      Access a2 = ((WildcardExtends) w).getAccess();
      return a1.sameType(a2);
    } else if (this instanceof WildcardSuper && w instanceof WildcardSuper) {
      Access a1 = ((WildcardSuper) this).getAccess();
      Access a2 = ((WildcardSuper) w).getAccess();
      return a1.sameType(a2);
    } else {
      return false;
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public AbstractWildcard() {
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
  /** @apilevel low-level 
   * @declaredat ASTNode:13
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:19
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:23
   */
  public void flushAttrCache() {
    super.flushAttrCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:27
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:31
   */
  public AbstractWildcard clone() throws CloneNotSupportedException {
    AbstractWildcard node = (AbstractWildcard) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:42
   */
  @Deprecated
  public abstract AbstractWildcard fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:50
   */
  public abstract AbstractWildcard treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:58
   */
  public abstract AbstractWildcard treeCopy();
  /**
   * WARNING: this attribute is not the same as TypeDecl.isWildcard,
   * which returns true for any wildcard type (even bounded wildcard types).
   * @return {@code true} if this is an unbounded wildcard access
   * @attribute syn
   * @aspect ReifiableTypes
   * @declaredat /home/olivier/projects/extendj/java5/frontend/ReifiableTypes.jrag:106
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ReifiableTypes", declaredAt="/home/olivier/projects/extendj/java5/frontend/ReifiableTypes.jrag:106")
  public boolean isWildcard() {
    boolean isWildcard_value = true;
    return isWildcard_value;
  }
  /**
   * @attribute syn
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:42
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Expressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:42")
  public Value eval(Body b) {
    Value eval_Body_value = eval_fail_unerased_generics();
    return eval_Body_value;
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
