package org.openforis.idm.metamodel;

import java.util.List;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface CodeListItem extends Versionable {

	/**
	 * @return Returns the labels.
	 * @uml.property name="labels" readOnly="true"
	 */
	public List<LanguageSpecificText> getLabels();

	/**
	 * @return Returns the descriptions.
	 * @uml.property name="descriptions" readOnly="true"
	 */
	public List<LanguageSpecificText> getDescriptions();

	/**
	 * @return Returns the children.
	 * @uml.property name="children"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="item1:org.openforis.idm.metamodel.CodeListItem" readOnly="true"
	 */
	public List<CodeListItem> getChildren();

	/**
	 * @return Returns the qualifiable.
	 * @uml.property name="qualifiable" readOnly="true"
	 */
	public boolean isQualifiable();

	/**
	 * @return Returns the codes.
	 * @uml.property name="codes"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="codeListItem:org.openforis.idm.metamodel.CodeDefinition" readOnly="true"
	 */
	public List<CodeDefinition> getCodes();

}