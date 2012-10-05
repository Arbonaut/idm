package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 */
class FileAttributeDefinitionPR extends AttributeDefinitionPR {

	public FileAttributeDefinitionPR() {
		super("file");
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		return schema.createFileAttributeDefinition(id);
	}
}