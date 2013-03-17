package org.openforis.idm.metamodel.xml.internal.marshal;

import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

import java.io.IOException;

import org.openforis.idm.metamodel.AttributeDefault;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.validation.Check;
import org.openforis.idm.metamodel.validation.ComparisonCheck;
import org.openforis.idm.metamodel.validation.CustomCheck;
import org.openforis.idm.metamodel.validation.DistanceCheck;
import org.openforis.idm.metamodel.validation.PatternCheck;
import org.openforis.idm.metamodel.validation.UniquenessCheck;

/**
 * 
 * @author G. Miceli
 *
 * @param <T>
 */
abstract class AttributeDefinitionXS<T extends AttributeDefinition> extends NodeDefinitionXS<T, EntityDefinition> {

	protected AttributeDefinitionXS(String tag) {
		super(tag);
		addChildMarshallers(new DefaultXS(), new CheckXSDelegator());
	}

	private class DefaultXS extends XmlSerializerSupport<AttributeDefault, AttributeDefinition> {
		public DefaultXS() {
			super(DEFAULT);
		}
		@Override
		protected void marshalInstances(AttributeDefinition defn) throws IOException {
			marshal(defn.getAttributeDefaults());
		}
		
		@Override
		protected void attributes(AttributeDefault d) throws IOException {
			attribute(VALUE, d.getValue());
			attribute(EXPR, d.getExpression());
			attribute(IF, d.getCondition());
		}
	}
	
	private class CheckXSDelegator extends PolymorphicXmlSerializer<Check<?>, AttributeDefinition> {
		public CheckXSDelegator() {
			setDelegate(CustomCheck.class, new CustomCheckXS());
			setDelegate(ComparisonCheck.class, new ComparisonCheckXS());
			setDelegate(UniquenessCheck.class, new UniquenessCheckXS());
			setDelegate(DistanceCheck.class, new DistanceCheckXS());
			setDelegate(PatternCheck.class, new PatternCheckXS());
		}
		@Override
		protected void marshalInstances(AttributeDefinition defn) throws IOException {
			marshal(defn.getChecks());
		}
	}
	
	private abstract class CheckXS<C extends Check<?>> extends XmlSerializerSupport<C, AttributeDefinition> {
		protected CheckXS(String tag) {
			super(tag);
			addChildMarshallers(new MessageXS());
		}
		
		@Override
		protected void attributes(C check) throws IOException {
			attribute(FLAG, check.getFlag().name().toLowerCase());
			attribute(IF, check.getCondition());
		}
		
		private class MessageXS extends LanguageSpecificTextXS<Check<?>> {

			public MessageXS() {
				super(MESSAGE);
			}
			
			@Override
			protected void marshalInstances(Check<?> check) throws IOException {
				marshal(check.getMessages());
			}
		}
	}
	
	private class CustomCheckXS extends CheckXS<CustomCheck> {
		public CustomCheckXS() {
			super(CHECK);
		}
		
		@Override
		protected void attributes(CustomCheck check) throws IOException {
			super.attributes(check);
			attribute(EXPR, check.getExpression());
		}
	}
	
	private class ComparisonCheckXS extends CheckXS<ComparisonCheck> {
		public ComparisonCheckXS() {
			super(COMPARE);
		}
		
		@Override
		protected void attributes(ComparisonCheck check) throws IOException {
			super.attributes(check);
			attribute(LT, check.getLessThanExpression());
			attribute(LTE, check.getLessThanOrEqualsExpression());
			attribute(GT, check.getGreaterThanExpression());
			attribute(GTE, check.getGreaterThanOrEqualsExpression());
		}
	}
	
	private class UniquenessCheckXS extends CheckXS<UniquenessCheck> {
		public UniquenessCheckXS() {
			super(UNIQUE);
		}
		
		@Override
		protected void attributes(UniquenessCheck check) throws IOException {
			super.attributes(check);
			attribute(EXPR, check.getExpression());
		}
	}
	
	private class DistanceCheckXS extends CheckXS<DistanceCheck> {
		public DistanceCheckXS() {
			super(DISTANCE);
		}
		
		@Override
		protected void attributes(DistanceCheck check) throws IOException {
			super.attributes(check);
			attribute(MIN, check.getMinDistanceExpression());
			attribute(MAX, check.getMaxDistanceExpression());
			attribute(FROM, check.getSourcePointExpression());
			attribute(TO, check.getDestinationPointExpression());
		}
	}
	
	private class PatternCheckXS extends CheckXS<PatternCheck> {
		public PatternCheckXS() {
			super(PATTERN);
		}
		
		@Override
		protected void attributes(PatternCheck check) throws IOException {
			super.attributes(check);
			attribute(REGEX, check.getRegularExpression());
		}
	}
}
