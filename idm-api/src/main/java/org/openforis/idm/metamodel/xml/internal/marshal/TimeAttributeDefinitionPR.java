package org.openforis.idm.metamodel.xml.internal.marshal;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;

/**
 * @author G. Miceli
 */
class TimeAttributeDefinitionPR extends AttributeDefinitionPR {

	public TimeAttributeDefinitionPR() {
		super("time");
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createTimeAttributeDefinition(id);
	}
}