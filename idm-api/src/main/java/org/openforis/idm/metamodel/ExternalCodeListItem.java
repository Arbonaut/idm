/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.List;
import java.util.Map;

import org.openforis.commons.collection.CollectionUtils;

/**
 * @author S. Ricci
 *
 */
public class ExternalCodeListItem extends CodeListItem {

	private static final long serialVersionUID = 1L;

	private Integer systemId;
	private int parentId;
	private Map<String, String> parentKeyByLevel;
	
	public ExternalCodeListItem(CodeList codeList, int itemId) {
		super(codeList, itemId);
	}
	
	public ExternalCodeListItem(CodeList codeList, int itemId, Map<String, String> parentKeyByLevel) {
		this(codeList, itemId);
		this.parentKeyByLevel = parentKeyByLevel;
	}
	
	@Override
	public CodeListItem getParentItem() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends CodeListItem> List<T> getChildItems() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public CodeListItem getChildItem(String code) {
		throw new UnsupportedOperationException();
	}
	
	public Map<String, String> getParentKeyByLevel() {
		return CollectionUtils.unmodifiableMap(parentKeyByLevel);
	}
	
	public int getLevel() {
		return getParentKeyByLevel().size() + 1;
	}

	public Integer getSystemId() {
		return systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
}
