package org.openforis.idm.metamodel.xml.internal.unmarshal;

import java.io.IOException;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.TaxonAttributeDefinition;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.openforis.idm.model.species.Taxon.TaxonRank;
import org.xmlpull.v1.XmlPullParserException;
import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

/**
 * @author G. Miceli
 * @author S. Ricci
 */
class TaxonAttributeDefinitionPR extends AttributeDefinitionPR {

	public TaxonAttributeDefinitionPR() {
		super(TAXON);
	}

	@Override
	protected void onStartDefinition() throws XmlParseException, XmlPullParserException, IOException {
		TaxonAttributeDefinition defn = (TaxonAttributeDefinition) getDefinition();
		String taxonomy = getAttribute(TAXONOMY, true);
		String highestRankName = getAttribute(HIGHEST_RANK, false);
		String qualifiers = getAttribute(QUALIFIERS, false);
		defn.setTaxonomy(taxonomy);
		defn.setHighestTaxonRank(TaxonRank.fromName(highestRankName, true));
		defn.setQualifiers(qualifiers);
	}
	
	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createTaxonAttributeDefinition(id);
	}
}