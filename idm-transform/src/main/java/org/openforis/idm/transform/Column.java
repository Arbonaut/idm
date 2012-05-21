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
