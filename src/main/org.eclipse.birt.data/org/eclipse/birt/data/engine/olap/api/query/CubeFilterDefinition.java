/*******************************************************************************
 * Copyright (c) 2004, 2007 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.data.engine.olap.api.query;

import org.eclipse.birt.data.engine.api.IBaseExpression;
import org.eclipse.birt.data.engine.api.querydefn.FilterDefinition;

/**
 * 
 */
public class CubeFilterDefinition extends FilterDefinition
		implements
			ICubeFilterDefinition
{

	private ILevelDefinition targetLevel;
	private ILevelDefinition[] axisQualifierLevels;
	private Object[] axisQualifierValues;

	public CubeFilterDefinition( IBaseExpression filterExpr )
	{
		super( filterExpr );
	}

	public CubeFilterDefinition( IBaseExpression filterExpr,
			ILevelDefinition targetLevel, ILevelDefinition[] axisQulifierLevel,
			Object[] axisQulifierValue )
	{
		this( filterExpr );
		this.targetLevel = targetLevel;		
		this.axisQualifierLevels = axisQulifierLevel;
		this.axisQualifierValues = axisQulifierValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.data.engine.olap.api.query.ICubeFilterDefinition#getTargetLevel()
	 */
	@Override
	public ILevelDefinition getTargetLevel( )
	{
		return targetLevel;
	}

	/**
	 * @param targetLevel
	 *            the targetLevel to set
	 */
	public void setTargetLevel( ILevelDefinition targetLevel )
	{
		this.targetLevel = targetLevel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.data.engine.olap.api.query.ICubeFilterDefinition#getAxisQualifierLevel()
	 */
	@Override
	public ILevelDefinition[] getAxisQualifierLevels( )
	{
		return axisQualifierLevels;
	}

	/**
	 * @param axisQualifierLevel
	 *            the axisQualifierLevel to set
	 */
	public void setAxisQualifierLevels( ILevelDefinition[] axisQualifierLevel )
	{
		this.axisQualifierLevels = axisQualifierLevel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.data.engine.olap.api.query.ICubeFilterDefinition#getAxisQualifierValue()
	 */
	@Override
	public Object[] getAxisQualifierValues( )
	{
		return axisQualifierValues;
	}

	
	/**
	 * @param axisQualifierValue the axisQualifierValue to set
	 */
	public void setAxisQualifierValues( Object[] axisQualifierValue )
	{
		this.axisQualifierValues = axisQualifierValue;
	}
}
