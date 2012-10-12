/**
 * 
 */
package org.openforis.idm.metamodel;

/**
 * @author S. Ricci
 *
 */
public class NodeLabelMap extends TypedLanguageSpecificTextAbstractMap<NodeLabel, NodeLabel.Type> {
	
	public void add(NodeLabel label) {
		super.add(label.getType(), label);
	}

}
