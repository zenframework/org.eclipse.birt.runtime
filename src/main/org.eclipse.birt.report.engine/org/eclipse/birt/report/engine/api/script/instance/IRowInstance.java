package org.eclipse.birt.report.engine.api.script.instance;


public interface IRowInstance extends IReportElementInstance
{
	/**
	 * Get the style of the row
	 * @return style of the row
	 */
	@Override
	IScriptStyle getStyle( );

	/**
	 * Get the value of bookmark
	 * @return value of bookmark
	 */
	String getBookmarkValue( );

	/**
	 * Set the bookmark
	 * @param bookmark
	 */
	void setBookmark( String bookmark );

	/**
	 * Get the height of the row
	 * @return the height of the row
	 */
	@Override
	String getHeight( );

	/**
	 * Set the height of the row
	 * @param height
	 */
	@Override
	void setHeight( String height );

	/*
	IRowData getRowData( );
	*/

}