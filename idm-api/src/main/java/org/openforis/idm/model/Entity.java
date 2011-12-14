package org.openforis.idm.model;

import java.util.Set;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.ModelObjectDefinition;

/**
 * NOTE: METHODS ARE DRAFT; TO BE IMPLEMENTED AS NEEDED. UNUSED METHODS WILL BE REMOVED.
 * 
 * @author G. Miceli
 * @author M. Togna
 */
public interface Entity extends ModelObject<EntityDefinition> {

	ModelObject<? extends ModelObjectDefinition> get(String name, int index);

	// TODO Is getChildNames required?  Can use ModelObjectDefinition instead?
	/**
	 * Returns an unmodifiable set of the child names
	 * 
	 * @return
	 */
	Set<String> getChildNames();

	/**
	 * @return Immutable list containing all children with the specified name (entities and attributes), or an empty list if none exist.
	 */
	// List<ModelObject<?>> get(String name);

	int getCount(String name);

	void move(String name, int oldIndex, int newIndex);

	void add(ModelObject<? extends ModelObjectDefinition> o);

	void add(ModelObject<? extends ModelObjectDefinition> o, int index);

	ModelObject<? extends ModelObjectDefinition> remove(String name, int index);

	// ModelObject<?> remove(ModelObject<?> o);

	// void remove(String name);

	// void clear();

	// ModelObject<?> replace(ModelObject<?> o, int index);
}
