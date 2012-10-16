package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;

import org.openforis.idm.metamodel.CodeAttributeDefinition;

/**
 * 
 * @author G. Miceli
 *
 */
class CodeAttributeXS extends AttributeDefinitionXS<CodeAttributeDefinition> {

	CodeAttributeXS() {
		super(CODE);
	}
	
	@Override
	protected void attributes(CodeAttributeDefinition defn) throws IOException {
		super.attributes(defn);
		if ( defn.isKey() ) {
			attribute(KEY, true);
		}
		if ( defn.isAllowUnlisted() ) {
			attribute(STRICT, false);
		}
		attribute(LIST, defn.getListName());
		attribute(PARENT, defn.getParentExpression());
	}
}
