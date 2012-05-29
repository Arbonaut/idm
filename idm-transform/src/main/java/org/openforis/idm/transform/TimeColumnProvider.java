package org.openforis.idm.transform;

import org.openforis.idm.metamodel.TimeAttributeDefinition;

/**
 * @author G. Miceli
 */
public class TimeColumnProvider extends AttributeColumnProvider {

	public TimeColumnProvider(TimeAttributeDefinition attributeDefinition, EntityColumnProvider parentProvider) {
		super(attributeDefinition, parentProvider);
	}
}
