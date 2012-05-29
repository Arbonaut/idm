package org.openforis.idm.transform;

import org.openforis.idm.metamodel.BooleanAttributeDefinition;

/**
 * @author G. Miceli
 */
public class BooleanColumnProvider extends AttributeColumnProvider {

	public BooleanColumnProvider(BooleanAttributeDefinition attributeDefinition, EntityColumnProvider parentProvider) {
		super(attributeDefinition, parentProvider);
	}
}
