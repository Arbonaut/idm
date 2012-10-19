package org.openforis.idm.metamodel.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

/**
 * @author G. Miceli
 * @author S. Ricci
 * 
 */
public class IdmlValidator {

	private static final String XML_SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";
	private static final String IDML_SCHEMA_RESOURCE_PATH = "/idml3.xsd";
	
	public IdmlValidator() {
	}

	public void validate(byte[] bytes) throws InvalidIdmlException {
		validate(new ByteArrayInputStream(bytes));
	}
	
	public void validate(InputStream is) throws InvalidIdmlException {
		try {
			SchemaFactory factory = SchemaFactory.newInstance(XML_SCHEMA_LANGUAGE);
			URL schemaLocation = getClass().getResource(IDML_SCHEMA_RESOURCE_PATH);
			Schema schema = factory.newSchema(schemaLocation);
			javax.xml.validation.Validator validator = schema.newValidator();
			Source source = new StreamSource(is);
			validator.validate(source);
		} catch (IOException e) {
			throw new InvalidIdmlException("Cannot find the xsd to validate the idml");
		} catch (SAXException e) {
			throw new InvalidIdmlException("Error validating the idml against the schema: " + e.getMessage());
		}
	}
}
