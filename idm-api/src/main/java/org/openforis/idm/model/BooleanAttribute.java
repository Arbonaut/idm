package org.openforis.idm.model;

import org.openforis.idm.metamodel.BooleanAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class BooleanAttribute extends Attribute<BooleanAttributeDefinition, Boolean> {

	private static final long serialVersionUID = 1L;

	public BooleanAttribute(BooleanAttributeDefinition definition) {
		super(definition, Boolean.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Boolean getValue() {
		Field<Boolean> field = (Field<Boolean>) getField(0);
		return field.getValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(Boolean value) {
		Field<Boolean> field = (Field<Boolean>) getField(0);
		field.setValue(value);
		onUpdateValue();
	}

}
