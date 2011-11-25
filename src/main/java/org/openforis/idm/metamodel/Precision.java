package org.openforis.idm.metamodel;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Precision {

	/**
	 * @return  Returns the unit.
	 * @uml.property  name="unit"
	 * @uml.associationEnd  inverse="precision:org.openforis.idm.metamodel.Unit"
	 */
	public Unit getUnit();

	/**
	 * Setter of the property <tt>unit</tt>
	 * @param unit  The unit to set.
	 * @uml.property  name="unit"
	 */
	public void setUnit(Unit unit);

	/**
	 * @return  Returns the decimalDigits.
	 * @uml.property  name="decimalDigits"
	 */
	public Integer getDecimalDigits();

	/**
	 * Setter of the property <tt>decimalDigits</tt>
	 * @param decimalDigits  The decimalDigits to set.
	 * @uml.property  name="decimalDigits"
	 */
	public void setDecimalDigits(Integer decimalDigits);

	/**
	 * @return  Returns the defaultPrecision.
	 * @uml.property  name="defaultPrecision"
	 */
	public boolean isDefaultPrecision();

	/**
	 * Setter of the property <tt>defaultPrecision</tt>
	 * @param defaultPrecision  The defaultPrecision to set.
	 * @uml.property  name="defaultPrecision"
	 */
	public void setDefaultPrecision(boolean defaultPrecision);

}
