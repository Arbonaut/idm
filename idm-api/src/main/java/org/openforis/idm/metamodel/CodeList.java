package org.openforis.idm.metamodel;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.xml.XmlParent;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "sinceVersionName", "deprecatedVersionName", "labels", "descriptions", "codingScheme", "hierarchy", "items" })
public class CodeList extends Versionable implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum CodeType {
		NUMERIC, ALPHANUMERIC
	}

	public enum CodeScope {
		SCHEME, LOCAL
	}

	@XmlTransient
	@XmlParent
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
		if(this.labels != null) {
			return Collections.unmodifiableList(this.labels);
		} else {
			return null;
		}
	}

	public List<LanguageSpecificText> getDescriptions() {
		if(this.descriptions != null) {
			return Collections.unmodifiableList(this.descriptions);
		} else {
			return null;
		}
	}

	public List<CodeListLevel> getHierarchy() {
		if(this.hierarchy != null) {
			return Collections.unmodifiableList(this.hierarchy);
		} else {
			return null;
		}
	}

	public List<CodeListItem> getItems() {
		if(this.items != null) {
			return Collections.unmodifiableList(this.items);
		} else {
			return null;
		}
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
	
	
}
