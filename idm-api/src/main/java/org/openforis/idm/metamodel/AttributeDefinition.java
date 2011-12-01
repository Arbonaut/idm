package org.openforis.idm.metamodel;

import java.util.List;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface AttributeDefinition extends ModelObjectDefinition {

	/**
	 * @return Returns the checks.
	 * @uml.property name="explicitChecks"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="attributeDefinition:org.openforis.idm.metamodel.ExplicitCheck"
	 */
	public List<ExplicitCheck> getExplicitChecks();

	/**
	 * Setter of the property <tt>checks</tt>
	 * 
	 * @param checks
	 *            The checks to set.
	 * @uml.property name="explicitChecks"
	 */
	public void setExplicitCheck(List<ExplicitCheck> explicitCheck);

	/**
	 * @return Returns the attributeDefaults.
	 * @uml.property name="attributeDefaults"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="attributeDefinition:org.openforis.idm.metamodel.AttributeDefault"
	 */
	public List<AttributeDefault> getAttributeDefaults();

	/**
	 * Setter of the property <tt>attributeDefaults</tt>
	 * 
	 * @param attributeDefaults
	 *            The attributeDefaults to set.
	 * @uml.property name="attributeDefaults"
	 */
	public void setAttributeDefaults(List<AttributeDefault> attributeDefaults);
}
