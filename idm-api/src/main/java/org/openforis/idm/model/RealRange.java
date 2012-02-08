package org.openforis.idm.model;

import java.util.StringTokenizer;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public final class RealRange extends NumericRange<Double> {

	public RealRange(Double value) {
		super(value);
	}

	public RealRange(Double from, Double to) {
		super(from, to);
	}

	public static RealRange parseRealRange(String string) {
		StringTokenizer st = new StringTokenizer(string, DELIM);
		double from = Double.parseDouble(st.nextToken());

		if (st.hasMoreTokens()) {
			double to = Double.parseDouble(st.nextToken());
			return new RealRange(from, to);
		} else {
			return new RealRange(from);
		}
	}
}
