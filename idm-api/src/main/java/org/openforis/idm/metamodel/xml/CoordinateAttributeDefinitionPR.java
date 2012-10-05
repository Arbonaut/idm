package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 */
class CoordinateAttributeDefinitionPR extends AttributeDefinitionPR {

	public CoordinateAttributeDefinitionPR() {
		super("coordinate");
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		return schema.createCoordinateAttributeDefinition(id);
	}
}