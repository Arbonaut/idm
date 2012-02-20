package org.openforis.idm.model;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.CoordinateAttributeDefinition;
import org.openforis.idm.metamodel.validation.CoordinateValidator;
import org.openforis.idm.metamodel.validation.ValidationResults;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class CoordinateAttribute extends Attribute<CoordinateAttributeDefinition, Coordinate> {

	public CoordinateAttribute(CoordinateAttributeDefinition definition) {
		super(definition, 3);
	}

	@Override
	public Coordinate createValue(String string) {
		return Coordinate.parseCoordinate(string);
	}

	@Override
	protected boolean validateValue(ValidationResults results) {
		CoordinateValidator validator = new CoordinateValidator();
		boolean valid = validator.validate(this);
		results.addResult(this, validator, valid);
		return valid;
	}

}
