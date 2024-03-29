/*******************************************************************************
 * Copyright (c)2009 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.engine.extension;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.IDataExtractionOption;
import org.eclipse.birt.report.engine.api.IExtractionResults;
import org.eclipse.birt.report.engine.api.script.IReportContext;

public class DataExtractionExtensionBase implements IDataExtractionExtension
{

	@Override
	public void initialize( IReportContext context, IDataExtractionOption option )
			throws BirtException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void output( IExtractionResults results ) throws BirtException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void release( )
	{
		// TODO Auto-generated method stub

	}

}
