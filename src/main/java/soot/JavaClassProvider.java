/* Soot - a J*va Optimization Framework
 * Copyright (C) 2004 Ondrej Lhotak
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

/* 04.04.2006 mbatch	if there is a $ in the name,
 *						we need to check if it's a real file, 
 * 						not just inner class								
 */

package soot;

import soot.javaToJimple.ExtendJInitialResolver;
import soot.javaToJimple.IInitialResolver;
import soot.javaToJimple.extendj.ast.CompilationUnit;
import soot.javaToJimple.extendj.ast.Option;

/** A class provider looks for a file of a specific format for a specified
 * class, and returns a ClassSource for it if it finds it.
 */
public class JavaClassProvider implements ClassProvider
{
    public static class JarException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public JarException(String className) {
            super("Class "+className+" was found in an archive, but Soot doesn't support reading source files out of an archive");
        }

    }

    /** Look for the specified class. Return a ClassSource for it if found,
     * or null if it was not found. */
    public ClassSource find( String className ) {
        Option<CompilationUnit> cu = ExtendJInitialResolver.v().cuForClass(className);
        if (!cu.hasValue()) return findSrcFile(className);

        return new ClassSource(className) {
            public IInitialResolver.Dependencies resolve(SootClass sc)
            { return ExtendJInitialResolver.v().resolveFromCache(className, sc); }
        };
    }

    private JavaClassSource findSrcFile(String className) {
        // 04.04.2006 mbatch
        //  if there is a $ in the name then we need to check if it's a real file, not just an inner class
        boolean checkForLiteralFileName = className.indexOf('$') >= 0;

        FoundFile file = null;
        try {
            String javaClassName  = SourceLocator.v().getSourceForClass(className);
            String fileName       = javaClassName.replace('.', '/') + ".java";

            file = SourceLocator.v().lookupInClassPath(fileName);

            // 04.04.2006 mbatch
            //  if inner class not found, check if it's a real file
            if ((file == null) && checkForLiteralFileName) {
                String directFileName = className.replace('.', '/') + ".java";
                file                  = SourceLocator.v().lookupInClassPath(directFileName);
            }

            if (file == null    ) return null;
            if (file.isZipFile()) return null;//throw new JarException(className);

            return new JavaClassSource(className, file.getFile());
        }
        finally {
            if (file != null)
                file.close();
        }
    }
}