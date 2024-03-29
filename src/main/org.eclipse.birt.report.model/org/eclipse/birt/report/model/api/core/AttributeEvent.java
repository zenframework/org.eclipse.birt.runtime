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

package org.eclipse.birt.report.model.api.core;

import org.eclipse.birt.report.model.api.activity.NotificationEvent;
import org.eclipse.birt.report.model.core.Module;

/**
 * Notification event that says that the attribute of the module is changed. The
 * listener can find out which report design changed by calling
 * {@link org.eclipse.birt.report.model.api.activity.NotificationEvent#getTarget}( ).
 * The listener can get the new attribute value from the focus object.
 */

public class AttributeEvent extends NotificationEvent
{

	/**
	 *  Name of 'fileName' attribute.  
	 */
	
	public static final String FILE_NAME_ATTRIBUTE = "fileName"; //$NON-NLS-1$

	/**
	 * The attribute name of the event.
	 */

	protected final String attrName;

	/**
	 * Constructs a new file name event with the given module.
	 * 
	 * @param module
	 *            the changed module
	 * @param attrName
	 *            the changed attribute name
	 */

	public AttributeEvent( Module module, String attrName )
	{
		super( module );
		this.attrName = attrName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.api.activity.NotificationEvent#getEventType()
	 */

	@Override
	public int getEventType( )
	{
		return ATTRIBUTE_EVENT;
	}

	/**
	 * Gets the changed attribute name.
	 * 
	 * @return the changed attribute name
	 */

	public String getAttributeName( )
	{
		return this.attrName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.api.activity.NotificationEvent#isSame(org.eclipse.birt.report.model.api.activity.NotificationEvent)
	 */

	@Override
	public boolean isSame( NotificationEvent event )
	{
		if ( !super.isSame( event ) )
			return false;
		AttributeEvent attrEvent = (AttributeEvent) event;
		if ( attrName != null
				&& !attrName.equals( attrEvent.getAttributeName( ) ) )
			return false;
		if ( attrName == null && attrEvent.getAttributeName( ) != null )
			return false;
		return true;
	}

}