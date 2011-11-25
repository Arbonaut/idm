/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.Model;
import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.Unit;

/**
 * @author Mino Togna
 * 
 */
public class ModelImpl implements Model {
	
	private Schema schema;
	private List<ModelVersion> versions;
	private List<CodeList> codeLists;
	private List<Unit> units;

	public Schema getSchema() {
		return schema;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	public List<ModelVersion> getVersions() {
		return versions;
	}

	public void setVersions(List<ModelVersion> versions) {
		this.versions = versions;
	}

	public List<CodeList> getCodeLists() {
		return codeLists;
	}

	public void setCodeLists(List<CodeList> codeLists) {
		this.codeLists = codeLists;
	}

	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}

}
