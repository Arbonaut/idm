package org.openforis.idm.metamodel;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface BooleanAttributeDefinition extends AttributeDefinition {

	/**
	 * @return  Returns the affirmativeOnly.
	 * @uml.property  name="affirmativeOnly"
	 */
	public boolean isAffirmativeOnly();

	/**
	 * Setter of the property <tt>affirmativeOnly</tt>
	 * @param affirmativeOnly  The affirmativeOnly to set.
	 * @uml.property  name="affirmativeOnly"
	 */
	public void setAffirmativeOnly(boolean affirmativeOnly);

}
