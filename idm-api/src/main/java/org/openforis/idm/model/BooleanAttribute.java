package org.openforis.idm.model;

import org.openforis.idm.metamodel.BooleanAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class BooleanAttribute extends AtomicAttribute<BooleanAttributeDefinition, Boolean> {

	public BooleanAttribute(BooleanAttributeDefinition definition) {
		super(definition, Boolean.class);
	}

	@Override
	public Boolean createValue(String string) {
		return Boolean.parseBoolean(string);
	}
	
}
