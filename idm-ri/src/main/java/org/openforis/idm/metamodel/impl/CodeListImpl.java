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
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.metamodel.CodeListLabel;
import org.openforis.idm.metamodel.CodeListLevel;
import org.openforis.idm.metamodel.CodingScheme;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.ModelVersion;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "sinceAttribute", "deprecatedAttribute", "name", "labels", "descriptions", "codingSchemes", "hierarchy", "items" })
public class CodeListImpl implements CodeList {

	@XmlAttribute(name = "since")
	String sinceAttribute;
	@XmlTransient
	private ModelVersion since;

	@XmlAttribute(name = "deprecated")
	String deprecatedAttribute;
	@XmlTransient
	private ModelVersion deprecated;

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
	public ModelVersion getSince() {
		return this.since;
	}

	@Override
	public void setSince(ModelVersion since) {
		this.since = since;
	}

	@Override
	public ModelVersion getDeprecated() {
		return this.deprecated;
	}

	@Override
	public void setDeprecated(ModelVersion deprecated) {
		this.deprecated = deprecated;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<CodeListLabel> getLabels() {
		return this.labels;
	}

	@Override
	public void setLabels(List<CodeListLabel> labels) {
		this.labels = labels;
	}

	@Override
	public List<LanguageSpecificText> getDescriptions() {
		return this.descriptions;
	}

	@Override
	public void setDescriptions(List<LanguageSpecificText> descriptions) {
		this.descriptions = descriptions;
	}

	@Override
	public List<CodeListLevel> getHierarchy() {
		return this.hierarchy;
	}

	@Override
	public void setHierarchy(List<CodeListLevel> hierarchy) {
		this.hierarchy = hierarchy;
	}

	@Override
	public List<CodingScheme> getCodingSchemes() {
		return this.codingSchemes;
	}

	@Override
	public void setCodingSchemes(List<CodingScheme> codingSchemes) {
		this.codingSchemes = codingSchemes;
	}

	@Override
	public List<CodeListItem> getItems() {
		return this.items;
	}

	@Override
	public void setItems(List<CodeListItem> items) {
		this.items = items;
	}

}
