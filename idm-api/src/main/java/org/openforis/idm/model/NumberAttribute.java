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
		return getUnitField().getValue();
	}

	public Unit getUnit() {
		String unitName = getUnitName();
		if ( unitName != null ) {
			Unit unit = getSurvey().getUnit(unitName);
			return unit;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Field<N> getNumberField() {
		return (Field<N>) getField(0);
	}

	@SuppressWarnings("unchecked")
	public Field<String> getUnitField() {
		return (Field<String>) getField(1);
	}

	public N getNumber() {
		return getNumberField().getValue();
	}
	
	public void setNumber(N value) {
		getNumberField().setValue(value);
		onUpdateValue();
	}
	
	public void setUnitName(String name) {
		getUnitField().setValue(name);
		onUpdateValue();
	}
	
	protected abstract T createValue(N value, Unit unit);
	
	@Override
	public void setValue(T value) {
		N number = value.getValue();
		Unit unit = value.getUnit();
		String unitName = unit == null ? null : unit.getName();
		getNumberField().setValue(number);
		getUnitField().setValue(unitName);
		onUpdateValue();
	}

	@Override
	public T getValue() {
		N value = (N) getNumberField().getValue();
		Unit unit = getUnit();
		return createValue(value, unit);
	}
	
	@Override
	public boolean isFilled() {
		return getNumberField().hasValue();
	}
	
	@Override
	public boolean isEmpty() {
		return ! getNumberField().hasValue();
	}
	
	
}
