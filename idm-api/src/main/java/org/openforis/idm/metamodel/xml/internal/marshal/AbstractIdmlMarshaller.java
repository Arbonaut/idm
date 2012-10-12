package org.openforis.idm.metamodel.xml.internal.marshal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.openforis.idm.metamodel.xml.IdmlConstants;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/**
 * @author G. Miceli
 *
 * @param <P>
 */
public abstract class AbstractIdmlMarshaller<T,P> {
	private T sourceObject;
	private XmlSerializer xmlSerializer;
	private List<AbstractIdmlMarshaller<?,T>> childMarshallers;
	private String encoding;
	private String tagNamespace;
	private String tagName;
	private boolean includeEmpty;
	private Writer writer;
	
	protected AbstractIdmlMarshaller() {
		this.includeEmpty = false; 
	}

	protected AbstractIdmlMarshaller(String tag) {
		this(IdmlConstants.IDML3_NAMESPACE_URI, tag);
	}

	protected AbstractIdmlMarshaller(String tagNamespace, String tagName) {
		this();
		this.tagNamespace = tagNamespace;
		this.tagName = tagName;
	}

	protected T getSourceObject() {
		return sourceObject;
	}

	protected void setSourceObject(T sourceObject) {
		this.sourceObject = sourceObject;
	}

	protected XmlSerializer getSerializer() {
		return xmlSerializer;
	}
	
	protected void setXmlSerializer(XmlSerializer serializer) {
		this.xmlSerializer = serializer;
	}
	
	protected String getEncoding() {
		return encoding;
	}

	protected void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public boolean isIncludeEmpty() {
		return includeEmpty;
	}

	public void setIncludeEmpty(boolean includeEmpty) {
		this.includeEmpty = includeEmpty;
	}

	synchronized
	public void marshal(T sourceObject, OutputStream os, String enc) throws IOException {
		XmlSerializer ser = createXmlSerializer();
		ser.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
	    ser.setOutput(os, enc);
	    Writer writer = new OutputStreamWriter(os, enc);
	    marshal(ser, enc, writer, sourceObject);
	}

	private static XmlSerializer createXmlSerializer() {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			return factory.newSerializer();
		} catch (XmlPullParserException e) {
			throw new RuntimeException(e);
		}
	}

	synchronized
	private void marshal(XmlSerializer serializer, String encoding, Writer wr, T sourceObject) throws IOException {
		this.xmlSerializer = serializer;
		this.writer = wr;
	    this.encoding = encoding;
		marshal(sourceObject);
	}

	protected final void marshal(T sourceObject) throws IOException {
		if ( includeEmpty || sourceObject != null ) {
			this.sourceObject = sourceObject;
			start();
			attributes();
			body();
			end();
		}
	}

	protected void start() throws IOException {
		if ( tagName != null ) {
			xmlSerializer.startTag(tagNamespace, tagName);
		}
	}

	protected void attributes() throws IOException {
		// no-op
	}

	protected void body() throws IOException {
		if ( childMarshallers != null ) {
			for (AbstractIdmlMarshaller<?,T> ser : childMarshallers) {
				ser.marshalInstances(this);
			}
		}
	}

	private void marshalInstances(AbstractIdmlMarshaller<P,?> parentSerializer) throws IOException {
		this.xmlSerializer = parentSerializer.xmlSerializer;
	    this.encoding = parentSerializer.encoding;
	    this.writer = parentSerializer.writer;
	    P parentObject = parentSerializer.sourceObject;
		marshalInstances(parentObject);
	}

	/**
	 * Override this method to extract instanced from parent.  
	 * Should call marshal() on List or single instances 
	 * @param parentObject
	 * @throws IOException
	 */
	protected void marshalInstances(P parentObject) throws IOException {
		// TODO Auto-generated method stub
	}

	protected void marshal(List<T> sourceObjects) throws IOException {
		if ( sourceObjects == null || sourceObjects.isEmpty() ) {
			if ( includeEmpty ) {
				marshal((T) null);
			}
		} else {
			for (T obj : sourceObjects) {
				marshal(obj);
			}
		}
	}

	protected void end() throws IOException {
		if ( tagName != null ) {
			xmlSerializer.endTag(tagNamespace, tagName);
		}
	}

	public void addChildMarshallers(AbstractIdmlMarshaller<?,T>... marshallers) {
		if ( childMarshallers == null ) {
			this.childMarshallers = new ArrayList<AbstractIdmlMarshaller<?,T>>(marshallers.length); 
		}
		
		for (AbstractIdmlMarshaller<?,T> ser : marshallers) {
			ser.setXmlSerializer(xmlSerializer);
			childMarshallers.add(ser);
		}
	}

	
	public void setPrefix(String prefix, String namespaceUri) throws IOException{
		xmlSerializer.setPrefix(prefix, namespaceUri);
	}

	protected void setDefaultNamespace(String namespaceUri) throws IOException {
		setPrefix("", namespaceUri);
	}

	protected void attribute(String name, String value) 
				throws IOException {
		xmlSerializer.attribute("", name, value);
	}
	
	protected void attribute(String name, Object value) 
			throws IOException {
		if ( value != null ) {
			attribute(name, value.toString());
		}
	}

	protected void attribute(String ns, String name, String value) 
				throws IOException {
		xmlSerializer.attribute(ns, name, value);
	}

	protected void cdsect(String cdata) throws IOException {
		xmlSerializer.cdsect(cdata);
	}

	protected void comment(String comment) throws IOException {
		xmlSerializer.comment(comment);
	}

	protected void endDocument() throws IOException {
		xmlSerializer.endDocument();
	}

	protected void endTag(String ns, String name) throws IOException {
		xmlSerializer.endTag(ns, name);
	}

	protected void endTag(String name) throws IOException {
		xmlSerializer.endTag(tagNamespace, name);
	}

	protected void startDocument() throws IOException {
		xmlSerializer.startDocument(getEncoding(), true);
	}

	protected void startTag(String ns, String name) throws IOException{
		xmlSerializer.startTag(ns, name);
	}

	protected void startTag(String name) throws IOException{
		xmlSerializer.startTag(tagNamespace, name);
	}

	protected void text(String text) throws IOException  {
		xmlSerializer.text(text);
	}
	
	protected void writeXml(String xml) throws IOException {
		xmlSerializer.flush();
		writer.write(xml);
		writer.flush();
	}
}
