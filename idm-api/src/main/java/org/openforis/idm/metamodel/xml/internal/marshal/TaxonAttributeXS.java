package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.TaxonAttributeDefinition;
import org.openforis.idm.model.species.Taxon.TaxonRank;

/**
 * 
 * @author G. Miceli
 * @author S. Ricci
 *
 */
class TaxonAttributeXS extends AttributeDefinitionXS<TaxonAttributeDefinition> {

	TaxonAttributeXS() {
		super(TAXON);
	}
	
	@Override
	protected void attributes(TaxonAttributeDefinition defn) throws IOException {
		super.attributes(defn);
		//attribute(QUALIFIABLE, defn.getQualifiers());
		attribute(TAXONOMY, defn.getTaxonomy());
		TaxonRank highestRank = defn.getHighestTaxonRank();
		attribute(HIGHEST_RANK, highestRank == null ? null: highestRank.getName());
		List<String> qualifiers = defn.getQualifiers();
		String jointQualifiers = StringUtils.join(qualifiers, ",");
		attribute(QUALIFIERS, jointQualifiers);
	}
}
