/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "labels", "descriptions", "date" })
public class ModelVersion implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "name")
	private String name;

	@XmlElement(name = "label", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> labels;

	@XmlElement(name = "description", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> descriptions;

	@XmlElement(name = "date")
	private String date;

	public String getName() {
		return this.name;
	}

	public List<LanguageSpecificText> getLabels() {
		return Collections.unmodifiableList(this.labels);
	}

	public List<LanguageSpecificText> getDescriptions() {
		return Collections.unmodifiableList(this.descriptions);
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

}
