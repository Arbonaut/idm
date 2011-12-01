package org.openforis.idm.metamodel;

import java.util.List;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CodeList extends Versionable {

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
	 * @return Returns the labels.
	 * @uml.property name="labels"
	 */
	public List<CodeListLabel> getLabels();

	/**
	 * Setter of the property <tt>labels</tt>
	 * 
	 * @param labels
	 *            The labels to set.
	 * @uml.property name="labels"
	 */
	public void setLabels(List<CodeListLabel> labels);

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
	 * @return Returns the hierarchy.
	 * @uml.property name="hierarchy"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="codeList:org.openforis.idm.metamodel.CodeListRank"
	 */
	public List<CodeListLevel> getHierarchy();

	/**
	 * Setter of the property <tt>hierarchy</tt>
	 * 
	 * @param hierarchy
	 *            The hierarchy to set.
	 * @uml.property name="hierarchy"
	 */
	public void setHierarchy(List<CodeListLevel> hierarchy);

	/**
	 * @return Returns the codingSchemes.
	 * @uml.property name="codingSchemes"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="codeList:org.openforis.idm.metamodel.CodingScheme"
	 */
	public List<CodingScheme> getCodingSchemes();

	/**
	 * Setter of the property <tt>codingSchemes</tt>
	 * 
	 * @param codingSchemes
	 *            The codingSchemes to set.
	 * @uml.property name="codingSchemes"
	 */
	public void setCodingSchemes(List<CodingScheme> codingSchemes);

	/**
	 * @return Returns the items.
	 * @uml.property name="items"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="codeList:org.openforis.idm.metamodel.CodeListItem"
	 */
	public List<CodeListItem> getItems();

	/**
	 * Setter of the property <tt>items</tt>
	 * 
	 * @param items
	 *            The items to set.
	 * @uml.property name="items"
	 */
	public void setItems(List<CodeListItem> items);

}
