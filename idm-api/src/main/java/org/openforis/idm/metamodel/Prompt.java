/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Prompt extends TypedLanguageSpecificText<Prompt.Type> {

	private static final long serialVersionUID = 1L;

	public enum Type {
		INTERVIEW, PAPER, HANDHELD, PC;
	}
	
	public Prompt(Type type, String language, String text) {
		super(type, language, text);
	}

}
