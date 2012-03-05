package org.openforis.idm.metamodel;

import java.io.Serializable;
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

import org.openforis.idm.metamodel.validation.Check;
import org.openforis.idm.metamodel.validation.ComparisonCheck;
import org.openforis.idm.metamodel.validation.CustomCheck;
import org.openforis.idm.metamodel.validation.DistanceCheck;
import org.openforis.idm.metamodel.validation.UniquenessCheck;
import org.openforis.idm.metamodel.xml.internal.ConfigurationXmlAdapter;
import org.openforis.idm.model.NodePathPointer;


/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "projectNames", "cycle", "descriptions", "configuration", "modelVersions",
		"codeLists", "units", "spatialReferenceSystems", "schema" })
@XmlRootElement(name = "survey")
public class Survey implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private String name;
	
	@XmlTransient
	private Integer id;
	
	@XmlElement(name = "project", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> projectNames;

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
	private StateDependencyMap relevanceDependencies;
	
	@XmlTransient
	private StateDependencyMap requiredDependencies;
	
	@XmlTransient
	private StateDependencyMap checkDependencies;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LanguageSpecificText> getProjectNames() {
		return Collections.unmodifiableList(this.projectNames);
	}

	public Integer getCycle() {
		return this.cycle;
	}

	public List<LanguageSpecificText> getDescriptions() {
		return Collections.unmodifiableList(this.descriptions);
	}

	public List<ModelVersion> getVersions() {
		return Collections.unmodifiableList(this.modelVersions);
	}

	public List<CodeList> getCodeLists() {
		return Collections.unmodifiableList(this.codeLists);
	}

	public List<Unit> getUnits() {
		return Collections.unmodifiableList(this.units);
	}

	public List<SpatialReferenceSystem> getSpatialReferenceSystems() {
		return Collections.unmodifiableList(this.spatialReferenceSystems);
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
			return Collections.unmodifiableList(configuration.list);
		}
	}
	
	public SurveyContext getContext() {
		return surveyContext;
	}
	
	public void setSurveyContext(SurveyContext surveyContext) {
		this.surveyContext = surveyContext;
	}
	
	public Set<NodePathPointer> getCheckDependencies(NodeDefinition definition) {
		if(checkDependencies == null){
			initDependencies();
		}
		return checkDependencies.getDependencySet(definition.getPath());
	}
	
	public Set<NodePathPointer> getRelevanceDependencies(NodeDefinition definition) {
		if(relevanceDependencies == null){
			initDependencies();
		}
		return relevanceDependencies.getDependencySet(definition.getPath());
	}
	
	public Set<NodePathPointer> getRequiredDependencies(NodeDefinition definition) {
		if(requiredDependencies == null){
			initDependencies();
		}
		return requiredDependencies.getDependencySet(definition.getPath());
	}
	
	private void initDependencies() {
		requiredDependencies = new StateDependencyMap(surveyContext.getExpressionFactory());
		relevanceDependencies = new StateDependencyMap(surveyContext.getExpressionFactory());
		checkDependencies = new StateDependencyMap(surveyContext.getExpressionFactory());
		
		List<EntityDefinition> rootEntityDefinitions = schema.getRootEntityDefinitions();
		for (EntityDefinition rootDefn : rootEntityDefinitions) {
			registerDependencies(rootDefn);
		}
	}
	
	private void registerDependencies(EntityDefinition entityDefinition) {
		List<NodeDefinition> childDefinitions = entityDefinition.getChildDefinitions();
		for (NodeDefinition nodeDefinition : childDefinitions) {
			relevanceDependencies.registerDependencies(nodeDefinition, nodeDefinition.getRelevantExpression());
			requiredDependencies.registerDependencies(nodeDefinition, nodeDefinition.getRequiredExpression());

			if (nodeDefinition instanceof AttributeDefinition) {
				registerDependencies((AttributeDefinition) nodeDefinition);
			} else {
				registerDependencies((EntityDefinition) nodeDefinition);
			}
		}
	}
	
	private void registerDependencies(AttributeDefinition attributeDefinition) {
		List<Check<?>> checks = attributeDefinition.getChecks();
		for (Check<?> check : checks) {
			checkDependencies.registerDependencies(attributeDefinition, check.getCondition());
			if (check instanceof ComparisonCheck) {
				checkDependencies.registerDependencies(attributeDefinition, ((ComparisonCheck) check).getEqualsExpression());
				checkDependencies.registerDependencies(attributeDefinition, ((ComparisonCheck) check).getLessThanExpression());
				checkDependencies.registerDependencies(attributeDefinition, ((ComparisonCheck) check).getLessThanOrEqualsExpression());
				checkDependencies.registerDependencies(attributeDefinition, ((ComparisonCheck) check).getGreaterThanExpression());
				checkDependencies.registerDependencies(attributeDefinition, ((ComparisonCheck) check).getGreaterThanOrEqualsExpression());
			} else if (check instanceof CustomCheck) {
				checkDependencies.registerDependencies(attributeDefinition, ((CustomCheck) check).getExpression());
			} else if (check instanceof DistanceCheck) {
				checkDependencies.registerDependencies(attributeDefinition, ((DistanceCheck) check).getDestinationPointExpression());
				checkDependencies.registerDependencies(attributeDefinition, ((DistanceCheck) check).getMaxDistanceExpression());
				checkDependencies.registerDependencies(attributeDefinition, ((DistanceCheck) check).getMinDistanceExpression());
				checkDependencies.registerDependencies(attributeDefinition, ((DistanceCheck) check).getSourcePointExpression());
			} else if (check instanceof UniquenessCheck) {
				checkDependencies.registerDependencies(attributeDefinition, ((UniquenessCheck) check).getExpression());
			}
		}
	}

	/**
	 * Workaround for JAXB since @XmlAnyElement, @XmlElementWrapper and @XmlJavaTypeAdapter 
	 * wouldn't play nice together
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType
	private static class ConfigurationWrapper {
		@XmlAnyElement
		@XmlJavaTypeAdapter(ConfigurationXmlAdapter.class)
		List<Configuration> list;
	}
}