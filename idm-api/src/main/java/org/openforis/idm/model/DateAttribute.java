package org.openforis.idm.model;

import org.openforis.idm.metamodel.DateAttributeDefinition;
import org.openforis.idm.validation.DateValidator;
import org.openforis.idm.validation.ValidationResults;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class DateAttribute extends Attribute<DateAttributeDefinition, Date> {

	public DateAttribute(DateAttributeDefinition definition) {
		super(definition);
	}

	@Override
	public Date createValue(String string) {
		return Date.parseDate(string);
	}
	
	@Override
	protected boolean validateValue(ValidationResults results) {
		DateValidator validator = new DateValidator();
		return validator.validate(this);
	}
}
