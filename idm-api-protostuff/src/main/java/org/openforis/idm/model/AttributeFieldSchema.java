package org.openforis.idm.model;

import java.io.IOException;

import com.dyuproject.protostuff.Input;
import com.dyuproject.protostuff.Output;
import com.dyuproject.protostuff.ProtobufException;

/**
 * @author G. Miceli
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class AttributeFieldSchema extends SchemaSupport<AttributeField> {

	public AttributeFieldSchema() {
		super(AttributeField.class, "value", "symbol", "remarks");
	}

	@Override
	public String messageName() {
		return "field";
	}

	@Override
	public void writeTo(Output out, AttributeField fld) throws IOException {
		if ( fld.value != null ) { 
			if ( fld.valueType == Boolean.class ) {
				out.writeInt32(1, (Boolean) fld.value ? -1 : 0, false);
			} else if ( fld.valueType == Integer.class ) {
				out.writeInt32(1, (Integer) fld.value, false);
			} else if ( fld.valueType ==  Long.class ) {
				out.writeInt64(1, (Long) fld.value, false);
			} else if ( fld.valueType == Double.class ) {
				out.writeDouble(1, (Double) fld.value, false);
			} else if ( fld.valueType == String.class ) {
				out.writeString(1, (String) fld.value, false);
			} else {
				throw new UnsupportedOperationException("Cannot serialize AttributeField<"+fld.valueType.getClass().getSimpleName()+">");
			}
		}
		if ( fld.symbol != null ) {
			out.writeString(2, fld.symbol.toString(), false);
		}
		if ( fld.remarks != null ) {
			out.writeString(3, fld.remarks, false);
		}
	}

	@Override
	public void mergeFrom(Input in, AttributeField fld) throws IOException {
        for(int number = in.readFieldNumber(this);;
        		number = in.readFieldNumber(this))
        {
			switch (number) {
			case 0:
				return;
			case 1:
				if ( fld.valueType == Boolean.class ) {
					fld.value = in.readInt32() != 0;
				} else if ( fld.valueType == Integer.class ) {
					fld.value = in.readInt32();
				} else if ( fld.valueType ==  Long.class ) {
					fld.value = in.readInt64();
				} else if ( fld.valueType ==  Double.class ) {
					fld.value = in.readDouble();
				} else if ( fld.valueType == String.class ) {
					fld.value = in.readString();
				} else {
					throw new UnsupportedOperationException("Cannot deserialize AttributeField<"+fld.valueType.getClass().getSimpleName()+">");
				}
				break;
			case 2:
				fld.symbol = in.readString().charAt(0);
				break;
			case 3:
				fld.remarks = in.readString();
				break;
			default:
				throw new ProtobufException("AttributeField inccorectly serialized");
			}
        }
	}

}