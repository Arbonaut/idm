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

import org.openforis.idm.metamodel.CodingScheme;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "codeType", "codeScope", "name" })
public class CodingSchemeImpl implements CodingScheme {

	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(value = CodeTypeAdapter.class)
	private CodeType codeType;

	@XmlAttribute(name = "scope")
	@XmlJavaTypeAdapter(value = CodeScopeAdapter.class)
	private CodeScope codeScope;

	@XmlAttribute(name = "name")
	private String name;

	@Override
	public CodeType getCodeType() {
		return this.codeType;
	}

	@Override
	public void setCodeType(CodeType codeType) {
		this.codeType = codeType;
	}

	@Override
	public CodeScope getCodeScope() {
		return this.codeScope;
	}

	@Override
	public void setCodeScope(CodeScope codeScope) {
		this.codeScope = codeScope;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	private static class CodeScopeAdapter extends XmlAdapter<String, CodeScope> {

		@Override
		public CodeScope unmarshal(String v) throws Exception {
			return CodeScope.valueOf(v.toUpperCase());
		}

		@Override
		public String marshal(CodeScope v) throws Exception {
			return v.toString().toLowerCase();
		}

	}

	private static class CodeTypeAdapter extends XmlAdapter<String, CodeType> {

		@Override
		public CodeType unmarshal(String v) throws Exception {
			return CodeType.valueOf(v.toUpperCase());
		}

		@Override
		public String marshal(CodeType v) throws Exception {
			return v.toString().toLowerCase();
		}

	}

}
