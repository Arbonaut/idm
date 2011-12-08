package org.openforis.idm.model;

import java.util.List;

/**
 * NOTE: METHODS ARE DRAFT; TO BE IMPLEMENTED AS NEEDED. UNUSED METHODS WILL BE REMOVED.
 * 
 * @author G. Miceli
 * @author M. Togna
 */
public interface Entity extends ModelObject {

	ModelObject get(String name, int index);

	/**
	 * @return Immutable list containing all children with the specified name (entities and attributes), or an empty list if none exist.
	 */
	List<ModelObject> get(String name);

	void add(ModelObject o);

	void add(ModelObject o, int index);

	ModelObject remove(String name, int index);

	void clear(String name);

	void clear();

	ModelObject set(ModelObject o, int index);
}
