package org.openforis.idm.metamodel.xml.internal.marshal;

import java.io.IOException;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.xml.IdmlConstants;

/**
 * 
 * @author G. Miceli
 *
 */
abstract class LanguageSpecificTextIM<P> extends AbstractIdmlMarshaller<LanguageSpecificText, P>{

	public LanguageSpecificTextIM(String tag) {
		super(tag);
	}

	@Override
	protected void attributes() throws IOException {
		LanguageSpecificText lst = getSourceObject();
		String lang = lst.getLanguage();
		if ( lang != null ) {
			attribute(IdmlConstants.XML_NAMESPACE_URI, lang);
		}
	}
	
	@Override
	protected void body() throws IOException {
		LanguageSpecificText lst = getSourceObject();
		String text = lst.getText();
		text(text);
	}
}
