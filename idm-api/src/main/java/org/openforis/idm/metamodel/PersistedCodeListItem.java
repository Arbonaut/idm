/**
 * 
 */
package org.openforis.idm.metamodel;


/**
 * @author S. Ricci
 *
 */
public class PersistedCodeListItem extends CodeListItem {

	private static final long serialVersionUID = 1L;

	private Integer systemId;
	private Integer sortOrder;
	private Integer parentId;
	
	public PersistedCodeListItem(CodeList codeList) {
		this(codeList, codeList.getSurvey().nextId());
	}

	public PersistedCodeListItem(CodeList codeList, int id) {
		super(codeList, id);
	}

	public Integer getSystemId() {
		return systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result
				+ ((sortOrder == null) ? 0 : sortOrder.hashCode());
		result = prime * result
				+ ((systemId == null) ? 0 : systemId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersistedCodeListItem other = (PersistedCodeListItem) obj;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (sortOrder == null) {
			if (other.sortOrder != null)
				return false;
		} else if (!sortOrder.equals(other.sortOrder))
			return false;
		if (systemId == null) {
			if (other.systemId != null)
				return false;
		} else if (!systemId.equals(other.systemId))
			return false;
		return true;
	}

}