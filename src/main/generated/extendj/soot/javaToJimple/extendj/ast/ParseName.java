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
 * An unclassified parse name.
 * 
 * <p>Parse names are classified automatically using the JastAdd rewrite
 * mechanism.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:108
 * @production ParseName : {@link Access};

 */
public class ParseName extends Access implements Cloneable {
  /**
   * @aspect DumpTree
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DumpTree.jadd:112
   */
  @Override
  public String getTokens() {
    return " " + name();
  }
  /**
   * @aspect Java4PrettyPrint
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrint.jadd:539
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(name());
  }
  /**
   * @aspect PrettyPrintUtil
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:72
   */
  @Override public String toString() {
    return name();
  }
  /**
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:217
   */
  public Access qualifiesAccess(Access access) {
    if (access instanceof ParseName) {
      ParseName name = (ParseName) access;
      ArrayList<NamePart> parts = new ArrayList<NamePart>(nameParts);
      parts.addAll(name.nameParts);
      return new ParseName(parts);
    } else {
      Access result = new Dot(this, access);
      result.setStart(getStart());
      result.setEnd(access.getEnd());
      return result;
    }
  }
  /**
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:438
   */
  private ArrayList<NamePart> nameParts = new ArrayList<NamePart>();
  /**
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:440
   */
  public ParseName(ArrayList<NamePart> nameParts) {
    this.nameParts.addAll(nameParts);
  }
  /**
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:444
   */
  public ParseName(Symbol name) {
    nameParts.add(new NamePart(name));
  }
  /**
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:456
   */
  public Access buildMethodAccess(List<Expr> arguments) {
    if (nameParts.isEmpty()) {
      throw new Error("Can not build method access using empty parse name!");
    }
    int num = nameParts.size();
    NamePart methodName = nameParts.get(nameParts.size() - 1);
    ArrayList<NamePart> head = new ArrayList<NamePart>();
    for (int i = 0; i < nameParts.size() - 1; ++i) {
      head.add(nameParts.get(i));
    }
    MethodAccess call = new MethodAccess(methodName.toString(), arguments);
    if (head.isEmpty()) {
      return call;
    } else {
      return new Dot(new ParseName(head), call);
    }
  }
  /**
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:508
   */
  protected NamePart resolvePackageOrTypeName(NamePart qualifier, NamePart name) {
    if (qualifier.isPackageQualifier()) {
      if (!qualifier.hasType(this, name)) {
        return new NamePart.Package(qualifier, name);
      }
    }
    return new NamePart.Type(qualifier, name);
  }
  /**
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:518
   */
  protected NamePart resolveAmbiguousName(NamePart qualifier, NamePart name) {
    // "If the AmbiguousName is a simple name, consisting of a single Identifier: "
    if (qualifier == NamePart.EMPTY) {
      // If variable/param/field -> ExpressionName
      SimpleSet<Variable> var = lookupVariable(name.toString());
      if (!var.isEmpty()) return new NamePart.VarName(qualifier, name);

      // TODO: If field based single-static-import/static-import-on-demand -> ExpressionName

      // If top-level-class/top-level-interface/member-type -> TypeName
      // If single-type-import/type-import-on-demand/single-static-import/static-import-on-demand
      //  -> TypeName
      // Otherwise, assume its a package.
      return resolvePackageOrTypeName(qualifier, name);
    }

    // "If the AmbiguousName is a qualified name, consisting of a name, a ".", and an Identifier,
    //  then the name to the left of the "." is first reclassified, for it is itself an
    //  AmbiguousName."

    // "If the name to the left of the "." is reclassified as a PackageName"
    if (qualifier instanceof NamePart.Package) {
      // "If there is a package whose name is the name to the left of the "." and that package
      //  contains a declaration of a type whose name is the same as the Identifier, then this
      //  AmbiguousName is reclassified as a TypeName.
      //  Otherwise, this AmbiguousName is reclassified as a PackageName."
      return resolvePackageOrTypeName(qualifier, name);
    }

    // "If the name to the left of the "." is reclassified as a TypeName, then: "
    if (qualifier instanceof NamePart.Type) {
      TypeDecl hostType = qualifier.lookupType(this);

      // "If the Identifier is the name of a method or field of the type denoted by TypeName, this
      //  AmbiguousName is reclassified as an ExpressionName."
      SimpleSet<Variable> var   = hostType.memberFields(name.toString());
      if (!var  .isEmpty()) return new NamePart.VarName(qualifier, name);

      // "Otherwise, if the Identifier is the name of a member-type of the type denoted by TypeName,
      //  this AmbiguousName is reclassified as a TypeName.""
      SimpleSet<TypeDecl> type  = hostType.memberTypes (name.toString());
      if (!type .isEmpty()) return new NamePart.Type   (qualifier, name);

      // "Otherwise, a compile-time error occurs."
      // TODO: Add problem-handling for this.
      throw new RuntimeException("Welp.");
    }

    // "If the name to the left of the "." is reclassified as an ExpressionName, then let T be the
    //  type of the expression denoted by ExpressionName."
    if (qualifier instanceof NamePart.VarName) {
      TypeDecl hostType = qualifier.lookupType(this);

      // "If the Identifier is the name of a method or field of the type denoted by T, this
      //  AmbiguousName is reclassified as an ExpressionName."
      SimpleSet<Variable>     var     = hostType.memberFields (name.toString());
      if (!var    .isEmpty()) return new NamePart.VarName(qualifier, name);
      Collection<MethodDecl>  method  = hostType.memberMethods(name.toString());
      if (!method .isEmpty()) return new NamePart.VarName(qualifier, name);

      // "Otherwise, if the Identifier is the name of a member type (\u00a78.5, \u00a79.5) of the type denoted
      //  by T, then this AmbiguousName is reclassified as a TypeName."
      SimpleSet<TypeDecl>     type    = hostType.memberTypes   (name.toString());
      if (!type   .isEmpty()) return new NamePart.Type(qualifier, name);

      // "Otherwise, a compile-time error occurs."
      // TODO: Add problem-handling for this.
      throw new RuntimeException("Welp.");
    }

    throw new RuntimeException("the impossible has occurred in name disambiguation");
  }
  /**
   * @declaredat ASTNode:1
   */
  public ParseName() {
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
    return true;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:23
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    rewrittenNode_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:28
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:32
   */
  public ParseName clone() throws CloneNotSupportedException {
    ParseName node = (ParseName) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:37
   */
  public ParseName copy() {
    try {
      ParseName node = (ParseName) clone();
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
   * @declaredat ASTNode:56
   */
  @Deprecated
  public ParseName fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:66
   */
  public ParseName treeCopyNoTransform() {
    ParseName tree = (ParseName) copy();
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
   * @declaredat ASTNode:86
   */
  public ParseName treeCopy() {
    ParseName tree = (ParseName) copy();
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
   * @declaredat ASTNode:100
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:608
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:608")
  public SimpleSet<TypeDecl> qualifiedLookupType(String name) {
    SimpleSet<TypeDecl> qualifiedLookupType_String_value = emptySet();
    return qualifiedLookupType_String_value;
  }
  /**
   * @attribute syn
   * @aspect VariableScope
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:264
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="VariableScope", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:264")
  public SimpleSet<Variable> qualifiedLookupVariable(String name) {
    SimpleSet<Variable> qualifiedLookupVariable_String_value = emptySet();
    return qualifiedLookupVariable_String_value;
  }
  /**
   * @attribute syn
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:493
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameResolution", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:493")
  public boolean isParseName() {
    boolean isParseName_value = true;
    return isParseName_value;
  }
  /**
   * @attribute syn
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:497
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameResolution", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:497")
  public String name() {
    {
        StringBuilder sb = new StringBuilder();
        for (NamePart part : nameParts) {
          if (sb.length() != 0) {
            sb.append(".");
          }
          sb.append(part.toString());
        }
        return sb.toString();
      }
  }
  /** @apilevel internal */
  public ASTNode rewriteTo() {
    // Declared at /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:592
    return rewriteRule0();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:592
   * @apilevel internal
   */
  private Access rewriteRule0() {
{
      NamePart name = NamePart.EMPTY;
      switch (nameType()) {
        case PACKAGE_NAME:
          for (NamePart next : nameParts) {
            name = new NamePart.Package(name, next);
          }
          break;
        case TYPE_NAME:
          // The first part of the name is the package part.
          for (int i = 0; i < nameParts.size() - 1; ++i) {
            name = resolvePackageOrTypeName(name, nameParts.get(i));
          }
          // The rest of the name is the type name.
          name = new NamePart.Type(name, nameParts.get(nameParts.size() - 1));
          break;
        case PACKAGE_OR_TYPE_NAME:
          for (NamePart next : nameParts) {
            name = resolvePackageOrTypeName(name, next);
          }
          break;
        case AMBIGUOUS_NAME:
          for (NamePart next : nameParts) {
            name = resolveAmbiguousName(name, next);
          }
          break;
        case EXPRESSION_NAME:
          // The first part of the name is the package part.
          for (int i = 0; i < nameParts.size() - 1; ++i) {
            name = resolveAmbiguousName(name, nameParts.get(i));
          }
          name = new NamePart.VarName(name, nameParts.get(nameParts.size() - 1));
          break;
        case NOT_CLASSIFIED:
        default:
          throw new Error("Failure in name classification: unknown name type encountered");
      }
      Access result = null;
      while (name != NamePart.EMPTY) {
        if (result == null) {
          result = name.buildAccess();
        } else {
          result = new Dot(name.buildAccess(), result);
        }
        name = name.pred;
      }
      return result;
    }  }
  /** @apilevel internal */
  public boolean canRewrite() {
    return true;
  }
  /** @apilevel internal */
  private void rewrittenNode_reset() {
    rewrittenNode_computed = false;
    rewrittenNode_initialized = false;
    rewrittenNode_value = null;
    rewrittenNode_cycle = null;
  }
/** @apilevel internal */
protected ASTNode$State.Cycle rewrittenNode_cycle = null;
  /** @apilevel internal */
  protected boolean rewrittenNode_computed = false;

  /** @apilevel internal */
  protected ASTNode rewrittenNode_value;
  /** @apilevel internal */
  protected boolean rewrittenNode_initialized = false;
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isCircular=true, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="", declaredAt=":0")
  public ASTNode rewrittenNode() {
    if (rewrittenNode_computed) {
      return rewrittenNode_value;
    }
    ASTNode$State state = state();
    if (!rewrittenNode_initialized) {
      rewrittenNode_initialized = true;
      rewrittenNode_value = this;
      if (rewrittenNode_value != null) {
        rewrittenNode_value.setParent(getParent());
      }
    }
    if (!state.inCircle() || state.calledByLazyAttribute()) {
      state.enterCircle();
      do {
        rewrittenNode_cycle = state.nextCycle();
        ASTNode new_rewrittenNode_value = rewrittenNode_value.rewriteTo();
        if (new_rewrittenNode_value != rewrittenNode_value || new_rewrittenNode_value.canRewrite()) {
          state.setChangeInCycle();
        }
        rewrittenNode_value = new_rewrittenNode_value;
        if (rewrittenNode_value != null) {
          rewrittenNode_value.setParent(getParent());
        }
      } while (state.testAndClearChangeInCycle());
      rewrittenNode_computed = true;

      state.leaveCircle();
    } else if (rewrittenNode_cycle != state.cycle()) {
      rewrittenNode_cycle = state.cycle();
      ASTNode new_rewrittenNode_value = rewrittenNode_value.rewriteTo();
      if (new_rewrittenNode_value != rewrittenNode_value || new_rewrittenNode_value.canRewrite()) {
        state.setChangeInCycle();
      }
      rewrittenNode_value = new_rewrittenNode_value;
      if (rewrittenNode_value != null) {
        rewrittenNode_value.setParent(getParent());
      }
    } else {
    }
    return rewrittenNode_value;
  }
}
