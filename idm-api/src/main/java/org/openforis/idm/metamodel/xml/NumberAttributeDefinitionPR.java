package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 */
class NumberAttributeDefinitionPR extends AttributeDefinitionPR {

	public NumberAttributeDefinitionPR() {
		super("number");
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		return schema.createNumberAttributeDefinition(id);
	}
}