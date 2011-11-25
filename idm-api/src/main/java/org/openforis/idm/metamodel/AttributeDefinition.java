package org.openforis.idm.metamodel;

import java.util.List;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface AttributeDefinition<C extends ValueCheck> extends ModelObjectDefinition {

	/**
	 * @return    Returns the checks.
	 * @uml.property   name="valueChecks"
	 * @uml.associationEnd   multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite" inverse="attributeDefinition:org.openforis.idm.metamodel.ValueCheck"
	 */
	public List<C> getValueChecks();

	/**
	 * Setter of the property <tt>checks</tt>
	 * @param checks  The checks to set.
	 * @uml.property  name="valueChecks"
	 */
	public void setValueChecks(List<C> valueChecks);
	
	/**
	 * @return    Returns the attributeDefaults.
	 * @uml.property   name="attributeDefaults"
	 * @uml.associationEnd   multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite" inverse="attributeDefinition:org.openforis.idm.metamodel.AttributeDefault"
	 */
	public List<AttributeDefault> getAttributeDefaults();

	/**
	 * Setter of the property <tt>attributeDefaults</tt>
	 * @param attributeDefaults  The attributeDefaults to set.
	 * @uml.property  name="attributeDefaults"
	 */
	public void setAttributeDefaults(List<AttributeDefault> attributeDefaults);
}
