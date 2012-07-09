package org.openforis.idm.metamodel;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.xml.internal.XmlParent;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "name", "lookupTable", "sinceVersionName", "deprecatedVersionName", "labels", "descriptions", "codingScheme", "hierarchy", "items" })
public class CodeList extends Versionable implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum CodeScope {
		SCHEME, LOCAL
	}

	@XmlTransient
	@XmlParent
	private Survey survey;

	@XmlAttribute(name = "id")
	private int id;

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "lookup")
	private String lookupTable;

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

	public int getId() {
		return id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getLookupTable() {
		return lookupTable;
	}

	public List<CodeListLabel> getLabels() {
		return CollectionUtil.unmodifiableList(this.labels);
	}

	public List<LanguageSpecificText> getDescriptions() {
		return CollectionUtil.unmodifiableList(this.descriptions);
	}

	public List<CodeListLevel> getHierarchy() {
		return CollectionUtil.unmodifiableList(this.hierarchy);
	}

	public List<CodeListItem> getItems() {
		return CollectionUtil.unmodifiableList(this.items);
	}
	
	public CodeScope getCodeScope() {
		if ( codingScheme == null || codingScheme.getCodeScope() == null ) {
			return CodeScope.LOCAL;
		} else {
			return codingScheme.getCodeScope();
		}
	}

	public Survey getSurvey() {
		return survey;
	}

	protected void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public boolean isQualifiable() {
		for (CodeListItem item : getItems()) {
			if ( item.isQualifiableRecursive() ) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codingScheme == null) ? 0 : codingScheme.hashCode());
		result = prime * result + ((descriptions == null) ? 0 : descriptions.hashCode());
		result = prime * result + ((hierarchy == null) ? 0 : hierarchy.hashCode());
		result = prime * result + id;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result + ((lookupTable == null) ? 0 : lookupTable.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CodeList other = (CodeList) obj;
		if (codingScheme == null) {
			if (other.codingScheme != null)
				return false;
		} else if (!codingScheme.equals(other.codingScheme))
			return false;
		if (descriptions == null) {
			if (other.descriptions != null)
				return false;
		} else if (!descriptions.equals(other.descriptions))
			return false;
		if (hierarchy == null) {
			if (other.hierarchy != null)
				return false;
		} else if (!hierarchy.equals(other.hierarchy))
			return false;
		if (id != other.id)
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (labels == null) {
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
			return false;
		if (lookupTable == null) {
			if (other.lookupTable != null)
				return false;
		} else if (!lookupTable.equals(other.lookupTable))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
