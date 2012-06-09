package org.openforis.idm.model;

import org.openforis.idm.metamodel.RangeAttributeDefinition;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.Unit;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class NumericRangeAttribute<T extends NumericRange<N>,N extends Number> extends Attribute<RangeAttributeDefinition, T> {

	private static final long serialVersionUID = 1L;

	protected NumericRangeAttribute(RangeAttributeDefinition definition) {
		super(definition);
	}

	@SuppressWarnings("unchecked")
	public Field<N> getFromField() {
		return (Field<N>) getField(0);
	}

	@SuppressWarnings("unchecked")
	public Field<N> getToField() {
		return (Field<N>) getField(1);
	}

	@SuppressWarnings("unchecked")
	public Field<String> getUnitField() {
		return (Field<String>) getField(2);		
	}
	public N getFrom() {
		return getFromField().getValue();
	}
	
	public void setFrom(N value) {
		getFromField().setValue(value);
		onUpdateValue();
	}	

	public N getTo() {
		return getToField().getValue();
	}
	
	public void setTo(N value) {
		getToField().setValue(value);
		onUpdateValue();
	}	

	public void setUnitName(String name){
		getUnitField().setValue(name);
		onUpdateValue();
	}
	
	@Override
	public T getValue() {
		N from = getFromField().getValue();
		N to = getToField().getValue();
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
			N from = value.getFrom();
			N to = value.getTo();
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
	
	public Unit getUnit() {
		String unitName = getUnitName();
		if ( unitName != null ) {
			Unit unit = getSurvey().getUnit(unitName);
			return unit;
		} else {
			return null;
		}
	}

	@Override
	public boolean isFilled() {
		return getField(0).hasValue() && getField(1).hasValue(); 
	}
	
	@Override
	public boolean isEmpty() {
		return getFromField().getValue() == null && getToField().getValue() == null;
	}
	
	protected abstract T createRange(N from, N to, Unit unit);

}
