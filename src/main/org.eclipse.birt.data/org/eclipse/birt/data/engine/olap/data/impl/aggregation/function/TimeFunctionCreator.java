package org.eclipse.birt.data.engine.olap.data.impl.aggregation.function;

import org.eclipse.birt.data.engine.api.timefunction.IParallelPeriod;
import org.eclipse.birt.data.engine.api.timefunction.IPeriodsFunction;
import org.eclipse.birt.data.engine.api.timefunction.ITimeFunctionCreator;


public class TimeFunctionCreator implements ITimeFunctionCreator
{
	@Override
	public IPeriodsFunction createPeriodsToDateFunction( String levelType, boolean isCurrent )
	{
		return TimeFunctionFactory.createPeriodsToDateFunction( levelType, isCurrent );
	}
	
	@Override
	public IPeriodsFunction createTrailingFunction( String levelType, int Offset )
	{
		return TimeFunctionFactory.createTrailingFunction( levelType, Offset );
	}
	
	@Override
	public IParallelPeriod createParallelPeriodFunction( String levelType, int Offset )
	{
		return TimeFunctionFactory.createParallelPeriodFunction( levelType, Offset );
	}
}
