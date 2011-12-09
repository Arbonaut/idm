package org.openforis.idm.model;

import org.openforis.idm.metamodel.EntityDefinition;

/**
 * NOTE: METHODS ARE DRAFT; TO BE IMPLEMENTED AS NEEDED. UNUSED METHODS WILL BE REMOVED.
 * 
 * @author G. Miceli
 * @author M. Togna
 */
public interface Entity extends ModelObject<EntityDefinition> {

	ModelObject<?> get(String name, int index);

	/**
	 * @return Immutable list containing all children with the specified name (entities and attributes), or an empty list if none exist.
	 */
//	List<ModelObject<?>> get(String name);

	int getCount(String name); 
	
	int move(String name, int oldIndex, int newIndex);
	
	void add(ModelObject<?> o);

	void add(ModelObject<?> o, int index);

	ModelObject<?> remove(String name, int index);

//	ModelObject<?> remove(ModelObject<?> o);

//	void remove(String name);

//	void clear();
	
//	ModelObject<?> replace(ModelObject<?> o, int index);
}
