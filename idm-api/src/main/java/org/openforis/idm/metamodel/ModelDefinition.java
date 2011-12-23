package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ModelDefinition {
	
	@XmlTransient
	private Survey survey;
	
	public Survey getSurvey() {
		return survey;
	}
	
	void setSurvey(Survey survey) {
		this.survey = survey;
	}
	
	protected void beforeUnmarshal(Object parent) {
		if ( parent instanceof Survey ) {
			this.survey = (Survey) parent; 
		} else if ( parent instanceof ModelDefinition ) {
			this.survey = ((ModelDefinition) parent).getSurvey();
		} 
	}

	protected void afterUnmarshal(Object parent) {
	}
}
