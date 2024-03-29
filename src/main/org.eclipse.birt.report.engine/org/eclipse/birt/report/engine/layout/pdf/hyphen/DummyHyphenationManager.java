/***********************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Actuate Corporation - initial API and implementation
 ***********************************************************************/

package org.eclipse.birt.report.engine.layout.pdf.hyphen;


public class DummyHyphenationManager implements IHyphenationManager
{

	@Override
	public Hyphenation getHyphenation( String word )
	{
        return new Hyphenation(word, new int[]{0, word.length( )});
	}

}
