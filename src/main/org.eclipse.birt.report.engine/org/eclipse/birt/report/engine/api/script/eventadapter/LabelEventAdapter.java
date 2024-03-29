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
package org.eclipse.birt.report.engine.api.script.eventadapter;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.element.ILabel;
import org.eclipse.birt.report.engine.api.script.eventhandler.ILabelEventHandler;
import org.eclipse.birt.report.engine.api.script.instance.ILabelInstance;

/**
 * Default (empty) implementation of the ILabelEventHandler interface
 */
public class LabelEventAdapter implements ILabelEventHandler
{

	@Override
	public void onPrepare( ILabel labelHandle, IReportContext reportContext )
			throws ScriptException
	{

	}

	@Override
	public void onCreate( ILabelInstance label, IReportContext reportContext )
			throws ScriptException
	{

	}

	@Override
	public void onRender( ILabelInstance label, IReportContext reportContext )
			throws ScriptException
	{

	}

	@Override
	public void onPageBreak( ILabelInstance label, IReportContext reportContext )
			throws ScriptException
	{
		
	}

}
