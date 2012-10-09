package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;

/**
 * @author G. Miceli
 */
class RangeAttributeDefinitionPR extends NumericAttributeDefinitionPR {

	public RangeAttributeDefinitionPR() {
		super("range");
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createRangeAttributeDefinition(id);
	}
}