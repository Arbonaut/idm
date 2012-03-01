/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.IdmInterpretationError;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.CodeAttribute;
import org.openforis.idm.model.CoordinateAttribute;
import org.openforis.idm.model.DateAttribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.TimeAttribute;
import org.openforis.idm.model.expression.CheckConditionExpression;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;
import org.openforis.idm.model.state.NodeState;

/**
 * @author M. Togna
 * 
 */
public class Validator {


	public ValidationResults validate(Attribute<?, ?> attribute) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public ValidationResults validate(NodeState nodeState) {
		Node<?> node = nodeState.getNode();
		if (node instanceof Entity) {
			return validateEntity(nodeState);
		} else {
			return validateAttribute(nodeState);
		}
	}

	// public ValidationResults validate(Node<?> node) {
	// if (node instanceof Entity) {
	// return validate((Entity) node);
	// } else {
	// return validate((Attribute<?, ?>) node);
	// }
	// }

	/**
	 * Validates cardinality of all entity children
	 * 
	 * @param nodeState
	 */
	protected ValidationResults validateEntity(NodeState nodeState) {
		ValidationResults validationResults = new ValidationResults();
		Entity entity = (Entity) nodeState.getNode();
		List<NodeDefinition> childDefinitions = entity.getDefinition().getChildDefinitions();
		for (NodeDefinition childDef : childDefinitions) {
			validateChildMinCount(nodeState, childDef, validationResults);
			validateChildMaxCount(nodeState, childDef, validationResults);
		}
		return validationResults;
	}

	protected ValidationResults validateAttribute(NodeState nodeState) {
		ValidationResults results = new ValidationResults();
		Attribute<?, ?> attribute = (Attribute<?, ?>) nodeState.getNode();
		if (!attribute.isEmpty()) {
			boolean valid = validateAttributeValue(nodeState, results);
			if (valid) {
				validateAttributeChecks(nodeState, results);
			}
		}
		return results;
	}

	private void validateChildMinCount(NodeState nodeState, NodeDefinition childDef, ValidationResults validationResults) {
		MinCountValidator validator = new MinCountValidator(childDef);
		boolean result = validator.evaluate(nodeState);
		validationResults.addResult(nodeState.getNode(), validator, result);
	}

	private void validateChildMaxCount(NodeState nodeState, NodeDefinition childDef, ValidationResults validationResults) {
		MaxCountValidator validator = new MaxCountValidator(childDef);
		boolean result = validator.evaluate(nodeState);
		validationResults.addResult(nodeState.getNode(), validator, result);
	}

	private void validateAttributeChecks(NodeState nodeState, ValidationResults results) {
		@SuppressWarnings("unchecked")
		Attribute<? extends AttributeDefinition, ?> attribute = (Attribute<? extends AttributeDefinition, ?>) nodeState.getNode();
		AttributeDefinition defn = attribute.getDefinition();
		List<Check> checks = defn.getChecks();
		for (Check check : checks) {
			if (evaluateCheckCondition(attribute, check.getCondition())) {
				boolean result = check.evaluate(nodeState);
				results.addResult(attribute, check, result);
			}
		}
	}

	private boolean validateAttributeValue(NodeState nodeState, ValidationResults results) {
		Attribute<?, ?> attribute = (Attribute<?, ?>) nodeState.getNode();
		if (attribute instanceof CodeAttribute) {
			return validateCodeAttributeValue(nodeState, results);
		} else if (attribute instanceof CoordinateAttribute) {
			return validateCoordinateAttributeValue(nodeState, results);
		} else if (attribute instanceof DateAttribute) {
			return validateDateAttributeValue(nodeState, results);
		} else if (attribute instanceof TimeAttribute) {
			return validateTimeAttributeValue(nodeState, results);
		}
		return true;
	}

	private boolean validateTimeAttributeValue(NodeState nodeState, ValidationResults results) {
		TimeAttribute attribute = (TimeAttribute) nodeState.getNode();
		TimeValidator validator = new TimeValidator();
		boolean valid = validator.evaluate(nodeState);
		results.addResult(attribute, validator, valid);
		return valid;
	}

	private boolean validateDateAttributeValue(NodeState nodeState, ValidationResults results) {
		DateAttribute attribute = (DateAttribute) nodeState.getNode();
		DateValidator validator = new DateValidator();
		boolean result = validator.evaluate(nodeState);
		results.addResult(attribute, validator, result);
		return result;
	}

	private boolean validateCoordinateAttributeValue(NodeState nodeState, ValidationResults results) {
		CoordinateAttribute attribute = (CoordinateAttribute) nodeState.getNode();
		CoordinateValidator validator = new CoordinateValidator();
		boolean valid = validator.evaluate(nodeState);
		results.addResult(attribute, validator, valid);
		return valid;
	}

	private boolean validateCodeAttributeValue(NodeState nodeState, ValidationResults results) {
		CodeAttribute attribute = (CodeAttribute) nodeState.getNode();
		CodeParentValidator parentValidator = new CodeParentValidator();
		boolean validParent = parentValidator.evaluate(nodeState);
		if (validParent) {
			CodeValidator codeValidator = new CodeValidator();
			boolean valid = codeValidator.evaluate(nodeState);
			results.addResult(attribute, codeValidator, valid);
			return valid;
		} else {
			results.addResult(attribute, parentValidator, false);
			return false;
		}
	}

	private boolean evaluateCheckCondition(Attribute<?, ?> attribute, String condition) {
		if (StringUtils.isBlank(condition)) {
			return true;
		} else {
			try {
				Record record = attribute.getRecord();
				SurveyContext recordContext = record.getSurveyContext();
				ExpressionFactory expressionFactory = recordContext.getExpressionFactory();
				CheckConditionExpression expression = expressionFactory.createCheckConditionExpression(condition);
				return expression.evaluate(attribute.getParent(), attribute);
			} catch (InvalidExpressionException e) {
				throw new IdmInterpretationError("Unable to evaluate condition " + condition, e);
			}
		}
	}

}
