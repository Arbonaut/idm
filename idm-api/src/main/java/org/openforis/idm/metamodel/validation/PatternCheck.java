/**
 * 
 */
package org.openforis.idm.metamodel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;*/
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Transient;

import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Code;
import org.openforis.idm.model.TextValue;

/**
 * @author G. Miceli
 * @author M. Togna
 * @author K. Waga
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@Order
public class PatternCheck extends Check<Attribute<?,?>> {

	private static final long serialVersionUID = 1L;

	@Transient
	private Pattern pattern;

	@org.simpleframework.xml.Attribute(name = "regex")
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
	public ValidationResultFlag evaluate(Attribute<?,?> node) {
		Object value = node.getValue();
		String string = null;
		if (value instanceof TextValue) {
			string = ((TextValue) value).getValue();
		} else if (value instanceof Code) {
			string = ((Code) value).getCode();
		} else {
			throw new IllegalArgumentException("Pattern check cannot be applied to value type " + value.getClass().getName());
		}

		Matcher matcher = getPattern().matcher(string);
		boolean matches = matcher.matches();
		return ValidationResultFlag.valueOf(matches, this.getFlag());
	}

}
