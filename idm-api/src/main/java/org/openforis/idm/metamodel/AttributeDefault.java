/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.Serializable;

/*import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;*/
import org.simpleframework.xml.Order;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.Value;
import org.openforis.idm.model.expression.DefaultConditionExpression;
import org.openforis.idm.model.expression.DefaultValueExpression;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author K. Waga
 */
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "", propOrder = { "value", "expression", "condition" })
@Order(attributes = "", elements = { "value", "expression", "condition" })
public class AttributeDefault implements Serializable {

	private static final long serialVersionUID = 1L;

	@org.simpleframework.xml.Attribute(name = "value", required=false)
	private String value;

	@org.simpleframework.xml.Attribute(name = "expr", required=false)
	private String expression;

	@org.simpleframework.xml.Attribute(name = "if", required=false)
	private String condition;

	public String getValue() {
		return value;
	}
	
	public String getExpression() {
		return this.expression;
	}

	public String getCondition() {
		return this.condition;
	}
	
	@SuppressWarnings("unchecked")
	public <V extends Value> V evaluate(Attribute<? extends AttributeDefinition,V> attrib) throws InvalidExpressionException {
		Record record = attrib.getRecord();
		SurveyContext recordContext = record.getSurveyContext();
		ExpressionFactory expressionFactory = recordContext.getExpressionFactory();
		if ( StringUtils.isBlank(condition) || evaluateCondition(attrib, expressionFactory) ) {
			if (StringUtils.isBlank(value)) {
				return evaluateExpression(attrib, expressionFactory);
			} else {
				AttributeDefinition definition = attrib.getDefinition();
				return (V) definition.createValue(value);
			}
		} else {
			return null;
		}
	}

	private boolean evaluateCondition(Attribute<?,?> attrib, ExpressionFactory expressionFactory) throws InvalidExpressionException {
		DefaultConditionExpression expr = expressionFactory.createDefaultConditionExpression(condition);
		return expr.evaluate(attrib.getParent(), attrib);
	}

	@SuppressWarnings("unchecked")
	private <V extends Value> V evaluateExpression(Attribute<?, V> attrib, ExpressionFactory expressionFactory) throws InvalidExpressionException {
		DefaultValueExpression defaultValueExpression = expressionFactory.createDefaultValueExpression(expression);
		Object object = defaultValueExpression.evaluate(attrib.getParent(), attrib);
		return (V) object;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((expression == null) ? 0 : expression.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeDefault other = (AttributeDefault) obj;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
