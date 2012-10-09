package org.openforis.idm.metamodel.xml;


import java.io.IOException;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
class NumberAttributeDefinitionPR extends NumericAttributeDefinitionPR {

	public NumberAttributeDefinitionPR() {
		super("number");
	}

	@Override
	protected void onStartDefinition() throws XmlParseException, XmlPullParserException, IOException {
		super.onStartDefinition();
		Boolean key = getBooleanAttribute("key", false);
		NumberAttributeDefinition defn = (NumberAttributeDefinition) getDefinition();
		defn.setKey(key == null ? false : key);
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createNumberAttributeDefinition(id);
	}
}