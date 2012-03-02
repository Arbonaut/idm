package org.openforis.idm.model;

import org.openforis.idm.metamodel.TextAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class TextAttribute extends AtomicAttribute<TextAttributeDefinition, String> {

	private static final long serialVersionUID = 1L;

	public TextAttribute(TextAttributeDefinition definition) {
		super(definition, String.class);
	}
	
}
