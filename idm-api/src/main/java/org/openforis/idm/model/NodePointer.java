package org.openforis.idm.model;


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

}
