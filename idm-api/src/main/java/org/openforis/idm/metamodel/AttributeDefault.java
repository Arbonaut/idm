/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.expression.DefaultConditionExpression;
import org.openforis.idm.model.expression.DefaultValueExpression;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "value", "expression", "condition" })
public class AttributeDefault implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "value")
	private String value;

	@XmlAttribute(name = "expr")
	private String expression;

	@XmlAttribute(name = "if")
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
	public <V> V evaluate(Attribute<? extends AttributeDefinition,V> attrib) throws InvalidExpressionException {
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
	private <V> V evaluateExpression(Attribute<?, V> attrib, ExpressionFactory expressionFactory) throws InvalidExpressionException {
		DefaultValueExpression defaultValueExpression = expressionFactory.createDefaultValueExpression(expression);
		Object object = defaultValueExpression.evaluate(attrib.getParent(), attrib);
		return (V) object;
	}
}
