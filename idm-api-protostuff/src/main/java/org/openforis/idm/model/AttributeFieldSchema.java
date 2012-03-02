package org.openforis.idm.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.dyuproject.protostuff.Input;
import com.dyuproject.protostuff.Output;
import com.dyuproject.protostuff.Pipe;
import com.dyuproject.protostuff.WireFormat.FieldType;
import com.dyuproject.protostuff.runtime.MappedSchema;
import com.dyuproject.protostuff.runtime.MappedSchema.Field;
import com.dyuproject.protostuff.runtime.RuntimeFieldFactory;

@SuppressWarnings({"rawtypes", "unchecked"})
public class AttributeFieldSchema extends MappedSchema<AttributeField> {

	private static Collection<Field<AttributeField>> FIELDS;
	
	static {
		try {
			FIELDS = new ArrayList<MappedSchema.Field<AttributeField>>();
			ValueTypeField f = new ValueTypeField();
//			java.lang.reflect.Field fld = AttributeField.class.getDeclaredField("valueType");
//			RuntimeFieldFactory<?> factory = RuntimeFieldFactory.getFieldFactory(Object.class);
//			Field<AttributeField> f = factory.create(0, "value", fld);
			FIELDS.add(f);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/* catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public AttributeFieldSchema() {
		super(AttributeField.class, FIELDS, FIELDS.size());
	}

	@Override
	public boolean isInitialized(AttributeField field) {
		return field.valueType != null;
	}

	@Override
	public AttributeField<?> newMessage() {
		return new AttributeField();
	}
}

class ValueTypeField extends Field<AttributeField> {
	/* WARNING: deleting or reordering FIELD_TYPES or fields will break protostuff deserialization! */
	
	private static final Class<?> FIELD_TYPES[] = {
		Boolean.class, Integer.class, Long.class, Double.class, String.class
	};

	public ValueTypeField() {
		super(FieldType.INT32, 1, "valueType");
	}

	@Override
	protected void writeTo(Output output, AttributeField valueType) throws IOException {
		System.out.println();
//		int type = getTypeFromInt(valueType);
//		output.writeInt32(number, type, false);
	}

//	private int getTypeFromInt(AttributeField valueType) {
//		for (int i = 0; i < FIELD_TYPES.length; i++) {
//			if ( valueType == FIELD_TYPES[i] ) {
//				return i;
//			}
//		}
//		throw new UnsupportedOperationException("AttributeField<"+valueType.getName()+"> not supported");
//	}

	@Override
	protected void mergeFrom(Input input, AttributeField message) throws IOException {
		System.out.println();
		int type = input.readInt32();
		// TODO Auto-generated method stub
		// NOW WHAT
	}

	@Override
	protected void transfer(Pipe pipe, Input input, Output output,
			boolean repeated) throws IOException {
		System.out.println();
		// TODO Auto-generated method stub
		
	}
	
}