package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class TextValue implements Value {

	private String string;

	public TextValue(String string) {
		super();
		this.string = string;
	}

	public String getString() {
		return string;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((string == null) ? 0 : string.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TextValue other = (TextValue) obj;
		if (string == null) {
			if (other.string != null)
				return false;
		} else if (!string.equals(other.string))
			return false;
		return true;
	}

}
