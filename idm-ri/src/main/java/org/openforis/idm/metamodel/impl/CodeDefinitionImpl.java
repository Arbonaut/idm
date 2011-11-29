/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.CodeDefinition;
import org.openforis.idm.metamodel.CodingScheme;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeDefinitionImpl implements CodeDefinition {

	@XmlAttribute(name = "scheme")
	String schemeName;
	@XmlTransient
	private CodingScheme codingScheme;

	@XmlValue
	@XmlJavaTypeAdapter(NormalizedStringAdapter.class)
	private String code;

	@Override
	public CodingScheme getCodingScheme() {
		return this.codingScheme;
	}

	@Override
	public void setCodingScheme(CodingScheme codingScheme) {
		this.codingScheme = codingScheme;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

}
