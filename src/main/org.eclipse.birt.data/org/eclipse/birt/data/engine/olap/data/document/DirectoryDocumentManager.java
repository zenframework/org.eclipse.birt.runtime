
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

import java.io.File;
import java.io.IOException;

import org.eclipse.birt.data.engine.core.DataException;
import org.eclipse.birt.data.engine.core.security.FileSecurity;
import org.eclipse.birt.data.engine.i18n.ResourceConstants;

/**
 * An implementation of the <tt>IDocumentManager</tt> interface. This class
 * create document object in a disk directory. Each document object is saved in
 * a disk file.
 * 
 */

public class DirectoryDocumentManager implements IDocumentManager
{
	private String documentDir = null;

	/**
	 * 
	 * @param documentDir
	 * @param deleteOld
	 * @throws DataException
	 */
	public DirectoryDocumentManager( String documentDir, boolean deleteOld ) throws DataException
	{
		this.documentDir = documentDir;
		File dir = new File( documentDir );
		if(!FileSecurity.fileExist( dir )||!FileSecurity.fileIsDirectory( dir ))
		{
			if ( !FileSecurity.fileMakeDirs( dir ) )
			{
				throw new DataException( ResourceConstants.OLAPDIR_CREATE_FAIL,
						documentDir );
			}
		}
		if ( deleteOld )
		{
			File[] oldFiles = FileSecurity.fileListFiles( dir );
			for ( int i = 0; i < oldFiles.length; i++ )
			{
				FileSecurity.fileDelete( oldFiles[i] );
			}
		}
	}

	@Override
	public void close( ) throws IOException
	{
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.birt.data.olap.data.document.IDocumentManager#createDocumentObject(java.lang.String)
	 */
	@Override
	public IDocumentObject createDocumentObject( String documentObjectName ) throws IOException
	{
		File file =  new File(documentDir + File.separatorChar + documentObjectName);
		if ( FileSecurity.fileExist( file ) )
		{
			return null;
		}
		else
		{
			if ( !FileSecurity.createNewFile( file ) )
			{
				return null;
			}
			return new DocumentObject( new BufferedRandomDataAccessObject( new SimpleRandomAccessObject( file,
					"rw" ),
					1024 ) );
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.birt.data.olap.data.document.IDocumentManager#openDocumentObject(java.lang.String)
	 */
	@Override
	public IDocumentObject openDocumentObject( String documentObjectName ) throws IOException
	{
		File file = new File( documentDir
				+ File.separatorChar + documentObjectName );
		if ( !FileSecurity.fileExist( file ) )
		{
			return null;
		}
		
		return new DocumentObject( new BufferedRandomDataAccessObject( new SimpleRandomAccessObject( file,
				"rw" ),
				1024 ) );
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.birt.data.olap.data.document.IDocumentManager#exist(java.lang.String)
	 */
	@Override
	public boolean exist( String documentObjectName )
	{
		File file =  new File(documentDir + File.separatorChar + documentObjectName);
		return FileSecurity.fileExist( file );
	}

	@Override
	public void flush( ) throws IOException
	{
		
	}

}
