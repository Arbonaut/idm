package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;

/**
 * @author G. Miceli
 */
class DateAttributeDefinitionPR extends AttributeDefinitionPR {

	public DateAttributeDefinitionPR() {
		super("date");
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createCoordinateAttributeDefinition(id);
	}
}