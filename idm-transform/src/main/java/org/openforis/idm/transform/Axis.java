package org.openforis.idm.transform;

import java.util.List;

import org.openforis.idm.model.Node;

/**
 * @author G. Miceli
 */
public interface Axis {
	List<Node<?>> pivot(Node<?> node);
}
