package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.model.Node;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 */
public class ColumnProviderChain extends ColumnProvider {
	private List<ColumnProvider> providers;

	public ColumnProviderChain() {
		this.providers = new ArrayList<ColumnProvider>();
	}
	
	public ColumnProviderChain(List<ColumnProvider> providers) {
		setProviders(providers);
	}

	public ColumnProviderChain(ColumnProvider... providers) {
		this(Arrays.asList(providers));
	}

	public List<ColumnProvider> getProviders() {
		return CollectionUtil.unmodifiableList(providers);
	}
	
	public void setProviders(List<ColumnProvider> providers) {
		this.providers = new ArrayList<ColumnProvider>(providers);
	}
	
	public void addProvider(ColumnProvider provider) {
		addProvider(providers.size(), provider);
	}
	
	public void addProvider(int idx, ColumnProvider provider) {
		providers.add(idx, provider);
	}

	public List<Column> getColumns() {
		List<Column> columns = new ArrayList<Column>();
		for (ColumnProvider p : providers) {
			List<Column> cols = p.getColumns();
			columns.addAll(cols);
		}
		return Collections.unmodifiableList(columns);
	}

	public List<Cell> getCells(Node<?> parent) {
		List<Cell> row = new ArrayList<Cell>();
		for (ColumnProvider p : getProviders()) {
			List<Cell> r = p.getCells(parent);
			row.addAll(r);
		}
		return Collections.unmodifiableList(row);
	}
//	
//	protected List<String> emptyValues() {
//		ArrayList<String> v = new ArrayList<String>();
//		for (int i = 0; i < getColumns().size(); i++) {
//			v.add("");
//		}
//		return v;
//	}

//	@Override
//	public ColumnGroup getColumnGroup() {
//		return columnGroup;
//	}
}
