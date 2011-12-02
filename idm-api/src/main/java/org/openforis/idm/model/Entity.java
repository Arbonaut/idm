package org.openforis.idm.model;

import java.util.List;

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

	/**
	 * @return Immutable list containing all children with the specified name (entities and attributes), or an empty list if none exist.
	 */
	List<ModelObject<? extends ModelObjectDefinition>> get(String name);

	void add(ModelObject<? extends ModelObjectDefinition> o);

	void add(ModelObject<? extends ModelObjectDefinition> o, int index);

	ModelObject<? extends ModelObjectDefinition> remove(String name, int index);

	void clear(String name);

	void clear();

	ModelObject<? extends ModelObjectDefinition> set(ModelObject<? extends ModelObjectDefinition> o, int index);
}
