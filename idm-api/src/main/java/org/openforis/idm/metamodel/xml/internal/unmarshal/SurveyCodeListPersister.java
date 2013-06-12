package org.openforis.idm.metamodel.xml.internal.unmarshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.APPLICATION_OPTIONS;
import static org.openforis.idm.metamodel.xml.IdmlConstants.CYCLE;
import static org.openforis.idm.metamodel.xml.IdmlConstants.DESCRIPTION;
import static org.openforis.idm.metamodel.xml.IdmlConstants.LANGUAGE;
import static org.openforis.idm.metamodel.xml.IdmlConstants.PROJECT;
import static org.openforis.idm.metamodel.xml.IdmlConstants.SCHEMA;
import static org.openforis.idm.metamodel.xml.IdmlConstants.SPATIAL_REFERENCE_SYSTEMS;
import static org.openforis.idm.metamodel.xml.IdmlConstants.SURVEY;
import static org.openforis.idm.metamodel.xml.IdmlConstants.UNITS;
import static org.openforis.idm.metamodel.xml.IdmlConstants.URI;
import static org.openforis.idm.metamodel.xml.IdmlConstants.VERSIONING;

import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.SurveyCodeListPersisterBinder;

/**
 * 
 * @author S. Ricci
 *
 */
public class SurveyCodeListPersister extends IdmlPullReader {
	
	private SurveyCodeListPersisterBinder binder;
	private Survey survey;

	public SurveyCodeListPersister(SurveyCodeListPersisterBinder binder, Survey survey) {
		super(SURVEY);
		if ( binder == null ) {
			throw new NullPointerException("binder");
		}
		if ( survey == null ) {
			throw new NullPointerException("survey");
		}
		this.binder = binder;
		this.survey = survey;
		
		addChildPullReaders(
				new SkipElementPR(PROJECT), 
				new SkipElementPR(URI), 
				new SkipElementPR(CYCLE),
				new SkipElementPR(DESCRIPTION),
				new SkipElementPR(LANGUAGE),
				new SkipElementPR(APPLICATION_OPTIONS),
				new SkipElementPR(VERSIONING), 
				new CodeListsPersisterPR(),
				new SkipElementPR(UNITS),
				new SkipElementPR(SPATIAL_REFERENCE_SYSTEMS),
				new SkipElementPR(SCHEMA)
		);
	}

	public SurveyCodeListPersisterBinder getBinder() {
		return binder;
	}
	
	@Override
	public Survey getSurvey() {
		return survey;
	}
}
