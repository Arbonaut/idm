package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 */
class TaxonAttributeDefinitionPR extends AttributeDefinitionPR {

	public TaxonAttributeDefinitionPR() {
		super("taxon");
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		return schema.createTaxonAttributeDefinition(id);
	}
}