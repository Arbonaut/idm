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
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Time;
import org.openforis.idm.model.TimeAttribute;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "relevantExpression","required", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName", 
	"labels", "prompts", "descriptions", "attributeDefaults", "checks"})
public class TimeAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;

	static List<FieldDefinition> fieldsDefinitions = Collections.unmodifiableList(Arrays.asList(
			new FieldDefinition("hour", "h", Integer.class), 
			new FieldDefinition("minute", "m", Integer.class)
		));
	
	@Override
	public Node<?> createNode() {
		return new TimeAttribute(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Time createValue(String string) {
		if ( StringUtils.isBlank(string) ) {
			return null;
		} else {
			return Time.parseTime(string);
		}
	}
	
	@Override
	public List<FieldDefinition> getFieldsDefinitions() {
		return fieldsDefinitions;
	}
	
}
