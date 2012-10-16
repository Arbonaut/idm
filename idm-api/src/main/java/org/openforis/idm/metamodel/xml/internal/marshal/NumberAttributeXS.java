package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import org.openforis.idm.metamodel.NumberAttributeDefinition;

/**
 * 
 * @author G. Miceli
 *
 */
class NumberAttributeXS extends NumericAttributeXS<NumberAttributeDefinition> {

	NumberAttributeXS() {
		super(NUMBER);
	}

}
