/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "rootEntityDefinitions" })
public class Schema  {
	
	@XmlElement(name = "entity", type = EntityDefinition.class)
	private List<EntityDefinition> rootEntityDefinitions;

	@XmlTransient
	private Map<String, NodeDefinition> definitionsByPath;

	@XmlTransient
	private Map<Integer, NodeDefinition> definitionsById;
	
	@XmlTransient
	private Survey survey;
	
	public Schema() {
		definitionsByPath = new HashMap<String, NodeDefinition>(); 
		definitionsById = new HashMap<Integer, NodeDefinition>(); 
	}
	
	public Survey getSurvey() {
		return survey;
	}

	protected void setSurvey(Survey survey) {
		this.survey = survey;
	}
	
	public NodeDefinition getByPath(String absolutePath) {
		return definitionsByPath.get(absolutePath);
	}
	
	public NodeDefinition getById(int id) {
		return definitionsById.get(id);
	}
	
	protected void indexByPath(NodeDefinition definition) {
		String path = definition.getPath();
		definitionsByPath.put(path, definition);
	}

	protected void indexById(NodeDefinition definition) {
		Integer id = definition.getId();
		if ( id != null ) {
			definitionsById.put(id, definition);
		}
	}

	public Set<String> getDefinedPaths() {
		return Collections.unmodifiableSet(definitionsByPath.keySet());
	}

	public Collection<NodeDefinition> getDefinitions() {
		return Collections.unmodifiableCollection(definitionsByPath.values());
	}
	
	public List<EntityDefinition> getRootEntityDefinitions() {
		return Collections.unmodifiableList(this.rootEntityDefinitions);
	}

	public EntityDefinition getRootEntityDefinition(String name) {
		return (EntityDefinition) getByPath("/"+name);
	}
}
