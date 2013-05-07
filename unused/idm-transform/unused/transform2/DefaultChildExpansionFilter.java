package org.openforis.idm.transform2;

import static org.openforis.idm.metamodel.CodeAttributeDefinition.QUALIFIER_FIELD;
import static org.openforis.idm.metamodel.NumericAttributeDefinition.UNIT_FIELD;
import static org.openforis.idm.metamodel.NumericAttributeDefinition.UNIT_NAME_FIELD;

import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.FieldDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.NumericAttributeDefinition;

/**
 * 
 * @author G. Miceli
 *
 */
public class DefaultChildExpansionFilter implements ChildExpansionFilter {

	private static final DefaultChildExpansionFilter INSTANCE = new DefaultChildExpansionFilter();

	@Override
	public boolean isIncluded(NodeDefinition nodeDefn) {
		NodeDefinition parentDefn = nodeDefn.getParentDefinition();
		if ( parentDefn instanceof CodeAttributeDefinition ) {
			return isFieldIncluded((CodeAttributeDefinition) parentDefn, (FieldDefinition<?>) nodeDefn);
		} else if ( parentDefn instanceof NumericAttributeDefinition ) {
			return isFieldIncluded((NumericAttributeDefinition) parentDefn, (FieldDefinition<?>) nodeDefn);
		} else {
			return true;
		}
	}

	protected boolean isFieldIncluded(CodeAttributeDefinition parentDefn, FieldDefinition<?> fieldDefn) {
		String fieldName = fieldDefn.getName();
		if ( fieldName.equals(QUALIFIER_FIELD) ) {
			// Only include qualifier and column group if qualifier is ever allowed   
			CodeList list = parentDefn.getList();
			return list.isQualifiable();
		} else {
			return true;
		}
	}
	
	protected boolean isFieldIncluded(NumericAttributeDefinition parentDefn, FieldDefinition<?> fieldDefn) {
		String fieldName = fieldDefn.getName();
		if ( fieldName.equals(UNIT_FIELD) ) {
			// Only include unit column if it may ever be ambiguous
			return parentDefn.isVariableUnit();
		} else if ( fieldName.equals(UNIT_NAME_FIELD) ) {
			//TODO
			return false;
		} else {
			return true;
		}
	}

	public static DefaultChildExpansionFilter getInstance() {
		return INSTANCE;
	}
}
