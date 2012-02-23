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
	public boolean evaluate(CodeAttribute node) {
		CodeListItem item = node.getCodeListItem();
		if (item == null) {
			return false;
		} else {
			return true;
		}
	}

}
