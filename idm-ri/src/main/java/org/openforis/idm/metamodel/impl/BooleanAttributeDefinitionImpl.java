/**
 * 
 */
package org.openforis.idm.metamodel.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.openforis.idm.metamodel.BooleanAttributeDefinition;

/**
 * @author M. Togna
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class BooleanAttributeDefinitionImpl extends AbstractAttributeDefinition implements BooleanAttributeDefinition {

	@XmlAttribute(name = "affirmativeOnly")
	private boolean affirmativeOnly;

	@Override
	public boolean isAffirmativeOnly() {
		return this.affirmativeOnly;
	}


}
