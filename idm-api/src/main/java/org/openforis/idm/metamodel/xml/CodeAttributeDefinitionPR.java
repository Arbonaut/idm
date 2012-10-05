package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 */
class CodeAttributeDefinitionPR extends AttributeDefinitionPR {

	public CodeAttributeDefinitionPR() {
		super("code");
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		return schema.createCodeAttributeDefinition(id);
	}
}