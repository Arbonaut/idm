/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import org.openforis.idm.metamodel.IdmInterpretationError;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.expression.CheckExpression;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author K. Waga
 */

public class CustomCheck extends Check<Attribute<?,?>> {

	private static final long serialVersionUID = 1L;

	private String expression;

	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	@Override
	public ValidationResultFlag evaluate(Attribute<?,?> node) {
		String expr = getExpression();
		try {
			SurveyContext recordContext = node.getRecord().getSurveyContext();
			ExpressionFactory expressionFactory = recordContext.getExpressionFactory();
			CheckExpression checkExpression = expressionFactory.createCheckExpression(expr);
			boolean valid = checkExpression.evaluate(node.getParent(), node);
			return ValidationResultFlag.valueOf(valid, this.getFlag());
		} catch (InvalidExpressionException e) {
			throw new IdmInterpretationError("Error evaluating custom check", e);
		}
	}
	
}
