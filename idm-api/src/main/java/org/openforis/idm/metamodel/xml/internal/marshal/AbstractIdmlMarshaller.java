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
public abstract class AbstractIdmlMarshaller<T, P> {

	private XmlSerializer xmlSerializer;
	private List<AbstractIdmlMarshaller<?,T>> childMarshallers;
	private String encoding;
	private String tagNamespace;
	private String tagName;
	private boolean includeEmpty;
	private Writer writer;
	private String listWrapperTag;
	
	protected AbstractIdmlMarshaller() {
		this(IdmlConstants.IDML3_NAMESPACE_URI, null);
	}
	
	protected AbstractIdmlMarshaller(String tag) {
		this(IdmlConstants.IDML3_NAMESPACE_URI, tag);
	}

	protected AbstractIdmlMarshaller(String tagNamespace, String tagName) {
		this.includeEmpty = false; 
		this.tagNamespace = tagNamespace;
		this.tagName = tagName;
	}

	protected XmlSerializer getXmlSerializer() {
		return xmlSerializer;
	}
	
	public boolean isIncludeEmpty() {
		return includeEmpty;
	}

	public void setIncludeEmpty(boolean includeEmpty) {
		this.includeEmpty = includeEmpty;
	}

	public void setListWrapperTag(String listWrapperTag) {
		this.listWrapperTag = listWrapperTag;
	}
	
	synchronized
	public void marshal(T sourceObject, OutputStream os, String enc) throws IOException {
		XmlSerializer ser = createXmlSerializer();
		ser.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
	    ser.setOutput(os, enc);
	    Writer writer = new OutputStreamWriter(os, enc);
	    this.xmlSerializer = ser;
		this.writer = writer;
		this.encoding = enc;
		marshal(sourceObject);
	}

	private static XmlSerializer createXmlSerializer() {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			return factory.newSerializer();
		} catch (XmlPullParserException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Main method which calls start, attributes, body and end
	 * @param sourceObject
	 * @throws IOException
	 */
	protected void marshal(T sourceObject) throws IOException {
		if ( includeEmpty || sourceObject != null ) {
			start(sourceObject);
			attributes(sourceObject);
			body(sourceObject);
			end(sourceObject);
		}
	}

	protected void start(T sourceObject) throws IOException {
		if ( tagName != null ) {
			xmlSerializer.startTag(tagNamespace, tagName);
		}
	}

	protected void attributes(T sourceObject) throws IOException {
		// no-op
	}

	protected void body(T sourceObject) throws IOException {
		marshalChildren(sourceObject);
	}

	protected void marshalChildren(T parentObject) throws IOException {
		if ( childMarshallers != null ) {
			for (AbstractIdmlMarshaller<?,T> ser : childMarshallers) {
				prepareChildMarshaller(ser);
				ser.marshalInstances(parentObject);
			}
		}
	}

	/**
	 * Override this method to extract instances from parent.  
	 * Should call marshal() on List or single instances 
	 * @param parentObject
	 * @throws IOException
	 */
	protected void marshalInstances(P parentObject) throws IOException {
		// TODO Auto-generated method stub
	}

	protected void marshal(List<? extends T> sourceObjects) throws IOException {
		if ( sourceObjects == null || sourceObjects.isEmpty() ) {
			if ( includeEmpty ) {
				startList();
				marshal((T) null);
				endList();
			}
		} else {
			startList();
			for (T obj : sourceObjects) {
				marshal(obj);
			}
			endList();
		}
	}

	protected void startList() throws IOException {
		if ( listWrapperTag != null ) {
			startTag(listWrapperTag);
		}
	}
	
	protected void endList() throws IOException {
		if ( listWrapperTag != null ) {
			endTag(listWrapperTag);
		}
	}

	protected void end(T sourceObject) throws IOException {
		if ( tagName != null ) {
			xmlSerializer.endTag(tagNamespace, tagName);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addChildMarshallers(AbstractIdmlMarshaller<?,?>... marshallers) {
		if ( childMarshallers == null ) {
			this.childMarshallers = new ArrayList<AbstractIdmlMarshaller<?,T>>(marshallers.length); 
		}
		
		for (AbstractIdmlMarshaller im : marshallers) {
			childMarshallers.add(im);
		}
	}

	protected final void prepareChildMarshaller(AbstractIdmlMarshaller<?,?> im) {
		im.xmlSerializer = this.xmlSerializer;
		im.encoding = this.encoding;
		im.writer = this.writer;
	}

	
	public void setPrefix(String prefix, String namespaceUri) throws IOException{
		xmlSerializer.setPrefix(prefix, namespaceUri);
	}

	protected void setDefaultNamespace(String namespaceUri) throws IOException {
		setPrefix("", namespaceUri);
	}

	protected void attribute(String name, String value) throws IOException {
		if ( value != null ) {
			xmlSerializer.attribute("", name, value);
		}
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
		xmlSerializer.startDocument(encoding, true);
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
