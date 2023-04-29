
package org.eclipse.birt.report.engine.layout.pdf;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.extension.IReportItemExecutor;
import org.eclipse.birt.report.engine.layout.IBlockStackingLayoutManager;
import org.eclipse.birt.report.engine.layout.area.impl.AreaFactory;
import org.eclipse.birt.report.engine.layout.area.impl.ContainerArea;
import org.eclipse.birt.report.engine.layout.content.ItemExecutorWrapper;
import org.eclipse.birt.report.engine.layout.content.LineStackingExecutor;

public class PDFImageBlockContainerLM extends PDFBlockContainerLM
		implements
			IBlockStackingLayoutManager
{

	public PDFImageBlockContainerLM( PDFLayoutEngineContext context,
			PDFStackingLM parent, IContent content, IReportItemExecutor executor )
	{
		super( context, parent, content, executor );
		child = new PDFLineAreaLM( context, this,
				new LineStackingExecutor( new ItemExecutorWrapper( executor,
						content ), executor ) );
	}

	@Override
	protected boolean traverseChildren( ) throws BirtException
	{
		return traverseSingleChild( );
	}

	@Override
	protected void closeLayout( )
	{
		/**
		 * set root height.
		 * For Image block container, OffsetY and box property should be zero
		 */
		root.setHeight( getCurrentBP( ));
	}

	@Override
	protected void createRoot( )
	{
		root = (ContainerArea)AreaFactory.createLogicContainer( content.getReportContent( ) );
	}

	@Override
	protected void closeExecutor( )
	{
		
	}

}
