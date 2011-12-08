package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Precision {

	/**
	 * @return Returns the unit.
	 * @uml.property name="unit"
	 * @uml.associationEnd inverse="precision:org.openforis.idm.metamodel.Unit"
	 */
	public Unit getUnit();

	/**
	 * @return Returns the decimalDigits.
	 * @uml.property name="decimalDigits"
	 */
	public Integer getDecimalDigits();

	/**
	 * @return Returns the defaultPrecision.
	 * @uml.property name="defaultPrecision"
	 */
	public boolean isDefaultPrecision();

}
