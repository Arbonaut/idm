/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.state.NodeState;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class PatternCheck extends Check {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private Pattern pattern;

	@XmlAttribute(name = "regex")
	private String regularExpression;

	public String getRegularExpression() {
		return this.regularExpression;
	}

	private Pattern getPattern() {
		if (pattern == null) {
			this.pattern = Pattern.compile(regularExpression);
		}
		return pattern;
	}

	@Override
	public boolean evaluate(NodeState nodeState) {
		Attribute<?,?> node = (Attribute<?, ?>) nodeState.getNode();
		Object value = node.getValue();
		String string = null;
		if (value instanceof String) {
			string = (String) value;
		} else if (value instanceof Code) {
			string = ((Code) value).getCode();
		} else {
			throw new IllegalArgumentException("Pattern check cannot be applied to value type " + value.getClass().getName());
		}

		Matcher matcher = getPattern().matcher(string);
		boolean matches = matcher.matches();
		return matches;
	}

}
