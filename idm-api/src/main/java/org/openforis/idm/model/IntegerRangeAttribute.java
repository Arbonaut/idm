package org.openforis.idm.model;

import org.openforis.idm.metamodel.RangeAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class IntegerRangeAttribute extends NumericRangeAttribute<IntegerRange, Integer> {

	private static final long serialVersionUID = 1L;

	public IntegerRangeAttribute(RangeAttributeDefinition definition) {
		super(definition, Integer.class);
		if (!definition.isInteger()) {
			throw new IllegalArgumentException("Attempted to create IntegerRangeAttribute with real definition");
		}
	}

	@Override
	protected IntegerRange createRange(Integer from, Integer to) {
		return new IntegerRange(from, to);
	}

}
