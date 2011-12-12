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

	@XmlElements({ @XmlElement(name = "entity", type = EntityDefinitionImpl.class), @XmlElement(name = "number", type = NumberAttributeDefinitionImpl.class),
			@XmlElement(name = "range", type = NumericRangeAttributeDefinitionImpl.class), @XmlElement(name = "boolean", type = BooleanAttributeDefinitionImpl.class),
			@XmlElement(name = "date", type = DateAttributeDefinitionImpl.class), @XmlElement(name = "time", type = TimeAttributeDefinitionImpl.class),
			@XmlElement(name = "file", type = FileAttributeDefinitionImpl.class), @XmlElement(name = "taxon", type = TaxonAttributeDefinitionImpl.class),
			@XmlElement(name = "coordinate", type = CoordinateAttributeDefinitionImpl.class), @XmlElement(name = "code", type = CodeAttributeDefinitionImpl.class),
			@XmlElement(name = "text", type = TextAttributeDefinitionImpl.class) })
	private List<ModelObjectDefinition> childDefinitions;

	@Override
	public List<ModelObjectDefinition> getChildDefinitions() {
		return this.childDefinitions;
	}

	@Override
	public String toString() {
		return "Entity " + this.getName();
	}

	public ModelObjectDefinition getChildDefinition(String name) {
		if (this.childDefinitions != null) {
			for (ModelObjectDefinition childDefinition : this.childDefinitions) {
				if (childDefinition.getName().equals(name)) {
					return childDefinition;
				}
			}
		}
		return null;
	}

}
