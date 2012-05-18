package org.openforis.idm.transform;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.metamodel.FieldDefinition;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Field;
import org.openforis.idm.model.Node;

public class FieldColumnProvider extends ColumnProvider {

	private FieldDefinition fieldDefinition;
	private List<Column> columns;
	
	public FieldColumnProvider(FieldDefinition fieldDefinition, ColumnGroup columnGroup) {		
		this.fieldDefinition = fieldDefinition;
		Column col = new Column();
		col.setShortName(fieldDefinition.getSuffix());
		col.setValueType(fieldDefinition.getValueType());
		col.setParentGroup(columnGroup);
//		TODO col.setHeading(heading)
		this.columns = Arrays.asList(col);
	}

	@Override
	public List<Column> getColumns() {
		return Collections.unmodifiableList(columns);
	}

	@Override
	public List<Cell> getCells(Node<?> parent) {
		if ( parent == null ) {
			return getEmptyCells();
		} else if ( parent instanceof Attribute ) {
			Attribute<?,?> attr = (Attribute<?,?>) parent;
			String fieldName = fieldDefinition.getName();
			Field<?> fld = attr.getField(fieldName);
			Object val = fld.getValue();
			Cell cell = new Cell(val, fieldDefinition.getValueType(), parent);  // TODO Use field as a node
			return Arrays.asList(cell);
		} else {
			throw new IllegalArgumentException("Expected "+Attribute.class+" but got "+parent.getClass());
		}
	}

	public FieldDefinition getFieldDefinition() {
		return fieldDefinition;
	}
}
