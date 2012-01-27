package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class AlphanumericCode extends Code<String> {

	public AlphanumericCode(String code) {
		super(code);
	}

	public AlphanumericCode(String code, String qualifier) {
		super(code, qualifier);
	}
}
