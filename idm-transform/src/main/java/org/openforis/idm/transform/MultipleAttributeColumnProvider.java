package org.openforis.idm.transform;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Entity;
import org.openforis.idm.model.Field;
import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 */
public class MultipleAttributeColumnProvider implements ColumnProvider {

	private List<Column> columns; 
	private String attributeName;
	private String delimiter;

	public MultipleAttributeColumnProvider(String attributeName, String delimiter) {
		this(attributeName, delimiter, attributeName);
	}
	
	public MultipleAttributeColumnProvider(String attributeName, String delimiter, String columnName) {
		this.attributeName = attributeName;
		this.delimiter = delimiter;
		this.columns = Collections.unmodifiableList(Arrays.asList(new Column(columnName)));
	}
	
	public List<Column> getColumns() {
		return columns;
	}
	
	public List<String> extractValues(Node<?> axis) {
		if ( axis instanceof Entity ) {
			Entity entity = (Entity) axis;
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
		} else {
			throw new UnsupportedOperationException();
		}
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