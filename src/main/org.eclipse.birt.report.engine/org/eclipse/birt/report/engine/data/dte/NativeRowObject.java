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

package org.eclipse.birt.report.engine.data.dte;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.data.engine.api.IResultMetaData;
import org.eclipse.birt.report.engine.executor.ExecutionContext;
import org.eclipse.birt.report.engine.extension.IBaseResultSet;
import org.eclipse.birt.report.engine.extension.IQueryResultSet;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Scriptable;

/**
 * Represents the scriptable object for Java object which implements the
 * interface <code>Map</code>.
 * 
 */
public class NativeRowObject implements Scriptable
{

	ExecutionContext context;
	Scriptable prototype;
	Scriptable parent;
	IQueryResultSet rset;

	static final String JS_CLASS_NAME = "DataSetRow";

	@Override
	public String getClassName( )
	{
		return JS_CLASS_NAME;
	}

	public NativeRowObject( )
	{
	}

	public NativeRowObject( Scriptable parent, ExecutionContext context )
	{
		setParentScope( parent );
		this.context = context;
	}

	public NativeRowObject( Scriptable parent, IQueryResultSet rset )
	{
		setParentScope( parent );
		this.rset = rset;
	}

	protected IQueryResultSet getResultSet( )
	{
		if ( rset == null )
		{
			IBaseResultSet ctxRset = context.getResultSet( );
			if ( ctxRset != null )
			{
				if ( ctxRset.getType( ) == IBaseResultSet.QUERY_RESULTSET )
				{
					return (IQueryResultSet) ctxRset;
				}
			}
		}
		return rset;
	}

	@Override
	public Object get( String name, Scriptable start )
	{
		IQueryResultSet rset = getResultSet( );
		if ( rset == null )
		{
			return null;
		}

		if ( "_outer".equals( name ) )
		{
			IBaseResultSet parent = rset.getParent( );
			if ( parent != null
					&& parent.getType( ) == IBaseResultSet.QUERY_RESULTSET )
			{
				return new NativeRowObject( start, (IQueryResultSet) parent );
			}
			else
			{
				// TODO: return cuber object used in script
				// return new NativeCubeObject(start, parent);
			}
			return null;
		}
		try
		{
			if ( "__rownum".equals( name ) )
			{
				return Long.valueOf( rset.getRowIndex( ) );
			}
			return rset.getValue( name );
		}
		catch ( BirtException ex )
		{
			throw new EvaluatorException( ex.toString( ) );
		}
	}

	@Override
	public Object get( int index, Scriptable start )
	{
		if ( index == 0 )
		{
			return get( "__rownum", start );
		}
		return get( String.valueOf( index ), start );

	}

	@Override
	public boolean has( String name, Scriptable start )
	{
		IQueryResultSet rset = getResultSet( );
		if ( rset == null )
		{
			return false;
		}

		try
		{
			IResultMetaData metaData = rset.getResultMetaData( );
			for ( int i = 0; i < metaData.getColumnCount( ); i++ )
			{
				String colName = metaData.getColumnName( i );
				if ( colName.equals( name ) )
				{
					return true;
				}
			}
		}
		catch ( BirtException ex )
		{
			// not exist
		}
		return false;
	}

	@Override
	public boolean has( int index, Scriptable start )
	{
		return false;
	}

	@Override
	public void put( String name, Scriptable start, Object value )
	{
	}

	@Override
	public void put( int index, Scriptable start, Object value )
	{
	}

	@Override
	public void delete( String name )
	{
	}

	@Override
	public void delete( int index )
	{
	}

	@Override
	public Scriptable getPrototype( )
	{
		return prototype;
	}

	@Override
	public void setPrototype( Scriptable prototype )
	{
		this.prototype = prototype;
	}

	@Override
	public Scriptable getParentScope( )
	{
		return parent;
	}

	@Override
	public void setParentScope( Scriptable parent )
	{
		this.parent = parent;
	}

	@Override
	public Object[] getIds( )
	{
		IQueryResultSet rset = getResultSet( );
		if ( rset == null )
		{
			return null;
		}
		try
		{
			IResultMetaData metaData = rset.getResultMetaData( );
			Object[] names = new Object[metaData.getColumnCount( )];
			for ( int i = 0; i < names.length; i++ )
			{
				names[i] = metaData.getColumnName( i );
			}
			return names;
		}
		catch ( BirtException ex )
		{
		}
		return null;
	}

	@Override
	public Object getDefaultValue( Class hint )
	{
		return null;
	}

	@Override
	public boolean hasInstance( Scriptable instance )
	{
		return false;
	}
}
