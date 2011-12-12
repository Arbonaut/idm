package org.openforis.idm.metamodel;

import java.util.List;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CodeList extends Versionable {

	/**
	 * @return Returns the name.
	 * @uml.property name="name" readOnly="true"
	 */
	public String getName();

	/**
	 * @return Returns the labels.
	 * @uml.property name="labels" readOnly="true"
	 */
	public List<CodeListLabel> getLabels();

	/**
	 * @return Returns the descriptions.
	 * @uml.property name="descriptions" readOnly="true"
	 */
	public List<LanguageSpecificText> getDescriptions();

	/**
	 * @return Returns the hierarchy.
	 * @uml.property name="hierarchy"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="codeList:org.openforis.idm.metamodel.CodeListRank" readOnly="true"
	 */
	public List<CodeListLevel> getHierarchy();

	/**
	 * @return Returns the codingSchemes.
	 * @uml.property name="codingSchemes"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="codeList:org.openforis.idm.metamodel.CodingScheme" readOnly="true"
	 */
	public List<CodingScheme> getCodingSchemes();

	/**
	 * @return Returns the items.
	 * @uml.property name="items"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="codeList:org.openforis.idm.metamodel.CodeListItem" readOnly="true"
	 */
	public List<CodeListItem> getItems();

}
