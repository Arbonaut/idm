package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.w3c.dom.Element;

public class ConfigurationTypeAdapter extends XmlAdapter<Object, String> {

	@Override
	public String unmarshal(Object v) throws Exception {
		String value = "";
		if ((v != null) && (v instanceof Element)) {
			Element element = (Element) v;
			java.io.StringWriter out = new java.io.StringWriter();
			javax.xml.transform.Transformer serializer = javax.xml.transform.TransformerFactory.newInstance().newTransformer();
			serializer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
			serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			serializer.setOutputProperty("omit-xml-declaration", "yes");
			serializer.transform(new javax.xml.transform.dom.DOMSource(element), new javax.xml.transform.stream.StreamResult(out));
			value = out.toString();
		}
		return value;
	}

	@Override
	public Object marshal(String v) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}