package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface BooleanAttributeDefinition extends AttributeDefinition {

	/**
	 * @return Returns the affirmativeOnly.
	 * @uml.property name="affirmativeOnly" readOnly="true"
	 */
	public boolean isAffirmativeOnly();

}
