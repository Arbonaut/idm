package org.openforis.idm.metamodel;

import java.io.Serializable;

/*import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;*/
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.convert.Convert;
import org.simpleframework.xml.Order;

import org.openforis.idm.metamodel.CodeList.CodeScope;
import org.openforis.idm.metamodel.xml.internal.CodeScopeAdapter;

/**
 * @author K. Waga
 */

//@XmlAccessorType(XmlAccessType.FIELD)
@Order(attributes = "codeScope") 
class CodingScheme implements Serializable {

	private static final long serialVersionUID = 1L;

	@Attribute(name = "scope")
	@Convert(CodeScopeAdapter.class)
	private CodeScope codeScope;

	public CodeScope getCodeScope() {
		return this.codeScope;
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