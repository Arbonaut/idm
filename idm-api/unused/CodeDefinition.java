/**
 * 
 */
package org.openforis.idm.metamodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeDefinition  {

//	@XmlAttribute(name = "scheme")
//	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
//	private String schemeName;
	
	@XmlTransient
	private CodeListItem item;
	
//	@XmlTransient
//	private CodingScheme scheme;

	@XmlValue
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	private String code;

//	public CodingScheme getScheme() {
//		return scheme;
//	}

	public String getCode() {
		return this.code;
	}

//	public String getSchemeName() {
//		return scheme == null ? null : scheme.getName();
//	}
//	
//	protected void setScheme(CodingScheme scheme) {
//		this.scheme = scheme;
//	}
//	
//	protected void setSchemeName(String name) {
//		if ( item == null ) {
//			throw new IllegalStateException("Code not attached to CodeListItem ");
//		}
//		CodeList list = item.getCodeList();
//		if ( list == null ) {
//			throw new IllegalStateException("CodeListItem not attached to CodeList");
//		}
//		CodingScheme newScheme = list.getCodingScheme(name);
//		if ( newScheme == null ) {
//			throw new IllegalArgumentException("CodingScheme '"+name+"' not defined in CodeList '"+list.getName()+"'");
//		}
//		this.scheme = newScheme;
//	}

	protected void setParentItem(CodeListItem item) {
		this.item = item;
	}
}
