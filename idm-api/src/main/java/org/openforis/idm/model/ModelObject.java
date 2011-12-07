package org.openforis.idm.model;

import org.openforis.idm.metamodel.ModelObjectDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface ModelObject<D extends ModelObjectDefinition> {

	D getDefinition();

}
