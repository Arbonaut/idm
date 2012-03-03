package org.openforis.idm.model;

import org.openforis.idm.metamodel.RangeAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class RealRangeAttribute extends NumericRangeAttribute<RealRange, Double> {

	private static final long serialVersionUID = 1L;

	RealRangeAttribute() {
	}
	
	public RealRangeAttribute(RangeAttributeDefinition definition) {
		super(definition, Double.class);
		if (!definition.isReal()) {
			throw new IllegalArgumentException("Attempted to create RealRangeAttribute with integer definition");
		}
	}

	@Override
	protected RealRange createRange(Double from, Double to) {
		return new RealRange(from, to);
	}

}
