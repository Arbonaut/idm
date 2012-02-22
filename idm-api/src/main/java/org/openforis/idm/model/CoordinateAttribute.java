package org.openforis.idm.model;

import org.openforis.idm.metamodel.CoordinateAttributeDefinition;
import org.openforis.idm.metamodel.validation.CoordinateValidator;
import org.openforis.idm.metamodel.validation.ValidationResults;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class CoordinateAttribute extends Attribute<CoordinateAttributeDefinition, Coordinate> {

	public CoordinateAttribute(CoordinateAttributeDefinition definition) {
		super(definition, Long.class, Long.class, String.class);
	}

	@SuppressWarnings("unchecked")
	public Field<Long> getXField() {
		return (Field<Long>) getField(0);
	}

	@SuppressWarnings("unchecked")
	public Field<Long> getYField() {
		return (Field<Long>) getField(1);
	}
	
	@SuppressWarnings("unchecked")
	public Field<String> getSrsIdField() {
		return (Field<String>) getField(2);
	}

	@Override
	public Coordinate getValue() {
		Long x = getXField().getValue();
		Long y = getYField().getValue();
		String srsId = getSrsIdField().getValue();
		return new Coordinate(x, y, srsId);
	}

	@Override
	public void setValue(Coordinate coordinate) {
		Long x = coordinate.getX();
		Long y = coordinate.getY();
		String srsId = coordinate.getSrsId();
		getXField().setValue(x);
		getYField().setValue(y);
		getSrsIdField().setValue(srsId);
	}
	
	@Override
	protected boolean validateValue(ValidationResults results) {
		CoordinateValidator validator = new CoordinateValidator();
		boolean valid = validator.validate(this);
		results.addResult(this, validator, valid);
		return valid;
	}

}
