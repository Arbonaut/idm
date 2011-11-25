package org.openforis.idm.model;

import java.util.Date;
import org.openforis.idm.metamodel.ModelVersion;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Record {

	/**
	 * @return  Returns the rootEntity.
	 * @uml.property  name="rootEntity"
	 * @uml.associationEnd  aggregation="composite" inverse="record:org.openforis.idm.model.Entity"
	 */
	public Entity getRootEntity();

	/**
	 * Setter of the property <tt>rootEntity</tt>
	 * @param rootEntity  The rootEntity to set.
	 * @uml.property  name="rootEntity"
	 */
	public void setRootEntity(Entity rootEntity);

	/**
	 * @return  Returns the creationDate.
	 * @uml.property  name="creationDate"
	 */
	public Date getCreationDate();

	/**
	 * Setter of the property <tt>creationDate</tt>
	 * @param creationDate  The creationDate to set.
	 * @uml.property  name="creationDate"
	 */
	public void setCreationDate(Date creationDate);

	/**
	 * @return  Returns the createdBy.
	 * @uml.property  name="createdBy"
	 */
	public String getCreatedBy();

	/**
	 * Setter of the property <tt>createdBy</tt>
	 * @param createdBy  The createdBy to set.
	 * @uml.property  name="createdBy"
	 */
	public void setCreatedBy(String createdBy);

	/**
	 * @return  Returns the modifiedDate.
	 * @uml.property  name="modifiedDate"
	 */
	public Date getModifiedDate();

	/**
	 * Setter of the property <tt>modifiedDate</tt>
	 * @param modifiedDate  The modifiedDate to set.
	 * @uml.property  name="modifiedDate"
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * @return  Returns the modifiedBy.
	 * @uml.property  name="modifiedBy"
	 */
	public String getModifiedBy();

	/**
	 * Setter of the property <tt>modifiedBy</tt>
	 * @param modifiedBy  The modifiedBy to set.
	 * @uml.property  name="modifiedBy"
	 */
	public void setModifiedBy(String modifiedBy);

	/**
	 * @return  Returns the id.
	 * @uml.property  name="id"
	 */
	public Long getId();

	/**
	 * Setter of the property <tt>id</tt>
	 * @param id  The id to set.
	 * @uml.property  name="id"
	 */
	public void setId(Long id);

	/**
	 * @return  Returns the version.
	 * @uml.property  name="version"
	 */
	public ModelVersion getVersion();

	/**
	 * Setter of the property <tt>version</tt>
	 * @param version  The version to set.
	 * @uml.property  name="version"
	 */
	public void setVersion(ModelVersion version);

}
