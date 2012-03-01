package org.openforis.idm.model;

import org.apache.commons.lang3.StringUtils;

/**
 * @author G. Miceli
 */
public final class Field<T> {
	private T value;
	private String remarks;
	private Character symbol;
	private Class<T> valueType;
	private Attribute<?,?> attribute;
	
	private Field(Class<T> valueType, Attribute<?,?> attribute) {
		this.valueType = valueType;
		this.attribute = attribute;
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
			throw new UnsupportedOperationException("Field<"+valueType.getName()+"> not supported");
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
