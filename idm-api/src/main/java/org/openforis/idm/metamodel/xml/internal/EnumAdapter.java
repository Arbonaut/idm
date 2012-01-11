package org.openforis.idm.metamodel.xml.internal;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class EnumAdapter<T extends Enum<T>> extends XmlAdapter<String, T> {

	private Class<T> enumType;

	public EnumAdapter(Class<T> enumType) {
		this.enumType = enumType;
	}
	
	@Override
	public T unmarshal(String v) throws Exception {
		T value = Enum.valueOf(enumType, v.toUpperCase());
		return v==null ? null : value;
	}

	@Override
	public String marshal(T v) throws Exception {
		return v==null ? null : v.toString().toLowerCase();
	}

}
