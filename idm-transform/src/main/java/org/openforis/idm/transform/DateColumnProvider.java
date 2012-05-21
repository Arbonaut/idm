package org.openforis.idm.transform;

import org.openforis.idm.metamodel.DateAttributeDefinition;

/**
 * @author G. Miceli
 */
public class DateColumnProvider extends AttributeColumnProvider {

	public DateColumnProvider(DateAttributeDefinition attributeDefinition, ColumnGroup parentGroup) {
		super(attributeDefinition, parentGroup);
	}
}
