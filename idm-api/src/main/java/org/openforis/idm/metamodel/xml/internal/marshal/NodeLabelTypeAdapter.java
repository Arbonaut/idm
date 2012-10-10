package org.openforis.idm.metamodel.xml.internal.marshal;

import org.openforis.idm.metamodel.NodeLabel;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public class NodeLabelTypeAdapter extends EnumAdapter<NodeLabel.Type> {

	public NodeLabelTypeAdapter() {
		super(NodeLabel.Type.class);
	}
}
