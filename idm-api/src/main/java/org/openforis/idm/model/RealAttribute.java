package org.openforis.idm.model;

import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.Unit;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class RealAttribute extends NumberAttribute<Double, RealValue> {

	private static final long serialVersionUID = 1L;

	public RealAttribute(NumberAttributeDefinition definition) {
		super(definition);
		if (!definition.isReal()) {
			throw new IllegalArgumentException("Attempted to create RealAttribute with integer NumberDefinition");
		}
	}

	@Override
	protected RealValue createValue(Double value, Unit unit) {
		return new RealValue(value, unit);
	}
}
