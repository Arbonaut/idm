package org.openforis.idm.transform;

import org.openforis.idm.metamodel.TextAttributeDefinition;

/**
 * @author G. Miceli
 */
public class TextColumnProvider extends AttributeColumnProvider {

	public TextColumnProvider(TextAttributeDefinition attributeDefinition, EntityColumnProvider parentProvider) {
		super(attributeDefinition, parentProvider);
	}
}
