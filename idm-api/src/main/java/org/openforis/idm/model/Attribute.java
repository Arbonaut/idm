/**
 * 
 */
package org.openforis.idm.model;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.openforis.idm.metamodel.AttributeDefault;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.model.expression.InvalidExpressionException;

/**
 * @author M. Togna
 * @author G. Miceli
 */
public abstract class Attribute<D extends AttributeDefinition, V> extends Node<D> {

	private static final long serialVersionUID = 1L;

	ArrayList<AttributeField<?>> attributeFields;
	
	private boolean defaultValue;
	
	Attribute() {
		this.attributeFields = new ArrayList<AttributeField<?>>();
	}
	
	protected Attribute(D definition, Class<?>... fieldTypes) {
		super(definition);
		initFields(fieldTypes);
	}

	private void initFields(Class<?>... fieldTypes) {
		this.attributeFields = new ArrayList<AttributeField<?>>(fieldTypes.length);
		for (Class<?> type : fieldTypes) {
			this.attributeFields.add(AttributeField.newInstance(type));
		}
	}
	
	public AttributeField<?> getField(int idx) {
		return attributeFields.get(idx);
	}

	public int getFieldCount() {
		return attributeFields.size();
	}
	
	/**
	 * Reset value and symbol
	 */
	public void clearValue() {
		for (AttributeField<?> field : attributeFields) {
			field.setValue(null);
		}
	}

	/**
	 * Reset all properties of all attributeFields (remarks, value, symbol)
	 */
	public void clearFields() {
		for (AttributeField<?> field : attributeFields) {
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
		for (AttributeField<?> field : attributeFields) {
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
