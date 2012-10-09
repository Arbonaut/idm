package org.openforis.idm.metamodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.CodeList.CodeScope;
import org.openforis.idm.metamodel.xml.internal.CodeScopeAdapter;

/**
 * Defines the coding schemes used by a code list
 * 
 * @author G. Miceli
 */
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
	
	public void setCodeScope(CodeScope codeScope) {
		this.codeScope = codeScope;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeScope == null) ? 0 : codeScope.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CodingScheme other = (CodingScheme) obj;
		if (codeScope != other.codeScope)
			return false;
		return true;
	}
	
}