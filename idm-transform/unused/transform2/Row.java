package org.openforis.idm.transform2;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author G. Miceli
 */
public class Row implements Iterable<Cell> {
	private List<Cell> cells;
	
	public Row(List<Cell> cells) {
		this.cells = Collections.unmodifiableList(cells);
	}

	public List<Cell> getCells() {
		return cells;
	}

	@Override
	public Iterator<Cell> iterator() {
		return cells.iterator();
	}
}
