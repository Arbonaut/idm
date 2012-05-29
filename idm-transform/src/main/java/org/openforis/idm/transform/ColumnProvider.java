package org.openforis.idm.transform;

import java.util.List;

import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 */
public interface ColumnProvider {
	
	List<Column> getColumns() throws IllegalTransformationException;
	
	List<Cell> getCells(Node<?> parentNode) throws IllegalTransformationException;
	
}
