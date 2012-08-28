package org.openforis.idm.metamodel.xml;

import java.io.OutputStream;

import org.openforis.idm.metamodel.Survey;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * @author G. Miceli
 * @author S. Ricci
 * 
 */
public class SurveyMarshaller {

	private boolean indent;
//	private Marshaller marshaller;
//
//	SurveyMarshaller(Marshaller marshaller) {
//		super();
//		this.marshaller = marshaller;
//	}

	public boolean isIndent() {
		return indent;
	}

	public void setIndent(boolean indent) {
		this.indent = indent;
	}
	
	/*
	public void marshal(Survey survey, OutputStream os) throws IOException {
		try {
		
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
	*/
	
	public void marshal(Survey survey, OutputStream os) throws Exception {
		Serializer serializer = new Persister();
		serializer.write(survey, os);
	}
}
