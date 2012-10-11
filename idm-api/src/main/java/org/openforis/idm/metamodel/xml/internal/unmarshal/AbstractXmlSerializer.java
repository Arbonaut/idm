package org.openforis.idm.metamodel.xml.internal.unmarshal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/**
 * @author G. Miceli
 *
 * @param <P>
 */
public abstract class AbstractXmlSerializer<T,P> {
	private T sourceObject;
	private XmlSerializer serializer;
	private List<AbstractXmlSerializer<?,T>> childSerializers;
	private String encoding;
	private String tagNamespace;
	private String tagName;
	private boolean includeEmpty;
	
	protected AbstractXmlSerializer() {
		this.includeEmpty = false; 
	}

	protected AbstractXmlSerializer(String tagNamespace, String tagName) {
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
		return serializer;
	}
	
	protected void setSerializer(XmlSerializer serializer) {
		this.serializer = serializer;
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
	public void serialize(T sourceObject, OutputStream os, String enc) throws IOException {
		XmlSerializer ser = createSerializer();
		ser.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
	    ser.setOutput(os, enc);
	    serialize(ser, enc, sourceObject);
	}

	private static XmlSerializer createSerializer() {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			return factory.newSerializer();
		} catch (XmlPullParserException e) {
			throw new RuntimeException(e);
		}
	}

	synchronized
	private void serialize(XmlSerializer serializer, String encoding, T sourceObject) throws IOException {
		this.serializer = serializer;
	    this.encoding = encoding;
		serialize(sourceObject);
	}

	protected final void serialize(T sourceObject) throws IOException {
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
			serializer.startTag(tagNamespace, tagName);
		}
	}

	protected void attributes() throws IOException {
		// no-op
	}

	protected void body() throws IOException {
		if ( childSerializers != null ) {
			for (AbstractXmlSerializer<?,T> ser : childSerializers) {
				ser.serializeInstances(this);
			}
		}
	}

	private void serializeInstances(AbstractXmlSerializer<P,?> parentSerializer) throws IOException {
		this.serializer = parentSerializer.serializer;
	    this.encoding = parentSerializer.encoding;
	    P parentObject = parentSerializer.sourceObject;
		serializeInstances(parentObject);
	}

	protected void serializeInstances(P parentObject) throws IOException {
		// TODO Auto-generated method stub
	}

	protected void serialize(List<T> sourceObjects) throws IOException {
		if ( sourceObjects == null || sourceObjects.isEmpty() ) {
			if ( includeEmpty ) {
				serialize((T) null);
			}
		} else {
			for (T obj : sourceObjects) {
				serialize(obj);
			}
		}
	}

	protected void end() throws IOException {
		if ( tagName != null ) {
			serializer.endTag(tagNamespace, tagName);
		}
	}

	public void addChildSerializers(AbstractXmlSerializer<?,T>... serializers) {
		if ( childSerializers == null ) {
			this.childSerializers = new ArrayList<AbstractXmlSerializer<?,T>>(serializers.length); 
		}
		
		for (AbstractXmlSerializer<?,T> ser : serializers) {
			ser.setSerializer(serializer);
			childSerializers.add(ser);
		}
	}

	
	public void setPrefix(String prefix, String namespaceUri) throws IOException{
		serializer.setPrefix(prefix, namespaceUri);
	}

	protected void setDefaultNamespace(String namespaceUri) throws IOException {
		setPrefix("", namespaceUri);
	}

	protected void attribute(String name, String value) 
				throws IOException {
		serializer.attribute("", name, value);
	}
	
	protected void attribute(String name, Object value) 
			throws IOException {
		if ( value != null ) {
			attribute(name, value.toString());
		}
	}

	protected void attribute(String ns, String name, String value) 
				throws IOException {
		serializer.attribute(ns, name, value);
	}

	protected void cdsect(String cdata) throws IOException {
		serializer.cdsect(cdata);
	}

	protected void comment(String comment) throws IOException {
		serializer.comment(comment);
	}

	protected void endDocument() throws IOException {
		serializer.endDocument();
	}

	protected void endTag(String ns, String name) throws IOException {
		serializer.endTag(ns, name);
	}

	protected void endTag(String name) throws IOException {
		serializer.endTag(tagNamespace, name);
	}

	protected void startDocument() throws IOException {
		serializer.startDocument(getEncoding(), true);
	}

	protected void startTag(String ns, String name) throws IOException{
		serializer.startTag(ns, name);
	}

	protected void startTag(String name) throws IOException{
		serializer.startTag(tagNamespace, name);
	}

	protected void text(String text) throws IOException  {
		serializer.text(text);
	}
}
