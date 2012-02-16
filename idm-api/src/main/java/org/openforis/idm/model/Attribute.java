/**
 * 
 */
package org.openforis.idm.model;

import java.io.StringWriter;
import java.util.List;

import org.openforis.idm.metamodel.AttributeDefault;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.validation.Check;
import org.openforis.idm.metamodel.validation.ValidationResults;
import org.openforis.idm.model.expression.InvalidExpressionException;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public abstract class Attribute<D extends AttributeDefinition, V> extends Node<D> {

	private V value;
	private String remarks;
	private Character symbol;

	private AttributeMetadata metadata;
	private boolean defaultValue;
	
	public Attribute(D definition) {
		super(definition);
	}
	
	public abstract V createValue(String string);
	
	public V getValue() {
		return this.value;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
	@Override
	public ValidationResults validate() {
		ValidationResults results = new ValidationResults();
		if ( !isEmpty() ) {
			boolean valid = validateValue(results);
			if ( valid ) {
				validateChecks(results);
			}
		}
		return results;
	}

	/**
	 * 
	 * @param results
	 * @return true if other defined checks should be executed.  false to stop executing checks
	 */
	protected boolean validateValue(ValidationResults results) {
		return true;
	}
	
	private void validateChecks(ValidationResults results) {
		D defn = getDefinition();
		List<Check> checks = defn.getChecks();
		for (Check check : checks) {
			boolean result = check.validate(this);
			results.addResult(this, check, result);
		}
	}
	
	public V getDefaultValue() throws InvalidExpressionException {
		D definition = getDefinition();
		List<AttributeDefault> attributeDefaults = definition.getAttributeDefaults();
		for (AttributeDefault attributeDefault : attributeDefaults) {
			V value = attributeDefault.evaluate(this);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	/**
	 * @return true if value is not null
	 */
	@Override
	public boolean isEmpty() {
		return value == null || value.toString().trim().length() == 0;
	}

	public boolean isDefaultValue() {
		return defaultValue;
	}
	
	public void applyDefaultValue() throws InvalidExpressionException {
		this.value = getDefaultValue();
		this.defaultValue = true;
	}
	
	public AttributeMetadata getMetadata() {
		return metadata;
	}
	
	public void setMetadata(AttributeMetadata metadata) {
		this.metadata = metadata;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Character getSymbol() {
		return symbol;
	}

	public void setSymbol(Character symbol) {
		this.symbol = symbol;
	}

	/*
	public List<CheckResult> getErrors() {
		List<CheckResult> errors = this.errors != null ? this.errors : new ArrayList<CheckResult>();
		return Collections.unmodifiableList(errors);
	}

	@Override
	public List<CheckResult> getWarnings() {
		List<CheckResult> warnings = this.warnings != null ? this.warnings : new ArrayList<CheckResult>();
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
