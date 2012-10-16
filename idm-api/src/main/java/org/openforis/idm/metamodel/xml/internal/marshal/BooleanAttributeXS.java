package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;

import org.openforis.idm.metamodel.BooleanAttributeDefinition;

/**
 * 
 * @author G. Miceli
 *
 */
class BooleanAttributeXS extends AttributeDefinitionXS<BooleanAttributeDefinition> {

	BooleanAttributeXS() {
		super(CODE);
	}

	@Override
	protected void attributes(BooleanAttributeDefinition defn) throws IOException {
		super.attributes(defn);
		if ( defn.isAffirmativeOnly() ) {
			attribute(AFFIRMATIVE_ONLY, true);
		}
	}
}
