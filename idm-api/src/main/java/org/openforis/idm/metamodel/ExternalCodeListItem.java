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

	private Map<String, String> parentKeyByLevel;
	
	public ExternalCodeListItem(CodeList codeList, int id, Map<String, String> parentKeyByLevel) {
		super(codeList, id);
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
	
}
