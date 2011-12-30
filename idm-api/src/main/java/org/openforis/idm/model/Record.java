/**
 * 
 */
package org.openforis.idm.model;


import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.ModelVersion;
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
	private Entity rootEntity;
	
	public Record(Survey survey, String rootEntityName, String version) {
		this.survey = survey;
		Schema schema = survey.getSchema();
		EntityDefinition rootEntityDefinition = schema.getRootEntityDefinition(rootEntityName);
		if ( rootEntityDefinition == null ) {
			throw new IllegalArgumentException("Invalid root entity '"+rootEntity+'"');			
		}
		this.rootEntity = new Entity(rootEntityDefinition);
		this.modelVersion = survey.getVersion(version);
		if ( modelVersion == null ) {
			throw new IllegalArgumentException("Invalid version '"+version+'"');
		}
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

//	
//	public void setVersion(ModelVersion modelVersion) {
//		this.modelVersion = modelVersion;
//	}
/*
	protected void notifyListener(ModelObject<? extends SchemaObjectDefinition> modelObject) {
		this.listener.onStateChange(modelObject);
	}
*/
}
