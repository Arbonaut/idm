package org.openforis.idm.model;

import org.openforis.idm.metamodel.DateAttributeDefinition;

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
	
}
