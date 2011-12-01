package org.openforis.idm.model;

import java.util.List;

import org.openforis.idm.metamodel.EntityDefinition;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Entity extends ModelObject<EntityDefinition, ModelObject<?, ?>> {

	public ModelObject<?, ?> get(String name);

	/**
	 * @param name
	 *            attribute name
	 * @return Immutable list of values contained in the attribute specified in name or null if attribute does not exist
	 */
	public List<Value> getValues(String name);
}
