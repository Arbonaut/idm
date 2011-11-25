package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Versionable {

	/**
	 * @return  Returns the since.
	 * @uml.property  name="since"
	 */
	public ModelVersion getSince();

	/**
	 * Setter of the property <tt>since</tt>
	 * @param since  The since to set.
	 * @uml.property  name="since"
	 */
	public void setSince(ModelVersion since);

	/**
	 * @return  Returns the deprecated.
	 * @uml.property  name="deprecated"
	 */
	public ModelVersion getDeprecated();

	/**
	 * Setter of the property <tt>deprecated</tt>
	 * @param deprecated  The deprecated to set.
	 * @uml.property  name="deprecated"
	 */
	public void setDeprecated(ModelVersion deprecated);

}
