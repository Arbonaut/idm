package org.openforis.idm.model;

import static com.dyuproject.protostuff.runtime.RuntimeReflectionFieldFactory.CHAR;
import static com.dyuproject.protostuff.runtime.RuntimeReflectionFieldFactory.STRING;

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

	static {
		/* WARNING: deleting or reordering FIELDS will break protostuff deserialization! */
		FIELDS = new Fields<AttributeField>(AttributeField.class, 3);
		FIELDS.set(new ValueField(1, "value"));
		FIELDS.set(STRING, 2, "remarks");
		FIELDS.set(CHAR, 3, "symbol");
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
		throw new UnsupportedOperationException();
	}

	public static class ValueField extends Field<AttributeField> {

		public ValueField(int number, String name) {
			super(FieldType.MESSAGE, number, name);
		}

		@Override
		protected void writeTo(Output out, AttributeField fld) throws IOException {
			if ( fld.valueType == Boolean.class ) {
				out.writeInt32(number, encodeBoolean((Boolean) fld.value), false);
			} else if ( fld.valueType == Integer.class ) {
				out.writeInt32(number, encodeInteger((Integer) fld.value), false);
			} else if ( fld.valueType ==  Long.class ) {
				out.writeInt64(number, encodeLong((Long) fld.value), false);
			} else if ( fld.valueType ==  Double.class ) {
				out.writeDouble(number, encodeDouble((Double) fld.value), false);
			} else if ( fld.valueType == String.class ) {
				out.writeString(number, encodeString((String) fld.value), false);
			} else {
				throw new UnsupportedOperationException("Cannot serialize AttributeField<"+fld.valueType.getClass().getSimpleName()+">");
			}
		}

		private int encodeBoolean(Boolean value) {
			return value == null ? Integer.MIN_VALUE : value ? -1 : 0;
		}

		private int encodeInteger(Integer value) {
			return value == null ? Integer.MIN_VALUE : value;
		}
		
		private long encodeLong(Long value) {
			return value == null ? Long.MIN_VALUE : value;
		}

		private double encodeDouble(Double value) {
			return value == null ? Double.MIN_VALUE : value;
		}

		private String encodeString(String value) {
			return value == null ? "null" : value;
		}

		@Override
		protected void mergeFrom(Input in, AttributeField fld) throws IOException {
			if ( fld.valueType == Boolean.class ) {
				fld.value = decodeBoolean(in.readInt32());
			} else if ( fld.valueType == Integer.class ) {
				fld.value = decodeInteger(in.readInt32());
			} else if ( fld.valueType ==  Long.class ) {
				fld.value = decodeDouble(in.readInt64());
			} else if ( fld.valueType ==  Double.class ) {
				fld.value = decodeDouble(in.readDouble());
			} else if ( fld.valueType == String.class ) {
				fld.value = decodeString(in.readString());
			} else {
				throw new UnsupportedOperationException("Cannot deserialize AttributeField<"+fld.valueType.getClass().getSimpleName()+">");
			}
		}

		private Boolean decodeBoolean(int value) {
			return value == Integer.MIN_VALUE ? null : value == -1;
		}

		private Integer decodeInteger(int value) {
			return value == Integer.MIN_VALUE ? null : value;
		}

		private Double decodeDouble(double value) {
			return value == Double.MIN_VALUE ? null : value;
		}

		private String decodeString(String value) {
			return value.equals("null") ? null : value;
		}

		@Override
		protected void transfer(Pipe pipe, Input input, Output output, boolean repeated) throws IOException {
			throw new UnsupportedOperationException("Pipes not supported");		
		}
	}
}