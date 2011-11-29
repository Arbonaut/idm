/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import org.openforis.idm.metamodel.MultilingualStringMap;
import org.openforis.idm.metamodel.SpatialReferenceSystem;

/**
 * @author Mino Togna
 * 
 */
public class SpatialReferenceSystemImpl implements SpatialReferenceSystem {

	private String id;
	private MultilingualStringMap labels;
	private MultilingualStringMap descriptions;
	private String wellKnownText;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MultilingualStringMap getLabels() {
		return labels;
	}

	public void setLabels(MultilingualStringMap labels) {
		this.labels = labels;
	}

	public MultilingualStringMap getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(MultilingualStringMap descriptions) {
		this.descriptions = descriptions;
	}

	public String getWellKnownText() {
		return wellKnownText;
	}

	public void setWellKnownText(String wellKnownText) {
		this.wellKnownText = wellKnownText;
	}

}
