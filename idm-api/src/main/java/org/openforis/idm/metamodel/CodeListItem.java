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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "qualifiable", "sinceVersionName", "deprecatedVersionName", "code", "labels", "descriptions", "childItems" })
public class CodeListItem extends Versionable {

	@XmlAttribute(name = "qualifiable")
	private Boolean qualifiable;

	@XmlElement(name = "code")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	private String code;

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
		return qualifiable == null ? false : qualifiable;
	}

	public String getCode() {
		return code;
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
	
	protected void setParentItem(CodeListItem parent) {
		this.parentItem = parent;
		this.list = parent.list;
	}

	protected void setList(CodeList parent) {
		this.list = parent;
	}

	@Override
	public Survey getSurvey() {
		return list == null ? null : list.getSurvey();
	}
}
