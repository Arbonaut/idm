/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.model.Coordinate;
import org.openforis.idm.model.CoordinateAttribute;
import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "relevantExpression","required", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName", 
		"labels", "prompts", "descriptions", "attributeDefaults", "checks" })
public class CoordinateAttributeDefinition extends AttributeDefinition  {

	private static final long serialVersionUID = 1L;

	@Override
	public Node<?> createNode() {
		return new CoordinateAttribute(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Coordinate createValue(String string) {
		return Coordinate.parseCoordinate(string);
	}
	
}
