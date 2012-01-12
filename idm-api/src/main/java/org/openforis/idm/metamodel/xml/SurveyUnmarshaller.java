package org.openforis.idm.metamodel.xml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.ValidationEventCollector;

import org.openforis.idm.metamodel.Configuration;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.xml.internal.ConfigurationXmlAdapter;
import org.openforis.idm.metamodel.xml.internal.XmlInherited;
import org.openforis.idm.metamodel.xml.internal.XmlInit;
import org.openforis.idm.metamodel.xml.internal.XmlParent;

/**
 * @author G. Miceli
 */
public class SurveyUnmarshaller {

	private ConfigurationAdapter<? extends Configuration> configurationAdapter;
	
	public ConfigurationAdapter<? extends Configuration> getConfigurationAdapter() {
		return configurationAdapter;
	}

	public void setConfigurationAdapter(ConfigurationAdapter<? extends Configuration> configurationAdapter) {
		this.configurationAdapter = configurationAdapter;
	}

	public Survey unmarshal(InputStream is) throws IOException, InvalidIdmlException {
		try {
			JAXBContext jc = BindingContext.getInstance();
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			if ( configurationAdapter == null ) {
				unmarshaller.setAdapter(BindingContext.getDefaultConfigurationAdapter());
			} else {
				unmarshaller.setAdapter(new ConfigurationXmlAdapter(configurationAdapter));
			}
			Listener listener = new Listener();
			unmarshaller.setListener(listener);
			ValidationEventCollector vec = new ValidationEventCollector();
			unmarshaller.setEventHandler( vec );

			Survey survey = (Survey) unmarshaller.unmarshal(is);

			if ( vec.hasEvents() ) {
				throw new InvalidIdmlException(vec.getEvents());
			}
			return survey;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}


	private class Listener extends Unmarshaller.Listener {
		@Override
		public void beforeUnmarshal(Object target, Object parent) {
			Class<?> targetClass = target.getClass();
			while ( !Object.class.equals(targetClass) ) {
				Field[] targetFields = targetClass.getDeclaredFields();
				for (Field targetField : targetFields) {
					XmlParent xmlParentAnnotation = targetField.getAnnotation(XmlParent.class);
					if ( xmlParentAnnotation != null ) {
						setParentReference(targetField, target, parent);
					}
					
					XmlInherited xmlInheritedAnnotation = targetField.getAnnotation(XmlInherited.class);
					if ( xmlInheritedAnnotation != null ) {
						setInheritedReference(xmlInheritedAnnotation.value()[0], targetField, target, parent);
					}
				}
				targetClass = targetClass.getSuperclass();
			}
		}

		@Override
		public void afterUnmarshal(Object target, Object parent) {
			Class<?> targetClass = target.getClass();
			while ( !Object.class.equals(targetClass) ) {
				Method[] targetMethods = targetClass.getDeclaredMethods();
				for (Method targetMethod : targetMethods) {
					XmlInit ann = targetMethod.getAnnotation(XmlInit.class);
					if ( ann != null ) {
						try {
							targetMethod.setAccessible(true);
							targetMethod.invoke(target);
						} catch (InvocationTargetException e) {
							throw new RuntimeException("Error initializing object after unmarshalling", e);
						} catch (IllegalAccessException e) {
							throw new RuntimeException("Error initializing object after unmarshalling", e);
						} finally {
							targetMethod.setAccessible(false);
						}
					}
				}
				targetClass = targetClass.getSuperclass();
			}
		}
	}

	
	private static void setParentReference(Field targetField, Object target, Object parent) {
		if ( targetField.getType().isAssignableFrom(parent.getClass()) ) {
			targetField.setAccessible(true);
			try {
				// Set parent into field with annotation
				targetField.set(target, parent);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Error unmarshalling parent reference", e);
			} finally {
				targetField.setAccessible(false);
			}
		}
	}
	
	private static void setInheritedReference(String parentFieldName, Field targetField, Object target, Object parent) {
		Field sourceField = findField(parentFieldName, parent);
		if ( sourceField == null ) {
			return;
		}
		try {
			sourceField.setAccessible(true);
			targetField.setAccessible(true);
			
			// Copy value from field in parent into field with annotation
			Object inheritedValue = sourceField.get(parent);
			targetField.set(target, inheritedValue);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error unmarshalling inherited reference", e);
		} finally {
			sourceField.setAccessible(false);
			targetField.setAccessible(false);
		}
	}
	
	private static Field findField(String name, Object parent) {
		Class<?> parentClass = parent.getClass();
		while ( !Object.class.equals(parentClass) ) {
			try {
				Field field = parentClass.getDeclaredField(name);
				if ( field != null ) {
					return field;
				}
			} catch (NoSuchFieldException e) {
			}
			parentClass = parentClass.getSuperclass();
		}
		return null;
	}

}
