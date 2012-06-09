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
	public T unmarshal(String v) {
		if ( v == null ) {
			return null;
		} else {
			return Enum.valueOf(enumType, v.toUpperCase());
		}
	}

	@Override
	public String marshal(T v) {
		if ( v==null ) {
			return null;
		} else {
			return v.toString().toLowerCase();
		}
	}

}
