package org.openforis.idm.model;

import org.openforis.idm.metamodel.NumberAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class IntegerAttribute extends NumberAttribute<Integer> {

	private static final long serialVersionUID = 1L;

	public IntegerAttribute(NumberAttributeDefinition definition) {
		super(definition, Integer.class);
		if ( !definition.isInteger() ) {
			throw new IllegalArgumentException("Attempted to create IntegerAttribute with real NumberDefinition");
		}
	}
	
}
