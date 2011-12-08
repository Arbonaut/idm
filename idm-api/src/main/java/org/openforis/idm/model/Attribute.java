package org.openforis.idm.model;

/**
 * @author   G. Miceli
 * @author   M. Togna
 */
public interface Attribute<V extends Value> extends ModelObject {

	/**
	 * @return
	 * @uml.property  name="value"
	 */
	V getValue();

	/**
	 * @param  value
	 * @uml.property  name="value"
	 */
	void setValue(V value);

}
