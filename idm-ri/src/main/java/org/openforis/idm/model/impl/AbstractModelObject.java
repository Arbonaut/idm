/**
 * 
 */
package org.openforis.idm.model.impl;

import org.openforis.idm.metamodel.ModelObjectDefinition;
import org.openforis.idm.model.ModelObject;

/**
 * @author M. Togna
 * 
 */
public abstract class AbstractModelObject<D extends ModelObjectDefinition> implements ModelObject<D> {

	private D definition;

//	private DefaultRecord record;
	private ModelObject<? extends ModelObjectDefinition> parent;
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
	public ModelObject<? extends ModelObjectDefinition> getParent() {
		return this.parent;
	}

	protected void setParent(ModelObject<? extends ModelObjectDefinition> parent) {
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
