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

package org.eclipse.birt.report.engine.content.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.util.IOUtil;
import org.eclipse.birt.report.engine.content.IAutoTextContent;
import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.content.IContentVisitor;

public class AutoTextContent extends TextContent implements IAutoTextContent
{
	protected int type = -1;
	
	AutoTextContent(IAutoTextContent autoText)
	{
		super(autoText);
		this.type = autoText.getType( );
	}

	@Override
	public int getContentType( )
	{
		return AUTOTEXT_CONTENT;
	}

	AutoTextContent( ReportContent report )
	{
		super( report );
	}

	AutoTextContent( IContent content )
	{
		super( content );
	}
	
	@Override
	public void setType ( int type )
	{
		this.type  = type;
	}

	@Override
	public int getType ( )
	{
		return this.type;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.engine.content.impl.AbstractContent#accept(org.eclipse.birt.report.engine.content.IContentVisitor)
	 */
	@Override
	public Object accept( IContentVisitor visitor, Object value )
			throws BirtException
	{
		return visitor.visitAutoText( this, value );
	}
	
	static final protected short FIELD_TYPE = 650;
	static final protected short FIELD_TEXT = 651;
	
	@Override
	protected void writeFields( DataOutputStream out ) throws IOException
	{
		super.writeFields( out );
		if ( type != -1 )
		{
			IOUtil.writeShort( out, FIELD_TYPE );
			IOUtil.writeInt( out, type );
		}
		if ( text != null)
		{
			IOUtil.writeShort( out, FIELD_TEXT );
			IOUtil.writeString( out, text );
		}
	}

	@Override
	public boolean needSave( )
	{
		return true;
	}

	@Override
	protected void readField( int version, int filedId, DataInputStream in,
			ClassLoader loader ) throws IOException
	{
		switch ( filedId )
		{
			case  FIELD_TYPE:
				type = IOUtil.readInt( in );
				break;
			case  FIELD_TEXT:
				text = IOUtil.readString( in );
				break;
			default :
				super.readField( version, filedId, in, loader );
		}
	}
	
	@Override
	protected IContent cloneContent()
	{
		return new AutoTextContent(this);
	}

}
