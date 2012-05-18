/**
 * 
 */
package org.openforis.idm.metamodel;

/**
 * @author S. Ricci
 *
 */
public final class FieldDefinition {

	private String name;
	private String alias;
	private String suffix;
	private Class<?> valueType;
	
	public FieldDefinition(String name, String alias, String suffix, Class<?> valueType) {
		super();
		this.name = name;
		this.alias = alias;
		this.suffix = suffix;
		this.valueType = valueType;
	}

	public String getName() {
		return name;
	}
	
	public String getAlias() {
		return alias;
	}

	public Class<?> getValueType() {
		return valueType;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
