package org.openforis.idm.model;

import org.openforis.idm.metamodel.RangeAttributeDefinition;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.Unit;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class NumericRangeAttribute<T extends NumericRange<V>,V extends Number> extends Attribute<RangeAttributeDefinition, T> {

	private static final long serialVersionUID = 1L;

	protected NumericRangeAttribute(RangeAttributeDefinition definition) {
		super(definition);
	}

	@SuppressWarnings("unchecked")
	public Field<V> getFromField() {
		return (Field<V>) getField(0);
	}

	@SuppressWarnings("unchecked")
	public Field<V> getToField() {
		return (Field<V>) getField(1);
	}

	@SuppressWarnings("unchecked")
	public Field<String> getUnitField() {
		return (Field<String>) getField(2);		
	}
	
	@Override
	public T getValue() {
		V from = getFromField().getValue();
		V to = getToField().getValue();
		String unitName = getUnitField().getValue();
		Survey survey = getSurvey();
		Unit unit = survey.getUnit(unitName);
		return createRange(from, to, unit);
	}

	@Override
	public void setValue(T value) {
		if ( value == null ) {
			clearValue();
		} else {
			V from = value.getFrom();
			V to = value.getTo();
			Unit unit = value.getUnit();
			String unitName = unit == null ? null : unit.getName();
			getFromField().setValue(from);
			getToField().setValue(to);
			getUnitField().setValue(unitName);
		}
		onUpdateValue();
	}
	
	public String getUnitName(){
		return getUnitField().getValue();
	}

	public void setUnitName(String unitName){
		getUnitField().setValue(unitName);
	}
	
	@Override
	public boolean isFilled() {
		return getField(0).hasValue() && getField(1).hasValue(); 
	}
	
	@Override
	public boolean isEmpty() {
		return getFromField().getValue() == null && getToField().getValue() == null;
	}
	
	protected abstract T createRange(V from, V to, Unit unit);

}
