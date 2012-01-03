/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {  "name", "codeScope", "codeType", "isDefault", "sinceVersionName", "deprecatedVersionName", "labels", "descriptions" })
public class CodingScheme extends Versionable {

	public enum CodeType {
		NUMERIC, ALPHANUMERIC
	}

	public enum CodeScope {
		SCHEME, LOCAL
	}
	
	@XmlTransient
	private CodeList list;
	
	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "scope")
	@XmlJavaTypeAdapter(value = CodeScopeAdapter.class)
	private CodeScope codeScope;

	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(value = CodeTypeAdapter.class)
	private CodeType codeType;

	@XmlAttribute(name = "default")
	private Boolean isDefault;

	@XmlElement(name = "label", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> labels;

	@XmlElement(name = "description", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> descriptions;


	public CodeType getCodeType() {
		return this.codeType;
	}

	public CodeScope getCodeScope() {
		return this.codeScope;
	}

	public String getName() {
		return this.name;
	}

	public boolean isDefault() {
		return isDefault == null ? false : isDefault;
	}

	public List<LanguageSpecificText> getLabels() {
		return Collections.unmodifiableList(this.labels);
	}

	public List<LanguageSpecificText> getDescriptions() {
		return Collections.unmodifiableList(this.descriptions);
	}
	
	
	public CodeList getList() {
		return list;
	}
	
	protected void setList(CodeList list) {
		this.list = list;
	}
	
	@Override
	public Survey getSurvey() {
		return list == null ? null : list.getSurvey();
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
