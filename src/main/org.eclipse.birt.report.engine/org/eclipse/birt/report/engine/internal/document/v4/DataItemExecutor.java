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

package org.eclipse.birt.report.engine.internal.document.v4;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.content.IDataContent;
import org.eclipse.birt.report.engine.extension.IBaseResultSet;
import org.eclipse.birt.report.engine.extension.ICubeResultSet;
import org.eclipse.birt.report.engine.extension.IQueryResultSet;
import org.eclipse.birt.report.engine.i18n.MessageConstants;
import org.eclipse.birt.report.engine.ir.DataItemDesign;
import org.eclipse.birt.report.engine.ir.MapDesign;

/**
 * <code>DataItemExecutor</code> is a concrete subclass of
 * <code>QueryItemExecutor</code> that manipulates data items from database
 * columns, expressions and so on.
 * <p>
 * Data item executor calculates expressions in data item design, generate a
 * data content instance, evaluate styles, bookmark, action property and pass
 * this instance to emitter.
 * 
 */
public class DataItemExecutor extends ReportItemExecutor
{

	/**
	 * construct a data item executor by giving execution context and report
	 * executor visitor
	 * 
	 * @param loader
	 *            the executor context
	 * @param itemEmitter
	 *            the emitter
	 */
	public DataItemExecutor( ExecutorManager manager )
	{
		super( manager, ExecutorManager.DATAITEM );
	}

	@Override
	protected IContent doCreateContent( )
	{
		return report.createDataContent( );
	}

	@Override
	protected void doExecute( ) throws Exception
	{
		DataItemDesign dataDesign = (DataItemDesign) design;
		IDataContent dataContent = (IDataContent) content;

		executeQuery( );

		// design.map is empty means the value has been loaded from the content
		MapDesign map = design.getMap( );
		if ( map == null || map.getRuleCount( ) == 0 )
		{
			String bindingColumn = dataDesign.getBindingColumn( );
			if ( bindingColumn != null )
			{

				IBaseResultSet rset = getResultSet( );
				if ( rset == null )
				{
					rset = restoreParentResultSet( );
				}

				if ( rset != null )
				{
					try
					{
						Object value = null;
						if ( rset.getType( ) == IBaseResultSet.QUERY_RESULTSET )
						{
							value = ( (IQueryResultSet) rset )
									.getValue( bindingColumn );
						}
						else if ( rset.getType( ) == IBaseResultSet.CUBE_RESULTSET )
						{
							value = ( (ICubeResultSet) rset ).getCubeCursor( )
									.getObject( bindingColumn );
						}
						dataContent.setValue( value );
					}
					catch ( BirtException bex )
					{
						context.addException( dataDesign, bex );
					}
					catch ( Exception ex )
					{
						context.addException( dataDesign, new EngineException(
								MessageConstants.BIND_DATA_RETRIVING_ERROR, ex ) );
					}
				}
			}
		}
	}

	@Override
	public void close( )
	{
		closeQuery( );
		super.close( );
	}
}
