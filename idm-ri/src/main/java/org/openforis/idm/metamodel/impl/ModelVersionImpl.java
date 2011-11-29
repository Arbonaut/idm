/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.Date;

import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.MultilingualStringMap;

/**
 * @author Mino Togna
 * 
 */
public class ModelVersionImpl implements ModelVersion {

	private String name;
	private MultilingualStringMap labels;
	private Date date;
	private MultilingualStringMap descriptions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MultilingualStringMap getLabels() {
		return labels;
	}

	public void setLabels(MultilingualStringMap labels) {
		this.labels = labels;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public MultilingualStringMap getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(MultilingualStringMap descriptions) {
		this.descriptions = descriptions;
	}

}
