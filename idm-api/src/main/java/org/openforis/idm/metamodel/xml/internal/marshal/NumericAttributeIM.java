package org.openforis.idm.metamodel.xml.internal.marshal;

import org.openforis.idm.metamodel.NumericAttributeDefinition;

/**
 * 
 * @author G. Miceli
 *
 */
abstract class NumericAttributeIM<T extends NumericAttributeDefinition> extends AbstractAttributeDefinitionIM<T> {

	protected NumericAttributeIM(String tag) {
		super(tag);
	}

}
