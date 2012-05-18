package org.openforis.idm.transform;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public class Column extends ColumnGroup {

	private Class<?> valueType;
	
	public Column() {
	}
		
	public Class<?> getValueType() {
		return valueType;
	}

	public void setValueType(Class<?> valueType) {
		this.valueType = valueType;
	}
}
