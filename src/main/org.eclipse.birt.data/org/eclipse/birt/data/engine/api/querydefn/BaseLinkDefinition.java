
package org.eclipse.birt.data.engine.api.querydefn;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.birt.data.engine.api.IBaseLinkDefinition;

public class BaseLinkDefinition implements IBaseLinkDefinition
{

    private String leftDataSet;
    private String rightDataSet;
    private List<String> leftColumns;
    private List<String> rightColumns;
    private String joinType;
    
    public BaseLinkDefinition(String leftDS, String rightDS, List<String> leftColumns, List<String> rightColumns, String joinType)
    {
        this.leftDataSet = leftDS;
        this.rightDataSet = rightDS;
        this.leftColumns = leftColumns;
        this.rightColumns = rightColumns;
        this.joinType = joinType;
    }

    @Override
	public String getLeftDataSet( )
    {
        return this.leftDataSet;
    }

    @Override
	public List<String> getLeftColumns( )
    {
        return this.leftColumns;
    }

    @Override
	public String getRightDataSet( )
    {
        return this.rightDataSet;
    }

    @Override
	public List<String> getRightColumns( )
    {
        return this.rightColumns;
    }

    @Override
	public String getJoinType( )
    {
        return this.joinType;
    }

    @Override
	public void setJoinType( String joinType )
    {
        this.joinType = joinType;
    }
}
