
/*******************************************************************************
 * Copyright (c) 2004, 2005 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/
package org.eclipse.birt.data.engine.olap.api.query;

import org.eclipse.birt.data.engine.api.ISortDefinition;



/**
 * The sort definition for cube query to define a sort against aggregations.  
 */
public interface ICubeSortDefinition extends ISortDefinition
{
	/**
	 * The targeting level that this sort definition will act against.
	 * @return
	 */
	public ILevelDefinition getTargetLevel( );
	
	/**
	 * Return the Axis qualifier level.
	 * @return
	 */
	public ILevelDefinition[] getAxisQualifierLevels( );
	
	/**
	 * Return the Axis qualifier value
	 * @return
	 */
	public Object[] getAxisQualifierValues( );
}
