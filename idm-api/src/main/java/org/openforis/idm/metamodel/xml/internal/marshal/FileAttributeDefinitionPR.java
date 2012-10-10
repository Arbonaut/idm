package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.FILE;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;

/**
 * @author G. Miceli
 */
class FileAttributeDefinitionPR extends AttributeDefinitionPR {

	public FileAttributeDefinitionPR() {
		super(FILE);
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		Schema schema = getSchema();
		return schema.createFileAttributeDefinition(id);
	}
}