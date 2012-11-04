package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Node;

/**
 * Analogous to a database row, a tuple represents a set of elements 
 * extracted from a single data node.
 * 
 * @author G. Miceli
 *
 */
public final class Tuple {
	private TupleDefinition tupleDefinition;
	private Node<?> node;
	private List<Element> elements;
	
	Tuple(TupleDefinition tupleDefinition, Node<?> node) {
		this.tupleDefinition = tupleDefinition;
		this.node = node;
		int elementCount = getElementCount();
		this.elements = new ArrayList<Element>(elementCount);
	}
	
	public Node<?> getNode() {
		return node;
	}
	
	public TupleDefinition getTupleDefinition() {
		return tupleDefinition;
	}

	public int getElementCount() {
		return tupleDefinition.getElementCount();
	}
	
	public Element getElement(int idx) {
		return elements.get(idx);
	}
	
	public void setElement(int idx, Element element) {
		elements.set(idx, element);
	}

	public void setElementNodes(int idx, List<Node<?>> nodes) {
	    NodeDefinition defn = tupleDefinition.getElementDefinition(idx);
		Element element = defn.isMultiple() ? new Element(nodes) : new SingleElement(nodes);
		setElement(idx, element);
	}

	public List<Element> getElements() {
		return Collections.unmodifiableList(elements);
	}
	
	/**
	 * A list of extracted nodes for a single element (e.g. column)
	 * 
	 * @author G. Miceli
	 *
	 */
	public static class Element {
		private List<Node<?>> nodes;
		
		Element(List<Node<?>> nodes) {
			this.nodes = nodes;
		}

		public int getNodeCount() {
			return nodes.size();
		}
		
		public List<Node<?>> getNodes() {
			return Collections.unmodifiableList(nodes);
		}
		
		public Node<?> getNode(int idx) {
			return nodes.get(idx);
		}
	}

	/**
	 * A single extracted nodes for a single element (e.g. column).  This
	 * applies to single nodes (non-multiple) only 
	 * 
	 * @author G. Miceli
	 *
	 */
	public static class SingleElement extends Element {
		SingleElement(List<Node<?>> nodes) {
			super(nodes);
		}

		public Node<?> getNode() {
			List<Node<?>> nodes = getNodes();
			return nodes.isEmpty() ? null : nodes.get(0);
		}
	}
}
