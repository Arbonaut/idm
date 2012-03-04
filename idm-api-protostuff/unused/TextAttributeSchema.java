package org.openforis.idm.model;

import java.io.IOException;

import org.openforis.idm.model.TextAttribute;

import com.dyuproject.protostuff.Input;
import com.dyuproject.protostuff.Output;
import com.dyuproject.protostuff.Schema;

public class TextAttributeSchema implements Schema<TextAttribute> {

	@Override
	public String getFieldName(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFieldNumber(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isInitialized(TextAttribute message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TextAttribute newMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String messageName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String messageFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? super TextAttribute> typeClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mergeFrom(Input input, TextAttribute message)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeTo(Output output, TextAttribute message)
			throws IOException {
		// TODO Auto-generated method stub

	}

}
