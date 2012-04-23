package org.openforis.idm.model;

import org.openforis.idm.metamodel.TextAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class TextAttribute extends Attribute<TextAttributeDefinition, String> {

	private static final long serialVersionUID = 1L;

	public TextAttribute(TextAttributeDefinition definition) {
		super(definition);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public String getValue() {
		Field<String> field = (Field<String>) getField(0);
		return field.getValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setValue(String value) {
		Field<String> field = (Field<String>) getField(0);
		field.setValue(value);
		onUpdateValue();
	}
}
