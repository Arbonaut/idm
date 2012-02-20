package org.openforis.idm.model;

public class Field<T> {
	private T value;
	private String remarks;
	private Character symbol;

	public Field() {
	}
	
	public Field(T value) {
		this.value = value;
	}
	
	public Field(T value, String remarks, Character symbol) {
		this.value = value;
		this.remarks = remarks;
		this.symbol = symbol;
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
}
