package org.openforis.idm.metamodel;

import java.io.Serializable;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class SurveyObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private Survey survey;

	protected SurveyObject(Survey survey) {
		this.survey = survey;
	}

	public final Survey getSurvey() {
		return survey;
	}

	public final Schema getSchema() {
		return survey == null ? null : survey.getSchema();
	}
}