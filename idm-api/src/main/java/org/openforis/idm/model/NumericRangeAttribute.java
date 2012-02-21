package org.openforis.idm.model;

import org.openforis.idm.metamodel.RangeAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class NumericRangeAttribute<T extends NumericRange<V>,V extends Number> extends Attribute<RangeAttributeDefinition, T> {

	protected NumericRangeAttribute(RangeAttributeDefinition definition, Class<V> fieldType) {
		super(definition, fieldType, fieldType);
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
		V from = value.getFrom();
		V to = value.getTo();

		getFromField().setValue(from);
		getToField().setValue(to);
	}
	
	protected abstract T createRange(V from, V to);

}
