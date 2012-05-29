package org.openforis.idm.transform;

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
 * Provider chain is created on instantiation based IDML schema 
 * 
 * Columns are generated one call to getColumns based on provider parameters
 * 
 * @author G. Miceli
 */
public class EntityColumnProvider extends NodeColumnProvider {

//	private static final Log LOG = LogFactory.getLog(EntityColumnProvider.class);

	public EntityColumnProvider(EntityDefinition entityDefinition) {
		super(entityDefinition, null);
	}
	
	public EntityColumnProvider(EntityDefinition entityDefinition, EntityColumnProvider parentProvider) {
		super(entityDefinition, parentProvider);
	}
	
	@Override
	protected void createChildProviders(List<NodeColumnProvider> providers) throws IllegalTransformationException {
		EntityDefinition defn = (EntityDefinition) getNodeDefinition();
		List<NodeDefinition> children = defn.getChildDefinitions();
		for (NodeDefinition child : children) {
			NodeColumnProvider provider;
			if ( child instanceof EntityDefinition ) {
				provider = new EntityColumnProvider((EntityDefinition) child, this);
			} else if ( child instanceof NumberAttributeDefinition ) {
				provider = new NumberColumnProvider((NumberAttributeDefinition) child, this);
			} else if ( child instanceof BooleanAttributeDefinition ) { 
				provider = new BooleanColumnProvider((BooleanAttributeDefinition) child, this);
			} else if ( child instanceof CodeAttributeDefinition ) { 
				provider = new CodeColumnProvider((CodeAttributeDefinition) child, this);
			} else if ( child instanceof TextAttributeDefinition ) { 
				provider = new TextColumnProvider((TextAttributeDefinition) child, this);
			} else if ( child instanceof DateAttributeDefinition ) {
				provider = new DateColumnProvider((DateAttributeDefinition) child, this);
			} else if ( child instanceof TimeAttributeDefinition ) {
				provider = new TimeColumnProvider((TimeAttributeDefinition) child, this);
			} else if ( child instanceof RangeAttributeDefinition ) { 
				provider = new RangeColumnProvider((RangeAttributeDefinition) child, this);
			} else if ( child instanceof TaxonAttributeDefinition ) { 
				provider = new TaxonColumnProvider((TaxonAttributeDefinition) child, this);
			} else if ( child instanceof CoordinateAttributeDefinition ) { 
				provider = new CoordinateColumnProvider((CoordinateAttributeDefinition) child, this);
			} else if ( child instanceof FileAttributeDefinition ) { 
				provider = new FileColumnProvider((FileAttributeDefinition) child, this);
			} else {
				throw new RuntimeException("Entity should never have child "+child.getClass());
			}
			providers.add(provider);
		}
	}
}

