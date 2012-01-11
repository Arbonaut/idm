package org.openforis.idm.metamodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.CodeList.CodeScope;
import org.openforis.idm.metamodel.CodeList.CodeType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {  "codeScope", "codeType" }) 
class CodingScheme implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "scope")
	private CodeScope codeScope;

	@XmlAttribute(name = "type")
	private CodeType codeType;

	public CodeType getCodeType() {
		return this.codeType;
	}

	public CodeScope getCodeScope() {
		return this.codeScope;
	}
}