package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 */
class RangeAttributeDefinitionPR extends AttributeDefinitionPR {

	public RangeAttributeDefinitionPR() {
		super("range");
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		return schema.createRangeAttributeDefinition(id);
	}
}