package org.openforis.idm.metamodel;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

/**
 * 
 * @author S. Ricci
 *
 */
public class StringKeyValuePair extends SimpleEntry<String, Object> {

	private static final long serialVersionUID = 1L;
	
	public StringKeyValuePair(Entry<? extends String, ? extends Object> entry) {
		super(entry);
	}
	
	public StringKeyValuePair(String key, Object value) {
		super(key, value);
	}

}