/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "maxSize", "extensions", "relevantExpression", "requiredExpression", "multiple", "minCount", "maxCount", 
		"since", "deprecated", "labels", "prompts", "descriptions", "attributeDefaults", "checks" })
public class FileAttributeDefinition extends AttributeDefinition {

	@XmlAttribute(name = "maxSize")
	private Integer maxSize;

	@XmlAttribute(name = "extensions")
	private List<String> extensions;

	public Integer getMaxSize() {
		return this.maxSize;
	}

	public List<String> getExtensions() {
		return Collections.unmodifiableList(this.extensions);
	}
}
