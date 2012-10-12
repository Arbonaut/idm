package org.openforis.idm.metamodel.xml.internal.unmarshal;

import org.openforis.idm.metamodel.PlainTextApplicationOptions;
import org.openforis.idm.metamodel.xml.ApplicationOptionsBinder;

/**
 * @author G. Miceli
 */
public class PlainTextApplicationOptionsBinder implements ApplicationOptionsBinder<PlainTextApplicationOptions> {

	private String optionsType;
	
	public PlainTextApplicationOptionsBinder(String optionsType) {
		this.optionsType = optionsType;
	}

	@Override
	public PlainTextApplicationOptions unmarshal(String body) {
		return new PlainTextApplicationOptions(body, optionsType);
	} 

	@Override
	public String marshal(PlainTextApplicationOptions options) {
		return options.getText();
	}

	@Override
	public boolean isSupported(String type) {
		return optionsType.equals(type);
	}
}
