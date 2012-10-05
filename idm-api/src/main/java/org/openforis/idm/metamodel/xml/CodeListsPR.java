package org.openforis.idm.metamodel.xml;

import java.io.IOException;

import org.openforis.idm.metamodel.CodeList;
import org.openforis.idm.metamodel.CodeList.CodeScope;
import org.openforis.idm.metamodel.CodeListItem;
import org.openforis.idm.metamodel.CodeListLabel;
import org.openforis.idm.metamodel.CodeListLevel;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.Survey;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author G. Miceli
 */
class CodeListsPR extends IdmlPullReader {
	
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
			Survey survey = getSurvey();
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
			Survey survey = list.getSurvey();
			survey.addCodeList(list);
			this.list = null;
		}
	}
}