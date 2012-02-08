package org.openforis.idm.model;

import org.openforis.idm.metamodel.CodeAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class CodeAttribute extends Attribute<CodeAttributeDefinition, Code> {

	public CodeAttribute(CodeAttributeDefinition definition) {
		super(definition);
	}

	@Override
	public Code createValue(String string) {
		return new Code(string);
	}
}
