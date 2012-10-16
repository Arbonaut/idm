package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;

import org.openforis.idm.metamodel.LanguageSpecificText;

/**
 * 
 * @author G. Miceli
 *
 */
abstract class LanguageSpecificTextXS<P> extends XmlSerializerSupport<LanguageSpecificText, P>{

	public LanguageSpecificTextXS(String tag) {
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
