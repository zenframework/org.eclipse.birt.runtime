package org.eclipse.birt.report.engine.layout.content;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.extension.IReportItemExecutor;
import org.eclipse.birt.report.engine.extension.ReportItemExecutorBase;


public class LineStackingExecutor extends ReportItemExecutorBase
{
	protected IReportItemExecutor executor;
	protected IReportItemExecutor current;
	protected IReportItemExecutor next;
	
	
	public LineStackingExecutor(IReportItemExecutor first, IReportItemExecutor executor)
	{
		this.next = first;
		this.executor = executor;
	}
	@Override
	public void close( )
	{
		//do nothing
	}

	@Override
	public IContent execute( )
	{
		return null;
	}

	@Override
	public IReportItemExecutor getNextChild( ) throws BirtException
	{
		current = next;
		if(executor!=null && executor instanceof BlockStackingExecutor)
		{
			next = ((BlockStackingExecutor)executor).nextInline( );
		}
		else
		{
			next = null;
		}
		return current;
	}

	@Override
	public boolean hasNextChild( )
	{
		return next!=null;
	}
	
	
}
