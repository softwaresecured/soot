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
 * An unclassified parse name.
 * 
 * <p>Parse names are classified automatically using the JastAdd rewrite
 * mechanism.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:108
 * @astdecl ParseName : Access;
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:113
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:258
   */
  private ArrayList<NamePart> nameParts = new ArrayList<NamePart>();
  /**
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:260
   */
  public ParseName(ArrayList<NamePart> nameParts) {
    this.nameParts.addAll(nameParts);
  }
  /**
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:264
   */
  public ParseName(Symbol name) {
    nameParts.add(new NamePart(name));
  }
  /**
   * @aspect QualifiedNames
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:276
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:328
   */
  protected Access resolvePackageOrTypeName(Access qualifier, NamePart name) {
    if (qualifier == null)
      return lookupType(name.toString()).isEmpty() ? mkPkgName(name) : mkTypeName(name);

    if (qualifier.isPackageAccess()) {
      if (qualifier.qualifiedLookupType(name.toString()).isEmpty())
        return mkPkgName(qualifier, name);
    }

    return mkTypeName(qualifier, name);
  }
  /**
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:340
   */
  protected Access resolveAmbiguousName(Access qualifier, NamePart name) {
    if (qualifier == null) {
      SimpleSet<Variable> var = lookupVariable(name.toString());
      if (!var.isEmpty()) return mkVarName(name);
    } else {
      TypeDecl qualType = qualifier.type();
      if (qualType != null) {
        SimpleSet<Variable> var = qualifier.keepAccessibleFields(qualType.memberFields(name.toString()));
        if (!var.isEmpty()) return mkVarName(qualifier, name);
      }
    }

    return resolvePackageOrTypeName(qualifier, name);
  }
  /**
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:355
   */
  private VarAccess mkVarName(NamePart varName) {
    VarAccess a = new VarAccess(varName.toString(), varName.start, varName.end);
    a.setParent(getParent());
    return a;
  }
  /**
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:361
   */
  private TypeAccess mkTypeName(NamePart typeName) {
    TypeAccess a = new TypeAccess(typeName.toString(), typeName.start, typeName.end);
    a.setParent(getParent());
    return a;
  }
  /**
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:367
   */
  private PackageAccess mkPkgName(NamePart pkgName) {
    PackageAccess a = new PackageAccess(pkgName.toString(), pkgName.start, pkgName.end);
    a.setParent(getParent());
    return a;
  }
  /**
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:373
   */
  private Access mkVarName(Access qualifier, NamePart varName)
  { return mkDot(qualifier, mkVarName(varName)); }
  /**
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:376
   */
  private Access mkTypeName(Access qualifier, NamePart typeName) {
    TypeAccess access = new TypeAccess(
      typeName.toString(),
      qualifier.isPackageAccess() ? qualifier.getStart() : typeName.start,
      typeName.end);

    if (qualifier.isPackageAccess())
      access.setPackage(qualifier.packageName());

    return mkDot(qualifier, access);
  }
  /**
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:388
   */
  private PackageAccess mkPkgName(Access qualifier, NamePart pkgName) {
    assert qualifier.isPackageAccess();
    PackageAccess a = new PackageAccess(qualifier.packageName() + "." + pkgName.toString()
                                       ,qualifier.getStart(), pkgName.end);
    a.setParent(getParent());
    return a;
  }
  /**
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:396
   */
  private Dot mkDot(Expr lhs, Access rhs) {
    Dot a = new Dot(lhs, rhs);
    a.setParent(getParent());
    return a;
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:612
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeScopePropagation", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:612")
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
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:313
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameResolution", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:313")
  public boolean isParseName() {
    boolean isParseName_value = true;
    return isParseName_value;
  }
  /**
   * @attribute syn
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:317
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameResolution", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:317")
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
  /**
   * Test if an expression contains an unresolved parse name.
   * @attribute syn
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:421
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="NameResolution", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:421")
  public boolean containsParseName() {
    boolean containsParseName_value = true;
    return containsParseName_value;
  }
  /**
   * @attribute syn
   * @aspect Expressions
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:42
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="Expressions", declaredAt="/home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:42")
  public Value eval(Body b) {
    Value eval_Body_value = eval_fail_general();
    return eval_Body_value;
  }
  /**
   * A parse name can only be disambiguated if it is not qualified by an
   * expression containing another unresolved parse name.
   * @attribute inh
   * @aspect NameResolution
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:406
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.INH)
  @ASTNodeAnnotation.Source(aspect="NameResolution", declaredAt="/home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:406")
  public boolean canResolve() {
    boolean canResolve_value = getParent().Define_canResolve(this, null);
    return canResolve_value;
  }
  /** @apilevel internal */
  public ASTNode rewriteTo() {
    // Declared at /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:434
    if (canResolve()) {
      return rewriteRule0();
    }
    return super.rewriteTo();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:434
   * @apilevel internal
   */
  private Access rewriteRule0() {
{
      Access lhs = null;
      switch (nameType()) {
        case PACKAGE_NAME:
          for (NamePart next : nameParts)
            lhs = lhs == null ? mkPkgName(next) : mkPkgName(lhs, next);

          return lhs;

        case TYPE_NAME: {
          // The first part of the name is the package part.
          for (int i = 0; i < nameParts.size() - 1; ++i)
            lhs = resolvePackageOrTypeName(lhs, nameParts.get(i));

          // The rest of the name is the type name.
          NamePart last = nameParts.get(nameParts.size() - 1);
          return lhs == null ? mkTypeName(last) : mkTypeName(lhs, last);
        }

        case PACKAGE_OR_TYPE_NAME:
          for (NamePart next : nameParts)
            lhs = resolvePackageOrTypeName(lhs, next);

          return lhs;

        case AMBIGUOUS_NAME:
          for (NamePart next : nameParts)
            lhs = resolveAmbiguousName(lhs, next);

          return lhs;

        case EXPRESSION_NAME: {
          // The first part of the name is the package part.
          for (int i = 0; i < nameParts.size() - 1; ++i)
            lhs = resolveAmbiguousName(lhs, nameParts.get(i));

          NamePart last = nameParts.get(nameParts.size() - 1);
          return lhs == null ? mkVarName(last) : mkVarName(lhs, last);
        }

        case NOT_CLASSIFIED: break;
      }

      throw new Error("Failure in name classification: unknown name type encountered");
    }  }
  /** @apilevel internal */
  public boolean canRewrite() {
    // Declared at /home/olivier/projects/extendj/java4/frontend/ResolveAmbiguousNames.jrag:434
    if (canResolve()) {
      return true;
    }
    return false;
  }
  /** @apilevel internal */
  private void rewrittenNode_reset() {
    rewrittenNode_computed = false;
    rewrittenNode_initialized = false;
    rewrittenNode_value = null;
    rewrittenNode_cycle = null;
  }
/** @apilevel internal */
protected ASTState.Cycle rewrittenNode_cycle = null;
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
    ASTState state = state();
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
