package org.openforis.idm.model;

import org.openforis.idm.metamodel.RangeAttributeDefinition;
import org.openforis.idm.validation.IntegerRangeValidator;
import org.openforis.idm.validation.ValidationResults;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class IntegerRangeAttribute extends Attribute<RangeAttributeDefinition, IntegerRange> {

	public IntegerRangeAttribute(RangeAttributeDefinition definition) {
		super(definition);
		if (!definition.isReal()) {
			throw new IllegalArgumentException("Attempted to create IntegerRangeAttribute with real definition");
		}
	}

	@Override
	public IntegerRange createValue(String string) {
		return IntegerRange.parseIntegerRange(string);
	}

	@Override
	public boolean isEmpty() {
		IntegerRange v = getValue();
		return v == null || (v.getFrom() == null && v.getTo() == null);
	}

	@Override
	protected boolean validateValue(ValidationResults results) {
		IntegerRangeValidator validator = new IntegerRangeValidator();
		boolean valid = validator.validate(this);
		results.addResult(this, validator, valid);
		return valid;
	}

}
