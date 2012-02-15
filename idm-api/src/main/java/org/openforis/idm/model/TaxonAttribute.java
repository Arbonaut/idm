package org.openforis.idm.model;

import org.openforis.idm.metamodel.TaxonAttributeDefinition;
import org.openforis.idm.validation.ValidationResults;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class TaxonAttribute extends Attribute<TaxonAttributeDefinition, Taxon> {

	public TaxonAttribute(TaxonAttributeDefinition definition) {
		super(definition);
	}

	@Override
	public Taxon createValue(String string) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean isEmpty() {
		//TODO
		// is null when pointer to taxon is null
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected boolean validateValue(ValidationResults results) {
		// currently not required.
		// in the future check if all values are set
		return true;
	}
}
