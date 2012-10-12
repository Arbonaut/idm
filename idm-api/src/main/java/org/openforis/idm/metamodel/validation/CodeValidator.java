/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.model.CodeAttribute;

/**
 * @author M. Togna
 * 
 */
public class CodeValidator implements ValidationRule<CodeAttribute> {

	@Override
	public ValidationResultFlag evaluate(CodeAttribute attribute) {
		if (isExternalCodeList(attribute)) {
			ExternalCodeValidator externalCodeValidator = new ExternalCodeValidator();
			return externalCodeValidator.evaluate(attribute);
		} else {
			CodeListItem item = attribute.getCodeListItem();
			if (item == null) {
				return ValidationResultFlag.ERROR;
			} else {
				return ValidationResultFlag.OK;
			}
		}
	}

	private boolean isExternalCodeList(CodeAttribute node) {
		CodeAttributeDefinition definition = node.getDefinition();
		CodeList codeList = definition.getList();
		return StringUtils.isNotBlank(codeList.getLookupTable());
	}

}
