package org.openforis.idm.metamodel.impl;

import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.SpatialReferenceSystem;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.Unit;
import org.w3c.dom.Element;

/**
 * 
 * @author M. Togna
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "projectNames", "cycle", "descriptions", "configuration", "versions", "codeLists", "units", "spatialReferenceSystems", "schema" })
@XmlRootElement(name = "survey")
public class SurveyImpl implements Survey {

	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "project", type = LanguageSpecificTextImpl.class)
	private List<LanguageSpecificText> projectNames;

	@XmlElement(name = "description", type = LanguageSpecificTextImpl.class)
	private List<LanguageSpecificText> descriptions;

	@XmlElement(name = "cycle")
	private Integer cycle;

	@XmlElement(name = "configuration")
	@XmlJavaTypeAdapter(value = ConfigurationTypeAdapter.class)
	private Element configuration;

	@XmlElement(name = "spatialReferenceSystem", type = SpatialReferenceSystemImpl.class)
	@XmlElementWrapper(name = "spatialReferenceSystems")
	private Collection<SpatialReferenceSystem> spatialReferenceSystems;

	@XmlElement(name = "schema", type = SchemaImpl.class)
	private Schema schema;

	@XmlElement(name = "version", type = ModelVersionImpl.class)
	@XmlElementWrapper(name = "versioning")
	private List<ModelVersion> versions;

	@XmlElement(name = "list", type = CodeListImpl.class)
	@XmlElementWrapper(name = "codeLists")
	private List<CodeList> codeLists;

	@XmlElement(name = "unit", type = UnitImpl.class)
	@XmlElementWrapper(name = "units")
	private List<Unit> units;

	@Override
	public Schema getSchema() {
		return this.schema;
	}

	@Override
	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	@Override
	public List<ModelVersion> getVersions() {
		return this.versions;
	}

	@Override
	public void setVersions(List<ModelVersion> versions) {
		this.versions = versions;
	}

	@Override
	public List<CodeList> getCodeLists() {
		return this.codeLists;
	}

	@Override
	public void setCodeLists(List<CodeList> codeLists) {
		this.codeLists = codeLists;
	}

	@Override
	public List<Unit> getUnits() {
		return this.units;
	}

	@Override
	public void setUnits(List<Unit> units) {
		this.units = units;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
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
	public Element getConfiguration() {
		return this.configuration;
	}

	@Override
	public void setConfiguration(Element configuration) {
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

	private static class ConfigurationTypeAdapter extends XmlAdapter<Object, Object> {

		@Override
		public Object unmarshal(Object v) {
			return v;
		}

		@Override
		public Object marshal(Object v) throws Exception {
			return v;
		}

	}
}