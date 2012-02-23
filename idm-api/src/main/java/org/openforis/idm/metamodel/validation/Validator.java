/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.IdmInterpretationError;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.CodeAttribute;
import org.openforis.idm.model.CoordinateAttribute;
import org.openforis.idm.model.DateAttribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.RecordContext;
import org.openforis.idm.model.TimeAttribute;
import org.openforis.idm.model.expression.CheckConditionExpression;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;

/**
 * @author M. Togna
 * 
 */
public class Validator {

	public ValidationResults validate(Node<?> node) {
		if (node instanceof Entity) {
			return validate((Entity) node);
		} else {
			return validate((Attribute<?, ?>) node);
		}
	}

	/**
	 * Validates cardinality of all entity children
	 */
	protected ValidationResults validate(Entity entity) {
		ValidationResults validationResults = new ValidationResults();
		List<NodeDefinition> childDefinitions = entity.getDefinition().getChildDefinitions();
		for (NodeDefinition childDef : childDefinitions) {
			validateChildMinCount(entity, childDef, validationResults);
			validateChildMaxCount(entity, childDef, validationResults);
		}
		return validationResults;
	}

	protected ValidationResults validate(Attribute<?, ?> attribute) {
		ValidationResults results = new ValidationResults();
		if (!attribute.isEmpty()) {
			boolean valid = validateAttributeValue(attribute, results);
			if (valid) {
				validateAttributeChecks(attribute, results);
			}
		}
		return results;
	}

	private void validateChildMinCount(Entity entity, NodeDefinition childDef, ValidationResults validationResults) {
		MinCountValidator validator = new MinCountValidator(childDef);
		boolean result = validator.evaluate(entity);
		validationResults.addResult(entity, validator, result);
	}

	private void validateChildMaxCount(Entity entity, NodeDefinition childDef, ValidationResults validationResults) {
		MaxCountValidator validator = new MaxCountValidator(childDef);
		boolean result = validator.evaluate(entity);
		validationResults.addResult(entity, validator, result);
	}

	private void validateAttributeChecks(Attribute<? extends AttributeDefinition, ?> attribute, ValidationResults results) {
		AttributeDefinition defn = attribute.getDefinition();
		List<Check> checks = defn.getChecks();
		for (Check check : checks) {
			if (evaluateCheckCondition(attribute, check.getCondition())) {
				boolean result = check.evaluate(attribute);
				results.addResult(attribute, check, result);
			}
		}
	}

	private boolean validateAttributeValue(Attribute<?, ?> attribute, ValidationResults results) {
		if (attribute instanceof CodeAttribute) {
			return validateCodeAttributeValue((CodeAttribute) attribute, results);
		} else if (attribute instanceof CoordinateAttribute) {
			return validateCoordinateAttributeValue((CoordinateAttribute) attribute, results);
		} else if (attribute instanceof DateAttribute) {
			return validateDateAttributeValue((DateAttribute) attribute, results);
		} else if (attribute instanceof TimeAttribute) {
			return validateTimeAttributeValue((TimeAttribute) attribute, results);
		}
		return true;
	}

	private boolean validateTimeAttributeValue(TimeAttribute attribute, ValidationResults results) {
		TimeValidator validator = new TimeValidator();
		boolean valid = validator.evaluate(attribute);
		results.addResult(attribute, validator, valid);
		return valid;
	}

	private boolean validateDateAttributeValue(DateAttribute attribute, ValidationResults results) {
		DateValidator validator = new DateValidator();
		boolean result = validator.evaluate(attribute);
		results.addResult(attribute, validator, result);
		return result;
	}

	private boolean validateCoordinateAttributeValue(CoordinateAttribute attribute, ValidationResults results) {
		CoordinateValidator validator = new CoordinateValidator();
		boolean valid = validator.evaluate(attribute);
		results.addResult(attribute, validator, valid);
		return valid;
	}

	private boolean validateCodeAttributeValue(CodeAttribute attribute, ValidationResults results) {
		CodeParentValidator parentValidator = new CodeParentValidator();
		boolean validParent = parentValidator.evaluate(attribute);
		if (validParent) {
			CodeValidator codeValidator = new CodeValidator();
			boolean valid = codeValidator.evaluate(attribute);
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
				RecordContext recordContext = record.getContext();
				ExpressionFactory expressionFactory = recordContext.getExpressionFactory();
				CheckConditionExpression expression = expressionFactory.createCheckConditionExpression(condition);
				return expression.evaluate(attribute.getParent(), attribute);
			} catch (InvalidExpressionException e) {
				throw new IdmInterpretationError("Unable to evaluate condition " + condition, e);
			}
		}
	}

}
