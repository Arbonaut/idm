package org.openforis.idm.model;

import org.openforis.idm.metamodel.AttributeDefinition;

public abstract class AtomicAttribute<D extends AttributeDefinition, V>  extends Attribute<D,V> {

	private static final long serialVersionUID = 1L;

	protected AtomicAttribute(D definition, Class<?> valueClass) {
		super(definition, valueClass);
	}

	@Override
	public V getValue() {
		AttributeField<V> field = getField();
		return field.getValue();
	}

	@Override
	public void setValue(V value) {
		AttributeField<V> field = getField();
		field.setValue(value);
	}

	@SuppressWarnings("unchecked")
	public AttributeField<V> getField() {
		return (AttributeField<V>) getField(0);
	}
}
