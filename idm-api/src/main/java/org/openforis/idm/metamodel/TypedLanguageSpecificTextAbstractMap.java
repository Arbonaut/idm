package org.openforis.idm.metamodel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 
 * @author S. Ricci
 *
 * @param <L>
 * @param <T>
 * @param <T>
 */
public abstract class TypedLanguageSpecificTextAbstractMap<L extends TypedLanguageSpecificText<T>, T extends Enum<T>> {
	
	private final Class<L> itemClass;
	private final Class<T> typeClass;
	
	private LinkedHashMap<MapKey<T>, L> map;

	@SuppressWarnings("unchecked")
	TypedLanguageSpecificTextAbstractMap() {
		ParameterizedType parameterizedType = (ParameterizedType)getClass().getGenericSuperclass();
		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		itemClass = (Class<L>) actualTypeArguments[0];
		typeClass = (Class<T>) actualTypeArguments[1];
		map = new LinkedHashMap<MapKey<T>, L>();
	}
	
	public L get(T type, String language) {
		MapKey<T> key = new MapKey<T>(type, language);
		return map.get(key);
	}
	
	public List<L> values() {
		Collection<L> result = map.values();
		return new ArrayList<L>(result);
	}
	
	public String getText(T type, String language) {
		L languageSpecificText = get(type, language);
		return languageSpecificText != null ? languageSpecificText.getText(): null;
	}
	
	public void setText(T type, String language, String text) {
		L languageSpecificText = get(type, language);
		if ( languageSpecificText == null ) {
			languageSpecificText = createLanguageSpecificTextInstance(type, language, text);
			add(languageSpecificText);
		} else {
			languageSpecificText.setText(text);
		}
	}

	protected L createLanguageSpecificTextInstance(T type, String language, String text) {
		try {
			L instance = itemClass.getConstructor(typeClass, String.class, String.class).newInstance(type, language, text);
			return instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void add(L languageSpecificText) {
		T type = languageSpecificText.getType();
		MapKey<T> key = new MapKey<T>(type, languageSpecificText.getLanguage());
		map.put(key, languageSpecificText);
	}
	
	public void remove(T type, String language) {
		MapKey<T> key = new MapKey<T>(type, language);
		map.remove(key);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypedLanguageSpecificTextAbstractMap<?, ?> other = (TypedLanguageSpecificTextAbstractMap<?, ?>) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		return true;
	}

	static class MapKey<T> {
		private String language;
		private T type;
		
		MapKey(T type, String language) {
			super();
			this.type = type;
			this.language = language == null ? "": language;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((language == null) ? 0 : language.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MapKey<?> other = (MapKey<?>) obj;
			if (language == null) {
				if (other.language != null)
					return false;
			} else if (!language.equals(other.language))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}
	}

}
