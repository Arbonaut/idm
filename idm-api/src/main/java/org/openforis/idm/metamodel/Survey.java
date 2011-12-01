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
	 * Setter of the property <tt>name</tt>
	 * 
	 * @param name
	 *            The name to set.
	 * @uml.property name="name"
	 */
	public void setName(String name);

	/**
	 * @return Returns the projectNames.
	 * @uml.property name="projectNames"
	 */
	public List<LanguageSpecificText> getProjectNames();

	/**
	 * Setter of the property <tt>projectNames</tt>
	 * 
	 * @param projectNames
	 *            The projectNames to set.
	 * @uml.property name="projectNames"
	 */
	public void setProjectNames(List<LanguageSpecificText> projectNames);

	/**
	 * @return Returns the cycle.
	 * @uml.property name="cycle"
	 */
	public Integer getCycle();

	/**
	 * Setter of the property <tt>cycle</tt>
	 * 
	 * @param cycle
	 *            The cycle to set.
	 * @uml.property name="cycle"
	 */
	public void setCycle(Integer cycle);

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
	 * @return Returns the configuration.
	 * @uml.property name="configuration"
	 */
	public Element getConfiguration();

	/**
	 * Setter of the property <tt>configuration</tt>
	 * 
	 * @param configuration
	 *            The configuration to set.
	 * @uml.property name="configuration"
	 */
	public void setConfiguration(Element configuration);

	/**
	 * @return Returns the spatialReferenceSystems.
	 * @uml.property name="spatialReferenceSystems"
	 * @uml.associationEnd multiplicity="(0 -1)" aggregation="composite" inverse="survey:org.openforis.idm.metamodel.SpatialReferenceSystem"
	 */
	public Collection<SpatialReferenceSystem> getSpatialReferenceSystems();

	/**
	 * Setter of the property <tt>spatialReferenceSystems</tt>
	 * 
	 * @param spatialReferenceSystems
	 *            The spatialReferenceSystems to set.
	 * @uml.property name="spatialReferenceSystems"
	 */
	public void setSpatialReferenceSystems(Collection<SpatialReferenceSystem> spatialReferenceSystems);

	/**
	 * @return Returns the schema.
	 * @uml.property name="schema"
	 * @uml.associationEnd aggregation="composite" inverse="model:org.openforis.idm.metamodel.Schema"
	 */
	public Schema getSchema();

	/**
	 * Setter of the property <tt>schema</tt>
	 * 
	 * @param schema
	 *            The schema to set.
	 * @uml.property name="schema"
	 */
	public void setSchema(Schema schema);

	/**
	 * @return Returns the versions.
	 * @uml.property name="versions"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="model:org.openforis.idm.metamodel.ModelVersion"
	 */
	public List<ModelVersion> getVersions();

	/**
	 * Setter of the property <tt>versions</tt>
	 * 
	 * @param versions
	 *            The versions to set.
	 * @uml.property name="versions"
	 */
	public void setVersions(List<ModelVersion> versions);

	/**
	 * @return Returns the codeLists.
	 * @uml.property name="codeLists"
	 * @uml.associationEnd multiplicity="(0 -1)" aggregation="composite" inverse="model:org.openforis.idm.metamodel.CodeList"
	 */
	public List<CodeList> getCodeLists();

	/**
	 * Setter of the property <tt>codeLists</tt>
	 * 
	 * @param codeLists
	 *            The codeLists to set.
	 * @uml.property name="codeLists"
	 */
	public void setCodeLists(List<CodeList> codeLists);

	/**
	 * @return Returns the units.
	 * @uml.property name="units"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="model:org.openforis.idm.metamodel.Unit"
	 */
	public List<Unit> getUnits();

	/**
	 * Setter of the property <tt>units</tt>
	 * 
	 * @param units
	 *            The units to set.
	 * @uml.property name="units"
	 */
	public void setUnits(List<Unit> units);
}
