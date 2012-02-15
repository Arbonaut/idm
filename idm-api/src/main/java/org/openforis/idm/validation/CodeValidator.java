/**
 * 
 */
package org.openforis.idm.validation;

import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.model.CodeAttribute;

/**
 * @author M. Togna
 * 
 */
public class CodeValidator implements Validator<CodeAttribute> {

	@Override
	public boolean validate(CodeAttribute node) {
		CodeListItem item = node.getCodeListItem();
		if (item == null) {
			return false;
		} else {
			return true;
		}
	}

}
