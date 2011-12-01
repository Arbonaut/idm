package org.openforis.idm.model;

import org.openforis.idm.metamodel.AttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Attribute<D extends AttributeDefinition, V extends Value> extends ModelObject<D> {
	
	V getValue();
	
	void setValue(V value);
	
}
