package org.openforis.idm.model;

/**
 * @author G. Miceli
 */
public abstract class NodeSchema<T extends Node<?>> extends MappedSchemaSupport<T> {

	public NodeSchema(Fields<T> fields) {
		super(fields);
	}

	@Override
	public boolean isInitialized(T entity) {
		return entity.definitionId != null;
	}
}
	