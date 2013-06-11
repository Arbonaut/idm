package org.openforis.idm.metamodel.xml;

import java.io.IOException;
import java.io.InputStream;

import org.openforis.idm.metamodel.SurveyCodeListPersisterContext;
import org.openforis.idm.metamodel.xml.internal.unmarshal.SurveyCodeListPersister;

/**
 * Load code lists inside a XML file and store them into the database
 * using a ExternalCodeListPersister
 * 
 * @author S. Ricci
 */
public class SurveyCodeListPersisterBinder {
	
	private static final String UTF8_ENCODING = "UTF-8";
	
	private SurveyCodeListPersisterContext context;

	public SurveyCodeListPersisterBinder(SurveyCodeListPersisterContext context) {
		this.context = context;
	}

	public SurveyCodeListPersisterContext getContext() {
		return context;
	}
	
	public void exportFromXMLAndStore(InputStream is) throws IdmlParseException {
		try {
			SurveyCodeListPersister persister = new SurveyCodeListPersister(this);
			persister.parse(is, UTF8_ENCODING);
		} catch (XmlParseException e) {
			throw new IdmlParseException(e);
		} catch (IOException e) {
			throw new IdmlParseException(e);
		}
	}	
	
}
