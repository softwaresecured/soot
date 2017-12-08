package soot.jimple.toolkits.scalar;

import java.util.Iterator;
import java.util.Map;

import soot.Body;
import soot.ConflictingFieldRefException;
import soot.G;
import soot.Singletons;
import soot.SootField;
import soot.Unit;
import soot.jimple.AssignStmt;
import soot.jimple.FieldRef;
import soot.jimple.InstanceFieldRef;
import soot.jimple.Jimple;
import soot.jimple.Stmt;

/**
 * Transformer that checks whether a static field is used like an instance
 * field. If this is the case, all instance references are replaced by static
 * field references.
 * 
 * @author Steven Arzt
 *
 */
public class FieldStaticnessCorrector extends AbstractStaticnessCorrector {

	public FieldStaticnessCorrector(Singletons.Global g) {
	}

	public static FieldStaticnessCorrector v() {
		return G.v().soot_jimple_toolkits_scalar_FieldStaticnessCorrector();
	}

	@Override
	protected void internalTransform(Body b, String phaseName, Map<String, String> options) {
		// Some apps reference static fields as instance fields. We need to fix
		// this for not breaking the client analysis.
		for (Iterator<Unit> unitIt = b.getUnits().iterator(); unitIt.hasNext();) {
			Stmt s = (Stmt) unitIt.next();
			if (s.containsFieldRef() && s instanceof AssignStmt) {
				FieldRef ref = s.getFieldRef();
				// Make sure that the target class has already been loaded
				if (isTypeLoaded(ref.getFieldRef().type())) {
					try {
						if (ref instanceof InstanceFieldRef) {
							SootField fld = ref.getField();
							if (fld != null && fld.isStatic()) {
								AssignStmt assignStmt = (AssignStmt) s;
								if (assignStmt.getLeftOp() == ref)
									assignStmt.setLeftOp(Jimple.v().newStaticFieldRef(ref.getField().makeRef()));
								else if (assignStmt.getRightOp() == ref)
									assignStmt.setRightOp(Jimple.v().newStaticFieldRef(ref.getField().makeRef()));
							}
						}
					} catch (ConflictingFieldRefException ex) {
						// That field is broken, just don't touch it
					}
				}
			}
		}
	}

}
