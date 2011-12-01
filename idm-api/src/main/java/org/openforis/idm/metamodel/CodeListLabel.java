/**
 * 
 */
package org.openforis.idm.metamodel;

/**
 * @author M. Togna
 * 
 */
public interface CodeListLabel extends LanguageSpecificText {

	public enum Type {
		ITEM, LIST
	}

	Type getType();

	void setType(Type type);

}
