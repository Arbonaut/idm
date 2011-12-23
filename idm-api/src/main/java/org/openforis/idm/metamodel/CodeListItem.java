/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "qualifiable", "since", "deprecated", "codes", "labels", "descriptions", "childItems" })
public class CodeListItem extends VersionableModelDefinition {

	@XmlAttribute(name = "qualifiable")
	private boolean qualifiable;

	@XmlElement(name = "code", type = CodeDefinition.class)
	private List<CodeDefinition> codes;

	@XmlElement(name = "label", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> labels;

	@XmlElement(name = "description", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> descriptions;

	@XmlElement(name = "item", type = CodeListItem.class)
	private List<CodeListItem> childItems;

	@XmlTransient
	private CodeList list;

	@XmlTransient
	private CodeListItem parentItem;

	public boolean isQualifiable() {
		return this.qualifiable;
	}

	public List<CodeDefinition> getCodes() {
		return Collections.unmodifiableList(this.codes);
	}

	public List<LanguageSpecificText> getLabels() {
		return Collections.unmodifiableList(this.labels);
	}

	public List<LanguageSpecificText> getDescriptions() {
		return Collections.unmodifiableList(this.descriptions);
	}

	public List<CodeListItem> getChildItems() {
		return Collections.unmodifiableList(this.childItems);
	}

	public CodeListItem getParentItem() {
		return parentItem;
	}
	
	public CodeList getCodeList() {
		return list;
	}
	
	@Override
	protected void beforeUnmarshal(Object parent) {
		super.beforeUnmarshal(parent);
		if ( parent instanceof CodeDefinition ) {
			this.parentItem = (CodeListItem) parent;
			this.list = ((CodeListItem) parent).list;
		} else if ( parent instanceof CodeList ) {
			this.list = (CodeList) parent;
		}
	}
}
