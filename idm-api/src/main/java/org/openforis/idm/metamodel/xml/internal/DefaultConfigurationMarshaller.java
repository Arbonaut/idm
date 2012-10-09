package org.openforis.idm.metamodel.xml.internal;

import org.openforis.idm.metamodel.xml.ConfigurationMarshaller;

/**
 * @author G. Miceli
 */
public class DefaultConfigurationMarshaller implements ConfigurationMarshaller<TextConfiguration> {

	private static DefaultConfigurationMarshaller INSTANCE = new DefaultConfigurationMarshaller();
	
	@Override
	public String marshal(TextConfiguration config) {
		return config.getText();
	}
	
	public static DefaultConfigurationMarshaller getInstance() {
		return INSTANCE;
	}
}
