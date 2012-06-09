package org.openforis.idm.metamodel.xml.internal;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class InvertBooleanAdapter extends XmlAdapter<Boolean, Boolean> {

	@Override
	public Boolean unmarshal(Boolean v) throws Exception {
		return v == null ? null : !v;
	}

	@Override
	public Boolean marshal(Boolean v) throws Exception {
		return v == null ? null : !v;
	}
}
