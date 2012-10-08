/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "unitName", "decimalDigits", "defaultPrecision" })
public class Precision implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private Unit unit;

	@XmlAttribute(name = "decimalDigits")
	private Integer decimalDigits;

	@XmlAttribute(name = "default")
	private Boolean defaultPrecision;

	public Unit getUnit() {
		return this.unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public Integer getDecimalDigits() {
		return this.decimalDigits;
	}
	
	public void setDecimalDigits(Integer decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public boolean isDefaultPrecision() {
		return defaultPrecision == null ? false : defaultPrecision;
	}
	
	public void setDefaultPrecision(boolean defaultPrecision) {
		this.defaultPrecision = defaultPrecision;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((decimalDigits == null) ? 0 : decimalDigits.hashCode());
		result = prime * result + ((defaultPrecision == null) ? 0 : defaultPrecision.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
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
		Precision other = (Precision) obj;
		if (decimalDigits == null) {
			if (other.decimalDigits != null)
				return false;
		} else if (!decimalDigits.equals(other.decimalDigits))
			return false;
		if (defaultPrecision == null) {
			if (other.defaultPrecision != null)
				return false;
		} else if (!defaultPrecision.equals(other.defaultPrecision))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}
	
}
