/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.io.Serializable;
import java.util.List;

/*import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;*/
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.convert.Convert;

import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.xml.internal.CheckFlagAdapter;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author K. Waga
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@Order(attributes = "")
public abstract class Check<T extends Attribute<?, ?>> implements Serializable, ValidationRule<T> {

	private static final long serialVersionUID = 1L;

	public enum Flag {
		ERROR, WARN
	}

	@org.simpleframework.xml.Attribute(name = "flag")
	@Convert(CheckFlagAdapter.class)
	private Flag flag;

	@org.simpleframework.xml.Attribute(name = "if")
	private String condition;

	@Element(name = "message", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> messages;

	public Flag getFlag() {
		return flag == null ? Flag.ERROR : flag;
	}

	public void setFlag(Flag flag) {
		this.flag = flag;
	}
	
	public String getCondition() {
		return this.condition;
	}

	public List<LanguageSpecificText> getMessages() {
		return CollectionUtil.unmodifiableList(this.messages);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((messages == null) ? 0 : messages.hashCode());
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
		Check<?> other = (Check<?>) obj;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (flag != other.flag)
			return false;
		if (messages == null) {
			if (other.messages != null)
				return false;
		} else if (!messages.equals(other.messages))
			return false;
		return true;
	}
	
	
}
