/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.xml.internal.XmlInherited;
import org.openforis.idm.metamodel.xml.internal.XmlParent;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "qualifiable", "sinceVersionName", "deprecatedVersionName", "code", "labels", "descriptions", "childItems" })
public class CodeListItem extends Versionable implements Serializable {

	private static final long serialVersionUID = 1L;

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
	@XmlInherited("list")
	@XmlParent
	private CodeList list;

	@XmlTransient
	@XmlParent
	private CodeListItem parentItem;
	
	public boolean isQualifiable() {
		return qualifiable == null ? false : qualifiable;
	}

	public String getCode() {
		return code;
	}

	public List<LanguageSpecificText> getLabels() {
		return CollectionUtil.unmodifiableList(labels);
	}

	public List<LanguageSpecificText> getDescriptions() {
		return CollectionUtil.unmodifiableList(descriptions);
	}

	public List<CodeListItem> getChildItems() {
		return CollectionUtil.unmodifiableList(childItems);
	}

	public CodeListItem getParentItem() {
		return parentItem;
	}
	
	public CodeList getCodeList() {
		return list;
	}

	@Override
	public Survey getSurvey() {
		return list == null ? null : list.getSurvey();
	}

	boolean isQualifiableRecursive() {
		if ( isQualifiable() ) {
			return true;
		}
		for (CodeListItem child : getChildItems()) {
			if ( child.isQualifiableRecursive() ) {
				return true;
			}
		}
		return false;
	}
}
