package org.openforis.idm.metamodel.xml.internal.unmarshal;

import java.io.StringWriter;

import org.kxml2.io.KXmlSerializer;
import org.openforis.idm.metamodel.xml.SurveyIdmlBinder;
import org.xmlpull.v1.XmlSerializer;

/**
 * Load a Survey object from IDML
 * 
 * @author G. Miceli
 */
public class SurveySerializer {

	private SurveyIdmlBinder binder;

	public SurveySerializer(SurveyIdmlBinder binder) {
		this.binder = binder;
	}
	
	public static void main(String[] args) {
	    XmlSerializer serializer = new KXmlSerializer();
	    StringWriter writer = new StringWriter();
	    try {
	        serializer.setOutput(writer);
	        serializer.startDocument("UTF-8", true);
	        serializer.startTag("", "survey");
	        serializer.attribute("", "number", String.valueOf(messages.size()));
	        for (Message msg: messages){
	            serializer.startTag("", "message");
	            serializer.attribute("", "date", msg.getDate());
	            serializer.startTag("", "title");
	            serializer.text(msg.getTitle());
	            serializer.endTag("", "title");
	            serializer.startTag("", "url");
	            serializer.text(msg.getLink().toExternalForm());
	            serializer.endTag("", "url");
	            serializer.startTag("", "body");
	            serializer.text(msg.getDescription());
	            serializer.endTag("", "body");
	            serializer.endTag("", "message");
	        }
	        serializer.endTag("", "messages");
	        serializer.endDocument();
	        return writer.toString();
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    } 
	}
}
