package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class Code<T>  {

	private T code;
	private String qualifier;
	
	public Code(T code) {
		this.code = code;
	}

	public Code(T code, String qualifier) {
		this.code = code;
		this.qualifier = qualifier;
	}

	public T getCode() {
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
		result = prime * result
				+ ((qualifier == null) ? 0 : qualifier.hashCode());
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
		@SuppressWarnings("unchecked")
		Code<T> other = (Code<T>) obj;
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
	
	
}
