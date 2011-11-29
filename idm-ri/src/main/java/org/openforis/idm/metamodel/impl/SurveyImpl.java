package org.openforis.idm.metamodel.impl;

import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.Model;
import org.openforis.idm.metamodel.SpatialReferenceSystem;
import org.openforis.idm.metamodel.Survey;

/**
 * 
 * @author M. Togna
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "projectNames", "cycle", "descriptions", "model", "spatialReferenceSystems", "configuration" })
@XmlRootElement(name = "survey")
public class SurveyImpl implements Survey {

	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "project", type = LanguageSpecificTextImpl.class)
	private List<LanguageSpecificText> projectNames;

	@XmlElement(name = "description", type = LanguageSpecificTextImpl.class)
	private List<LanguageSpecificText> descriptions;

	@XmlElement(type = ModelImpl.class)
	private Model model;

	@XmlElement(name = "cycle")
	private Integer cycle;

	@XmlElement(name = "configuration")
	@XmlJavaTypeAdapter(value = ConfigurationTypeAdapter.class)
	private String configuration;

	@XmlElement(name = "spatialReferenceSystem", type = SpatialReferenceSystemImpl.class)
	@XmlElementWrapper(name = "spatialReferenceSystems")
	private Collection<SpatialReferenceSystem> spatialReferenceSystems;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Model getModel() {
		return this.model;
	}

	@Override
	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public Integer getCycle() {
		return this.cycle;
	}

	@Override
	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	@Override
	public List<LanguageSpecificText> getDescriptions() {
		return this.descriptions;
	}

	@Override
	public void setDescriptions(List<LanguageSpecificText> descriptions) {
		this.descriptions = descriptions;
	}

	@Override
	public String getConfiguration() {
		return this.configuration;
	}

	@Override
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	@Override
	public Collection<SpatialReferenceSystem> getSpatialReferenceSystems() {
		return this.spatialReferenceSystems;
	}

	@Override
	public void setSpatialReferenceSystems(Collection<SpatialReferenceSystem> spatialReferenceSystems) {
		this.spatialReferenceSystems = spatialReferenceSystems;
	}

	@Override
	public List<LanguageSpecificText> getProjectNames() {
		return this.projectNames;
	}

	@Override
	public void setProjectNames(List<LanguageSpecificText> projectNames) {
		this.projectNames = projectNames;
	}

}