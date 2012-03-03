package org.openforis.idm.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.model.expression.ExpressionFactory;
import org.openforis.idm.model.expression.ModelPathExpression;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class CodeAttribute extends Attribute<CodeAttributeDefinition, Code> {

	private static final long serialVersionUID = 1L;

	CodeAttribute() {
	}
	
	public CodeAttribute(CodeAttributeDefinition definition) {
		super(definition, String.class, String.class);
	}

	@SuppressWarnings("unchecked")
	public AttributeField<String> getCodeField() {
		return (AttributeField<String>) getField(0);
	}
	
	@SuppressWarnings("unchecked")
	public AttributeField<String> getQualifierField() {
		return (AttributeField<String>) getField(1);
	}

	@Override
	public Code getValue() {
		String code = getCodeField().getValue();
		String qualifier = getQualifierField().getValue();
		return new Code(code, qualifier);
	}
	
	@Override
	public void setValue(Code value) {
		if ( value == null ) {
			clearValue();
		} else {
			String code = value == null ? null : value.getCode();
			String qualifier = value == null ? null : value.getQualifier();
			getCodeField().setValue(code);
			getQualifierField().setValue(qualifier);
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
