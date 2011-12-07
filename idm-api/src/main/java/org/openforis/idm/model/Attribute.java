package org.openforis.idm.model;

import org.openforis.idm.metamodel.AttributeDefinition;

/**
 * @author   G. Miceli
 * @author   M. Togna
 */
public interface Attribute<D extends AttributeDefinition, V extends Value> extends ModelObject<D> {

	/**
	 * @return
	 * @uml.property  name="value"
	 */
	V getValue();

	/**
	 * @param  value
	 * @uml.property  name="value"
	 */
	void setValue(V value);

}
