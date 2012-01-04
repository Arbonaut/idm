package org.openforis.idm.metamodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.CodeList.CodeScope;
import org.openforis.idm.metamodel.CodeList.CodeType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {  "codeScope", "codeType" }) 
class CodingScheme implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "scope")
	@XmlJavaTypeAdapter(value = CodingScheme.CodeScopeAdapter.class)
	private CodeScope codeScope;

	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(value = CodingScheme.CodeTypeAdapter.class)
	private CodeType codeType;

	public CodeType getCodeType() {
		return this.codeType;
	}

	public CodeScope getCodeScope() {
		return this.codeScope;
	}

	private static class CodeTypeAdapter extends XmlAdapter<String, CodeType> {
		@Override
		public CodeType unmarshal(String v) throws Exception {
			return v==null ? null : CodeType.valueOf(v.toUpperCase());
		}

		@Override
		public String marshal(CodeType v) throws Exception {
			return v==null ? null : v.toString().toLowerCase();
		}
	}

	private static class CodeScopeAdapter extends XmlAdapter<String, CodeScope> {
		@Override
		public CodeScope unmarshal(String v) throws Exception {
			return v==null ? null : CodeScope.valueOf(v.toUpperCase());
		}

		@Override
		public String marshal(CodeScope v) throws Exception {
			return v==null ? null : v.toString().toLowerCase();
		}
	}
}