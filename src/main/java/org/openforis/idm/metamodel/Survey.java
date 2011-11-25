package org.openforis.idm.metamodel;

import java.util.Collection;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Survey {

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
	 * @return  Returns the model.
	 * @uml.property  name="model"
	 * @uml.associationEnd  aggregation="composite" inverse="survey:org.openforis.idm.metamodel.Model"
	 */
	public Model getModel();

	/**
	 * Setter of the property <tt>model</tt>
	 * @param model  The model to set.
	 * @uml.property  name="model"
	 */
	public void setModel(Model model);

	/**
	 * @return  Returns the projectNames.
	 * @uml.property  name="projectNames"
	 */
	public MultilingualStringMap getProjectNames();

	/**
	 * Setter of the property <tt>projectNames</tt>
	 * @param projectNames  The projectNames to set.
	 * @uml.property  name="projectNames"
	 */
	public void setProjectNames(MultilingualStringMap projectNames);

	/**
	 * @return  Returns the cycle.
	 * @uml.property  name="cycle"
	 */
	public Integer getCycle();

	/**
	 * Setter of the property <tt>cycle</tt>
	 * @param cycle  The cycle to set.
	 * @uml.property  name="cycle"
	 */
	public void setCycle(Integer cycle);

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

	/**
	 * @return  Returns the configuration.
	 * @uml.property  name="configuration"
	 */
	public String getConfiguration();

	/**
	 * Setter of the property <tt>configuration</tt>
	 * @param configuration  The configuration to set.
	 * @uml.property  name="configuration"
	 */
	public void setConfiguration(String configuration);

	/**
	 * @return  Returns the spatialReferenceSystems.
	 * @uml.property  name="spatialReferenceSystems"
	 * @uml.associationEnd  multiplicity="(0 -1)" aggregation="composite" inverse="survey:org.openforis.idm.metamodel.SpatialReferenceSystem"
	 */
	public Collection<SpatialReferenceSystem> getSpatialReferenceSystems();

	/**
	 * Setter of the property <tt>spatialReferenceSystems</tt>
	 * @param spatialReferenceSystems  The spatialReferenceSystems to set.
	 * @uml.property  name="spatialReferenceSystems"
	 */
	public void setSpatialReferenceSystems(
			Collection<SpatialReferenceSystem> spatialReferenceSystems);
}
