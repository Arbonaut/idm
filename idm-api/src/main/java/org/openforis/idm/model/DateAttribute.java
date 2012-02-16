package org.openforis.idm.model;

import org.openforis.idm.metamodel.DateAttributeDefinition;
import org.openforis.idm.metamodel.validation.DateValidator;
import org.openforis.idm.metamodel.validation.ValidationResults;

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
		boolean result = validator.validate(this);
		results.addResult(this, validator, result);
		return result;
	}
	
	@Override
	public boolean isEmpty() {
		Date d = getValue();
		return d == null || (d.getYear() == null && d.getMonth() == null || d.getDay() == null);
	}
}
