/**
 * 
 */
package org.openforis.idm.metamodel;


/**
 * @author S. Ricci
 */
public interface SurveyCodeListPersisterContext {

	ExternalCodeListProvider getExternalCodeListProvider();
	
	ExternalCodeListPersister getExternalCodeListPersister();

}
