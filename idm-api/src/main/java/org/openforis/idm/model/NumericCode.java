package org.openforis.idm.model;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class NumericCode extends Code<Integer> {

	public NumericCode(Integer code) {
		super(code);
	}

	public NumericCode(Integer code, String qualifier) {
		super(code, qualifier);
	}

	public static void main(String[] args) {
		FakeCode<?> code1 = new FakeCode<Integer>(25);
		FakeCode<?> code2 = new FakeCode<String>("25");
		
		System.out.println(code1.equals(code2));
	}
}

class FakeCode<T> extends Code<T> {

	public FakeCode(T code) {
		super(code);
	}
	
}