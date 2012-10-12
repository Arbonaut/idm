package org.openforis.idm.metamodel.xml.internal.marshal;

import java.io.IOException;
import java.util.List;

import org.openforis.idm.metamodel.BooleanAttributeDefinition;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CoordinateAttributeDefinition;
import org.openforis.idm.metamodel.DateAttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.FileAttributeDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.NumberAttributeDefinition;
import org.openforis.idm.metamodel.RangeAttributeDefinition;
import org.openforis.idm.metamodel.TaxonAttributeDefinition;
import org.openforis.idm.metamodel.TextAttributeDefinition;
import org.openforis.idm.metamodel.TimeAttributeDefinition;

/**
 * @author G. Miceli
 */
public class NodeDefinitionIM extends PolymorphicIdmlMarshaller<NodeDefinition, EntityDefinition> {
	public NodeDefinitionIM(EntityDefinitionIM entityIM) {
		setDelegate(EntityDefinition.class, entityIM);
		setDelegate(CodeAttributeDefinition.class, new CodeAttributeIM());
		setDelegate(BooleanAttributeDefinition.class, new BooleanAttributeIM());
		setDelegate(TextAttributeDefinition.class, new TextAttributeIM());
		setDelegate(NumberAttributeDefinition.class, new NumberAttributeIM());
		setDelegate(RangeAttributeDefinition.class, new RangeAttributeIM());
		setDelegate(FileAttributeDefinition.class, new FileAttributeIM());
		setDelegate(CoordinateAttributeDefinition.class, new CoordinateAttributeIM());
		setDelegate(DateAttributeDefinition.class, new DateAttributeIM());
		setDelegate(TimeAttributeDefinition.class, new TimeAttributeIM());
		setDelegate(TaxonAttributeDefinition.class, new TaxonAttributeIM());
	}
	
	@Override
	protected void marshalInstances(EntityDefinition parent) throws IOException {
		List<NodeDefinition> children = parent.getChildDefinitions();
		marshal(children);
	}
}
