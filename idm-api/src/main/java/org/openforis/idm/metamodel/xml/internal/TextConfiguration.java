package org.openforis.idm.metamodel.xml.internal;

import org.openforis.idm.metamodel.Configuration;

/**
 * @author G. Miceli
 */
public class TextConfiguration implements Configuration {
	private String text;

	TextConfiguration(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
