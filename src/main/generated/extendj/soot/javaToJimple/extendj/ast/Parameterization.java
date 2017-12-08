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
 * @ast class
 * @aspect LookupParTypeDecl
 * @declaredat /home/olivier/projects/extendj/java5/frontend/Generics.jrag:1063
 */
public class Parameterization extends java.lang.Object {
  
    public interface TypeProperty {
      boolean holds(TypeDecl t1, TypeDecl t2);
    }

  

    static class Substitution {
      final public TypeVariable param;
      final public TypeDecl arg;

      public Substitution(TypeVariable param, TypeDecl arg) {
        this.param = param;
        this.arg = arg;
      }

      /** Creates a raw type substitution. */
      public Substitution(TypeVariable param) {
        this.param = param;
        this.arg = null;
      }

      public TypeDecl substitute() {
        return arg == null
            ? param.erasure()
            : arg.expandWildcard(param);
      }

      /** Substitute types, preserving wildcards. */
      public TypeDecl substituteBound() {
        return arg == null
            ? param.erasure()
            : arg;
      }
    }

  

    /** Type variable names mapped to wildcard expanded argument types. */
    public final Map<String, Substitution> typeMap = new HashMap<String, Substitution>();

  

    public final java.util.List<TypeVariable> params = new ArrayList<TypeVariable>();

  

    /** Original type arguments (not wildcard expanded). */
    public final java.util.List<TypeDecl> args = new ArrayList<TypeDecl>();

  

    private final boolean isRaw;

  

    public Parameterization(Iterable<TypeVariable> typeParams, Iterable<TypeDecl> typeArgs) {
      Iterator<TypeVariable> param = typeParams.iterator();
      Iterator<TypeDecl> arg = typeArgs.iterator();
      isRaw = param.hasNext() && !arg.hasNext();
      while (param.hasNext()) {
        TypeVariable variable = param.next();
        Substitution substitution;
        if (arg.hasNext()) {
          TypeDecl argument = arg.next();
          substitution = new Substitution(variable, argument);
          this.args.add(argument);
        } else {
          substitution = new Substitution(variable);
        }
        typeMap.put(variable.name(), substitution);
        params.add(variable);
      }
    }

  

    @Override public String toString() {
      StringBuilder str = new StringBuilder();
      str.append("[");
      for (TypeVariable var : params) {
        if (str.length() > 1) {
          str.append(",");
        }
        TypeDecl arg = typeMap.get(var.name()).arg;
        str.append(var.name()).append("=").append(arg.typeName());
      }
      str.append("]");
      return str.toString();
    }

  

    public boolean isRawType() {
      return isRaw;
    }

  

    /**
     * @return the substituted type, if the name matches a type variable name.
     * Returns {@code null} if the name does not match a type variable in this
     * parameterization.
     */
    public TypeDecl substitute(String name) {
      if (typeMap.containsKey(name)) {
        return typeMap.get(name).substitute();
      }
      return null;
    }

  

    /**
     * Substitute a type bound of a type variable.
     * This should preserve wildcards.
     */
    public TypeDecl substituteBound(String name) {
      if (typeMap.containsKey(name)) {
        return typeMap.get(name).substituteBound();
      }
      return null;
    }

  

    /**
     * @return the original type argument, or the Object type, if this is a raw
     * parameterization.
     */
    public TypeDecl getArg(int i) {
      if (args.isEmpty()) {
        // This is a raw parameterization.
        return params.get(0).typeObject();
      } else {
        return args.get(i);
      }
    }

  

    public boolean sameArguments(Parameterization that) {
      Iterator<TypeDecl> it1 = this.args.iterator();
      Iterator<TypeDecl> it2 = that.args.iterator();
      while (it1.hasNext() && it2.hasNext()) {
        TypeDecl t1 = it1.next();
        TypeDecl t2 = it2.next();
        if (t1 instanceof ParTypeDecl && t2 instanceof ParTypeDecl) {
          if (!((ParTypeDecl) t1).sameArguments((ParTypeDecl) t2)) {
            return false;
          }
        } else {
          if (t1 != t2) {
            return false;
          }
        }
      }
      return !it1.hasNext() && !it2.hasNext();
    }

  

    /**
     * Check a type property on each pair of type arguments in this and that
     * parameterization.
     * @return {@code true} if this parameterization and the argument
     * parameterization have the same number of type arguments, and if the
     * given property holds for each type argument pair.
     */
    public boolean compare(Parameterization that, TypeProperty property) {
      Iterator<TypeDecl> it1 = this.args.iterator();
      Iterator<TypeDecl> it2 = that.args.iterator();
      while (it1.hasNext() && it2.hasNext()) {
        TypeDecl t1 = it1.next();
        TypeDecl t2 = it2.next();
        if (!property.holds(t1, t2)) {
          return false;
        }
      }
      return !it1.hasNext() && !it2.hasNext();
    }

  

    /**
     * Check a type property on each pair of type arguments in this and that
     * parameterization, with substituted arguments in this parameterization.
     * @return {@code true} if this parameterization and the argument
     * parameterization have the same number of type arguments, and if the
     * given property holds for each type argument pair, with the argument
     * from this parameterization substituted.
     */
    public boolean compareSubstituted(Parameterization that, TypeProperty property) {
      Iterator<TypeVariable> it1 = this.params.iterator();
      Iterator<TypeDecl> it2 = that.args.iterator();
      while (it1.hasNext() && it2.hasNext()) {
        TypeDecl t1 = substitute(it1.next().name());
        TypeDecl t2 = it2.next();
        if (!property.holds(t1, t2)) {
          return false;
        }
      }
      return !it1.hasNext() && !it2.hasNext();
    }

  public static final TypeProperty SAME_STRUCTURE = new TypeProperty() {
    @Override
    public boolean holds(TypeDecl t1, TypeDecl t2) {
      return t1.sameStructure(t2);
    }
  };

  public static final TypeProperty CONTAINED_IN = new TypeProperty() {
    @Override
    public boolean holds(TypeDecl t1, TypeDecl t2) {
      return t1.containedIn(t2);
    }
  };

  public static final TypeProperty STRICT_CONTAINED_IN = new TypeProperty() {
    @Override
    public boolean holds(TypeDecl t1, TypeDecl t2) {
      return t1.strictContainedIn(t2);
    }
  };


}
