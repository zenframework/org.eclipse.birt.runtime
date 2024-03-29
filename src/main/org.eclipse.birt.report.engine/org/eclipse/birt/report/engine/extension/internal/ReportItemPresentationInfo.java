
package org.eclipse.birt.report.engine.extension.internal;

import java.util.Locale;

import org.eclipse.birt.data.engine.api.IBaseQueryDefinition;
import org.eclipse.birt.data.engine.api.IDataQueryDefinition;
import org.eclipse.birt.report.engine.api.IHTMLActionHandler;
import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.content.IStyle;
import org.eclipse.birt.report.engine.extension.IReportItemPresentationInfo;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;

public class ReportItemPresentationInfo implements IReportItemPresentationInfo
{

	private ExtendedItemHandle modelHandle = null;
	private IHTMLActionHandler ah = null;
	private ClassLoader loader = null;
	private IContent content = null;
	private String outputFormat = null;
	private IDataQueryDefinition[] queries = null;
	private int dpi;
	private IReportContext context = null;
	private String supportedImageFormats = null;

	public void setActionHandler( IHTMLActionHandler ah )
	{
		this.ah = ah;
	}

	@Override
	public IHTMLActionHandler getActionHandler( )
	{
		return ah;
	}

	public void setApplicationClassLoader( ClassLoader loader )
	{
		this.loader = loader;
	}

	@Override
	public ClassLoader getApplicationClassLoader( )
	{
		return loader;
	}

	public IStyle getDynamicStyle( )
	{
		if ( content != null )
			return content.getComputedStyle( );
		return null;
	}

	public void setExtendedItemContent( IContent content )
	{
		this.content = content;
	}

	@Override
	public IContent getExtendedItemContent( )
	{
		return content;
	}

	public Locale getLocale( )
	{
		if ( context != null )
		{
			return context.getLocale( );
		}
		return null;
	}

	public void setModelObject( ExtendedItemHandle modelHandle )
	{
		this.modelHandle = modelHandle;
	}

	@Override
	public ExtendedItemHandle getModelObject( )
	{
		return modelHandle;
	}

	public void setOutputFormat( String outputFormat )
	{
		this.outputFormat = outputFormat;
	}

	@Override
	public String getOutputFormat( )
	{
		return outputFormat;
	}

	public void setReportQueries( IDataQueryDefinition[] queries )
	{
		this.queries = queries;
	}

	@Override
	public IDataQueryDefinition[] getReportQueries( )
	{
		return queries;
	}

	public void setResolution( int dpi )
	{
		this.dpi = dpi;
	}

	@Override
	public int getResolution( )
	{
		return dpi;
	}

	public void setReportContext( IReportContext context )
	{
		this.context = context;
	}

	@Override
	public IReportContext getReportContext( )
	{
		return context;
	}

	public void setSupportedImageFormats( String supportedImageFormats )
	{
		this.supportedImageFormats = supportedImageFormats;
	}

	@Override
	public String getSupportedImageFormats( )
	{
		return supportedImageFormats;
	}
}
