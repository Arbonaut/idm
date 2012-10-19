package org.openforis.idm.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * @author G. Miceli
 */
public final class AttributeField<T> implements Serializable {
	
	private static final long serialVersionUID = 1;
	
	/* WARNING: deleting or reordering fields will break protostuff deserialization! */
	
	Class<T> valueType;
	T value;
	String remarks;
	Character symbol;

	AttributeField() {
	}
	
	private AttributeField(Class<T> valueType) {
		this.valueType = valueType;  
	}

	public static <C> AttributeField<C> newInstance(Class<C> valueType) {
		return new AttributeField<C>(valueType);
	}
	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
		this.symbol = null;
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
	}

	/**
	 * Reset all properties (value, remarks and symbol)
	 */
	public void clear() {
		this.value = null;
		this.remarks = null;
		this.symbol = null;
	}
}
