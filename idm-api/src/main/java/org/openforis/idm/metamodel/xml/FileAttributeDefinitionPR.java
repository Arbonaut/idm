package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;

/**
 * @author G. Miceli
 */
class FileAttributeDefinitionPR extends AttributeDefinitionPR {

	public FileAttributeDefinitionPR() {
		super("file");
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createFileAttributeDefinition(id);
	}
}