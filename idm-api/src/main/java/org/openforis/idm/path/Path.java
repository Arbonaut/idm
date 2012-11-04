package org.openforis.idm.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.model.Node;
import org.openforis.idm.model.Record;

/**
 * 
 * @author G. Miceli
 *
 */
public final class Path implements Axis, Iterable<PathElement> {
	private Path parentPath;
	private PathElement lastElement;
	private boolean absolute;
	
	private Path(Path parentPath, PathElement lastElement) {
		this.parentPath = parentPath;
		this.lastElement = lastElement;
		this.absolute = parentPath.absolute;
	}

	private Path(PathElement firstElement, boolean absolute) {
		this.lastElement = firstElement;
		this.absolute = absolute;
	}
	
	public Path getParentPath() {
		return parentPath;
	}
	
	public Axis getAxis() {
		return lastElement;
	}

	public Path append(PathElement lastElement) {
		return new Path(this, lastElement);
	}

	public Path appendElement(String name) {
		return new Path(this, new PathElement(name));
	}

	public Path appendElement(String name, int idx) {
		return new Path(this, new PathElement(name, idx));
	}
	
	public boolean isAbsolute() {
		return absolute;
	}
	
	@Override
	public List<Node<?>> evaluate(Node<?> context) {
		if ( absolute ) {
			Record record = context.getRecord();
			return evaluate(record);
		} else if ( parentPath == null ) {
			return lastElement.evaluate(context);
		} else {
			List<Node<?>> contexts = parentPath.evaluate(context);
			List<Node<?>> results = new ArrayList<Node<?>>();
			for (Node<?> ctx : contexts) {
				List<Node<?>> eval = lastElement.evaluate(ctx);
				results.addAll(eval);
			}
			return Collections.unmodifiableList(results);
		}
	}

	@Override
	public List<Node<?>> evaluate(Record context) {
		if ( parentPath == null ) {
			return lastElement.evaluate(context);
		} else {
			List<Node<?>> contexts = parentPath.evaluate(context);
			List<Node<?>> results = new ArrayList<Node<?>>();
			for (Node<?> ctx : contexts) {
				List<Node<?>> eval = lastElement.evaluate(ctx);
				results.addAll(eval);
			}
			return Collections.unmodifiableList(results);
		}
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		toString(sb);
		return sb.toString();
	}

	private void toString(StringBuffer sb) {
		if ( absolute ) {
			sb.append('/');
		}
		if ( parentPath != null ) {
			parentPath.toString(sb);
			sb.append('/');
		}
		sb.append(lastElement);
	}

	@Override
	public NodeDefinition evaluate(NodeDefinition context) {
		if ( absolute ) {
			Schema schema = context.getSchema();
			return evaluate(schema);
		} else { 
			if ( parentPath != null ) {
				context = parentPath.evaluate(context);
			}
			return lastElement.evaluate(context);
		}
	}

	@Override
	public NodeDefinition evaluate(Schema context) {
		if ( parentPath == null ) {
			return lastElement.evaluate(context);
		} else {
			NodeDefinition ctx = parentPath.evaluate(context);
			return lastElement.evaluate(ctx);
		}
	}
	
	public static Path parse(String path) throws InvalidPathException {
		int idx = path.lastIndexOf('/');
		if ( idx < 0 ) {
			PathElement lastElement = PathElement.parseElement(path);
			return new Path(lastElement, false);
		} else {
			String head = path.substring(0, idx);
			String tail = path.substring(idx+1);
			PathElement lastElement = PathElement.parseElement(tail);
			if ( idx == 0 ){
				return new Path(lastElement, true);
			} else {
				Path parentPath = parse(head);
				return parentPath.append(lastElement);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lastElement == null) ? 0 : lastElement.hashCode());
		result = prime * result + ((parentPath == null) ? 0 : parentPath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Path other = (Path) obj;
		if (lastElement == null) {
			if (other.lastElement != null)
				return false;
		} else if (!lastElement.equals(other.lastElement))
			return false;
		if (parentPath == null) {
			if (other.parentPath != null)
				return false;
		} else if (!parentPath.equals(other.parentPath))
			return false;
		return true;
	}

	public static Path pathOf(NodeDefinition defn) {
		NodeDefinition parent = defn.getParentDefinition();
		String name = defn.getName();
		PathElement elem = new PathElement(name);
		if ( parent == null ) {
			return new Path(elem, true);
		} else {
			Path parentPath = Path.pathOf(parent);
			return parentPath.append(elem);
		}
	}

	public static Path relative(String... elements) {
		return fromElements(false, elements);
	}

	public static Path absolute(String... elements) {
		return fromElements(true, elements);
	}

	private static Path fromElements (boolean absolute, String... elements) {
		Path path = null;
		for (String element : elements) {
			PathElement e = new PathElement(element);
			if ( path == null ) {
				path = new Path(e, absolute);
			} else {
				path = path.append(e);
			}
		}
		return path;
	}
	
	public List<PathElement> elements() {
		List<PathElement> list = new ArrayList<PathElement>();
		elements(list);
		return Collections.unmodifiableList(list);
	}
	
	private void elements(List<PathElement> list) {
		if ( parentPath != null ) {
			parentPath.elements(list);
		}
		list.add(lastElement);
	}

	@Override
	public Iterator<PathElement> iterator() {
		return elements().iterator();
	}
}
