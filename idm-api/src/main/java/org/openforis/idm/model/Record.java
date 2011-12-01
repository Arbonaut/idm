package org.openforis.idm.model;

import java.util.Date;

import org.openforis.idm.metamodel.ModelVersion;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Record {

	/**
	 * @return Returns the rootEntity.
	 * @uml.property name="rootEntity"
	 * @uml.associationEnd aggregation="composite" inverse="record:org.openforis.idm.model.Entity"
	 */
	Entity getRootEntity();

	/**
	 * Setter of the property <tt>rootEntity</tt>
	 * 
	 * @param rootEntity
	 *            The rootEntity to set.
	 * @uml.property name="rootEntity"
	 */
	void setRootEntity(Entity rootEntity);

	/**
	 * @return Returns the creationDate.
	 * @uml.property name="creationDate"
	 */
	Date getCreationDate();

	/**
	 * Setter of the property <tt>creationDate</tt>
	 * 
	 * @param creationDate
	 *            The creationDate to set.
	 * @uml.property name="creationDate"
	 */
	void setCreationDate(Date creationDate);

	/**
	 * @return Returns the createdBy.
	 * @uml.property name="createdBy"
	 */
	String getCreatedBy();

	/**
	 * Setter of the property <tt>createdBy</tt>
	 * 
	 * @param createdBy
	 *            The createdBy to set.
	 * @uml.property name="createdBy"
	 */
	void setCreatedBy(String createdBy);

	/**
	 * @return Returns the modifiedDate.
	 * @uml.property name="modifiedDate"
	 */
	Date getModifiedDate();

	/**
	 * Setter of the property <tt>modifiedDate</tt>
	 * 
	 * @param modifiedDate
	 *            The modifiedDate to set.
	 * @uml.property name="modifiedDate"
	 */
	void setModifiedDate(Date modifiedDate);

	/**
	 * @return Returns the modifiedBy.
	 * @uml.property name="modifiedBy"
	 */
	String getModifiedBy();

	/**
	 * Setter of the property <tt>modifiedBy</tt>
	 * 
	 * @param modifiedBy
	 *            The modifiedBy to set.
	 * @uml.property name="modifiedBy"
	 */
	void setModifiedBy(String modifiedBy);

	/**
	 * @return Returns the id.
	 * @uml.property name="id"
	 */
	Long getId();

	/**
	 * Setter of the property <tt>id</tt>
	 * 
	 * @param id
	 *            The id to set.
	 * @uml.property name="id"
	 */
	void setId(Long id);

	/**
	 * @return Returns the version.
	 * @uml.property name="version"
	 */
	ModelVersion getVersion();

	/**
	 * Setter of the property <tt>version</tt>
	 * 
	 * @param version
	 *            The version to set.
	 * @uml.property name="version"
	 */
	void setVersion(ModelVersion version);

}
