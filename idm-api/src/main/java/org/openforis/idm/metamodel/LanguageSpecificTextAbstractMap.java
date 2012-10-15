/**
 * 
 */
package org.openforis.idm.metamodel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.openforis.idm.util.CollectionUtil;

/**
 * @author S. Ricci
 *
 */
public abstract class LanguageSpecificTextAbstractMap<T extends LanguageSpecificText> {
	
	private static final String VOID_LANGUAGE_KEY = "";

	private final Class<T> genericType;
	
	private LinkedHashMap<String, T> map;

	@SuppressWarnings("unchecked")
	LanguageSpecificTextAbstractMap() {
		ParameterizedType parameterizedType = (ParameterizedType)getClass().getGenericSuperclass();
		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		genericType = (Class<T>) actualTypeArguments[0];
		map = new LinkedHashMap<String, T>();
	}
	
	public T get(String language) {
		String key = getMapKey(language);
		return map.get(key);
	}

	protected String getMapKey(String language) {
		String key = language == null ? VOID_LANGUAGE_KEY: language;
		return key;
	}
	
	public List<T> values() {
		Collection<T> result = map.values();
		return CollectionUtil.unmodifiableList(new ArrayList<T>(result));
	}
	
	public String getText(String language) {
		T languageSpecificText = get(language);
		return languageSpecificText != null ? languageSpecificText.getText(): null;
	}
	
	public void setText(String language, String text) {
		String key = getMapKey(language);
		T languageSpecificText = map.get(key);
		if ( languageSpecificText == null ) {
			languageSpecificText = createLanguageSpecificTextInstance(language, text);
			map.put(key, languageSpecificText);
		} else {
			languageSpecificText.setText(text);
		}
	}

	public void add(T languageSpecificText) {
		String key = getMapKey(languageSpecificText.getLanguage());
		map.put(key, languageSpecificText);
	}
	
	public void remove(String language) {
		String key = getMapKey(language);
		map.remove(key);
	}

	protected T createLanguageSpecificTextInstance(String language, String text) {
		try {
			T instance = genericType.getConstructor(String.class, String.class).newInstance(language, text);
			return instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}