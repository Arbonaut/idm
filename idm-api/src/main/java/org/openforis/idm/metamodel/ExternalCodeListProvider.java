/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.List;

/**
 * @author M. Togna
 * @author S. Ricci
 *
 */
public interface ExternalCodeListProvider {
	
	@Deprecated
	String getCode(CodeList list, String attribute, Object... keys);
	
	String getCode(CodeList list, String attribute, StringKeyValuePair... filters);
	
	ExternalCodeListItem getItem(CodeList list, String codeKey, StringKeyValuePair... filters);

	ExternalCodeListItem getParentItem(ExternalCodeListItem item);
	
	List<ExternalCodeListItem> getChildItems(CodeList list, String attribute);
	
	List<ExternalCodeListItem> getChildItems(ExternalCodeListItem item, String attribute);

}
