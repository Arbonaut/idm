package org.openforis.idm.metamodel;

import java.util.List;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Check  {

	public enum Flag {
		ERROR, WARN
	}

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
