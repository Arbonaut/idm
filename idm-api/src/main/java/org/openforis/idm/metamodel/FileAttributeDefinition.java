package org.openforis.idm.metamodel;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface FileAttributeDefinition extends AttributeDefinition {

	/**
	 * @return  Returns the maxSize.
	 * @uml.property  name="maxSize"
	 */
	public Integer getMaxSize();

	/**
	 * Setter of the property <tt>maxSize</tt>
	 * @param maxSize  The maxSize to set.
	 * @uml.property  name="maxSize"
	 */
	public void setMaxSize(Integer maxSize);

	/**
	 * @return  Returns the extensions.
	 * @uml.property  name="extensions" multiplicity="(0 -1)" dimension="1"
	 */
	public String[] getExtensions();

	/**
	 * Setter of the property <tt>extensions</tt>
	 * @param extensions  The extensions to set.
	 * @uml.property  name="extensions"
	 */
	public void setExtensions(String[] extensions);
}
