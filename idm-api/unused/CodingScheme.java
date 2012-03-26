package org.openforis.idm.metamodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.CodeList.CodeScope;
import org.openforis.idm.metamodel.CodeList.CodeType;
import org.openforis.idm.metamodel.xml.internal.CodeScopeAdapter;
import org.openforis.idm.metamodel.xml.internal.CodeTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {  "codeScope", "codeType" }) 
class CodingScheme implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "scope")
	@XmlJavaTypeAdapter(CodeScopeAdapter.class)
	private CodeScope codeScope;

	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(CodeTypeAdapter.class)
	private CodeType codeType;

	public CodeType getCodeType() {
		return this.codeType;
	}

	public CodeScope getCodeScope() {
		return this.codeScope;
	}
}