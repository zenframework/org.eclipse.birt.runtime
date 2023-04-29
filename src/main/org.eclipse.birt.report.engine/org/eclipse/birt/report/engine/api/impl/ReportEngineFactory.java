
package org.eclipse.birt.report.engine.api.impl;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;

public class ReportEngineFactory implements IReportEngineFactory
{

	@Override
	public IReportEngine createReportEngine( final EngineConfig config )
	{
		return java.security.AccessController
				.doPrivileged( new java.security.PrivilegedAction<IReportEngine>( ) {

					@Override
					public IReportEngine run( )
					{
						return new ReportEngine( config );
					}
				} );
	}
}
