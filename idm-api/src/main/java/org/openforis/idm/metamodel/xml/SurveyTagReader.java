package org.openforis.idm.metamodel.xml;


import static org.openforis.idm.metamodel.xml.IdmlParser.IDML3_NS_URI;

import java.io.IOException;

import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodeListLabel;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.ModelVersion;
import org.openforis.idm.metamodel.Survey;
import org.openforis.idm.metamodel.SurveyContext;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SurveyTagReader extends XmlTagReader {
	
	private SurveyContext surveyContext;
	private Survey survey;
	 
	protected SurveyTagReader(SurveyContext surveyContext) {
		super(IDML3_NS_URI, "survey");
		this.surveyContext = surveyContext;
		setChildTagReaders(
			new ProjectTagReader(), 
			new SurveyUriTagReader(), 
			new CycleTagReader(),
			new SurveyDescriptionTagReader(), 
			new ConfigurationTagReader(),
			new VersioningTagReader(), 
			new CodeListsTagReader());
	}

	@Override
	public void readTag(XmlPullParser parser) throws XmlParseException {
		try {
			parser.nextTag();
			super.readTag(parser);
		} catch (XmlPullParserException e) {
			throw new XmlParseException(e);
		} catch (IOException e) {
			throw new XmlParseException(e);
		}
	}
	
	// TODO wrap exceptions with own class
	@Override
	public boolean handleStartTag(XmlPullParser parser) throws XmlParseException {
		// TODO update test IDML so that ids are unique within file and that lastId is correct
		String lastId = getRequiredAttribute(parser, "lastId");
		this.survey = new Survey(surveyContext, Integer.valueOf(lastId));
		return false;
	}
	
	public Survey getSurvey() {
		return survey;
	}

	// HELPER METHODS

	protected String getRequiredAttribute(XmlPullParser parser, String attr)
			throws XmlParseException {
		String value = getAttribute(parser, attr);
		if ( value == null || value.isEmpty() ) {
			throw new XmlParseException(parser, "missing required attribute "+attr);
		}
		return value;
	}

	// TODO namespace in kXML must be null for this to work, even though the attributes 
	// are in IDML namespace.  how does this behave with other xmlpull implementation  
	protected String getAttribute(XmlPullParser parser, String attr) {
		String value = parser.getAttributeValue(null, attr);
		return value;
	}

	
	// TAG READERS
	
	private class ProjectTagReader extends LanguageSpecificTextTagReader {
		public ProjectTagReader() {
			super("project");
		}
		
		@Override
		public void processText(LanguageSpecificText lst) {
			survey.addProjectName(lst);
		}
	}
	
	private class SurveyUriTagReader extends TextTagReader {
		public SurveyUriTagReader() {
			super("uri");
		}
		
		@Override
		public void processText(String text) {
			survey.setUri(text);
		}
	}
	
	private class CycleTagReader extends TextTagReader {
		public CycleTagReader() {
			super("cycle");
		}
		
		@Override
		public void processText(String text) {
			survey.setCycle(text);
		}
	}
	
	private class SurveyDescriptionTagReader extends LanguageSpecificTextTagReader {
		public SurveyDescriptionTagReader() {
			super("description");
		}
		
		@Override
		public void processText(LanguageSpecificText lst) {
			survey.addDescription(lst);
		}
	}
	
	private class ConfigurationTagReader extends XmlTagReader {
		public ConfigurationTagReader() {
			super(IDML3_NS_URI, "configuration");
		}

		@Override
		protected boolean handleStartTag(XmlPullParser parser)
				throws XmlParseException, XmlPullParserException, IOException {
			skip(parser);
			return true;
		}
	}
	
	private class VersioningTagReader extends XmlTagReader {
		
		private ModelVersion version;
		
		public VersioningTagReader() {
			super(IDML3_NS_URI, "versioning");
			setChildTagReaders(new VersionTagReader());
		}

		@Override
		public void readTag(XmlPullParser parser) throws XmlParseException, XmlPullParserException, IOException {
			super.readTag(parser);
			survey.addVersion(version);
		}

		@Override
		public void reset() {
			super.reset();
			this.version = null;
		}
		
		private class VersionTagReader extends XmlTagReader {
			
			public VersionTagReader() {
				super(IDML3_NS_URI, "version");
				setChildTagReaders(new LabelTagReader(), new DescriptionTagReader(), new DateTagReader());
			}
			
			@Override
			protected boolean handleStartTag(XmlPullParser parser) throws XmlParseException {
				version = new ModelVersion();
				String idString = getRequiredAttribute(parser, "id");
				int id = Integer.valueOf(idString);
				String name = getAttribute(parser, "name");
				version.setId(id);
				version.setName(name);
				return false;
			}
			
			private class LabelTagReader extends LanguageSpecificTextTagReader {
				public LabelTagReader() {
					super("label");
				}
				
				@Override
				public void processText(LanguageSpecificText lst) {
					version.addLabel(lst);
				}
			}

			private class DescriptionTagReader extends LanguageSpecificTextTagReader {
				public DescriptionTagReader() {
					super("description");
				}
				
				@Override
				public void processText(LanguageSpecificText lst) {
					version.addDescription(lst);
				}
			}

			private class DateTagReader extends TextTagReader {
				public DateTagReader() {
					super("date");
				}
				
				@Override
				public void processText(String text) {
					version.setDate(text);
				}
			}
		}
	}
	
	
	// TODO check 
	private class CodeListsTagReader extends XmlTagReader {
		
		public CodeListsTagReader() {
			super(IDML3_NS_URI, "codeLists");
			setChildTagReaders(new CodeListTagReader());
		}

		private class CodeListTagReader extends XmlTagReader {
			
			private CodeList list;
			
			public CodeListTagReader() {
				super(IDML3_NS_URI, "list");
				setChildTagReaders(new LabelTagReader(), new DescriptionTagReader());
			}

			@Override
			public void readTag(XmlPullParser parser)
					throws XmlParseException, XmlPullParserException, IOException {
				super.readTag(parser);
				survey.addCodeList(list);
			}

			@Override
			public void reset() {
				super.reset();
				this.list = null;
			}
			
			@Override
			protected boolean handleStartTag(XmlPullParser parser) throws XmlParseException {
				String idString = getRequiredAttribute(parser, "id");
				int id = Integer.valueOf(idString);
				String name = getAttribute(parser, "name");
				list = survey.createCodeList(id);
				list.setName(name);
				return false;
			}
			
			private class LabelTagReader extends LanguageSpecificTextTagReader {
				public LabelTagReader() {
					super("label");
				}
				
				@Override
				public void processLabel(String lang, String typeStr, String text) {
					// TODO throw IdmlParseException if typeStr is empty
					CodeListLabel.Type type = CodeListLabel.Type.valueOf(typeStr.toUpperCase()); 
					CodeListLabel label = new CodeListLabel(type, lang, text);
					list.addLabel(label);
				}
			}

			private class DescriptionTagReader extends LanguageSpecificTextTagReader {
				public DescriptionTagReader() {
					super("description");
				}
				
				@Override
				public void processText(LanguageSpecificText lst) {
					list.addDescription(lst);
				}
			}
		}
	}

}

