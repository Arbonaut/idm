/**
 * 
 */
package org.openforis.idm.metamodel;

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
public class Precision extends ModelDefinition {

	@XmlTransient
	private Unit unit;

	@XmlAttribute(name = "decimalDigits")
	private Integer decimalDigits;

	@XmlAttribute(name = "default")
	private boolean defaultPrecision;

	public Unit getUnit() {
		return this.unit;
	}

	void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	@XmlAttribute(name = "unit")
	public String getUnitName() {
		return unit == null ? null : unit.getName();
	}
	
	void setUnitName(String name) {
		Survey survey = getSurvey();
		if ( survey == null ) {
			throw new DetachedModelDefinitionException(Precision.class, Survey.class);
		}
		Unit newUnit = survey.getUnit(name);
		if ( newUnit == null ) {
			throw new IllegalArgumentException("Unit '"+name+"' not defined in survey");
		}
		this.unit = newUnit;
	}
	
	public Integer getDecimalDigits() {
		return this.decimalDigits;
	}

	public boolean isDefaultPrecision() {
		return this.defaultPrecision;
	}


}
