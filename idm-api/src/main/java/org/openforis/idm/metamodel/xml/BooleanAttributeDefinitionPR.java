package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.openforis.idm.metamodel.BooleanAttributeDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
class BooleanAttributeDefinitionPR extends AttributeDefinitionPR {

	public BooleanAttributeDefinitionPR() {
		super("boolean");
	}

	@Override
	protected void onStartDefinition() throws XmlParseException, XmlPullParserException, IOException {
		BooleanAttributeDefinition defn = (BooleanAttributeDefinition) getDefinition();
		Boolean affirmativeOnly = getBooleanAttribute("affirmativeOnly", false);
		defn.setAffirmativeOnly(affirmativeOnly == null ? false : affirmativeOnly);
	}
	
	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createBooleanAttributeDefinition(id);
	}
}