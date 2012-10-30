package org.openforis.idm.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.model.Node;

/**
 * 
 * @author G. Miceli
 *
 */
public final class Tuple {
	private TupleDefinition tupleDefinition;
	private Node<?> parentNode;
	private List<Element> elements;
	
	public Tuple(TupleDefinition tupleDefinition, Node<?> parentNode) {
		this.tupleDefinition = tupleDefinition;
		this.parentNode = parentNode;
		int elementCount = getElementCount();
		this.elements = new ArrayList<Element>(elementCount);
	}
	
	public Node<?> getParentNode() {
		return parentNode;
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
