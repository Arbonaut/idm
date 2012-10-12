package org.openforis.idm.model;

import java.io.IOException;
import java.util.HashMap;

import com.dyuproject.protostuff.Input;
import com.dyuproject.protostuff.Output;
import com.dyuproject.protostuff.Pipe;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.WireFormat.FieldType;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * @author G. Miceli
 */
public class EntitySchema extends NodeSchema<Entity> {

	private static Fields<Entity> FIELDS;
	
	static {
		/* WARNING: deleting or reordering FIELD_TYPES or fields will break protostuff deserialization! */
		FIELDS = new Fields<Entity>(Entity.class, 2);
		FIELDS.set(new ChildrenByNameField(1, "childrenByName"));
		FIELDS.set(new DefinitionIdField<Entity>(2, "definitionId"));
//		FIELDS.set(1, "childrenByName");
//		FIELDS.set(2, "definitionId");
	}
	
	public EntitySchema() {
		super(FIELDS);
	}
	
	@Override
	public Entity newMessage() {
		return new Entity();
	}	
	
	private static class ChildrenByNameField extends Field<Entity> {

		public ChildrenByNameField(int number, String name) {
			super(FieldType.MESSAGE, number, name);
		}

		@Override
		protected void writeTo(Output output, Entity entity) throws IOException {
			Schema<HashMap> schema = RuntimeSchema.getSchema(HashMap.class);
			output.writeObject(number, entity.childrenByName, schema, false);
		}

		@Override
		protected void mergeFrom(Input input, Entity entity) throws IOException {
			Schema<HashMap> schema = RuntimeSchema.getSchema(HashMap.class);
			input.mergeObject(entity.childrenByName, schema);
		}

		@Override
		protected void transfer(Pipe pipe, Input input, Output output, boolean repeated) throws IOException {
			throw new UnsupportedOperationException("Pipes not supported");
		}
		
	}
}
