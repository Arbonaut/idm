package org.openforis.idm.transform;

import java.util.List;

import org.openforis.idm.metamodel.FieldDefinition;

/**
 * @author G. Miceli
 */
public class FieldColumnProvider extends NodeColumnProvider {

	public FieldColumnProvider(FieldDefinition<?> fieldDefinition, AttributeColumnProvider parentProvider) {
		super(fieldDefinition, parentProvider);
	}

	@Override
	protected void createChildProviders(List<NodeColumnProvider> providers) throws IllegalTransformationException {
		// no child providers
	}
}
