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
	protected void body() throws IOException {
		String text = getSourceObject();
		text(text);
	}
}
