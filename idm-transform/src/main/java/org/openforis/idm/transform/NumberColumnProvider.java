package org.openforis.idm.transform;

import org.openforis.idm.metamodel.FieldDefinition;
import org.openforis.idm.metamodel.NumberAttributeDefinition;

/**
 * @author G. Miceli
 */
public class NumberColumnProvider extends AttributeColumnProvider {

	public NumberColumnProvider(NumberAttributeDefinition attributeDefinition, NodeColumnProvider parentProvider) {
		super(attributeDefinition, parentProvider);
	}

	@Override
	protected boolean isIncluded(FieldDefinition<?> fieldDefinition) {
		String fieldName = fieldDefinition.getName();
		if ( fieldName.equals("unit") ) {
			// Only include unit column if it may ever be ambiguous
			NumberAttributeDefinition defn = (NumberAttributeDefinition) getNodeDefinition();
			return defn.isVariableUnit();
		} else {
			return true;
		}
	}
}
