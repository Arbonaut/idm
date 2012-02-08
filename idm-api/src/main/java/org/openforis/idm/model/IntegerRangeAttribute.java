package org.openforis.idm.model;

import org.openforis.idm.metamodel.RangeAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class IntegerRangeAttribute extends Attribute<RangeAttributeDefinition, IntegerRange> {

	public IntegerRangeAttribute(RangeAttributeDefinition definition) {
		super(definition);
		if (!definition.isReal()) {
			throw new IllegalArgumentException("Attempted to create IntegerRangeAttribute with real definition");
		}
	}

	@Override
	public IntegerRange createValue(String string) {
		return IntegerRange.parseIntegerRange(string);
	}
}
