/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.Model;
import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.Unit;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "versions", "schema", "codeLists", "units" })
public class ModelImpl implements Model {

	@XmlElement(name = "schema", type = SchemaImpl.class)
	private Schema schema;

	@XmlElement(name = "version", type = ModelVersionImpl.class)
	@XmlElementWrapper(name = "versioning")
	private List<ModelVersion> versions;

	@XmlElement(name = "list", type = CodeListImpl.class)
	@XmlElementWrapper(name = "codeLists")
	private List<CodeList> codeLists;

	@XmlElement(name = "unit", type = UnitImpl.class)
	@XmlElementWrapper(name = "units")
	private List<Unit> units;

	@Override
	public Schema getSchema() {
		return this.schema;
	}

	@Override
	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	@Override
	public List<ModelVersion> getVersions() {
		return this.versions;
	}

	@Override
	public void setVersions(List<ModelVersion> versions) {
		this.versions = versions;
	}

	@Override
	public List<CodeList> getCodeLists() {
		return this.codeLists;
	}

	@Override
	public void setCodeLists(List<CodeList> codeLists) {
		this.codeLists = codeLists;
	}

	@Override
	public List<Unit> getUnits() {
		return this.units;
	}

	@Override
	public void setUnits(List<Unit> units) {
		this.units = units;
	}

}
