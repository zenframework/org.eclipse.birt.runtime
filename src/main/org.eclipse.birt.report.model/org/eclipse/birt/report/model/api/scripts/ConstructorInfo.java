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

package org.eclipse.birt.report.model.api.scripts;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.birt.report.model.api.metadata.IClassInfo;
import org.eclipse.birt.report.model.api.metadata.IMethodInfo;
import org.eclipse.birt.report.model.api.util.StringUtil;

/**
 * Represents the method information for both class and element. The class
 * includes the argument list, return type, and whether this method is static or
 * constructor,
 */

public class ConstructorInfo implements IMethodInfo
{

	/**
	 * 
	 */

	private List arguments;

	private final Constructor method;

	/**
	 * Constructor.
	 * 
	 * @param method
	 *            the method of java class
	 */

	protected ConstructorInfo( Constructor method )
	{
		this.method = method;

		addArgumentList( method.getParameterTypes( ) );
	}

	/**
	 * Returns the iterator of argument definition. Each one is a list that
	 * contains <code>ArgumentInfoList</code>.
	 * 
	 * @return iterator of argument definition.
	 */

	@Override
	public Iterator argumentListIterator( )
	{
		return arguments.iterator( );
	}

	/**
	 * Returns the resource key for tool tip.
	 * 
	 * @return the resource key for tool tip
	 */

	@Override
	public String getToolTipKey( )
	{
		return StringUtil.EMPTY_STRING;
	}

	/**
	 * Returns the display string for the tool tip of this method.
	 * 
	 * @return the user-visible, localized display name for the tool tip of this
	 *         method.
	 */

	@Override
	public String getToolTip( )
	{
		return StringUtil.EMPTY_STRING;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.api.metadata.ILocalizableInfo#getDisplayName()
	 */

	@Override
	public String getDisplayName( )
	{
		return StringUtil.EMPTY_STRING;
	}

	@Override
	public String getDisplayNameKey( )
	{
		return StringUtil.EMPTY_STRING;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.api.metadata.ILocalizableInfo#getName()
	 */

	@Override
	public String getName( )
	{
		return method.getName( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.api.metadata.IMethodInfo#getJavaDoc()
	 */
	@Override
	public String getJavaDoc( )
	{
		return StringUtil.EMPTY_STRING;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.api.metadata.IMethodInfo#getReturnType()
	 */

	@Override
	public String getReturnType( )
	{
		return method.getName( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.api.metadata.IMethodInfo#isConstructor()
	 */

	@Override
	public boolean isConstructor( )
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.api.metadata.IMethodInfo#isStatic()
	 */

	@Override
	public boolean isStatic( )
	{
		return Modifier.isStatic( method.getModifiers( ) );
	}

	@Override
	public IClassInfo getClassReturnType( )
	{
		return new ClassInfo( method.getClass( ) );
	}

	/**
	 * Adds an optional argument list to the method information.
	 * 
	 * @param argumentList
	 *            an optional argument list
	 * 
	 */

	void addArgumentList( Class[] argumentList )
	{
		if ( arguments == null )
			arguments = new ArrayList( );

		ArgumentInfoList argumentInfo = new ArgumentInfoList( argumentList );
		arguments.add( argumentInfo );
	}

}