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

package org.eclipse.birt.report.model.api.metadata;

import java.util.Iterator;

/**
 * Represents the method information for both class and element. The class
 * includes the argument list, return type, and whether this method is static or
 * constructor,
 */

public interface IMethodInfo extends ILocalizableInfo
{

	/**
	 * Returns the iterator of argument definition. Each one is a list that
	 * contains <code>IArgumentInfoList</code>.
	 * 
	 * @return iterator of argument definition.
	 */

	public Iterator<IArgumentInfoList> argumentListIterator( );

	/**
	 * Returns the script type for return.
	 * 
	 * @return the script type for return
	 */

	public String getReturnType( );

	/**
	 * Returns the script type for return.
	 * 
	 * @return the script type for return
	 */

	public IClassInfo getClassReturnType( );

	/**
	 * Returns the resource key for tool tip.
	 * 
	 * @return the resource key for tool tip
	 */

	@Override
	public String getToolTipKey( );

	/**
	 * Returns the display string for the tool tip of this method.
	 * 
	 * @return the user-visible, localized display name for the tool tip of this
	 *         method.
	 */

	@Override
	public String getToolTip( );

	/**
	 * Returns whether this method is constructor.
	 * 
	 * @return true, if this method is constructor
	 */

	public boolean isConstructor( );

	/**
	 * Returns whether this method is static.
	 * 
	 * @return true if this method is static
	 */

	public boolean isStatic( );

	/**
	 * Returns the method javadoc.
	 * 
	 * @return the javadoc
	 */

	public String getJavaDoc( );
}
