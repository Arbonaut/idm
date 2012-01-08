package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class NumericRange<T extends Number> {

	private final T from;
	private final T to;

	NumericRange(T value) {
		this.from = value;
		this.to = value;
	}

	NumericRange(T from, T to) {
		this.from = from;
		this.to = to;
	}

	public T getFrom() {
		return from;
	}

	public T getTo() {
		return to;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
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
		NumericRange<T> other = (NumericRange<T>) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{from:").append(from);
		sb.append(", to:").append(to);
		sb.append("}");
		return sb.toString();
	}
}
