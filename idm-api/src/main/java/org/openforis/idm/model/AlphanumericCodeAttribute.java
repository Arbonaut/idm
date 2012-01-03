package org.openforis.idm.model;

import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeList.CodeType;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class AlphanumericCodeAttribute extends CodeAttribute<AlphanumericCode> {

	public AlphanumericCodeAttribute(CodeAttributeDefinition definition) {
		super(definition);
		if ( definition.getList().getCodeType()!=CodeType.ALPHANUMERIC ) {
			throw new IllegalArgumentException("Wrote codingScheme type defined; cannot create AlphanumericCodeAttribute ");		
		}
	}
}
