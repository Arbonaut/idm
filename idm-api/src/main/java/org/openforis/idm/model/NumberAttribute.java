package org.openforis.idm.model;

import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.Survey;
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
	
	public String getUnit() {
		return getUnitField().getValue();
	}
	
	public void setUnit(String name) {
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
		Survey survey = getSurvey();
		String unitName = (String) getUnitField().getValue();
		Unit unit = unitName == null ? null : survey.getUnit(unitName);
		
		return createValue(value, unit);
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
