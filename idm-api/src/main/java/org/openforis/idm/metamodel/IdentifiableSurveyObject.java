package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 */
public abstract class IdentifiableSurveyObject extends SurveyObject {
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	protected IdentifiableSurveyObject(Survey survey, int id) {
		super(survey);
		this.id = id;
	}

	public final int getId() {
		return id;
	}
}
