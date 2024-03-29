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

import java.util.ArrayList;
import java.util.logging.Level;

import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IParameterGroupDefn;
import org.eclipse.birt.report.engine.api.UnsupportedFormatException;
import org.eclipse.birt.report.engine.i18n.MessageConstants;

/**
 * <code>ParameterGroupDefn</code> is an concrete subclass of
 * <code>ReportElementDesign</code> that implements the interface
 * <code>IParameterGroupDefn</code>. It is used to visually group report
 * parameters.
 * 
 */
public class ParameterGroupDefn extends ParameterDefnBase implements IParameterGroupDefn
{
	

	protected ArrayList contents = new ArrayList();


	public void addParameter(IParameterDefnBase param)
	{
		contents.add(param);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.birt.report.engine.api2.IParameterGroupDefn#getContents()
	 */
	@Override
	public ArrayList getContents()
	{
		return contents;
	}
	
	public void setContents(ArrayList list)
	{
		contents = list;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		ParameterGroupDefn newParam = (ParameterGroupDefn) super.clone();
		ArrayList list = newParam.getContents();
		if(list==null)
			return newParam;
		
		ArrayList newList = new ArrayList();
		for(int i=0; i<list.size(); i++)
		{
			Object parameterDefn = list.get(i);
			if( parameterDefn instanceof ParameterDefn )
			{
				ParameterDefn p = (ParameterDefn) parameterDefn;
				newList.add(p.clone());
			}
			else 
			{
				throw new CloneNotSupportedException(
						MessageConstants.PARAMETER_TYPE_IS_INVALID_EXCEPTION);
			}						
		}
		newParam.setContents(newList);
		return newParam;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.birt.report.engine.api2.IParameterGroupDefn#displayExpanded()
	 */
	@Override
	public boolean displayExpanded() {
		return true;
	}
}