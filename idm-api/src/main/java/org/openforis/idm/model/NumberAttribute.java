package org.openforis.idm.model;

import org.openforis.idm.metamodel.NumberAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class NumberAttribute<T extends Number> extends AtomicAttribute<NumberAttributeDefinition, T> {

	protected NumberAttribute(NumberAttributeDefinition definition) {
		super(definition);
	}
	
}
