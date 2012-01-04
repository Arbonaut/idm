/**
 * 
 */
package org.openforis.idm.model;

import java.io.StringWriter;

import org.openforis.idm.metamodel.AttributeDefinition;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public abstract class Attribute<D extends AttributeDefinition, V> extends Node<D> {

	public Attribute(D definition) {
		super(definition);
	}

	private V value;
	
	private AttributeMetadata metadata;

	public V getValue() {
		return this.value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public V getDefaultValue() {
		// TODO Evaluate defaultExpression in definition on value
		throw new UnsupportedOperationException();
	}
	
	public AttributeMetadata getMetadata() {
		return metadata;
	}
	
	public void setMetadata(AttributeMetadata metadata) {
		this.metadata = metadata;
	}
/*
	public List<CheckFailure> getErrors() {
		List<CheckFailure> errors = this.errors != null ? this.errors : new ArrayList<CheckFailure>();
		return Collections.unmodifiableList(errors);
	}

	@Override
	public List<CheckFailure> getWarnings() {
		List<CheckFailure> warnings = this.warnings != null ? this.warnings : new ArrayList<CheckFailure>();
		return Collections.unmodifiableList(warnings);
	}

	@Override
	public boolean hasErrors() {
		return (this.errors != null) && !this.errors.isEmpty();
	}

	@Override
	public boolean hasWarnings() {
		return (this.warnings != null) && !this.warnings.isEmpty();
	}
*/
/*
	protected void addError(CheckFailure checkFailure) {
		if (this.errors == null) {
			this.errors = new ArrayList<CheckFailure>();
		}
		this.errors.add(checkFailure);
	}

	protected void addWarning(CheckFailure checkFailure) {
		if (this.warnings == null) {
			this.warnings = new ArrayList<CheckFailure>();
		}
		this.warnings.add(checkFailure);
	}
*/
	@Override
	protected void write(StringWriter sw, int indent) {
		for (int i = 0; i < indent; i++) {
			sw.append('\t');
		}
		sw.append(getName());
		sw.append(": ");
		sw.append(value == null ? "!!null" : value.toString());
		sw.append("\n");
	}
}
