package org.openforis.idm.metamodel.xml.internal.marshal;

import org.openforis.idm.metamodel.NumericAttributeDefinition;

/**
 * 
 * @author G. Miceli
 *
 */
abstract class NumericAttributeXS<T extends NumericAttributeDefinition> extends AttributeDefinitionXS<T> {

	protected NumericAttributeXS(String tag) {
		super(tag);
	}

}
