/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.openforis.idm.metamodel.xml.IdmlParseException;
import org.openforis.idm.metamodel.xml.SurveyIdmlBinder;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class XmlBindingIntegrationTest {

	private Log log = LogFactory.getLog(XmlBindingIntegrationTest.class);

	@Test
	public void roundTripMarshallingTest() throws IdmlParseException, IOException {
		URL idm = ClassLoader.getSystemResource("test.idm.xml");
		InputStream is = idm.openStream();
		SurveyContext ctx = new DefaultSurveyContext();
		SurveyIdmlBinder su = new SurveyIdmlBinder(ctx);
		
		Survey survey = su.unmarshal(is);
		
		StringWriter sw = new StringWriter();
		su.marshal(survey, sw);
		String idml2 = sw.toString();

		StringReader sr = new StringReader(idml2);
		Survey survey2 = su.unmarshal(sr);
		
		Assert.assertEquals(survey, survey2);
//// TODO			
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
