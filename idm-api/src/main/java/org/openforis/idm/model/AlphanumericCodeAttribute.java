package org.openforis.idm.model;

import org.openforis.idm.metamodel.CodeAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class AlphanumericCodeAttribute extends CodeAttribute<AlphanumericCode> {

	public AlphanumericCodeAttribute(CodeAttributeDefinition definition) {
		super(definition);
		if ( !definition.getList().isAlphanumeric() ) {
			throw new IllegalArgumentException("Wrong codingScheme type for AlphanumericCodeAttribute ");		
		}
	}
}
