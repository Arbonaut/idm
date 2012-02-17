package org.openforis.idm.model;

import org.openforis.idm.metamodel.TaxonAttributeDefinition;
import org.openforis.idm.metamodel.validation.ValidationResults;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class TaxonAttribute extends Attribute<TaxonAttributeDefinition, TaxonOccurrence> {

	public TaxonAttribute(TaxonAttributeDefinition definition) {
		super(definition);
	}

	@Override
	public TaxonOccurrence createValue(String string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEmpty() {
		TaxonOccurrence taxonOccurrence = getValue();
		return taxonOccurrence.getTaxon() == null;
	}

	@Override
	protected boolean validateValue(ValidationResults results) {
		// currently not required.
		// in the future check if all values are set
		return true;
	}
}
