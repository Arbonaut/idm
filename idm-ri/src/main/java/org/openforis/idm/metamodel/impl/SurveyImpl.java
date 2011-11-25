package org.openforis.idm.metamodel.impl;

import java.util.Collection;

import org.openforis.idm.metamodel.Model;
import org.openforis.idm.metamodel.MultilingualStringMap;
import org.openforis.idm.metamodel.SpatialReferenceSystem;
import org.openforis.idm.metamodel.Survey;

/**
 * @author Mino Togna
 * 
 */
public class SurveyImpl implements Survey {

	private String name;
	private Model model;
	private MultilingualStringMap projectNames;
	private Integer cycle;
	private MultilingualStringMap descriptions;
	private String configuration;
	private Collection<SpatialReferenceSystem> spatialReferenceSystems;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public MultilingualStringMap getProjectNames() {
		return projectNames;
	}

	public void setProjectNames(MultilingualStringMap projectNames) {
		this.projectNames = projectNames;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public MultilingualStringMap getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(MultilingualStringMap descriptions) {
		this.descriptions = descriptions;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public Collection<SpatialReferenceSystem> getSpatialReferenceSystems() {
		return spatialReferenceSystems;
	}

	public void setSpatialReferenceSystems(Collection<SpatialReferenceSystem> spatialReferenceSystems) {
		this.spatialReferenceSystems = spatialReferenceSystems;
	}

}
