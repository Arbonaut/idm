package org.openforis.idm.metamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.xml.internal.ConfigurationXmlAdapter;
import org.openforis.idm.model.NodePathPointer;
import org.openforis.idm.util.CollectionUtil;


/**
 * @author G. Miceli
 * @author M. Togna
 * @author S. Ricci
 * @author E. Suprapto Wibowo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "projectNames", "uri", "cycle", "descriptions", "configuration", "modelVersions",
		"codeLists", "units", "spatialReferenceSystems", "schema" })
@XmlRootElement(name = "survey")
public class Survey implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private Integer id;
	
	@XmlTransient
	private String name;
	
	@XmlElement(name = "project", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> projectNames;
	
	@XmlElement(name = "uri")
	private String uri;

	@XmlElement(name = "cycle")
	private Integer cycle;

	@XmlElement(name = "description", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> descriptions;

	@XmlElement(name = "configuration")
	private ConfigurationWrapper configuration;
	
	@XmlElement(name = "version", type = ModelVersion.class)
	@XmlElementWrapper(name = "versioning")
	private List<ModelVersion> modelVersions;

	@XmlElement(name = "list", type = CodeList.class)
	@XmlElementWrapper(name = "codeLists")
	private List<CodeList> codeLists;

	@XmlElement(name = "unit", type = Unit.class)
	@XmlElementWrapper(name = "units")
	private List<Unit> units;

	@XmlElement(name = "spatialReferenceSystem", type = SpatialReferenceSystem.class)
	@XmlElementWrapper(name = "spatialReferenceSystems")
	private List<SpatialReferenceSystem> spatialReferenceSystems;

	@XmlElement(name = "schema", type = Schema.class)
	private Schema schema;

	@XmlTransient
	private SurveyContext surveyContext;
	
	@XmlTransient
	private SurveyDependencies surveyDependencies;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LanguageSpecificText> getProjectNames() {
		return CollectionUtil.unmodifiableList(this.projectNames);
	}
	
	public String getProjectName(String language) {
		if (projectNames != null ) {
			return LanguageSpecificText.getText(projectNames, language);
		} else {
			return null;
		}
	}
	
	public void setProjectName(String language, String name) {
		if ( projectNames == null ) {
			projectNames = new ArrayList<LanguageSpecificText>();
		}
		LanguageSpecificText.setText(projectNames, language, name);
	}
	
	public void removeProjectName(String language) {
		if ( projectNames != null ) {
			LanguageSpecificText.remove(projectNames, language);
		}
	}
	
	public Integer getCycle() {
		return this.cycle;
	}

	public List<LanguageSpecificText> getDescriptions() {
		return Collections.unmodifiableList(this.descriptions);
	}

	public String getDescription(String language) {
		if (descriptions != null ) {
			return LanguageSpecificText.getText(descriptions, language);
		} else {
			return null;
		}
	}
	
	public void setDescription(String language, String description) {
		if ( descriptions == null ) {
			descriptions = new ArrayList<LanguageSpecificText>();
		}
		LanguageSpecificText.setText(descriptions, language, description);
	}
	
	public void removeDescription(String language) {
		LanguageSpecificText.remove(descriptions, language);
	}

	public List<ModelVersion> getVersions() {
		return CollectionUtil.unmodifiableList(this.modelVersions);
	}
	
	public void addVersion(ModelVersion version) {
		if ( modelVersions == null ) {
			modelVersions = new ArrayList<ModelVersion>();
		}
		modelVersions.add(version);
	}
	
	public void removeVersion(ModelVersion version) {
		if ( modelVersions != null ) {
			ModelVersion oldVersion = getVersionById(version.getId());
			modelVersions.remove(oldVersion);
		}
	}
	
	public void moveVersion(ModelVersion version, int index) {
		modelVersions.remove(version);
		modelVersions.add(index, version);
	}
	
	public void updateVersion(ModelVersion version) {
		ModelVersion oldVersion = getVersionById(version.getId());
		int index = modelVersions.indexOf(oldVersion);
		modelVersions.set(index, version);
	}

	protected ModelVersion getVersionById(int id) {
		for (ModelVersion v : modelVersions) {
			if (id == v.getId() ) {
				return v;
			}
		}
		return null;
	}

	public List<CodeList> getCodeLists() {
		return CollectionUtil.unmodifiableList(this.codeLists);
	}
	
	public void addCodeList(CodeList codeList) {
		if ( codeLists == null ) {
			codeLists = new ArrayList<CodeList>();
		}
		codeLists.add(codeList);
	}
	
	public void removeCodeList(CodeList codeList) {
		if ( codeLists != null ) {
			CodeList oldCodeList = getCodeListById(codeList.getId());
			codeLists.remove(oldCodeList);
		}
	}
	
	public void moveCodeList(CodeList codeList, int index) {
		codeLists.remove(codeList);
		codeLists.add(index, codeList);
	}
	
	public void updateCodeList(CodeList codeList) {
		CodeList oldCodeList = getCodeListById(codeList.getId());
		int index = codeLists.indexOf(oldCodeList);
		codeLists.set(index, codeList);
	}

	protected CodeList getCodeListById(int id) {
		for (CodeList c : codeLists) {
			if ( id == c.getId() ) {
				return c;
			}
		}
		return null;
	}

	public List<Unit> getUnits() {
		return CollectionUtil.unmodifiableList(this.units);
	}
	
	public void addUnit(Unit unit) {
		if ( units != null ) {
			units = new ArrayList<Unit>();
		}
		units.add(unit);
	}
	
	public void removeUnit(Unit unit) {
		if ( units != null ) {
			Unit oldUnit = getUnitById(unit.getId());
			units.remove(oldUnit);
		}
	}
	
	public void moveUnit(Unit unit, int index) {
		units.remove(unit);
		units.add(index, unit);
	}
	
	public void updateUnit(Unit unit) {
		Unit oldUnit = getUnitById(unit.getId());
		int index = units.indexOf(oldUnit);
		units.set(index, unit);
	}

	protected Unit getUnitById(int id) {
		for (Unit i : units) {
			if ( id == i.getId() ) {
				return i;
			}
		}
		return null;
	}

	public List<SpatialReferenceSystem> getSpatialReferenceSystems() {
		return CollectionUtil.unmodifiableList(this.spatialReferenceSystems);
	}
	
	public void addSpatialReferenceSystem(SpatialReferenceSystem unit) {
		spatialReferenceSystems.add(unit);
	}
	
	public void removeSpatialReferenceSystem(SpatialReferenceSystem unit) {
		spatialReferenceSystems.remove(unit);
	}
	
	public void moveSpatialReferenceSystem(SpatialReferenceSystem unit, int index) {
		spatialReferenceSystems.remove(unit);
		spatialReferenceSystems.add(index, unit);
	}
	
	public void updateSpatialReferenceSystem(SpatialReferenceSystem unit) {
		SpatialReferenceSystem oldSpatialReferenceSystem = getSpatialReferenceSystemById(unit.getId());
		int index = spatialReferenceSystems.indexOf(oldSpatialReferenceSystem);
		spatialReferenceSystems.set(index, unit);
	}

	protected SpatialReferenceSystem getSpatialReferenceSystemById(String id) {
		for (SpatialReferenceSystem i : spatialReferenceSystems) {
			if ( id.equals(i.getId()) ) {
				return i;
			}
		}
		return null;
	}

	public Schema getSchema() {
		return this.schema;
	}
	
	public ModelVersion getVersion(String name) {
		if ( modelVersions != null ) {
			for (ModelVersion v : modelVersions) {
				if ( name.equals(v.getName()) ) {
					return v;
				}
			}
		}
		return null;
	}

	public CodeList getCodeList(String name) {
		for (CodeList codeList : codeLists) {
			if (codeList.getName().equals(name)) {
				return codeList;
			}
		}
		return null;
	}

	public Unit getUnit(String name) {
		for (Unit unit : units) {
			if (unit.getName().equals(name)) {
				return unit;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Configuration> getConfiguration() {
		if ( configuration == null ) {
			return (List<Configuration>) Collections.EMPTY_LIST;
		} else {
			return CollectionUtil.unmodifiableList(configuration.list);
		}
	}
	
	public SurveyContext getContext() {
		return surveyContext;
	}
	
	public void setSurveyContext(SurveyContext surveyContext) {
		this.surveyContext = surveyContext;
	}
	
	public Set<NodePathPointer> getCheckDependencies(NodeDefinition definition) {
		return getSurveyDependencies().getCheckDependencies(definition);
	}
	
	public Set<NodePathPointer> getRelevanceDependencies(NodeDefinition definition) {
		return getSurveyDependencies().getRelevanceDependencies(definition);
	}
	
	public Set<NodePathPointer> getRequiredDependencies(NodeDefinition definition) {
		return getSurveyDependencies().getRequiredDependencies(definition);
	}
	
	private SurveyDependencies getSurveyDependencies() {
		if(surveyDependencies == null){
			surveyDependencies = new SurveyDependencies(this);
		}
		return surveyDependencies;
	}
	
	/**
	 * Workaround for JAXB since @XmlAnyElement, @XmlElementWrapper and @XmlJavaTypeAdapter 
	 * wouldn't play nice together
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType
	private static class ConfigurationWrapper implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@XmlAnyElement
		@XmlJavaTypeAdapter(ConfigurationXmlAdapter.class)
		List<Configuration> list;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((list == null) ? 0 : list.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ConfigurationWrapper other = (ConfigurationWrapper) obj;
			if (list == null) {
				if (other.list != null)
					return false;
			} else if (!list.equals(other.list))
				return false;
			return true;
		}
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeLists == null) ? 0 : codeLists.hashCode());
		result = prime * result + ((configuration == null) ? 0 : configuration.hashCode());
		result = prime * result + ((cycle == null) ? 0 : cycle.hashCode());
		result = prime * result + ((descriptions == null) ? 0 : descriptions.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modelVersions == null) ? 0 : modelVersions.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((projectNames == null) ? 0 : projectNames.hashCode());
		result = prime * result + ((schema == null) ? 0 : schema.hashCode());
		result = prime * result + ((spatialReferenceSystems == null) ? 0 : spatialReferenceSystems.hashCode());
		result = prime * result + ((units == null) ? 0 : units.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Survey other = (Survey) obj;
		if (codeLists == null) {
			if (other.codeLists != null)
				return false;
		} else if (!codeLists.equals(other.codeLists))
			return false;
		if (configuration == null) {
			if (other.configuration != null)
				return false;
		} else if (!configuration.equals(other.configuration))
			return false;
		if (cycle == null) {
			if (other.cycle != null)
				return false;
		} else if (!cycle.equals(other.cycle))
			return false;
		if (descriptions == null) {
			if (other.descriptions != null)
				return false;
		} else if (!descriptions.equals(other.descriptions))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modelVersions == null) {
			if (other.modelVersions != null)
				return false;
		} else if (!modelVersions.equals(other.modelVersions))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (projectNames == null) {
			if (other.projectNames != null)
				return false;
		} else if (!projectNames.equals(other.projectNames))
			return false;
		if (schema == null) {
			if (other.schema != null)
				return false;
		} else if (!schema.equals(other.schema))
			return false;
		if (spatialReferenceSystems == null) {
			if (other.spatialReferenceSystems != null)
				return false;
		} else if (!spatialReferenceSystems.equals(other.spatialReferenceSystems))
			return false;
		if (units == null) {
			if (other.units != null)
				return false;
		} else if (!units.equals(other.units))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
}