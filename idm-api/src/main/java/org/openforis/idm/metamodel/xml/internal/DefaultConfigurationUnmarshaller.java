package org.openforis.idm.metamodel.xml.internal;

import org.openforis.idm.metamodel.xml.ConfigurationUnmarshaller;

/**
 * @author G. Miceli
 */
public class DefaultConfigurationUnmarshaller implements ConfigurationUnmarshaller<TextConfiguration> {

	private static DefaultConfigurationUnmarshaller INSTANCE = new DefaultConfigurationUnmarshaller();
	
	@Override
	public TextConfiguration unmarshal(String body) {
		return new TextConfiguration(body);
	}

	public static DefaultConfigurationUnmarshaller getInstance() {
		return INSTANCE;
	}
}
