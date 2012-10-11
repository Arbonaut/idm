package org.openforis.idm.metamodel.xml.internal.unmarshal;

import java.io.IOException;

/**
 * 
 * @author G. Miceli
 *
 */
abstract class TextXS<P> extends IdmlSerializer<String, P>{

	public TextXS(String tag) {
		super(tag);
	}

	@Override
	protected void body() throws IOException {
		String text = getSourceObject();
		text(text);
	}
}
