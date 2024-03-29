/*******************************************************************************
 * Copyright (c) 2008 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.engine.internal.index.v2;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.birt.core.archive.IDocArchiveWriter;
import org.eclipse.birt.core.archive.RAOutputStream;
import org.eclipse.birt.core.util.IOUtil;
import org.eclipse.birt.report.engine.content.impl.BookmarkContent;

public class IndexWriter implements IndexConstants
{

	public static int MAX_INLINE_INDEX_ENTRY = MAX_INLINE_ENTIRES;

	IDocArchiveWriter archive;
	String name;

	int type;
	HashMap<String, Object> inlineMap;
	BTreeMap btree;

	int entrySize;

	public IndexWriter( IDocArchiveWriter archive, String name )
	{
		this.archive = archive;
		this.name = name;
	}

	void add( String key, long value ) throws IOException
	{
		if ( inlineMap == null )
		{
			type = BTreeMap.LONG_VALUE;
			inlineMap = new HashMap<String, Object>( );
		}
		if ( inlineMap.size( ) >= MAX_INLINE_INDEX_ENTRY )
		{
			flushBtree( );
			inlineMap.clear( );
		}
		if ( !inlineMap.containsKey( key ) )
		{
			inlineMap.put( key, value );
			entrySize++;
		}
	}

	void add( String bookmark, BookmarkContent info ) throws IOException
	{
		if ( inlineMap == null )
		{
			type = BTreeMap.BOOKMARK_VALUE;
			inlineMap = new HashMap<String, Object>( );
		}
		if ( inlineMap.size( ) >= MAX_INLINE_INDEX_ENTRY )
		{
			flushBtree( );
			inlineMap.clear( );
		}
		if ( !inlineMap.containsKey( bookmark ) )
		{
			inlineMap.put( bookmark, info );
			entrySize++;
		}
	}

	void close( ) throws IOException
	{
		if ( btree == null )
		{
			RAOutputStream stream = archive.createOutputStream( name );
			try
			{
				DataOutputStream output = new DataOutputStream( stream );
				if ( type == BTreeMap.LONG_VALUE )
				{
					IOUtil.writeInt( output, VERSION_0 );
					IOUtil.writeInt( output, INLINE_MAP );
					IOUtil.writeInt( output, inlineMap.size( ) );
					for ( Map.Entry<String, Object> entry : inlineMap
							.entrySet( ) )
					{
						IOUtil.writeString( output, entry.getKey( ) );
						IOUtil.writeLong( output, (Long) entry.getValue( ) );
					}
				}
				else if ( type == BTreeMap.BOOKMARK_VALUE )
				{
					IOUtil.writeInt( output, VERSION_1 );
					IOUtil.writeInt( output, INLINE_MAP );
					IOUtil.writeInt( output, inlineMap.size( ) );
					for ( Map.Entry<String, Object> entry : inlineMap
							.entrySet( ) )
					{
						IOUtil.writeString( output, entry.getKey( ) );
						( (BookmarkContent) entry.getValue( ) )
								.writeStream( output );
					}
				}
				inlineMap.clear( );
			}
			finally
			{
				stream.close( );
			}
		}
		if ( btree != null )
		{
			flushBtree( );
			btree.close( );
		}
	}

	protected void flushBtree( ) throws IOException
	{
		if ( btree == null )
		{
			btree = BTreeMap.createTreeMap( archive, name, type );
		}
		ArrayList<Map.Entry<String, Object>> entries = new ArrayList<Map.Entry<String, Object>>(
				inlineMap.entrySet( ) );
		Collections.sort( entries,
				new Comparator<Map.Entry<String, Object>>( ) {

					@Override
					public int compare( Entry<String, Object> o1,
							Entry<String, Object> o2 )
					{
						return o1.getKey( ).compareTo( o2.getKey( ) );
					}
				} );

		for ( Map.Entry<String, Object> entry : entries )
		{
			btree.insert( entry.getKey( ), entry.getValue( ) );
		}
	}
}
