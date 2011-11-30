/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.Precision;
import org.openforis.idm.metamodel.Unit;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "unitAttribute", "decimalDigits", "defaultPrecision" })
public class PrecisionImpl implements Precision {

	@XmlAttribute(name = "")
	String unitAttribute;
	@XmlTransient
	private Unit unit;

	@XmlAttribute(name = "decimalDigits")
	private Integer decimalDigits;

	@XmlAttribute(name = "default")
	private boolean defaultPrecision;

	@Override
	public Unit getUnit() {
		return this.unit;
	}

	@Override
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@Override
	public Integer getDecimalDigits() {
		return this.decimalDigits;
	}

	@Override
	public void setDecimalDigits(Integer decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	@Override
	public boolean isDefaultPrecision() {
		return this.defaultPrecision;
	}

	@Override
	public void setDefaultPrecision(boolean defaultPrecision) {
		this.defaultPrecision = defaultPrecision;
	}

}
