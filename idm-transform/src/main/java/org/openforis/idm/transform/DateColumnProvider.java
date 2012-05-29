package org.openforis.idm.transform;

import org.openforis.idm.metamodel.DateAttributeDefinition;

/**
 * @author G. Miceli
 */
public class DateColumnProvider extends AttributeColumnProvider {

	public DateColumnProvider(DateAttributeDefinition attributeDefinition, EntityColumnProvider parentProvider) {
		super(attributeDefinition, parentProvider);
	}
}
