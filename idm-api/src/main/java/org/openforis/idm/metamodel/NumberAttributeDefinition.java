/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.model.IntegerAttribute;
import org.openforis.idm.model.IntegerValue;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.NumberValue;
import org.openforis.idm.model.RealAttribute;
import org.openforis.idm.model.RealValue;
import org.openforis.idm.model.Value;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {"id", "name", "type", "key", "relevantExpression","required", "requiredExpression", "multiple", "minCount", "maxCount", "sinceVersionName", "deprecatedVersionName", 
		"labels", "prompts", "descriptions", "attributeDefaults", "precisionDefinitions", "checks" })
public class NumberAttributeDefinition extends NumericAttributeDefinition implements KeyAttributeDefinition {

	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "key")
	private Boolean key;

	@Override
	public boolean isKey() {
		return this.key == null ? false : key;
	}

	@Override
	public Node<?> createNode() {
		Type effectiveType = getType();
		switch (effectiveType) {
		case INTEGER:
			return new IntegerAttribute(this);
		case REAL:
			return new RealAttribute(this);
		default:
			throw new UnsupportedOperationException("Unknown type");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public NumberValue<? extends Number> createValue(String string) {
		if ( StringUtils.isBlank(string) ) {
			return null;
		} 
		Unit unit = getDefaultUnit();
		if(isInteger()){
			return new IntegerValue(Integer.valueOf(string), unit);
		} else if(isReal()) {
			return new RealValue(Double.valueOf(string), unit);
		}
		throw new RuntimeException("Invalid type " + type);
	}
	
	@Override
	public List<FieldDefinition<?>> getFieldDefinitions() {
		List<FieldDefinition<?>> result = new ArrayList<FieldDefinition<?>>(2);
		Type type = getType();
		switch (type) {
		case INTEGER:
			result.add(new FieldDefinition<Integer>("value", "v", null, Integer.class, this));
			break;
		case REAL:
			result.add(new FieldDefinition<Double>("value", "v", null, Double.class, this));
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
			return IntegerValue.class;
		case REAL:
			return RealValue.class;
		default:
			throw new UnsupportedOperationException("Unknown type");
		}
	}
}
