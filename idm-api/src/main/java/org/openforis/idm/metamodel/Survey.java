package org.openforis.idm.metamodel;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.w3c.dom.Element;

/**
 * @author G. Miceli
 * @author M. Togna
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "projectNames", "cycle", "descriptions", "configuration", "modelVersions",
		"codeLists", "units", "spatialReferenceSystems", "schema" })
@XmlRootElement(name = "survey")
public class Survey {

	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "project", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> projectNames;

	@XmlElement(name = "cycle")
	private Integer cycle;

	@XmlElement(name = "description", type = LanguageSpecificText.class)
	private List<LanguageSpecificText> descriptions;

	@XmlElement(name = "configuration")
	@XmlJavaTypeAdapter(value = ElementTypeAdapter.class)
	private Element configuration;

	@XmlElement(name = "version", type = ModelVersion.class)
	@XmlElementWrapper(name = "versioning")
	private List<ModelVersion> modelVersions;

	@XmlElement(name = "list", type = CodeList.class)
	@XmlElementWrapper(name = "codeLists")
	private List<CodeList> codeLists;

	@XmlElement(name = "unit", type = Unit.class)
	@XmlElementWrapper(name = "units")
	private List<Unit> units;

	@XmlElement(name = "spatialReferenceSystem", type = SpatialReferenceSystem.class)
	@XmlElementWrapper(name = "spatialReferenceSystems")
	private List<SpatialReferenceSystem> spatialReferenceSystems;

	@XmlElement(name = "schema", type = Schema.class)
	private Schema schema;

	public String getName() {
		return this.name;
	}

	public List<LanguageSpecificText> getProjectNames() {
		return Collections.unmodifiableList(this.projectNames);
	}

	public Integer getCycle() {
		return this.cycle;
	}

	public List<LanguageSpecificText> getDescriptions() {
		return Collections.unmodifiableList(this.descriptions);
	}

	public Element getConfiguration() {
		return this.configuration;
	}
	
	public List<ModelVersion> getVersions() {
		return Collections.unmodifiableList(this.modelVersions);
	}

	public List<CodeList> getCodeLists() {
		return Collections.unmodifiableList(this.codeLists);
	}

	public List<Unit> getUnits() {
		return Collections.unmodifiableList(this.units);
	}

	public List<SpatialReferenceSystem> getSpatialReferenceSystems() {
		return Collections.unmodifiableList(this.spatialReferenceSystems);
	}

	public Schema getSchema() {
		return this.schema;
	}
	
	/**
	 * Passes DOM Element directly without conversion
	 */
	private static class ElementTypeAdapter extends XmlAdapter<Object, Object> {
		@Override
		public Object unmarshal(Object v) {
			return v;
		}

		@Override
		public Object marshal(Object v) throws Exception {
			return v;
		}
	}

	public ModelVersion getVersion(String name) {
		if ( modelVersions != null ) {
			for (ModelVersion v : modelVersions) {
				if ( name.equals(v.getName()) ) {
					return v;
				}
			}
		}
		return null;
	}

	public CodeList getCodeList(String name) {
		for (CodeList codeList : codeLists) {
			if (codeList.getName().equals(name)) {
				return codeList;
			}
		}
		return null;
	}

	public Unit getUnit(String name) {
		for (Unit unit : units) {
			if (unit.getName().equals(name)) {
				return unit;
			}
		}
		return null;
	}
}