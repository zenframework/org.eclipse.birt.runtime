/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/
package org.eclipse.birt.report.engine.api.impl;

import org.eclipse.birt.report.engine.api.ICascadingParameterGroup;



public class CascadingParameterGroupDefn extends ParameterGroupDefn implements ICascadingParameterGroup
{
	String dataSet;
	
	@Override
	public String getDataSet()
	{
		return dataSet;
	}
	
	public void setDataSet(String dataSet)
	{
		this.dataSet = dataSet;
	}
}
