package org.openforis.idm.metamodel;

/**
 * 
 * @author S. Ricci
 *
 */
public interface ExternalCodeListPersister {

	void save(ExternalCodeListItem item);
	
	void delete(ExternalCodeListItem item);
	
	void deleteAll(CodeList list);
	
}
