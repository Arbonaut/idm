package org.openforis.idm.metamodel;

/**
 * 
 * @author S. Ricci
 *
 */
public interface CodeListItemPersister {

	void save(PersistedCodeListItem item);
	
	void delete(PersistedCodeListItem item);
	
	void deleteAllItems(CodeList list);
	
}
