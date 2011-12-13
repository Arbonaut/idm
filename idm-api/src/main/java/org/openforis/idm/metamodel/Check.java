package org.openforis.idm.metamodel;

import java.util.List;

import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Value;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Check extends Rule {

	public enum Flag {
		ERROR, WARN
	}

	boolean execute(Attribute<? extends AttributeDefinition, ? extends Value> attribute);

	/**
	 * @return Returns the flag.
	 * @uml.property name="flag" readOnly="true"
	 */
	public Flag getFlag();

	/**
	 * @return Returns the condition.
	 * @uml.property name="condition" readOnly="true"
	 */
	public String getCondition();

	/**
	 * @return Returns the messages.
	 * @uml.property name="messages" readOnly="true"
	 */
	public List<LanguageSpecificText> getMessages();

}
