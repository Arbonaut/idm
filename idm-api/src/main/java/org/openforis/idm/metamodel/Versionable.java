package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Versionable {

	/**
	 * @return Returns the since.
	 * @uml.property name="since"
	 */
	public ModelVersion getSince();

	/**
	 * @return Returns the deprecated.
	 * @uml.property name="deprecated"
	 */
	public ModelVersion getDeprecated();

}
