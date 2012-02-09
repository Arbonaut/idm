/**
 * 
 */
package org.openforis.idm.model;

import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openforis.idm.metamodel.AttributeDefault;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.Check;
import org.openforis.idm.model.expression.DefaultValueExpression;
import org.openforis.idm.model.expression.InvalidPathException;
import org.openforis.idm.validation.CheckResult;
import org.openforis.idm.validation.ValidationResults;

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

	public V getDefaultValue() {
		V defaultValue = null;
		List<AttributeDefault> attributeDefaults = getDefinition().getAttributeDefaults();
		for (AttributeDefault attributeDefault : attributeDefaults) {
			V value = getDefaultValue(attributeDefault);
			if (value != null) {
				defaultValue = value;
				break;
			}
		}
		return defaultValue;
	}
	
	@Override
	public ValidationResults validate() {
		ValidationResults results = new ValidationResults();
		if (getValue() != null) {
			validateChecks(results);
		}
		return results;
	}

	private void validateChecks(ValidationResults results) {
		List<Check> checks = getDefinition().getChecks();
		for (Check check : checks) {
			CheckResult result = check.evaluate(this);
			if (!result.isPassed()) {
				if (result.isError()) {
					results.addError(result);
				} else if (result.isWarning()) {
					results.addWarning(result);
				}
			}
		}
	}

	public boolean isDefaultValue() {
		return defaultValue;
	}
	
	public void applyDefaultValue(){
		this.value = getDefaultValue();
		this.defaultValue = Boolean.TRUE;
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

	@SuppressWarnings("unchecked")
	private V getDefaultValue(AttributeDefault attributeDefault) {
		String condition = attributeDefault.getCondition();
		if (StringUtils.isBlank(condition) || evaluate(condition)) {
			String constValue = attributeDefault.getValue();
			if (StringUtils.isBlank(constValue)) {
				String expression = attributeDefault.getExpression();
				DefaultValueExpression defaultValueExpression = getExpressionFactory().createDefaultValueExpression(expression);
				try {
					Object object = defaultValueExpression.evaluate(getParent());
					return (V) object;
				} catch (InvalidPathException e) {
					throw new RuntimeException("Unable to evaluate expression " + condition, e);
				}
			} else {
				return createValue(constValue);
			}
		}
		return null;
	}

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
