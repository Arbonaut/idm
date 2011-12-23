/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;



/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "relevantExpression", "requiredExpression", "multiple", "minCount", "maxCount", "since", "deprecated", "labels", "prompts", "descriptions", "childDefinitions" })
public class EntityDefinition extends SchemaObjectDefinition implements SchemaObjectDefinitionContext  {

	@XmlElements({
		@XmlElement(name = "entity",     type = EntityDefinition.class), 
		@XmlElement(name = "number",     type = NumberAttributeDefinition.class),
		@XmlElement(name = "range",      type = RangeAttributeDefinition.class), 
		@XmlElement(name = "boolean",    type = BooleanAttributeDefinition.class),
		@XmlElement(name = "date",       type = DateAttributeDefinition.class), 
		@XmlElement(name = "time",       type = TimeAttributeDefinition.class),
		@XmlElement(name = "file",       type = FileAttributeDefinition.class), 
		@XmlElement(name = "taxon",      type = TaxonAttributeDefinition.class),
		@XmlElement(name = "coordinate", type = CoordinateAttributeDefinition.class), 
		@XmlElement(name = "code",       type = CodeAttributeDefinition.class),
		@XmlElement(name = "text",       type = TextAttributeDefinition.class) })
	private List<SchemaObjectDefinition> childDefinitions;

	public List<SchemaObjectDefinition> getChildDefinitions() {
		return Collections.unmodifiableList(childDefinitions);
	}
	
	public SchemaObjectDefinition getChildDefinition(String name) {
		if (childDefinitions != null) {
			for (SchemaObjectDefinition childDefinition : childDefinitions) {
				if (childDefinition.getName().equals(name)) {
					return childDefinition;
				}
			}
		}
		return null;
	}
}
