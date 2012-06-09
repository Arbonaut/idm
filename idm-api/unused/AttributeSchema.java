package org.openforis.idm.model;

/**
 * @author G. Miceli
 */
public class AttributeSchema<T extends Attribute<?,?>> extends NodeSchema<T> {
	
	public AttributeSchema(Class<T> typeClass) {
		super(AttributeSchema.fields(typeClass));
	}
	
	private static <V> Fields<V> fields(Class<V> typeClass){
		/* WARNING: deleting or reordering FIELD_TYPES or fields will break protostuff deserialization! */
		Fields<V> fields = new Fields<V>(typeClass, 2);
		fields.set(1, "definitionId");
		fields.set(2, "attributeFields");
		return fields;
	}
	
	@Override
	public T newMessage() {
		try {
			return typeClass.newInstance();
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		}
	}
}
