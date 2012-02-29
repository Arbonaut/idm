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

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.xml.internal.RangeAttributeDefinitionTypeAdapter;
import org.openforis.idm.model.IntegerRange;
import org.openforis.idm.model.IntegerRangeAttribute;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.NumericRange;
import org.openforis.idm.model.RealRange;
import org.openforis.idm.model.RealRangeAttribute;
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
	@XmlJavaTypeAdapter(RangeAttributeDefinitionTypeAdapter.class)
	private Type type;

	@XmlElement(name = "precision", type = Precision.class)
	private List<Precision> precisionDefinitions;
	
	public Type getType() {
		return type == null ? Type.REAL : type;
	}

	public boolean isInteger() {
		return getType() == Type.INTEGER;
	}

	public boolean isReal() {
		return getType() == Type.REAL;
	}
	
	public List<Precision> getPrecisionDefinitions() {
		return CollectionUtil.unmodifiableList(precisionDefinitions);
	}
	

	@Override
	public Node<?> createNode() {
		Type effectiveType = getType();
		switch (effectiveType) {
		case INTEGER:
			return new IntegerRangeAttribute(this);
		case REAL:
			return new RealRangeAttribute(this);
		default:
			throw new UnsupportedOperationException("Unknown type");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public NumericRange<? extends Number> createValue(String string) {
		if ( StringUtils.isBlank(string) ) {
			return null;
		} else if (isInteger()) {
			return IntegerRange.parseIntegerRange(string);
		} else if (isReal()) {
			return RealRange.parseRealRange(string);
		}
		throw new RuntimeException("Invalid range type " + type);
	}
	
}
