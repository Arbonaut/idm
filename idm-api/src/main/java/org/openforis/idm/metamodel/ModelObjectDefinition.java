package org.openforis.idm.metamodel;

import java.util.List;

import org.openforis.idm.metamodel.ModelObjectLabel.LabelType;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface ModelObjectDefinition extends Versionable, Annotatable, ModelObjectDefinitionContainer {

	/**
	 * @return Returns the parent.
	 * @uml.property name="parentDefinition"
	 * @uml.associationEnd inverse="children:org.openforis.idm.metamodel.EntityDefinition"
	 */
	EntityDefinition getParentDefinition();

	/**
	 * @return Returns the name.
	 * @uml.property name="name"
	 */
	String getName();

	/**
	 * Setter of the property <tt>name</tt>
	 * 
	 * @param name
	 *            The name to set.
	 * @uml.property name="name"
	 */
	void setName(String name);


	/**
	 * @return Returns the requiredExpression.
	 * @uml.property name="requiredExpression"
	 */
	String getRequiredExpression();

	/**
	 * Setter of the property <tt>requiredExpression</tt>
	 * 
	 * @param requiredExpression
	 *            The requiredExpression to set.
	 * @uml.property name="requiredExpression"
	 */
	void setRequiredExpression(String requiredExpression);

	/**
	 * @return Returns the minCount.
	 * @uml.property name="minCount"
	 */
	Integer getMinCount();

	/**
	 * Setter of the property <tt>minCount</tt>
	 * 
	 * @param minCount
	 *            The minCount to set.
	 * @uml.property name="minCount"
	 */
	void setMinCount(Integer minCount);

	/**
	 * @return Returns the maxCount.
	 * @uml.property name="maxCount"
	 */
	Integer getMaxCount();

	/**
	 * Setter of the property <tt>maxCount</tt>
	 * 
	 * @param maxCount
	 *            The maxCount to set.
	 * @uml.property name="maxCount"
	 */
	void setMaxCount(Integer maxCount);

	/**
	 * @return Returns the relevantExpression.
	 * @uml.property name="relevantExpression"
	 */
	String getRelevantExpression();

	/**
	 * Setter of the property <tt>relevantExpression</tt>
	 * 
	 * @param relevantExpression
	 *            The relevantExpression to set.
	 * @uml.property name="relevantExpression"
	 */
	void setRelevantExpression(String relevantExpression);
	
	/**
	 * @return Returns the descriptions.
	 * @uml.property name="descriptions"
	 */
	List<LanguageSpecificText> getDescriptions();

	/**
	 * Setter of the property <tt>descriptions</tt>
	 * 
	 * @param descriptions
	 *            The descriptions to set.
	 * @uml.property name="descriptions"
	 */
	void setDescriptions(List<LanguageSpecificText> descriptions);

	/**
	 * @return Returns the labels.
	 * @uml.property name="labels"
	 */
	List<ModelObjectLabel> getLabels();

	List<ModelObjectLabel> getLabelsByType(LabelType labelType);

	/**
	 * Setter of the property <tt>labels</tt>
	 * 
	 * @param labels
	 *            The labels to set.
	 * @uml.property name="labels"
	 */
	void setLabels(List<ModelObjectLabel> labels);

	/**
	 * @return Returns the multiple.
	 * @uml.property name="multiple"
	 */
	boolean isMultiple();

	/**
	 * Setter of the property <tt>multiple</tt>
	 * 
	 * @param multiple
	 *            The multiple to set.
	 * @uml.property name="multiple"
	 */
	void setMultiple(boolean multiple);
}
