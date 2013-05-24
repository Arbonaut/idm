/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.List;

import org.openforis.idm.model.CodeAttribute;

/**
 * @author M. Togna
 * @author S. Ricci
 *
 */
public interface ExternalCodeListProvider {
	
	@Deprecated
	String getCode(CodeList list, String attribute, Object... keys);
	
	ExternalCodeListItem getItem(CodeAttribute atttribute);

	ExternalCodeListItem getParentItem(ExternalCodeListItem item);
	
	List<ExternalCodeListItem> getChildItems(CodeList list);
	
	List<ExternalCodeListItem> getChildItems(ExternalCodeListItem item);

}
