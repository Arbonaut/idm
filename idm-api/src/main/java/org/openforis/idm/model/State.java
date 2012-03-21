/**
 * 
 */
package org.openforis.idm.model;

import java.util.BitSet;

/**
 * @author M. Togna
 * 
 */
public class State {
	private static final int N_BITS = 8;

	private BitSet bitSet;

	public State() {
		bitSet = new BitSet(N_BITS);
	}

	public void set(int position, boolean state) {
		if( position < 0 || position >= N_BITS ) {
			throw new IllegalArgumentException("Posion must be greather than 0 and less that  " + (N_BITS-1) );
		}
		
		bitSet.set(position, state);
	}

	public boolean get(int position) {
		if( position < 0 || position >= N_BITS ) {
			throw new IllegalArgumentException("Posion must be greather than 0 and less that  " + (N_BITS-1) );
		}
		
		return bitSet.get(position);
	}

	public int intValue() {
		String booleanString = getBooleanString();
		return Integer.parseInt( booleanString, 2 );
	}

	private String getBooleanString() {
		StringBuilder str = new StringBuilder(N_BITS);
		for (int i = N_BITS - 1; i >= 0; i--) {
			boolean b = bitSet.get(i);
			str.append( b ? "1" : "0" );
		}
		String booleanString = str.toString();
		return booleanString;
	}

	public static State parseState(int value) {
		if ( value > Math.pow( 2, N_BITS ) ) {
			throw new IllegalArgumentException("Value cannot be grater than " + Math.pow( 2, N_BITS )+ ", but it was " + value);
		}
		
		State state = new State();

		String binaryString = Integer.toBinaryString(value);
		char[] charArray = binaryString.toCharArray();

		int pos = binaryString.length();
		for ( char c : charArray ) {
			state.set(--pos, c == '1' ? true : false);
		}
		return state;
	}
	
	@Override
	public String toString() {
		return getBooleanString();
	}
	
	
	
}
