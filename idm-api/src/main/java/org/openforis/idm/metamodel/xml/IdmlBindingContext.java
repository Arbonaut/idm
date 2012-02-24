package org.openforis.idm.metamodel.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.openforis.idm.metamodel.Configuration;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.internal.ConfigurationXmlAdapter;
import org.openforis.idm.metamodel.xml.internal.DefaultConfigurationAdapter;

/**
 * @author G. Miceli
 */
public class IdmlBindingContext {
	private final JAXBContext surveyJaxbContext;
	protected static final ConfigurationXmlAdapter DEFAULT_CONFIG_ADAPTER;
	private Class<? extends Survey> surveyClass;
	
	private ConfigurationAdapter<? extends Configuration> configurationAdapter;

	public IdmlBindingContext() {
		this(Survey.class);
	}
	
	public IdmlBindingContext(Class<? extends Survey> surveyClass) {
		try {
			this.surveyClass = surveyClass;
			this.surveyJaxbContext = JAXBContext.newInstance(surveyClass);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
	static {
		DEFAULT_CONFIG_ADAPTER = new ConfigurationXmlAdapter(DefaultConfigurationAdapter.getInstance());
	}
	
	public ConfigurationAdapter<? extends Configuration> getConfigurationAdapter() {
		return configurationAdapter;
	}

	public void setConfigurationAdapter(ConfigurationAdapter<? extends Configuration> configurationAdapter) {
		this.configurationAdapter = configurationAdapter;
	}
	
	public SurveyMarshaller createSurveyMarshaller(){
		try {
			Marshaller marshaller = surveyJaxbContext.createMarshaller();
			if ( configurationAdapter == null ) {
				marshaller.setAdapter(DEFAULT_CONFIG_ADAPTER);
			} else {
				marshaller.setAdapter(new ConfigurationXmlAdapter(configurationAdapter));
			}
			return new SurveyMarshaller(marshaller);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
	public SurveyUnmarshaller createSurveyUnmarshaller(){
		try {
			Unmarshaller unmarshaller = surveyJaxbContext.createUnmarshaller();
			if ( configurationAdapter == null ) {
				unmarshaller.setAdapter(DEFAULT_CONFIG_ADAPTER);
			} else {
				unmarshaller.setAdapter(new ConfigurationXmlAdapter(configurationAdapter));
			}
			return new SurveyUnmarshaller(unmarshaller, surveyClass);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
/*	
	static JAXBContext getInstance() {
		return JAXB_CONTEXT;
	}
	
	static ConfigurationXmlAdapter getDefaultConfigurationAdapter() {
		return DEFAULT_CONFIG_ADAPTER;
	}
*/	
}
