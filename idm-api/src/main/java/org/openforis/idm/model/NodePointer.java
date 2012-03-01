package org.openforis.idm.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class NodePointer {
	private String entityPath;
	private String childName;

	public NodePointer(String entityPath, String childName) {
		this.entityPath = entityPath;
		this.childName = childName;
	}

	public String getEntityPath() {
		return entityPath;
	}

	public void setEntityPath(String entityPath) {
		this.entityPath = entityPath;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
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
		return new EqualsBuilder().append(childName, other.childName).append(entityPath, other.entityPath).isEquals();
	}

}
