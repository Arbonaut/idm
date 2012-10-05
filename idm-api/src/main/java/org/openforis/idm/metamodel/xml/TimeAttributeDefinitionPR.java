package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 */
class TimeAttributeDefinitionPR extends AttributeDefinitionPR {

	public TimeAttributeDefinitionPR() {
		super("time");
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		return schema.createTimeAttributeDefinition(id);
	}
}