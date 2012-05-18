package org.openforis.idm.transform.flat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Field;
import org.openforis.idm.model.Node;
import org.openforis.idm.transform.ColumnProvider;

/**
 * @author G. Miceli
 */
public class SimpleAttributeColumnProvider implements ColumnProvider {

	private String attributeName;
	private String headerName;
	private String delimiter;

	public SimpleAttributeColumnProvider(String childName, String delimiter) {
		this.attributeName = childName;
		this.delimiter = delimiter;
		this.headerName = childName;
	}
	
	public SimpleAttributeColumnProvider(String childName, String delimiter, String headerName) {
		this.attributeName = childName;
		this.delimiter = delimiter;
		this.headerName = headerName;
	}
	
	public List<Column> getColumns() {
		return Collections.unmodifiableList(Arrays.asList(headerName));
	}
	
	public List<String> extractValues(Entity entity) {
		int cnt = entity.getCount(attributeName);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cnt; i++) {
			if ( i > 0 ) {
				sb.append(delimiter);
			}
			String val = extractValue(entity, i);
			sb.append(val);
		}
		return Arrays.asList(sb.toString());
	}

	private String extractValue(Entity entity, int i) {
		Attribute<?,?> attr = (Attribute<?, ?>) entity.get(attributeName, i);
		if ( attr == null ) {
			return ""; 
		} else {
			Field<?> fld = attr.getField(0);
			Object v = fld.getValue();
			return v == null ? "" : v.toString();
		}
	}
}