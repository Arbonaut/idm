package org.openforis.idm.metamodel;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "sinceVersionName", "deprecatedVersionName", "labels", "descriptions", "codingScheme", "hierarchy", "items" })
public class CodeList extends Versionable {

	public enum CodeType {
		NUMERIC, ALPHANUMERIC
	}

	public enum CodeScope {
		SCHEME, LOCAL
	}

	@XmlTransient
	private Survey survey;

	@XmlAttribute(name = "name")
	private String name;

	@XmlElement(name = "label", type = CodeListLabel.class)
	private List<CodeListLabel> labels;

	@XmlElement(name = "description", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> descriptions;

//	@XmlElement(name = "codingScheme", type = CodingScheme.class)
//	private List<CodingScheme> codingSchemes;

	@XmlElement(name = "codingScheme", type = CodingScheme.class)
	private CodingScheme codingScheme;

	@XmlElement(name = "level", type = CodeListLevel.class)
	@XmlElementWrapper(name = "hierarchy")
	private List<CodeListLevel> hierarchy;

	@XmlElement(name = "item", type = CodeListItem.class)
	@XmlElementWrapper(name = "items")
	private List<CodeListItem> items;

	public String getName() {
		return this.name;
	}

	public List<CodeListLabel> getLabels() {
		return Collections.unmodifiableList(this.labels);
	}

	public List<LanguageSpecificText> getDescriptions() {
		return Collections.unmodifiableList(this.descriptions);
	}

	public List<CodeListLevel> getHierarchy() {
		return Collections.unmodifiableList(this.hierarchy);
	}

	public List<CodeListItem> getItems() {
		return Collections.unmodifiableList(this.items);
	}
	
	public CodeType getCodeType() {
		if ( codingScheme == null || codingScheme.getCodeType() == null ) {
			return CodeType.ALPHANUMERIC;
		} else {
			return codingScheme.getCodeType();
		}
	}

	public CodeScope getCodeScope() {
		if ( codingScheme == null || codingScheme.getCodeScope() == null ) {
			return CodeScope.LOCAL;
		} else {
			return codingScheme.getCodeScope();
		}
	}

	public boolean isAlphanumeric() {
		return getCodeType() == CodeType.ALPHANUMERIC;
	}

	public boolean isNumeric() {
		return getCodeType() == CodeType.NUMERIC;
	}
	
	public Survey getSurvey() {
		return survey;
	}

	protected void setSurvey(Survey survey) {
		this.survey = survey;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {  "codeScope", "codeType" })
	private static class CodingScheme {

		@XmlAttribute(name = "scope")
		@XmlJavaTypeAdapter(value = CodeScopeAdapter.class)
		private CodeScope codeScope;

		@XmlAttribute(name = "type")
		@XmlJavaTypeAdapter(value = CodeTypeAdapter.class)
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
	
	
}
