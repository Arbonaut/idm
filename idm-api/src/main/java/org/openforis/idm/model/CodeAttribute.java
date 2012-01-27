package org.openforis.idm.model;

import org.openforis.idm.metamodel.CodeAttributeDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class CodeAttribute<V extends Code<?>> extends Attribute<CodeAttributeDefinition, V> {

	public CodeAttribute(CodeAttributeDefinition definition) {
		super(definition);
	}
}
