package org.openforis.idm.metamodel.xml.internal.marshal;

import java.io.IOException;

import org.openforis.idm.metamodel.VersionableSurveyObject;
import static org.openforis.idm.metamodel.xml.IdmlConstants.*;

/**
 * 
 * @author G. Miceli
 *
 * @param <T>
 * @param <P>
 */
public abstract class VersionableSurveyObjectMarshaller<T extends VersionableSurveyObject, P>
		extends AbstractIdmlMarshaller<T, P> {

	protected VersionableSurveyObjectMarshaller(String tag) {
		super(tag);
	}

	@Override
	protected void attributes(T o) throws IOException {
		attribute(SINCE, o.getSinceVersionName());
		attribute(DEPRECATED, o.getDeprecatedVersion());
	}
}
