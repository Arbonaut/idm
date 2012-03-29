package org.openforis.idm.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class IntegerRange extends NumericRange<Integer> {

	private static final String REGEX = "(-?\\d+)(-(-?\\d+))?";
	private static final Pattern PATTERN = Pattern.compile(REGEX);

	public IntegerRange(Integer value) {
		super(value);
	}

	public IntegerRange(Integer from, Integer to) {
		super(from, to);
	}

	public static IntegerRange parseIntegerRange(String string) {
		if ( StringUtils.isBlank(string) ) {
			return null;
		} else {
			Matcher matcher = PATTERN.matcher(string);
			if ( matcher.matches() ) {
				String fromStr = matcher.group(1);
				String toStr = matcher.group(3);
				if ( toStr == null ) {
					toStr = fromStr;
				}
				int from = Integer.parseInt(fromStr);
				int to = Integer.parseInt(toStr);
				return new IntegerRange(from, to);
			} else {
				return null;
			}
		}
	}

}
