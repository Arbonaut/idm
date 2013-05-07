package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;
import org.openforis.idm.path.Axis;
import org.openforis.idm.path.InvalidPathException;
import org.openforis.idm.path.Path;

/**
 * Given a tuple axis and a set of element axes, extracts 
 * data or metadata from the IDM model and metamodel.  The tuple
 * axis defines what node type each tuples is to represent.  Element
 * axes define how to extract data and metadata elements relative to 
 * the tuple node.
 * 
 * In relational database terms, a pivot produces a single table, where
 * each rows represents an instance obtained by applying the tuple axis 
 * to the model, and columns are extracted by applying the element axes. 
 *  
 * @author G. Miceli
 */
public final class Pivot {
	private Axis tupleAxis;
	private List<Axis> elementAxes;
	
	public Pivot(Axis tupleAxis) {
		this.tupleAxis = tupleAxis;
		this.elementAxes = new ArrayList<Axis>();  
	}
	
	public void addElementAxis(Axis elementAxis) {
		elementAxes.add(elementAxis);
	}
	
	public Axis getTupleAxis() {
		return tupleAxis;
	}
	
	public int getElementCount() {
		return elementAxes.size();
	}

	public Axis getElementAxis(int i) {
		return elementAxes.get(i);
	}

	public List<Axis> getElementAxes() {
		return Collections.unmodifiableList(elementAxes);
	}

	public TupleDefinition apply(Schema schema) {
		NodeDefinition parentDefinition = tupleAxis.evaluate(schema);		
		TupleDefinition defn = new TupleDefinition(parentDefinition);
		for (Axis axis : elementAxes) {
			NodeDefinition elementDefn = axis.evaluate(parentDefinition);
			defn.addElementDefinition(elementDefn);
		}
		return defn;
	}
	
	public List<Tuple> apply(Record record) {
		Survey survey = record.getSurvey();
		Schema schema = survey.getSchema();
		TupleDefinition tupleDefinition = apply(schema);
		List<Node<?>> parentNodes = tupleAxis.evaluate(record);
		List<Tuple> tuples = new ArrayList<Tuple>();
		for (Node<?> parentNode : parentNodes) {
			Tuple tuple = new Tuple(tupleDefinition, parentNode);
			for (int i = 0; i < getElementCount(); i++) {
			    Axis axis = getElementAxis(i);
				List<Node<?>> nodes = axis.evaluate(parentNode);
				tuple.setElementNodes(i, nodes);
			}
			tuples.add(tuple);
		}
		return Collections.unmodifiableList(tuples);
	}
	
//	public static Pivot fromPath(String path) throws InvalidPathException {
//		Path pathObj = Path.parse(path);
//		return new Pivot(pathObj);
//	}
//	
//	public static Pivot fromNodeDefinition(NodeDefinition defn) {
//		Path path = Path.pathOf(defn);
//		return new Pivot(path);
//	}
}
