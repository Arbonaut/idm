/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.xml.internal.NumericAttributeDefinitionTypeAdapter;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"name", "relevantExpression","required", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName",
		"type", "labels", "prompts", "descriptions", "attributeDefaults", "precisionDefinitions", "checks" })
public class RangeAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;
	
	public enum Type {
		INTEGER, REAL
	}
	
	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(NumericAttributeDefinitionTypeAdapter.class)
	private Type type;

	@XmlElement(name = "precision", type = Precision.class)
	private List<Precision> precisionDefinitions;
	
	public Type getType() {
		return this.type;
	}

	public boolean isInteger() {
		return type == Type.INTEGER;
	}

	public boolean isReal() {
		return type == Type.REAL;
	}
	
	public List<Precision> getPrecisionDefinitions() {
		return CollectionUtil.unmodifiableList(precisionDefinitions);
	}
}
