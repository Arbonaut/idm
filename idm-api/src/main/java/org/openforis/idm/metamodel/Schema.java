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
	private Map<String, SchemaObjectDefinition> definitionsByPath;

	@XmlTransient
	private Map<Integer, SchemaObjectDefinition> definitionsById;
	
	@XmlTransient
	private Survey survey;
	
	public Schema() {
		definitionsByPath = new HashMap<String, SchemaObjectDefinition>(); 
		definitionsById = new HashMap<Integer, SchemaObjectDefinition>(); 
	}
	
	public Survey getSurvey() {
		return survey;
	}

	protected void setSurvey(Survey survey) {
		this.survey = survey;
	}
	
	public SchemaObjectDefinition getByPath(String absolutePath) {
		return definitionsByPath.get(absolutePath);
	}
	
	public SchemaObjectDefinition getById(int id) {
		return definitionsById.get(id);
	}
	
	protected void indexByPath(SchemaObjectDefinition definition) {
		String path = definition.getPath();
		definitionsByPath.put(path, definition);
	}

	protected void indexById(SchemaObjectDefinition definition) {
		Integer id = definition.getId();
		if ( id != null ) {
			definitionsById.put(id, definition);
		}
	}

	public Set<String> getDefinedPaths() {
		return Collections.unmodifiableSet(definitionsByPath.keySet());
	}

	public Collection<SchemaObjectDefinition> getDefinitions() {
		return Collections.unmodifiableCollection(definitionsByPath.values());
	}
	
	public List<EntityDefinition> getRootEntityDefinitions() {
		return Collections.unmodifiableList(this.rootEntityDefinitions);
	}

	public EntityDefinition getRootEntityDefinition(String name) {
		return (EntityDefinition) getByPath("/"+name);
	}
}
