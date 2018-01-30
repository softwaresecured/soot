/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003 Navindra Umanee <navindra@cs.mcgill.ca>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package soot.shimple;

import soot.*;
import soot.options.Options;
import java.util.*;

/**
 * Traverses all methods, in all classes from the Scene, and
 * transforms them to Shimple.  Typically used for whole-program
 * analysis on Shimple.
 *
 * @author Navindra Umanee
 **/
public class ShimpleTransformer extends SceneTransformer
{
    public ShimpleTransformer( Singletons.Global g ) {}
    public static ShimpleTransformer v() { return G.v().soot_shimple_ShimpleTransformer(); }

    protected void internalTransform(String phaseName, Map options)
    {
        if(Options.v().verbose())
            G.v().out.println("Transforming all classes in the Scene to Shimple...");

        // *** FIXME: Add debug output to indicate which class/method is being shimplified.
        // *** FIXME: Is ShimpleTransformer the right solution?  The call graph may deem
        //            some classes unreachable.

        // HACK: Workaround inference bug in extendj. If you put the whole thing in one expression w/o an explicit type
		//       it'll infer `.flatMap(c -> c.getMethods().stream())` to yield `Stream<Object>` instead of
		//       `Stream<SootMethod>`. And it'll explode later in NTA generation.
        Main.runConcurrent(
            Scene.v().getClasses().stream()
            .filter (c -> !c.isPhantom())
            .<SootMethod>flatMap(c ->  c.getMethods().stream())
            .filter(SootMethod::isConcrete)
            .map(m -> () -> {
                if (!m.hasActiveBody()) {
                    m.setSource(new ShimpleMethodSource(m.getSource()));
                    return;
                }

                final Body body = m.getActiveBody();
                ShimpleBody sBody;

                if (body instanceof ShimpleBody) {
                    sBody = (ShimpleBody) body;
                    if (!sBody.isSSA())
                        sBody.rebuild();
                } else {
                    sBody = Shimple.v().newBody(body);
                }

                m.setActiveBody(sBody);
            })
        );
    }
}
