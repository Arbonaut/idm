/**
 * 
 */
package org.openforis.idm.model;

import java.io.StringWriter;
import java.util.List;

import org.openforis.idm.metamodel.AttributeDefault;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.validation.ValidationResults;
import org.openforis.idm.metamodel.validation.Validator;
import org.openforis.idm.model.expression.InvalidExpressionException;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public abstract class Attribute<D extends AttributeDefinition, V> extends Node<D> {

	private Field<?>[] fields;
	
	private boolean defaultValue;
	
	private transient ValidationResults validationResults;
	
	protected Attribute(D definition, Class<?>... fieldTypes) {
		super(definition);
		initFields(fieldTypes);
	}

	private void initFields(Class<?>... fieldTypes) {
		this.fields = new Field[fieldTypes.length];
		for (int i = 0; i < fields.length; i++) {
			Class<?> t = fieldTypes[i];
			this.fields[i] = Field.newInstance(t);
		}
	}
	
	public Field<?> getField(int idx) {
		return fields[idx];
	}

	public int getFieldCount() {
		return fields.length;
	}
	
	/**
	 * Reset value and symbol
	 */
	public void clearValue() {
		for (Field<?> field : fields) {
			field.setValue(null);
		}
	}

	/**
	 * Reset all properties of all fields (remarks, value, symbol)
	 */
	public void clearFields() {
		for (Field<?> field : fields) {
			field.clear();
		}
	}
	/**
	 * @return a non-null, immutable value
	 */
	public abstract V getValue();

	/**
	 * @param value a non-null, immutable value to set
	 */
	public abstract void setValue(V value);
	
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
		for (Field<?> field : fields) {
			if ( !field.isEmpty() ) {
				return false;
			}
		}
		return true;
	}

	public boolean isDefaultValue() {
		return defaultValue;
	}

	public void applyDefaultValue() throws InvalidExpressionException {
		V value = getDefaultValue();
		if (value != null) {
			setValue(value);
			this.defaultValue = true;
		}
	}

	/**
	 * 
	 * @return list of failed validation rules
	 */
	public ValidationResults validateValue() {
		if ( validationResults == null ) {
			RecordContext recordContext = getRecordContext();
			Validator validator = recordContext.getValidator();
			validationResults = validator.validate(this);
		}
		return validationResults;
	}

	protected void clearValidationResults() {
		validationResults = null;
	}
	
	@Override
	protected void write(StringWriter sw, int indent) {
		V value = getValue();
		for (int i = 0; i < indent; i++) {
			sw.append('\t');
		}
		sw.append(getName());
		sw.append(": ");
		sw.append(value == null ? "!!null" : value.toString());
		sw.append("\n");
	}
}
