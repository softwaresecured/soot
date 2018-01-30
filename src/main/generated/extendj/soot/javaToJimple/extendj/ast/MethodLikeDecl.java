package soot.javaToJimple.extendj.ast;

import java.util.ArrayList;
import soot.*;


public interface MethodLikeDecl<Impl extends MethodLikeDecl<Impl>> {
  default void jimpleDeclare() { sootMethod(); /* invoke for side-effects */ }

  default SootMethod sootMethod() {
    Impl self = typeErased();
    if (self != this) return self.sootMethod();

    SootClass             klass       = self.hostType().sootClass();
    ArrayList<soot.Type>  paramTypes  = sootMethodParamTypes();

    String sig = SootMethod.getSubSignature(sootMethodName(), paramTypes, sootMethodType());
    if (klass.declaresMethod(sig)) return klass.getMethod(sig);

    ArrayList<String   >  paramNames  = new ArrayList<>();
    ArrayList<SootClass>  thrown      = new ArrayList<>();
    for (ParameterDeclaration p : getExplicitisedParameters() ) paramNames.add(p.name());
    for (Access               t : getExceptions()             ) thrown.add(t.type().sootClass());

    SootMethod m = new SootMethod(sootMethodName(), paramTypes, sootMethodType(), flags(),thrown);
    m.addTag(new soot.tagkit.ParamNamesTag(paramNames));
    klass.addMethod(m);
    return m;
  }

  default ArrayList<soot.Type> sootMethodParamTypes() {
    ArrayList<soot.Type>  paramTypes  = new ArrayList<>();
    for (ParameterDeclaration p : typeErased().getExplicitisedParameters())
      paramTypes.add(p.type().sootType());

    return paramTypes;
  }

  // existing AST attributes (`BodyDecl` and common subset of `ConstructorDecl`, `MethodDecl`)
  int                             flags();
  String                          name();
  TypeDecl                        type();
  TypeDecl                        hostType();
  soot.jimple.JimpleBody          jimpleBody();
  Iterable<Access               > getExceptions();
  Iterable<ParameterDeclaration > getParameters();

  // glue/bridge methods
          Impl                            typeErased();
  default String                          sootMethodName()            { return name(); }
  default soot.Type                       sootMethodType()            { return type().sootType(); }
  default Iterable<ParameterDeclaration > getExplicitisedParameters() { return getParameters(); }
}