package org.openforis.idm.metamodel.xml;

import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.Check.Flag;
import org.xml.sax.ContentHandler;

/**
 * @author G. Miceli
 */
public class SurveyMarshaller {

	public void marshal(Survey survey, OutputStream os, boolean indent) throws IOException {
		try {
			JAXBContext jc = BindingContext.getInstance();
			Marshaller marshaller = jc.createMarshaller();
			BindingContext.setAdapters(marshaller);
//			marshaller.setProperty("jaxb.formatted.output", true);
//			marshaller.setProperty("jaxb.encoding", "UTF-8");

			// JAXP Transformer not respecting CDATA_SECTION_ELEMENTS
//			SAXTransformerFactory tfactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
//			TransformerHandler handler = tfactory.newTransformerHandler();
//			Transformer t = handler.getTransformer();
//			t.setOutputProperty(OutputKeys.METHOD, "xml");
//			t.setOutputProperty(OutputKeys.INDENT, "no");
//			t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//			t.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "{http://www.openforis.org/idml/3.0}wkt");
//			Result outputResult = new StreamResult(System.out);
//			handler.setResult(outputResult);
			
			// Using deprecated Xerces form now...
			OutputFormat of = new OutputFormat();
			of.setCDataElements(new String[] { "http://www.openforis.org/idml/3.0^wkt" }); //
			of.setEncoding("UTF-8");
			of.setIndenting(indent);
			XMLSerializer serializer1 = new XMLSerializer(of);
			serializer1.setOutputByteStream(os);
			XMLSerializer serializer = serializer1;
			ContentHandler handler = serializer.asContentHandler();
			
			// TODO when JDK includes a custom NamespacePrefixMapper, use it to avoid repeated namespace declarations 
			marshaller.marshal(survey, handler);
			// marshaller.marshal(this, os);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
