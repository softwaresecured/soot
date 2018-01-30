/* Soot - a J*va Optimization Framework
 * Copyright (C) 2008 Eric Bodden
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
package soot.javaToJimple;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import soot.SootClass;
import soot.Type;

public interface IInitialResolver {
    public class Dependencies {
        public Set<Type> typesToHierarchy = new HashSet<>(), typesToSignature = new HashSet<>();
    }

    /**
     * "Resolve the class into the SootClass sc."
     * In practice:
     *  1) Attempts to parse/load the java source.[1][2]
     *  2) Assigns a method-source for all the methods in `sc` (possibly indirectly, in the case of member classes).
     *  3) Returns all the dependencies of the requested class.[3]
     *
     * [1]  Partial lie: Depending on `Options.v().src_prec()` settings, it might load the byte-code instead (if avail)
     * [2]  As is, `ExtendJInitialResolver` caches it and this is effectively a mem-leak since the cache is never
     *      cleared.
     * [3]  Possibly partially transitive. e.g. requesting to load an inner class could include dependencies of the
     *      top-level class.
     */
    public Dependencies resolveFromJavaFile(List<String> locations, File src, String className, SootClass sc);
}
