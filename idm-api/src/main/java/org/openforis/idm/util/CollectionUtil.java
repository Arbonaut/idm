/**
 * 
 */
package org.openforis.idm.util;

import java.util.Collections;
import java.util.List;

/**
 * @author S. Ricci
 *
 */
public class CollectionUtil {

	 /**
	 * 
	 * Returns an unmodifiable view of the specified list. 
	 * This method makes use of the method unmodifiableList of java.util.Collections 
	 * and returns an empty list if the provided list is null.
	 * 
	 * @param list
	 * @return 
	 * @see java.util.Collections.unmodifiableList
	 */
	 public static <T> List<T> unmodifiableList(List<? extends T> list) {
		 if(list == null) {
			 return Collections.emptyList();
		 } else {
			 return Collections.unmodifiableList(list);
		 }
	 }
	
}
