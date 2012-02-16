package org.openforis.idm.model;

import org.openforis.idm.metamodel.RangeAttributeDefinition;
import org.openforis.idm.metamodel.validation.RealRangeValidator;
import org.openforis.idm.metamodel.validation.ValidationResults;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class RealRangeAttribute extends Attribute<RangeAttributeDefinition, RealRange> {

	public RealRangeAttribute(RangeAttributeDefinition definition) {
		super(definition);
		if (!definition.isReal()) {
			throw new IllegalArgumentException("Attempted to create RealRangeAttribute with integer definition");
		}
	}

	@Override
	public RealRange createValue(String string) {
		return RealRange.parseRealRange(string);
	}

	@Override
	public boolean isEmpty() {
		RealRange r = getValue();
		return r == null || (r.getFrom() == null && r.getTo() == null);
	}

	@Override
	protected boolean validateValue(ValidationResults results) {
		RealRangeValidator validator = new RealRangeValidator();
		boolean valid = validator.validate(this);
		results.addResult(this, validator, valid);
		return valid;
	}
}
