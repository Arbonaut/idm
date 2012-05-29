package org.openforis.idm.transform;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.FieldDefinition;

/**
 * @author G. Miceli
 */
public class CodeColumnProvider extends AttributeColumnProvider {

	public CodeColumnProvider(AttributeDefinition attributeDefinition, EntityColumnProvider parentProvider) {
		super(attributeDefinition, parentProvider);
	}
	
	@Override
	protected boolean isIncluded(FieldDefinition<?> fieldDefinition) {
		String fieldName = fieldDefinition.getName();
		if ( fieldName.equals("qualifier") ) {
			// Only include qualifier and column group if qualifier is ever allowed   
			CodeAttributeDefinition defn = (CodeAttributeDefinition) getNodeDefinition();
			CodeList list = defn.getList();
			return list.isQualifiable();
		} else {
			return true;
		}
	}
}
