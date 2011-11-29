/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.ModelObjectDefinition;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class EntityDefinitionImpl extends AbstractModelObjectDefinition implements EntityDefinition {

	@XmlElements(@XmlElement(name = "entity", type = EntityDefinitionImpl.class))
	private List<ModelObjectDefinition> childDefinitions;

	@Override
	public List<ModelObjectDefinition> getChildDefinitions() {
		return this.childDefinitions;
	}

	@Override
	public void setChildDefinitions(List<ModelObjectDefinition> childDefinitions) {
		this.childDefinitions = childDefinitions;
	}

}
