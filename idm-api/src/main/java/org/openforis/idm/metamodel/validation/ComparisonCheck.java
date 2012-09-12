/**
 * 
 */
package org.openforis.idm.metamodel.validation;

/*import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;*/
import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.IdmInterpretationError;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.expression.CheckExpression;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;
import org.simpleframework.xml.Transient;

/**
 * @author M. Togna
 * @author G. Miceli
 * @author K. Waga
 */
//@XmlAccessorType(XmlAccessType.FIELD)
public class ComparisonCheck extends Check<Attribute<?,?>> {

	private static final long serialVersionUID = 1L;

	@Transient
	private enum Operation {
		LT("<"), LTE("<="), GT(">"), GTE(">="), EQ("=");

		private String xpathOperator;

		private Operation(final String xpathOperator) {
			this.xpathOperator = xpathOperator;
		}
	}

	@Transient
	private String expression;

	@org.simpleframework.xml.Attribute(name = "lt", required = false)
	private String lessThanExpression;

	@org.simpleframework.xml.Attribute(name = "lte", required = false)
	private String lessThanOrEqualsExpression;

	@org.simpleframework.xml.Attribute(name = "gt", required = false)
	private String greaterThanExpression;

	@org.simpleframework.xml.Attribute(name = "gte", required = false)
	private String greaterThanOrEqualsExpression;

	@org.simpleframework.xml.Attribute(name = "eq", required = false)
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
	public ValidationResultFlag evaluate(Attribute<?, ?> node) {
		Record record = node .getRecord();
		SurveyContext recordContext = record.getSurveyContext();
		if (expression == null) {
			expression = buildExpression();
		}
		try {
			ExpressionFactory expressionFactory = recordContext.getExpressionFactory();
			CheckExpression checkExpr = expressionFactory.createCheckExpression(expression, true);
			boolean valid = checkExpr.evaluate(node.getParent(), node);
			return ValidationResultFlag.valueOf(valid, this.getFlag());
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
			expression.append("$this");
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
