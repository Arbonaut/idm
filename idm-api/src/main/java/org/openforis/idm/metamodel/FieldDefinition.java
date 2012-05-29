/**
 * 
 */
package org.openforis.idm.metamodel;

import org.openforis.idm.model.Field;
import org.openforis.idm.model.Node;

/**
 * @author S. Ricci
 * @author G. Miceli
 *
 */
public final class FieldDefinition<T> extends NodeDefinition {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String alias;
	private String suffix;
	private Class<T> valueType;
	
	public FieldDefinition(String name, String alias, String suffix, Class<T> valueType) {
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

	@Override
	public Node<?> createNode() {
		return new Field<T>(valueType); 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((suffix == null) ? 0 : suffix.hashCode());
		result = prime * result
				+ ((valueType == null) ? 0 : valueType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldDefinition<?> other = (FieldDefinition<?>) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (suffix == null) {
			if (other.suffix != null)
				return false;
		} else if (!suffix.equals(other.suffix))
			return false;
		if (valueType == null) {
			if (other.valueType != null)
				return false;
		} else if (!valueType.equals(other.valueType))
			return false;
		return true;
	}
	

	@Override
	public Integer getMaxCount() {
		return 1;
	}
}
