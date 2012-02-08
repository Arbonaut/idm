package org.openforis.idm.model;

import org.openforis.idm.metamodel.RangeAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class RealRangeAttribute extends Attribute<RangeAttributeDefinition, RealRange> {

	public RealRangeAttribute(RangeAttributeDefinition definition) {
		super(definition);
		if ( !definition.isReal() ) {
			throw new IllegalArgumentException("Attempted to create RealRangeAttribute with integer definition");
		}
	}

	@Override
	public RealRange createValue(String string) {
		return RealRange.parseRealRange(string);
	}
}
