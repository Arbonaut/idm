package org.openforis.idm.metamodel.xml.internal.marshal;

import org.openforis.idm.metamodel.RangeAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class RangeAttributeDefinitionTypeAdapter extends EnumAdapter<RangeAttributeDefinition.Type> {

	public RangeAttributeDefinitionTypeAdapter() {
		super(RangeAttributeDefinition.Type.class);
	}
}
