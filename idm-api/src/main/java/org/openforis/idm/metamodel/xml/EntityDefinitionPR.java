package org.openforis.idm.metamodel.xml;

import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author G. Miceli
 */
class EntityDefinitionPR extends NodeDefinitionPR {
	public EntityDefinitionPR() {
		super("entity");
		setChildPullReaders(
				new LabelPR(), 
				new DescriptionPR(),
				this,
				new BooleanAttributeDefinitionPR(), 
				new CodeAttributeDefinitionPR(),
				new CoordinateAttributeDefinitionPR(),
				new DateAttributeDefinitionPR(),
				new TimeAttributeDefinitionPR(),
				new FileAttributeDefinitionPR(),
				new NumberAttributeDefinitionPR(),
				new RangeAttributeDefinitionPR(),
				new TaxonAttributeDefinitionPR(),
				new TextAttributeDefinitionPR());
	}

	@Override
	protected NodeDefinition createDefinition(int id) {
		return schema.createEntityDefinition(id);
	}
	
	@Override
	protected void onEndTag(XmlPullParser parser)
			throws XmlParseException {
		if ( parentDefn == null ) {
			schema.addRootEntityDefinition((EntityDefinition) defn);
		} else {
			EntityDefinition parentEntity = (EntityDefinition) parentDefn;
			parentEntity.addChildDefinition(defn);
		}
		super.onEndTag(parser);
	}
}