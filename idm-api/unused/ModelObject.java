package org.openforis.idm.model;

import org.openforis.idm.metamodel.SchemaObjectDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface ModelObject<D extends SchemaObjectDefinition> {

	D getDefinition();

	/**
	 * @return Convenience method, returns the name of the model object as specified in its definition.
	 * @uml.property name="name" 
	 */
	String getName();

	Entity getParent();
}
