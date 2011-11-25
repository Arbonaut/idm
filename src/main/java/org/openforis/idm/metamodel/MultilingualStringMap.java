package org.openforis.idm.metamodel;

import java.util.HashMap;
import java.util.Map;

/**
 * Language keys based on RFC 5646 http://tools.ietf.org/html/rfc5646
 * 
 * @author G. Miceli
 */
public class MultilingualStringMap {

	private Map<String,String> strings;
	
	public MultilingualStringMap() {
		strings = new HashMap<String, String>();
	}
	
	public String get(String language) {
		return strings.get(language);
	}

	public void put(String language, String string) {
		strings.put(language, string);
	}

	public void remove(String language) {
		strings.remove(language);
	}
	
	public void clear() {
		strings.clear();
	}
	
	public String[] getDefinedLanguages() {
		return strings.keySet().toArray(new String[0]);
	}
}
