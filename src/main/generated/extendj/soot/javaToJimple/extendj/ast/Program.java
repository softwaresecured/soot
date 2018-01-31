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
 * The root of a Java AST.
 * 
 * <p>Consists of multiple compilation units that represent the
 * source files of the program.
 * @ast node
 * @declaredat /home/olivier/projects/extendj/java4/grammar/Java.ast:37
 * @astdecl Program : ASTNode ::= CompilationUnit*;
 * @production Program : {@link ASTNode} ::= <span class="component">{@link CompilationUnit}*</span>;

 */
public class Program extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @aspect FrontendMain
   * @declaredat /home/olivier/projects/extendj/java4/frontend/FrontendMain.jrag:37
   */
  public long javaParseTime;
  /**
   * @aspect FrontendMain
   * @declaredat /home/olivier/projects/extendj/java4/frontend/FrontendMain.jrag:38
   */
  public long bytecodeParseTime;
  /**
   * @aspect FrontendMain
   * @declaredat /home/olivier/projects/extendj/java4/frontend/FrontendMain.jrag:39
   */
  public long codeGenTime;
  /**
   * @aspect FrontendMain
   * @declaredat /home/olivier/projects/extendj/java4/frontend/FrontendMain.jrag:40
   */
  public long errorCheckTime;
  /**
   * @aspect FrontendMain
   * @declaredat /home/olivier/projects/extendj/java4/frontend/FrontendMain.jrag:41
   */
  public int numJavaFiles;
  /**
   * @aspect FrontendMain
   * @declaredat /home/olivier/projects/extendj/java4/frontend/FrontendMain.jrag:42
   */
  public int numClassFiles;
  /**
   * Reset the profile statistics.
   * @aspect FrontendMain
   * @declaredat /home/olivier/projects/extendj/java4/frontend/FrontendMain.jrag:47
   */
  public void resetStatistics() {
    javaParseTime = 0;
    bytecodeParseTime = 0;
    codeGenTime = 0;
    errorCheckTime = 0;
    numJavaFiles = 0;
    numClassFiles = 0;
  }
  /**
   * @aspect FrontendMain
   * @declaredat /home/olivier/projects/extendj/java4/frontend/FrontendMain.jrag:56
   */
  public void printStatistics(PrintStream out) {
    out.println("javaParseTime: " + javaParseTime);
    out.println("numJavaFiles: " + numJavaFiles);
    out.println("bytecodeParseTime: " + bytecodeParseTime);
    out.println("numClassFiles: " + numClassFiles);
    out.println("errorCheckTime: " + errorCheckTime);
    out.println("codeGenTime: " + codeGenTime);
  }
  /**
   * Returns a robust iterator that can be iterated while the colleciton is updated.
   * @aspect LibraryCompilationUnits
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LibCompilationUnits.jadd:35
   */
  public Iterator<CompilationUnit> libraryCompilationUnitIterator() {
    return libraryCompilationUnitSet.iterator();
  }
  /**
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:133
   */
  public int classFileReadTime;
  /**
   * Cache for source type lookups.
   * 
   * <p>Should only be accessed via Program.lookupSourceType(String,String)!
   * 
   * <p>This cache is important in order to make all source types shadow
   * library types with matching names, even when the source type lives in a
   * compilation unit with a different name.
   * 
   * <p>When loading a compilation unit, all additional types in the compilation unit
   * must become visible after the type lookup for the type with the same name as the
   * compilation unit. This map ensures that additional types become visible.
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:170
   */
  private final Map<String, TypeDecl> sourceTypeMap = new HashMap<String, TypeDecl>();
  /**
   * Flag indictating if the source type map has already been initialized.
   * 
   * <p>Should only be accessed via Program.lookupSourceType(String,String)!
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:177
   */
  private boolean sourceTypeMapInitialized = false;
  /**
   * Lookup a type among source classes.
   * 
   * <p>Invoking this method may cause more than just the specified type to be
   * loaded, for example if there exists other types in the same source file,
   * the additional types are also loaded and memoized for the next lookup.
   * 
   * <p>This is a method rather than an attribute because it uses side-effects
   * to memoize additional types. The side effects are only observable via the
   * fields Program.sourceTypeMapInitialized and Program.sourceTypeMap.
   * These fields should only be used by this method to ensure that it is
   * observationally pure.
   * 
   * <p>This method is synchronized to ensure that concurrent type lookups
   * run sequentially.
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:195
   */
  protected synchronized TypeDecl lookupSourceType(String packageName, String typeName) {
    String fullName = packageName.equals("") ? typeName : packageName + "." + typeName;

    if (!sourceTypeMapInitialized) {
      initializeSourceTypeMap();
      sourceTypeMapInitialized = true;
    }

    if (sourceTypeMap.containsKey(fullName)) {
      return sourceTypeMap.get(fullName);
    } else {
      sourceTypeMap.put(fullName, unknownType());
    }

    // Source type not found: lookup library type instead.
    return unknownType();
  }
  /**
   * Initialize source types in the source type map.  This puts all the types provided by
   * Program.addSourceFile() in a map for lookup by Program.lookupSourceType.
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:217
   */
  private void initializeSourceTypeMap() {
    // Initialize source type map with the compilation units supplied by Program.addSourceFile.
    for (int i = 0; i < getNumCompilationUnit(); i++) {
      CompilationUnit unit = getCompilationUnit(i);
      for (int j = 0; j < unit.getNumTypeDecl(); j++) {
        TypeDecl type = unit.getTypeDecl(j);
        sourceTypeMap.put(type.fullName(), type);
      }
    }
  }
  /**
   * Extra cache for library type lookups.
   * 
   * <p>Should only be accessed via Program.lookupLibraryType(String,String)!
   * 
   * <p>This cache is needed to be able to track library types that are
   * declared in compilation units with a different name than the type itself.
   * Note that this only affects library types loaded from source (unfortunately easy
   * to confuse with sourceTypeMap).
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:238
   */
  private final Map<String, TypeDecl> libraryTypeMap = new HashMap<String, TypeDecl>();
  /**
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:240
   */
  private final Set<CompilationUnit> libraryCompilationUnitSet =
      new RobustSet<CompilationUnit>(new HashSet<CompilationUnit>());
  /**
   * Flag indictating if the library type map has already been initialized.
   * 
   * <p>Should only be accessed via Program.lookupLibraryType(String,String)!
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:248
   */
  private boolean libraryTypeMapInitialized = false;
  /**
   * Lookup a type among library classes. The lookup includes Jar and source files.
   * 
   * <p>Invoking this method may cause more than just the specified type to be loaded, for
   * example if there exists other types in the same source file, the additional
   * types are also loaded and memoized for the next lookup.
   * 
   * <p>This is a method rather than an attribute because it uses side-effects
   * to memoize additional types. The side effects are only observable via the
   * fields Program.libraryTypeMapInitialized and Program.libraryTypeMap.
   * These fields should only be used by this method to ensure that it is
   * observationally pure.
   * 
   * <p>This method is synchronized to ensure that concurrent type lookups
   * run sequentially.
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:266
   */
  protected synchronized TypeDecl lookupLibraryType(String packageName, String typeName) {
    String fullName = packageName.isEmpty() ? typeName : packageName + "." + typeName;

    if (!libraryTypeMapInitialized) {
      initializeLibraryTypeMap();
      libraryTypeMapInitialized = true;
    }

    if (libraryTypeMap.containsKey(fullName)) {
      return libraryTypeMap.get(fullName);
    }

    // Lookup the type in the library class path.
    CompilationUnit libraryUnit = getLibCompilationUnit(fullName);

    // Store the compilation unit in a set for later introspection of loaded compilation units.
    libraryCompilationUnitSet.add(libraryUnit);

    // Add all types from the compilation unit in the library type map so that we can find them on
    // the next type lookup. If we don't do this lookup might incorrectly miss a type that is not
    // declared in a Java source file with a matching name.
    for (int j = 0; j < libraryUnit.getNumTypeDecl(); j++) {
      TypeDecl type = libraryUnit.getTypeDecl(j);
      if (!libraryTypeMap.containsKey(type.fullName())) {
        libraryTypeMap.put(type.fullName(), type);
      }
    }

    if (libraryTypeMap.containsKey(fullName)) {
      return libraryTypeMap.get(fullName);
    } else {
      libraryTypeMap.put(fullName, unknownType());
      return unknownType();
    }
  }
  /** Initialize primitive types in the library type map.  
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:303
   */
  private void initializeLibraryTypeMap() {
    PrimitiveCompilationUnit unit = getPrimitiveCompilationUnit();
    libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".boolean", unit.typeBoolean());
    libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".byte", unit.typeByte());
    libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".short", unit.typeShort());
    libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".char", unit.typeChar());
    libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".int", unit.typeInt());
    libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".long", unit.typeLong());
    libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".float", unit.typeFloat());
    libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".double", unit.typeDouble());
    libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".null", unit.typeNull());
    libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".void", unit.typeVoid());
    libraryTypeMap.put(PRIMITIVE_PACKAGE_NAME + ".Unknown", unit.unknownType());
  }
  /**
   * @aspect AddOptionsToProgram
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Options.jadd:34
   */
  public Options options = new Options();
  /**
   * @aspect AddOptionsToProgram
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Options.jadd:36
   */
  public Options options() {
    return options;
  }
  /**
   * @aspect PrettyPrintUtil
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrettyPrintUtil.jrag:176
   */
  public void prettyPrint(PrettyPrinter out) {
    for (Iterator iter = compilationUnitIterator(); iter.hasNext(); ) {
      CompilationUnit cu = (CompilationUnit) iter.next();
      if (cu.fromSource()) {
        out.print(cu);
      }
    }
  }
  /**
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:32
   */
  public void jimpleDeclare() {
    for (Iterator<CompilationUnit> iter = compilationUnitIterator(); iter.hasNext(); ) {
      CompilationUnit u = (CompilationUnit)iter.next();
      if (u.fromSource())
        u.jimpleDeclare();
    }
  }
  /**
   * @aspect EmitJimple
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/EmitJimple.jrag:279
   */
  public void jimpleDefineTopLevelTerms() {
    for (Iterator<CompilationUnit> iter = compilationUnitIterator(); iter.hasNext(); ) {
      CompilationUnit u = iter.next();
      if (u.fromSource())
        u.jimpleDefineTopLevelTerms();
    }
  }
  /**
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:45
   */
  private final HashMap<String, TypeDecl> lookupByJvmName         = new HashMap<>();
  /**
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:46
   */
  private       HashSet<CompilationUnit> lookupByJvmName_pending = new HashSet<>();
  /**
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:48
   */
  public synchronized TypeDecl lookupTypeByJvmName(String jvmName) {
    HashSet<CompilationUnit> us = lookupByJvmName_pending;
    lookupByJvmName_pending = new HashSet<>();

    for (CompilationUnit  u : us)
    for (TypeDecl         d : u.getTypeDecls())
      lookupTypeByJvmName_addTypeDecl(d);

    return lookupByJvmName.containsKey(jvmName) ? lookupByJvmName.get(jvmName) : unknownType();
  }
  /**
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:59
   */
  private void lookupTypeByJvmName_addTypeDecl(TypeDecl d) {
    d = d.erasure();
    lookupByJvmName.put(d.jvmName(), d);
    for (TypeDecl x : d.nestedTypes())
      lookupTypeByJvmName_addTypeDecl(x);
  }
  /**
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:143
   */
  protected BytecodeReader bytecodeReader = defaultBytecodeReader();
  /**
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:145
   */
  public void initBytecodeReader(BytecodeReader r) {
    bytecodeReader = r;
  }
  /**
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:149
   */
  public static BytecodeReader defaultBytecodeReader() {
    return new BytecodeReader() {
      @Override
      public CompilationUnit read(InputStream is, String fullName, Program p)
          throws FileNotFoundException, IOException {
        return new BytecodeParser(is, fullName).parse(null, null, p);
      }
    };
  }
  /**
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:159
   */
  protected JavaParser javaParser = defaultJavaParser();
  /**
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:161
   */
  public void initJavaParser(JavaParser p) {
    javaParser = p;
  }
  /**
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:165
   */
  public static JavaParser defaultJavaParser() {
    return new JavaParser() {
      @Override
      public CompilationUnit parse(InputStream is, String fileName)
          throws IOException, beaver.Parser.Exception {
        return new soot.javaToJimple.extendj.parser.JavaParser().parse(is, fileName);
      }
    };
  }
  /**
   * Creates an iterator to iterate over compilation units parsed from source files.
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:201
   */
  public Iterator<CompilationUnit> compilationUnitIterator() {
    return new Iterator<CompilationUnit>() {
      int index = 0;

      @Override
      public boolean hasNext() {
        return index < getNumCompilationUnit();
      }

      @Override
      public CompilationUnit next() {
        if (getNumCompilationUnit() == index) {
          throw new java.util.NoSuchElementException();
        }
        return getCompilationUnit(index++);
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
  /**
   * Get the input stream for a compilation unit specified using a canonical
   * name. This is used by the bytecode reader to load nested types.
   * @param name The canonical name of the compilation unit.
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:230
   */
  public InputStream getInputStream(String name) {
    return classPath.getInputStream(name);
  }
  /**
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:234
   */
  private final ClassPath classPath = new ClassPath(this);
  /**
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:490
   */
  private int srcPrec = ClassPath.SRC_PREC_CLASS;
  /**
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:491
   */
  public void setSrcPrec(int i) {
    srcPrec = i;
  }
  /**
   * @return <code>true</code> if there is a package with the given name on
   * the classpath
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:519
   */
  public boolean isPackage(String packageName) {
    return classPath.isPackage(packageName);
  }
  /**
   * Add a path part to the library class path.
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:542
   */
  public void addClassPath(PathPart pathPart) {
    classPath.addClassPath(pathPart);
  }
  /**
   * Add a path part to the user class path.
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:549
   */
  public void addSourcePath(PathPart pathPart) {
    classPath.addSourcePath(pathPart);
  }
  /**
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:558
   */
  public void addSourceFile(String fileName)
  { throw new Error("UNUSED/NOT-IMPL: Only here as a stub for unused `Frontend` class."); }
  /**
   * @declaredat ASTNode:1
   */
  public Program() {
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
    setChild(new List(), 0);
  }
  /**
   * @declaredat ASTNode:14
   */
  @ASTNodeAnnotation.Constructor(
    name = {"CompilationUnit"},
    type = {"List<CompilationUnit>"},
    kind = {"List"}
  )
  public Program(List<CompilationUnit> p0) {
    setChild(p0, 0);
  }
  /** @apilevel low-level 
   * @declaredat ASTNode:23
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:29
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:33
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    typeObject_reset();
    typeCloneable_reset();
    typeSerializable_reset();
    typeBoolean_reset();
    typeByte_reset();
    typeShort_reset();
    typeChar_reset();
    typeInt_reset();
    typeLong_reset();
    typeFloat_reset();
    typeDouble_reset();
    typeString_reset();
    typeVoid_reset();
    typeNull_reset();
    unknownType_reset();
    hasPackage_String_reset();
    lookupType_String_String_reset();
    getLibCompilationUnit_String_reset();
    emptyCompilationUnit_reset();
    getPrimitiveCompilationUnit_reset();
    unknownConstructor_reset();
    wildcards_reset();
    getCompilationUnit_String_reset();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:60
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /** @apilevel internal 
   * @declaredat ASTNode:64
   */
  public Program clone() throws CloneNotSupportedException {
    Program node = (Program) super.clone();
    return node;
  }
  /** @apilevel internal 
   * @declaredat ASTNode:69
   */
  public Program copy() {
    try {
      Program node = (Program) clone();
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
   * @declaredat ASTNode:88
   */
  @Deprecated
  public Program fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:98
   */
  public Program treeCopyNoTransform() {
    Program tree = (Program) copy();
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
   * @declaredat ASTNode:118
   */
  public Program treeCopy() {
    Program tree = (Program) copy();
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
   * @declaredat ASTNode:132
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the CompilationUnit list.
   * @param list The new list node to be used as the CompilationUnit list.
   * @apilevel high-level
   */
  public void setCompilationUnitList(List<CompilationUnit> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the CompilationUnit list.
   * @return Number of children in the CompilationUnit list.
   * @apilevel high-level
   */
  public int getNumCompilationUnit() {
    return getCompilationUnitList().getNumChild();
  }
  /**
   * Retrieves the number of children in the CompilationUnit list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the CompilationUnit list.
   * @apilevel low-level
   */
  public int getNumCompilationUnitNoTransform() {
    return getCompilationUnitListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the CompilationUnit list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the CompilationUnit list.
   * @apilevel high-level
   */
  public CompilationUnit getCompilationUnit(int i) {
    return (CompilationUnit) getCompilationUnitList().getChild(i);
  }
  /**
   * Check whether the CompilationUnit list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasCompilationUnit() {
    return getCompilationUnitList().getNumChild() != 0;
  }
  /**
   * Append an element to the CompilationUnit list.
   * @param node The element to append to the CompilationUnit list.
   * @apilevel high-level
   */
  public void addCompilationUnit(CompilationUnit node) {
    List<CompilationUnit> list = (parent == null) ? getCompilationUnitListNoTransform() : getCompilationUnitList();
    list.addChild(node);
  }
  /** @apilevel low-level 
   */
  public void addCompilationUnitNoTransform(CompilationUnit node) {
    List<CompilationUnit> list = getCompilationUnitListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the CompilationUnit list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setCompilationUnit(CompilationUnit node, int i) {
    List<CompilationUnit> list = getCompilationUnitList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the CompilationUnit list.
   * @return The node representing the CompilationUnit list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="CompilationUnit")
  public List<CompilationUnit> getCompilationUnitList() {
    List<CompilationUnit> list = (List<CompilationUnit>) getChild(0);
    return list;
  }
  /**
   * Retrieves the CompilationUnit list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the CompilationUnit list.
   * @apilevel low-level
   */
  public List<CompilationUnit> getCompilationUnitListNoTransform() {
    return (List<CompilationUnit>) getChildNoTransform(0);
  }
  /**
   * @return the element at index {@code i} in the CompilationUnit list without
   * triggering rewrites.
   */
  public CompilationUnit getCompilationUnitNoTransform(int i) {
    return (CompilationUnit) getCompilationUnitListNoTransform().getChildNoTransform(i);
  }
  /**
   * Retrieves the CompilationUnit list.
   * @return The node representing the CompilationUnit list.
   * @apilevel high-level
   */
  public List<CompilationUnit> getCompilationUnits() {
    return getCompilationUnitList();
  }
  /**
   * Retrieves the CompilationUnit list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the CompilationUnit list.
   * @apilevel low-level
   */
  public List<CompilationUnit> getCompilationUnitsNoTransform() {
    return getCompilationUnitListNoTransform();
  }
  /** @apilevel internal */
  private void typeObject_reset() {
    typeObject_computed = null;
    typeObject_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeObject_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeObject_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:41
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:41")
  public TypeDecl typeObject() {
    ASTState state = state();
    if (typeObject_computed == ASTState.NON_CYCLE || typeObject_computed == state().cycle()) {
      return typeObject_value;
    }
    typeObject_value = lookupType("java.lang", "Object");
    if (state().inCircle()) {
      typeObject_computed = state().cycle();
    
    } else {
      typeObject_computed = ASTState.NON_CYCLE;
    
    }
    return typeObject_value;
  }
  /** @apilevel internal */
  private void typeCloneable_reset() {
    typeCloneable_computed = null;
    typeCloneable_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeCloneable_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeCloneable_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:42
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:42")
  public TypeDecl typeCloneable() {
    ASTState state = state();
    if (typeCloneable_computed == ASTState.NON_CYCLE || typeCloneable_computed == state().cycle()) {
      return typeCloneable_value;
    }
    typeCloneable_value = lookupType("java.lang", "Cloneable");
    if (state().inCircle()) {
      typeCloneable_computed = state().cycle();
    
    } else {
      typeCloneable_computed = ASTState.NON_CYCLE;
    
    }
    return typeCloneable_value;
  }
  /** @apilevel internal */
  private void typeSerializable_reset() {
    typeSerializable_computed = null;
    typeSerializable_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeSerializable_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeSerializable_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:43
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:43")
  public TypeDecl typeSerializable() {
    ASTState state = state();
    if (typeSerializable_computed == ASTState.NON_CYCLE || typeSerializable_computed == state().cycle()) {
      return typeSerializable_value;
    }
    typeSerializable_value = lookupType("java.io", "Serializable");
    if (state().inCircle()) {
      typeSerializable_computed = state().cycle();
    
    } else {
      typeSerializable_computed = ASTState.NON_CYCLE;
    
    }
    return typeSerializable_value;
  }
  /** @apilevel internal */
  private void typeBoolean_reset() {
    typeBoolean_computed = null;
    typeBoolean_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeBoolean_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeBoolean_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:49
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:49")
  public TypeDecl typeBoolean() {
    ASTState state = state();
    if (typeBoolean_computed == ASTState.NON_CYCLE || typeBoolean_computed == state().cycle()) {
      return typeBoolean_value;
    }
    typeBoolean_value = getPrimitiveCompilationUnit().typeBoolean();
    if (state().inCircle()) {
      typeBoolean_computed = state().cycle();
    
    } else {
      typeBoolean_computed = ASTState.NON_CYCLE;
    
    }
    return typeBoolean_value;
  }
  /** @apilevel internal */
  private void typeByte_reset() {
    typeByte_computed = null;
    typeByte_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeByte_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeByte_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:50
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:50")
  public TypeDecl typeByte() {
    ASTState state = state();
    if (typeByte_computed == ASTState.NON_CYCLE || typeByte_computed == state().cycle()) {
      return typeByte_value;
    }
    typeByte_value = getPrimitiveCompilationUnit().typeByte();
    if (state().inCircle()) {
      typeByte_computed = state().cycle();
    
    } else {
      typeByte_computed = ASTState.NON_CYCLE;
    
    }
    return typeByte_value;
  }
  /** @apilevel internal */
  private void typeShort_reset() {
    typeShort_computed = null;
    typeShort_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeShort_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeShort_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:51
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:51")
  public TypeDecl typeShort() {
    ASTState state = state();
    if (typeShort_computed == ASTState.NON_CYCLE || typeShort_computed == state().cycle()) {
      return typeShort_value;
    }
    typeShort_value = getPrimitiveCompilationUnit().typeShort();
    if (state().inCircle()) {
      typeShort_computed = state().cycle();
    
    } else {
      typeShort_computed = ASTState.NON_CYCLE;
    
    }
    return typeShort_value;
  }
  /** @apilevel internal */
  private void typeChar_reset() {
    typeChar_computed = null;
    typeChar_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeChar_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeChar_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:52
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:52")
  public TypeDecl typeChar() {
    ASTState state = state();
    if (typeChar_computed == ASTState.NON_CYCLE || typeChar_computed == state().cycle()) {
      return typeChar_value;
    }
    typeChar_value = getPrimitiveCompilationUnit().typeChar();
    if (state().inCircle()) {
      typeChar_computed = state().cycle();
    
    } else {
      typeChar_computed = ASTState.NON_CYCLE;
    
    }
    return typeChar_value;
  }
  /** @apilevel internal */
  private void typeInt_reset() {
    typeInt_computed = null;
    typeInt_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeInt_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeInt_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:53
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:53")
  public TypeDecl typeInt() {
    ASTState state = state();
    if (typeInt_computed == ASTState.NON_CYCLE || typeInt_computed == state().cycle()) {
      return typeInt_value;
    }
    typeInt_value = getPrimitiveCompilationUnit().typeInt();
    if (state().inCircle()) {
      typeInt_computed = state().cycle();
    
    } else {
      typeInt_computed = ASTState.NON_CYCLE;
    
    }
    return typeInt_value;
  }
  /** @apilevel internal */
  private void typeLong_reset() {
    typeLong_computed = null;
    typeLong_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeLong_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeLong_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:54
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:54")
  public TypeDecl typeLong() {
    ASTState state = state();
    if (typeLong_computed == ASTState.NON_CYCLE || typeLong_computed == state().cycle()) {
      return typeLong_value;
    }
    typeLong_value = getPrimitiveCompilationUnit().typeLong();
    if (state().inCircle()) {
      typeLong_computed = state().cycle();
    
    } else {
      typeLong_computed = ASTState.NON_CYCLE;
    
    }
    return typeLong_value;
  }
  /** @apilevel internal */
  private void typeFloat_reset() {
    typeFloat_computed = null;
    typeFloat_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeFloat_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeFloat_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:55
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:55")
  public TypeDecl typeFloat() {
    ASTState state = state();
    if (typeFloat_computed == ASTState.NON_CYCLE || typeFloat_computed == state().cycle()) {
      return typeFloat_value;
    }
    typeFloat_value = getPrimitiveCompilationUnit().typeFloat();
    if (state().inCircle()) {
      typeFloat_computed = state().cycle();
    
    } else {
      typeFloat_computed = ASTState.NON_CYCLE;
    
    }
    return typeFloat_value;
  }
  /** @apilevel internal */
  private void typeDouble_reset() {
    typeDouble_computed = null;
    typeDouble_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeDouble_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeDouble_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:56
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:56")
  public TypeDecl typeDouble() {
    ASTState state = state();
    if (typeDouble_computed == ASTState.NON_CYCLE || typeDouble_computed == state().cycle()) {
      return typeDouble_value;
    }
    typeDouble_value = getPrimitiveCompilationUnit().typeDouble();
    if (state().inCircle()) {
      typeDouble_computed = state().cycle();
    
    } else {
      typeDouble_computed = ASTState.NON_CYCLE;
    
    }
    return typeDouble_value;
  }
  /** @apilevel internal */
  private void typeString_reset() {
    typeString_computed = null;
    typeString_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeString_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeString_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:57
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:57")
  public TypeDecl typeString() {
    ASTState state = state();
    if (typeString_computed == ASTState.NON_CYCLE || typeString_computed == state().cycle()) {
      return typeString_value;
    }
    typeString_value = lookupType("java.lang", "String");
    if (state().inCircle()) {
      typeString_computed = state().cycle();
    
    } else {
      typeString_computed = ASTState.NON_CYCLE;
    
    }
    return typeString_value;
  }
  /** @apilevel internal */
  private void typeVoid_reset() {
    typeVoid_computed = null;
    typeVoid_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeVoid_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeVoid_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:69
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:69")
  public TypeDecl typeVoid() {
    ASTState state = state();
    if (typeVoid_computed == ASTState.NON_CYCLE || typeVoid_computed == state().cycle()) {
      return typeVoid_value;
    }
    typeVoid_value = getPrimitiveCompilationUnit().typeVoid();
    if (state().inCircle()) {
      typeVoid_computed = state().cycle();
    
    } else {
      typeVoid_computed = ASTState.NON_CYCLE;
    
    }
    return typeVoid_value;
  }
  /** @apilevel internal */
  private void typeNull_reset() {
    typeNull_computed = null;
    typeNull_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle typeNull_computed = null;

  /** @apilevel internal */
  protected TypeDecl typeNull_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:72
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:72")
  public TypeDecl typeNull() {
    ASTState state = state();
    if (typeNull_computed == ASTState.NON_CYCLE || typeNull_computed == state().cycle()) {
      return typeNull_value;
    }
    typeNull_value = getPrimitiveCompilationUnit().typeNull();
    if (state().inCircle()) {
      typeNull_computed = state().cycle();
    
    } else {
      typeNull_computed = ASTState.NON_CYCLE;
    
    }
    return typeNull_value;
  }
  /** @apilevel internal */
  private void unknownType_reset() {
    unknownType_computed = null;
    unknownType_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle unknownType_computed = null;

  /** @apilevel internal */
  protected TypeDecl unknownType_value;

  /**
   * @attribute syn
   * @aspect SpecialClasses
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:75
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="SpecialClasses", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:75")
  public TypeDecl unknownType() {
    ASTState state = state();
    if (unknownType_computed == ASTState.NON_CYCLE || unknownType_computed == state().cycle()) {
      return unknownType_value;
    }
    unknownType_value = getPrimitiveCompilationUnit().unknownType();
    if (state().inCircle()) {
      unknownType_computed = state().cycle();
    
    } else {
      unknownType_computed = ASTState.NON_CYCLE;
    
    }
    return unknownType_value;
  }
  /** @apilevel internal */
  private void hasPackage_String_reset() {
    hasPackage_String_computed = null;
    hasPackage_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map hasPackage_String_values;
  /** @apilevel internal */
  protected java.util.Map hasPackage_String_computed;
  /**
   * @attribute syn
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:105
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupFullyQualifiedTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:105")
  public boolean hasPackage(String packageName) {
    Object _parameters = packageName;
    if (hasPackage_String_computed == null) hasPackage_String_computed = new java.util.HashMap(4);
    if (hasPackage_String_values == null) hasPackage_String_values = new java.util.HashMap(4);
    ASTState state = state();
    if (hasPackage_String_values.containsKey(_parameters)
        && hasPackage_String_computed.containsKey(_parameters)
        && (hasPackage_String_computed.get(_parameters) == ASTState.NON_CYCLE || hasPackage_String_computed.get(_parameters) == state().cycle())) {
      return (Boolean) hasPackage_String_values.get(_parameters);
    }
    boolean hasPackage_String_value = isPackage(packageName);
    if (state().inCircle()) {
      hasPackage_String_values.put(_parameters, hasPackage_String_value);
      hasPackage_String_computed.put(_parameters, state().cycle());
    
    } else {
      hasPackage_String_values.put(_parameters, hasPackage_String_value);
      hasPackage_String_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return hasPackage_String_value;
  }
  /** @apilevel internal */
  private void lookupType_String_String_reset() {
    lookupType_String_String_computed = null;
    lookupType_String_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map lookupType_String_String_values;
  /** @apilevel internal */
  protected java.util.Map lookupType_String_String_computed;
  /**
   * Checks from-source compilation units for the given type.
   * If no matching compilation unit is found the library compliation units
   * will be searched.
   * @attribute syn
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:146
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="LookupFullyQualifiedTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:146")
  public TypeDecl lookupType(String packageName, String typeName) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(packageName);
    _parameters.add(typeName);
    if (lookupType_String_String_computed == null) lookupType_String_String_computed = new java.util.HashMap(4);
    if (lookupType_String_String_values == null) lookupType_String_String_values = new java.util.HashMap(4);
    ASTState state = state();
    if (lookupType_String_String_values.containsKey(_parameters)
        && lookupType_String_String_computed.containsKey(_parameters)
        && (lookupType_String_String_computed.get(_parameters) == ASTState.NON_CYCLE || lookupType_String_String_computed.get(_parameters) == state().cycle())) {
      return (TypeDecl) lookupType_String_String_values.get(_parameters);
    }
    TypeDecl lookupType_String_String_value = lookupType_compute(packageName, typeName);
    if (state().inCircle()) {
      lookupType_String_String_values.put(_parameters, lookupType_String_String_value);
      lookupType_String_String_computed.put(_parameters, state().cycle());
    
    } else {
      lookupType_String_String_values.put(_parameters, lookupType_String_String_value);
      lookupType_String_String_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return lookupType_String_String_value;
  }
  /** @apilevel internal */
  private TypeDecl lookupType_compute(String packageName, String typeName) {
      // Look for a matching source type.
      TypeDecl sourceType = lookupSourceType(packageName, typeName);
      if (!sourceType.isUnknown()) {
        return sourceType;
      }
  
      // Look for a matching library type.
      return lookupLibraryType(packageName, typeName);
    }
  /** @apilevel internal */
  private void getLibCompilationUnit_String_reset() {
    getLibCompilationUnit_String_values = null;
    getLibCompilationUnit_String_proxy = null;
  }
  /** @apilevel internal */
  protected ASTNode getLibCompilationUnit_String_proxy;
  /** @apilevel internal */
  protected java.util.Map getLibCompilationUnit_String_values;

  /**
   * This attribute is used to cache library compilation units, by storing the compilation units in
   * a parameterized NTA.
   * @attribute syn
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:322
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="LookupFullyQualifiedTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:322")
  public CompilationUnit getLibCompilationUnit(String typeName) {
    Object _parameters = typeName;
    if (getLibCompilationUnit_String_values == null) getLibCompilationUnit_String_values = new java.util.HashMap(4);
    ASTState state = state();
    if (getLibCompilationUnit_String_values.containsKey(_parameters)) {
      return (CompilationUnit) getLibCompilationUnit_String_values.get(_parameters);
    }
    state().enterLazyAttribute();
    CompilationUnit getLibCompilationUnit_String_value = getCompilationUnit(typeName);
    if (getLibCompilationUnit_String_proxy == null) {
      getLibCompilationUnit_String_proxy = new ASTNode();
      getLibCompilationUnit_String_proxy.setParent(this);
    }
    if (getLibCompilationUnit_String_value != null) {
      getLibCompilationUnit_String_value.setParent(getLibCompilationUnit_String_proxy);
      if (getLibCompilationUnit_String_value.mayHaveRewrite()) {
        getLibCompilationUnit_String_value = (CompilationUnit) getLibCompilationUnit_String_value.rewrittenNode();
        getLibCompilationUnit_String_value.setParent(getLibCompilationUnit_String_proxy);
      }
    }
    getLibCompilationUnit_String_values.put(_parameters, getLibCompilationUnit_String_value);
    state().leaveLazyAttribute();
    return getLibCompilationUnit_String_value;
  }
  /** @apilevel internal */
  private void emptyCompilationUnit_reset() {
    emptyCompilationUnit_computed = false;
    
    emptyCompilationUnit_value = null;
  }
  /** @apilevel internal */
  protected boolean emptyCompilationUnit_computed = false;

  /** @apilevel internal */
  protected CompilationUnit emptyCompilationUnit_value;

  /**
   * @attribute syn
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:325
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="LookupFullyQualifiedTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/LookupType.jrag:325")
  public CompilationUnit emptyCompilationUnit() {
    ASTState state = state();
    if (emptyCompilationUnit_computed) {
      return emptyCompilationUnit_value;
    }
    state().enterLazyAttribute();
    emptyCompilationUnit_value = new CompilationUnit();
    emptyCompilationUnit_value.setParent(this);
    emptyCompilationUnit_computed = true;
    state().leaveLazyAttribute();
    return emptyCompilationUnit_value;
  }
  /** @apilevel internal */
  private void getPrimitiveCompilationUnit_reset() {
    getPrimitiveCompilationUnit_computed = false;
    
    getPrimitiveCompilationUnit_value = null;
  }
  /** @apilevel internal */
  protected boolean getPrimitiveCompilationUnit_computed = false;

  /** @apilevel internal */
  protected PrimitiveCompilationUnit getPrimitiveCompilationUnit_value;

  /** Creates a compilation unit with primitive types. 
   * @attribute syn
   * @aspect PrimitiveTypes
   * @declaredat /home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:155
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="PrimitiveTypes", declaredAt="/home/olivier/projects/extendj/java4/frontend/PrimitiveTypes.jrag:155")
  public PrimitiveCompilationUnit getPrimitiveCompilationUnit() {
    ASTState state = state();
    if (getPrimitiveCompilationUnit_computed) {
      return getPrimitiveCompilationUnit_value;
    }
    state().enterLazyAttribute();
    getPrimitiveCompilationUnit_value = getPrimitiveCompilationUnit_compute();
    getPrimitiveCompilationUnit_value.setParent(this);
    getPrimitiveCompilationUnit_computed = true;
    state().leaveLazyAttribute();
    return getPrimitiveCompilationUnit_value;
  }
  /** @apilevel internal */
  private PrimitiveCompilationUnit getPrimitiveCompilationUnit_compute() {
      PrimitiveCompilationUnit u = new PrimitiveCompilationUnit();
      u.setPackageDecl(PRIMITIVE_PACKAGE_NAME);
      return u;
    }
  /** @apilevel internal */
  private void unknownConstructor_reset() {
    unknownConstructor_computed = null;
    unknownConstructor_value = null;
  }
  /** @apilevel internal */
  protected ASTState.Cycle unknownConstructor_computed = null;

  /** @apilevel internal */
  protected ConstructorDecl unknownConstructor_value;

  /**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:263
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="TypeAnalysis", declaredAt="/home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:263")
  public ConstructorDecl unknownConstructor() {
    ASTState state = state();
    if (unknownConstructor_computed == ASTState.NON_CYCLE || unknownConstructor_computed == state().cycle()) {
      return unknownConstructor_value;
    }
    unknownConstructor_value = unknownType().constructors().iterator().next();
    if (state().inCircle()) {
      unknownConstructor_computed = state().cycle();
    
    } else {
      unknownConstructor_computed = ASTState.NON_CYCLE;
    
    }
    return unknownConstructor_value;
  }
  /** @apilevel internal */
  private void wildcards_reset() {
    wildcards_computed = false;
    
    wildcards_value = null;
  }
  /** @apilevel internal */
  protected boolean wildcards_computed = false;

  /** @apilevel internal */
  protected WildcardsCompilationUnit wildcards_value;

  /**
   * @attribute syn
   * @aspect LookupParTypeDecl
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1720
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN, isNTA=true)
  @ASTNodeAnnotation.Source(aspect="LookupParTypeDecl", declaredAt="/home/olivier/projects/extendj/java5/frontend/Generics.jrag:1720")
  public WildcardsCompilationUnit wildcards() {
    ASTState state = state();
    if (wildcards_computed) {
      return wildcards_value;
    }
    state().enterLazyAttribute();
    wildcards_value = new WildcardsCompilationUnit(
              "wildcards",
              new List(),
              new List());
    wildcards_value.setParent(this);
    wildcards_computed = true;
    state().leaveLazyAttribute();
    return wildcards_value;
  }
  /** @apilevel internal */
  private void getCompilationUnit_String_reset() {
    getCompilationUnit_String_computed = null;
    getCompilationUnit_String_values = null;
  }
  /** @apilevel internal */
  protected java.util.Map getCompilationUnit_String_values;
  /** @apilevel internal */
  protected java.util.Map getCompilationUnit_String_computed;
  /**
   * Load a compilation unit from disk, selecting a class file
   * if one exists that is not older than a corresponding source
   * file, otherwise the source file is selected.
   * 
   * <p>This method is called by the LibCompilationUnit NTA.  We rely on the result of this method
   * being cached because it will return a newly parsed compilation unit each time it is called.
   * 
   * @return the loaded compilation unit, or the empty compilation unit if no compilation unit was
   * found.
   * @attribute syn
   * @aspect ClassPath
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:506
   */
  @ASTNodeAnnotation.Attribute(kind=ASTNodeAnnotation.Kind.SYN)
  @ASTNodeAnnotation.Source(aspect="ClassPath", declaredAt="/home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:506")
  public CompilationUnit getCompilationUnit(String typeName) {
    Object _parameters = typeName;
    if (getCompilationUnit_String_computed == null) getCompilationUnit_String_computed = new java.util.HashMap(4);
    if (getCompilationUnit_String_values == null) getCompilationUnit_String_values = new java.util.HashMap(4);
    ASTState state = state();
    if (getCompilationUnit_String_values.containsKey(_parameters)
        && getCompilationUnit_String_computed.containsKey(_parameters)
        && (getCompilationUnit_String_computed.get(_parameters) == ASTState.NON_CYCLE || getCompilationUnit_String_computed.get(_parameters) == state().cycle())) {
      return (CompilationUnit) getCompilationUnit_String_values.get(_parameters);
    }
    CompilationUnit getCompilationUnit_String_value = getCompilationUnit_compute(typeName);
    if (state().inCircle()) {
      getCompilationUnit_String_values.put(_parameters, getCompilationUnit_String_value);
      getCompilationUnit_String_computed.put(_parameters, state().cycle());
    
    } else {
      getCompilationUnit_String_values.put(_parameters, getCompilationUnit_String_value);
      getCompilationUnit_String_computed.put(_parameters, ASTState.NON_CYCLE);
    
    }
    return getCompilationUnit_String_value;
  }
  /** @apilevel internal */
  private CompilationUnit getCompilationUnit_compute(String typeName) {
      CompilationUnit u = classPath.getCompilationUnit(classPath.classSource(srcPrec, typeName)
                                                      ,typeName, emptyCompilationUnit());
      u.setParent(this);
  
      lookupByJvmName_pending.add(u);
      return u;
    }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/AnonymousClasses.jrag:33
   * @apilevel internal
   */
  public TypeDecl Define_superType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/AnonymousClasses.jrag:33
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute superType
   */
  protected boolean canDefine_superType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/AnonymousClasses.jrag:39
   * @apilevel internal
   */
  public ConstructorDecl Define_constructorDecl(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unknownConstructor();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/AnonymousClasses.jrag:39
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute constructorDecl
   */
  protected boolean canDefine_constructorDecl(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Arrays.jrag:56
   * @apilevel internal
   */
  public TypeDecl Define_componentType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unknownType();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Arrays.jrag:56
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute componentType
   */
  protected boolean canDefine_componentType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:255
   * @apilevel internal
   */
  public LabeledStmt Define_lookupLabel(ASTNode _callerNode, ASTNode _childNode, String name) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/BranchTarget.jrag:255
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupLabel
   */
  protected boolean canDefine_lookupLabel(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DeclareBeforeUse.jrag:35
   * @apilevel internal
   */
  public int Define_blockIndex(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return -1;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DeclareBeforeUse.jrag:35
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute blockIndex
   */
  protected boolean canDefine_blockIndex(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DeclareBeforeUse.jrag:58
   * @apilevel internal
   */
  public boolean Define_declaredBefore(ASTNode _callerNode, ASTNode _childNode, Variable decl) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DeclareBeforeUse.jrag:58
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute declaredBefore
   */
  protected boolean canDefine_declaredBefore(ASTNode _callerNode, ASTNode _childNode, Variable decl) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:34
   * @apilevel internal
   */
  public boolean Define_isDest(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:34
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isDest
   */
  protected boolean canDefine_isDest(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:44
   * @apilevel internal
   */
  public boolean Define_isSource(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:44
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isSource
   */
  protected boolean canDefine_isSource(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:66
   * @apilevel internal
   */
  public boolean Define_isIncOrDec(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:66
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isIncOrDec
   */
  protected boolean canDefine_isIncOrDec(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:256
   * @apilevel internal
   */
  public boolean Define_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:256
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute assignedBefore
   */
  protected boolean canDefine_assignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:887
   * @apilevel internal
   */
  public boolean Define_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/DefiniteAssignment.jrag:887
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute unassignedBefore
   */
  protected boolean canDefine_unassignedBefore(ASTNode _callerNode, ASTNode _childNode, Variable v) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:47
   * @apilevel internal
   */
  public TypeDecl Define_typeException(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return lookupType("java.lang", "Exception");
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:47
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeException
   */
  protected boolean canDefine_typeException(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:122
   * @apilevel internal
   */
  public TypeDecl Define_typeRuntimeException(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return lookupType("java.lang", "RuntimeException");
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:122
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeRuntimeException
   */
  protected boolean canDefine_typeRuntimeException(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:120
   * @apilevel internal
   */
  public TypeDecl Define_typeError(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return lookupType("java.lang", "Error");
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:120
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeError
   */
  protected boolean canDefine_typeError(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:56
   * @apilevel internal
   */
  public TypeDecl Define_typeNullPointerException(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return lookupType("java.lang", "NullPointerException");
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/ExceptionHandling.jrag:56
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeNullPointerException
   */
  protected boolean canDefine_typeNullPointerException(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:97
   * @apilevel internal
   */
  public TypeDecl Define_typeThrowable(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return lookupType("java.lang", "Throwable");
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:97
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeThrowable
   */
  protected boolean canDefine_typeThrowable(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:112
   * @apilevel internal
   */
  public boolean Define_handlesException(ASTNode _callerNode, ASTNode _childNode, TypeDecl exceptionType) {
    int childIndex = this.getIndexOfChild(_callerNode);
    {
        throw new Error("Operation handlesException not supported");
      }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:112
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute handlesException
   */
  protected boolean canDefine_handlesException(ASTNode _callerNode, ASTNode _childNode, TypeDecl exceptionType) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:35
   * @apilevel internal
   */
  public Collection<ConstructorDecl> Define_lookupConstructor(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return Collections.emptyList();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:35
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupConstructor
   */
  protected boolean canDefine_lookupConstructor(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:43
   * @apilevel internal
   */
  public Collection<ConstructorDecl> Define_lookupSuperConstructor(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return Collections.emptyList();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupConstructor.jrag:43
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupSuperConstructor
   */
  protected boolean canDefine_lookupSuperConstructor(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:98
   * @apilevel internal
   */
  public Expr Define_nestedScope(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    {
        throw new UnsupportedOperationException(
            "Can not evaluate nestedScope() on unqualified access.");
      }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:98
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute nestedScope
   */
  protected boolean canDefine_nestedScope(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:116
   * @apilevel internal
   */
  public Collection<MethodDecl> Define_lookupMethod(ASTNode _callerNode, ASTNode _childNode, String name) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return Collections.EMPTY_LIST;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupMethod.jrag:116
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupMethod
   */
  protected boolean canDefine_lookupMethod(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1290
   * @apilevel internal
   */
  public TypeDecl Define_typeObject(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeObject();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1290
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeObject
   */
  protected boolean canDefine_typeObject(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:151
   * @apilevel internal
   */
  public TypeDecl Define_typeCloneable(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeCloneable();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:151
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeCloneable
   */
  protected boolean canDefine_typeCloneable(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:150
   * @apilevel internal
   */
  public TypeDecl Define_typeSerializable(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeSerializable();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:150
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeSerializable
   */
  protected boolean canDefine_typeSerializable(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:78
   * @apilevel internal
   */
  public TypeDecl Define_typeBoolean(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeBoolean();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:78
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeBoolean
   */
  protected boolean canDefine_typeBoolean(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:79
   * @apilevel internal
   */
  public TypeDecl Define_typeByte(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeByte();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:79
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeByte
   */
  protected boolean canDefine_typeByte(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:80
   * @apilevel internal
   */
  public TypeDecl Define_typeShort(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeShort();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:80
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeShort
   */
  protected boolean canDefine_typeShort(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:81
   * @apilevel internal
   */
  public TypeDecl Define_typeChar(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeChar();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:81
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeChar
   */
  protected boolean canDefine_typeChar(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:90
   * @apilevel internal
   */
  public TypeDecl Define_typeInt(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeInt();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:90
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeInt
   */
  protected boolean canDefine_typeInt(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:92
   * @apilevel internal
   */
  public TypeDecl Define_typeLong(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeLong();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:92
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeLong
   */
  protected boolean canDefine_typeLong(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:84
   * @apilevel internal
   */
  public TypeDecl Define_typeFloat(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeFloat();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:84
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeFloat
   */
  protected boolean canDefine_typeFloat(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:85
   * @apilevel internal
   */
  public TypeDecl Define_typeDouble(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeDouble();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:85
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeDouble
   */
  protected boolean canDefine_typeDouble(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:538
   * @apilevel internal
   */
  public TypeDecl Define_typeString(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeString();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Enums.jrag:538
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeString
   */
  protected boolean canDefine_typeString(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:87
   * @apilevel internal
   */
  public TypeDecl Define_typeVoid(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeVoid();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:87
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeVoid
   */
  protected boolean canDefine_typeVoid(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1300
   * @apilevel internal
   */
  public TypeDecl Define_typeNull(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeNull();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1300
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeNull
   */
  protected boolean canDefine_typeNull(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TypeCheck.jrag:32
   * @apilevel internal
   */
  public TypeDecl Define_unknownType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unknownType();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TypeCheck.jrag:32
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute unknownType
   */
  protected boolean canDefine_unknownType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:113
   * @apilevel internal
   */
  public boolean Define_hasPackage(ASTNode _callerNode, ASTNode _childNode, String packageName) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return hasPackage(packageName);
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupType.jrag:113
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute hasPackage
   */
  protected boolean canDefine_hasPackage(ASTNode _callerNode, ASTNode _childNode, String packageName) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:40
   * @apilevel internal
   */
  public TypeDecl Define_lookupType(ASTNode _callerNode, ASTNode _childNode, String packageName, String typeName) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return lookupType(packageName, typeName);
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:40
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupType
   */
  protected boolean canDefine_lookupType(ASTNode _callerNode, ASTNode _childNode, String packageName, String typeName) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethods.jrag:231
   * @apilevel internal
   */
  public SimpleSet<TypeDecl> Define_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return emptySet();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethods.jrag:231
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupType
   */
  protected boolean canDefine_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LookupVariable.jrag:30
   * @apilevel internal
   */
  public SimpleSet<Variable> Define_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return emptySet();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/LookupVariable.jrag:30
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupVariable
   */
  protected boolean canDefine_lookupVariable(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:430
   * @apilevel internal
   */
  public boolean Define_mayBePublic(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:430
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayBePublic
   */
  protected boolean canDefine_mayBePublic(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:432
   * @apilevel internal
   */
  public boolean Define_mayBeProtected(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:432
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayBeProtected
   */
  protected boolean canDefine_mayBeProtected(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:431
   * @apilevel internal
   */
  public boolean Define_mayBePrivate(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:431
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayBePrivate
   */
  protected boolean canDefine_mayBePrivate(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:433
   * @apilevel internal
   */
  public boolean Define_mayBeStatic(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:433
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayBeStatic
   */
  protected boolean canDefine_mayBeStatic(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:434
   * @apilevel internal
   */
  public boolean Define_mayBeFinal(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:434
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayBeFinal
   */
  protected boolean canDefine_mayBeFinal(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:435
   * @apilevel internal
   */
  public boolean Define_mayBeAbstract(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:435
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayBeAbstract
   */
  protected boolean canDefine_mayBeAbstract(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:436
   * @apilevel internal
   */
  public boolean Define_mayBeVolatile(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:436
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayBeVolatile
   */
  protected boolean canDefine_mayBeVolatile(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:437
   * @apilevel internal
   */
  public boolean Define_mayBeTransient(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:437
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayBeTransient
   */
  protected boolean canDefine_mayBeTransient(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:438
   * @apilevel internal
   */
  public boolean Define_mayBeStrictfp(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:438
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayBeStrictfp
   */
  protected boolean canDefine_mayBeStrictfp(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:439
   * @apilevel internal
   */
  public boolean Define_mayBeSynchronized(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:439
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayBeSynchronized
   */
  protected boolean canDefine_mayBeSynchronized(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:440
   * @apilevel internal
   */
  public boolean Define_mayBeNative(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Modifiers.jrag:440
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayBeNative
   */
  protected boolean canDefine_mayBeNative(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:376
   * @apilevel internal
   */
  public BodyDecl Define_enclosingMemberDecl(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:376
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute enclosingMemberDecl
   */
  protected boolean canDefine_enclosingMemberDecl(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:31
   * @apilevel internal
   */
  public VariableScope Define_outerScope(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    {
        throw new UnsupportedOperationException("outerScope() not defined");
      }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:31
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute outerScope
   */
  protected boolean canDefine_outerScope(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:523
   * @apilevel internal
   */
  public boolean Define_insideLoop(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:523
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute insideLoop
   */
  protected boolean canDefine_insideLoop(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:531
   * @apilevel internal
   */
  public boolean Define_insideSwitch(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:531
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute insideSwitch
   */
  protected boolean canDefine_insideSwitch(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:592
   * @apilevel internal
   */
  public Case Define_previousCase(ASTNode _callerNode, ASTNode _childNode, Case c) {
    int childIndex = this.getIndexOfChild(_callerNode);
    {
        throw new Error("Missing enclosing switch for case label.");
      }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/NameCheck.jrag:592
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute previousCase
   */
  protected boolean canDefine_previousCase(ASTNode _callerNode, ASTNode _childNode, Case c) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Options.jadd:40
   * @apilevel internal
   */
  public Program Define_program(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return this;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/Options.jadd:40
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute program
   */
  protected boolean canDefine_program(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   */
  public NameType Define_nameType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return NameType.NOT_CLASSIFIED;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/SyntacticClassification.jrag:36
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute nameType
   */
  protected boolean canDefine_nameType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:231
   * @apilevel internal
   */
  public boolean Define_isAnonymous(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:231
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isAnonymous
   */
  protected boolean canDefine_isAnonymous(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:340
   * @apilevel internal
   */
  public Variable Define_unknownField(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unknownType().findSingleVariable("unknown");
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/LookupVariable.jrag:340
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute unknownField
   */
  protected boolean canDefine_unknownField(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/MethodReference.jrag:31
   * @apilevel internal
   */
  public MethodDecl Define_unknownMethod(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    {
        for (MethodDecl m : unknownType().memberMethods("unknown")) {
          return m;
        }
        throw new Error("Could not find method unknown in type Unknown");
      }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/MethodReference.jrag:31
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute unknownMethod
   */
  protected boolean canDefine_unknownMethod(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/ConstructorReference.jrag:30
   * @apilevel internal
   */
  public ConstructorDecl Define_unknownConstructor(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unknownConstructor();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/ConstructorReference.jrag:30
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute unknownConstructor
   */
  protected boolean canDefine_unknownConstructor(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:723
   * @apilevel internal
   */
  public TypeDecl Define_declType(ASTNode _callerNode, ASTNode _childNode) {
    int i = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:723
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute declType
   */
  protected boolean canDefine_declType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:30
   * @apilevel internal
   */
  public BodyDecl Define_enclosingBodyDecl(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/NameCheck.jrag:30
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute enclosingBodyDecl
   */
  protected boolean canDefine_enclosingBodyDecl(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:584
   * @apilevel internal
   */
  public boolean Define_isMemberType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeAnalysis.jrag:584
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isMemberType
   */
  protected boolean canDefine_isMemberType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:76
   * @apilevel internal
   */
  public TypeDecl Define_hostType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:76
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute hostType
   */
  protected boolean canDefine_hostType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:484
   * @apilevel internal
   */
  public TypeDecl Define_switchType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unknownType();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:484
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute switchType
   */
  protected boolean canDefine_switchType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:536
   * @apilevel internal
   */
  public TypeDecl Define_returnType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeVoid();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:536
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute returnType
   */
  protected boolean canDefine_returnType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:669
   * @apilevel internal
   */
  public TypeDecl Define_enclosingInstance(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeCheck.jrag:669
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute enclosingInstance
   */
  protected boolean canDefine_enclosingInstance(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:33
   * @apilevel internal
   */
  public String Define_methodHost(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    {
        throw new Error("Needs extra equation for methodHost()");
      }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:33
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute methodHost
   */
  protected boolean canDefine_methodHost(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:204
   * @apilevel internal
   */
  public boolean Define_inExplicitConstructorInvocation(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:204
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute inExplicitConstructorInvocation
   */
  protected boolean canDefine_inExplicitConstructorInvocation(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:212
   * @apilevel internal
   */
  public TypeDecl Define_enclosingExplicitConstructorHostType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:212
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute enclosingExplicitConstructorHostType
   */
  protected boolean canDefine_enclosingExplicitConstructorHostType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:223
   * @apilevel internal
   */
  public boolean Define_inStaticContext(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/frontend/TypeHierarchyCheck.jrag:223
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute inStaticContext
   */
  protected boolean canDefine_inStaticContext(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:280
   * @apilevel internal
   */
  public boolean Define_reportUnreachable(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:280
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute reportUnreachable
   */
  protected boolean canDefine_reportUnreachable(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:44
   * @apilevel internal
   */
  public boolean Define_isMethodParameter(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:44
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isMethodParameter
   */
  protected boolean canDefine_isMethodParameter(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:45
   * @apilevel internal
   */
  public boolean Define_isConstructorParameter(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:45
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isConstructorParameter
   */
  protected boolean canDefine_isConstructorParameter(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:46
   * @apilevel internal
   */
  public boolean Define_isExceptionHandlerParameter(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:46
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isExceptionHandlerParameter
   */
  protected boolean canDefine_isExceptionHandlerParameter(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:104
   * @apilevel internal
   */
  public TypeDecl Define_expectedType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:104
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute expectedType
   */
  protected boolean canDefine_expectedType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:435
   * @apilevel internal
   */
  public ClassInstanceExpr Define_classInstanceExpression(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    {
        throw new Error("Missing enclosing class instance.");
      }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java4/backend/InnerClasses.jrag:435
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute classInstanceExpression
   */
  protected boolean canDefine_classInstanceExpression(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:131
   * @apilevel internal
   */
  public boolean Define_mayUseAnnotationTarget(ASTNode _callerNode, ASTNode _childNode, String name) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:131
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute mayUseAnnotationTarget
   */
  protected boolean canDefine_mayUseAnnotationTarget(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:278
   * @apilevel internal
   */
  public ElementValue Define_lookupElementTypeValue(ASTNode _callerNode, ASTNode _childNode, String name) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:278
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupElementTypeValue
   */
  protected boolean canDefine_lookupElementTypeValue(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/SuppressWarnings.jrag:37
   * @apilevel internal
   */
  public boolean Define_withinSuppressWarnings(ASTNode _callerNode, ASTNode _childNode, String annot) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/SuppressWarnings.jrag:37
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute withinSuppressWarnings
   */
  protected boolean canDefine_withinSuppressWarnings(ASTNode _callerNode, ASTNode _childNode, String annot) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:533
   * @apilevel internal
   */
  public boolean Define_withinDeprecatedAnnotation(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:533
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute withinDeprecatedAnnotation
   */
  protected boolean canDefine_withinDeprecatedAnnotation(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:604
   * @apilevel internal
   */
  public Annotation Define_lookupAnnotation(ASTNode _callerNode, ASTNode _childNode, TypeDecl typeDecl) {
    int i = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:604
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupAnnotation
   */
  protected boolean canDefine_lookupAnnotation(ASTNode _callerNode, ASTNode _childNode, TypeDecl typeDecl) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:648
   * @apilevel internal
   */
  public TypeDecl Define_enclosingAnnotationDecl(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return unknownType();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Annotations.jrag:648
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute enclosingAnnotationDecl
   */
  protected boolean canDefine_enclosingAnnotationDecl(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:69
   * @apilevel internal
   */
  public TypeDecl Define_assignConvertedType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeNull();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:69
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute assignConvertedType
   */
  protected boolean canDefine_assignConvertedType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:383
   * @apilevel internal
   */
  public boolean Define_inExtendsOrImplements(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:383
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute inExtendsOrImplements
   */
  protected boolean canDefine_inExtendsOrImplements(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1384
   * @apilevel internal
   */
  public FieldDecl Define_fieldDecl(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1384
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute fieldDecl
   */
  protected boolean canDefine_fieldDecl(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1734
   * @apilevel internal
   */
  public TypeDecl Define_typeWildcard(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return wildcards().typeWildcard();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1734
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute typeWildcard
   */
  protected boolean canDefine_typeWildcard(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1733
   * @apilevel internal
   */
  public TypeDecl Define_lookupWildcardExtends(ASTNode _callerNode, ASTNode _childNode, TypeDecl typeDecl) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return wildcards().lookupWildcardExtends(typeDecl);
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1733
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupWildcardExtends
   */
  protected boolean canDefine_lookupWildcardExtends(ASTNode _callerNode, ASTNode _childNode, TypeDecl typeDecl) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1732
   * @apilevel internal
   */
  public TypeDecl Define_lookupWildcardSuper(ASTNode _callerNode, ASTNode _childNode, TypeDecl typeDecl) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return wildcards().lookupWildcardSuper(typeDecl);
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1732
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupWildcardSuper
   */
  protected boolean canDefine_lookupWildcardSuper(ASTNode _callerNode, ASTNode _childNode, TypeDecl typeDecl) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:210
   * @apilevel internal
   */
  public LUBType Define_lookupLUBType(ASTNode _callerNode, ASTNode _childNode, Collection<TypeDecl> bounds) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return wildcards().lookupLUBType(bounds);
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/MultiCatch.jrag:210
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupLUBType
   */
  protected boolean canDefine_lookupLUBType(ASTNode _callerNode, ASTNode _childNode, Collection<TypeDecl> bounds) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1834
   * @apilevel internal
   */
  public GLBType Define_lookupGLBType(ASTNode _callerNode, ASTNode _childNode, Collection<TypeDecl> bounds) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return wildcards().lookupGLBType(bounds);
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1834
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute lookupGLBType
   */
  protected boolean canDefine_lookupGLBType(ASTNode _callerNode, ASTNode _childNode, Collection<TypeDecl> bounds) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsParTypeDecl.jrag:74
   * @apilevel internal
   */
  public TypeDecl Define_genericDecl(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericsParTypeDecl.jrag:74
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute genericDecl
   */
  protected boolean canDefine_genericDecl(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/VariableArityParameters.jrag:46
   * @apilevel internal
   */
  public boolean Define_variableArityValid(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java5/frontend/VariableArityParameters.jrag:46
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute variableArityValid
   */
  protected boolean canDefine_variableArityValid(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:106
   * @apilevel internal
   */
  public ClassInstanceExpr Define_getClassInstanceExpr(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:106
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute getClassInstanceExpr
   */
  protected boolean canDefine_getClassInstanceExpr(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:307
   * @apilevel internal
   */
  public boolean Define_isAnonymousDecl(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:307
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isAnonymousDecl
   */
  protected boolean canDefine_isAnonymousDecl(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:323
   * @apilevel internal
   */
  public boolean Define_isExplicitGenericConstructorAccess(ASTNode _callerNode, ASTNode _childNode) {
    int i = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/Diamond.jrag:323
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isExplicitGenericConstructorAccess
   */
  protected boolean canDefine_isExplicitGenericConstructorAccess(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:202
   * @apilevel internal
   */
  public boolean Define_isCatchParam(ASTNode _callerNode, ASTNode _childNode) {
    int i = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:202
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute isCatchParam
   */
  protected boolean canDefine_isCatchParam(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:209
   * @apilevel internal
   */
  public CatchClause Define_catchClause(ASTNode _callerNode, ASTNode _childNode) {
    int i = this.getIndexOfChild(_callerNode);
    {
        throw new IllegalStateException("Could not find parent " + "catch clause");
      }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/PreciseRethrow.jrag:209
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute catchClause
   */
  protected boolean canDefine_catchClause(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:178
   * @apilevel internal
   */
  public boolean Define_resourcePreviouslyDeclared(ASTNode _callerNode, ASTNode _childNode, String name) {
    int i = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java7/frontend/TryWithResources.jrag:178
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute resourcePreviouslyDeclared
   */
  protected boolean canDefine_resourcePreviouslyDeclared(ASTNode _callerNode, ASTNode _childNode, String name) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:31
   * @apilevel internal
   */
  public TypeDecl Define_targetType(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return typeNull();
  }
  /**
   * @declaredat /home/olivier/projects/extendj/java8/frontend/TargetType.jrag:31
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute targetType
   */
  protected boolean canDefine_targetType(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:49
   * @apilevel internal
   */
  public Body.Label Define_condition_false_label(ASTNode _callerNode, ASTNode _childNode, Body b) {
    int i = this.getIndexOfChild(_callerNode);
    { throw new Error("condition_false_label not implemented"); }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:49
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute condition_false_label
   */
  protected boolean canDefine_condition_false_label(ASTNode _callerNode, ASTNode _childNode, Body b) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:50
   * @apilevel internal
   */
  public Body.Label Define_condition_true_label(ASTNode _callerNode, ASTNode _childNode, Body b) {
    int i = this.getIndexOfChild(_callerNode);
    { throw new Error("condition_true_label not implemented"); }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/BooleanExpressions.jrag:50
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute condition_true_label
   */
  protected boolean canDefine_condition_true_label(ASTNode _callerNode, ASTNode _childNode, Body b) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:466
   * @apilevel internal
   */
  public ConstructorDecl Define_hostingCtorHack(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    {
        throw new RuntimeException("someone tried to use a hack for ctor/var access outside of a ctor");
      }
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Expressions.jrag:466
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute hostingCtorHack
   */
  protected boolean canDefine_hostingCtorHack(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:307
   * @apilevel internal
   */
  public boolean Define_enclosedByExceptionHandler(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return false;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:307
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute enclosedByExceptionHandler
   */
  protected boolean canDefine_enclosedByExceptionHandler(ASTNode _callerNode, ASTNode _childNode) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:413
   * @apilevel internal
   */
  public ArrayList<soot.jimple.Stmt> Define_exceptionRanges(ASTNode _callerNode, ASTNode _childNode, Body b) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/jimple8/backend/Statements.jrag:413
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute exceptionRanges
   */
  protected boolean canDefine_exceptionRanges(ASTNode _callerNode, ASTNode _childNode, Body b) {
    return true;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:192
   * @apilevel internal
   */
  public CompilationUnit Define_compilationUnit(ASTNode _callerNode, ASTNode _childNode) {
    int childIndex = this.getIndexOfChild(_callerNode);
    return null;
  }
  /**
   * @declaredat /home/olivier/projects/extendj/soot8/frontend/ClassPath.jrag:192
   * @apilevel internal
   * @return {@code true} if this node has an equation for the inherited attribute compilationUnit
   */
  protected boolean canDefine_compilationUnit(ASTNode _callerNode, ASTNode _childNode) {
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
}
