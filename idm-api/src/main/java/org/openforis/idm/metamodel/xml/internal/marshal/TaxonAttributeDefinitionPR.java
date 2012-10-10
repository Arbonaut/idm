package org.openforis.idm.metamodel.xml.internal.marshal;

import java.io.IOException;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.TaxonAttributeDefinition;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;
import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

/**
 * @author G. Miceli
 */
class TaxonAttributeDefinitionPR extends AttributeDefinitionPR {

	public TaxonAttributeDefinitionPR() {
		super(TAXON);
	}

	@Override
	protected void onStartDefinition() throws XmlParseException, XmlPullParserException, IOException {
		TaxonAttributeDefinition defn = (TaxonAttributeDefinition) getDefinition();
		String taxonomy = getAttribute(TAXONOMY, true);
		String highestRank = getAttribute(HIGHEST_RANK, false);
		defn.setTaxonomy(taxonomy);
		defn.setHighestRank(highestRank);
	}
	
	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createTaxonAttributeDefinition(id);
	}
}