/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.Serializable;

/*import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;*/
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Transient;

import org.openforis.idm.metamodel.xml.internal.XmlParent;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author K. Waga
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@Order(attributes = {"unit", "decimalDigits", "default"} )
public class Precision implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	@XmlParent
	private NodeDefinition definition;
	
	@Attribute(name = "decimalDigits", required = false)
	private Integer decimalDigits;

	@Attribute(name = "default", required = false)
	private Boolean defaultPrecision;

	@Attribute(name = "unit", required = false)
	private String unitName;
	
	public Unit getUnit() {
		return getUnitByName(unitName);
	}

	public void setUnit(Unit unit) {
		if ( unit == null ) {
			this.unitName = null;
		} else {
			Unit otherUnit = getUnitByName(unit.getName());
			if ( unit != otherUnit ) {
				throw new IllegalArgumentException("Unit not in survey");
			}
			this.unitName = unit.getName();
		}
	}
	
	public void setUnitByName(String unitName) {
		getUnitByName(unitName);
		this.unitName = unitName;
	}
	
	public void setUnit(String unitName) {
		this.unitName = unitName;
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

	public NodeDefinition getDefinition() {
		return definition;
	}

	protected void setDefinition(NodeDefinition definition) {
		this.definition = definition;
	}

	private Unit getUnitByName(String unitName) {
		if ( unitName == null ) {
			return null;
		}
		Survey survey = getSurvey();
		if ( survey == null ) {
			throw new DetachedNodeDefinitionException(Precision.class, Survey.class);
		}
		Unit unit = survey.getUnit(unitName);
		if ( unit == null ) {
			throw new IllegalArgumentException("Unit '"+unitName+"' not defined in survey");
		}
		return unit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((decimalDigits == null) ? 0 : decimalDigits.hashCode());
		result = prime
				* result
				+ ((defaultPrecision == null) ? 0 : defaultPrecision.hashCode());
		result = prime * result
				+ ((unitName == null) ? 0 : unitName.hashCode());
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
		if (unitName == null) {
			if (other.unitName != null)
				return false;
		} else if (!unitName.equals(other.unitName))
			return false;
		return true;
	}
}
