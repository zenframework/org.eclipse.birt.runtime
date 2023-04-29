
package org.eclipse.birt.report.engine.internal.executor.dom;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.content.IReportContent;
import org.eclipse.birt.report.engine.executor.IReportExecutor;
import org.eclipse.birt.report.engine.extension.IReportItemExecutor;
import org.eclipse.birt.report.engine.ir.MasterPageDesign;

public class DOMReportExecutor implements IReportExecutor
{

	DOMReportItemExecutorManager manager;
	IReportContent reportContent;

	public DOMReportExecutor( IReportContent reportContent, boolean cloneContent )
	{
		manager = new DOMReportItemExecutorManager( cloneContent );
	}

	@Override
	public IReportContent execute( )
	{
		childIterator = new ArrayList( ).iterator( );
		return reportContent;
	}

	@Override
	public void close( )
	{
	}

	Iterator childIterator;

	@Override
	public IReportItemExecutor getNextChild( )
	{
		if ( childIterator.hasNext( ) )
		{
			IContent child = (IContent) childIterator.next( );
			return manager.createExecutor( child );
		}
		return null;
	}

	@Override
	public boolean hasNextChild( )
	{
		return childIterator.hasNext( );
	}

	@Override
	public IReportItemExecutor createPageExecutor( long pageNumber,
			MasterPageDesign pageDesign )
	{
		return null;
	}
}
