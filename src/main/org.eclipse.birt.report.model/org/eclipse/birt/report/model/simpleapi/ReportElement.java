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

package org.eclipse.birt.report.model.simpleapi;

import org.eclipse.birt.report.model.api.ReportElementHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.simpleapi.IReportElement;
import org.eclipse.birt.report.model.elements.interfaces.IDesignElementModel;

public class ReportElement extends DesignElement implements IReportElement
{

	private ReportElementHandle reportElementHandle;

	public ReportElement( ReportElementHandle handle )
	{
		super( handle );
		this.reportElementHandle = handle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.engine.api.script.element.IReportItem#setCustomXml
	 * (java.lang.String)
	 */

	@Override
	public void setCustomXml( String customXml ) throws SemanticException
	{
		setProperty( IDesignElementModel.CUSTOM_XML_PROP, customXml );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.engine.api.script.element.IReportItem#getName()
	 */

	@Override
	public String getName( )
	{
		return reportElementHandle.getName( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.engine.api.script.element.IReportItem#setName
	 * (java.lang.String)
	 */

	@Override
	public void setName( String name ) throws SemanticException
	{
		setProperty( IDesignElementModel.NAME_PROP, name );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.engine.api.script.element.IReportItem#getCustomXml
	 * ()
	 */

	@Override
	public String getCustomXml( )
	{
		return reportElementHandle.getCustomXml( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.engine.api.script.element.IReportItem#setComments
	 * (java.lang.String)
	 */

	@Override
	public void setComments( String theComments ) throws SemanticException
	{
		setProperty( IDesignElementModel.COMMENTS_PROP, theComments );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.engine.api.script.element.IReportItem#getComments
	 * ()
	 */

	@Override
	public String getComments( )
	{
		return reportElementHandle.getComments( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.engine.api.script.element.IReportItem#
	 * setDisplayNameKey(java.lang.String)
	 */

	@Override
	public void setDisplayNameKey( String displayNameKey )
			throws SemanticException
	{
		setProperty( IDesignElementModel.DISPLAY_NAME_ID_PROP, displayNameKey );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.engine.api.script.element.IReportItem#
	 * getDisplayNameKey()
	 */

	@Override
	public String getDisplayNameKey( )
	{
		return reportElementHandle.getDisplayNameKey( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.engine.api.script.element.IReportItem#setDisplayName
	 * (java.lang.String)
	 */

	@Override
	public void setDisplayName( String displayName ) throws SemanticException
	{

		setProperty( IDesignElementModel.DISPLAY_NAME_PROP, displayName );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.engine.api.script.element.IReportItem#getDisplayName
	 * ()
	 */

	@Override
	public String getDisplayName( )
	{
		return reportElementHandle.getDisplayName( );
	}
}
