package org.openforis.idm.metamodel;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CodeAttributeDefinition extends AttributeDefinition<CodeCheck> {

	/**
	 * @return  Returns the list.
	 * @uml.property  name="list"
	 */
	public CodeList getList();

	/**
	 * Setter of the property <tt>list</tt>
	 * @param list  The list to set.
	 * @uml.property  name="list"
	 */
	public void setList(CodeList list);

	/**
	 * @return  Returns the key.
	 * @uml.property  name="key"
	 */
	public boolean isKey();

	/**
	 * Setter of the property <tt>key</tt>
	 * @param key  The key to set.
	 * @uml.property  name="key"
	 */
	public void setKey(boolean key);

	/**
	 * @return  Returns the parentCodeAttributeDefinition.
	 * @uml.property  name="parentCodeAttributeDefinition"
	 */
	public CodeAttributeDefinition getParentCodeAttributeDefinition();

	/**
	 * Setter of the property <tt>parentCodeAttributeDefinition</tt>
	 * @param parentCodeAttributeDefinition  The parentCodeAttributeDefinition to set.
	 * @uml.property  name="parentCodeAttributeDefinition"
	 */
	public void setParentCodeAttributeDefinition(
			CodeAttributeDefinition parentCodeAttributeDefinition);

	/**
	 * @return  Returns the allowUnlisted.
	 * @uml.property  name="allowUnlisted"
	 */
	public boolean getAllowUnlisted();

	/**
	 * Setter of the property <tt>allowUnlisted</tt>
	 * @param allowUnlisted  The allowUnlisted to set.
	 * @uml.property  name="allowUnlisted"
	 */
	public void setAllowUnlisted(boolean allowUnlisted);

}
