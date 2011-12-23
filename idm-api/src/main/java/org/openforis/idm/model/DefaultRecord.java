/**
 * 
 */
package org.openforis.idm.model;

import java.util.Date;

import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.Survey;

/**
 * @author M. Togna
 * 
 */
public class DefaultRecord implements Record {

	private Long id;
	private Survey survey;
	private ModelVersion modelVersion;
	private Entity rootEntity;
	private Date creationDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;

	public DefaultRecord(Survey survey, ModelVersion modelVersion) {
		this.survey = survey;
		this.modelVersion = modelVersion;
	}

	@Override
	public Survey getSurvey() {
		return this.survey;
	}

	@Override
	public Entity getRootEntity() {
		return this.rootEntity;
	}

	// TODO Need path?
//	@Override
//	public void setRootEntity(Entity rootEntity) {
//		DefaultEntity entityImpl = (DefaultEntity) rootEntity;
//		this.rootEntity = entityImpl;
//		entityImpl.setRecord(this);
//		entityImpl.setPath("/" + rootEntity.getDefinition().getName());
//	}

	@Override
	public Date getCreationDate() {
		return this.creationDate;
	}

	@Override
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String getCreatedBy() {
		return this.createdBy;
	}

	@Override
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	@Override
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public ModelVersion getVersion() {
		return this.modelVersion;
	}

	@Override
	public void setVersion(ModelVersion modelVersion) {
		this.modelVersion = modelVersion;
	}
/*
	protected void notifyListener(ModelObject<? extends SchemaObjectDefinition> modelObject) {
		this.listener.onStateChange(modelObject);
	}
*/
}
