package org.openforis.idm.metamodel.xml.internal;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;

/**
 * @author G. Miceli
 */
class CoordinateAttributeDefinitionPR extends AttributeDefinitionPR {

	public CoordinateAttributeDefinitionPR() {
		super("coordinate");
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createCoordinateAttributeDefinition(id);
	}
}