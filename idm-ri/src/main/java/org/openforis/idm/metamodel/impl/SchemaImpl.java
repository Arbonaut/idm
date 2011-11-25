/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.ModelObjectDefinition;
import org.openforis.idm.metamodel.Schema;

/**
 * @author Mino Togna
 * 
 */
public class SchemaImpl implements Schema {

	private List<EntityDefinition> rootEntityDefinitions;

	@Override
	public ModelObjectDefinition get(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EntityDefinition> getRootEntityDefinitions() {
		return this.rootEntityDefinitions;
	}

	@Override
	public void setRootEntityDefinitions(List<EntityDefinition> rootEntityDefinitions) {
		this.rootEntityDefinitions = rootEntityDefinitions;
	}

}
