package org.openforis.idm.metamodel.xml.internal.unmarshal;

import org.openforis.idm.metamodel.xml.IdmlConstants;

/**
 * @author G. Miceli
 *
 * @param <T>
 */
public abstract class IdmlSerializer<T,P> extends AbstractXmlSerializer<T,P> {
	public IdmlSerializer(String tag) {
		super(IdmlConstants.IDML3_NAMESPACE_URI, tag);
	}
}
