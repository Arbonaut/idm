package org.openforis.idm.metamodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.CodeList.CodeScope;
import org.openforis.idm.metamodel.xml.internal.CodeScopeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {  "codeScope" }) 
class CodingScheme implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "scope")
	@XmlJavaTypeAdapter(CodeScopeAdapter.class)
	private CodeScope codeScope;

	public CodeScope getCodeScope() {
		return this.codeScope;
	}
}