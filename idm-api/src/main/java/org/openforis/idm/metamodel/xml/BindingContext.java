package org.openforis.idm.metamodel.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.internal.ConfigurationXmlAdapter;
import org.openforis.idm.metamodel.xml.internal.DefaultConfigurationAdapter;

/**
 * @author G. Miceli
 */
class BindingContext {
	private static final JAXBContext INSTANCE;
	private static final ConfigurationXmlAdapter DEFAULT_CONFIG_ADAPTER;

	static {
		try {
			INSTANCE = JAXBContext.newInstance(Survey.class);
			DEFAULT_CONFIG_ADAPTER = new ConfigurationXmlAdapter(DefaultConfigurationAdapter.getInstance());
		} catch (JAXBException e) {
			throw new RuntimeException("Failed to initialize JAXBContext", e);
		}
	}
	
	static JAXBContext getInstance() {
		return INSTANCE;
	}
	
	static ConfigurationXmlAdapter getDefaultConfigurationAdapter() {
		return DEFAULT_CONFIG_ADAPTER;
	}
}
