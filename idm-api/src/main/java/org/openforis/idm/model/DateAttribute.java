package org.openforis.idm.model;

import org.openforis.idm.metamodel.DateAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class DateAttribute extends Attribute<DateAttributeDefinition, Date> {

	private static final long serialVersionUID = 1L;

	DateAttribute() {
	}
	
	public DateAttribute(DateAttributeDefinition definition) {
		super(definition, Integer.class, Integer.class, Integer.class);
	}

	@SuppressWarnings("unchecked")
	public AttributeField<Integer> getYearField() {
		return (AttributeField<Integer>) getField(0);
	}

	@SuppressWarnings("unchecked")
	public AttributeField<Integer> getMonthField() {
		return (AttributeField<Integer>) getField(1);
	}

	@SuppressWarnings("unchecked")
	public AttributeField<Integer> getDayField() {
		return (AttributeField<Integer>) getField(2);
	}

	@Override
	public Date getValue() {
		Integer year = getYearField().getValue();
		Integer month = getMonthField().getValue();
		Integer day = getDayField().getValue();
		return new Date(year, month, day);
	}

	@Override
	public void setValue(Date date) {
		if ( date == null ) {
			clearValue();
		} else {
			Integer year = date.getYear();
			Integer month = date.getMonth();
			Integer day = date.getDay();
			getYearField().setValue(year);
			getMonthField().setValue(month);
			getDayField().setValue(day);
		}
	}

}
