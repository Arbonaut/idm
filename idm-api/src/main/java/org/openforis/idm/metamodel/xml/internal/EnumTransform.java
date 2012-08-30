package org.openforis.idm.metamodel.xml.internal;

import org.simpleframework.xml.transform.Transform;

/**
 * 
 * @author S. Ricci
 *
 */
public class EnumTransform<T extends Enum<T>> implements Transform<Enum<T>> {
	
	private final Class<T> type;

    public EnumTransform(Class<T> type) {
        this.type = type;
    }

    @Override
    public Enum<T> read(String value) throws Exception {
    	if ( value != null ) {
    		return Enum.valueOf(type, value.toUpperCase());
    	} else {
    		return null;
    	}
    }

    @Override
    public String write(Enum<T> value) throws Exception {
    	 return value.name().toLowerCase();
    }

}
