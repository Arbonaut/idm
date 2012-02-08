package org.openforis.idm.model;

import org.openforis.idm.metamodel.NumberAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class RealAttribute extends NumberAttribute<Double> {

	public RealAttribute(NumberAttributeDefinition definition) {
		super(definition);
		if (!definition.isReal()) {
			throw new IllegalArgumentException("Attempted to create RealAttribute with integer NumberDefinition");
		}
	}

	@Override
	public Double createValue(String string) {
		return Double.parseDouble(string);
	}

}
