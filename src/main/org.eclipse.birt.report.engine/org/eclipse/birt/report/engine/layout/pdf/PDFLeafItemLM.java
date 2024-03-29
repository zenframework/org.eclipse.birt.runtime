/***********************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Actuate Corporation - initial API and implementation
 ***********************************************************************/

package org.eclipse.birt.report.engine.layout.pdf;

import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.content.IStyle;
import org.eclipse.birt.report.engine.extension.IReportItemExecutor;
import org.eclipse.birt.report.engine.layout.area.IContainerArea;
import org.eclipse.birt.report.engine.layout.area.impl.AreaFactory;
import org.eclipse.birt.report.engine.layout.pdf.util.PropertyUtil;

public abstract class PDFLeafItemLM extends PDFAbstractLM
{

	public PDFLeafItemLM( PDFLayoutEngineContext context, PDFStackingLM parent,
			IContent content, IReportItemExecutor executor )
	{
		super( context, parent, content,  executor );
	}

	@Override
	protected void cancelChildren( )
	{

	}



	@Override
	public boolean allowPageBreak( )
	{
		return false;
	}
	
	@Override
	protected boolean hasNextChild()
	{
		return true;
	}
	
	@Override
	public void autoPageBreak()
	{
		return;
	}

}
