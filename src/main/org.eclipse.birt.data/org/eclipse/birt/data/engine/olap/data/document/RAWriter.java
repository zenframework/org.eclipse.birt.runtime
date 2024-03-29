
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
package org.eclipse.birt.data.engine.olap.data.document;

import java.io.IOException;

import org.eclipse.birt.core.archive.RAOutputStream;


/**
 * 
 */

public class RAWriter implements IRandomAccessObject
{
	private RAOutputStream outputStream;
	private long length;
	
	/**
	 * 
	 * @param outputStream
	 */
	RAWriter( RAOutputStream outputStream )
	{
		this.outputStream = outputStream;
		this.length = 0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.birt.data.engine.olap.data.document.IRandomAccessObject#close()
	 */
	@Override
	public void close( ) throws IOException
	{
		outputStream.flush( );
		outputStream.close( );
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.birt.data.engine.olap.data.document.IRandomAccessObject#getFilePointer()
	 */
	@Override
	public long getFilePointer( ) throws IOException
	{
		return outputStream.getOffset( );
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.birt.data.engine.olap.data.document.IRandomAccessObject#length()
	 */
	@Override
	public long length( ) throws IOException
	{
		return length;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.birt.data.engine.olap.data.document.IRandomAccessObject#read(byte[], int, int)
	 */
	@Override
	public int read( byte[] b, int off, int len ) throws IOException
	{
		throw new UnsupportedOperationException( );
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.birt.data.engine.olap.data.document.IRandomAccessObject#read(byte[])
	 */
	@Override
	public int read( byte[] b ) throws IOException
	{
		throw new UnsupportedOperationException( );
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.birt.data.engine.olap.data.document.IRandomAccessObject#seek(long)
	 */
	@Override
	public void seek( long pos ) throws IOException
	{
		outputStream.seek( pos );
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.birt.data.engine.olap.data.document.IRandomAccessObject#setLength(long)
	 */
	@Override
	public void setLength( long newLength ) throws IOException
	{
		throw new UnsupportedOperationException( );
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.birt.data.engine.olap.data.document.IRandomAccessObject#write(byte[], int, int)
	 */
	@Override
	public void write( byte[] b, int off, int len ) throws IOException
	{
		long start = getFilePointer();
		
		outputStream.write( b, off, len );
		if ( start + len > length )
			length = start + len;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.birt.data.engine.olap.data.document.IRandomAccessObject#flush()
	 */
	@Override
	public void flush( ) throws IOException
	{
		outputStream.flush( );
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.birt.data.engine.olap.data.document.IRandomAccessObject#read()
	 */
	@Override
	public int read() throws IOException
	{
		throw new UnsupportedOperationException( );
	}

}
