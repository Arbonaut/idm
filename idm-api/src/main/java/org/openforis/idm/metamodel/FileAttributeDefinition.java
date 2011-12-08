package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface FileAttributeDefinition extends AttributeDefinition {

	/**
	 * @return Returns the maxSize.
	 * @uml.property name="maxSize"
	 */
	public Integer getMaxSize();

	/**
	 * @return Returns the extensions.
	 * @uml.property name="extensions" multiplicity="(0 -1)" dimension="1"
	 */
	public String[] getExtensions();
}
