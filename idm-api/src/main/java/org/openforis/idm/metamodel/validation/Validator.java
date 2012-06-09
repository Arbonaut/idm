/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.IdmInterpretationError;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.CodeAttribute;
import org.openforis.idm.model.CoordinateAttribute;
import org.openforis.idm.model.DateAttribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.IntegerRangeAttribute;
import org.openforis.idm.model.RealRangeAttribute;
import org.openforis.idm.model.Record;
import org.openforis.idm.model.TimeAttribute;
import org.openforis.idm.model.expression.CheckConditionExpression;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.InvalidExpressionException;

/**
 * @author M. Togna
 * 
 */
public class Validator {

	public ValidationResults validate(Attribute<?, ?> attribute) {
		ValidationResults results = new ValidationResults();
		if (!attribute.isEmpty()) {
			validateAttributeValue(attribute, results);
			if ( !results.hasErrors() ) {
				validateAttributeChecks(attribute, results);
			}
		}
		return results;
	}
	
	public ValidationResultFlag validateMinCount(Entity entity, String childName) {
		NodeDefinition childDefn = getChildDefinition(entity, childName);
		MinCountValidator v = getMinCountValidator(childDefn);
		ValidationResultFlag result = v.evaluate(entity);
		return result;
	}
	
	public ValidationResultFlag validateMaxCount(Entity entity, String childName) {
		NodeDefinition childDefn = getChildDefinition(entity, childName);
		MaxCountValidator v = getMaxCountValidator(childDefn);
		ValidationResultFlag result = v.evaluate(entity);
		return result;
	}

	protected MinCountValidator getMinCountValidator(NodeDefinition defn) {
		return new MinCountValidator(defn);
	}

	protected MaxCountValidator getMaxCountValidator(NodeDefinition defn) {
		return new MaxCountValidator(defn);
	}

	private NodeDefinition getChildDefinition(Entity entity, String childName) {
		EntityDefinition entityDefn = entity.getDefinition();
		NodeDefinition childDefn = entityDefn.getChildDefinition(childName);
		return childDefn;
	}

	@SuppressWarnings("rawtypes")
	protected void validateAttributeChecks(Attribute<?, ?> attribute, ValidationResults results) {
		
		// Attribute<? extends AttributeDefinition, ?> attribute = (Attribute<? extends AttributeDefinition, ?>) nodeState.getNode();
		AttributeDefinition defn = attribute.getDefinition();
		List<Check<?>> checks = defn.getChecks();
		for (Check check : checks) {
			if (evaluateCheckCondition(attribute, check.getCondition())) {
				@SuppressWarnings("unchecked")
				ValidationResultFlag result = check.evaluate(attribute);
				results.addResult(check, result);
			}
		}
	}

	protected void validateAttributeValue(Attribute<?, ?> attribute, ValidationResults results) {
		if (attribute instanceof CodeAttribute) {
			validateCodeAttributeValue((CodeAttribute) attribute, results);
		} else if (attribute instanceof CoordinateAttribute) {
			validateCoordinateAttributeValue((CoordinateAttribute) attribute, results);
		} else if (attribute instanceof DateAttribute) {
			validateDateAttributeValue((DateAttribute) attribute, results);
		} else if (attribute instanceof IntegerRangeAttribute) {
			validateIntegerRangeAttributeValue((IntegerRangeAttribute) attribute, results);
		} else if (attribute instanceof RealRangeAttribute) {
			validateRealRangeAttributeValue((RealRangeAttribute) attribute, results);
		} else if (attribute instanceof TimeAttribute) {
			validateTimeAttributeValue((TimeAttribute) attribute, results);
		}
	}

	private void validateTimeAttributeValue(TimeAttribute timeAttribute, ValidationResults results) {
		TimeValidator validator = new TimeValidator();
		ValidationResultFlag result = validator.evaluate(timeAttribute);
		results.addResult(validator, result);
	}

	private void validateDateAttributeValue(DateAttribute attribute, ValidationResults results) {
		DateValidator validator = new DateValidator();
		ValidationResultFlag result = validator.evaluate(attribute);
		results.addResult(validator, result);
	}

	private void validateCoordinateAttributeValue(CoordinateAttribute attribute, ValidationResults results) {
		CoordinateValidator validator = new CoordinateValidator();
		ValidationResultFlag result = validator.evaluate(attribute);
		results.addResult(validator, result);
	}

	private void validateCodeAttributeValue(CodeAttribute attribute, ValidationResults results) {
		CodeParentValidator parentValidator = new CodeParentValidator();
		ValidationResultFlag validParent = parentValidator.evaluate(attribute);
		if (validParent == ValidationResultFlag.OK ) {
			CodeValidator codeValidator = new CodeValidator();
			ValidationResultFlag result = codeValidator.evaluate(attribute);
			results.addResult(codeValidator, result);
		} else {
			results.addResult(parentValidator, ValidationResultFlag.WARNING);
		}
	}
	
	private void validateIntegerRangeAttributeValue(IntegerRangeAttribute attribute, ValidationResults results) {
		IntegerRangeValidator validator = new IntegerRangeValidator();
		ValidationResultFlag result = validator.evaluate(attribute);
		results.addResult(validator, result);
	}

	private void validateRealRangeAttributeValue(RealRangeAttribute attribute, ValidationResults results) {
		RealRangeValidator validator = new RealRangeValidator();
		ValidationResultFlag result = validator.evaluate(attribute);
		results.addResult(validator, result);
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
