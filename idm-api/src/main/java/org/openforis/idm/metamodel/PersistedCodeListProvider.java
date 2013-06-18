package org.openforis.idm.metamodel;

import java.util.List;

/**
 * 
 * @author S. Ricci
 *
 */
public interface PersistedCodeListProvider {

	List<PersistedCodeListItem> getRootItems(CodeList codeList);
	
	List<PersistedCodeListItem> getChildItems(PersistedCodeListItem parentItem);
	
	List<PersistedCodeListItem> getItems(CodeList codeList, int level);

	PersistedCodeListItem getParentItem(PersistedCodeListItem item);
	
	boolean hasQualifiableItems(CodeList codeList);

}
