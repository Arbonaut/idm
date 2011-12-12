/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.ModelObjectDefinition;
import org.openforis.idm.metamodel.Schema;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "rootEntityDefinitions" })
public class SchemaImpl implements Schema {

	@XmlElement(name = "entity", type = EntityDefinitionImpl.class)
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

}
