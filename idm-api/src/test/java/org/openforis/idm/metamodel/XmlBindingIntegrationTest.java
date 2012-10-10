/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.openforis.idm.metamodel.xml.InvalidIdmlException;
import org.openforis.idm.metamodel.xml.SurveyIdmlBinder;
import org.openforis.idm.metamodel.xml.XmlParseException;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class XmlBindingIntegrationTest {

	private Log log = LogFactory.getLog(XmlBindingIntegrationTest.class);

	@Test
	public void roundTripMarshallingTest() throws IOException, InvalidIdmlException, XmlParseException {
		URL idm = ClassLoader.getSystemResource("test.idm.xml");
		InputStream is = idm.openStream();
		SurveyContext ctx = new DefaultSurveyContext();
		SurveyIdmlBinder su = new SurveyIdmlBinder(ctx); 
		Survey survey = su.unmarshal(is);

// TODO			
//			new File("target/test/output").mkdirs();
//			FileOutputStream fos = new FileOutputStream("target/test/output/marshalled.idm.xml");
//			SurveyMarshaller sm = new SurveyMarshaller();
//			sm.setIndent(true);
//			sm.marshal(survey, fos);
//			fos.flush();
//			fos.close();
	}
	
	public void validateValidIdmlTest() {
		// TODO
//		ValidationEvent[] validationEvents = e.getValidationEvents();
//		for (ValidationEvent validationEvent : validationEvents) {
//			log.error(validationEvent.getMessage());
//		}
	}
}
