/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.xml.EnumAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeListLabel extends LanguageSpecificText {

	private static final long serialVersionUID = 1L;

	public enum Type { ITEM, LIST }
	
	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(EnumAdapter.class)
	private Type type;

	public Type getType() {
		return this.type;
	}
}
