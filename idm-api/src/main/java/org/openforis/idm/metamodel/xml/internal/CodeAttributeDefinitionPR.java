package org.openforis.idm.metamodel.xml.internal;

import java.io.IOException;

import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
class CodeAttributeDefinitionPR extends AttributeDefinitionPR {

	public CodeAttributeDefinitionPR() {
		super("code");
	}
	@Override
	protected void onStartDefinition() throws XmlParseException, XmlPullParserException, IOException {
		Boolean key = getBooleanAttribute("key", false);
		String parent = getAttribute("parent", false);
		Boolean strict = getBooleanAttribute("strict", false);
		CodeAttributeDefinition defn = (CodeAttributeDefinition) getDefinition();
		defn.setKey(key == null ? false : key);
		defn.setParentExpression(parent);
		defn.setAllowUnlisted(strict == null ? false : strict);
	}
	
	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createCodeAttributeDefinition(id);
	}
}