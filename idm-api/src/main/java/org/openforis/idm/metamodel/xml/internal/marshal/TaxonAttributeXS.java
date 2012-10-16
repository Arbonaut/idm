package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;

import org.openforis.idm.metamodel.TaxonAttributeDefinition;

/**
 * 
 * @author G. Miceli
 *
 */
class TaxonAttributeXS extends AttributeDefinitionXS<TaxonAttributeDefinition> {

	TaxonAttributeXS() {
		super(TAXON);
	}
	
	@Override
	protected void attributes(TaxonAttributeDefinition defn) throws IOException {
		super.attributes(defn);
		attribute(QUALIFIABLE, defn.getQualifiers());
		attribute(TAXONOMY, defn.getTaxonomy());
		attribute(HIGHEST_RANK, defn.getHighestRank());
	}
}
