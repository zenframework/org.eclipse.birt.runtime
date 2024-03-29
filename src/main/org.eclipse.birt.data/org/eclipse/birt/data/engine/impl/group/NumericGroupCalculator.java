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

package org.eclipse.birt.data.engine.impl.group;

import org.eclipse.birt.core.data.DataTypeUtil;
import org.eclipse.birt.core.exception.BirtException;

/**
 * This calculator is used to calculate a numeric group key basing group
 * interval.
 */
class NumericGroupCalculator extends GroupCalculator
{

	double doubleStartValue;

	/**
	 * 
	 * @param intervalStart
	 * @param intervalRange
	 * @throws BirtException
	 */
	public NumericGroupCalculator( Object intervalStart, double intervalRange )
			throws BirtException
	{
		super( intervalStart, intervalRange );
		intervalRange = (intervalRange == 0 ? 1 : intervalRange);
		this.intervalRange = intervalRange;
		if ( intervalStart == null )
			doubleStartValue = 0;
		else
			doubleStartValue = ( DataTypeUtil.toDouble( intervalStart ) ).doubleValue( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.data.engine.impl.group.GroupCalculator#calculate(java.lang.Object)
	 */
	@Override
	public Object calculate( Object value ) throws BirtException
	{
		if ( value == null )
		{
			return new Double( -1 );
		}

		double dValue = ( DataTypeUtil.toDouble( value ) ).doubleValue( );

		if ( dValue < doubleStartValue )
		{
			return new Double( -1 );
		}
		else
		{
			return new Double( Math.floor( ( dValue - doubleStartValue )
					/ intervalRange ) );

		}
	}
}
