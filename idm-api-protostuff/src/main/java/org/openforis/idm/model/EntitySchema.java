package org.openforis.idm.model;

import java.util.ArrayList;
import java.util.Collection;

import com.dyuproject.protostuff.runtime.RuntimeFieldFactory;

/**
 * @author G. Miceli
 */
@SuppressWarnings("unchecked")
public class EntitySchema extends NodeSchema<Entity> {

	private static Collection<Field<Entity>> FIELDS;
	
	static {
		try {
			RuntimeFieldFactory<Entity> fieldFactory = (RuntimeFieldFactory<Entity>) RuntimeFieldFactory.getFieldFactory(Entity.class);
			Field<Entity> childrenByNameField = fieldFactory.create(2, "childrenByName", Entity.class.getDeclaredField("childrenByName"));
			FIELDS = new ArrayList<Field<Entity>>();
			FIELDS.add(new DefinitionIdField<Entity>());
			FIELDS.add(childrenByNameField);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}
	
	public EntitySchema() {
		super(Entity.class, FIELDS);
	}
	
	@Override
	public Entity newMessage() {
		return new Entity();
	}
	
}
