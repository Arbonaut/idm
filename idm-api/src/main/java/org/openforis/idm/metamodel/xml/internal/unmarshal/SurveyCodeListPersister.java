package org.openforis.idm.metamodel.xml.internal.unmarshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.SURVEY;

import org.openforis.idm.metamodel.xml.SurveyCodeListPersisterBinder;

/**
 * 
 * @author S. Ricci
 *
 */
public class SurveyCodeListPersister extends IdmlPullReader {
	
	private SurveyCodeListPersisterBinder binder;

	public SurveyCodeListPersister(SurveyCodeListPersisterBinder binder) {
		super(SURVEY);
		
		if ( binder == null ) {
			throw new NullPointerException("binder");
		}
		this.binder = binder;
		
		addChildPullReaders(
			new CodeListsPersisterPR()
		);
	}

	public SurveyCodeListPersisterBinder getBinder() {
		return binder;
	}
}
