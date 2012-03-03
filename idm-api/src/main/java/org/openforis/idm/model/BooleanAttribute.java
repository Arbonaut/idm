package org.openforis.idm.model;

import org.openforis.idm.metamodel.BooleanAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class BooleanAttribute extends AtomicAttribute<BooleanAttributeDefinition, Boolean> {

	private static final long serialVersionUID = 1L;

	BooleanAttribute() {
	}
	
	public BooleanAttribute(BooleanAttributeDefinition definition) {
		super(definition, Boolean.class);
	}

}
