package org.openforis.idm.metamodel.xml.internal.marshal;

import java.io.IOException;

/**
 * 
 * @author G. Miceli
 *
 */
abstract class TextIM<P> extends AbstractIdmlMarshaller<String, P>{

	public TextIM(String tag) {
		super(tag);
	}

	@Override
	protected void body(String text) throws IOException {
		text(text);
	}
}
