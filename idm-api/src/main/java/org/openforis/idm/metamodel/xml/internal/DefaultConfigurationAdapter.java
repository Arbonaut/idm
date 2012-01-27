package org.openforis.idm.metamodel.xml.internal;

import org.openforis.idm.metamodel.xml.ConfigurationAdapter;
import org.w3c.dom.Element;

/**
 * @author G. Miceli
 */
public class DefaultConfigurationAdapter implements ConfigurationAdapter<UnprocessedConfiguration> {

	private static DefaultConfigurationAdapter INSTANCE = new DefaultConfigurationAdapter();
	
	@Override
	public UnprocessedConfiguration unmarshal(Element elem) {
		return new UnprocessedConfiguration(elem);
	}

	@Override
	public Element marshal(UnprocessedConfiguration config) {
		return config.getElement();
	}
	
	public static DefaultConfigurationAdapter getInstance() {
		return INSTANCE;
	}
}
