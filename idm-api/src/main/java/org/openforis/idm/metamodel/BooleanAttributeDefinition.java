/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.model.BooleanAttribute;
import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author S. Ricci
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "relevantExpression","required", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName", 
		"affirmativeOnly", "labels", "prompts", "descriptions", "attributeDefaults", "checks" })
public class BooleanAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;
	
	static final List<FieldDefinition> fieldsDefinitions = Collections.unmodifiableList(Arrays.asList(
		new FieldDefinition("value", "v", Boolean.class)
	));
	
	@XmlAttribute(name = "affirmativeOnly")
	private Boolean affirmativeOnly;

	public boolean isAffirmativeOnly() {
		return affirmativeOnly == null ? false : affirmativeOnly;
	}
	
	protected void setAffirmativeOnly(boolean affirmativeOnly) {
		this.affirmativeOnly = affirmativeOnly ? true : null;
	}

	@Override
	public Node<?> createNode() {
		return new BooleanAttribute(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Boolean createValue(String string) {
		if ( StringUtils.isBlank(string) ) {
			return null;
		} else {
			return Boolean.parseBoolean(string);
		}
	}
	
	@Override
	public List<FieldDefinition> getFieldsDefinitions() {
		return fieldsDefinitions;
	}
}
