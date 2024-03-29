/***********************************************************************
 * Copyright (c) 2004, 2007 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Actuate Corporation - initial API and implementation
 ***********************************************************************/

package org.eclipse.birt.report.engine.layout.html;

import java.util.ArrayList;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.content.IPageContent;
import org.eclipse.birt.report.engine.content.IReportContent;
import org.eclipse.birt.report.engine.emitter.ContentEmitterAdapter;
import org.eclipse.birt.report.engine.emitter.ContentEmitterUtil;
import org.eclipse.birt.report.engine.emitter.IContentEmitter;
import org.eclipse.birt.report.engine.executor.IReportExecutor;
import org.eclipse.birt.report.engine.executor.ReportExecutorUtil;
import org.eclipse.birt.report.engine.extension.IReportItemExecutor;
import org.eclipse.birt.report.engine.extension.ReportItemExecutorBase;
import org.eclipse.birt.report.engine.ir.MasterPageDesign;
import org.eclipse.birt.report.engine.layout.html.buffer.DummyPageBuffer;
import org.eclipse.birt.report.engine.layout.html.buffer.INode;
import org.eclipse.birt.report.engine.layout.html.buffer.IPageBuffer;
import org.eclipse.birt.report.engine.layout.html.buffer.TableBreakBuffer;
import org.eclipse.birt.report.engine.presentation.TableColumnHint;

public class HTMLPageLM extends HTMLBlockStackingLM
{

	protected IReportContent report;

	protected IPageContent pageContent;

	protected IReportExecutor reportExecutor = null;

	public HTMLPageLM( HTMLReportLayoutEngine engine, IReportContent report,
			IReportExecutor executor, IContentEmitter emitter )
	{
		super( engine.getFactory( ) );
		this.report = report;
		this.reportExecutor = executor;
		this.emitter = emitter;
		this.executor = new ReportItemExecutorBase( ) {

			@Override
			public void close( )
			{
			}

			@Override
			public IContent execute( )
			{
				return pageContent;
			}

			@Override
			public IReportItemExecutor getNextChild( ) throws BirtException
			{
				return reportExecutor.getNextChild( );
			}

			@Override
			public boolean hasNextChild( ) throws BirtException
			{
				return reportExecutor.hasNextChild( );
			}
		};
		
		context.setPageLM( this );
	}

	@Override
	public int getType( )
	{
		return LAYOUT_MANAGER_PAGE;
	}

	boolean isLastPage = false;
	boolean isFirstPage = true;

	@Override
	public boolean layout( ) throws BirtException
	{
		if ( context.getCancelFlag( ) )
		{
			close( );
			isLastPage = true;
			return false;
		}
		start( isFirstPage );
		boolean hasNextPage = layoutNodes( );
		if ( isChildrenFinished( ) )
		{
			isLastPage = true;
		}
		isFirstPage = false;
		end( isLastPage );
		context.initilizePage( );
		return hasNextPage;
	}

	@Override
	public boolean isFinished( )
	{
		return isLastPage;
	}

	public void layoutPageContent( IPageContent pageContent )
			throws BirtException
	{
		IContent header = pageContent.getPageHeader( );
		if ( header != null )
		{
			pageContent.setPageHeader( layoutContent( header ) );
		}
		IContent footer = pageContent.getPageFooter( );
		if ( footer != null )
		{
			pageContent.setPageFooter( layoutContent( footer ) );
		}

	}

	protected IContent layoutContent( IContent content ) throws BirtException
	{
		if ( content == null )
		{
			return null;
		}
		ContentDOMEmitter domEmitter = new ContentDOMEmitter( content, emitter );
		boolean pageBreak = context.allowPageBreak( );
		IPageBuffer pageBuffer = context.getPageBufferManager( );
		context.setPageBufferManager( new PageContentBuffer( ) );
		context.setAllowPageBreak( false );
		engine.layout( this, content, domEmitter );
		context.setAllowPageBreak( pageBreak );
		context.setPageBufferManager( pageBuffer );
		domEmitter.refreshChildren( );
		return content;
	}

	@Override
	protected void start( boolean isFirst ) throws BirtException
	{
		context.getBufferFactory( ).refresh( );
		context.setPageBufferManager( createPageBuffer( ) );
		MasterPageDesign pageDesign = getMasterPage( report );
		pageContent = ReportExecutorUtil.executeMasterPage( reportExecutor,
				context.getPageNumber( ), pageDesign );
		if ( pageContent != null && context.needLayoutPageContent( ) )
		{
			layoutPageContent( pageContent );
		}
		if ( emitter != null )
		{
			context.getPageBufferManager( ).startContainer( pageContent,
					isFirst, emitter, true );
		}
	}

	@Override
	protected IContent getContent( )
	{
		return pageContent;
	}

	protected IPageBuffer createPageBuffer( )
	{
		IPageBuffer bufferMgr = null;
		if ( context.allowPageBreak )
		{
			bufferMgr = new TableBreakBuffer( null, context );
		}
		else
		{
			bufferMgr = new DummyPageBuffer( context, reportExecutor );
		}
		return bufferMgr;
	}

	@Override
	protected void end( boolean finished ) throws BirtException
	{
		if ( emitter != null )
		{
			context.getPageBufferManager( ).endContainer( pageContent,
					finished, emitter, true );
			context.getBufferFactory( ).close( );
		}
		if ( !finished )
		{
			context.getPageHintManager( ).resetRowHint( );
		}
		context.setEmptyPage( false );
	}

	public class ContentDOMEmitter extends ContentEmitterAdapter
	{

		protected ArrayList nodes = new ArrayList( );
		protected BufferNode current;
		protected IContentEmitter emitter;

		public ContentDOMEmitter( IContent root, IContentEmitter emitter )
		{
			this.emitter = emitter;
			current = new BufferNode( root, null );
			nodes.add( current );
		}

		@Override
		public String getOutputFormat( )
		{
			return emitter.getOutputFormat( );
		}

		public void refreshChildren( )
		{
			for ( int i = 0; i < nodes.size( ); i++ )
			{
				BufferNode node = (BufferNode) nodes.get( i );
				node.content.getChildren( ).clear( );
				node.content.getChildren( ).addAll( node.children );
				// FIMXE need set parent?
			}
		}

		@Override
		public void startContent( IContent content )
		{
			if ( current != null )
			{
				if ( content != null )
				{
					current.children.add( content );
					current = new BufferNode( content, current );
					nodes.add( current );
				}
				else
				{
					current = null;
				}
			}

		}

		@Override
		public void endContent( IContent content )
		{
			if ( current != null )
			{
				if ( content != null )
				{
					current = current.parent;
				}
			}
		}
	}

	static class BufferNode
	{

		IContent content;
		ArrayList children = new ArrayList( );
		BufferNode parent;

		public BufferNode( IContent content, BufferNode parent )
		{
			this.content = content;
			this.parent = parent;
		}

		public void addChild( IContent child )
		{
			children.add( child );
		}
	}

	public static class PageContentBuffer implements IPageBuffer
	{

		@Override
		public boolean isRepeated( )
		{
			return false;
		}

		@Override
		public void setRepeated( boolean isRepeated )
		{
		}

		@Override
		public void endContainer( IContent content, boolean finished,
				IContentEmitter emitter, boolean visible ) throws BirtException
		{
			if ( content != null && visible )
			{
				ContentEmitterUtil.endContent( content, emitter );
			}
		}

		@Override
		public void startContainer( IContent content, boolean isFirst,
				IContentEmitter emitter, boolean visible ) throws BirtException
		{
			if ( content != null && visible )
			{
				ContentEmitterUtil.startContent( content, emitter );
			}

		}

		@Override
		public void startContent( IContent content, IContentEmitter emitter,
				boolean visible ) throws BirtException
		{
			if ( content != null && visible )
			{
				ContentEmitterUtil.startContent( content, emitter );
				ContentEmitterUtil.endContent( content, emitter );
			}

		}

		@Override
		public void closePage( INode[] nodeList )
		{
			// TODO Auto-generated method stub

		}

		@Override
		public boolean finished( )
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void flush( )
		{
			// TODO Auto-generated method stub

		}

		@Override
		public INode[] getNodeStack( )
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void openPage( INode[] nodeList )
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void addTableColumnHint( TableColumnHint hint )
		{
			// TODO Auto-generated method stub

		}

	}

}
