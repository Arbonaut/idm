/**
 * 
 */
package org.openforis.idm.model.impl;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Value;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public class DefaultAttribute<D extends AttributeDefinition, V extends Value> extends AbstractModelObject<D> implements Attribute<D, V> {

	private V value;

	@Override
	public V getValue() {
		return this.value;
	}

	@Override
	public void setValue(V value) {
		this.value = value;
	}

	@Override
	public V getDefaultValue() {
		// TODO Auto-generated method stub
		return null;
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
}
