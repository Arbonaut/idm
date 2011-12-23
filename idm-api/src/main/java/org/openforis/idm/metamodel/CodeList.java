package org.openforis.idm.metamodel;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "since", "deprecated", "labels", "descriptions", "codingSchemes", "hierarchy", "items" })
public class CodeList extends VersionableModelDefinition {

	@XmlAttribute(name = "name")
	private String name;

	@XmlElement(name = "label", type = CodeListLabel.class)
	private List<CodeListLabel> labels;

	@XmlElement(name = "description", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> descriptions;

	@XmlElement(name = "codingScheme", type = CodingScheme.class)
	private List<CodingScheme> codingSchemes;

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

	public List<CodingScheme> getCodingSchemes() {
		return Collections.unmodifiableList(this.codingSchemes);
	}

	public List<CodeListLevel> getHierarchy() {
		return Collections.unmodifiableList(this.hierarchy);
	}

	public List<CodeListItem> getItems() {
		return Collections.unmodifiableList(this.items);
	}
	
	public CodingScheme getCodingScheme(String name) {
		for (CodingScheme codingScheme : codingSchemes) {
			if (codingScheme.getName().equals(name)) {
				return codingScheme;
			}
		}
		return null;
	}
}
