package org.openforis.idm.metamodel.xml.internal.marshal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates a method which is called after unmarshalling has completed 
 * 
 * @author G. Miceli
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlInit {
}
