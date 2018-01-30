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

package soot;
import java.io.File;

import soot.javaToJimple.ExtendJInitialResolver;
import soot.javaToJimple.IInitialResolver;
import soot.javaToJimple.IInitialResolver.Dependencies;
import soot.options.Options;

/** A class source for resolving from .java files using javaToJimple.
 */
public class JavaClassSource extends ClassSource
{
    public JavaClassSource( String className, File fullPath ) {
        super( className );
        this.fullPath = fullPath;
    }

    public Dependencies resolve( SootClass sc ) {
        assert (sc == null) || sc.getName().equals(className);

        if (Options.v().verbose())
            G.v().out.println("resolving [from .java]: " + className);

        ExtendJInitialResolver resolver = ExtendJInitialResolver.v();
        return resolver.resolveFromJavaFile(SourceLocator.v().sourcePath(), fullPath, className, sc);
    }

    public File   sourceFile() { return fullPath; }
    public String className()  { return className; }

    private File fullPath;
}

