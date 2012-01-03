package org.openforis.idm.model;

import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeList.CodeType;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class NumericCodeAttribute extends CodeAttribute<NumericCode> {

	public NumericCodeAttribute(CodeAttributeDefinition definition) {
		super(definition);
		if ( definition.getList().getCodeType()!=CodeType.NUMERIC ) {
			throw new IllegalArgumentException("Wrote codingScheme type defined; cannot create NumericCodeAttribute ");		
		}
	}
}
