package org.openforis.idm.transform;

import org.openforis.idm.metamodel.FileAttributeDefinition;

/**
 * @author G. Miceli
 */
public class FileColumnProvider extends AttributeColumnProvider {

	public FileColumnProvider(FileAttributeDefinition attributeDefinition, ColumnGroup parentGroup) {
		super(attributeDefinition, parentGroup);
	}
}
