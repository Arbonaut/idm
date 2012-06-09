package org.openforis.idm.metamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openforis.idm.metamodel.xml.internal.NumberAttributeDefinitionTypeAdapter;
import org.openforis.idm.util.CollectionUtil;

/**
 * @author G. Miceli
 */
@XmlTransient
public abstract class NumericAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 1L;

	public enum Type {
		INTEGER(Integer.class), REAL(Double.class);

		private Class<? extends Number> numberType;
		
		private Type(Class<? extends Number> numberType) {
			this.numberType = numberType;
		}
		public Class<?> getNumberType() {
			return numberType;
		}
	}

	@XmlElement(name = "precision", type = Precision.class)
	private List<Precision> precisionDefinitions;

	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(NumberAttributeDefinitionTypeAdapter.class) Type type;

	public NumericAttributeDefinition() {
		super();
	}

	public Type getType() {
		return type == null ? Type.REAL : type;
	}

	public void setType(Type type) {
		checkLockState();
		this.type = type;
	}
	
	public boolean isInteger() {
		return getType() == Type.INTEGER;
	}

	public boolean isReal() {
		return getType() == Type.REAL;
	}

	public List<Precision> getPrecisionDefinitions() {
		return CollectionUtil.unmodifiableList(precisionDefinitions);
	}

	public void addPrecisionDefinition(Precision precision) {
		checkLockState();
		if ( precisionDefinitions == null ) {
			precisionDefinitions = new ArrayList<Precision>();
		}
		precisionDefinitions.add(precision);
	}
	
	/**
	 * @return true if the unit may be user-defined, false if the value is always measured with the same (or no) unit  
	 */
	public boolean isVariableUnit() {
		if ( precisionDefinitions == null ) {
			return false;
		}
		boolean unitFound = false;
		for (Precision p : precisionDefinitions) {
			Unit unit = p.getUnit();
			if ( unit != null ) {
				if ( unitFound ) {
					return true;
				}
				unitFound = true;
			}
		}
		return false;
	}

	/**
	 * Returns the precision with default="true".  If no default precision is
	 * specified returns the first one.  Null if none are specified.
	 * @return
	 */
	public Precision getDefaultPrecision() {
		if ( precisionDefinitions == null || precisionDefinitions.isEmpty() ) {
			return null;
		}
		for (Precision pd : precisionDefinitions) {
			if ( pd.isDefaultPrecision() ) {
				return pd;
			}
		} 
		return precisionDefinitions.get(0);
	}

	/**
	 * @return the unit of the default precision.  If no default precision is specified,
	 * returns the first unit defined
	 */
	public Unit getDefaultUnit() {
		Precision defaultPrecision = getDefaultPrecision();
		Unit unit = defaultPrecision.getUnit();
		if ( unit != null && precisionDefinitions != null ) {
			for (Precision pd : precisionDefinitions) {
				unit = pd.getUnit();
				if ( unit != null ) {
					break;
				}
			}
		}
		return unit;
	}
	
	public List<Unit> getUnits() {
		List<Unit> units = new ArrayList<Unit>();
		if ( precisionDefinitions != null ) {
			for (Precision precision : precisionDefinitions) {
				Unit unit = precision.getUnit();
				if ( unit != null ) {
					units.add(unit);
				}
			}
		}
		return CollectionUtil.unmodifiableList(units);
	}
	
}