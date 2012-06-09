package org.openforis.idm.metamodel.xml.internal;

import org.openforis.idm.metamodel.Configuration;
import org.w3c.dom.Element;

/**
 * @author G. Miceli
 */
public class UnprocessedConfiguration implements Configuration {
	private Element element;

	UnprocessedConfiguration(Element element) {
		this.element = element;
	}
	
	public Element getElement() {
		return element;
	}
}
