package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 */
class TextAttributeDefinitionPR extends AttributeDefinitionPR {

	public TextAttributeDefinitionPR() {
		super("text");
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		return schema.createTextAttributeDefinition(id);
	}
}