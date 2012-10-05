package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 */
class BooleanAttributeDefinitionPR extends AttributeDefinitionPR {

	public BooleanAttributeDefinitionPR() {
		super("boolean");
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		return schema.createBooleanAttributeDefinition(id);
	}
}