/**
 * 
 */
package org.openforis.idm.metamodel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openforis.commons.collection.CollectionUtils;

/**
 * @author S. Ricci
 *
 */
public class ExternalCodeListItem extends CodeListItem {

	private static final long serialVersionUID = 1L;

	private Map<String, String> parentKeysByLevel;
	
	public ExternalCodeListItem(CodeList codeList, int id) {
		super(codeList, id);
	}
	
	public ExternalCodeListItem(CodeList codeList, int id, Map<String, String> parentKeysByLevel) {
		this(codeList, id);
		this.parentKeysByLevel = parentKeysByLevel;
	}
	
	@Override
	public CodeListItem getParentItem() {
		Survey survey = getSurvey();
		SurveyContext context = survey.getContext();
		ExternalCodeListProvider provider = context.getExternalCodeListProvider();
		ExternalCodeListItem parentItem = provider.getParentItem(this);
		return parentItem;
	}
	
	public Map<String, String> getParentKeysByLevel() {
		return CollectionUtils.unmodifiableMap(parentKeysByLevel);
	}

	public Collection<StringKeyValuePair> getParentKeys() {
		Set<StringKeyValuePair> result = new HashSet<StringKeyValuePair>();
		Set<Entry<String,String>> entrySet = parentKeysByLevel.entrySet();
		for (Entry<String, String> entry : entrySet) {
			StringKeyValuePair item = new StringKeyValuePair(entry);
			result.add(item);
		}
		return result;
	}
	
}
