/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.PatternCheck;
import org.openforis.idm.model.AlphanumericCode;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.TextValue;
import org.openforis.idm.model.Value;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class PatternCheckImpl extends AbstractCheck implements PatternCheck {

	@XmlTransient
	private Pattern pattern;

	@XmlAttribute(name = "regex")
	private String regularExpression;

	@Override
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
	public boolean execute(Attribute<? extends AttributeDefinition, ? extends Value> attribute) {
		Value value = attribute.getValue();
		String string = null;
		// textvalue
		// code
		if (value instanceof TextValue) {
			string = ((TextValue) value).getString();
		} else if (value instanceof AlphanumericCode) {
			string = ((AlphanumericCode) value).getCode();
		} else {
			throw new IllegalArgumentException("Pattern check cannot be applied to value type " + value.getClass().getName());
		}

		Matcher matcher = getPattern().matcher(string);
		boolean matches = matcher.matches();
		return matches;
	}

}
