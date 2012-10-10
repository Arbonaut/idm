package org.openforis.idm.metamodel.xml.internal.marshal;

import java.io.IOException;

import org.openforis.idm.metamodel.BooleanAttributeDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;
import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

/**
 * @author G. Miceli
 */
class BooleanAttributeDefinitionPR extends AttributeDefinitionPR {

	public BooleanAttributeDefinitionPR() {
		super(BOOLEAN);
	}

	@Override
	protected void onStartDefinition() throws XmlParseException, XmlPullParserException, IOException {
		BooleanAttributeDefinition defn = (BooleanAttributeDefinition) getDefinition();
		Boolean affirmativeOnly = getBooleanAttribute(AFFIRMATIVE_ONLY, false);
		defn.setAffirmativeOnly(affirmativeOnly == null ? false : affirmativeOnly);
	}
	
	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createBooleanAttributeDefinition(id);
	}
}