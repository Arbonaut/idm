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

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class Record {

	private Integer id;
	private Survey survey;
	private ModelVersion modelVersion;
	private Map<Integer, Node<? extends NodeDefinition>> nodesById;
	private int nextId;
	private Entity rootEntity;
	private RecordContext context;
	
	public Record(RecordContext context, Survey survey, String version) {
		if ( context == null ) {
			throw new IllegalArgumentException("Invalid context '"+context+'"');
		}
		this.context = context;
		this.survey = survey;
		this.modelVersion = survey.getVersion(version);
		if ( modelVersion == null ) {
			throw new IllegalArgumentException("Invalid version '"+version+'"');
		}
		this.nodesById = new HashMap<Integer, Node<? extends NodeDefinition>>();
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
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RecordContext getContext() {
		return context;
	}
	
	public Survey getSurvey() {
		return this.survey;
	}

	public Entity getRootEntity() {
		return this.rootEntity;
	}

	// TODO Need path?
//	
//	public void setRootEntity(Entity rootEntity) {
//		Entity entityImpl = (Entity) rootEntity;
//		this.rootEntity = entityImpl;
//		entityImpl.setRecord(this);
//		entityImpl.setPath("/" + rootEntity.getDefinition().getName());
//	}

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
//	
//	public void setVersion(ModelVersion modelVersion) {
//		this.modelVersion = modelVersion;
//	}
	public Node<? extends NodeDefinition> getNodeById(int id) {
		return this.nodesById.get(id);
	}
	
	protected void put(Node<? extends NodeDefinition> node){
		this.nodesById.put(node.getId(), node);
	}

	protected int nextId() {
		return nextId++;
	}
}
