package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;

import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.Survey;

/**
 * 
 * @author G. Miceli
 *
 */
class SchemaIM extends AbstractIdmlMarshaller<Schema, Survey> {

	SchemaIM() {
		super(SCHEMA);
		addChildMarshallers(new EntityDefinitionIM());
	}
	
	@Override
	protected void marshalInstances(Survey survey) throws IOException {
		Schema schema = survey.getSchema();
		marshal(schema);
	}
}
