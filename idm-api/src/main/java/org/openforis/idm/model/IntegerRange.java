package org.openforis.idm.model;

import java.util.StringTokenizer;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class IntegerRange extends NumericRange<Integer> {

	public IntegerRange(Integer value) {
		super(value);
	}

	public IntegerRange(Integer from, Integer to) {
		super(from, to);
	}

	public static IntegerRange parseIntegerRange(String string) {
		StringTokenizer st = new StringTokenizer(string, DELIM);
		int from = Integer.parseInt(st.nextToken());

		if (st.hasMoreTokens()) {
			int to = Integer.parseInt(st.nextToken());
			return new IntegerRange(from, to);
		} else {
			return new IntegerRange(from);
		}
	}
}
