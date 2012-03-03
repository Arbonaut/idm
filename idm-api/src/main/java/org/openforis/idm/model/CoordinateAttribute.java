package org.openforis.idm.model;

import org.openforis.idm.metamodel.CoordinateAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class CoordinateAttribute extends Attribute<CoordinateAttributeDefinition, Coordinate> {

	private static final long serialVersionUID = 1L;

	CoordinateAttribute() {
	}
	
	public CoordinateAttribute(CoordinateAttributeDefinition definition) {
		super(definition, Double.class, Double.class, String.class);
	}

	@SuppressWarnings("unchecked")
	public AttributeField<Double> getXField() {
		return (AttributeField<Double>) getField(0);
	}

	@SuppressWarnings("unchecked")
	public AttributeField<Double> getYField() {
		return (AttributeField<Double>) getField(1);
	}
	
	@SuppressWarnings("unchecked")
	public AttributeField<String> getSrsIdField() {
		return (AttributeField<String>) getField(2);
	}

	@Override
	public Coordinate getValue() {
		Double x = getXField().getValue();
		Double y = getYField().getValue();
		String srsId = getSrsIdField().getValue();
		return new Coordinate(x, y, srsId);
	}

	@Override
	public void setValue(Coordinate coordinate) {
		if ( coordinate == null ) {
			clearValue();
		} else {
			Double x = coordinate.getX();
			Double y = coordinate.getY();
			String srsId = coordinate.getSrsId();
			getXField().setValue(x);
			getYField().setValue(y);
			getSrsIdField().setValue(srsId);
		}
	}
	
}
