package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.model.Record;

/**
 * Transforms metadata and data from IDM's n-dimensional hierarchical structure
 * into a four-dimensional structure: TupleDefinition/Tuple/Element/Node.
 *  
 * When element axes point to single nodes (non-multiple), one node per element
 * is produced, reducing the structure to a three-dimensions.  Transformation
 * results may then be mapped to database and flat-file Table/Row/Column structures.
 * 
 * @author G. Miceli
 */
public final class Transformation {
	private List<Pivot> pivots;
	
	public Transformation() {
		this.pivots = new ArrayList<Pivot>();
	}
	
	public void addPivot(Pivot pivot) {
		pivots.add(pivot);
	}
	
	public List<Pivot> getPivots() {
		return Collections.unmodifiableList(pivots);
	}
	
	public List<TupleDefinition> apply(Schema schema) {
		List<TupleDefinition> defns = new ArrayList<TupleDefinition>(pivots.size());
		for (Pivot pivot : pivots) {
			TupleDefinition defn = pivot.apply(schema);
			defns.add(defn);
		}
		return Collections.unmodifiableList(defns);
	}
	
	public List<Tuple> apply(Record record) {
		List<Tuple> result = new ArrayList<Tuple>();
		for (Pivot pivot : pivots) {
			List<Tuple> tuples = pivot.apply(record);
			result.addAll(tuples);
		}
		return Collections.unmodifiableList(result);
	}
}
