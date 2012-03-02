/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.IdmInterpretationError;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.expression.CheckExpression;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;
import org.openforis.idm.model.state.NodeState;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class CustomCheck extends Check {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "expr")
	private String expression;

	public String getExpression() {
		return this.expression;
	}

	@Override
	public boolean evaluate(NodeState nodeState) {
		Attribute<?,?> node = (Attribute<?, ?>) nodeState.getNode();
		String expr = getExpression();
		try {
			SurveyContext recordContext = node.getRecord().getSurveyContext();
			ExpressionFactory expressionFactory = recordContext.getExpressionFactory();
			CheckExpression checkExpression = expressionFactory.createCheckExpression(expr);
			boolean result = checkExpression.evaluate(node.getParent(), node);
			return result;
		} catch (InvalidExpressionException e) {
			throw new IdmInterpretationError("Error evaluating custom check", e);
		}
	}
	
}
