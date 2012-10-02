package org.openforis.idm.metamodel;

import java.io.Serializable;

/**
 * @author G. Miceli
 */
public abstract class SurveyObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Survey survey;

	protected SurveyObject(Survey survey) {
		this.survey = survey;
	}

	public final Survey getSurvey() {
		return survey;
	}
	
	public final Schema getSchema() {
		return survey.getSchema();
	}

	public final Integer getId() {
		return id;
	}

	protected void setId(Integer id) {
		this.id = id;
	}
}
