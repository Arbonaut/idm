package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class NumericValue<T extends Number> implements Value {

	private T number;

	public NumericValue(T number) {
		this.number = number;
	}

	public T getNumber() {
		return number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		NumericValue<?> other = (NumericValue<?>) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

}
