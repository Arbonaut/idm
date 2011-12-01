package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface TextAttributeDefinition extends AttributeDefinition {
	enum Type {
		SHORT, MEMO 
	}
	
	Type getType();
}
