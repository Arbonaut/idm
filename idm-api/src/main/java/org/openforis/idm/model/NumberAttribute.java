package org.openforis.idm.model;

import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.Unit;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class NumberAttribute<N extends Number, T extends NumberValue<N>> extends Attribute<NumberAttributeDefinition, T> {

	private static final long serialVersionUID = 1L;

	protected NumberAttribute(NumberAttributeDefinition definition) {
		super(definition);
	}

	public String getUnitName() {
		return (String) getField(1).getValue();
	}

	@SuppressWarnings("unchecked")
	public void setUnitName(String unitName) {
		Field<String> unitFld = (Field<String>) getField(1);
		unitFld.setValue(unitName);
	}

	public abstract T getValue();

	@Override
	@SuppressWarnings("unchecked")
	public void setValue(T value) {
		// Set value
		Field<N> valueField = (Field<N>) getField(0);
		N val = value.getValue();
		valueField.setValue(val);
		// Set unit
		Field<String> unitField = (Field<String>) getField(1);
		Unit unit = value.getUnit();
		unitField.setValue(unit == null ? null : unit.getName());
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
