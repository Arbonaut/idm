package org.openforis.idm.model;

/**
 * @author G. Miceli
 */
public abstract class Field<T> {
	private T value;
	private String remarks;
	private Character symbol;

	public Field() {
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
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

	public abstract T parseValue(String s);

	public void setValueFromString(String s) {
		this.value = parseValue(s);
	}

}
