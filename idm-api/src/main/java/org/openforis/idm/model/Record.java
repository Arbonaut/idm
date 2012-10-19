/**
 * 
 */
package org.openforis.idm.model;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class Record {
	
	private Map<Integer, Node<? extends NodeDefinition>> nodesByInternalId;
	private Survey survey;
	
	private Integer id;
	private ModelVersion modelVersion;
	private int nextId;
	private Entity rootEntity;

	public Record(Survey survey, String version) {
		if ( survey == null ) {
			throw new IllegalArgumentException("Survey required");
		}
		this.survey = survey;
		this.nodesByInternalId = new HashMap<Integer, Node<? extends NodeDefinition>>();
		this.survey = survey;
		this.modelVersion = survey.getVersion(version);
		if ( modelVersion == null ) {
			throw new IllegalArgumentException("Invalid version '"+version+'"');
		}
		this.nextId = 0;
	}

	public Entity createRootEntity(String name) {
		if ( rootEntity != null ) {
			throw new IllegalStateException("Record already has an associated root entity");
		}
		Schema schema = survey.getSchema();
		EntityDefinition rootEntityDefinition = schema.getRootEntityDefinition(name);
		if ( rootEntityDefinition == null ) {
			throw new IllegalArgumentException("Invalid root entity '"+rootEntity+'"');			
		}
		rootEntity = new Entity(rootEntityDefinition);
		rootEntity.setRecord(this);
		return rootEntity;
	}
	

	public Entity createRootEntity(int id) {
		if ( rootEntity != null ) {
			throw new IllegalStateException("Record already has an associated root entity");
		}
		Schema schema = survey.getSchema();
		NodeDefinition def = schema.getDefinitionById(id);
		if ( def == null || !(def instanceof EntityDefinition) || def.getParentDefinition() != null) {
			throw new IllegalArgumentException("Invalid root entity id");			
		}
		rootEntity = new Entity((EntityDefinition) def);
		rootEntity.setRecord(this);
		return rootEntity;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SurveyContext getSurveyContext() {
		return survey.getContext();
	}
	
	public Survey getSurvey() {
		return this.survey;
	}

	public Entity getRootEntity() {
		return this.rootEntity;
	}

	public void setRootEntity(Entity entity) {
		if ( entity != null ) {
			Schema schema = survey.getSchema();
			EntityDefinition currentEntityDefinition = entity.getDefinition();
			EntityDefinition rootEntityDefinition = schema.getRootEntityDefinition(entity.getName());
			if ( rootEntityDefinition == null || rootEntityDefinition != currentEntityDefinition ) {
				throw new IllegalArgumentException("Invalid root entity");
			}
			this.nodesByInternalId = new HashMap<Integer, Node<? extends NodeDefinition>>();
			entity.setRecord(this);
		}
		this.rootEntity = entity;
	}
	
	public ModelVersion getVersion() {
		return this.modelVersion;
	}

	@Override
	public String toString() {
		StringWriter sw = new StringWriter();
		sw.append("id: ").append(String.valueOf(id)).append("\n");
		rootEntity.write(sw, 0);
		return sw.toString();
	}

	public Node<? extends NodeDefinition> getNodeByInternalId(int id) {
		return this.nodesByInternalId.get(id);
	}
	
	void put(Node<? extends NodeDefinition> node){
		this.nodesByInternalId.put(node.getInternalId(), node);
	}

	int nextId() {
		return nextId++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modelVersion == null) ? 0 : modelVersion.hashCode());
		result = prime * result + ((rootEntity == null) ? 0 : rootEntity.hashCode());
		result = prime * result + ((survey == null) ? 0 : survey.hashCode());
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
		Record other = (Record) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modelVersion == null) {
			if (other.modelVersion != null)
				return false;
		} else if (!modelVersion.equals(other.modelVersion))
			return false;
		if (rootEntity == null) {
			if (other.rootEntity != null)
				return false;
		} else if (!rootEntity.equals(other.rootEntity))
			return false;
		if (survey == null) {
			if (other.survey != null)
				return false;
		} else if (!survey.equals(other.survey))
			return false;
		return true;
	}
	
	
	
}
