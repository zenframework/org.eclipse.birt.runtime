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

import org.eclipse.birt.report.engine.api.script.instance.IReportItemInstance;
import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.content.IHyperlinkAction;
import org.eclipse.birt.report.engine.executor.ExecutionContext;

/**
 * A class representing the runtime state of a report item
 */
public class ReportItemInstance extends ReportElementInstance implements
		IReportItemInstance
{

	public ReportItemInstance( IContent content, ExecutionContext context,
			RunningState runningState )
	{
		super( content, context, runningState );
	}

	protected void setContent( IContent content )
	{
		this.content = content;
	}

	protected ReportItemInstance( ExecutionContext context,
			RunningState runningState )
	{
		super( context, runningState );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.engine.api.script.instance.IReportInstance#getHyperlink()
	 */
	@Override
	public String getHyperlink( )
	{
		IHyperlinkAction hyperlinkAction = content.getHyperlinkAction( );
		return hyperlinkAction == null ? null : hyperlinkAction.getHyperlink( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.engine.api.script.instance.IReportInstance#getName()
	 */
	@Override
	public String getName( )
	{
		return content.getName( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.engine.api.script.instance.IReportInstance#setName(java.lang.String)
	 */
	@Override
	public void setName( String name )
	{
		content.setName( name );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.engine.api.script.instance.IReportInstance#getHelpText()
	 */
	@Override
	public String getHelpText( )
	{
		return content.getHelpText( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.engine.api.script.instance.IReportInstance#setHelpText(java.lang.String)
	 */
	@Override
	public void setHelpText( String helpText )
	{
		content.setHelpText( helpText );
	}

}
