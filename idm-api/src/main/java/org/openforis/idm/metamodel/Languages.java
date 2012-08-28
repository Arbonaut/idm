package org.openforis.idm.metamodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.model.TaxonAttribute;

/**
 * 
 * @author S. Ricci
 *
 */
public class Languages {

	public static List<String> LANGUAGE_CODES;

	static {
		initLanguageCodesList();
	}
	
	private static void initLanguageCodesList() {
		List<String> temp = new ArrayList<String>();
		InputStream is = null;
		BufferedReader br = null;
		try {
			String fileName = "lang_codes_iso_639.txt";
			is = TaxonAttribute.class.getResourceAsStream(fileName);
			br = new BufferedReader(new InputStreamReader(is));
			String langCode;
			while ((langCode = br.readLine()) != null) {
				temp.add(langCode);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if ( is != null ) {
				try {
					is.close();
				} catch (IOException e) {}
			}
			if ( br != null ) {
				try {
					br.close();
				} catch (IOException e) {}
			}
		}
		LANGUAGE_CODES = Collections.unmodifiableList(temp);
	}

	public static boolean contains(String lang) {
		return LANGUAGE_CODES.contains(lang);
	}


}
