package org.openforis.idm.metamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.xml.internal.XmlParent;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "name", "lookupTable", "sinceVersionName", "deprecatedVersionName", "labels", "descriptions", "codingScheme", "hierarchy", "items" })
public class CodeList extends Versionable implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum CodeScope {
		SCHEME, LOCAL
	}

	@XmlTransient
	@XmlParent
	private Survey survey;

	@XmlAttribute(name = "id")
	private int id;

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "lookup")
	private String lookupTable;

	@XmlElement(name = "label", type = CodeListLabel.class)
	private List<CodeListLabel> labels;

	@XmlElement(name = "description", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> descriptions;

//	@XmlElement(name = "codingScheme", type = CodingScheme.class)
//	private List<CodingScheme> codingSchemes;

	@XmlElement(name = "codingScheme", type = CodingScheme.class)
	private CodingScheme codingScheme;

	@XmlElement(name = "level", type = CodeListLevel.class)
	@XmlElementWrapper(name = "hierarchy")
	private List<CodeListLevel> hierarchy;

	@XmlElement(name = "item", type = CodeListItem.class)
	@XmlElementWrapper(name = "items")
	private List<CodeListItem> items;

	@XmlTransient
	private int lastItemId;
	
	@XmlTransient
	private int lastLevelId;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLookupTable() {
		return lookupTable;
	}
	
	public void setLookupTable(String lookupTable) {
		this.lookupTable = lookupTable;
	}

	public List<CodeListLabel> getLabels() {
		return CollectionUtil.unmodifiableList(this.labels);
	}
	
	public String getLabel(CodeListLabel.Type type, String language) {
		CodeListLabel label = getCodeListLabel(type, language);
		if ( label != null ) {
			return label.getText();
		} else {
			return null;
		}
	}
	
	protected CodeListLabel getCodeListLabel(CodeListLabel.Type type, String language) {
		if (labels != null ) {
			for (CodeListLabel label : labels) {
				String labelLang = label.getLanguage();
				if ( label.getType()== type && ( language == null && labelLang == null ||
						language != null && language.equals(labelLang) ) ) {
					return label;
				}
			}
		}
		return null;
	}
	
	public void setLabel(CodeListLabel.Type type, String language, String text) {
		if ( labels == null ) {
			labels = new ArrayList<CodeListLabel>();
		}
		CodeListLabel oldLabel = getCodeListLabel(type, language);
		if ( oldLabel == null ) {
			CodeListLabel newLabel = new CodeListLabel(type, language, text);
			addLabel(newLabel);
		} else {
			oldLabel.setText(text);
		}
	}

	public void addLabel(CodeListLabel label) {
		if ( labels == null ) {
			labels = new ArrayList<CodeListLabel>();
		}
		labels.add(label);
	}

	public void removeLabel(CodeListLabel.Type type, String language) {
		if (labels != null ) {
			Iterator<CodeListLabel> it = labels.iterator();
			while ( it.hasNext() ) {
				CodeListLabel label = it.next();
				if ( label.getType()== type && label.getLanguage().equals(language)) {
					it.remove();
					return;
				}
			}
		}
	}

	public List<LanguageSpecificText> getDescriptions() {
		return CollectionUtil.unmodifiableList(this.descriptions);
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

	public List<CodeListLevel> getHierarchy() {
		return CollectionUtil.unmodifiableList(this.hierarchy);
	}

	public void addLevel(CodeListLevel level) {
		if ( this.hierarchy == null ) {
			this.hierarchy = new ArrayList<CodeListLevel>();
		}
		level.setId(nextLevelId());
		this.hierarchy.add(level);
	}
	
	public void removeLevel(int id) {
		int index = getLevelIndex(id);
		if ( index >= 0 ) {
			hierarchy.remove(index);
			if ( index > 0 ) {
				removeItemsInLevel(index);
			}
		}
	}

	private int getLevelIndex(int id) {
		for (int i = 0; i < hierarchy.size(); i++) {
			CodeListLevel level = hierarchy.get(i);
			if ( level.getId() == id ) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean hasItemsInLevel(int levelIndex) {
		List<CodeListItem> itemsInLevel = getItemsInLevel(levelIndex);
		return ! itemsInLevel.isEmpty();
	}
	
	protected List<CodeListItem> getItemsInLevel(int levelIndex) {
		List<CodeListItem> itemsInLevel = getItems();
		for (int depth = 0; ! itemsInLevel.isEmpty() && depth < levelIndex; depth ++ ) {
			List<CodeListItem> itemsInNextLevel = new ArrayList<CodeListItem>();
			for (CodeListItem item : itemsInLevel) {
				List<CodeListItem> childItems = item.getChildItems();
				if ( ! childItems.isEmpty() ) {
					itemsInNextLevel.addAll(childItems);
				}
			}
			itemsInLevel = itemsInNextLevel;
		}
		return itemsInLevel;
	}
	
	protected void removeItemsInLevel(int levelIndex) {
		List<CodeListItem> itemsInLevel = getItemsInLevel(levelIndex);
		for (CodeListItem item : itemsInLevel) {
			CodeListItem parent = item.getParentItem();
			int itemId = item.getId();
			if ( parent != null ) {
				parent.removeChildItem(itemId);
			} else {
				this.removeItem(itemId);
			}
		}
	}

	protected int nextLevelId() {
		if ( lastLevelId == 0 ) {
			lastLevelId = calculateLastUsedLevelId();
		}
		return lastLevelId++;
	}

	protected int calculateLastUsedLevelId() {
		int result = 0;
		List<CodeListLevel> levels = getHierarchy();
		for (CodeListLevel level : levels) {
			result = Math.max(result, level.getId());
		}
		return result;
	}
	
	public List<CodeListItem> getItems() {
		return CollectionUtil.unmodifiableList(this.items);
	}
	
	public CodeListItem getItem(String code) {
		if ( items != null ) {
			for (CodeListItem item : items) {
				if ( item.getCode().equals(code) ) {
					return item;
				}
			}
		}
		return null;
	}

	public void addItem(CodeListItem item) {
		if ( items == null ) {
			items = new ArrayList<CodeListItem>();
		}
		item.setId(nextItemId());
		items.add(item);
	}
	
	public void removeItem(int id) {
		if ( items != null ) {
			Iterator<CodeListItem> it = items.iterator();
			while ( it.hasNext() ) {
				CodeListItem item = it.next();
				if ( item.getId() == id ) {
					it.remove();
				}
			}
		}
	}
	
	protected int nextItemId() {
		if ( lastItemId == 0 ) {
			lastItemId = calculateLastUsedItemId();
		}
		return lastItemId++;
	}

	protected int calculateLastUsedItemId() {
		int result = 0;
		List<CodeListItem> items = getItems();
		for (CodeListItem item : items) {
			result = Math.max(result, item.getId());	
		}
		return result;
	}
	
	public CodeScope getCodeScope() {
		if ( codingScheme == null || codingScheme.getCodeScope() == null ) {
			return CodeScope.LOCAL;
		} else {
			return codingScheme.getCodeScope();
		}
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public boolean isQualifiable() {
		for (CodeListItem item : getItems()) {
			if ( item.isQualifiableRecursive() ) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codingScheme == null) ? 0 : codingScheme.hashCode());
		result = prime * result + ((descriptions == null) ? 0 : descriptions.hashCode());
		result = prime * result + ((hierarchy == null) ? 0 : hierarchy.hashCode());
		result = prime * result + id;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result + ((lookupTable == null) ? 0 : lookupTable.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		CodeList other = (CodeList) obj;
		if (codingScheme == null) {
			if (other.codingScheme != null)
				return false;
		} else if (!codingScheme.equals(other.codingScheme))
			return false;
		if (descriptions == null) {
			if (other.descriptions != null)
				return false;
		} else if (!descriptions.equals(other.descriptions))
			return false;
		if (hierarchy == null) {
			if (other.hierarchy != null)
				return false;
		} else if (!hierarchy.equals(other.hierarchy))
			return false;
		if (id != other.id)
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (labels == null) {
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
			return false;
		if (lookupTable == null) {
			if (other.lookupTable != null)
				return false;
		} else if (!lookupTable.equals(other.lookupTable))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
