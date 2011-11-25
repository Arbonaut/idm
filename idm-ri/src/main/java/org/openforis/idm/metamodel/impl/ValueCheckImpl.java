/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import org.openforis.idm.metamodel.MultilingualStringMap;
import org.openforis.idm.metamodel.ValueCheck;

/**
 * @author Mino Togna
 * 
 */
public class ValueCheckImpl implements ValueCheck {

	private Flag flag;
	private String condition;
	private MultilingualStringMap messages;

	public Flag getFlag() {
		return flag;
	}

	public void setFlag(Flag flag) {
		this.flag = flag;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public MultilingualStringMap getMessages() {
		return messages;
	}

	public void setMessages(MultilingualStringMap messages) {
		this.messages = messages;
	}
}
