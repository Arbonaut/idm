/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.CodingScheme;
import org.openforis.idm.metamodel.LanguageSpecificText;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "codeType", "codeScope", "name", "isDefault", "labels", "descriptions" })
public class CodingSchemeImpl implements CodingScheme {

	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(value = CodeTypeAdapter.class)
	private CodeType codeType;

	@XmlAttribute(name = "scope")
	@XmlJavaTypeAdapter(value = CodeScopeAdapter.class)
	private CodeScope codeScope;

	@XmlAttribute(name = "name")
	private String name;

	@XmlElement(name = "label", type = LanguageSpecificTextImpl.class)
	private List<LanguageSpecificText> labels;

	@XmlElement(name = "description", type = LanguageSpecificTextImpl.class)
	private List<LanguageSpecificText> descriptions;

	@XmlAttribute(name = "default")
	private boolean isDefault;

	@Override
	public CodeType getCodeType() {
		return this.codeType;
	}

	@Override
	public CodeScope getCodeScope() {
		return this.codeScope;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean isDefault() {
		return this.isDefault;
	}

	@Override
	public List<LanguageSpecificText> getLabels() {
		return this.labels;
	}

	@Override
	public List<LanguageSpecificText> getDescriptions() {
		return this.descriptions;
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
}
