package org.openforis.idm.metamodel.xml.internal.marshal;

import org.openforis.idm.metamodel.TextAttributeDefinition;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public class TextAttributeDefinitionTypeAdapter extends EnumAdapter<TextAttributeDefinition.Type> {

	public TextAttributeDefinitionTypeAdapter() {
		super(TextAttributeDefinition.Type.class);
	}
}
