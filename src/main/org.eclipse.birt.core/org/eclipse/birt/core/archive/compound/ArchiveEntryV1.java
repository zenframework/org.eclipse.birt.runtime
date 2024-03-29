/*******************************************************************************
 * Copyright (c) 2004,2011 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.core.archive.compound;

import java.io.IOException;

class ArchiveEntryV1 extends ArchiveEntry
{

	protected ArchiveFileV1 af;
	protected long start;
	protected long end;
	protected long length;

	ArchiveEntryV1( ArchiveFileV1 af, String name, long start, long length )
			throws IOException
	{
		super( name );
		this.af = af;
		this.start = start;
		this.length = length;
	}

	@Override
	public void close( ) throws IOException
	{
	}

	@Override
	public long getLength( ) throws IOException
	{
		return length;
	}

	@Override
	public void setLength( long length ) throws IOException
	{
		throw new IOException( "" );
	}

	@Override
	public int read( long pos, byte[] b, int off, int len ) throws IOException
	{
		if ( pos >= length )
		{
			return -1;
		}

		if ( pos + len > length )
		{
			len = (int) ( length - pos );
		}

		if ( len == 0 )
		{
			return 0;
		}
		// read first block
		return af.read( start + pos, b, off, len );
	}

	@Override
	public void write( long pos, byte[] b, int off, int len )
			throws IOException
	{
		af.write( pos, b, off, len );
	}
}
