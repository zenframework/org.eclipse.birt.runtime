/*
 * Java(TM) OLAP Interface
 */

package javax.olap.cursor;

public interface Cursor extends javax.olap.query.querycoremodel.NamedObject
{

	public java.lang.Object clone( );

	@Override
	public boolean equals( java.lang.Object arg0 );

	@Override
	public int hashCode( );

}
