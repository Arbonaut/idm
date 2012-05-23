package org.openforis.idm.transform;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class Column extends ColumnGroup {

	private Class<?> valueType;
	
	public Column() {
	}
	
	public Column(String shortName, String heading, NodeDefinition nodeDefinition, 
				  ColumnGroup parentGroup, ColumnProvider provider, Class<?> valueType) {
		super(shortName, heading, nodeDefinition, parentGroup, provider);
		this.valueType = valueType;
	}


	public Class<?> getValueType() {
		return valueType;
	}

	public void setValueType(Class<?> valueType) {
		this.valueType = valueType;
	}

	/**
	 * Concatenates the heading names of the column and all groups using
	 * the a space as delimiter 
	 * @return
	 */
	public String getHeadings() {
		return getHeadings(" ");
	}
	
	/**
	 * Concatenates the heading names of the column and all groups using
	 * the specified delimiters 
	 * @param delim
	 * @return
	 */
	public String getHeadings(String delim) {
		StringBuilder sb = new StringBuilder();
		ColumnGroup c = this;
		do {
			String heading = c.getHeading();
			if ( sb.length() > 0 ) {
				sb.insert(0, delim);
			}
			sb.insert(0, heading);
			c = c.getParentGroup();
		} while ( c != null );
		
		return sb.toString();
	}

	/**
	 * Concatenates short name and short name of all groups, separating with underscore ("_") 
	 */
	public String getName() {
		StringBuilder sb = new StringBuilder();
		ColumnGroup c = this;
		do {
			String heading = c.getShortName();
			if ( sb.length() > 0 ) {
				sb.insert(0, '_');
			}
			sb.insert(0, heading);
			c = c.getParentGroup();
		} while ( c != null );
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("shortName", getShortName())
			.append("heading", getHeading())
			.append("nodeDefinition", getNodeDefinition())
			.append("parentGroup", getParentGroup())
			.append("valueType", valueType)
			.toString();
	}

}
