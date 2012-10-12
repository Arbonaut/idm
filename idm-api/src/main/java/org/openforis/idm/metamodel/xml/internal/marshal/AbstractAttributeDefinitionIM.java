package org.openforis.idm.metamodel.xml.internal.marshal;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;

/**
 * 
 * @author G. Miceli
 *
 * @param <T>
 */
class AbstractAttributeDefinitionIM<T extends AttributeDefinition> extends 
	AbstractNodeDefinitionIM<T, EntityDefinition> {

	protected AbstractAttributeDefinitionIM(String tag) {
		super(tag);
	}

}
