/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.xml.internal.TextAttributeDefinitionTypeAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "type", "relevantExpression", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName",
	"labels", "prompts", "descriptions", "attributeDefaults", "checks"})
public class TextAttributeDefinition extends AttributeDefinition {

	public enum Type {
		SHORT, MEMO
	}

	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(TextAttributeDefinitionTypeAdapter.class)
	private Type type;

	public Type getType() {
		return this.type;
	}
}
