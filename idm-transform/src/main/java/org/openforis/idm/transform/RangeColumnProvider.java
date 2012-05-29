package org.openforis.idm.transform;

import org.openforis.idm.metamodel.RangeAttributeDefinition;

/**
 * @author G. Miceli
 */
public class RangeColumnProvider extends AttributeColumnProvider {

	public RangeColumnProvider(RangeAttributeDefinition attributeDefinition, EntityColumnProvider parentProvider) {
		super(attributeDefinition, parentProvider);
	}
}
