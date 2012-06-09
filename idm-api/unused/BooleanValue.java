package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class BooleanValue implements Value {

	private Boolean value;
	
	public BooleanValue(Boolean value) {
		this.value = value;
	}

	public Boolean getBoolean() {
		return value;
	}

	@Override
	public int hashCode() {
		return value == null ? 0 : value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BooleanValue other = (BooleanValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
