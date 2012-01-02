/**
 * 
 */
package org.openforis.idm.model;


import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.SchemaObjectDefinition;
import org.openforis.idm.metamodel.Survey;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class Record {

	private Integer id;
	private Survey survey;
	private ModelVersion modelVersion;
	private Entity rootEntity;
	private List<RecordObserver> observers;
//	private Map<Integer, ModelObject<? extends SchemaObjectDefinition>> modelObjectsById;
	private int nextId;

	public Record(Survey survey, String rootEntityName, String version) {
		this.survey = survey;
		Schema schema = survey.getSchema();
		EntityDefinition rootEntityDefinition = schema.getRootEntityDefinition(rootEntityName);
		if ( rootEntityDefinition == null ) {
			throw new IllegalArgumentException("Invalid root entity '"+rootEntity+'"');			
		}
		this.modelVersion = survey.getVersion(version);
		if ( modelVersion == null ) {
			throw new IllegalArgumentException("Invalid version '"+version+'"');
		}
		this.rootEntity = new Entity(rootEntityDefinition);
		this.rootEntity.setRecord(this);
		this.rootEntity.setId(-1);
		this.nextId = -2;
//		this.modelObjectsById = new HashMap<Integer, ModelObject<? extends SchemaObjectDefinition>>();
		this.observers = new ArrayList<RecordObserver>();
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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
/*
	protected void notifyListener(ModelObject<? extends SchemaObjectDefinition> modelObject) {
		this.listener.onStateChange(modelObject);
	}
*/
//
//	public ModelObject<? extends SchemaObjectDefinition> getModelObjectById(int id) {
//		return this.modelObjectsById.get(id);
//	}

	public void addObserver(RecordObserver observer) {
		observers.add(observer);
	}
	
	public void notifyObservers(ModelObject<?> target, Object... args) {
//		updateInternal(target);
		for (RecordObserver observer : observers) {
			observer.update(target, args);
		}
	}
/*
	protected void updateInternal(ModelObject<?> target) {
		modelObjectsById
		
	}
	*/

	protected int nextId() {
		return nextId--;
	}
}
