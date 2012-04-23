package org.openforis.idm.model;

import org.openforis.idm.metamodel.NumberAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class NumberAttribute<T extends Number> extends Attribute<NumberAttributeDefinition, T> {

	private static final long serialVersionUID = 1L;

	protected NumberAttribute(NumberAttributeDefinition definition, Class<T> valueType) {
		super(definition, valueType, String.class);
	}

	public String getUnitName() {
		return (String) getField(1).getValue();
	}

	@SuppressWarnings("unchecked")
	public void setUnitName(String unitName) {
		Field<String> unitFld = (Field<String>) getField(1);
		unitFld.setValue(unitName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getValue() {
		Field<T> field = (Field<T>) getField(0);
		return field.getValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setValue(T value) {
		Field<T> field = (Field<T>) getField(0);
		field.setValue(value);
		onUpdateValue();
	}

	@Override
	public boolean isFilled() {
		return getField(0).hasValue();
	}
	
	@Override
	public boolean isEmpty() {
		return getValue() == null;
	}
}
