package org.openforis.idm.model;

import org.openforis.idm.metamodel.RangeAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class IntegerRangeAttribute extends NumericRangeAttribute<IntegerRange, Integer> {

	public IntegerRangeAttribute(RangeAttributeDefinition definition) {
		super(definition, Integer.class);
		if (!definition.isInteger()) {
			throw new IllegalArgumentException("Attempted to create IntegerRangeAttribute with real definition");
		}
	}

	@Override
	public IntegerRange createValue(String string) {
		return IntegerRange.parseIntegerRange(string);
	}

	@Override
	protected IntegerRange createRange(Integer from, Integer to) {
		return new IntegerRange(from, to);
	}

}
