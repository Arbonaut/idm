package org.openforis.idm.metamodel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface ExplicitCheck extends Check {

	public enum Flag {
		ERROR, WARN
	}

	/**
	 * @return  Returns the flag.
	 * @uml.property  name="flag"
	 */
	public Flag getFlag();

	/**
	 * Setter of the property <tt>flag</tt>
	 * @param flag  The flag to set.
	 * @uml.property  name="flag"
	 */
	public void setFlag(Flag flag);

	/**
	 * @return  Returns the condition.
	 * @uml.property  name="condition"
	 */
	public String getCondition();

	/**
	 * Setter of the property <tt>condition</tt>
	 * @param condition  The condition to set.
	 * @uml.property  name="condition"
	 */
	public void setCondition(String condition);

	/**
	 * @return  Returns the messages.
	 * @uml.property  name="messages"
	 */
	public MultilingualStringMap getMessages();

	/**
	 * Setter of the property <tt>messages</tt>
	 * @param messages  The messages to set.
	 * @uml.property  name="messages"
	 */
	public void setMessages(MultilingualStringMap messages);
}
