/**
 * 
 */
package org.openforis.idm.model;

import java.io.StringWriter;

import org.openforis.idm.metamodel.SchemaObjectDefinition;


/**
 * @author G. Miceli
 * @author M. Togna
 */
public abstract class ModelObject<D extends SchemaObjectDefinition> {

	private Integer id;
	
	private D definition;

//	private Record record;
	private Entity parent;
//	private String path;
//	private String type;

	public ModelObject(D definition) {
		if ( definition == null ) {
			throw new NullPointerException("Null definition");
		}
		this.definition = definition;
	}
	
	public Integer getId() {
		return id;
	}
	
	// TODO encapsulate better?
	public void setId(Integer id) {
		this.id = id;
	}
	
	public D getDefinition() {
		return this.definition;
	}

	public String getName() {
		return this.getDefinition().getName();
	}

	public Entity getParent() {
		return this.parent;
	}

	protected void setParent(Entity parent) {
		this.parent = parent;
	}

	protected abstract void write(StringWriter sw, int indent);

	@Override
	public String toString() {
		StringWriter sw = new StringWriter();
		write(sw,0);
		return sw.toString();
	}


/*
	protected Record getRecord() {
		return this.record;
	}

	protected void setRecord(Record record) {
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
