package org.openforis.idm.model;

import org.openforis.idm.metamodel.TaxonAttributeDefinition;
import org.openforis.idm.validation.ValidationResults;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class TaxonAttribute extends Attribute<TaxonAttributeDefinition, TaxonOccurence> {

	public TaxonAttribute(TaxonAttributeDefinition definition) {
		super(definition);
	}

	@Override
	public TaxonOccurence createValue(String string) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEmpty() {
		TaxonOccurence taxonOccurence = getValue();
		return taxonOccurence.getTaxon() == null;
	}

	@Override
	protected boolean validateValue(ValidationResults results) {
		// currently not required.
		// in the future check if all values are set
		return true;
	}
}
