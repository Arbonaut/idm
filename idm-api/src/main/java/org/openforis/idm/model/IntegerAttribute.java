package org.openforis.idm.model;

import org.openforis.idm.metamodel.NumberAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class IntegerAttribute extends NumberAttribute<Integer> {

	public IntegerAttribute(NumberAttributeDefinition definition) {
		super(definition, Integer.class);
		if ( !definition.isInteger() ) {
			throw new IllegalArgumentException("Attempted to create IntegerAttribute with real NumberDefinition");
		}
	}
	
	@Override
	public Integer createValue(String string) {
		return Integer.valueOf(string);
	}
}
