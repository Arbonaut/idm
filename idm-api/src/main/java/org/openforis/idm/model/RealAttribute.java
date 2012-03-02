package org.openforis.idm.model;

import org.openforis.idm.metamodel.NumberAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class RealAttribute extends NumberAttribute<Double> {

	private static final long serialVersionUID = 1L;

	public RealAttribute() {
	}
	
	public RealAttribute(NumberAttributeDefinition definition) {
		super(definition, Double.class);
		if (!definition.isReal()) {
			throw new IllegalArgumentException("Attempted to create RealAttribute with integer NumberDefinition");
		}
	}

}
