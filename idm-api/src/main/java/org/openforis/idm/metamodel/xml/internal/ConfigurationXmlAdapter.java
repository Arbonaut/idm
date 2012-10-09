package org.openforis.idm.metamodel.xml.internal;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.openforis.idm.metamodel.Configuration;
import org.openforis.idm.metamodel.xml.ConfigurationMarshaller;
import org.w3c.dom.Element;


/**
 * @author G. Miceli
 */
public class ConfigurationXmlAdapter extends XmlAdapter<Element, Configuration> {

	@SuppressWarnings("rawtypes")
	private ConfigurationMarshaller configurationAdapter;
	
	public ConfigurationXmlAdapter() {
	}
	
	public ConfigurationXmlAdapter(ConfigurationMarshaller<? extends Configuration> configurationAdapter) {
		this.configurationAdapter = configurationAdapter;
	}

	@Override
	public Configuration unmarshal(Element e) {
		return configurationAdapter.unmarshal(e);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Element marshal(Configuration config)  {
		return configurationAdapter.marshal(config);
	}
}
