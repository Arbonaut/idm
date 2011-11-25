/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.ModelObjectDefinition;

/**
 * @author Mino Togna
 * 
 */
public class EntityDefinitionImpl extends AbstractModelObjectDefinition implements EntityDefinition {

	private List<ModelObjectDefinition> childDefinitions;

	public List<ModelObjectDefinition> getChildDefinitions() {
		return childDefinitions;
	}

	public void setChildDefinitions(List<ModelObjectDefinition> childDefinitions) {
		this.childDefinitions = childDefinitions;
	}

}
