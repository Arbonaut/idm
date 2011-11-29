package org.openforis.idm.metamodel;

import java.util.List;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CodeListItem extends Versionable {

	/**
	 * @return Returns the labels.
	 * @uml.property name="labels"
	 */
	public List<LanguageSpecificText> getLabels();

	/**
	 * Setter of the property <tt>labels</tt>
	 * 
	 * @param labels
	 *            The labels to set.
	 * @uml.property name="labels"
	 */
	public void setLabels(List<LanguageSpecificText> labels);

	/**
	 * @return Returns the descriptions.
	 * @uml.property name="descriptions"
	 */
	public List<LanguageSpecificText> getDescriptions();

	/**
	 * Setter of the property <tt>descriptions</tt>
	 * 
	 * @param descriptions
	 *            The descriptions to set.
	 * @uml.property name="descriptions"
	 */
	public void setDescriptions(List<LanguageSpecificText> descriptions);

	/**
	 * @return Returns the children.
	 * @uml.property name="children"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="item1:org.openforis.idm.metamodel.CodeListItem"
	 */
	public List<CodeListItem> getChildren();

	/**
	 * Setter of the property <tt>children</tt>
	 * 
	 * @param children
	 *            The children to set.
	 * @uml.property name="children"
	 */
	public void setChildren(List<CodeListItem> children);

	/**
	 * @return Returns the qualifiable.
	 * @uml.property name="qualifiable"
	 */
	public boolean isQualifiable();

	/**
	 * Setter of the property <tt>qualifiable</tt>
	 * 
	 * @param qualifiable
	 *            The qualifiable to set.
	 * @uml.property name="qualifiable"
	 */
	public void setQualifiable(boolean qualifiable);

	/**
	 * @return Returns the codes.
	 * @uml.property name="codes"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="codeListItem:org.openforis.idm.metamodel.CodeDefinition"
	 */
	public List<CodeDefinition> getCodes();

	/**
	 * Setter of the property <tt>codes</tt>
	 * 
	 * @param codeDefinitions
	 *            The codes to set.
	 * @uml.property name="codes"
	 */
	public void setCodes(List<CodeDefinition> codeDefinitions);

}