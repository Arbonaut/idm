package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class Coordinate {

	private Long x;
	private Long y;
	private Long z;
	private String srsId;
	
	public Coordinate(Long x, Long y, String srsId) {
		this.x = x;
		this.y = y;
		this.srsId = srsId;
	}
	
	public Coordinate(Long x, Long y, Long z, String srsId) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.srsId = srsId;
	}

	public Long getX() {
		return x;
	}

	public Long getY() {
		return y;
	}

	public Long getZ() {
		return z;
	}

	public String getSrsId() {
		return srsId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((srsId == null) ? 0 : srsId.hashCode());
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		result = prime * result + ((z == null) ? 0 : z.hashCode());
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
		Coordinate other = (Coordinate) obj;
		if (srsId == null) {
			if (other.srsId != null)
				return false;
		} else if (!srsId.equals(other.srsId))
			return false;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		if (z == null) {
			if (other.z != null)
				return false;
		} else if (!z.equals(other.z))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{x:").append(x);
		sb.append(", y:").append(y);
		if ( z!=null ) {
			sb.append(", z:").append(z);
		}
		sb.append(", srsId:").append(srsId);
		sb.append("}");
		return sb.toString();
	}
}
