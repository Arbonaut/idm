package org.openforis.idm.model;

import java.io.IOException;

import com.dyuproject.protostuff.Input;
import com.dyuproject.protostuff.Output;
import com.dyuproject.protostuff.Pipe;
import com.dyuproject.protostuff.WireFormat.FieldType;

/**
 * @author G. Miceli
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class AttributeFieldSchema extends MappedSchemaSupport<AttributeField> {

	private static Fields<AttributeField> FIELDS;

	/* WARNING: deleting or reordering FIELD_TYPES or fields will break protostuff deserialization! */
	private static final Class<?> FIELD_TYPES[] = {
		Boolean.class, Integer.class, Long.class, Double.class, String.class
	};
	
	static {
		/* WARNING: deleting or reordering FIELDS will break protostuff deserialization! */
		FIELDS = new Fields<AttributeField>(AttributeField.class, 4);
		FIELDS.set(new ValueTypeField(1, "valueType"));
		FIELDS.set(new ValueField(2, "value"));
		FIELDS.set(3, "remarks");
		FIELDS.set(4, "symbol");
	}
	
	public AttributeFieldSchema() {
		super(FIELDS);
	}

	@Override
	public boolean isInitialized(AttributeField field) {
		return field.valueType != null;
	}

	@Override
	public AttributeField<?> newMessage() {
		return new AttributeField();
	}
	
	private static int getTypeNumber(Class<?> valueType) {
		for (int i = 0; i < FIELD_TYPES.length; i++) {
			if ( valueType == FIELD_TYPES[i] ) {
				return i;
			}
		}
		throw new UnsupportedOperationException("AttributeField<"+valueType.getName()+"> not supported");
	}

	private static class ValueTypeField extends Field<AttributeField> {
		
		public ValueTypeField(int number, String name) {
			super(FieldType.UINT32, number, name);
		}
	
		@Override
		protected void writeTo(Output output, AttributeField fld) throws IOException {
			int type = getTypeNumber(fld.valueType);
			output.writeUInt32(number, type, false);
		}
	
		@Override
		protected void mergeFrom(Input input, AttributeField fld) throws IOException {
			int type = input.readUInt32();
			fld.valueType = FIELD_TYPES[type];
		}
	
		@Override
		protected void transfer(Pipe pipe, Input input, Output output, boolean repeated) throws IOException {
			throw new UnsupportedOperationException("Pipes not supported");
		}
	}
	
	private static class ValueField extends Field<AttributeField> {

		public ValueField(int number, String name) {
			super(FieldType.MESSAGE, number, name);
		}

		@Override
		protected void writeTo(Output out, AttributeField fld) throws IOException {
			if ( fld.value != null ) {
				if ( fld.valueType == Boolean.class ) {
					out.writeBool(number, (Boolean) fld.value, false);
				} else if ( fld.valueType == Integer.class ) {
					out.writeInt32(number, (Integer) fld.value, false);
				} else if ( fld.valueType ==  Long.class ) {
					out.writeInt64(number, (Long) fld.value, false);
				} else if ( fld.valueType ==  Double.class ) {
					out.writeDouble(number, (Double) fld.value, false);
				} else if ( fld.valueType == String.class ) {
					out.writeString(number, (String) fld.value, false);
				} else {
					throw new UnsupportedOperationException("Cannot serialized Field<"+fld.valueType.getClass().getSimpleName()+">");
				}
			}
		}

		@Override
		protected void mergeFrom(Input in, AttributeField fld) throws IOException {
			if ( fld.valueType == Boolean.class ) {
				fld.value = in.readBool();
			} else if ( fld.valueType == Integer.class ) {
				fld.value = in.readInt32();
			} else if ( fld.valueType ==  Long.class ) {
				fld.value = in.readInt64();
			} else if ( fld.valueType ==  Double.class ) {
				fld.value = in.readDouble();
			} else if ( fld.valueType == String.class ) {
				fld.value = in.readString();
			} else {
				throw new UnsupportedOperationException("Cannot serialized Field<"+fld.valueType.getClass().getSimpleName()+">");
			}
		}

		@Override
		protected void transfer(Pipe pipe, Input input, Output output, boolean repeated) throws IOException {
			throw new UnsupportedOperationException("Pipes not supported");		
		}
	}
}