package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface ModelAnnotation {

	/**
	 * @return Returns the namespace.
	 * @uml.property name="namespace"
	 */
	public String getNamespace();

	/**
	 * @return Returns the name.
	 * @uml.property name="name"
	 */
	public String getName();

	/**
	 * @return Returns the value.
	 * @uml.property name="value"
	 */
	public String getValue();

}
