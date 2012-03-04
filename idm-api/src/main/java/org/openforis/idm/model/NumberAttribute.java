package org.openforis.idm.model;

import org.openforis.idm.metamodel.NumberAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class NumberAttribute<T extends Number> extends AtomicAttribute<NumberAttributeDefinition, T> {

	private static final long serialVersionUID = 1L;

	protected NumberAttribute(NumberAttributeDefinition definition, Class<T> valueType) {
		super(definition, valueType);
	}

}
