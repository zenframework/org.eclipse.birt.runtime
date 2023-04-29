
/*******************************************************************************
 * Copyright (c) 2004, 2011 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/
package org.eclipse.birt.data.engine.olap.data.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.eclipse.birt.data.engine.core.DataException;
import org.eclipse.birt.data.engine.olap.data.api.cube.IDimension;
import org.eclipse.birt.data.engine.olap.data.document.IDocumentManager;
import org.eclipse.birt.data.engine.olap.data.impl.dimension.DimensionFactory;

/**
 * 
 */

public class SecuredCube extends Cube
{
	private Map<String, Set<String>> notAccessibleDimLvls;
	
	public SecuredCube( String name, IDocumentManager documentManager, Map<String, Set<String>> notAccessibleDimLvls )
	{
		super( name, documentManager );
		this.notAccessibleDimLvls = notAccessibleDimLvls;
	}
	
	@Override
	protected IDimension loadDimension( String name ) throws DataException,
	IOException
	{
		if( this.notAccessibleDimLvls.containsKey( name ))
			return DimensionFactory.loadDimension( name, documentManager, this.notAccessibleDimLvls.get( name ) );
		
		return DimensionFactory.loadDimension( name,
				documentManager );
	}

}
