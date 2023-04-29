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

package org.eclipse.birt.data.engine.olap.cursor;

import javax.olap.OLAPException;
import javax.olap.cursor.DimensionCursor;
import javax.olap.cursor.EdgeCursor;

import org.eclipse.birt.data.engine.olap.driver.DimensionAxis;

/**
 * 
 *
 */
class DimensionCursorImpl extends AbstractCursorSupport
		implements
			DimensionCursor
{

	private EdgeCursor edgeCursor;
	private DimensionAxis dimensionAxis;

	/**
	 * 
	 * @param edgeCursor
	 * @param dimensionAxis
	 * @param navigator
	 * @throws OLAPException
	 */
	DimensionCursorImpl( EdgeCursor edgeCursor, DimensionAxis dimensionAxis,
			DimensionNavigator navigator ) throws OLAPException
	{
		super( navigator, new DimensionAccessor( dimensionAxis ) );
		this.edgeCursor = edgeCursor;
		this.dimensionAxis = dimensionAxis;
	}

	/*
	 * @see javax.olap.cursor.DimensionCursor#getEdgeCursor()
	 */
	@Override
	public EdgeCursor getEdgeCursor( ) throws OLAPException
	{
		return this.edgeCursor;
	}

	/*
	 * @see javax.olap.cursor.DimensionCursor#getEdgeEnd()
	 */
	@Override
	public long getEdgeEnd( ) throws OLAPException
	{
		return dimensionAxis.getEdgeEnd( );
	}

	/*
	 * @see javax.olap.cursor.DimensionCursor#getEdgeStart()
	 */
	@Override
	public long getEdgeStart( ) throws OLAPException
	{
		return dimensionAxis.getEdgeStart( );

	}
	
	/*
	 * @see org.eclipse.birt.data.engine.olap.cursor.AbstractCursorSupport#getExtent()
	 */
	@Override
	public long getExtent( ) throws OLAPException
	{
		return dimensionAxis.getExtend( );
	}

	/*
	 * @see javax.olap.cursor.DimensionCursor#setEdgeCursor(javax.olap.cursor.EdgeCursor)
	 */
	@Override
	public void setEdgeCursor( EdgeCursor value ) throws OLAPException
	{
		this.edgeCursor = value;
	}

	/**
	 * 
	 */
	@Override
	public void setEdgeEnd( long value ) throws OLAPException
	{
		throw new UnsupportedOperationException( );
	}

	/**
	 * 
	 */
	@Override
	public void setEdgeStart( long value ) throws OLAPException
	{
		throw new UnsupportedOperationException( );

	}
}
