/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.model.CodeAttribute;
import org.openforis.idm.model.state.NodeState;

/**
 * @author M. Togna
 * 
 */
public class CodeValidator implements ValidationRule {

	@Override
	public boolean evaluate(NodeState nodeState) {
		CodeAttribute attribute = (CodeAttribute) nodeState.getNode();
		CodeListItem item = attribute.getCodeListItem();
		if (item == null) {
			return false;
		} else {
			return true;
		}
	}

}
