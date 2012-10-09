package org.openforis.idm.metamodel.xml.internal;

import org.openforis.idm.metamodel.ApplicationOptions;

/**
 * @author G. Miceli
 */
public class PlainTextApplicationOptions implements ApplicationOptions {
	private String type;
	private String text;

	public PlainTextApplicationOptions() {
	}

	protected PlainTextApplicationOptions(String text, String type) {
		this.text = text;
		this.type = type;
	}


	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
