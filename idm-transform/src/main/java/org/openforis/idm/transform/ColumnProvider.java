package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.List;

import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 */
public abstract class ColumnProvider {
	
	public abstract List<Column> getColumns();
	
	public abstract List<Cell> getCells(Node<?> parent);
	
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
