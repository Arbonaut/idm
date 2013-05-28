/**
 * 
 */
package org.openforis.idm.metamodel;

import org.openforis.idm.model.CodeAttribute;

/**
 * @author M. Togna
 * @author S. Ricci
 *
 */
public interface ExternalCodeListProvider extends Activable {
	
	@Deprecated
	String getCode(CodeList list, String attribute, Object... keys);
	
	ExternalCodeListItem getItem(CodeAttribute attribute);

}
