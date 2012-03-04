/**
 * 
 */
package org.openforis.idm.model;


import java.io.Serializable;
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
public class Record implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private transient Map<Integer, Node<? extends NodeDefinition>> nodesByInternalId;
	private transient RecordContext context;
	private transient Survey survey;
	
	private Integer id;
	private ModelVersion modelVersion;
	private int nextId;
	private Entity rootEntity;

	protected Record() {
	}
	
	public Record(RecordContext context, Survey survey, String version) {
		init(context, survey);
		this.modelVersion = survey.getVersion(version);
		if ( modelVersion == null ) {
			throw new IllegalArgumentException("Invalid version '"+version+'"');
		}
		this.nextId = 0;
	}

	public void init(RecordContext context, Survey survey) {
		if ( context == null ) {
			throw new IllegalArgumentException("Context required");
		}
		if ( survey == null ) {
			throw new IllegalArgumentException("Survey required");
		}
		this.context = context;
		this.survey = survey;
		this.nodesByInternalId = new HashMap<Integer, Node<? extends NodeDefinition>>();
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
		NodeDefinition def = schema.getById(id);
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
	public Node<? extends NodeDefinition> getNodeByInternalId(int id) {
		return this.nodesByInternalId.get(id);
	}
	
	void put(Node<? extends NodeDefinition> node){
		this.nodesByInternalId.put(node.getInternalId(), node);
	}

	int nextId() {
		return nextId++;
	}
}
