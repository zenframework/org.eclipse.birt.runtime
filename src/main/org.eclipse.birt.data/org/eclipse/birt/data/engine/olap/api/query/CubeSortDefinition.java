
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

import org.eclipse.birt.data.engine.api.querydefn.SortDefinition;


/**
 * 
 */

public class CubeSortDefinition extends SortDefinition
		implements
			ICubeSortDefinition
{
	private ILevelDefinition[] axisQualifierLevel;
	private Object[] axisQualifierValue;
	private ILevelDefinition targetLevel = null;
	
	public void setAxisQualifierLevels( ILevelDefinition[] level )
	{
		if ( level == null )
			this.axisQualifierLevel = new ILevelDefinition[0];
		this.axisQualifierLevel = level;
	}
	
	public void setAxisQualifierValues( Object[] value )
	{
		if ( value == null )
			this.axisQualifierValue = new Object[0];
		this.axisQualifierValue = value;
	}
	
	public void setTargetLevel( ILevelDefinition targetLevel )
	{
		this.targetLevel = targetLevel;
	}
	@Override
	public ILevelDefinition[] getAxisQualifierLevels( )
	{
		return this.axisQualifierLevel == null ? new ILevelDefinition[0]
				: this.axisQualifierLevel;
	}

	@Override
	public Object[] getAxisQualifierValues( )
	{
		return this.axisQualifierValue == null ? new Object[0]
				: this.axisQualifierValue;
	}

	@Override
	public ILevelDefinition getTargetLevel( )
	{
		return this.targetLevel;
	}

}
