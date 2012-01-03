/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.ModelExpression;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class ComparisonCheck extends Check  {

	@XmlTransient
	private enum OPERATION {
		LT("<"), LTE("<="), GT(">"), GTE(">="), EQ("=");

		private String xpathSymbol;

		private OPERATION(final String xpathSymbol) {
			this.xpathSymbol = xpathSymbol;
		}
	}

	@XmlAttribute(name = "lt")
	private String lessThanExpression;

	@XmlAttribute(name = "lte")
	private String lessThanOrEqualsExpression;

	@XmlAttribute(name = "gt")
	private String greaterThanExpression;

	@XmlAttribute(name = "gte")
	private String greaterThanOrEqualsExpression;

	@XmlAttribute(name = "eq")
	private String equalsExpression;

	public String getLessThanExpression() {
		return this.lessThanExpression;
	}

	public String getLessThanOrEqualsExpression() {
		return this.lessThanOrEqualsExpression;
	}

	public String getGreaterThanExpression() {
		return this.greaterThanExpression;
	}

	public String getGreaterThanOrEqualsExpression() {
		return this.greaterThanOrEqualsExpression;
	}

	public String getEqualsExpression() {
		return this.equalsExpression;
	}

	public boolean execute(Attribute<? extends AttributeDefinition, ?> attribute) {
		ExpressionBuilder expressionBuilder = this.new ExpressionBuilder(this);
		String expression = expressionBuilder.getExpression();
		ModelExpression modelExpression = new ModelExpression(expression);
		Boolean b = (Boolean) modelExpression.evaluate(attribute);
		return b;
	}

	private class ExpressionBuilder {

		private StringBuilder expression;
		private boolean firstOperation = true;

		private ExpressionBuilder(ComparisonCheck comparisonCheck) {
			this.expression = new StringBuilder();
			buildExpression(comparisonCheck);
		}

		private void buildExpression(ComparisonCheck c) {
			if (StringUtils.isNotBlank(c.getLessThanExpression())) {
				addOperation(OPERATION.LT, c.getLessThanExpression());
			}
			if (StringUtils.isNotBlank(c.getLessThanOrEqualsExpression())) {
				addOperation(OPERATION.LTE, c.getLessThanOrEqualsExpression());
			}
			if (StringUtils.isNotBlank(c.getGreaterThanExpression())) {
				addOperation(OPERATION.GT, c.getGreaterThanExpression());
			}
			if (StringUtils.isNotBlank(c.getGreaterThanOrEqualsExpression())) {
				addOperation(OPERATION.GTE, c.getGreaterThanOrEqualsExpression());
			}
			if (StringUtils.isNotBlank(c.getEqualsExpression())) {
				addOperation(OPERATION.EQ, c.getEqualsExpression());
			}
		}

		private void addOperation(OPERATION o, String value) {
			if (firstOperation) {
				firstOperation = true;
			} else {
				expression.append(" ");
				expression.append("and");
				expression.append(" ");
			}
			expression.append("$this");
			expression.append(" ");
			expression.append(o.xpathSymbol);
			expression.append(" ");
			expression.append(value);
		}

		private String getExpression() {
			return expression.toString();
		}
	}

}
