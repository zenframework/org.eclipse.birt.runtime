/*******************************************************************************
 * Copyright (c) 2005 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/
package org.eclipse.birt.report.model.simpleapi;

import org.eclipse.birt.report.model.api.ListGroupHandle;
import org.eclipse.birt.report.model.api.simpleapi.IListGroup;

public class ListGroup extends Group implements IListGroup
{

	public ListGroup( ListGroupHandle group )
	{
		super( group );
	}

}
