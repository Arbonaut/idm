package org.openforis.idm.metamodel.xml.internal.unmarshal;

import org.openforis.idm.metamodel.PlainTextApplicationOptions;
import org.openforis.idm.metamodel.xml.ApplicationOptionsBinder;

/**
 * @author G. Miceli
 */
public class PlainTextApplicationOptionsBinder implements ApplicationOptionsBinder<PlainTextApplicationOptions> {

	public PlainTextApplicationOptionsBinder() {
	}

	@Override
	public PlainTextApplicationOptions unmarshal(String type, String body) {
		PlainTextApplicationOptions options = new PlainTextApplicationOptions();
		options.setType(type);
		options.setBody(body);
		return options;
	} 

	@Override
	public String marshal(PlainTextApplicationOptions options) {
		return options.getBody();
	}

	@Override
	public boolean isSupported(String type) {
		return true;
	}
}
