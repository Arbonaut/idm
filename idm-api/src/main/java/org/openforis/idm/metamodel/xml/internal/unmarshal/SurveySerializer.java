package org.openforis.idm.metamodel.xml.internal.unmarshal;

import org.openforis.idm.metamodel.xml.SurveyIdmlBinder;

/**
 * Load a Survey object from IDML
 * 
 * @author G. Miceli
 */
public class SurveySerializer {

	private SurveyIdmlBinder binder;

	public SurveySerializer(SurveyIdmlBinder binder) {
		this.binder = binder;
	}

}
