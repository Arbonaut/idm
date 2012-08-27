package org.openforis.idm.metamodel.xml.internal;
import org.simpleframework.xml.convert.Converter;
//import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author K. Waga
 */

public class InvertBooleanAdapter implements Converter<Boolean> {

	@Override
	public Boolean read(InputNode in) throws Exception {
		return in == null ? null : !Boolean.getBoolean(in.getValue().toString());
	}

	@Override
	public void write(OutputNode out, Boolean v) throws Exception {
		out.setValue(v?"false":"true");
	}
}
/*public class InvertBooleanAdapter implements Transform<Boolean> {

	@Override
	public Boolean read(String v) throws Exception {
		return v == null ? null : (v.equals("true")?false:true);
	}

	@Override
	public String write(Boolean v) throws Exception {
		return v == null ? null : (v?"false":"true");
	}
}*/

/*public class InvertBooleanAdapter extends XmlAdapter<Boolean, Boolean> {

	@Override
	public Boolean unmarshal(Boolean v) throws Exception {
		return v == null ? null : !v;
	}

	@Override
	public Boolean marshal(Boolean v) throws Exception {
		return v == null ? null : !v;
	}
}*/
