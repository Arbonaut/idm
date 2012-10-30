package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.metamodel.NodeDefinition;

/**
 * @author G. Miceli
 */
public class TupleDefinition {
	private NodeDefinition parentDefinition;
	private List<NodeDefinition> elementDefinitions;
	
	public TupleDefinition(NodeDefinition parentDefinition) {
		this.parentDefinition = parentDefinition;
		this.elementDefinitions = new ArrayList<NodeDefinition>();
	}
	
	public NodeDefinition getParentDefinition() {
		return parentDefinition;
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
