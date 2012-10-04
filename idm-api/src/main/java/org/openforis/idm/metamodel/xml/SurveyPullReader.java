package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodeList.CodeScope;
import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.metamodel.CodeListLabel;
import org.openforis.idm.metamodel.CodeListLevel;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.NodeLabel;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.SpatialReferenceSystem;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.openforis.idm.metamodel.Unit;
import org.openforis.idm.metamodel.validation.Check;
import org.openforis.idm.metamodel.validation.ComparisonCheck;
import org.openforis.idm.metamodel.validation.DistanceCheck;
import org.openforis.idm.metamodel.validation.UniquenessCheck;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SurveyPullReader extends IdmlPullReader {
	
	private SurveyContext surveyContext;
	private Survey survey;
	private Schema schema;
	 
	protected SurveyPullReader(SurveyContext surveyContext) {
		super("survey");
		this.surveyContext = surveyContext;
		setChildPullReaders(
			new ProjectPR(), 
			new UriPR(), 
			new CyclePR(),
			new DescriptionPR(), 
			new ConfigurationPR(),
			new VersioningPR(), 
			new CodeListsPR(),
			new UnitsPR(),
			new SrsesPR(),
			new SchemaPR());
	}

	// TODO wrap exceptions with own class
	@Override
	public boolean onStartTag(XmlPullParser parser) throws XmlParseException {
		// TODO update test IDML so that ids are unique within file and that lastId is correct
		String lastId = getAttribute(parser, "lastId", true);
		this.survey = new Survey(surveyContext, Integer.valueOf(lastId));
		this.schema = survey.getSchema();
		return false;
	}
	
	public Survey getSurvey() {
		return survey;
	}

	// TAG READERS
	
	private class ProjectPR extends LanguageSpecificTextPullReader {
		public ProjectPR() {
			super("project");
		}
		
		@Override
		public void processText(LanguageSpecificText lst) {
			survey.addProjectName(lst);
		}
	}
	
	private class UriPR extends TextPullReader {
		public UriPR() {
			super("uri", 1);
		}
		
		@Override
		public void processText(String text) {
			survey.setUri(text);
		}
	}
	
	private class CyclePR extends TextPullReader {
		public CyclePR() {
			super("cycle", 1);
		}
		
		@Override
		public void processText(String text) {
			survey.setCycle(text);
		}
	}
	
	private class DescriptionPR extends LanguageSpecificTextPullReader {
		public DescriptionPR() {
			super("description");
		}
		
		@Override
		public void processText(LanguageSpecificText lst) {
			survey.addDescription(lst);
		}
	}
	
	private class ConfigurationPR extends IdmlPullReader {
		public ConfigurationPR() {
			super("configuration");
		}

		@Override
		protected boolean onStartTag(XmlPullParser parser)
				throws XmlParseException, XmlPullParserException, IOException {
			skip(parser);
			return true;
		}
	}
	
	private class VersioningPR extends IdmlPullReader {
		
		public VersioningPR() {
			super("versioning", 1);
			setChildPullReaders(new VersionPR());
		}

		private class VersionPR extends IdmlPullReader {

			private ModelVersion version;
			
			public VersionPR() {
				super("version");
				setChildPullReaders(new LabelPR(), new DescriptionPR(), new DatePR());
			}
			
			@Override
			protected boolean onStartTag(XmlPullParser parser) throws XmlParseException {
				int id = getIntegerAttribute(parser, "id", true);
				String name = getAttribute(parser, "name", false);
				version = survey.createModelVersion(id);
				version.setName(name);
				return false;
			}

			private class LabelPR extends LanguageSpecificTextPullReader {
				public LabelPR() {
					super("label");
				}
				
				@Override
				public void processText(LanguageSpecificText lst) {
					version.addLabel(lst);
				}
			}

			private class DescriptionPR extends LanguageSpecificTextPullReader {
				public DescriptionPR() {
					super("description");
				}
				
				@Override
				public void processText(LanguageSpecificText lst) {
					version.addDescription(lst);
				}
			}

			private class DatePR extends TextPullReader {
				public DatePR() {
					super("date", 1);
				}
				
				@Override
				public void processText(String text) {
					version.setDate(text);
				}
			}
		
			@Override
			protected void onEndTag(XmlPullParser parser) throws XmlParseException {
				survey.addVersion(version);
				this.version = null;
			}
		}
	}
	
	private class CodeListsPR extends IdmlPullReader {
		
		public CodeListsPR() {
			super("codeLists", 1);
			setChildPullReaders(new CodeListPR());
		}

		private class CodeListPR extends IdmlPullReader {
			
			private CodeList list;
			
			public CodeListPR() {
				super("list");
				setChildPullReaders(new LabelPR(), new CodingSchemePR(), new HierarchyPR(), new DescriptionPR(), new ItemsPR());
			}
			
			@Override
			protected boolean onStartTag(XmlPullParser parser) throws XmlParseException {
				int id = getIntegerAttribute(parser, "id", true);
				String name = getAttribute(parser, "name", false);
				list = survey.createCodeList(id);
				list.setName(name);
				return false;
			}

			private class CodingSchemePR extends IdmlPullReader {
				public CodingSchemePR() {
					super("codingScheme", 1);
				}
				
				@Override
				protected boolean onStartTag(XmlPullParser parser)
						throws XmlParseException, XmlPullParserException, IOException {
					String scopeStr = getAttribute(parser, "scope", true);
					try {
						CodeScope scope = CodeList.CodeScope.valueOf(scopeStr.toUpperCase());
						list.setCodeScope(scope);
						return false;
					} catch ( IllegalArgumentException ex ) {
						throw new XmlParseException(parser, "Invalid code scope "+scopeStr);
					}
				}
			}
			
			private class HierarchyPR extends IdmlPullReader {
				public HierarchyPR() {
					super("hierarchy", 1);
					setChildPullReaders(new LevelPR());
				}
				
				private class LevelPR extends IdmlPullReader {
					private CodeListLevel level;
					
					public LevelPR() {
						super("level");
						setChildPullReaders(new LabelPR(), new DescriptionPR());						
					}
					
					@Override
					protected boolean onStartTag(XmlPullParser parser)
							throws XmlParseException, XmlPullParserException, IOException {
						this.level = new CodeListLevel();
						String name = getAttribute(parser, "name", true);
						level.setName(name);
						return false;
					}

					private class LabelPR extends LanguageSpecificTextPullReader {
						public LabelPR() {
							super("label");
						}
						
						@Override
						public void processText(LanguageSpecificText lst) {
							level.addLabel(lst);
						}
					}

					private class DescriptionPR extends LanguageSpecificTextPullReader {
						public DescriptionPR() {
							super("description");
						}
						
						@Override
						public void processText(LanguageSpecificText lst) {
							level.addDescription(lst);
						}
					}
					
					@Override
					protected void onEndTag(XmlPullParser parser) throws XmlParseException {
						list.addLevel(level);
						this.level = null;
					}
				}
			}			
			
			private class LabelPR extends LanguageSpecificTextPullReader {
				public LabelPR() {
					super("label");
				}
				
				@Override
				public void processText(String lang, String typeStr, String text) {
					// TODO throw Exception if typeStr is empty
					CodeListLabel.Type type = CodeListLabel.Type.valueOf(typeStr.toUpperCase()); 
					CodeListLabel label = new CodeListLabel(type, lang, text);
					list.addLabel(label);
				}
			}

			private class DescriptionPR extends LanguageSpecificTextPullReader {
				public DescriptionPR() {
					super("description");
				}
				
				@Override
				public void processText(LanguageSpecificText lst) {
					list.addDescription(lst);
				}
			}

			private class ItemsPR extends IdmlPullReader {
				
				public ItemsPR() {
					super("items", 1);
					setChildPullReaders(new ItemPR());
				}
				
				private class ItemPR extends IdmlPullReader {
					private CodeListItem parentItem;
					private CodeListItem item;
					
					public ItemPR() {
						super("item");
						setChildPullReaders(new CodePR(), new LabelPR(), new DescriptionPR());
					} 
					
					public ItemPR(CodeListItem parentItem) {
						this();
						this.parentItem = parentItem;
					}

					@Override
					protected boolean onStartTag(XmlPullParser parser)
							throws XmlParseException, XmlPullParserException, IOException {
						int id = getIntegerAttribute(parser, "id", true);
						Boolean q = getBooleanAttribute(parser, "qualifiable", false);
						this.item = list.createItem(id);
						item.setQualifiable(q==null ? false : q);
						return false;
					}
					
					private class CodePR extends TextPullReader {
						public CodePR() {
							super("code", 1);
						}
						
						@Override
						public void processText(String text) {
							item.setCode(text);
						}
					}

					private class LabelPR extends LanguageSpecificTextPullReader {
						public LabelPR() {
							super("label");
						}
						
						@Override
						public void processText(LanguageSpecificText lst) {
							item.addLabel(lst);
						}
					}

					private class DescriptionPR extends LanguageSpecificTextPullReader {
						public DescriptionPR() {
							super("description");
						}
						
						@Override
						public void processText(LanguageSpecificText lst) {
							item.addDescription(lst);
						}
					}

					@Override
					protected XmlPullReader getChildTagReader(XmlPullParser parser) throws XmlParseException {
						if ( parser.getName().equals("item") ) {
							return new ItemPR(item);
						} else {
							return super.getChildTagReader(parser);
						}
					}
					@Override
					protected void onEndTag(XmlPullParser parser) throws XmlParseException {
						if ( parentItem == null ) {
							list.addItem(item);
						} else {
							parentItem.addChildItem(item);
						}
					}
				}
			}

			@Override
			public void onEndTag(XmlPullParser parser) throws XmlParseException {
				survey.addCodeList(list);
				this.list = null;
			}
		}
	}
	
	private class UnitsPR extends IdmlPullReader {

		public UnitsPR() {
			super("units", 1);
			setChildPullReaders(new UnitPR());
		}
		
		private class UnitPR extends IdmlPullReader {

			private Unit unit;
			
			public UnitPR() {
				super("unit");
				setChildPullReaders(new LabelPR(), new AbbreviationPR());
			}

			@Override
			protected boolean onStartTag(XmlPullParser parser) throws XmlParseException, XmlPullParserException, IOException {
				int id = getIntegerAttribute(parser, "id", true);
				String name = getAttribute(parser, "name", true);
				String dimension = getAttribute(parser, "dimension", true);
				Float conversionFactor = getFloatAttribute(parser, "conversionFactor", false);
				this.unit = survey.createUnit(id);
				unit.setName(name);
				unit.setDimension(dimension);
				unit.setConversionFactor(conversionFactor);
				return false;
			}
			
			private class LabelPR extends LanguageSpecificTextPullReader {
				public LabelPR() {
					super("label");
				}
				
				@Override
				public void processText(LanguageSpecificText lst) {
					unit.addLabel(lst);
				}
			}

			private class AbbreviationPR extends LanguageSpecificTextPullReader {
				public AbbreviationPR() {
					super("abbreviation");
				}
				
				@Override
				public void processText(LanguageSpecificText lst) {
					unit.addAbbreviation(lst);
				}
			}
		}
	}

	private class SrsesPR extends IdmlPullReader {
		
		public SrsesPR() {
			super("spatialReferenceSystems", 1);
			setChildPullReaders(new SrsPR());
		}

		private class SrsPR extends IdmlPullReader {

			private SpatialReferenceSystem srs;
			
			public SrsPR() {
				super("spatialReferenceSystem");
				setChildPullReaders(new LabelPR(), new DescriptionPR(), new WktPR());
			}
			
			@Override
			protected boolean onStartTag(XmlPullParser parser) throws XmlParseException {
				String id = getAttribute(parser, "srid", true);
				this.srs = new SpatialReferenceSystem();
				srs.setId(id);
				return false;
			}

			private class LabelPR extends LanguageSpecificTextPullReader {
				public LabelPR() {
					super("label");
				}
				
				@Override
				public void processText(LanguageSpecificText lst) {
					srs.addLabel(lst);
				}
			}

			private class DescriptionPR extends LanguageSpecificTextPullReader {
				public DescriptionPR() {
					super("description");
				}
				
				@Override
				public void processText(LanguageSpecificText lst) {
					srs.addDescription(lst);
				}
			}

			private class WktPR extends TextPullReader {
				public WktPR() {
					super("wkt", 1);
					setTrimWhitespace(false);
				}

				@Override
				public void processText(String text) {
					srs.setWellKnownText(text);
				}
			}
		
			@Override
			protected void onEndTag(XmlPullParser parser) throws XmlParseException {
				survey.addSpatialReferenceSystem(srs);
				this.srs = null;
			}
		}
	}

	private class SchemaPR extends IdmlPullReader {
		
		public SchemaPR() {
			super("schema");
			setChildPullReaders(new EntityDefinitionPR());
		}

		private abstract class NodeDefinitionPR extends IdmlPullReader {
			protected NodeDefinition defn;
			protected NodeDefinition parentDefn;
			
			public NodeDefinitionPR(String tagName) {
				super(tagName);
				setUnordered(true);
			}
			
			
			@Override
			protected boolean onStartTag(XmlPullParser parser)
					throws XmlParseException, XmlPullParserException,
					IOException {
				
				int id = getIntegerAttribute(parser, "id", true);
				this.defn = createDefinition(id);
				
				String name = getAttribute(parser, "name", true);
				String since = getAttribute(parser, "since", false);
				String deprecated = getAttribute(parser, "deprecated", false);
				Boolean required = getBooleanAttribute(parser, "required", false);
				String requiredIf = getAttribute(parser, "requiredIf", false);
				String relevant = getAttribute(parser, "relevant", false);
				Integer minCount = getIntegerAttribute(parser, "minCount", false);
				Boolean multiple = getBooleanAttribute(parser, "multiple", false);
				multiple = multiple==null ? false : multiple;
				Integer maxCount = getIntegerAttribute(parser, "maxCount", multiple && defn instanceof AttributeDefinition);
				// TODO parse "other" attributes (annotations)
				this.parentDefn = this.defn;
				defn.setMultiple(multiple);
				defn.setName(name);
				defn.setSinceVersionByName(since);
				defn.setDeprecatedVersionByName(deprecated);
				if ( minCount == null && required != null && required ) {
					defn.setMinCount(1);
				} else {
					defn.setMinCount(minCount);
				}
				defn.setMaxCount(maxCount);
				defn.setRequiredExpression(requiredIf);
				defn.setRelevantExpression(relevant);
				return false;
			}
			
			protected abstract NodeDefinition createDefinition(int id);
			
			@Override
			protected void onEndTag(XmlPullParser parser)
					throws XmlParseException {
				this.defn = parentDefn;
				this.parentDefn = defn.getParentDefinition();
			}
			
			protected class LabelPR extends LanguageSpecificTextPullReader {
				public LabelPR() {
					super("label");
				}
				
				@Override
				public void processText(String lang, String typeStr, String text) {
					// TODO throw Exception if typeStr is empty
					NodeLabel.Type type = typeStr == null ? NodeLabel.Type.INSTANCE : NodeLabel.Type.valueOf(typeStr.toUpperCase()); 
					NodeLabel label = new NodeLabel(type, lang, text);
					defn.addLabel(label);
				}
			}

			protected class DescriptionPR extends LanguageSpecificTextPullReader {
				public DescriptionPR() {
					super("description");
				}
				
				@Override
				public void processText(LanguageSpecificText lst) {
					defn.addDescription(lst);
				}
			}
		}
		
		private class EntityDefinitionPR extends NodeDefinitionPR {
			public EntityDefinitionPR() {
				super("entity");
				setChildPullReaders(
						new LabelPR(), 
						new DescriptionPR(),
						this,
						new BooleanAttributeDefinitionPR(), 
						new CodeAttributeDefinitionPR(),
						new CoordinateAttributeDefinitionPR(),
						new DateAttributeDefinitionPR(),
						new TimeAttributeDefinitionPR(),
						new FileAttributeDefinitionPR(),
						new NumberAttributeDefinitionPR(),
						new RangeAttributeDefinitionPR(),
						new TaxonAttributeDefinitionPR(),
						new TextAttributeDefinitionPR());
			}

			@Override
			protected NodeDefinition createDefinition(int id) {
				return schema.createEntityDefinition(id);
			}
			
			@Override
			protected void onEndTag(XmlPullParser parser)
					throws XmlParseException {
				if ( parentDefn == null ) {
					schema.addRootEntityDefinition((EntityDefinition) defn);
				} else {
					EntityDefinition parentEntity = (EntityDefinition) parentDefn;
					parentEntity.addChildDefinition(defn);
				}
				super.onEndTag(parser);
			}
		}

		private abstract class AttributeDefinitionPR extends NodeDefinitionPR {

			public AttributeDefinitionPR(String tagName) {
				super(tagName);
				setChildPullReaders(
						new LabelPR(), 
						new DescriptionPR(),
						new CompareCheckPR(),
						new UniquenessCheckPR(),
						new DistanceCheckPR()
				);
			}

			protected abstract class CheckPR extends IdmlPullReader {
				protected Check<?> check;
				
				protected CheckPR(String tagName) {
					super(tagName);
					setChildPullReaders(new MessagesPR());
				}
				
				@Override
				protected boolean onStartTag(XmlPullParser parser)
						throws XmlParseException, XmlPullParserException,
						IOException {
					this.check = createCheck(parser);
					String flagStr = getAttribute(parser, "flag", true);
					// check that flag is value
					Check.Flag flag = Check.Flag.valueOf(flagStr.toUpperCase());
					String condition = getAttribute(parser, "if", false);
					check.setFlag(flag);
					check.setCondition(condition);
					return false;
				}
				
				protected abstract Check<?> createCheck(XmlPullParser parser);
				
				private class MessagesPR extends LanguageSpecificTextPullReader {
					public MessagesPR() {
						super("message");
					}
					@Override
					public void processText(LanguageSpecificText lst) {
						check.addMessage(lst);
					}
				}
				
				@Override
				protected void onEndTag(XmlPullParser parser)
						throws XmlParseException {
					((AttributeDefinition) defn).addCheck(check);
				}
			}
			
			private class CompareCheckPR extends CheckPR {

				protected CompareCheckPR() {
					super("compare");
				}
				
				@Override
				protected boolean onStartTag(XmlPullParser parser)
						throws XmlParseException, XmlPullParserException, IOException {
					super.onStartTag(parser);
					ComparisonCheck chk = (ComparisonCheck) check;
					chk.setEqualsExpression(getAttribute(parser, "eq", false));
					chk.setLessThanExpression(getAttribute(parser, "lt", false));
					chk.setLessThanOrEqualsExpression(getAttribute(parser, "lte", false));
					chk.setGreaterThanExpression(getAttribute(parser, "gt", false));
					chk.setGreaterThanOrEqualsExpression(getAttribute(parser, "gte", false));
					return false;
				}

				@Override
				protected Check<?> createCheck(XmlPullParser parser) {
					return new ComparisonCheck();
				}
			}
			
			private class DistanceCheckPR extends CheckPR {

				protected DistanceCheckPR() {
					super("distance");
				}
				
				@Override
				protected boolean onStartTag(XmlPullParser parser)
						throws XmlParseException, XmlPullParserException, IOException {
					super.onStartTag(parser);
					DistanceCheck chk = (DistanceCheck) check;
					chk.setMinDistanceExpression(getAttribute(parser, "min", false));
					chk.setMaxDistanceExpression(getAttribute(parser, "max", false));
					chk.setSourcePointExpression(getAttribute(parser, "from", false));
					chk.setDestinationPointExpression(getAttribute(parser, "to", false));
					
					return false;
				}

				@Override
				protected Check<?> createCheck(XmlPullParser parser) {
					return new DistanceCheck();
				}
			}
			
			private class UniquenessCheckPR extends CheckPR {

				protected UniquenessCheckPR() {
					super("unique");
				}
				
				@Override
				protected boolean onStartTag(XmlPullParser parser)
						throws XmlParseException, XmlPullParserException, IOException {
					super.onStartTag(parser);
					UniquenessCheck chk = (UniquenessCheck) check;
					chk.setExpression(getAttribute(parser, "expr", true));
					return false;
				}

				@Override
				protected Check<?> createCheck(XmlPullParser parser) {
					return new UniquenessCheck();
				}
			}
		}
		
		private class BooleanAttributeDefinitionPR extends AttributeDefinitionPR {

			public BooleanAttributeDefinitionPR() {
				super("boolean");
			}

			@Override
			protected NodeDefinition createDefinition(int id) {
				return schema.createBooleanAttributeDefinition(id);
			}
		}

		private class CodeAttributeDefinitionPR extends AttributeDefinitionPR {

			public CodeAttributeDefinitionPR() {
				super("code");
			}

			@Override
			protected NodeDefinition createDefinition(int id) {
				return schema.createCodeAttributeDefinition(id);
			}
		}
		
		private class CoordinateAttributeDefinitionPR extends AttributeDefinitionPR {

			public CoordinateAttributeDefinitionPR() {
				super("coordinate");
			}

			@Override
			protected NodeDefinition createDefinition(int id) {
				return schema.createCoordinateAttributeDefinition(id);
			}
		}
		
		private class DateAttributeDefinitionPR extends AttributeDefinitionPR {

			public DateAttributeDefinitionPR() {
				super("date");
			}

			@Override
			protected NodeDefinition createDefinition(int id) {
				return schema.createCoordinateAttributeDefinition(id);
			}
		}
		
		private class TimeAttributeDefinitionPR extends AttributeDefinitionPR {

			public TimeAttributeDefinitionPR() {
				super("time");
			}

			@Override
			protected NodeDefinition createDefinition(int id) {
				return schema.createTimeAttributeDefinition(id);
			}
		}
		
		private class FileAttributeDefinitionPR extends AttributeDefinitionPR {

			public FileAttributeDefinitionPR() {
				super("file");
			}

			@Override
			protected NodeDefinition createDefinition(int id) {
				return schema.createFileAttributeDefinition(id);
			}
		}
		private class NumberAttributeDefinitionPR extends AttributeDefinitionPR {

			public NumberAttributeDefinitionPR() {
				super("number");
			}

			@Override
			protected NodeDefinition createDefinition(int id) {
				return schema.createNumberAttributeDefinition(id);
			}
		}
		
		private class RangeAttributeDefinitionPR extends AttributeDefinitionPR {

			public RangeAttributeDefinitionPR() {
				super("range");
			}

			@Override
			protected NodeDefinition createDefinition(int id) {
				return schema.createRangeAttributeDefinition(id);
			}
		}
		
		private class TaxonAttributeDefinitionPR extends AttributeDefinitionPR {

			public TaxonAttributeDefinitionPR() {
				super("taxon");
			}

			@Override
			protected NodeDefinition createDefinition(int id) {
				return schema.createTaxonAttributeDefinition(id);
			}
		}

		private class TextAttributeDefinitionPR extends AttributeDefinitionPR {

			public TextAttributeDefinitionPR() {
				super("text");
			}

			@Override
			protected NodeDefinition createDefinition(int id) {
				return schema.createTextAttributeDefinition(id);
			}
		}
	}
}
