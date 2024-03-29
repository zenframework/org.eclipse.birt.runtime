/*******************************************************************************
 * Copyright (c) 2005 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/
package org.eclipse.birt.report.engine.script.internal.instance;

import java.util.Map;

import org.eclipse.birt.data.engine.api.script.IDataSourceInstanceHandle;
import org.eclipse.birt.report.engine.api.script.instance.IDataSourceInstance;

public class DataSourceInstance implements IDataSourceInstance
{

	private IDataSourceInstanceHandle dataSource;

	public DataSourceInstance( IDataSourceInstanceHandle dataSource )
	{
		this.dataSource = dataSource;
	}

	@Override
	public String getName( )
	{
		return dataSource.getName( );
	}

	@Override
	public String getExtensionID( )
	{
		return dataSource.getExtensionID( );
	}

	/**
	 * @see org.eclipse.birt.report.engine.api.script.instance.IDataSourceInstance#getAllExtensionProperties()
	 */
	@Override
	public Map getAllExtensionProperties()
	{
		return dataSource.getAllExtensionProperties();
	}

	/**
	 * @see org.eclipse.birt.report.engine.api.script.instance.IDataSourceInstance#getExtensionProperty(java.lang.String)
	 */
	@Override
	public String getExtensionProperty(String name)
	{
		return dataSource.getExtensionProperty(name);
	}

	/**
	 * @see org.eclipse.birt.report.engine.api.script.instance.IDataSourceInstance#setExtensionProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public void setExtensionProperty(String name, String value)
	{
		dataSource.setExtensionProperty( name, value );
	}

}
