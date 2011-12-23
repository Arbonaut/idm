/**
 * 
 */
package org.openforis.idm.model;

import org.openforis.idm.metamodel.SchemaObjectDefinition;

/**
 * @author M. Togna
 * 
 */
public abstract class AbstractModelObject<D extends SchemaObjectDefinition> implements ModelObject<D> {

	private D definition;

//	private DefaultRecord record;
	private ModelObject<? extends SchemaObjectDefinition> parent;
//	private String path;
//	private String type;

	@Override
	public D getDefinition() {
		return this.definition;
	}

	@Override
	public String getName() {
		return this.getDefinition().getName();
	}

	@Override
	public ModelObject<? extends SchemaObjectDefinition> getParent() {
		return this.parent;
	}

	protected void setParent(ModelObject<? extends SchemaObjectDefinition> parent) {
		this.parent = parent;
	}

/*
	protected DefaultRecord getRecord() {
		return this.record;
	}

	protected void setRecord(DefaultRecord record) {
		this.record = record;
	}
	public String getPath() {
		return this.path;
	}

	void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public Long getId() {
		return id;
	}
*/
}
