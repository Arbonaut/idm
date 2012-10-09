package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.TextAttributeDefinition;
import org.openforis.idm.metamodel.TextAttributeDefinition.Type;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
class TextAttributeDefinitionPR extends AttributeDefinitionPR {

	public TextAttributeDefinitionPR() {
		super("text");
	}

	@Override
	protected void onStartDefinition() throws XmlParseException, XmlPullParserException, IOException {
		Boolean key = getBooleanAttribute("key", false);
		String typeStr = getAttribute("type", false); 
		TextAttributeDefinition defn = (TextAttributeDefinition) getDefinition();
		defn.setKey(key == null ? false : key);
		try {
			defn.setType(typeStr == null ? Type.SHORT : Type.valueOf(typeStr.toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new XmlParseException(getParser(), "invalid type "+typeStr);
		}
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createTextAttributeDefinition(id);
	}
}