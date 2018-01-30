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
 * @ast class
 * @aspect GreatestLowerBoundFactory
 * @declaredat /home/olivier/projects/extendj/java5/frontend/GLBTypeFactory.jadd:63
 */
public class GLBTypeFactory extends java.lang.Object {
  
    // TODO add support for array types.
    public static TypeDecl glb(java.util.List<TypeDecl> typeList) {
      TypeDecl retType = typeList.get(0).unknownType();
      TypeDecl cls = mostSpecificSuperClass(typeList);
      if (cls != null) {
        ArrayList<InterfaceDecl> intersectInterfaceList = new ArrayList<InterfaceDecl>();
        ArrayList<InterfaceDecl> allInterfaceList = new ArrayList<InterfaceDecl>();
        for (TypeDecl typeDecl : typeList) {
          addInterfaces(intersectInterfaceList, allInterfaceList, typeDecl);
        }

        // Remove all interfaces that are not most specific.
        greatestLowerBounds(intersectInterfaceList);

        // Check for interface compatibility.
        if (checkInterfaceCompatibility(allInterfaceList)
            && checkClassInterfaceCompatibility(cls, intersectInterfaceList)) {
          greatestLowerBounds(typeList);
          if (typeList.size() == 1) {
            retType = typeList.iterator().next();
          } else {
            retType = retType.lookupGLBType(typeList);
          }
        }
      }
      return retType;
    }

  

    /**
     * @param intersectInterfaceList
     * @param allInterfaceList
     * @param typeDecl
     */
    private static void addInterfaces(ArrayList<InterfaceDecl> intersectInterfaceList,
        ArrayList<InterfaceDecl> allInterfaceList, TypeDecl typeDecl) {
      if (typeDecl.isInterfaceDecl()) {
        intersectInterfaceList.add((InterfaceDecl) typeDecl);
        allInterfaceList.add((InterfaceDecl) typeDecl);
      } else if (typeDecl instanceof TypeVariable) {
        TypeVariable varTD = (TypeVariable) typeDecl;
        // Add the interfaces created for type variables to.
        // Interface list to be checked for compatibility.
        intersectInterfaceList.add(varTD.toInterface());
        // Add the bounds of the type variable that are interfaces..
        allInterfaceList.addAll(varTD.implementedInterfaces());
      } else if (typeDecl instanceof LUBType) {
        allInterfaceList.addAll(typeDecl.implementedInterfaces());
      } else if (typeDecl instanceof GLBType) {
        allInterfaceList.addAll(typeDecl.implementedInterfaces());
      }
    }

  

    /**
     * See JLS section 4.9 about Intersection Types
     *
     * <p>For each <i>T<sub>i</sub></i>, 1 &le; i &le; n, let <i>C<sub>i</sub></i>
     * be the most specific class or array type such that <i>T<sub>i</sub></i>
     * &lt;: <i>C<sub>i</sub></i> Then there must be some <i>T<sub>k</sub></i>
     * &lt;: <i>C<sub>k</sub></i> such that <i>C<sub>k</sub></i> &lt;:
     * <i>C<sub>i</sub></i> for any <i>i</i>, 1 &le; i &le; n, or a
     * compile-time error occurs.
     *
     * @param types
     * @return the most specific class that all elements in <i>types</i> are a
     *         subtype of. Or null if no such class exists.
     */
    public static TypeDecl mostSpecificSuperClass(Collection<TypeDecl> types) {
      ArrayList<TypeDecl> csList = new ArrayList<TypeDecl>();
      for (TypeDecl type : types) {
        csList.add(mostSpecificSuperClass(type));
      }

      // Find Tk with Ck.
      greatestLowerBounds(csList);
      if (csList.size() == 1) {
        // OK.
        return csList.get(0);
      } else {
        // Ck does not exist.
        return null;
      }
    }

  

    /**
     * Return most specific superclass of t.
     *
     * @param t
     * @return most specific superclass of t
     */
    private static TypeDecl mostSpecificSuperClass(TypeDecl t) {
      HashSet<TypeDecl> superTypes = new HashSet<TypeDecl>();
      addSuperClasses(t, superTypes);

      if (superTypes.isEmpty()) {
        return t.typeObject();
      }

      ArrayList<TypeDecl> result = new ArrayList<TypeDecl>(superTypes.size());
      result.addAll(superTypes);
      greatestLowerBounds(result);

      if (result.size() == 1) {
        return result.get(0);
      } else {
        return t.typeObject();
      }
    }

  

    /**
     * Add the superclasses (<i>C<sub>i</sub></i>) of <i>t</i> to the set
     * <i>result</i>.
     * <ul>
     * <li>If <i>t</i> is a class, then <i>C<sub>i</sub></i> is t itself.</li>
     * <li>If <i>t</i> is a type variable, then <i>C<sub>i</sub></i> is the
     * first class encountered in it bounds</li>
     * <li>It <i>t</i> is an intersection type, then <i>C<sub>i</sub></i>
     * is class that is a member of the intersection, otherwise it's Object</li>
     * </ul>
     *
     * @param t
     * @param result
     */
    private static void addSuperClasses(TypeDecl t, HashSet<TypeDecl> result) {
      if (t == null) {
        return;
      }

      // Class.
      if (t.isClassDecl() && !result.contains(t)) {
        result.add((ClassDecl) t);
      } else if (t.isTypeVariable()) {
        // Type variable, probably called from from 1st if case.
        TypeVariable var = (TypeVariable) t;
        for (int i = 0; i < var.getNumTypeBound(); i++)
          addSuperClasses(var.getTypeBound(i).type(), result);
      } else if (t instanceof LUBType || t instanceof GLBType) {
        // Intersection type.
        result.add(t);
      } else if (t.isInterfaceDecl()) {
        // Interface.
        result.add((ClassDecl) t.typeObject());
      }
    }

  

    /**
     * @param ifaceList
     */
    private static boolean checkInterfaceCompatibility(java.util.List<InterfaceDecl> ifaceList) {
      for (int i = 0; i < ifaceList.size(); i++) {
        Collection<ParTypeDecl> superISet_i = Constraints.parameterizedSupertypes(ifaceList.get(i));
        for (ParTypeDecl superIface_i : superISet_i) {
          if (superIface_i instanceof ParInterfaceDecl) {
            ParInterfaceDecl pi = (ParInterfaceDecl) superIface_i;
            for (int j = i + 1; j < ifaceList.size(); j++) {
              Collection<ParTypeDecl> superISet_j =
                Constraints.parameterizedSupertypes(ifaceList.get(j));
              for (ParTypeDecl superIface_j : superISet_j) {
                if (superIface_j instanceof ParInterfaceDecl) {
                  ParInterfaceDecl pj = (ParInterfaceDecl) superIface_j;
                  if (pi != pj
                      && pi.genericDecl() == pj.genericDecl()
                      && !pi.sameArguments(pj)) {
                    return false;
                  }
                }
              }
            }
          }
        }
      }
      return true;
    }

  

    /**
     * @param t
     * @param cls
     * @param ifaceList
     */
    private static boolean checkClassInterfaceCompatibility(TypeDecl cls,
        java.util.List<InterfaceDecl> ifaceList) {
      Collection<InterfaceDecl> implementedInterfaces = cls.implementedInterfaces();
      for (InterfaceDecl impInterface : implementedInterfaces) {
        if (impInterface instanceof ParInterfaceDecl) {
          ParInterfaceDecl impParIface = (ParInterfaceDecl) impInterface;
          for (InterfaceDecl iface : ifaceList) {
            if (iface instanceof ParInterfaceDecl) {
              ParInterfaceDecl parIface = (ParInterfaceDecl) iface;
              if (parIface != impParIface
                  && parIface.genericDecl() == impParIface.genericDecl()
                  && !parIface.sameArguments(impParIface)) {
                return false;
              }
            }
          }
        }
      }
      return true;
    }

  

    /**
     * Find the greatest lower bound(s).
     *
     * @param types
     */
    public static void greatestLowerBounds(java.util.List<? extends TypeDecl> types) {
      for (int i = 0; i < types.size(); i++) {
        TypeDecl U = types.get(i);
        for (int j = i + 1; j < types.size(); j++) {
          TypeDecl V = types.get(j);
          if (U == null || V == null) {
            continue;
          }
          if (U.instanceOf(V)) {
            types.set(j, null);
          } else if (V.instanceOf(U)) {
            types.set(i, null);
          }
        }
      }
      // Filter nulls.
      // TODO(joqvist): don't introduce nulls in the list in the first place.
      removeNullValues(types);
    }

  

    /**
     * Remove null values from the given list.
     *
     * @param types
     */
    public static <T> void removeNullValues(java.util.List<T> list) {
      for (Iterator<T> iter = list.iterator(); iter.hasNext(); ) {
        if (iter.next() == null) {
          iter.remove();
        }
      }
    }


}
