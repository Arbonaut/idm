/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "type", "relevantExpression", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName", 
		"labels", "prompts", "descriptions", "attributeDefaults", "precisionDefinitions", "checks" })
public class NumberAttributeDefinition extends NumericAttributeDefinition {
}
