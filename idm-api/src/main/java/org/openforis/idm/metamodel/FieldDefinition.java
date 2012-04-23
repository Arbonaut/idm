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
	private Class<?> valueType;
	
	public FieldDefinition(String name, String alias, Class<?> valueType) {
		super();
		this.name = name;
		this.alias = alias;
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
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("FieldDefinition name: ").append(name)
			.append(" alias: ").append(alias)
			.append(" type: ").append(valueType.getSimpleName())
		.toString();
	}
	
}
