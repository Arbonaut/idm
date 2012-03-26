package org.openforis.idm.model;

/**
 * @author G. Miceli
 */
public class AttributeSchema extends NodeSchema<RealAttribute> {

	private static Fields<Entity> FIELDS;
	
	static {
		/* WARNING: deleting or reordering FIELD_TYPES or fields will break protostuff deserialization! */
		FIELDS = new Fields<RealAttribute>(RealAttribute.class, 2);
		FIELDS.set(new DefinitionIdField<Entity>(1, "definitionId"));
		FIELDS.set(2, "childrenByName");
	}
	
	public RealAttributeSchema() {
		super(FIELDS);
	}
	
	@Override
	public Entity newMessage() {
		return new RealAttribute();
	}
}
