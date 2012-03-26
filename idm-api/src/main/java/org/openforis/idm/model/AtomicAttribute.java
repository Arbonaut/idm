package org.openforis.idm.model;

import org.openforis.idm.metamodel.AttributeDefinition;

public abstract class AtomicAttribute<D extends AttributeDefinition, V>  extends Attribute<D,V> {

	private static final long serialVersionUID = 1L;

	protected AtomicAttribute(D definition, Class<?> valueClass) {
		super(definition, valueClass);
	}

	@Override
	public V getValue() {
		Field<V> field = getField();
		return field.getValue();
	}

	@Override
	public void setValue(V value) {
		Field<V> field = getField();
		field.setValue(value);
		onUpdateValue();
	}

	@SuppressWarnings("unchecked")
	public Field<V> getField() {
		return (Field<V>) getField(0);
	}
}
