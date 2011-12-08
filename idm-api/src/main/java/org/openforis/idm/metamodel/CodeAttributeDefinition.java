package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CodeAttributeDefinition extends AttributeDefinition {

	/**
	 * @return Returns the list.
	 * @uml.property name="list" readOnly="true"
	 */
	public CodeList getList();

	/**
	 * @return Returns the key.
	 * @uml.property name="key" readOnly="true"
	 */
	public boolean isKey();

	/**
	 * @return Returns the parentExpression.
	 * @uml.property name="parentExpression" readOnly="true"
	 */
	public String getParentExpression();

	/**
	 * @return Returns the allowUnlisted.
	 * @uml.property name="allowUnlisted" readOnly="true"
	 */
	public boolean getAllowUnlisted();

}
