package org.openforis.idm.metamodel.xml.internal;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

/**
 * @author K. Waga
 */

public class CollapsedStringAdapter implements Converter<String> {

	@Override
	public String read(InputNode in) throws Exception {
		if (in == null){
			return null;
		}
		else{
			return in.getValue().trim().replaceAll("[\t\r\n]", " ");
		}
	}

	@Override
	public void write(OutputNode out, String v) throws Exception {
		
	}
}
