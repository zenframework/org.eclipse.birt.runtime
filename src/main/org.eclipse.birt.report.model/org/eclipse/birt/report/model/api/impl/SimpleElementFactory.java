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

package org.eclipse.birt.report.model.api.impl;

import org.eclipse.birt.report.model.api.ActionHandle;
import org.eclipse.birt.report.model.api.ComputedColumnHandle;
import org.eclipse.birt.report.model.api.DataSetHandle;
import org.eclipse.birt.report.model.api.DataSourceHandle;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.Expression;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.FilterConditionElementHandle;
import org.eclipse.birt.report.model.api.FilterConditionHandle;
import org.eclipse.birt.report.model.api.HideRuleHandle;
import org.eclipse.birt.report.model.api.HighlightRuleHandle;
import org.eclipse.birt.report.model.api.ReportItemHandle;
import org.eclipse.birt.report.model.api.ResultSetColumnHandle;
import org.eclipse.birt.report.model.api.SortElementHandle;
import org.eclipse.birt.report.model.api.SortKeyHandle;
import org.eclipse.birt.report.model.api.StyleHandle;
import org.eclipse.birt.report.model.api.elements.structures.ComputedColumn;
import org.eclipse.birt.report.model.api.elements.structures.FilterCondition;
import org.eclipse.birt.report.model.api.elements.structures.HideRule;
import org.eclipse.birt.report.model.api.elements.structures.HighlightRule;
import org.eclipse.birt.report.model.api.elements.structures.SortKey;
import org.eclipse.birt.report.model.api.simpleapi.IAction;
import org.eclipse.birt.report.model.api.simpleapi.IDataBinding;
import org.eclipse.birt.report.model.api.simpleapi.IDataSet;
import org.eclipse.birt.report.model.api.simpleapi.IDataSource;
import org.eclipse.birt.report.model.api.simpleapi.IDesignElement;
import org.eclipse.birt.report.model.api.simpleapi.IExpression;
import org.eclipse.birt.report.model.api.simpleapi.IFilterCondition;
import org.eclipse.birt.report.model.api.simpleapi.IFilterConditionElement;
import org.eclipse.birt.report.model.api.simpleapi.IHideRule;
import org.eclipse.birt.report.model.api.simpleapi.IHighlightRule;
import org.eclipse.birt.report.model.api.simpleapi.IReportItem;
import org.eclipse.birt.report.model.api.simpleapi.IResultSetColumn;
import org.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory;
import org.eclipse.birt.report.model.api.simpleapi.ISortCondition;
import org.eclipse.birt.report.model.api.simpleapi.ISortElement;
import org.eclipse.birt.report.model.api.simpleapi.IStyle;
import org.eclipse.birt.report.model.simpleapi.ActionImpl;
import org.eclipse.birt.report.model.simpleapi.DataBindingImpl;
import org.eclipse.birt.report.model.simpleapi.DataSet;
import org.eclipse.birt.report.model.simpleapi.DataSource;
import org.eclipse.birt.report.model.simpleapi.ElementUtil;
import org.eclipse.birt.report.model.simpleapi.ExpressionImpl;
import org.eclipse.birt.report.model.simpleapi.FilterConditionElement;
import org.eclipse.birt.report.model.simpleapi.FilterConditionImpl;
import org.eclipse.birt.report.model.simpleapi.HideRuleImpl;
import org.eclipse.birt.report.model.simpleapi.HighlightRuleImpl;
import org.eclipse.birt.report.model.simpleapi.MultiRowItem;
import org.eclipse.birt.report.model.simpleapi.ReportItem;
import org.eclipse.birt.report.model.simpleapi.ResultSetColumnImpl;
import org.eclipse.birt.report.model.simpleapi.SortConditionImpl;
import org.eclipse.birt.report.model.simpleapi.SortElement;
import org.eclipse.birt.report.model.simpleapi.Style;

/**
 * The factory class to create scriptable objects.
 */

public class SimpleElementFactory implements ISimpleElementFactory
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.model.api.script.IScriptElementFactory#getElement
	 * (org.eclipse.birt.report.model.api.DesignElementHandle)
	 */

	@Override
	public IReportItem wrapExtensionElement( ExtendedItemHandle handle, int type )
	{
		if ( handle == null )
			return null;

		if ( type == MULTI_ROW_ITEM )
			return new MultiRowItem( handle );

		if ( type == SIMPLE_ROW_ITEM )
			return new ReportItem( handle );

		return new ReportItem( handle );
	}

	/**
	 * Create <code>IHideRule</code> instance
	 * 
	 * @return IHideRule
	 */

	@Override
	public IHideRule createHideRule( )
	{
		HideRule r = new HideRule( );
		IHideRule rule = new HideRuleImpl( r );
		return rule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createHideRule(org.eclipse.birt.report.model.api.core.IStructure)
	 */

	@Override
	public IHideRule createHideRule( HideRule rule )
	{
		IHideRule hideRule = new HideRuleImpl( rule );
		return hideRule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createHideRule(org.eclipse.birt.report.model.api.HideRuleHandle)
	 */

	@Override
	public IHideRule createHideRule( HideRuleHandle handle )
	{
		return new HideRuleImpl( handle );
	}

	/**
	 * Create <code>IFilterCondition</code>
	 * 
	 * @return instance
	 */

	@Override
	public IFilterCondition createFilterCondition( )
	{
		FilterCondition c = new FilterCondition( );
		IFilterCondition condition = new FilterConditionImpl( c );
		return condition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createFilterCondition
	 * (org.eclipse.birt.report.model.api.elements.structures.FilterCondition)
	 */

	@Override
	public IFilterCondition createFilterCondition( FilterCondition condition )
	{
		return new FilterConditionImpl( condition );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createFilterCondition
	 * (org.eclipse.birt.report.model.api.FilterConditionHandle)
	 */

	@Override
	public IFilterCondition createFilterCondition( FilterConditionHandle handle )
	{
		return new FilterConditionImpl( handle );
	}

	/**
	 * Create <code>IDataBinding</code>
	 * 
	 * @return instance
	 */

	@Override
	public IDataBinding createDataBinding( )
	{
		ComputedColumn c = new ComputedColumn( );
		IDataBinding binding = new DataBindingImpl( c );
		return binding;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createDataBinding(org.eclipse.birt.report.model.api.ComputedColumnHandle)
	 */

	@Override
	public IDataBinding createDataBinding( ComputedColumnHandle columnHandle )
	{
		return new DataBindingImpl( columnHandle );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createDataBinding
	 * (org.eclipse.birt.report.model.api.elements.structures.ComputedColumn)
	 */

	@Override
	public IDataBinding createDataBinding( ComputedColumn column )
	{
		return new DataBindingImpl( column );
	}

	/**
	 * Create <code>ISortCondition</code>
	 * 
	 * @return instance
	 */

	@Override
	public ISortCondition createSortCondition( )
	{
		return new SortConditionImpl( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createSortCondition
	 * (org.eclipse.birt.report.model.api.elements.structures.SortKey)
	 */

	@Override
	public ISortCondition createSortCondition( SortKey sort )
	{
		return new SortConditionImpl( sort );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createSortCondition(org.eclipse.birt.report.model.api.SortKeyHandle)
	 */

	@Override
	public ISortCondition createSortCondition( SortKeyHandle sortHandle )
	{
		return new SortConditionImpl( sortHandle );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createAction(org.eclipse.birt.report.model.api.ActionHandle,
	 * org.eclipse.birt.report.model.api.ReportItemHandle)
	 */

	@Override
	public IAction createAction( ActionHandle action, ReportItemHandle handle )
	{
		return new ActionImpl( action, handle );
	}

	/* (non-Javadoc)
	 * @see org.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#createAction()
	 */
	@Override
	public IAction createAction( )
	{
		return new ActionImpl( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#getElement
	 * (org.eclipse.birt.report.model.api.DesignElementHandle)
	 */

	@Override
	public IDesignElement getElement( DesignElementHandle handle )
	{
		return ElementUtil.getElement( handle );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createDataSet(org.eclipse.birt.report.model.api.DataSetHandle)
	 */

	@Override
	public IDataSet createDataSet( DataSetHandle handle )
	{
		return new DataSet( handle );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createResultSetColumn
	 * (org.eclipse.birt.report.model.api.ResultSetColumnHandle)
	 */

	@Override
	public IResultSetColumn createResultSetColumn(
			ResultSetColumnHandle columnHandle )
	{
		return new ResultSetColumnImpl( columnHandle );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createResultSetColumn()
	 */

	@Override
	public IResultSetColumn createResultSetColumn( )
	{
		return new ResultSetColumnImpl( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createDataSource(org.eclipse.birt.report.model.api.DataSourceHandle)
	 */

	@Override
	public IDataSource createDataSource( DataSourceHandle handle )
	{
		return new DataSource( handle );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createHighlightRule()
	 */

	@Override
	public IHighlightRule createHighlightRule( )
	{
		return new HighlightRuleImpl( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createHighlightRule
	 * (org.eclipse.birt.report.model.api.elements.structures.HighlightRule)
	 */

	@Override
	public IHighlightRule createHighlightRule( HighlightRule highlightRule )
	{
		return new HighlightRuleImpl( highlightRule );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createHighlightRule
	 * (org.eclipse.birt.report.model.api.HighlightRuleHandle)
	 */

	@Override
	public IHighlightRule createHighlightRule( HighlightRuleHandle handle )
	{
		return new HighlightRuleImpl( handle );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#createStyle
	 * (org.eclipse.birt.report.model.api.StyleHandle)
	 */
	@Override
	public IStyle createStyle( StyleHandle style )
	{
		return new Style( style );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createFilterConditionElement
	 * (org.eclipse.birt.report.model.api.FilterConditionElementHandle)
	 */
	@Override
	public IFilterConditionElement createFilterConditionElement(
			FilterConditionElementHandle handle )
	{
		return new FilterConditionElement( handle );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createSortElement(org.eclipse.birt.report.model.api.SortElementHandle)
	 */
	@Override
	public ISortElement createSortElement( SortElementHandle handle )
	{
		return new SortElement( handle );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.birt.report.model.api.simpleapi.ISimpleElementFactory#
	 * createExpression()
	 */

	@Override
	public IExpression createExpression( )
	{
		return new ExpressionImpl( new Expression( null, null ) );
	}

}
