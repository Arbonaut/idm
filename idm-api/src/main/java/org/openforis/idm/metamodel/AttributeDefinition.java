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
	 *                     inverse="attributeDefinition:org.openforis.idm.metamodel.Check" readOnly="true"
	 */
	public List<Check> getExplicitChecks();

	/**
	 * @return Returns the attributeDefaults.
	 * @uml.property name="attributeDefaults"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite"
	 *                     inverse="attributeDefinition:org.openforis.idm.metamodel.AttributeDefault" readOnly="true"
	 */
	public List<AttributeDefault> getAttributeDefaults();

}
