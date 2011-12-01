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
	 * Setter of the property <tt>namespace</tt>
	 * 
	 * @param namespace
	 *            The namespace to set.
	 * @uml.property name="namespace"
	 */
	public void setNamespace(String namespace);

	/**
	 * @return Returns the name.
	 * @uml.property name="name"
	 */
	public String getName();

	/**
	 * Setter of the property <tt>name</tt>
	 * 
	 * @param name
	 *            The name to set.
	 * @uml.property name="name"
	 */
	public void setName(String name);

	/**
	 * @return Returns the value.
	 * @uml.property name="value"
	 */
	public String getValue();

	/**
	 * Setter of the property <tt>value</tt>
	 * 
	 * @param value
	 *            The value to set.
	 * @uml.property name="value"
	 */
	public void setValue(String value);

}
