package org.openforis.idm.metamodel.xml.internal.unmarshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.CODE;
import static org.openforis.idm.metamodel.xml.IdmlConstants.KEY;
import static org.openforis.idm.metamodel.xml.IdmlConstants.LIST;
import static org.openforis.idm.metamodel.xml.IdmlConstants.PARENT;
import static org.openforis.idm.metamodel.xml.IdmlConstants.STRICT;

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
		super(CODE);
	}
	@Override
	protected void onStartDefinition() throws XmlParseException, XmlPullParserException, IOException {
		Boolean key = getBooleanAttribute(KEY, false);
		String parent = getAttribute(PARENT, false);
		Boolean strict = getBooleanAttribute(STRICT, false);
		String listName = getAttribute(LIST, true);
		CodeAttributeDefinition defn = (CodeAttributeDefinition) getDefinition();
		defn.setKey(key == null ? false : key);
		defn.setParentExpression(parent);
		defn.setAllowUnlisted(strict == null ? false : ! strict);
		defn.setListName(listName);
	}
	
	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createCodeAttributeDefinition(id);
	}
}