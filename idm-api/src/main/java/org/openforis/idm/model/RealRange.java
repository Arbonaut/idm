package org.openforis.idm.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class RealRange extends NumericRange<Double> {

	private static final String REGEX = "(-?\\d+(\\.\\d+)?)(-(-?\\d+(\\.\\d+)?))?";
	private static final Pattern PATTERN = Pattern.compile(REGEX);

	public RealRange(Double value) {
		super(value);
	}

	public RealRange(Double from, Double to) {
		super(from, to);
	}

	public static RealRange parseRealRange(String string) {
		if ( StringUtils.isBlank(string) ) {
			return null;
		} else {
			Matcher matcher = PATTERN.matcher(string);
			if ( matcher.matches() ) {
				String fromStr = matcher.group(1);
				String toStr = matcher.group(4);
				if ( toStr == null ) {
					toStr = fromStr;
				}
				double from = Double.parseDouble(fromStr);
				double to = Double.parseDouble(toStr);
				return new RealRange(from, to);
			} else {
				return null;
			}
		}
	}
	
}
