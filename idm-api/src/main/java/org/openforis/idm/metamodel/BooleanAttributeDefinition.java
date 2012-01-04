/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "relevantExpression", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName", 
		"affirmativeOnly", "labels", "prompts", "descriptions", "attributeDefaults", "checks" })
public class BooleanAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "affirmativeOnly")
	private Boolean affirmativeOnly;

	public boolean isAffirmativeOnly() {
		return affirmativeOnly == null ? false : affirmativeOnly;
	}
	
	protected void setAffirmativeOnly(boolean affirmativeOnly) {
		this.affirmativeOnly = affirmativeOnly ? true : null;
	}
}
