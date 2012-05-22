package org.openforis.idm.transform;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.metamodel.FieldDefinition;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Field;
import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 */
public class FieldColumnProvider extends ColumnProvider {

	private FieldDefinition fieldDefinition;
	private List<Column> columns;
	
	public FieldColumnProvider(FieldDefinition fieldDefinition, ColumnGroup columnGroup) {		
		this(fieldDefinition, columnGroup, fieldDefinition.getSuffix());
	}

	public FieldColumnProvider(FieldDefinition fieldDefinition, ColumnGroup columnGroup, String shortName) {		
		this.fieldDefinition = fieldDefinition;
		Class<?> type = fieldDefinition.getValueType();
		// TODO attach to defn
		// TODO col.setHeading(heading)
		Column col = new Column(shortName, null, null, columnGroup, this, type);
		this.columns = Collections.unmodifiableList(Arrays.asList(col));
	}

	public Column getColumn() {
		return columns.get(0);
	}
	
	@Override
	public List<Column> getColumns() {
		return columns;
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
			Column col = getColumn();
			Cell cell = new Cell(val, fieldDefinition.getValueType(), col, parent);  // TODO Use field as a node
			return Arrays.asList(cell);
		} else {
			throw new IllegalArgumentException("Expected "+Attribute.class+" but got "+parent.getClass());
		}
	}

	public FieldDefinition getFieldDefinition() {
		return fieldDefinition;
	}
}
