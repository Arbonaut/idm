package org.openforis.idm.metamodel.xml.internal.marshal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author G. Miceli
 *
 */
public class PolymorphicIdmlMarshaller<T, P> extends AbstractIdmlMarshaller<T, P> {

	private Map<Class<? extends T>, AbstractIdmlMarshaller<? extends T, ?>> delegateMarshallers;
	
	public PolymorphicIdmlMarshaller() {
		this.delegateMarshallers = new HashMap<Class<? extends T>, AbstractIdmlMarshaller<? extends T,?>>();
	}
	
	protected <V extends T> void setDelegate(Class<V> clazz, AbstractIdmlMarshaller<V, ?> delegate) {
		delegateMarshallers.put(clazz, delegate);
	}
	
	@SuppressWarnings("unchecked")
	protected <V extends T> AbstractIdmlMarshaller<V, ?> getDelegate(Class<V> clazz) {
		return (AbstractIdmlMarshaller<V, ?>) delegateMarshallers.get(clazz);
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void marshal(T sourceObject) throws IOException {
		Class clz = sourceObject.getClass();
		AbstractIdmlMarshaller delegate = getDelegate(clz);
		if ( delegate == null ) {
			throw new UnsupportedOperationException("Unhandled "+clz); 
		} else {
			prepareChildMarshaller(delegate);
			delegate.marshal(sourceObject);
		}
	}
}
