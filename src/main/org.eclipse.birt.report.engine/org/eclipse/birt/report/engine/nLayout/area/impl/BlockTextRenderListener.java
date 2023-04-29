package org.eclipse.birt.report.engine.nLayout.area.impl;

import org.eclipse.birt.report.engine.content.ITextContent;


/**
 * this listener is only invoked in HTML render phase
 */
public class BlockTextRenderListener implements ITextListener
{
	private int textStartPos = -1;
	private int textLength = 0;
	
	private int readTextLength = 0;
	
	private boolean listeningStatus = false;
	
	private int offset = 0;
	private int dimension = 0;
	
	protected BlockTextArea blockContainer = null;
	
	public BlockTextRenderListener( BlockTextArea blockContainer, int offset, int dimension )
	{
		this.blockContainer = blockContainer;
		this.offset = offset;
		this.dimension = dimension;
		onNewLineEvent( );
	}
	
	@Override
	public void onAddEvent( TextArea textArea )
	{
		if( listeningStatus ) 
		{
			if ( textStartPos == -1 )
			{
				textStartPos = textArea.offset;
			}
			readTextLength = readTextLength + textArea.textLength;
		}
	}
	
	@Override
	public void onNewLineEvent( )
	{
		if ( blockContainer.getCurrentBP( ) < offset
				|| blockContainer.getCurrentBP( ) > offset + dimension )
		{
			listeningStatus = false;
		}
		else
		{
			listeningStatus = true;
			textLength = readTextLength;
		}
	}
	
	@Override
	public void onTextEndEvent( )
	{
		if ( blockContainer.getCurrentBP( ) < offset
				|| blockContainer.getCurrentBP( ) > offset + dimension )
		{
			listeningStatus = false;
		}
		if ( listeningStatus )
		{
			textLength = readTextLength;
		}
	}
	
	public String getSplitText( )
	{
		ITextContent textContent = (ITextContent) blockContainer.content;
		if( textStartPos == -1 || textLength == 0 )
		{
			return "";
		}
		else
		{
			String splitText = textContent.getText( ).substring( textStartPos,
					textStartPos + textLength );
			return splitText;
		}
		
	}
}
