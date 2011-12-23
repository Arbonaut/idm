package org.openforis.idm.model.persistence;

import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.model.Record;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface RecordManager {
	Survey getSurvey();	
	
	Record create(String entityName);
	
	Record load(String entityName, long id);
	
	void save(Record record);
	
	void delete(String entityName, long id);
}
