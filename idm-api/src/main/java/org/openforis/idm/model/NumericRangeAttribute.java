package org.openforis.idm.model;

import org.openforis.idm.metamodel.RangeAttributeDefinition;
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

	@Override
	public T getValue() {
		V from = getFromField().getValue();
		V to = getToField().getValue();
		return createRange(from, to);
	}

	@Override
	public void setValue(T value) {
		if ( value == null ) {
			clearValue();
		} else {
			V from = value.getFrom();
			V to = value.getTo();
			getFromField().setValue(from);
			getToField().setValue(to);
		}
		onUpdateValue();
	}
	
	public String getUnitName(){
		return (String) getField(2).getValue();
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
	public void setUnitName(String unitName){
		Field<String> unitFld = (Field<String>) getField(2);
		unitFld.setValue(unitName);
	}
	
	@Override
	public boolean isFilled() {
		return getField(0).hasValue() && getField(1).hasValue(); 
	}
	
	@Override
	public boolean isEmpty() {
		return getFromField().getValue() == null && getToField().getValue() == null;
	}
	
	protected abstract T createRange(V from, V to);

}
