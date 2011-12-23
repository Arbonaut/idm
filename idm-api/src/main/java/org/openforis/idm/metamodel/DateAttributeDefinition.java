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
@XmlType(name="", propOrder = {"name", "relevantExpression", "requiredExpression", "multiple", "minCount", "maxCount", "since", "deprecated",
		"labels", "prompts", "descriptions", "checks"})
public class DateAttributeDefinition extends AttributeDefinition {
}
