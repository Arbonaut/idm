/**
 * 
 */
package org.openforis.idm.metamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author S. Ricci
 */
public class CodeListItem extends VersionableSurveyObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean qualifiable;
	private String code;
	private LanguageSpecificTextMap labels;
	private LanguageSpecificTextMap descriptions;
	private List<CodeListItem> childItems;
	private CodeList list;
	private CodeListItem parentItem;

	CodeListItem(CodeList codeList, int id) {
		super(codeList.getSurvey(), id);
		this.list = codeList;
	}

	public boolean hasChildItems() {
		return ! ( childItems == null || childItems.isEmpty() );
	}
	
	public int getDepth() {
		int depth = 1;
		CodeListItem parent = parentItem;
		while ( parent != null ) {
			parent = parent.getParentItem();
			depth++;
		}
		return depth;
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
		if ( this.labels == null ) {
			return Collections.emptyList();
		} else {
			return this.labels.values();
		}
	}
	
	public String getLabel(String language) {
		return labels == null ? null: labels.getText(language);
	}
	
	public void addLabel(LanguageSpecificText label) {
		if ( labels == null ) {
			labels = new LanguageSpecificTextMap();
		}
		labels.add(label);
	}

	public void setLabel(String language, String text) {
		if ( labels == null ) {
			labels = new LanguageSpecificTextMap();
		}
		labels.setText(language, text);
	}
	
	public void removeLabel(String language) {
		labels.remove(language);
	}

	public List<LanguageSpecificText> getDescriptions() {
		if ( descriptions == null ) {
			return Collections.emptyList();
		} else {
			return descriptions.values();
		}
	}

	public String getDescription(String language) {
		return descriptions == null ? null: descriptions.getText(language);
	}
	
	public void setDescription(String language, String text) {
		if ( descriptions == null ) {
			descriptions = new LanguageSpecificTextMap();
		}
		descriptions.setText(language, text);
	}
	
	public void addDescription(LanguageSpecificText description) {
		if ( descriptions == null ) {
			descriptions = new LanguageSpecificTextMap();
		}
		descriptions.add(description);
	}

	public void removeDescription(String language) {
		descriptions.remove(language);
	}

	public List<CodeListItem> getChildItems() {
		return CollectionUtil.unmodifiableList(childItems);
	}
	
	public CodeListItem getChildItem(String code) {
		if ( childItems != null && code != null) {
			for (CodeListItem item : childItems) {
				if ( code.equals(item.getCode()) ) {
					return item;
				}
			}
		}
		return null;
	}
	
	public CodeListItem findChildItem(String code) {
		if ( childItems != null && code != null ) {
			Pattern pattern = createMatchingPattern(code);
			for (CodeListItem item : childItems) {
				if ( item.matchCode(pattern) ) {
					return item;
				}
			}
		}
		return null;
	}
	
	protected Pattern createMatchingPattern(String code) {
		String adaptedCode = Pattern.quote(code);
		Pattern pattern = Pattern.compile("^" + adaptedCode + "$", Pattern.CASE_INSENSITIVE);
		return pattern;
	}
	
	public boolean matchCode(String code) {
		Pattern pattern = createMatchingPattern(code);
		return matchCode(pattern);
	}

	protected boolean matchCode(Pattern pattern) {
		String itemCode = getCode();
		Matcher matcher = pattern.matcher(itemCode);
		if(matcher.find()) {
			return true;
		} else {
			return false;
		}
	}

	public void addChildItem(CodeListItem item) {
		if ( childItems == null ) {
			childItems = new ArrayList<CodeListItem>();
		}
		// TODO check id is unique and don't exceed max
//		item.setId(nextItemId());
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

	public void moveChildItem(CodeListItem item, int indexTo) {
		CollectionUtil.moveItem(childItems, item, indexTo);
	}

	protected int calculateLastUsedItemId() {
		int result = 0;
		List<CodeListItem> items = getChildItems();
		for (CodeListItem item : items) {
			result = Math.max(result, item.getId());	
		}
		return result;
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
		result = prime * result + getId();
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
		if (getId() != other.getId())
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
