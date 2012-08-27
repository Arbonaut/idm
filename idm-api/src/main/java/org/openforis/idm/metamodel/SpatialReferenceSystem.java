/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;*/

import org.openforis.idm.util.CollectionUtil;


/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "labels", "descriptions",  "wellKnownText" })
@XmlRootElement(name = "spatialReferenceSystem")
public class SpatialReferenceSystem implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "srid")
	private String id;

	@XmlElement(name = "label", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> labels;

	@XmlElement(name = "description", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> descriptions;

	@XmlElement(name = "wkt")
	private String wellKnownText;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	public void addLabel(LanguageSpecificText label) {
		if ( labels == null ) {
			labels = new ArrayList<LanguageSpecificText>();
		}
		labels.add(label);
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
		return CollectionUtil.unmodifiableList(descriptions);
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
	
	public void addDescription(LanguageSpecificText description) {
		if ( descriptions == null ) {
			descriptions = new ArrayList<LanguageSpecificText>();
		}
		descriptions.add(description);
	}

	public void removeDescription(String language) {
		LanguageSpecificText.remove(descriptions, language);
	}

	public String getWellKnownText() {
		return this.wellKnownText;
	}
	
	public void setWellKnownText(String wellKnownText) {
		this.wellKnownText = wellKnownText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descriptions == null) ? 0 : descriptions.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result + ((wellKnownText == null) ? 0 : wellKnownText.hashCode());
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
		SpatialReferenceSystem other = (SpatialReferenceSystem) obj;
		if (descriptions == null) {
			if (other.descriptions != null)
				return false;
		} else if (!descriptions.equals(other.descriptions))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (labels == null) {
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
			return false;
		if (wellKnownText == null) {
			if (other.wellKnownText != null)
				return false;
		} else if (!wellKnownText.equals(other.wellKnownText))
			return false;
		return true;
	}
	
}
