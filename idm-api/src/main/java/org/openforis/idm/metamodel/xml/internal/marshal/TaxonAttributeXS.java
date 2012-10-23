package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
		//attribute(QUALIFIABLE, defn.getQualifiers());
		attribute(TAXONOMY, defn.getTaxonomy());
		attribute(HIGHEST_RANK, defn.getHighestRank());
		List<String> qualifiers = defn.getQualifiers();
		String jointQualifiers = StringUtils.join(qualifiers, ",");
		attribute(QUALIFIERS, jointQualifiers);
	}
}
