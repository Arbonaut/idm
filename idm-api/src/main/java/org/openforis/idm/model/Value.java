package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Value {
	enum Symbol {
		BLANK, NOT_APPLICABLE, ILLEGIBLE
	}

	/**
	 * @return Returns the symbol.
	 * @uml.property name="symbol" readOnly="true"
	 */
	Symbol getSymbol();

	/**
	 * @return Returns the remarks.
	 * @uml.property name="remarks" readOnly="true"
	 */
	String getRemarks();

	abstract boolean isBlank();
}
