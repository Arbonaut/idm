package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * In relational database terms, a tuple is a table row 
 * with a fixed number of elements (columns).  This class
 * is analogous to a database table definition, describing
 * the elements (columns) contained therein.   
 * 
 * @author G. Miceli
 */
public class TupleDefinition {
	private NodeDefinition nodeDefinition;
	private List<NodeDefinition> elementDefinitions;
	
	public TupleDefinition(NodeDefinition nodeDefinition) {
		this.nodeDefinition = nodeDefinition;
		this.elementDefinitions = new ArrayList<NodeDefinition>();
	}
	
	public NodeDefinition getNodeDefinition() {
		return nodeDefinition;
	}

	public void addElementDefinition(NodeDefinition elementDefn) {
		elementDefinitions.add(elementDefn);
	}
	
	public List<NodeDefinition> getElementDefinitions() {
		return Collections.unmodifiableList(elementDefinitions);
	}

	public int getElementCount() {
		return elementDefinitions.size();
	}
	
	public NodeDefinition getElementDefinition(int idx) {
		return elementDefinitions.get(idx);
	}
}
