package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 */
class DateAttributeDefinitionPR extends AttributeDefinitionPR {

	public DateAttributeDefinitionPR() {
		super("date");
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		return schema.createCoordinateAttributeDefinition(id);
	}
}