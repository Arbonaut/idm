/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.model.CodeAttribute;

/**
 * @author M. Togna
 * 
 */
public class CodeValidator implements ValidationRule<CodeAttribute> {

	@Override
	public ValidationResultFlag evaluate(CodeAttribute attribute) {
		CodeListItem item = attribute.getCodeListItem();
		if (item == null) {
			return ValidationResultFlag.ERROR;
		} else {
			return ValidationResultFlag.OK;
		}
	}

}
