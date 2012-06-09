package org.openforis.idm.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class Code implements Value {

	private final String code;
	private final String qualifier;

	public Code(String code) {
		this.code = code;
		this.qualifier = null;
	}

	public Code(String code, String qualifier) {
		this.code = code;
		this.qualifier = qualifier;
	}

	public String getCode() {
		return code;
	}

	public String getQualifier() {
		return qualifier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((qualifier == null) ? 0 : qualifier.hashCode());
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
		Code other = (Code) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (qualifier == null) {
			if (other.qualifier != null)
				return false;
		} else if (!qualifier.equals(other.qualifier))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("code", code)
			.append("qualifier", qualifier)
			.toString();
	}
}
