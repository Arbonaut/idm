/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.CodeDefinition;
import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.metamodel.LanguageSpecificText;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "sinceAttribute", "deprecatedAttribute", "qualifiable", "codeDefinitions", "labels", "descriptions", "children" })
public class CodeListItemImpl extends AbstractVersionable implements CodeListItem {

	@XmlAttribute(name = "since")
	String sinceAttribute;

	@XmlAttribute(name = "deprecated")
	String deprecatedAttribute;

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
	public boolean isQualifiable() {
		return this.qualifiable;
	}

	@Override
	public List<CodeDefinition> getCodes() {
		return this.codeDefinitions;
	}

	@Override
	public List<LanguageSpecificText> getLabels() {
		return this.labels;
	}

	@Override
	public List<LanguageSpecificText> getDescriptions() {
		return this.descriptions;
	}

}
