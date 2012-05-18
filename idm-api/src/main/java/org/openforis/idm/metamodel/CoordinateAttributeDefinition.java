/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;
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

	static final List<FieldDefinition> fieldsDefinitions = Collections.unmodifiableList(Arrays.asList(
		new FieldDefinition("x", "x", Double.class),
		new FieldDefinition("y", "y", Double.class),
		new FieldDefinition("srs_id", "srs", String.class)
	));
	
	@Override
	public Node<?> createNode() {
		return new CoordinateAttribute(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Coordinate createValue(String string) {
		if ( StringUtils.isBlank(string) ) {
			return null;
		} else {
			return Coordinate.parseCoordinate(string);
		}
	}
	
	@Override
	public List<FieldDefinition> getFieldDefinitions() {
		return fieldsDefinitions;
	}
	
}
