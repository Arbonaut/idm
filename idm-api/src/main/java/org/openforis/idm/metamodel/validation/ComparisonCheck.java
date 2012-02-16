/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.IdmInterpretationError;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.RecordContext;
import org.openforis.idm.model.expression.CheckExpression;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;

/**
 * @author M. Togna
 * @author G. Miceli
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class ComparisonCheck extends Check {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private enum Operation {
		LT("<"), LTE("<="), GT(">"), GTE(">="), EQ("=");

		private String xpathOperator;

		private Operation(final String xpathOperator) {
			this.xpathOperator = xpathOperator;
		}
	}

	@XmlTransient
	private String expression;

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
			
	public void setLessThanExpression(String lessThanExpression) {
		this.lessThanExpression = lessThanExpression;
	}

	public void setLessThanOrEqualsExpression(String lessThanOrEqualsExpression) {
		this.lessThanOrEqualsExpression = lessThanOrEqualsExpression;
	}

	public void setGreaterThanExpression(String greaterThanExpression) {
		this.greaterThanExpression = greaterThanExpression;
	}

	public void setGreaterThanOrEqualsExpression(String greaterThanOrEqualsExpression) {
		this.greaterThanOrEqualsExpression = greaterThanOrEqualsExpression;
	}

	public void setEqualsExpression(String equalsExpression) {
		this.equalsExpression = equalsExpression;
	}

	private String buildExpression() {
		ExpressionBuilder expressionBuilder = new ExpressionBuilder();

		if (StringUtils.isNotBlank(greaterThanExpression)) {
			expressionBuilder.addOperation(Operation.GT, greaterThanExpression);
		}
		if (StringUtils.isNotBlank(greaterThanOrEqualsExpression)) {
			expressionBuilder.addOperation(Operation.GTE, greaterThanOrEqualsExpression);
		}
		if (StringUtils.isNotBlank(lessThanExpression)) {
			expressionBuilder.addOperation(Operation.LT, lessThanExpression);
		}
		if (StringUtils.isNotBlank(lessThanOrEqualsExpression)) {
			expressionBuilder.addOperation(Operation.LTE, lessThanOrEqualsExpression);
		}
		if (StringUtils.isNotBlank(equalsExpression)) {
			expressionBuilder.addOperation(Operation.EQ, equalsExpression);
		}
		
		return expressionBuilder.getExpression();
	}

	@Override
	public boolean validate(Attribute<?, ?> node) {
		Record record = node.getRecord();
		RecordContext recordContext = record.getContext();
		if (expression == null) {
			expression = buildExpression();
		}
		try {
			ExpressionFactory expressionFactory = recordContext.getExpressionFactory();
			CheckExpression checkExpr = expressionFactory.createCheckExpression(expression);
			return checkExpr.evaluate(node.getParent(), node);
		} catch (InvalidExpressionException e) {
			throw new IdmInterpretationError("Error evaluating comparison check", e);
		}
	}

	private class ExpressionBuilder {

		private StringBuilder expression;
		private boolean firstOperation = true;

		ExpressionBuilder() {
			this.expression = new StringBuilder();
		}

		void addOperation(Operation o, String value) {
			if (firstOperation) {
				firstOperation = Boolean.FALSE;
			} else {
				expression.append(" ");
				expression.append("and");
				expression.append(" ");
			}
			expression.append("number($this)");
			expression.append(" ");
			expression.append(o.xpathOperator);
			expression.append(" ");
			expression.append(value);
		}

		String getExpression() {
			return expression.toString();
		}
	}

}
