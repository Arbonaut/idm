/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "name", "labels", "descriptions", "date" })
public class ModelVersion implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "id")
	private int id;

	@XmlAttribute(name = "name")
	private String name;

	@XmlElement(name = "label", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> labels;

	@XmlElement(name = "description", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> descriptions;

	@XmlElement(name = "date")
	private String date;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<LanguageSpecificText> getLabels() {
		return CollectionUtil.unmodifiableList(labels);
	}

	public String getLabel(String language) {
		if (labels != null ) {
			return LanguageSpecificText.getText(labels, language);
		} else {
			return null;
		}
	}
	
	public void setLabel(String language, String description) {
		if ( labels == null ) {
			labels = new ArrayList<LanguageSpecificText>();
		}
		LanguageSpecificText.setText(labels, language, description);
	}
	
	public void removeLabel(String language) {
		LanguageSpecificText.remove(labels, language);
	}

	public List<LanguageSpecificText> getDescriptions() {
		return Collections.unmodifiableList(descriptions);
	}

	public String getDescription(String language) {
		if (descriptions != null ) {
			return LanguageSpecificText.getText(descriptions, language);
		} else {
			return null;
		}
	}
	
	public void setDescription(String language, String description) {
		if ( descriptions == null ) {
			descriptions = new ArrayList<LanguageSpecificText>();
		}
		LanguageSpecificText.setText(descriptions, language, description);
	}
	
	public void removeDescription(String language) {
		LanguageSpecificText.remove(descriptions, language);
	}


	public String getDate() {
		return date;
	}

	public boolean isApplicable(Versionable versionable) {
		ModelVersion since = versionable.getSinceVersion();
		ModelVersion deprecated = versionable.getDeprecatedVersion();
		if (since == null && deprecated == null) {
			return true;
		} else {
			int sinceResult = 1;
			int deprecatedResult = -1;
			if (since != null) {
				sinceResult = date.compareTo(since.getDate());
			}
			if (deprecated != null) {
				deprecatedResult = date.compareTo(deprecated.getDate());
			}
			return sinceResult >= 0 && deprecatedResult < 0;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{name: ");
		sb.append(name);
		sb.append(", date: ");
		sb.append(date);
		sb.append("}");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((descriptions == null) ? 0 : descriptions.hashCode());
		result = prime * result + id;
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelVersion other = (ModelVersion) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (descriptions == null) {
			if (other.descriptions != null)
				return false;
		} else if (!descriptions.equals(other.descriptions))
			return false;
		if (id != other.id)
			return false;
		if (labels == null) {
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
