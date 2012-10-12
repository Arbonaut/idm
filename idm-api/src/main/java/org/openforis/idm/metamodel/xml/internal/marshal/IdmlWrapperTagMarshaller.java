package org.openforis.idm.metamodel.xml.internal.marshal;

import java.io.IOException;

/**
 * 
 * @author G. Miceli
 *
 */
abstract class IdmlWrapperTagMarshaller<P>extends AbstractIdmlMarshaller<P,P> {

	IdmlWrapperTagMarshaller(String tag) {
		super(tag);
	}
	
	@Override
	protected void marshalInstances(P parentObject) throws IOException {
		marshal(parentObject);
	}
}
