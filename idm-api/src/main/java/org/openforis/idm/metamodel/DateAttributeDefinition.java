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
import org.openforis.idm.model.Date;
import org.openforis.idm.model.DateAttribute;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Value;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "relevantExpression","required", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName",
		"labels", "prompts", "descriptions", "attributeDefaults", "checks"})
public class DateAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;

	static final List<FieldDefinition> fieldsDefinitions = Collections.unmodifiableList(Arrays.asList(
			new FieldDefinition("year", "y", "y", Integer.class),
			new FieldDefinition("month", "m", "m", Integer.class),
			new FieldDefinition("day", "d", "d", Integer.class)
		));
	
	@Override
	public Node<?> createNode() {
		return new DateAttribute(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Date createValue(String string) {
		if ( StringUtils.isBlank(string) ) {
			return null;
		} else {
			return Date.parseDate(string);
		}
	}
	
	@Override
	public List<FieldDefinition> getFieldDefinitions() {
		return fieldsDefinitions;
	}

	@Override
	public Class<? extends Value> getValueType() {
		return Date.class;
	}
}
