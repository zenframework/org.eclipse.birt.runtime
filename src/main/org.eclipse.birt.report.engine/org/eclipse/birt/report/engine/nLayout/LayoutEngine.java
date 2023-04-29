/***********************************************************************
 * Copyright (c) 2009 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Actuate Corporation - initial API and implementation
 ***********************************************************************/

package org.eclipse.birt.report.engine.nLayout;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.format.NumberFormatter;
import org.eclipse.birt.report.engine.api.IEngineTask;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.content.Dimension;
import org.eclipse.birt.report.engine.content.IAutoTextContent;
import org.eclipse.birt.report.engine.content.IBandContent;
import org.eclipse.birt.report.engine.content.ICellContent;
import org.eclipse.birt.report.engine.content.IContainerContent;
import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.content.IForeignContent;
import org.eclipse.birt.report.engine.content.IListBandContent;
import org.eclipse.birt.report.engine.content.IListContent;
import org.eclipse.birt.report.engine.content.IListGroupContent;
import org.eclipse.birt.report.engine.content.IPageContent;
import org.eclipse.birt.report.engine.content.IReportContent;
import org.eclipse.birt.report.engine.content.IRowContent;
import org.eclipse.birt.report.engine.content.ITableBandContent;
import org.eclipse.birt.report.engine.content.ITableGroupContent;
import org.eclipse.birt.report.engine.css.engine.value.DataFormatValue;
import org.eclipse.birt.report.engine.css.engine.value.css.CSSConstants;
import org.eclipse.birt.report.engine.emitter.ContentEmitterUtil;
import org.eclipse.birt.report.engine.emitter.IContentEmitter;
import org.eclipse.birt.report.engine.emitter.IEmitterServices;
import org.eclipse.birt.report.engine.executor.ExecutionContext;
import org.eclipse.birt.report.engine.ir.MasterPageDesign;
import org.eclipse.birt.report.engine.ir.SimpleMasterPageDesign;
import org.eclipse.birt.report.engine.layout.ILayoutPageHandler;
import org.eclipse.birt.report.engine.layout.PDFConstants;
import org.eclipse.birt.report.engine.layout.html.HTMLLayoutContext;
import org.eclipse.birt.report.engine.layout.pdf.emitter.LayoutEmitterAdapter;
import org.eclipse.birt.report.engine.layout.pdf.font.FontInfo;
import org.eclipse.birt.report.engine.layout.pdf.text.Chunk;
import org.eclipse.birt.report.engine.layout.pdf.text.ChunkGenerator;
import org.eclipse.birt.report.engine.layout.pdf.util.HTML2Content;
import org.eclipse.birt.report.engine.layout.pdf.util.PropertyUtil;
import org.eclipse.birt.report.engine.nLayout.area.IContainerArea;
import org.eclipse.birt.report.engine.nLayout.area.ILayout;
import org.eclipse.birt.report.engine.nLayout.area.impl.AbstractArea;
import org.eclipse.birt.report.engine.nLayout.area.impl.AreaFactory;
import org.eclipse.birt.report.engine.nLayout.area.impl.CellArea;
import org.eclipse.birt.report.engine.nLayout.area.impl.ContainerArea;
import org.eclipse.birt.report.engine.nLayout.area.impl.ForeignHTMLRegionLayout;
import org.eclipse.birt.report.engine.nLayout.area.impl.ListArea;
import org.eclipse.birt.report.engine.nLayout.area.impl.ListGroupArea;
import org.eclipse.birt.report.engine.nLayout.area.impl.PageArea;
import org.eclipse.birt.report.engine.nLayout.area.impl.RepeatableArea;
import org.eclipse.birt.report.engine.nLayout.area.impl.TextArea;
import org.eclipse.birt.report.engine.nLayout.area.impl.TextAreaLayout;
import org.eclipse.birt.report.engine.nLayout.area.style.TextStyle;
import org.eclipse.birt.report.engine.util.BidiAlignmentResolver;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;

import com.ibm.icu.util.ULocale;

public class LayoutEngine extends LayoutEmitterAdapter
		implements
			IContentEmitter
{
	protected IContentEmitter emitter;

	protected LayoutContext context;

	protected ContainerArea current;

	protected AreaFactory af = null;
	
	protected ILayoutPageHandler pageHandler;

	/**
	 * identify if current area is the first area generated by content
	 */
	protected boolean isFirst = true;

	public LayoutEngine( IContentEmitter emitter,
			IRenderOption renderOptions, ExecutionContext executionContext,
			long documentTotalPage )
	{
		this.emitter = emitter;
		context = new LayoutContext( );
		af = new AreaFactory( this );
		// FIXME
		setupLayoutOptions( renderOptions );
		String format = "pdf";
		if ( renderOptions != null )
		{
			format = renderOptions.getOutputFormat( );
		}
		context.setFormat( format );
		context.setLocale( executionContext.getLocale( ) );
		context.setEngineTaskType( executionContext.getTaskType( ) );
		long filteredTotalPage = executionContext.getFilteredTotalPage( );
		if ( filteredTotalPage == 0 )
		{
			context.totalPage = documentTotalPage;
		}
		else
		{
			context.totalPage = filteredTotalPage;
		}
	}
	
	public LayoutEngine( HTMLLayoutContext htmlLayoutContext, IContentEmitter emitter,
			IRenderOption renderOptions, ExecutionContext executionContext,
			long totalPage )
	{
		this( emitter, renderOptions, executionContext, totalPage );
		context.setHtmlLayoutContext( htmlLayoutContext );
	}
	
	public LayoutEngine( LayoutContext context )
	{
		this.context = context;
		af = new AreaFactory(this);
	}

	@Override
	public void initialize( IEmitterServices service ) throws BirtException
	{
		if ( emitter != null )
		{
			emitter.initialize( service );
		}
		ReportDesignHandle designHandle = (ReportDesignHandle) service
				.getReportRunnable( ).getDesignHandle( );
		if ( designHandle != null )
		{
			String reportLayoutPreference = designHandle.getLayoutPreference( );
			context
					.setFixedLayout( DesignChoiceConstants.REPORT_LAYOUT_PREFERENCE_FIXED_LAYOUT
							.equals( reportLayoutPreference ) );
		}
		if ( context.getHtmlLayoutContext( ) != null )
		{
			if ( context.isFixedLayout( ) )
			{
				context.setAutoPageBreak( context.getHtmlLayoutContext( )
						.allowPageBreak( ) );
			}
		}
	}
	
	public void createPageHintGenerator( )
	{
		context.createPageHintGenerator( );
	}
	
	protected void setupLayoutOptions( IRenderOption renderOptions )
	{
		Map options = null;
		if ( renderOptions != null )
		{
			options = renderOptions.getOptions( );
		}
		if ( options != null )
		{
			Object fitToPage = options.get( IPDFRenderOption.FIT_TO_PAGE );
			if ( fitToPage != null && fitToPage instanceof Boolean )
			{
				if ( ( (Boolean) fitToPage ).booleanValue( ) )
				{
					context.setFitToPage( true );
				}
			}
			Object pageBreakOnly = options
					.get( IPDFRenderOption.PAGEBREAK_PAGINATION_ONLY );
			if ( pageBreakOnly != null && pageBreakOnly instanceof Boolean )
			{
				if ( ( (Boolean) pageBreakOnly ).booleanValue( ) )
				{
					context.setPagebreakPaginationOnly( true );
				}
			}
			Object pageOverflow = options.get( IPDFRenderOption.PAGE_OVERFLOW );
			if ( pageOverflow != null && pageOverflow instanceof Integer )
			{
				int pageOverflowType = ( (Integer) pageOverflow ).intValue( );
				context.setPageOverflow( pageOverflowType );
				if ( pageOverflowType == IPDFRenderOption.OUTPUT_TO_MULTIPLE_PAGES )
				{
					context.setPagebreakPaginationOnly( false );
				}
				else
				{
					context.setPagebreakPaginationOnly( true );
				}
			}
			else
			{
				if ( context.fitToPage( ) )
				{
					context.setPageOverflow( IPDFRenderOption.FIT_TO_PAGE_SIZE );
					context.setPagebreakPaginationOnly( true );
				}
			}
			/*
			 * Object outputDisplayNone = options .get(
			 * IPDFRenderOption.OUTPUT_DISPLAY_NONE ); if ( outputDisplayNone
			 * instanceof Boolean ) { if ( ( (Boolean) outputDisplayNone
			 * ).booleanValue( ) ) { context.setOutputDisplayNone( true ); } }
			 */

			Object textWrapping = options
					.get( IPDFRenderOption.PDF_TEXT_WRAPPING );
			if ( textWrapping != null && textWrapping instanceof Boolean )
			{
				if ( !( (Boolean) textWrapping ).booleanValue( ) )
				{
					context.setTextWrapping( false );
				}
			}
			Object pageLimit = options.get( IPDFRenderOption.PDF_PAGE_LIMIT );
			if ( pageLimit != null && pageLimit instanceof Integer )
			{
				int limit = ( (Integer) pageLimit ).intValue( );
				if ( limit > 0 )
				{
					context.setPageLimit( limit );
				}
			}

			Object fontSubstitution = options
					.get( IPDFRenderOption.PDF_FONT_SUBSTITUTION );
			if ( fontSubstitution != null
					&& fontSubstitution instanceof Boolean )
			{
				if ( !( (Boolean) fontSubstitution ).booleanValue( ) )
				{
					context.setFontSubstitution( false );
				}
			}
			Object bidiProcessing = options
					.get( IPDFRenderOption.PDF_BIDI_PROCESSING );
			if ( bidiProcessing != null && bidiProcessing instanceof Boolean )
			{
				if ( !( (Boolean) bidiProcessing ).booleanValue( ) )
				{
					context.setBidiProcessing( false );
				}
			}
			/*
			 * bidi_hcg: Only disable Bidi processing when the rtl flag is null,
			 * i.e. Bidi support is disabled.
			 */
			// if ( options.get( IRenderOption.RTL_FLAG ) == null )
			// {
			// context.setBidiProcessing( false );
			// }
			Object hyhenation = options.get( IPDFRenderOption.PDF_HYPHENATION );
			if ( hyhenation != null && hyhenation instanceof Boolean )
			{
				if ( ( (Boolean) hyhenation ).booleanValue( ) )
				{
					context.setEnableHyphenation( true );
				}
			}

			Object dpi = options.get( IPDFRenderOption.DPI );
			if ( dpi != null && dpi instanceof Integer )
			{
				int renderDpi = ( (Integer) dpi ).intValue( );
				context.setDpi( renderDpi );
			}
			
			Object supportedImageFormats = options.get( IRenderOption.SUPPORTED_IMAGE_FORMATS );
			if ( supportedImageFormats != null && supportedImageFormats instanceof String )
			{
				context.setSupportedImageFormats( (String)supportedImageFormats );
			}
			
			Object reserveDocumentPageNumbers = options
					.get( IPDFRenderOption.RESERVE_DOCUMENT_PAGE_NUMBERS );
			if ( reserveDocumentPageNumbers != null
					&& reserveDocumentPageNumbers instanceof Boolean )
			{
				if ( ( (Boolean) reserveDocumentPageNumbers ).booleanValue( ) )
				{
					context.setReserveDocumentPageNumbers( true );
				}
				else
				{
					context.setReserveDocumentPageNumbers( false );
				}
			}
			// Object rtlFlag = options.get( IRenderOption.RTL_FLAG );
			// if (rtlFlag != null && rtlFlag instanceof Boolean)
			// {
			// if (((Boolean)rtlFlag).booleanValue())
			// {
			// context.setRtl( true );
			// }
			// }
		}
	}
	
	@Override
	public String getOutputFormat( )
	{
		if( null != emitter )
		{
			return emitter.getOutputFormat( );
		}
		return null;
	}

	@Override
	public void start( IReportContent report ) throws BirtException
	{
		if( null != emitter )
		{
			emitter.start( report );
		}
		context.setReport( report );
	}

	@Override
	public void end( IReportContent report ) throws BirtException
	{
		if ( emitter != null )
		{
			resolveTotalPage( emitter );
		}
		context.setFinished( true );
		if ( pageHandler != null )
		{
			pageHandler.onPage( context.pageNumber, context );
		}
		if ( null != emitter )
		{
			emitter.end( report );
		}
	}

	@Override
	public void startContainer( IContainerContent container )
			throws BirtException
	{
		checkDisplayNone( container, true );
		_startContainer( container );
	}
	
	@Override
	public void endContainer( IContainerContent container )
			throws BirtException
	{
		_endContainer( container );
		checkDisplayNone( container, false );
	}

	protected void setContainer( ContainerArea container )
			throws BirtException
	{
		container.initialize( );
		current = container;
	}

	protected void closeContainer( ) throws BirtException
	{
		if ( current != null )
		{
			current.close( );
			current = current.getParent( );
		}
	}

	
	LinkedList<IContent> unfinishedContents = new LinkedList<IContent>( );
	
	protected IContent contentDisplayNone = null;
	
	protected void checkDisplayNone( IContent content, boolean isStart )
	{
		if ( isStart )
		{
			if ( context.isDisplayNone( ) )
			{
				return;
			}
			else
			{
				if ( PropertyUtil.isDisplayNone( content ) )
				{
					contentDisplayNone = content;
					context.setDisplayNone( true );
				}
			}
		}
		else
		{
			if ( context.isDisplayNone( ) )
			{
				if ( PropertyUtil.isDisplayNone( content ) )
				{
					if ( contentDisplayNone.getInstanceID( ) == content
							.getInstanceID( ) )
					{
						context.setDisplayNone( false );
						contentDisplayNone = null;
					}
				}
			}
		}
	}

	protected void _startContainer( IContent container ) throws BirtException
	{
		boolean isInline = PropertyUtil.isInlineElement( container );
		if ( isInline )
		{
			if ( !unfinishedContents.isEmpty( )
					&& container.getParent( ) == unfinishedContents.peek( ) )
			{
				IContent parent = unfinishedContents.poll( );
				_startContainer( parent );
			}
			else
			{
				if ( current != null && current.isInlineStacking( ) )
				{

				}
				else
				{
					setContainer( af.createLineArea( current, context ) );
				}
			}
		}
		else
		{
			while ( current != null && current.isInlineStacking( ) )
			{
				if ( null != current.getContent( ) )
				{
					unfinishedContents.add( current.getContent( ) );
				}
				closeContainer( );
			}
		}
		ContainerArea area = (ContainerArea) af.createArea( current, context,
				container );
		setContainer( area );
	}
	
	protected boolean checkUnfinishedContent( IContent content )
	{
		if ( !unfinishedContents.isEmpty( ) )
		{
			if ( unfinishedContents.contains( content ) )
			{
				unfinishedContents.remove( content );
				return true;
			}
		}
		return false;
	}

	protected void _endContainer( IContent container ) throws BirtException
	{
		if ( checkUnfinishedContent(container) )
		{
			return;
		}
		boolean isInline = PropertyUtil.isInlineElement( container );
		if ( isInline )
		{
			if ( current != null && current.isInlineStacking( ) )
			{

			}
			else
			{

			}
		}
		else
		{
			while ( current != null && current.isInlineStacking( ) )
			{
				closeContainer( );
			}
		}
		closeContainer( );
	}
	
	@Override
	public void endList( IListContent list ) throws BirtException
	{
		if ( checkUnfinishedContent( list ) )
		{
			return;
		}
		while ( current != null && !( current instanceof ListArea ) )
		{
			closeContainer( );
		}
		closeContainer( );
		checkDisplayNone( list, false );
	}

	
	protected void _endCell( ICellContent cell ) throws BirtException
	{
		while ( !( current instanceof CellArea ) )
		{
			current.close( );
			current = current.getParent( );
		}
		current.close( );
		current = current.getParent( );
	}

	@Override
	public void startContent( IContent content ) throws BirtException
	{
		boolean isInline = PropertyUtil.isInlineElement( content );
		if ( isInline )
		{
			if ( !unfinishedContents.isEmpty( )
					&& content.getParent( ) == unfinishedContents.peek( ) )
			{
				IContent parent = unfinishedContents.poll( );
				_startContainer( parent );
			}
			else
			{
				if ( current != null && current.isInlineStacking( ) )
				{

				}
				else
				{
					setContainer( af.createLineArea( current, context ) );
				}
			}
		}
		else
		{
			while ( current != null && current.isInlineStacking( ) )
			{
				if ( null != current.getContent( ) )
				{
					unfinishedContents.add( current.getContent( ) );
				}
				closeContainer( );
			}
		}
		checkDisplayNone( content, true );
		ILayout layout = af.createLayout( current, context, content );
		if ( layout != null )
		{
			layout.layout( );
		}
		checkDisplayNone( content, false );
	}

	@Override
	public void endContent( IContent content ) throws BirtException
	{
		if ( checkUnfinishedContent( content ) )
		{
			return;
		}
	}

	@Override
	public void startListBand( IListBandContent listBand ) throws BirtException
	{
		int bandType = listBand.getBandType( );
		if ( bandType == IBandContent.BAND_HEADER
				|| bandType == IBandContent.BAND_GROUP_HEADER )
		{
			if ( current instanceof RepeatableArea )
			{
				( (RepeatableArea) current ).setInHeaderBand( true );
			}
		}
	}

	@Override
	public void startListGroup( IListGroupContent listGroup )
			throws BirtException
	{
		super.startListGroup( listGroup );
	}

	@Override
	public void endListBand( IListBandContent listBand ) throws BirtException
	{
		int bandType = listBand.getBandType( );
		if ( bandType == IBandContent.BAND_HEADER
				|| bandType == IBandContent.BAND_GROUP_HEADER )
		{
			// the current may be a lineArea, groupArea or listArea
			ContainerArea container = current;
			while ( container != null && !( container instanceof ListArea )
					&& !( container instanceof ListGroupArea ) )
			{
				container = container.getParent( );
			}

			if ( container instanceof RepeatableArea )
			{
				( (RepeatableArea) container ).setInHeaderBand( false );
			}
		}
	}

//	public void startPage( IPageContent page ) throws BirtException
//	{
//		super.startPage( page );
//		if ( !context.autoPageBreak )
//		{
//			long number = page.getPageNumber( );
//			if ( number > 0 )
//			{
//				context.pageNumber = number;
//			}
//		}
//	}
//
//	public void endPage( IPageContent page ) throws BirtException
//	{
//		// TODO Auto-generated method stub
//		super.endPage( page );
//	}

	protected void startTableContainer( IContainerContent container )
			throws BirtException
	{
		setContainer( (ContainerArea) af.createArea( current, context,
				container ) );
	}

	protected void endTableContainer( IContainerContent container )
			throws BirtException
	{
		closeContainer( );
	}

	@Override
	public void startRow( IRowContent row ) throws BirtException
	{
		checkDisplayNone( row, true );
		startTableContainer( row );
	}

	@Override
	public void endRow( IRowContent row ) throws BirtException
	{
		endTableContainer( row );
		checkDisplayNone( row, false );
	}

	@Override
	public void startTableBand( ITableBandContent band ) throws BirtException
	{
		int bandType = band.getBandType( );
		if ( bandType == IBandContent.BAND_HEADER
				|| bandType == IBandContent.BAND_GROUP_HEADER )
		{
			if ( current instanceof RepeatableArea )
			{
				( (RepeatableArea) current ).setInHeaderBand( true );
			}
		}
	}

	@Override
	public void startTableGroup( ITableGroupContent group )
			throws BirtException
	{
		checkDisplayNone( group, true );
		startTableContainer( group );
	}

	@Override
	public void endTableBand( ITableBandContent band ) throws BirtException
	{
		int bandType = band.getBandType( );
		if ( bandType == IBandContent.BAND_HEADER
				|| bandType == IBandContent.BAND_GROUP_HEADER )
		{
			if ( current instanceof RepeatableArea )
			{
				( (RepeatableArea) current ).setInHeaderBand( false );
			}
		}
	}

	@Override
	public void endTableGroup( ITableGroupContent group ) throws BirtException
	{
		endTableContainer( group );
		checkDisplayNone( group, false );
	}

	@Override
	public void startCell( ICellContent cell ) throws BirtException
	{
		checkDisplayNone( cell, true );
		startTableContainer( cell );
	}

	@Override
	public void endCell( ICellContent cell ) throws BirtException
	{
		_endCell(cell);
		checkDisplayNone( cell, false );
	}
	
	protected void visitContent( IContent content, IContentEmitter emitter )
			throws BirtException
	{
		ContentEmitterUtil.startContent( content, emitter );
		java.util.Collection children = content.getChildren( );
		if ( children != null && !children.isEmpty( ) )
		{
			Iterator iter = children.iterator( );
			while ( iter.hasNext( ) )
			{
				IContent child = (IContent) iter.next( );
				visitContent( child, emitter );
			}
		}
		ContentEmitterUtil.endContent( content, emitter );
	}
	
	protected void visitChildren( IContent content, IContentEmitter emitter )
			throws BirtException
	{
		java.util.Collection children = content.getChildren( );
		if ( children != null && !children.isEmpty( ) )
		{
			Iterator iter = children.iterator( );
			while ( iter.hasNext( ) )
			{
				IContent child = (IContent) iter.next( );
				visitContent( child, emitter );
			}
		}
	}

	@Override
	public void startForeign( IForeignContent foreign ) throws BirtException
	{
		checkDisplayNone( foreign, true );
		if ( context.isFixedLayout( )
				&& context.getEngineTaskType( ) == IEngineTask.TASK_RUN
				&& IForeignContent.HTML_TYPE.equals( foreign.getRawType( ) ) )
		{
			HTML2Content.html2Content( foreign );
			processHTML( foreign );
		}
		else
		{
			_startContainer( foreign );
			if ( IForeignContent.HTML_TYPE.equals( foreign.getRawType( ) ) )
			{
				// build content DOM tree for HTML text
				HTML2Content.html2Content( foreign );
				java.util.Collection children = foreign.getChildren( );
				if ( children != null && !children.isEmpty( ) )
				{
					Iterator iter = children.iterator( );
					IContent child = (IContent) iter.next( );
					visitContent( child, this );
				}
				// FIXME
				foreign.getChildren( ).clear( );
			}
			_endContainer( foreign );
		}
		checkDisplayNone( foreign, false );
	}

	private void processHTML( IForeignContent foreign ) throws BirtException
	{
		boolean isInline = PropertyUtil.isInlineElement( foreign );
		if ( isInline )
		{
			if ( !unfinishedContents.isEmpty( )
					&& foreign.getParent( ) == unfinishedContents.peek( ) )
			{
				IContent parent = unfinishedContents.poll( );
				_startContainer( parent );
			}
			else
			{
				if ( current != null && current.isInlineStacking( ) )
				{

				}
				else
				{
					setContainer( af.createLineArea( current, context ) );
				}
			}
		}
		else
		{
			while ( current != null && current.isInlineStacking( ) )
			{
				if ( null != current.getContent( ) )
				{
					unfinishedContents.add( current.getContent( ) );
				}
				closeContainer( );
			}
		}

		ForeignHTMLRegionLayout rle = new ForeignHTMLRegionLayout( current,
				context, foreign );
		rle.layout( );
	}

	protected void resolveTotalPage( IContentEmitter emitter )
			throws BirtException
	{
		IContent con = context.getUnresolvedContent( );
		if ( !( con instanceof IAutoTextContent ) )
		{
			return;
		}

		IAutoTextContent totalPageContent = (IAutoTextContent) con;
		if ( null != totalPageContent )
		{
			DataFormatValue format = totalPageContent.getComputedStyle( )
					.getDataFormat( );
			NumberFormatter nf = null;
			if ( format == null )
			{
				nf = new NumberFormatter( );
			}
			else
			{
				String pattern = format.getNumberPattern( );
				String locale = format.getNumberLocale( );
				if ( locale == null )
					nf = new NumberFormatter( pattern );
				else
					nf = new NumberFormatter( pattern, new ULocale( locale ) );
			}

			totalPageContent.setText( nf.format( context.pageCount ) );

			AbstractArea totalPageArea = null;
			ChunkGenerator cg = new ChunkGenerator( context.getFontManager( ),
					totalPageContent, true, true );
			if ( cg.hasMore( ) )
			{
				Chunk c = cg.getNext( );
				Dimension d = new Dimension(
						(int) ( c.getFontInfo( ).getWordWidth( c.getText( ) ) * PDFConstants.LAYOUT_TO_PDF_RATIO ),
						(int) ( c.getFontInfo( ).getWordHeight( ) * PDFConstants.LAYOUT_TO_PDF_RATIO ) );
				totalPageArea = createTextArea( totalPageContent, c
						.getFontInfo( ), false );
				totalPageArea.setWidth( Math.min( context.getMaxWidth( ), d
						.getWidth( ) ) );
				totalPageArea.setHeight( Math.min( context.getMaxHeight( ), d
						.getHeight( ) ) );
			}

			String align = totalPageContent.getComputedStyle( ).getTextAlign( );
			// bidi_hcg: handle empty or justify align in RTL direction as right
			// alignment
			boolean isRightAligned = BidiAlignmentResolver.isRightAligned(
					totalPageContent, align, false );
			if ( ( isRightAligned || CSSConstants.CSS_CENTER_VALUE
					.equalsIgnoreCase( align ) ) )
			{
				int spacing = context.getTotalPageTemplateWidth( )
						- totalPageArea.getWidth( );
				if ( spacing > 0 )
				{
					if ( isRightAligned )
					{
						totalPageArea.setPosition( spacing
								+ totalPageArea.getX( ), totalPageArea.getY( ) );
					}
					else if ( CSSConstants.CSS_CENTER_VALUE
							.equalsIgnoreCase( align ) )
					{
						totalPageArea.setPosition( spacing / 2
								+ totalPageArea.getX( ), totalPageArea.getY( ) );
					}
				}
			}

			totalPageContent.setExtension( IContent.LAYOUT_EXTENSION,
					totalPageArea );
			emitter.startAutoText( totalPageContent );
		}
	}

	protected TextArea createTextArea( IAutoTextContent content,
			FontInfo fontInfo, boolean blankLine )
	{
		TextStyle textStyle = TextAreaLayout.buildTextStyle( content, fontInfo );
		String text = content.getText( );
		TextArea area = new TextArea( text, textStyle );
		area.setTextLength( text.length( ) );
		area.setAction( content.getHyperlinkAction( ) );
		return area;
	}

	boolean showPageFooter( SimpleMasterPageDesign masterPage, IPageContent page )
	{
		boolean showFooter = true;
		if ( !masterPage.isShowFooterOnLast( ) )
		{
			if ( page.getPageNumber( ) == context.totalPage )
			{
				showFooter = false;
			}
		}
		return showFooter;
	}

	@Override
	public void outputPage( IPageContent page ) throws BirtException
	{
		MasterPageDesign mp = (MasterPageDesign) page.getGenerateBy( );

		if ( mp instanceof SimpleMasterPageDesign )
		{
			Object obj = page.getExtension( IContent.LAYOUT_EXTENSION );

			if ( obj != null && obj instanceof PageArea )
			{
				PageArea pageArea = (PageArea) obj;

				if ( isFirst
						&& !( (SimpleMasterPageDesign) mp )
								.isShowHeaderOnFirst( ) )
				{
					pageArea.removeHeader( );
					isFirst = false;
				}
				if ( !showPageFooter( (SimpleMasterPageDesign) mp, page ) )
				{
					pageArea.removeFooter( );
				}

				if ( ( (SimpleMasterPageDesign) mp ).isFloatingFooter( ) )
				{
					ContainerArea footer = (ContainerArea) page.getFooter( );
					IContainerArea body = pageArea.getBody( );
					IContainerArea header = pageArea.getHeader( );
					if ( footer != null )
					{
						footer.setPosition( footer.getX( ), ( header == null
								? 0
								: header.getHeight( ) )
								+ ( body == null ? 0 : body.getHeight( ) ) );
					}
				}
			}
		}
		if ( null != emitter )
		{
			if ( !context.exceedPageLimit( ) )
			{
				emitter.startPage( page );
				emitter.endPage( page );
			}
		}
		context.pageCount++;
		if ( pageHandler != null )
		{
			pageHandler.onPage( context.pageNumber, context );
		}

	}

	@Override
	public ILayoutPageHandler getPageHandler( )
	{
		return pageHandler;
	}

	@Override
	public void setPageHandler( ILayoutPageHandler pageHandler )
	{
		this.pageHandler = pageHandler;
		
	}

}
