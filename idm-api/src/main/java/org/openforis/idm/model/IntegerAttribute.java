package org.openforis.idm.model;

import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.Unit;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class IntegerAttribute extends NumberAttribute<Integer, IntegerValue> {

	private static final long serialVersionUID = 1L;

	public IntegerAttribute(NumberAttributeDefinition definition) {
		super(definition);
		if ( !definition.isInteger() ) {
			throw new IllegalArgumentException("Attempted to create IntegerAttribute with real NumberDefinition");
		}
	}
	
	@Override
	protected IntegerValue createValue(Integer value, Unit unit) {
		return new IntegerValue(value, unit);
	}
}
