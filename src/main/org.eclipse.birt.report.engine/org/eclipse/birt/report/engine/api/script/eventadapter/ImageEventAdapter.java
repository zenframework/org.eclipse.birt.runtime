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
import org.eclipse.birt.report.engine.api.script.element.IImage;
import org.eclipse.birt.report.engine.api.script.eventhandler.IImageEventHandler;
import org.eclipse.birt.report.engine.api.script.instance.IImageInstance;

/**
 * Default (empty) implementation of the IImageEventHandler interface
 */
public class ImageEventAdapter implements IImageEventHandler
{

	@Override
	public void onPrepare( IImage imageHandle, IReportContext reportContext )
			throws ScriptException
	{

	}

	@Override
	public void onCreate( IImageInstance image, IReportContext reportContext )
			throws ScriptException
	{

	}

	@Override
	public void onRender( IImageInstance image, IReportContext reportContext )
			throws ScriptException
	{

	}

	@Override
	public void onPageBreak( IImageInstance image, IReportContext reportContext )
			throws ScriptException
	{

	}

}
