/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeListLabel extends LanguageSpecificText {

	public enum Type { ITEM, LIST }
	
	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(value = CodeListLabelTypeAdapter.class)
	private Type type;

	public Type getType() {
		return this.type;
	}

	private static class CodeListLabelTypeAdapter extends XmlAdapter<String, Type> {
		@Override
		public Type unmarshal(String v) throws Exception {
			return v==null ? null : Type.valueOf(v.toUpperCase());
		}

		@Override
		public String marshal(Type v) throws Exception {
			return v==null ? null : v.toString().toLowerCase();
		}
	}
}
