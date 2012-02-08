package org.openforis.idm.model;

import org.openforis.idm.metamodel.TaxonAttributeDefinition;

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
}
