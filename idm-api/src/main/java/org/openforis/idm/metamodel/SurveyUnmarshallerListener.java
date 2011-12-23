package org.openforis.idm.metamodel;

import javax.xml.bind.Unmarshaller;

/**
 * @author G. Miceli
 * @author M. Togna
 * 
 */
public class SurveyUnmarshallerListener extends Unmarshaller.Listener {

	@Override
	public void beforeUnmarshal(Object target, Object parent) {
		if ( target instanceof ModelDefinition ) {
			((ModelDefinition) target).beforeUnmarshal(parent);
		}

	}

	@Override
	public void afterUnmarshal(Object target, Object parent) {
		if ( target instanceof ModelDefinition ) {
			((ModelDefinition) target).afterUnmarshal(parent);
		}
	}

}
