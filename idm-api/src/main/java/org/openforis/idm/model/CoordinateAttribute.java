package org.openforis.idm.model;

import org.openforis.idm.metamodel.CoordinateAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class CoordinateAttribute extends Attribute<CoordinateAttributeDefinition, Coordinate> {

	public CoordinateAttribute(CoordinateAttributeDefinition definition) {
		super(definition);
	}

	@Override
	public Coordinate createValue(String string) {
		return Coordinate.parseCoordinate(string);
	}
}
