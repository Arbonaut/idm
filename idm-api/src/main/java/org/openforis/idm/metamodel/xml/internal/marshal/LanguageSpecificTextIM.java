package org.openforis.idm.metamodel.xml.internal.marshal;

import java.io.IOException;

import org.openforis.idm.metamodel.LanguageSpecificText;
import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

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
	protected void attributes(LanguageSpecificText txt) throws IOException {
		String lang = txt.getLanguage();
		if ( lang != null ) {
			attribute(XML_NAMESPACE_URI, XML_LANG_ATTRIBUTE, lang);
		}
	}
	
	@Override
	protected void body(LanguageSpecificText txt) throws IOException {
		text(txt.getText());
	}
}
