/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.model.IntegerRange;
import org.openforis.idm.model.IntegerRangeAttribute;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.NumericRange;
import org.openforis.idm.model.RealRange;
import org.openforis.idm.model.RealRangeAttribute;
import org.openforis.idm.model.Value;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"id", "name", "relevantExpression","required", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName",
		"type", "labels", "prompts", "descriptions", "attributeDefaults", "precisionDefinitions", "checks" })
public class RangeAttributeDefinition extends NumericAttributeDefinition {

	private static final long serialVersionUID = 1L;
	
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
		} 
		Unit unit = getDefaultUnit();
		if (isInteger()) {
			return IntegerRange.parseIntegerRange(string, unit);
		} else if (isReal()) {
			return RealRange.parseRealRange(string, unit);
		}
		throw new RuntimeException("Invalid range type " + type);
	}
	
	@Override
	public List<FieldDefinition<?>> getFieldDefinitions() {
		List<FieldDefinition<?>> result = new ArrayList<FieldDefinition<?>>(2);
		Type type = getType();
		switch (type) {
		case INTEGER:
			result.add(new FieldDefinition<Integer>("from", "f", "from", Integer.class, this));
			result.add(new FieldDefinition<Integer>("to", "t", "to", Integer.class, this));
			break;
		case REAL:
			result.add(new FieldDefinition<Double>("from", "f", "from", Double.class, this));
			result.add(new FieldDefinition<Double>("to", "t", "to", Double.class, this));
			break;
		default:
			throw new UnsupportedOperationException("Unknown type");
		}
		result.add(new FieldDefinition<String>("unit", "u", "unit", String.class, this));
		return Collections.unmodifiableList(result);
	}
	
	@Override
	public Class<? extends Value> getValueType() {
		Type type = getType();
		switch (type) {
		case INTEGER:
			return IntegerRange.class;
		case REAL:
			return RealRange.class;
		default:
			throw new UnsupportedOperationException("Unknown type");
		}
	}
}
