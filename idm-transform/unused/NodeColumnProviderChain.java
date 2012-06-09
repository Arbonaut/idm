package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.model.Node;
import org.openforis.idm.path.Path;

/**
 * @author G. Miceli
 */
public abstract class NodeColumnProviderChain  {

	public abstract List<NodeColumnProvider> getProviders();

//	@Override
	public List<Column> getColumns(Path parentPath) throws IllegalTransformationException {
		List<Column> columns = new ArrayList<Column>();
		for (NodeColumnProvider p : getProviders()) {
			List<Column> cols = p.getColumns(parentPath);
			columns.addAll(cols);
		}
		return Collections.unmodifiableList(columns);
	}

//	@Override
	public List<Cell> getCells(Node<?> parent) throws IllegalTransformationException {
		List<Cell> row = new ArrayList<Cell>();
		for (NodeColumnProvider p : getProviders()) {
			List<Cell> r = p.getCells(parent);
			row.addAll(r);
		}
		return Collections.unmodifiableList(row);
	}
}
