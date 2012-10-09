package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.TaxonAttributeDefinition;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
class TaxonAttributeDefinitionPR extends AttributeDefinitionPR {

	public TaxonAttributeDefinitionPR() {
		super("taxon");
	}

	@Override
	protected void onStartDefinition() throws XmlParseException, XmlPullParserException, IOException {
		TaxonAttributeDefinition defn = (TaxonAttributeDefinition) getDefinition();
		String taxonomy = getAttribute("taxonomy", true);
		String highestRank = getAttribute("highestRank", false);
		defn.setTaxonomy(taxonomy);
		defn.setHighestRank(highestRank);
	}
	
	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createTaxonAttributeDefinition(id);
	}
}