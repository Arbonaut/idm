package org.openforis.idm.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class Field<T> implements Serializable {
	
	private static final long serialVersionUID = 1;
	
	/* WARNING: deleting or reordering fields will break protostuff deserialization! */
	
	Class<T> valueType;
	T value;
	String remarks;
	Character symbol;
	Attribute<?,?> attribute;
	State state;
	
	private Field(Class<T> valueType, Attribute<?,?> attribute) {
		this.valueType = valueType;
		this.attribute = attribute;
		state = new State();
	}

	public static <C> Field<C> newInstance(Class<C> valueType, Attribute<?,?> attribute) {
		return new Field<C>(valueType, attribute);
	}
	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
		this.symbol = null;
		attribute.onUpdateValue();
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

	public boolean isEmpty() {
		return value == null || value.toString().trim().isEmpty();
	}

	public State getState() {
		return state;
	}
	
	@SuppressWarnings("unchecked")
	public T parseValue(String s) {
		if ( StringUtils.isBlank(s) ) {
			return null;
		} else if ( valueType == Boolean.class ) {
			return (T) Boolean.valueOf(s);
		} else if ( valueType == Integer.class ) {
			return (T) Integer.valueOf(s);
		} else if ( valueType == Long.class ) {
			return (T) Long.valueOf(s);
		} else if ( valueType == Double.class ) {
			return (T) Double.valueOf(s);
		} else if ( valueType == String.class ) {
			return (T) s;
		} else {
			throw new UnsupportedOperationException("AttributeField<"+valueType.getName()+"> not supported");
		}
	}

	public void setValueFromString(String s) {
		this.value = parseValue(s);
		attribute.onUpdateValue();
	}

	/**
	 * Reset all properties (value, remarks and symbol)
	 */
	public void clear() {
		this.value = null;
		this.remarks = null;
		this.symbol = null;
		attribute.onUpdateValue();
	}
}
