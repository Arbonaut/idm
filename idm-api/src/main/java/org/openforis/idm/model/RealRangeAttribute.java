package org.openforis.idm.model;

import org.openforis.idm.metamodel.RangeAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class RealRangeAttribute extends NumericRangeAttribute<RealRange, Double> {

	public RealRangeAttribute(RangeAttributeDefinition definition) {
		super(definition, Double.class);
		if (!definition.isReal()) {
			throw new IllegalArgumentException("Attempted to create RealRangeAttribute with integer definition");
		}
	}

	@Override
	public RealRange createValue(String string) {
		return RealRange.parseRealRange(string);
	}

	@Override
	protected RealRange createRange(Double from, Double to) {
		return new RealRange(from, to);
	}

}
