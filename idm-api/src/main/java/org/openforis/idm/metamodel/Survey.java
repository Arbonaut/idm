package org.openforis.idm.metamodel;

import java.util.Collection;
import java.util.List;

import org.w3c.dom.Element;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Survey {

	/**
	 * @return Returns the name.
	 * @uml.property name="name"
	 */
	public String getName();

	/**
	 * @return Returns the projectNames.
	 * @uml.property name="projectNames"
	 */
	public List<LanguageSpecificText> getProjectNames();

	/**
	 * @return Returns the cycle.
	 * @uml.property name="cycle"
	 */
	public Integer getCycle();

	/**
	 * @return Returns the descriptions.
	 * @uml.property name="descriptions"
	 */
	public List<LanguageSpecificText> getDescriptions();

	/**
	 * @return Returns the configuration.
	 * @uml.property name="configuration"
	 */
	public Element getConfiguration();

	/**
	 * @return Returns the spatialReferenceSystems.
	 * @uml.property name="spatialReferenceSystems"
	 * @uml.associationEnd multiplicity="(0 -1)" aggregation="composite" inverse="survey:org.openforis.idm.metamodel.SpatialReferenceSystem"
	 */
	public Collection<SpatialReferenceSystem> getSpatialReferenceSystems();

	/**
	 * @return Returns the schema.
	 * @uml.property name="schema"
	 * @uml.associationEnd aggregation="composite" inverse="model:org.openforis.idm.metamodel.Schema"
	 */
	public Schema getSchema();

	/**
	 * @return Returns the versions.
	 * @uml.property name="versions"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="model:org.openforis.idm.metamodel.ModelVersion"
	 */
	public List<ModelVersion> getVersions();

	/**
	 * @return Returns the codeLists.
	 * @uml.property name="codeLists"
	 * @uml.associationEnd multiplicity="(0 -1)" aggregation="composite" inverse="model:org.openforis.idm.metamodel.CodeList"
	 */
	public List<CodeList> getCodeLists();

	/**
	 * @return Returns the units.
	 * @uml.property name="units"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="model:org.openforis.idm.metamodel.Unit"
	 */
	public List<Unit> getUnits();
}
