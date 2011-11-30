/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.CodeListLabel;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class CodeListLabelImpl extends LanguageSpecificTextImpl implements CodeListLabel {

	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(value = CodeListLabelTypeAdapter.class)
	private Type type;

	@Override
	public Type getType() {
		return this.type;
	}

	@Override
	public void setType(Type type) {
		this.type = type;
	}

	private static class CodeListLabelTypeAdapter extends XmlAdapter<String, Type> {

		@Override
		public Type unmarshal(String v) throws Exception {
			return Type.valueOf(v.toUpperCase());
		}

		@Override
		public String marshal(Type v) throws Exception {
			return v.toString().toLowerCase();
		}
	}

}
