package soot.jimple.toolkits.scalar;

import java.util.Iterator;
import java.util.Map;

import soot.Body;
import soot.G;
import soot.Modifier;
import soot.Scene;
import soot.Singletons;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.jimple.InvokeExpr;
import soot.jimple.StaticInvokeExpr;
import soot.jimple.Stmt;

/**
 * Transformer that checks whether an instance method is used like a static
 * method, and can easily be made static, i.e., does not reference any field or
 * method in the "this" object. In this case, we make the method static, so that
 * it complies with the invocations.
 * 
 * Attention: This is not really a body transformer. It checks the current body,
 * but modifies the invocation target.
 * 
 * @author Steven Arzt
 *
 */
public class MethodStaticnessCorrector extends AbstractStaticnessCorrector {

	public MethodStaticnessCorrector(Singletons.Global g) {
	}

	public static MethodStaticnessCorrector v() {
		return G.v().soot_jimple_toolkits_scalar_MethodStaticnessCorrector();
	}

	@Override
	protected void internalTransform(Body b, String phaseName, Map<String, String> options) {
		for (Iterator<Unit> unitIt = b.getUnits().snapshotIterator(); unitIt.hasNext();) {
			Unit u = unitIt.next();
			if (u instanceof Stmt) {
				Stmt s = (Stmt) u;
				if (s.containsInvokeExpr()) {
					InvokeExpr iexpr = s.getInvokeExpr();
					if (iexpr instanceof StaticInvokeExpr) {
						if (isClassLoaded(iexpr.getMethodRef().declaringClass())) {
							SootMethod target = Scene.v().grabMethod(iexpr.getMethodRef().getSignature());
							if (target != null && !target.isStatic()) {
								if (canBeMadeStatic(target))
									target.setModifiers(target.getModifiers() | Modifier.STATIC);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Checks whether the given method can be made static, i.e., does not
	 * reference the "this" object
	 * 
	 * @param target
	 *            The method to check
	 * @return True if the given method can be made static, otherwise false
	 */
	private boolean canBeMadeStatic(SootMethod target) {
		if (target.hasActiveBody()) {
			Body body = target.getActiveBody();
			Value thisLocal = body.getThisLocal();
			for (Unit u : body.getUnits()) {
				for (ValueBox vb : u.getUseBoxes()) {
					if (vb.getValue() == thisLocal)
						return false;
				}
			}
			return true;
		}
		return false;
	}

}
