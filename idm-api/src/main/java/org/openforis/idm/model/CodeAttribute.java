package org.openforis.idm.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.ModelPathExpression;
import org.openforis.idm.validation.CodeParentValidator;
import org.openforis.idm.validation.CodeValidator;
import org.openforis.idm.validation.ValidationResults;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class CodeAttribute extends Attribute<CodeAttributeDefinition, Code> {

	public CodeAttribute(CodeAttributeDefinition definition) {
		super(definition);
	}

	@Override
	public Code createValue(String string) {
		return new Code(string);
	}

	@Override
	public boolean isEmpty() {
		Code c = getValue();
		return c == null || (StringUtils.isBlank(c.getCode()) && StringUtils.isBlank(c.getQualifier()));
	}

	@Override
	protected boolean validateValue(ValidationResults results) {
		CodeParentValidator parentValidator = new CodeParentValidator();
		boolean validParent = parentValidator.validate(this);
		if (validParent) {
			CodeValidator codeValidator = new CodeValidator();
			boolean valid = codeValidator.validate(this);
			results.addResult(this, codeValidator, valid);
			return valid;
		} else {
			results.addResult(this, parentValidator, false);
			return false;
		}
	}

	public CodeListItem getCodeListItem() {
		Code code = getValue();
		if (code != null) {
			String codeValue = code.getCode();
			if (StringUtils.isNotBlank(codeValue)) {
				ModelVersion currentVersion = getRecord().getVersion();

				CodeAttributeDefinition definition = getDefinition();
				String parentExpression = definition.getParentExpression();
				if (StringUtils.isBlank(parentExpression)) {
					return findCodeListItem(definition.getList().getItems(), codeValue, currentVersion);
				} else {
					CodeAttribute codeParent = getCodeParent();
					if (codeParent != null) {
						CodeListItem codeListItemParent = codeParent.getCodeListItem();
						if (codeListItemParent != null) {
							return findCodeListItem(codeListItemParent.getChildItems(), codeValue, currentVersion);
						}
					}
				}
			}

		}
		return null;
	}

	public CodeAttribute getCodeParent() {
		try {
			String parentExpr = getDefinition().getParentExpression();
			RecordContext recordContext = getRecord().getContext();
			ExpressionFactory expressionFactory = recordContext.getExpressionFactory();
			ModelPathExpression expression = expressionFactory.createModelPathExpression(parentExpr);
			Node<?> parentNode = expression.evaluate(getParent(), this);
			if (parentNode != null && parentNode instanceof CodeAttribute) {
				return (CodeAttribute) parentNode;
			}
		} catch (Exception e) {
			// return null;
		}
		return null;
	}

	// TODO
	// check if it's used elsewhere
	private CodeListItem findCodeListItem(List<CodeListItem> list, String value, ModelVersion version) {
		for (CodeListItem item : list) {
			if (item.getCode().equals(value) && version.isApplicable(item)) {
				return item;
			}
		}
		return null;
	}

}
