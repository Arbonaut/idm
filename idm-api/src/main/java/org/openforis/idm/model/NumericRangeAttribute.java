package org.openforis.idm.model;

import org.openforis.idm.metamodel.RangeAttributeDefinition;
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
	public Field<String> getUnitNameField() {
		return (Field<String>) getField(2);		
	}
	
	@SuppressWarnings("unchecked")
	public Field<Integer> getUnitIdField() {
		return (Field<Integer>) getField(3);		
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

	public String getUnitName(){
		return getUnitNameField().getValue();
	}
	
	public void setUnitName(String name){
		getUnitNameField().setValue(name);
		onUpdateValue();
	}

	public Integer getUnitId(){
		return getUnitIdField().getValue();
	}
	
	public void setUnitId(Integer id){
		getUnitIdField().setValue(id);
		onUpdateValue();
	}

	public Unit getUnit() {
		Integer unitId = getUnitId();
		Unit unit = null;
		if ( unitId != null ) {
			unit = getSurvey().getUnit(unitId);
		} else {
			String unitName = getUnitName();
			if ( unitName != null ) {
				unit = getSurvey().getUnit(unitName);
			}
		}
		return unit;
	}
	
	public void setUnit(Unit unit) {
		Integer unitId = unit == null ? null : unit.getId();
		setUnitId(unitId);
	}

	@Override
	public T getValue() {
		N from = getFromField().getValue();
		N to = getToField().getValue();
		Unit unit = getUnit();
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
			Integer unitId = unit == null ? null : unit.getId();
			getFromField().setValue(from);
			getToField().setValue(to);
			getUnitIdField().setValue(unitId);
		}
		onUpdateValue();
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
