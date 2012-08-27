package org.openforis.idm.metamodel.xml.internal;

//import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;
/**
 * @author G. Miceli
 * @author M. Togna
 * @author K. Waga
 */
public class EnumAdapter<T extends Enum<T>> implements Converter<T> {

	private Class<T> enumType;

	public EnumAdapter(Class<T> enumType) {
		this.enumType = enumType;
	}

	@Override
	public T read(InputNode v) throws Exception {
		if ( v == null ) {
			return null;
		} else {
			return Enum.valueOf(enumType, v.getValue().toUpperCase());
		}
	}

	@Override
	public void write(OutputNode out, T v) throws Exception {
		if ( v==null ) {
			out.setValue(null);
		} else {
			out.setValue(v.toString());
		}
	}

}
/*public class EnumAdapter<T extends Enum<T>> implements Transform<T> {

	private Class<T> enumType;

	public EnumAdapter(Class<T> enumType) {
		this.enumType = enumType;
	}

	@Override
	public T read(String v) throws Exception {
		if ( v == null ) {
			return null;
		} else {
			return Enum.valueOf(enumType, v.toUpperCase());
		}
	}

	@Override
	public String write(T v) throws Exception {
		if ( v==null ) {
			return null;
		} else {
			return v.toString().toLowerCase();
		}
	}

}*/
/*public class EnumAdapter<T extends Enum<T>> extends XmlAdapter<String, T> {

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

}*/
