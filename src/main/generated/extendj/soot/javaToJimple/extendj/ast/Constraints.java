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
 * @aspect GenericMethodsInference
 * @declaredat /home/olivier/projects/extendj/java5/frontend/GenericMethodsInference.jrag:175
 */
 class Constraints extends java.lang.Object {
  
    static class ConstraintSet {
      public Collection<TypeDecl> supertypeConstraints = new HashSet<TypeDecl>(4);
      public Collection<TypeDecl> subtypeConstraints = new HashSet<TypeDecl>(4);
      public Collection<TypeDecl> equaltypeConstraints = new HashSet<TypeDecl>(4);
      public TypeDecl typeArgument;
    }

  

    private Collection<TypeVariable> typeVariables;

  

    protected Map<TypeVariable, ConstraintSet> constraintsMap;

  

    public boolean rawAccess = false;

  

    public Constraints() {
      typeVariables = new ArrayList<TypeVariable>(4);
      constraintsMap = new HashMap<TypeVariable, ConstraintSet>();
    }

  

    public void addTypeVariable(TypeVariable T) {
      if (!typeVariables.contains(T)) {
        typeVariables.add(T);
        constraintsMap.put(T, new ConstraintSet());
      }
    }

  

    public boolean unresolvedTypeArguments() {
      for (TypeVariable T : typeVariables) {
        ConstraintSet set = constraintsMap.get(T);
        if (set.typeArgument == null) {
          return true;
        }
      }
      return false;
    }

  

    public String toString() {
      StringBuilder str = new StringBuilder();
      for (TypeVariable T : typeVariables) {
        ConstraintSet set = constraintsMap.get(T);
        for (TypeDecl U : set.supertypeConstraints) {
          if (str.length() > 0) {
            str.append("\n");
          }
          str.append(T.fullName() + " :> " + U.fullName());
        }
        for (TypeDecl U : set.subtypeConstraints) {
          if (str.length() > 0) {
            str.append("\n");
          }
          str.append(T.fullName() + " <: " + U.fullName());
        }
        for (TypeDecl U : set.equaltypeConstraints) {
          if (str.length() > 0) {
            str.append("\n");
          }
          str.append(T.fullName() + " = " + U.fullName());
        }
      }
      return str.toString();
    }

  


    public void resolveBounds() {
      for (TypeVariable T : typeVariables) {
        ConstraintSet set = constraintsMap.get(T);
        if (set.typeArgument == null) {
          set.typeArgument = T.getTypeBound(0).type();
        }
      }
    }

  

    public void resolveEqualityConstraints() {
      for (TypeVariable T : typeVariables) {
        ConstraintSet set = constraintsMap.get(T);
        for (TypeDecl U : set.equaltypeConstraints) {
          if (!typeVariables.contains(U)) {
            // Replace equality constraints for other type variables.
            replaceEqualityConstraints(T, U);
            set.equaltypeConstraints.clear();
            // Make U is the only equality constraint for T.
            set.equaltypeConstraints.add(U);
            set.typeArgument = U;
            break; // Continue on next type variable.
          } else if (T == U) {
            // Discard constraint.
          } else {
            replaceAllConstraints(T, U); // Rewrite all constraints involving T to use U instead.
            break; // Continue on next type variable.
          }
        }
        if (set.typeArgument == null
            && set.equaltypeConstraints.size() == 1
            && set.equaltypeConstraints.contains(T)) {
          set.typeArgument = T;
        }
      }
    }

  

    public void replaceEqualityConstraints(TypeDecl before, TypeDecl after) {
      for (TypeVariable T : typeVariables) {
        ConstraintSet set = constraintsMap.get(T);
        replaceConstraints(set.equaltypeConstraints, before, after);
      }
    }

  

    public void replaceAllConstraints(TypeDecl before, TypeDecl after) {
      for (TypeVariable T : typeVariables) {
        ConstraintSet set = constraintsMap.get(T);
        replaceConstraints(set.supertypeConstraints, before, after);
        replaceConstraints(set.subtypeConstraints, before, after);
        replaceConstraints(set.equaltypeConstraints, before, after);
      }
    }

  

    private void replaceConstraints(Collection<TypeDecl> constraints,
        TypeDecl before, TypeDecl after) {
      Collection<TypeDecl> newConstraints = new ArrayList<TypeDecl>();
      for (Iterator<TypeDecl> iter = constraints.iterator(); iter.hasNext(); ) {
        TypeDecl U = iter.next();
        if (U == before) { // TODO: fix parameterized type
          iter.remove();
          newConstraints.add(after);
        }
      }
      constraints.addAll(newConstraints);
    }

  

    public void resolveSubtypeConstraints() {
      for (TypeVariable T : typeVariables) {
        ConstraintSet set = constraintsMap.get(T);
        if ((set.typeArgument == null || set.typeArgument == T)
            && !set.subtypeConstraints.isEmpty()) {
          ArrayList<TypeDecl> bounds = new ArrayList<TypeDecl>();
          for (TypeDecl type : set.subtypeConstraints) {
            bounds.add(type);
          }
          set.typeArgument = GLBTypeFactory.glb(bounds);
        }
      }
    }

  

    public void resolveSupertypeConstraints() {
      for (TypeVariable T : typeVariables) {
        ConstraintSet set = constraintsMap.get(T);
        if ((set.typeArgument == null || set.typeArgument == T)
            && !set.supertypeConstraints.isEmpty()) {
          TypeDecl typeDecl = T.lookupLUBType(set.supertypeConstraints).lub();
          set.typeArgument = typeDecl;
        }
      }
    }

  

    public static Collection<TypeDecl> directSupertypes(TypeDecl t) {
      if (t instanceof ClassDecl) {
        ClassDecl type = (ClassDecl) t;
        Collection<TypeDecl> set = new HashSet<TypeDecl>();
        if (type.hasSuperclass()) {
          set.add(type.superclass());
        }
        for (int i = 0; i < type.getNumImplements(); i++) {
          set.add(type.getImplements(i).type());
        }
        return set;
      } else if (t instanceof InterfaceDecl) {
        InterfaceDecl type = (InterfaceDecl) t;
        Collection<TypeDecl> set = new HashSet<TypeDecl>();
        for (int i = 0; i < type.getNumSuperInterface(); i++) {
          set.add(type.getSuperInterface(i).type());
        }
        return set;
      } else if (t instanceof TypeVariable) {
        TypeVariable type = (TypeVariable) t;
        Collection<TypeDecl> set = new HashSet<TypeDecl>();
        for (int i = 0; i < type.getNumTypeBound(); i++) {
          set.add(type.getTypeBound(i).type());
        }
        return set;
      } else {
        throw new Error("Operation not supported for " + t.fullName() + ", " + t.getClass().getName());
      }
    }

  

    public static Collection<ParTypeDecl> parameterizedSupertypes(TypeDecl type) {
      Collection<ParTypeDecl> result = new HashSet<ParTypeDecl>();
      addParameterizedSupertypes(type, new HashSet<TypeDecl>(), result);
      return result;
    }

  

    public static void addParameterizedSupertypes(TypeDecl type, Collection<TypeDecl> processed,
        Collection<ParTypeDecl> result) {
      if (!processed.contains(type)) {
        processed.add(type);
        if (type.isParameterizedType()) {
          result.add((ParTypeDecl) type);
        }
        for (TypeDecl typeDecl : directSupertypes(type)) {
          addParameterizedSupertypes(typeDecl, processed, result);
        }
      }
    }

  

    public Collection<TypeDecl> typeArguments() {
      Collection<TypeDecl> list = new ArrayList<TypeDecl>(typeVariables.size());
      for (TypeVariable T : typeVariables) {
        ConstraintSet set = constraintsMap.get(T);
        list.add(set.typeArgument);
      }
      return list;
    }

  

    public void addSupertypeConstraint(TypeDecl T, TypeDecl A) {
      ConstraintSet set = constraintsMap.get(T);
      set.supertypeConstraints.add(A);
    }

  

    public void addSubtypeConstraint(TypeDecl T, TypeDecl A) {
      ConstraintSet set = constraintsMap.get(T);
      set.subtypeConstraints.add(A);
    }

  

    /**
     * T = A  : T and A are the same type.
     *
     * <p>This assigns type A to the type variable T.
     *
     * <p>It could happen that T and A refer to the same object, if the generic
     * method call is recursive.  It is important to still add the
     * constraint T = T even though it may seen redundant: one type variable
     * represents the type variable in the generic method call, and one type
     * variable represents a type in the context of the method call. Removing
     * a constraint T = T removes information from the type inference and
     * breaks existing tests.  See the regression test generics/method_23p.
     */
    public void addEqualConstraint(TypeDecl T, TypeDecl A) {
      ConstraintSet set = constraintsMap.get(T);
      set.equaltypeConstraints.add(A);
    }

  

    /**  A &lt;&lt; F  : A is convertible to F by method invocation conversion. */
    public void convertibleTo(TypeDecl A, TypeDecl F) {
      // If F does not involve a type parameter Tj then no constraint is implied on Tj.
      if (!F.involvesTypeParameters()) {
        return;
      }
      // If A is the type of null, no constraint is implied on Tj.
      if (A.isNull()) {
        return;
      }
      // If A is a primitive type, then A is converted to a reference type U via boxing conversion
      // and this algorithm is applied recursively to the constraint U << F.
      if (A.isUnboxedPrimitive()) {
        TypeDecl U = A.boxed();
        convertibleTo(U, F);
      } else if (F instanceof TypeVariable) {
        // If F = Tj, then the constraint Tj :> A is implied.
        if (typeVariables.contains(F)) {
          addSupertypeConstraint(F, A);
        }
      } else if (F.isArrayDecl()) {
        // If F = U[], where the type U involves Tj, then if A is an array type V[]
        // or a type variable with an upper bound that is an array type V[],
        // where V is a reference type, this algorithm is applied recursively
        // to the constraint V << U.
        TypeDecl U = ((ArrayDecl) F).componentType();
        if (!U.involvesTypeParameters()) {
          return;
        }
        if (A.isArrayDecl()) {
          TypeDecl V = ((ArrayDecl) A).componentType();
          if (V.isReferenceType()) {
            convertibleTo(V, U);
          }
        } else if (A.isTypeVariable()) {
          TypeVariable t = (TypeVariable) A;
          for (int i = 0; i < t.getNumTypeBound(); i++) {
            TypeDecl typeBound = t.getTypeBound(i).type();
            if (typeBound.isArrayDecl()
                && ((ArrayDecl) typeBound).componentType().isReferenceType()) {
              TypeDecl V = ((ArrayDecl) typeBound).componentType();
              convertibleTo(V, U);
            }
          }
        }
      } else if (F instanceof ParTypeDecl && !F.isRawType()) {
        ParTypeDecl PF = (ParTypeDecl) F;
        for (ParTypeDecl PA : parameterizedSupertypes(A)) {
          if (PF.genericDecl() == PA.genericDecl()) {
            if (A.isRawType()) {
              rawAccess = true;
            } else {
              java.util.List<TypeDecl> pfArgs = PF.getParameterization().args;
              java.util.List<TypeDecl> paArgs = PA.getParameterization().args;
              for (int i = 0; i < pfArgs.size(); i++) {
                TypeDecl T = pfArgs.get(i);
                if (T.involvesTypeParameters()) {
                  if (!T.isWildcard()) {
                    TypeDecl U = T;
                    TypeDecl V = paArgs.get(i);
                    constraintEqual(V, U);
                  } else if (T instanceof WildcardExtendsType) {
                    TypeDecl U = ((WildcardExtendsType) T).getAccess().type();
                    TypeDecl S = paArgs.get(i);
                    if (!S.isWildcard()) {
                      TypeDecl V = S;
                      convertibleTo(V, U);
                    } else if (S instanceof WildcardExtendsType) {
                      TypeDecl V = ((WildcardExtendsType) S).getAccess().type();
                      convertibleTo(V, U);
                    }
                  } else if (T instanceof WildcardSuperType) {
                    TypeDecl U = ((WildcardSuperType) T).getAccess().type();
                    TypeDecl S = paArgs.get(i);
                    if (!S.isWildcard()) {
                      TypeDecl V = S;
                      convertibleFrom(V, U);
                    } else if (S instanceof WildcardSuperType) {
                      TypeDecl V = ((WildcardSuperType) S).getAccess().type();
                      convertibleFrom(V, U);
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

  

    /**  A &gt;&gt; F  : F is convertible to A by method invocation conversion. */
    public void convertibleFrom(TypeDecl A, TypeDecl F) {
      // If F does not involve a type parameter Tj then no constraint is implied on Tj.
      if (!F.involvesTypeParameters()) {
        return;
      } else if (A.isNull()) {
        // If A is the type of null, no constraint is implied on Tj.
        return;
      } else if (F instanceof TypeVariable) {
        if (typeVariables.contains(F)) {
          addSubtypeConstraint(F, A);
        }
      } else if (F.isArrayDecl()) {
        TypeDecl U = ((ArrayDecl) F).componentType();
        if (A.isArrayDecl()) {
          TypeDecl V = ((ArrayDecl) A).componentType();
          convertibleFrom(V, U);
        } else if (A.isTypeVariable()) {
          TypeVariable t = (TypeVariable) A;
          for (int i = 0; i < t.getNumTypeBound(); i++) {
            TypeDecl typeBound = t.getTypeBound(i).type();
            if (typeBound.isArrayDecl()
                && ((ArrayDecl) typeBound).componentType().isReferenceType()) {
              TypeDecl V = ((ArrayDecl) typeBound).componentType();
              convertibleFrom(V, U);
            }
          }
        }
      } else if (F instanceof ParTypeDecl && !F.isRawType()
          && A instanceof ParTypeDecl && !A.isRawType()) {
        ParTypeDecl PF = (ParTypeDecl) F;
        ParTypeDecl PA = (ParTypeDecl) A;
        java.util.List<TypeDecl> pfArgs = PF.getParameterization().args;
        java.util.List<TypeDecl> paArgs = PA.getParameterization().args;
        TypeDecl G = PF.genericDecl();
        TypeDecl H = PA.genericDecl();
        for (int i = 0; i < pfArgs.size(); i++) {
          TypeDecl T = pfArgs.get(i);
          if (T.involvesTypeParameters()) {
            // If F has the form G<...,U,...> where U is a type expression that involves Tj.
            if (!T.isWildcard()) {
              TypeDecl U = T;
              if (G.instanceOf(H)) {
                if (H != G) {
                  for (ParTypeDecl V : parameterizedSupertypes(F)) {
                    if (!V.isRawType() && V.genericDecl() == H) {
                      if (F.instanceOf((TypeDecl) V)) {
                        convertibleFrom(A, (TypeDecl) V);
                      }
                    }
                  }
                } else if (pfArgs.size() == paArgs.size()) {
                  TypeDecl X = paArgs.get(i);
                  if (!X.isWildcard()) {
                    TypeDecl W = X;
                    constraintEqual(W, U);
                  } else if (X instanceof WildcardExtendsType) {
                    TypeDecl W = ((WildcardExtendsType) X).getAccess().type();
                    convertibleFrom(W, U);
                  } else if (X instanceof WildcardSuperType) {
                    TypeDecl W = ((WildcardSuperType) X).getAccess().type();
                    convertibleTo(W, U);
                  }
                }
              }
            } else if (T instanceof WildcardExtendsType) {
              TypeDecl U = ((WildcardExtendsType) T).getAccess().type();
              if (G.instanceOf(H)) {
                if (H != G) {
                  for (ParTypeDecl V : parameterizedSupertypes(F)) {
                    if (!V.isRawType() && V.genericDecl() == H) {
                      // Replace type argument Un with ? extends Un in V.
                      ArrayList<TypeDecl> list = new ArrayList<TypeDecl>();
                      for (TypeDecl vArg : V.getParameterization().args) {
                        list.add(vArg.asWildcardExtends());
                      }
                      TypeDecl typeV = ((GenericTypeDecl) H).lookupParTypeDecl(list);
                      convertibleFrom(A, typeV);
                    }
                  }
                } else if (pfArgs.size() == paArgs.size()) {
                  TypeDecl X = paArgs.get(i);
                  if (X instanceof WildcardExtendsType) {
                    TypeDecl W = ((WildcardExtendsType) X).getAccess().type();
                    convertibleFrom(W, U);
                  }
                }
              }
            } else if (T instanceof WildcardSuperType) {
              TypeDecl U = ((WildcardSuperType) T).getAccess().type();
              if (G.instanceOf(H)) {
                if (H != G) {
                  for (ParTypeDecl V : parameterizedSupertypes(F)) {
                    if (!V.isRawType() && V.genericDecl() == H) {
                      // Replace type argument Un with ? super Un in V.
                      ArrayList<TypeDecl> list = new ArrayList<TypeDecl>();
                      for (TypeDecl vArg : V.getParameterization().args) {
                        list.add(vArg.asWildcardExtends());
                      }
                      TypeDecl typeV = ((GenericTypeDecl) H).lookupParTypeDecl(list);
                      convertibleFrom(A, typeV);
                    }
                  }
                } else if (pfArgs.size() == paArgs.size()) {
                  TypeDecl X = paArgs.get(i);
                  if (X instanceof WildcardSuperType) {
                    TypeDecl W = ((WildcardSuperType) X).getAccess().type();
                    convertibleTo(W, U);
                  }
                }
              }
            }
          }
        }
      } else if (F.isRawType()) {
        rawAccess = true;
      }
    }

  

    public void constraintEqual(TypeDecl A, TypeDecl F) {
      // If F does not involve a type parameter Tj then no constraint is implied on Tj.
      if (!F.involvesTypeParameters()) {
        return;
      } else if (A.isNull()) {
        // If A is the type of null, no constraint is implied on Tj.
        return;
      } else if (F instanceof TypeVariable) {
        if (typeVariables.contains(F)) {
          addEqualConstraint(F, A);
        }
      } else if (F.isArrayDecl()) {
        TypeDecl U = ((ArrayDecl) F).componentType();
        if (A.isArrayDecl()) {
          TypeDecl V = ((ArrayDecl) A).componentType();
          constraintEqual(V, U);
        } else if (A.isTypeVariable()) {
          TypeVariable t = (TypeVariable) A;
          for (int i = 0; i < t.getNumTypeBound(); i++) {
            TypeDecl typeBound = t.getTypeBound(i).type();
            if (typeBound.isArrayDecl()
                && ((ArrayDecl) typeBound).componentType().isReferenceType()) {
              TypeDecl V = ((ArrayDecl) typeBound).componentType();
              constraintEqual(V, U);
            }
          }
        }
      } else if (F instanceof ParTypeDecl && !F.isRawType()) {
        if (A instanceof ParTypeDecl) {
          ParTypeDecl PF = (ParTypeDecl) F;
          ParTypeDecl PA = (ParTypeDecl) A;
          java.util.List<TypeDecl> pfArgs = PF.getParameterization().args;
          java.util.List<TypeDecl> paArgs = PA.getParameterization().args;
          if (PF.genericDecl() == PA.genericDecl()) {
            if (A.isRawType()) {
              rawAccess = true;
            } else
            for (int i = 0; i < pfArgs.size(); i++) {
              TypeDecl T = pfArgs.get(i);
              if (T.involvesTypeParameters()) {
                if (!T.isWildcard()) {
                  TypeDecl U = T;
                  TypeDecl V = paArgs.get(i);
                  constraintEqual(V, U);
                } else if (T instanceof WildcardExtendsType) {
                  TypeDecl U = ((WildcardExtendsType) T).getAccess().type();
                  TypeDecl S = paArgs.get(i);
                  if (S instanceof WildcardExtendsType) {
                    TypeDecl V = ((WildcardExtendsType) S).getAccess().type();
                    constraintEqual(V, U);
                  }
                } else if (T instanceof WildcardSuperType) {
                  TypeDecl U = ((WildcardSuperType) T).getAccess().type();
                  TypeDecl S = paArgs.get(i);
                  if (S instanceof WildcardSuperType) {
                    TypeDecl V = ((WildcardSuperType) S).getAccess().type();
                    constraintEqual(V, U);
                  }
                }
              }
            }
          }
        }
      }
    }


}
