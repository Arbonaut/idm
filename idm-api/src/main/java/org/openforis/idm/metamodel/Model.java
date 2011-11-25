package org.openforis.idm.metamodel;

import java.util.List;

/**
 * @author G. Miceli
 * @author M. Togna
 */
public interface Model {

	/**
	 * @return  Returns the schema.
	 * @uml.property  name="schema"
	 * @uml.associationEnd  aggregation="composite" inverse="model:org.openforis.idm.metamodel.Schema"
	 */
	public Schema getSchema();

	/**
	 * Setter of the property <tt>schema</tt>
	 * @param schema  The schema to set.
	 * @uml.property  name="schema"
	 */
	public void setSchema(Schema schema);

	/**
	 * @return  Returns the versions.
	 * @uml.property  name="versions"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite" inverse="model:org.openforis.idm.metamodel.ModelVersion"
	 */
	public List<ModelVersion> getVersions();

	/**
	 * Setter of the property <tt>versions</tt>
	 * @param versions  The versions to set.
	 * @uml.property  name="versions"
	 */
	public void setVersions(List<ModelVersion> versions);

	/**
	 * @return  Returns the codeLists.
	 * @uml.property  name="codeLists"
	 * @uml.associationEnd  multiplicity="(0 -1)" aggregation="composite" inverse="model:org.openforis.idm.metamodel.CodeList"
	 */
	public List<CodeList> getCodeLists();

	/**
	 * Setter of the property <tt>codeLists</tt>
	 * @param codeLists  The codeLists to set.
	 * @uml.property  name="codeLists"
	 */
	public void setCodeLists(List<CodeList> codeLists);

	/**
	 * @return  Returns the units.
	 * @uml.property  name="units"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" container="java.util.List" aggregation="composite" inverse="model:org.openforis.idm.metamodel.Unit"
	 */
	public List<Unit> getUnits();

	/**
	 * Setter of the property <tt>units</tt>
	 * @param units  The units to set.
	 * @uml.property  name="units"
	 */
	public void setUnits(List<Unit> units);

}
