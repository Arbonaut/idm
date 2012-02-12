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
import org.openforis.idm.geotools.IdmInterpretationError;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.RecordContext;
import org.openforis.idm.model.expression.CheckExpression;
import org.openforis.idm.model.expression.InvalidPathException;

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

		private String xpathSymbol;

		private Operation(final String xpathSymbol) {
			this.xpathSymbol = xpathSymbol;
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
	
	public String getExpression() {
		if (expression == null) {
			ExpressionBuilder expressionBuilder = new ExpressionBuilder();
			expression = expressionBuilder.getExpression();
		}
		return expression;
	}

	@Override
	public boolean validate(Attribute<?, ?> node) {
		RecordContext recordContext = node.getRecord().getContext();
		String expr = getExpression();
		try {
			CheckExpression checkExpr = recordContext.getExpressionFactory().createCheckExpression(expr);
			return checkExpr.evaluate(node.getParent());
		} catch (InvalidPathException e) {
			throw new IdmInterpretationError("Error evaluating comparison check", e);
		}
	}

	private class ExpressionBuilder {

		private StringBuilder expression;
		private boolean firstOperation = true;

		private ExpressionBuilder() {
			this.expression = new StringBuilder();
			buildExpression();
		}

		private void buildExpression() {
			if (StringUtils.isNotBlank(getGreaterThanExpression())) {
				addOperation(Operation.GT, getGreaterThanExpression());
			}
			if (StringUtils.isNotBlank(getGreaterThanOrEqualsExpression())) {
				addOperation(Operation.GTE, getGreaterThanOrEqualsExpression());
			}
			if (StringUtils.isNotBlank(getLessThanExpression())) {
				addOperation(Operation.LT, getLessThanExpression());
			}
			if (StringUtils.isNotBlank(getLessThanOrEqualsExpression())) {
				addOperation(Operation.LTE, getLessThanOrEqualsExpression());
			}
			if (StringUtils.isNotBlank(getEqualsExpression())) {
				addOperation(Operation.EQ, getEqualsExpression());
			}
		}

		private void addOperation(Operation o, String value) {
			if (firstOperation) {
				firstOperation = Boolean.FALSE;
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
