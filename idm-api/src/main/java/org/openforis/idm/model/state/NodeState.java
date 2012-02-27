/**
 * 
 */
package org.openforis.idm.model.state;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.IdmInterpretationError;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.validation.ValidationResults;
import org.openforis.idm.metamodel.validation.Validator;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.RecordContext;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;
import org.openforis.idm.model.expression.RelevanceExpression;
import org.openforis.idm.model.expression.RequiredExpression;

/**
 * @author M. Togna
 * 
 */
public class NodeState {

	private Node<? extends NodeDefinition> node;
	private boolean relevant;
	private boolean required;
	private ValidationResults validationResults;

	public NodeState(Node<? extends NodeDefinition> node) {
		this.node = node;
	}

	public void update(Validator validator) {
		updateRelevance();
		updateRequired();
		updateValidation(validator);
	}

	public void updateRequired() {
		NodeDefinition definition = node.getDefinition();
		if (definition.isRequired()) {
			required = true;
		} else {
			String expr = definition.getRequiredExpression();
			if (StringUtils.isNotBlank(expr)) {
				try {
					ExpressionFactory expressionFactory = getExpressionFactory();
					RequiredExpression requiredExpr = expressionFactory.createRequiredExpression(expr);
					required = requiredExpr.evaluate(node.getParent(), node);
				} catch (InvalidExpressionException e) {
					throw new IdmInterpretationError("Error evaluating required", e);
				}
			}
			required = true;
		}

	}

	public void updateRelevance() {
		String expr = node.getDefinition().getRequiredExpression();
		if (StringUtils.isNotBlank(expr)) {
			try {
				ExpressionFactory expressionFactory = getExpressionFactory();
				RelevanceExpression relevanceExpr = expressionFactory.createRelevanceExpression(expr);
				relevant = relevanceExpr.evaluate(node.getParent(), node);
			} catch (InvalidExpressionException e) {
				throw new IdmInterpretationError("Unable to evaluate expression: " + expr, e);
			}
		}
		relevant = true;
	}

	public void updateValidation(Validator validator) {
		this.validationResults = validator.validate(this);
	}

	public Node<? extends NodeDefinition> getNode() {
		return node;
	}

	public boolean isRelevant() {
		return relevant;
	}

	// public void setRelevant(boolean relevant) {
	// this.relevant = relevant;
	// }

	public boolean isRequired() {
		return required;
	}

	// public void setRequired(boolean required) {
	// this.required = required;
	// }

	public ValidationResults getValidationResults() {
		return validationResults;
	}

	private ExpressionFactory getExpressionFactory() {
		Record record = node.getRecord();
		RecordContext recordContext = record.getContext();
		ExpressionFactory expressionFactory = recordContext.getExpressionFactory();
		return expressionFactory;
	}
	// public void setValidationResults(ValidationResults validationResults) {
	// this.validationResults = validationResults;
	// }

}
