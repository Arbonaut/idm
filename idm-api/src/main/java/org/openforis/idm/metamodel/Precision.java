/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.xml.XmlParent;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "unitName", "decimalDigits", "defaultPrecision" })
public class Precision {

	@XmlTransient
	private Unit unit;

	@XmlTransient
	@XmlParent
	private NumericAttributeDefinition definition;
	
	@XmlAttribute(name = "decimalDigits")
	private Integer decimalDigits;

	@XmlAttribute(name = "default")
	private Boolean defaultPrecision;

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
	
	protected void setUnitName(String name) {
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
	
	private Survey getSurvey() {
		return definition == null ? null : definition.getSurvey();
	}
	
	public Integer getDecimalDigits() {
		return this.decimalDigits;
	}

	public boolean isDefaultPrecision() {
		return defaultPrecision == null ? false : defaultPrecision;
	}

	public NumericAttributeDefinition getDefinition() {
		return definition;
	}

	protected void setDefinition(NumericAttributeDefinition definition) {
		this.definition = definition;
	}
}
