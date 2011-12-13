package org.openforis.idm.model;

import org.openforis.idm.metamodel.ModelObjectDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface ModelObject<D extends ModelObjectDefinition> {

	D getDefinition();

	/**
	 * @return Convenience method, returns the name of the model object as specified in its definition.
	 * @uml.property name="name" readOnly="true"
	 */
	String getName();

	ModelObject<? extends ModelObjectDefinition> getParent();
	
	// DERIVED STATES

	boolean isRequired();

	boolean isRelevant();
}
