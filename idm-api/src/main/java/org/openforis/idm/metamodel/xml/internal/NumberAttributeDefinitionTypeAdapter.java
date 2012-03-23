package org.openforis.idm.metamodel.xml.internal;

import org.openforis.idm.metamodel.NumberAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class NumberAttributeDefinitionTypeAdapter extends EnumAdapter<NumberAttributeDefinition.Type> {

	public NumberAttributeDefinitionTypeAdapter() {
		super(NumberAttributeDefinition.Type.class);
	}
}
