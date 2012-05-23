package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 */
public abstract class ColumnProvider {
	
	public abstract List<Column> getColumns();
	
	public abstract List<Cell> getCells(Node<?> parent);
	
	public List<String> getColumnHeadings() {
		List<Column> columns = getColumns();
		List<String> headings = new ArrayList<String>(columns.size());
		for (Column column : columns) {
			String h = column.getHeadings();
			headings.add(h);
		}
		return Collections.unmodifiableList(headings); 
	}
	
	public List<String> getColumnNames() {
		List<Column> columns = getColumns();
		List<String> names = new ArrayList<String>(columns.size());
		for (Column column : columns) {
			String h = column.getName();
			names.add(h);
		}
		return Collections.unmodifiableList(names); 
	}
	
	public List<Cell> getEmptyCells() {
		List<Column> cols = getColumns();
		List<Cell> cells = new ArrayList<Cell>(cols.size());
		for (Column col : cols) {
			Cell cell = new Cell(null, col.getValueType(), col);
			cells.add(cell);
		}
		return cells;
	}

}
