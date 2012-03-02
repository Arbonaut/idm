package org.openforis.idm.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 
 * @author M. Togna
 * 
 */
public class NodePointer {
	private Entity entity;
	private String childName;
	private String entityPath;

	public NodePointer(Entity entity, String childName) {
		this.entity = entity;
		this.childName = childName;
		this.entityPath = entity.getPath();
	}

	public Entity getEntity() {
		return entity;
	}

	public String getChildName() {
		return childName;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(entityPath).append(childName).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodePointer other = (NodePointer) obj;
		return new EqualsBuilder().append(entityPath, other.entityPath).append(childName, other.childName).isEquals();
	}

}
