/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.model.CodeAttribute;

/**
 * @author M. Togna
 * 
 *         Checks if code is valid in context of parent (if parent scope in IDM, not list scope!)
 *         <p/>
 *         Generates  a warning if parent is invalid or missing.
 */
public class CodeParentValidator implements ValidationRule<CodeAttribute> {

	@Override
	public boolean evaluate(CodeAttribute node) {
		CodeAttributeDefinition definition = node.getDefinition();
		if (StringUtils.isBlank(definition.getParentExpression())) {
			return true;
		} else {
			CodeAttribute parentCode = node.getCodeParent();
			if (parentCode == null) {
				return false;
			} else {
				CodeListItem parentCodeListItem = parentCode.getCodeListItem();
				if (parentCodeListItem == null) {
					return false;
				}
				return true;
			}
		}
	}

}
