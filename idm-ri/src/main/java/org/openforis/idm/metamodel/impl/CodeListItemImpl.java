/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.CodeDefinition;
import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.ModelVersion;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "sinceAttribute", "deprecatedAttribute", "qualifiable", "codeDefinitions", "labels", "descriptions", "children" })
public class CodeListItemImpl implements CodeListItem {

	@XmlAttribute(name = "since")
	String sinceAttribute;
	@XmlTransient
	private ModelVersion since;

	@XmlAttribute(name = "deprecated")
	String deprecatedAttribute;
	@XmlTransient
	private ModelVersion deprecated;

	@XmlElement(name = "label", type = LanguageSpecificTextImpl.class)
	private List<LanguageSpecificText> labels;

	@XmlElement(name = "description", type = LanguageSpecificTextImpl.class)
	private List<LanguageSpecificText> descriptions;

	@XmlElement(name = "item", type = CodeListItemImpl.class)
	private List<CodeListItem> children;

	@XmlAttribute(name = "qualifiable")
	private boolean qualifiable;

	@XmlElement(name = "code", type = CodeDefinitionImpl.class)
	private List<CodeDefinition> codeDefinitions;

	@Override
	public List<CodeListItem> getChildren() {
		return this.children;
	}

	@Override
	public void setChildren(List<CodeListItem> children) {
		this.children = children;
	}

	@Override
	public boolean isQualifiable() {
		return this.qualifiable;
	}

	@Override
	public void setQualifiable(boolean qualifiable) {
		this.qualifiable = qualifiable;
	}

	@Override
	public List<CodeDefinition> getCodes() {
		return this.codeDefinitions;
	}

	@Override
	public void setCodes(List<CodeDefinition> codeDefinitions) {
		this.codeDefinitions = codeDefinitions;
	}

	@Override
	public List<LanguageSpecificText> getLabels() {
		return this.labels;
	}

	@Override
	public void setLabels(List<LanguageSpecificText> labels) {
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

}
