package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface ModelObjectDefinition extends Versionable, Annotatable {

	/**
	 * @return  Returns the parent.
	 * @uml.property  name="parent"
	 * @uml.associationEnd  inverse="children:org.openforis.idm.metamodel.EntityDefinition"
	 */
	public EntityDefinition getParent();

	CardinalityCheck getCardinalityCheck();
	
	/**
	 * @return  Returns the name.
	 * @uml.property  name="name"
	 */
	public String getName();

	/**
	 * Setter of the property <tt>name</tt>
	 * @param name  The name to set.
	 * @uml.property  name="name"
	 */
	public void setName(String name);

	/**
	 * @return  Returns the relevantExpression.
	 * @uml.property  name="relevantExpression"
	 */
	public String getRelevantExpression();

	/**
	 * Setter of the property <tt>relevantExpression</tt>
	 * @param relevantExpression  The relevantExpression to set.
	 * @uml.property  name="relevantExpression"
	 */
	public void setRelevantExpression(String relevantExpression);

	/**
	 * @return  Returns the descriptions.
	 * @uml.property  name="descriptions"
	 */
	public MultilingualStringMap getDescriptions();

	/**
	 * Setter of the property <tt>descriptions</tt>
	 * @param descriptions  The descriptions to set.
	 * @uml.property  name="descriptions"
	 */
	public void setDescriptions(MultilingualStringMap descriptions);
	
	public MultilingualStringMap getLabels(LabelType type);

	public void setLabels(LabelType type, MultilingualStringMap labels);

	/**
	 * @return  Returns the multiple.
	 * @uml.property  name="multiple"
	 */
	public boolean isMultiple();

	/**
	 * Setter of the property <tt>multiple</tt>
	 * @param multiple  The multiple to set.
	 * @uml.property  name="multiple"
	 */
	public void setMultiple(boolean multiple);
}
