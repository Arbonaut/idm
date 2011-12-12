/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.metamodel.CodeListLabel;
import org.openforis.idm.metamodel.CodeListLevel;
import org.openforis.idm.metamodel.CodingScheme;
import org.openforis.idm.metamodel.LanguageSpecificText;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "sinceAttribute", "deprecatedAttribute", "name", "labels", "descriptions", "codingSchemes", "hierarchy", "items" })
public class CodeListImpl extends AbstractVersionable implements CodeList {

	@XmlAttribute(name = "since")
	String sinceAttribute;
	
	@XmlAttribute(name = "deprecated")
	String deprecatedAttribute;
	
	@XmlAttribute(name = "name")
	private String name;

	@XmlElement(name = "label", type = CodeListLabelImpl.class)
	private List<CodeListLabel> labels;

	@XmlElement(name = "description", type = LanguageSpecificTextImpl.class)
	private List<LanguageSpecificText> descriptions;

	@XmlElement(name = "level", type = CodeListLevelImpl.class)
	@XmlElementWrapper(name = "hierarchy")
	private List<CodeListLevel> hierarchy;

	@XmlElement(name = "codingScheme", type = CodingSchemeImpl.class)
	private List<CodingScheme> codingSchemes;

	@XmlElement(name = "item", type = CodeListItemImpl.class)
	@XmlElementWrapper(name = "items")
	private List<CodeListItem> items;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public List<CodeListLabel> getLabels() {
		return this.labels;
	}

	@Override
	public List<LanguageSpecificText> getDescriptions() {
		return this.descriptions;
	}

	@Override
	public List<CodeListLevel> getHierarchy() {
		return this.hierarchy;
	}

	@Override
	public List<CodingScheme> getCodingSchemes() {
		return this.codingSchemes;
	}

	@Override
	public List<CodeListItem> getItems() {
		return this.items;
	}

}
