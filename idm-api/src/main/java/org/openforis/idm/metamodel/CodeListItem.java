/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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
 * @author S. Ricci
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "qualifiable", "sinceVersionName", "deprecatedVersionName", "code", "labels", "descriptions", "childItems" })
public class CodeListItem extends Versionable implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "id")
	private int id;

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
	
	@XmlTransient
	private int nextChildId;
	
	public CodeListItem() {
		nextChildId = 1;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isQualifiable() {
		return qualifiable == null ? false : qualifiable;
	}
	
	public void setQualifiable(Boolean qualifiable) {
		this.qualifiable = qualifiable;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public void setLabel(String language, String text) {
		if ( labels == null ) {
			labels = new ArrayList<LanguageSpecificText>();
		}
		LanguageSpecificText.setText(labels, language, text);
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

	public List<CodeListItem> getChildItems() {
		return CollectionUtil.unmodifiableList(childItems);
	}

	public void addChildItem(CodeListItem item) {
		if ( childItems == null ) {
			childItems = new ArrayList<CodeListItem>();
		}
		if ( item.getId() == 0 ) {
			item.setId(nextChildId ++);
		} else {
			nextChildId = Math.max(nextChildId, item.getId() + 1);
		}
		childItems.add(item);
		item.setParentItem(this);
	}
	
	public void removeChildItem(int id) {
		if ( childItems != null ) {
			Iterator<CodeListItem> it = childItems.iterator();
			while ( it.hasNext() ) {
				CodeListItem item = it.next();
				if ( item.getId() == id ) {
					it.remove();
				}
			}
		}
	}
	
	public CodeListItem getParentItem() {
		return parentItem;
	}
	
	public void setParentItem(CodeListItem parentItem) {
		this.parentItem = parentItem;
	}
	
	public CodeList getCodeList() {
		return list;
	}

	public void setCodeList(CodeList list) {
		this.list = list;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((childItems == null) ? 0 : childItems.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((descriptions == null) ? 0 : descriptions.hashCode());
		result = prime * result + id;
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result + ((qualifiable == null) ? 0 : qualifiable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CodeListItem other = (CodeListItem) obj;
		if (childItems == null) {
			if (other.childItems != null)
				return false;
		} else if (!childItems.equals(other.childItems))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (descriptions == null) {
			if (other.descriptions != null)
				return false;
		} else if (!descriptions.equals(other.descriptions))
			return false;
		if (id != other.id)
			return false;
		if (labels == null) {
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
			return false;
		if (qualifiable == null) {
			if (other.qualifiable != null)
				return false;
		} else if (!qualifiable.equals(other.qualifiable))
			return false;
		return true;
	}

}
