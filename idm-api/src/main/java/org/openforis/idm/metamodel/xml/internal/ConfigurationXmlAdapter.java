package org.openforis.idm.metamodel.xml.internal;

//import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import org.openforis.idm.metamodel.Configuration;
import org.openforis.idm.metamodel.xml.ConfigurationAdapter;
import org.w3c.dom.Element;


/**
 * @author G. Miceli
 * @author K. Waga
 */
public class ConfigurationXmlAdapter implements Converter<Configuration> {

	@SuppressWarnings("rawtypes")
	private ConfigurationAdapter configurationAdapter;
	
	public ConfigurationXmlAdapter() {
	}
	
	public ConfigurationXmlAdapter(ConfigurationAdapter<? extends Configuration> configurationAdapter) {
		this.configurationAdapter = configurationAdapter;
	}

	@Override
	public Configuration read(InputNode in) throws Exception {
		return configurationAdapter.unmarshal((Element)in);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(OutputNode out, Configuration config) throws Exception {
		Element el = configurationAdapter.marshal(config);
		out.setValue(el.getNodeValue());
		out.setName(el.getNodeName());
	}

	/*@Override
	public Configuration read(String e) throws Exception {
		Element el = ;
		return configurationAdapter.unmarshal(el);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String write(Configuration config) throws Exception {
		return configurationAdapter.marshal(config).toString();
	}*/

	/*@Override
	public Configuration unmarshal(Element e) {
		return configurationAdapter.unmarshal(e);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Element marshal(Configuration config)  {
		return configurationAdapter.marshal(config);
	}*/
}

/*public class ConfigurationXmlAdapter extends XmlAdapter<Element, Configuration> {

	@SuppressWarnings("rawtypes")
	private ConfigurationAdapter configurationAdapter;
	
	public ConfigurationXmlAdapter() {
	}
	
	public ConfigurationXmlAdapter(ConfigurationAdapter<? extends Configuration> configurationAdapter) {
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
}*/
